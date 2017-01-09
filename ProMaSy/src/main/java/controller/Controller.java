/*
  This class connects MainFrame and Database
 */
package controller;

import gui.*;
import gui.bids.BidsListPanelListener;
import gui.bids.reports.ReportParametersDialogListener;
import gui.bids.reports.ReportParametersEvent;
import gui.empedit.CreateEmployeeDialogListener;
import gui.empedit.CreateEmployeeFromLoginListener;
import gui.finance.FinancePanelListener;
import gui.instedit.OrganizationDialogListener;
import gui.login.LoginListener;
import model.dao.Database;
import model.dao.LoginData;
import model.enums.Role;
import model.models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class Controller {

    private Properties conSet;
    private MainFrame mainFrame;
    private Preferences prefs;

    public Controller(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        mainFrame.setVisible(false);

        // Preferences class used for storage of connection settings to DB
        prefs = Preferences.userRoot().node("db_con");

        // trying to get connection settings form prefs object,
        // if it doesn't exist defaults will be used
        setConnectionSettings();

        // if user entered new settings for connection to DB - putting them to Prefs
        mainFrame.setConSetListener(e -> {
            System.out.println(e.getServer());
            prefs.put("server", e.getServer());
            prefs.put("host", e.getDatabase());
            prefs.put("schema", e.getSchema());
            prefs.putInt("port", e.getPortNumber());
            prefs.put("user", e.getUser());
            prefs.put("password", e.getPassword());
            setConnectionSettings(e.getServer(), e.getDatabase(), e.getSchema(), e.getPortNumber(),
                    e.getUser(), e.getPassword());

            // trying to connect with new settings
            disconnect();
            setConnectionSettings();
            connect();
            checkVersion();
            checkFirstRun();
        });

        connect();
        checkVersion();
        checkFirstRun();

        // init LoginListener here, because loginDialog appears before the MainFrame
        mainFrame.setLoginListener(new LoginListener() {
            public void loginAttemptOccurred(String user, char[] password) {
                String pass = Utils.makePass(password, getSalt(user));
                boolean isLoginDataValid = checkLogin(user, pass);
                if (isLoginDataValid) {
                    // if login was successful init MainFrame and make it visible
                    mainFrame.initialize();
                    initListeners();
                    // if role USER or PERSONALLY_LIABLE_EMPLOYEE load data according to department
                    if (LoginData.getInstance().getRole() == Role.PERSONALLY_LIABLE_EMPLOYEE
                            || LoginData.getInstance().getRole() == Role.USER) {
                        loadDataToView(LoginData.getInstance().getSubdepartment().getDepartment().getModelId());
                    } else loadDataToView();

                    mainFrame.setVisible(true);
                    mainFrame.writeStatusPanelCurrentUser(LoginData.getInstance().getShortName());
                } else if (!isLoginDataValid) {
                    // if login wasn't successful showing error dialog
                    JOptionPane.showMessageDialog(mainFrame, Labels.getProperty("wrongCredentialsPlsCheck"),
                            Labels.getProperty("loginError"), JOptionPane.ERROR_MESSAGE);
                }
            }

            // if user do not want login call close method
            public void loginCancelled() {
                close();
            }

            // creating new user
            public boolean isAbleToRegister() {
                int registrationNumber = registrationsLeft();
                System.out.println("Ticket number: " + registrationNumber);
                if (registrationNumber > 0) {
                    LoginData.getInstance(new EmployeeModel(registrationNumber, Role.USER));
                    mainFrame.initialize();
                    initListeners();
                    loadDataToView();
                    return true;
                } else return false;
            }
        });

        mainFrame.showLoginDialog();
    }

    private void loadDataToView() {
        //loading default data into model
        mainFrame.setCpvModelList(getCpvRequest("", true));
        mainFrame.setInstituteModelList(getInstRequest());
//        getDepartments(LoginData.getInstance().getSubdepartment().getDepartment().getInstitute().getModelId());
        mainFrame.setEmployeeModelList(getEmployees());
        mainFrame.setAmountUnitsModelList(getAmUnits());
        mainFrame.setProducerModelList(getProd());
        mainFrame.setSupplierModelList(getSupl());
        mainFrame.setReasonsModelList(getReasons());
        mainFrame.setFinanceModelList(getFinances());
        mainFrame.setBidModelList(getBids());
        mainFrame.setFinanceDepartmentModelList(getDepartmentFinancesByOrder(0));
    }

    private void loadDataToView(long departmentId) {
        //loading default data into model
        mainFrame.setCpvModelList(getCpvRequest("", true));
        mainFrame.setInstituteModelList(getInstRequest());
//        getDepartments(LoginData.getInstance().getSubdepartment().getDepartment().getInstitute().getModelId());
        mainFrame.setEmployeeModelList(getEmployees(departmentId));
        mainFrame.setAmountUnitsModelList(getAmUnits());
        mainFrame.setProducerModelList(getProd());
        mainFrame.setSupplierModelList(getSupl());
        mainFrame.setReasonsModelList(getReasons());
        mainFrame.setFinanceModelList(getFinances(departmentId));
        mainFrame.setBidModelList(getBids());
        mainFrame.setFinanceDepartmentModelList(getDepartmentFinancesByOrder(0));
    }

    private void initListeners() {
        // adding implementation for closing operation via X-button on window
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                closeDialog();
            }
        });

        // setting listener to MainFrame
        mainFrame.setMainFrameListener(new MainFrameListener() {

            @Override
            public void searchForPerson(Role role, long selectedDepartmentId) {
                mainFrame.setEmployeeModelList(getEmployees(role, selectedDepartmentId));
            }

            @Override
            public void searchForPerson(Role role) {

                mainFrame.setEmployeeModelList(getEmployees(role));
            }

            @Override
            public void exitEventOccurred() {
                closeDialog();
            }

            @Override
            public void setMinimumVersionEventOccurred() {
                setCurrentVersionAsMinimum();
            }
        });

        mainFrame.setCpvListener(ev -> getCpvRequest(ev.getCpvRequest(), ev.isSameLvlShow()));

        mainFrame.setEmployeeDialogListener(model -> {
            createOrUpdate(model);
            mainFrame.setEmployeeModelList(getEmployees());
        });

        mainFrame.setCreateEmployeeDialogListener(new CreateEmployeeDialogListener() {

            @Override
            public void persistModelEventOccurred(EmployeeModel model) {
                createOrUpdate(model);
                mainFrame.setEmployeeModelList(getEmployees());
            }

            @Override
            public boolean checkUniqueLogin(String login) {
                return isLoginUnique(login);
            }
        });

        mainFrame.setOrganizationDialogListener(new OrganizationDialogListener() {

            @Override
            public void persistModelEventOccurred(InstituteModel model) {
                createOrUpdate(model);
                mainFrame.setInstituteModelList(getInstRequest());
            }

            @Override
            public void persistModelEventOccurred(DepartmentModel model) {
                createOrUpdate(model);
                mainFrame.setDepartmentModelList(getDepartments(model.getInstitute().getModelId()));
            }

            @Override
            public void persistModelEventOccurred(SubdepartmentModel model) {
                createOrUpdate(model);
                mainFrame.setSubdepartmentModelList(getSubdepRequest(model.getDepartment().getModelId()));
            }
        });

        mainFrame.setAmUnitsDialogListener(model -> {
            createOrUpdate(model);
            mainFrame.setAmountUnitsModelList(getAmUnits());
        });

        mainFrame.setProducerDialogListener(model -> {
            createOrUpdate(model);
            mainFrame.setProducerModelList(getProd());
        });

        mainFrame.setSupplierDialogListener(model -> {
            createOrUpdate(model);
            mainFrame.setSupplierModelList(getSupl());
        });
        mainFrame.setFinancePanelListener(new FinancePanelListener() {
            @Override
            public void persistModelEventOccurred(FinanceModel model) {
                createOrUpdate(model);
                mainFrame.setFinanceModelList(getFinances());
            }

            @Override
            public void persistModelEventOccurred(FinanceDepartmentModel model) {
                createOrUpdate(model);
                mainFrame.setFinanceDepartmentModelList(getDepartmentFinancesByOrder(model.getModelId()));
            }
        });

        mainFrame.setBidsListPanelListener(new BidsListPanelListener() {
            @Override
            public void persistModelEventOccurred(BidModel model) {
                createOrUpdate(model);
                mainFrame.setFinanceModelList(getFinances());
                mainFrame.setBidModelList(getBids());
            }

            @Override
            public void selectAllBidsEventOccurred() {
                mainFrame.setBidModelList(getBids());
            }
        });

        mainFrame.setCreateBidDialogListener(createdBidModel -> {
            createOrUpdate(createdBidModel);
            mainFrame.setFinanceModelList(getFinances());
            mainFrame.setBidModelList(getBids());
        });

        mainFrame.setReportParametersDialogListener(new ReportParametersDialogListener() {

            @Override
            public void roleSelectionOccurred(Role role) {
                mainFrame.setEmployeeModelList(getEmployees(role));
            }

            public void reportParametersSelectionOccurred(ReportParametersEvent ev) {
                ReportParametersData.getInstance().setData(ev.getHeadPosition(), ev.getHead(), ev.getDepartmentHead(),
                        ev.getPersonallyLiableEmpl(), ev.getAccountant(), ev.getEconomist(), ev.getHeadTender());
                mainFrame.bidListPrint();
            }
        });
    }

    private void checkVersion() {
        Version currentVersion = new Version(Labels.getVersion());
        Version dbVersion = getDBVersion();
        if (currentVersion.compareTo(dbVersion) == -1) {
            JOptionPane.showMessageDialog(mainFrame,
                    Labels.getProperty("youCantUseThisVersion") + "\n" +
                            Labels.withColon("yourVersion") + currentVersion.get() + "\n" +
                            Labels.withColon("newVersion") + dbVersion.get() + "\n" +
                            Labels.getProperty("askAdminAboutUpdate"),
                    Labels.getProperty("oldVersionOfApp"),
                    JOptionPane.ERROR_MESSAGE);
            close();
        }
    }

    private void checkFirstRun() {
        if (isFirstRun()) {
            int option = JOptionPane.showConfirmDialog(mainFrame, Labels.getProperty("firstRunLong"), Labels.getProperty("firstRun"), JOptionPane.YES_NO_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                createAdmin();
            } else if (option == JOptionPane.NO_OPTION) {
                closeDialog();
            }
        }
    }

    private void createAdmin() {
        mainFrame.getCreateEmployeeDialog().setLoginListener(new CreateEmployeeFromLoginListener() {
            @Override
            public void newUserCreatedEvent() {
                JOptionPane.showMessageDialog(mainFrame, Labels.getProperty("youCanLoginAfterRestart"),
                        Labels.getProperty("accountCreated"), JOptionPane.INFORMATION_MESSAGE);
                close();
            }

            @Override
            public void cancelEvent() {
                close();
            }
        });
        EmployeeModel firstUser = new EmployeeModel(Role.ADMIN);
        firstUser.setCreated();
        createOrUpdate(firstUser);
        LoginData.getInstance(firstUser);
        mainFrame.initialize();
        initListeners();
        loadDataToView();
        mainFrame.getCreateEmployeeDialog().createCustomUser(LoginData.getInstance());
    }

    private void logEvent(String message, Color color) {
        mainFrame.logEvent(message, color);
    }

    private void errorLogEvent(Exception exception, String message) {
        mainFrame.logEvent(exception, message);
    }

    // sets connection settings to Properties object
    private void setConnectionSettings() {
        setConnectionSettings(prefs.get("server", Labels.getProperty("connectionSettings.server")),
                prefs.get("database", Labels.getProperty("connectionSettings.database")),
                prefs.get("schema", Labels.getProperty("connectionSettings.schema")),
                prefs.getInt("port", Labels.getInt("connectionSettings.port")),
                prefs.get("user", Labels.getProperty("connectionSettings.user")),
                prefs.get("password", Labels.getProperty("connectionSettings.password")));
    }

    private void setConnectionSettings(String host, String database, String schema, int port, String user,
                                       String password) {
        mainFrame.setDefaultConnectionSettings(host, database, schema, port, user, password);
        if (conSet == null) {
            conSet = new Properties();
        }
        conSet.setProperty("host", host);
        conSet.setProperty("port", Integer.toString(port));
        conSet.setProperty("database", database);
        conSet.setProperty("user", user);
        conSet.setProperty("password", password);
        conSet.setProperty("currentSchema", schema);
    }

    // connecting to DB
    private void connect() {
        try {
            Database.DB.connect(conSet);
            logEvent(Labels.getProperty("connectedToDB"), Colors.GREEN);
        } catch (Exception e) {
            e.printStackTrace();
            logEvent(Labels.getProperty("noConnectionToDB"), Colors.RED);
            JOptionPane.showMessageDialog(mainFrame, Labels.getProperty("noConnectionToDB"),
                    Labels.getProperty("databaseConnectionError"), JOptionPane.ERROR_MESSAGE);
            // if can't connect - call ConnectionSettingsDialog
            mainFrame.showConSettDialog();
        }
    }

    // disconnecting from DB
    private void disconnect() {
        Database.DB.disconnect();
    }

    // methods requesting the DB
    // GETTERS
    private List<CPVModel> getCpvRequest(String cpvRequest, boolean sameLvlShow) {
        try {
            return Database.CPV.retrieve(cpvRequest, sameLvlShow);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("cpvRequest") + cpvRequest + Labels.withSpaceBefore("error"));
            return null;
        }
    }

    private Version getDBVersion() {
        try {
            return new Version(Database.VERSIONS.retrieve());
        } catch (SQLException e) {
            // this error occurs with old settings, have to reset it to defaults
            try {
                Preferences.userRoot().node("db_con").clear();
                disconnect();
                connect();
            } catch (BackingStoreException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            errorLogEvent(e, Labels.withColon("versionRequest") + Labels.withSpaceBefore("error"));
        }
        return null;
    }

    private void setCurrentVersionAsMinimum() {
        try {
            Database.VERSIONS.updateVersion();
            logEvent(Labels.withColon("minimumVersionWasSet") + Labels.getProperty("versionNumber"), Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("error") + Labels.withColon("minimumVersionWasSet") + Labels.getProperty("versionNumber"));
        }
    }

    private boolean isFirstRun() {
        try {
            return Database.EMPLOYEES.isFirstRun();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private List<EmployeeModel> getEmployees() {
        try {
            return Database.EMPLOYEES.getResults();
        } catch (SQLException e) {
            errorLogEvent(e,
                    Labels.withColon("request") + Labels.withSpaceBefore("user") + Labels.withSpaceBefore("error"));
            return null;
        }
    }

    private List<EmployeeModel> getEmployees(Role role) {
        try {
            return Database.EMPLOYEES.retrieve(role);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("request") + Labels.withSpaceBefore("user") + " role id: " + role
                    + Labels.withSpaceBefore("error"));
            return null;
        }
    }

    private List<EmployeeModel> getEmployees(long depId) {
        try {
            return Database.EMPLOYEES.retrieve(depId);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("request") + Labels.withSpaceBefore("user") + " dep id: " + depId
                    + Labels.withSpaceBefore("error"));
            return null;
        }
    }

    private List<EmployeeModel> getEmployees(Role role, long depId) {
        try {
            return Database.EMPLOYEES.retrieve(role, depId);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("request") + Labels.withSpaceBefore("user") + " role id: " + role
                    + " dep id: " + depId + Labels.withSpaceBefore("error"));
            return null;
        }
    }

    private long getSalt(String login) {
        try {
            return Database.EMPLOYEES.getSalt(login);
        } catch (SQLException e) {
            errorLogEvent(e, "Salt retrieval error with login: " + login);
            return 0;
        }
    }

    private List<InstituteModel> getInstRequest() {
        try {
            return Database.INSTITUTES.getResults();
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("request") + Labels.withSpaceBefore("institute")
                    + Labels.withSpaceBefore("error"));
            return null;
        }
    }

    private List<DepartmentModel> getDepartments(long instId) {
        try {
            return Database.DEPARTMENTS.retrieve(instId);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("request") + Labels.withSpaceBefore("department") + " inst id: " + instId
                    + Labels.withSpaceBefore("error"));
            return null;
        }
    }

    private List<SubdepartmentModel> getSubdepRequest(long depId) {
        try {
            return Database.SUBDEPARTMENS.retrieve(depId);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("request") + Labels.withSpaceBefore("subdepartment") + " dep id: " + depId
                    + Labels.withSpaceBefore("error"));
            return null;
        }
    }

    private List<AmountUnitsModel> getAmUnits() {
        try {
            return Database.AMOUNTUNITS.getResults();
        } catch (SQLException e) {
            errorLogEvent(e,
                    Labels.withColon("request") + Labels.withSpaceBefore("amount") + Labels.withSpaceBefore("error"));
            return null;
        }
    }

    private List<ProducerModel> getProd() {
        try {
            return Database.PRODUCERS.getResults();
        } catch (SQLException e) {
            errorLogEvent(e,
                    Labels.withColon("request") + Labels.withSpaceBefore("producer") + Labels.withSpaceBefore("error"));
            return null;
        }
    }

    private List<SupplierModel> getSupl() {
        try {
            return Database.SUPPLIERS.getResults();
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("request") + Labels.withSpaceBefore("suplBorder")
                    + Labels.withSpaceBefore("error"));
            return null;
        }
    }

    private List<ReasonForSupplierChoiceModel> getReasons() {
        try {
            return Database.REASONS.getResults();
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("request") + Labels.withSpaceBefore("reasonForSupplierChoice")
                    + Labels.withSpaceBefore("error"));
            return null;
        }
    }

    private List<FinanceModel> getFinances() {
        try {
            return Database.FINANCES.getResults();
        } catch (SQLException e) {
            errorLogEvent(e,
                    Labels.withColon("request") + Labels.withSpaceBefore("finances") + Labels.withSpaceBefore("error"));
            return null;
        }
    }

    private List<FinanceModel> getFinances(long departmentId) {
        try {
            return Database.FINANCES.retrieve(departmentId);
        } catch (SQLException e) {
            errorLogEvent(e,
                    Labels.withColon("request") + Labels.withSpaceBefore("finances") + Labels.withSpaceBefore("error"));
            return null;
        }
    }

    private List<FinanceDepartmentModel> getDepartmentFinancesByOrder(long orderId) {
        try {
            return Database.DEPARTMENT_FINANCES.retrieveByFinanceId(orderId);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("request") + Labels.withSpaceBefore("departmentFinances") + " order Id: "
                    + orderId + Labels.withSpaceBefore("error"));
            return null;
        }
    }

    private List<FinanceDepartmentModel> getDepartmentFinancesByDepartment(long departmentId) {
        try {
            return Database.DEPARTMENT_FINANCES.retrieveByDepartmentId(departmentId);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("request") + Labels.withSpaceBefore("departmentFinances")
                    + " department Id: " + departmentId + Labels.withSpaceBefore("error"));
            return null;
        }
    }

    // check user login and pass
    private boolean checkLogin(String username, String password) {
        try {
            return Database.EMPLOYEES.checkLogin(username, password);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("request") + Labels.withSpaceBefore("user") + " :" + username
                    + Labels.withSpaceBefore("error"));
            return false;
        }
    }

    private boolean isLoginUnique(String username) {
        try {
            return Database.EMPLOYEES.checkLogin(username);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private List<BidModel> getBids() {
        try {
            return Database.BIDS.getResults();
        } catch (SQLException e) {
            errorLogEvent(e,
                    Labels.withColon("request") + Labels.withSpaceBefore("bids") + Labels.withSpaceBefore("error"));
            return null;
        }
    }

    private void createOrUpdate(EmployeeModel model) {
        try {
            Database.EMPLOYEES.createOrUpdate(model);
            logEvent(Labels.withColon("createNewEmployee") + model.toString() + Labels.withSpaceBefore("success"),
                    Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e,
                    Labels.withColon("createNewEmployee") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void createOrUpdate(InstituteModel instModel) {
        try {
            Database.INSTITUTES.createOrUpdate(instModel);
            logEvent(Labels.withColon("addInstitute") + instModel.toString() + Labels.withSpaceBefore("success"),
                    Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("addInstitute") + instModel.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void createOrUpdate(DepartmentModel model) {
        try {
            Database.DEPARTMENTS.createOrUpdate(model);
            logEvent(Labels.withColon("addDepartment") + model.toString() + Labels.withSpaceBefore("success"),
                    Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("addDepartment") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void createOrUpdate(SubdepartmentModel model) {
        try {
            Database.SUBDEPARTMENS.createOrUpdate(model);
            logEvent(Labels.withColon("addSubdepartment") + model.toString() + Labels.withSpaceBefore("success"),
                    Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("addSubdepartment") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void createOrUpdate(AmountUnitsModel model) {
        try {
            Database.AMOUNTUNITS.createOrUpdate(model);
            logEvent(Labels.withColon("addAmUnit") + model.toString() + Labels.withSpaceBefore("success"), Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("addAmUnit") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void createOrUpdate(ProducerModel model) {
        try {
            Database.PRODUCERS.createOrUpdate(model);
            logEvent(Labels.withColon("addProd") + model.toString() + Labels.withSpaceBefore("success"), Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("addProd") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void createOrUpdate(SupplierModel model) {
        try {
            Database.SUPPLIERS.createOrUpdate(model);
            logEvent(Labels.withColon("addSupl") + model.toString() + Labels.withSpaceBefore("success"), Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("addSupl") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void createOrUpdate(ReasonForSupplierChoiceModel model) {
        try {
            Database.REASONS.createOrUpdate(model);
            logEvent(Labels.withColon("addReasonForSupplierChoice") + model.toString() + Labels.withSpaceBefore("success"), Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("addReasonForSupplierChoice") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void createOrUpdate(FinanceModel model) {
        try {
            Database.FINANCES.createOrUpdate(model);
            logEvent(Labels.withColon("createOrder") + model.toString() + Labels.withSpaceBefore("success"),
                    Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("createOrder") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void createOrUpdate(FinanceDepartmentModel model) {
        try {
            Database.DEPARTMENT_FINANCES.createOrUpdate(model);
            logEvent(Labels.withColon("addDepOrder") + model.toString() + Labels.withSpaceBefore("success"),
                    Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("addDepOrder") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }


    private void createOrUpdate(BidModel model) {
        try {
            Database.BIDS.createOrUpdate(model);
            logEvent(Labels.withColon("createBid") + model.toString() + Labels.withSpaceBefore("success"), Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("createBid") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private int registrationsLeft() {
        try {
            return Database.REGISTRATION.getRegistrationNumber();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // default close method
    private void close() {
        Database.DB.disconnect();
        mainFrame.dispose();
        System.exit(0);
    }

    // but it calls only in this close() method (except close in login dialog)
    private void closeDialog() {
        int action = JOptionPane.showConfirmDialog(this.mainFrame, Labels.getProperty("doYouWantExit"),
                Labels.getProperty("exitFromProgram"), JOptionPane.OK_CANCEL_OPTION);
        if (action == JOptionPane.OK_OPTION) {
            close();
        }
    }
}
