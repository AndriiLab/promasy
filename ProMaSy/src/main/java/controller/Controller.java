/*
  This class connects MainFrame and Database
 */
package controller;

import gui.*;
import gui.amunits.AmUnitsDialogListener;
import gui.bids.BidsListPanelListener;
import gui.bids.CreateBidDialogListener;
import gui.bids.reports.ReportParametersDialogListener;
import gui.bids.reports.ReportParametersEvent;
import gui.cpv.CpvReqEvent;
import gui.cpv.CpvSearchListener;
import gui.empedit.CreateEmployeeDialogListener;
import gui.empedit.CreateEmployeeFromLoginListener;
import gui.empedit.EditEmployeeDialogListener;
import gui.finance.FinancePanelListener;
import gui.instedit.OrganizationDialogListener;
import gui.login.LoginListener;
import gui.prodsupl.ProducerDialogListener;
import gui.prodsupl.ReasonsDialogListener;
import gui.prodsupl.SupplierDialogListener;
import model.DefaultValues;
import model.dao.Database;
import model.dao.LoginData;
import model.enums.BidType;
import model.enums.Role;
import model.models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class Controller {

    private ConnectionSettingsModel conSet;
    private MainFrame mainFrame;

    public Controller(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        // trying to get connection settings form serialized object,
        // if it doesn't exist defaults will be used
        setConnectionSettings();

        // if user entered new settings for connection to DB - putting them to Prefs
        mainFrame.setConSetListener(model -> {
            try {
                Utils.saveConnectionSettings(model);
            } catch (IOException e) {
                //TODO handle exception
                e.printStackTrace();
            }
            setConnectionSettings(model);

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
//                    // if role USER or PERSONALLY_LIABLE_EMPLOYEE load data according to department
//                    if (LoginData.getInstance().getRole() == Role.PERSONALLY_LIABLE_EMPLOYEE
//                            || LoginData.getInstance().getRole() == Role.USER) {
//                        loadDataToView(LoginData.getInstance().getSubdepartment().getDepartment().getModelId());
//                    } else loadDataToView();

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
                    return true;
                } else return false;
            }
        });

        mainFrame.showLoginDialog();
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

            @Override
            public void selectAllDepartmentsAndFinances(InstituteModel institute) {
                mainFrame.setDepartmentModelList(getDepartments(institute.getModelId()));
                mainFrame.setFinanceModelList(getFinances());
            }
        });

        mainFrame.setCpvListener(new CpvSearchListener() {
            @Override
            public void cpvSelectionEventOccurred(CpvReqEvent ev) {
                mainFrame.setCpvModelList(getCpvRequest(ev.getCpvRequest()));
            }

            @Override
            public void getTopCodes() {
                mainFrame.setCpvModelList(getCpvRequest(""));
            }
        });

        mainFrame.setEmployeeDialogListener(new EditEmployeeDialogListener() {
            @Override
            public void persistModelEventOccurred(EmployeeModel model) {
                createOrUpdate(model);
                mainFrame.setEmployeeModelList(getEmployees());
            }

            @Override
            public void getAllEmployees() {
                mainFrame.setEmployeeModelList(getEmployees());
            }
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

            @Override
            public void loadInstitutes() {
                mainFrame.setInstituteModelList(getInstRequest());
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

            @Override
            public void getAllInstitutes() {
                mainFrame.setInstituteModelList(getInstRequest());
            }
        });

        mainFrame.setAmUnitsDialogListener(new AmUnitsDialogListener() {
            @Override
            public void persistModelEventOccurred(AmountUnitsModel model) {
                createOrUpdate(model);
                mainFrame.setAmountUnitsModelList(getAmUnits());
            }

            @Override
            public void getAllAmUnits() {
                mainFrame.setAmountUnitsModelList(getAmUnits());
            }
        });

        mainFrame.setProducerDialogListener(new ProducerDialogListener() {
            @Override
            public void persistModelEventOccurred(ProducerModel model) {
                createOrUpdate(model);
                mainFrame.setProducerModelList(getProd());
            }

            @Override
            public void getAllProducers() {
                mainFrame.setProducerModelList(getProd());
            }
        });

        mainFrame.setSupplierDialogListener(new SupplierDialogListener() {
            @Override
            public void persistModelEventOccurred(SupplierModel model) {
                createOrUpdate(model);
                mainFrame.setSupplierModelList(getSupl());
            }

            @Override
            public void getAllSuppliers() {
                mainFrame.setSupplierModelList(getSupl());
            }
        });

        mainFrame.setReasonsDialogListener(new ReasonsDialogListener() {
            @Override
            public void persistModelEventOccurred(ReasonForSupplierChoiceModel model) {
                createOrUpdate(model);
                mainFrame.setReasonsModelList(getReasons());
            }

            @Override
            public void getReasonsForSupplierChoice() {
                mainFrame.setReasonsModelList(getReasons());
            }
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

            @Override
            public void loadDepartments() {
                mainFrame.setDepartmentModelList(getDepartments(LoginData.getInstance().getSubdepartment().getDepartment().getInstitute().getModelId()));
            }
        });

        mainFrame.setBidsListPanelListener(new BidsListPanelListener() {
            @Override
            public void persistModelEventOccurred(BidModel model) {
                createOrUpdate(model);
                mainFrame.setFinanceModelList(getFinances());
                mainFrame.setBidModelList(getBids(model.getType()));
            }

            @Override
            public void selectAllBidsEventOccurred(BidType type) {
                mainFrame.setBidModelList(getBids(type));
            }

            @Override
            public void getBidsByDepartment(BidType type, DepartmentModel selectedDepartmentModel) {
                mainFrame.setBidModelList(getBids(type, selectedDepartmentModel));
            }

            @Override
            public void getBidsBySubdepartment(BidType type, SubdepartmentModel selectedSubdepartmentModel) {
                mainFrame.setBidModelList(getBids(type, selectedSubdepartmentModel));
            }

            @Override
            public void getBidsByFinanceDepartment(BidType type, FinanceDepartmentModel selectedFinanceDepartmentModel) {
                mainFrame.setBidModelList(getBids(type, selectedFinanceDepartmentModel));
            }
        });

        mainFrame.setCreateBidDialogListener(new CreateBidDialogListener() {
            @Override
            public void persistModelEventOccurred(BidModel createdBidModel) {
                createOrUpdate(createdBidModel);
                mainFrame.setFinanceModelList(getFinances());
                mainFrame.setBidModelList(getBids(createdBidModel.getType()));
            }

            @Override
            public void getAllData() {
                mainFrame.setDepartmentModelList(getDepartments(LoginData.getInstance().getSubdepartment().getDepartment().getInstitute().getModelId()));
                mainFrame.setAmountUnitsModelList(getAmUnits());
                mainFrame.setProducerModelList(getProd());
                mainFrame.setSupplierModelList(getSupl());
                mainFrame.setReasonsModelList(getReasons());
            }

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
        List<EmployeeModel> employees = getEmployees();
        EmployeeModel firstUser;
        if (employees == null || employees.isEmpty()) {
            firstUser = new EmployeeModel(Role.ADMIN);
        } else {
            firstUser = employees.get(0);
        }
        firstUser.setCreated();
        createOrUpdate(firstUser);
        LoginData.getInstance(firstUser);
        mainFrame.initialize();
        initListeners();
        //passing institute structure
        try {
            DefaultValues.setAmountUnits();
            DefaultValues.setInstituteStructure();
            DefaultValues.setCpv();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        ConnectionSettingsModel model = null;
        try {
            model = Utils.loadConnectionSettings();
        } catch (FileNotFoundException e) {
            model = new ConnectionSettingsModel(Labels.getProperty("connectionSettings.server"), Labels.getProperty("connectionSettings.database"), Labels.getProperty("connectionSettings.schema"), Labels.getInt("connectionSettings.port"), Labels.getProperty("connectionSettings.user"), Labels.getProperty("connectionSettings.password"));
        } catch (IOException e) {
            // TODO handle exceptions
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        setConnectionSettings(model);
    }

    private void setConnectionSettings(ConnectionSettingsModel model) {
        this.conSet = model;
        mainFrame.setDefaultConnectionSettings(model);
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
    private List<CPVModel> getCpvRequest(String cpvRequest) {
        try {
            return Database.CPV.retrieve(cpvRequest);
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
            logEvent(Labels.withColon("minimumVersionWasSet") + Labels.getVersion(), Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("error") + Labels.withColon("minimumVersionWasSet") + Labels.getVersion());
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
                    Labels.withColon("request") + Labels.withSpaceBefore("role.user") + Labels.withSpaceBefore("error"));
            return null;
        }
    }

    private List<EmployeeModel> getEmployees(Role role) {
        try {
            return Database.EMPLOYEES.retrieve(role);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("request") + Labels.withSpaceBefore("role.user") + " role id: " + role
                    + Labels.withSpaceBefore("error"));
            return null;
        }
    }

    private List<EmployeeModel> getEmployees(long depId) {
        try {
            return Database.EMPLOYEES.retrieve(depId);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("request") + Labels.withSpaceBefore("role.user") + " dep id: " + depId
                    + Labels.withSpaceBefore("error"));
            return null;
        }
    }

    private List<EmployeeModel> getEmployees(Role role, long depId) {
        try {
            return Database.EMPLOYEES.retrieve(role, depId);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("request") + Labels.withSpaceBefore("role.user") + " role id: " + role
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
            errorLogEvent(e, Labels.withColon("request") + Labels.withSpaceBefore("role.user") + " :" + username
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

    private List<BidModel> getBids(BidType type) {
        try {
            return Database.BIDS.getResults(type);
        } catch (SQLException e) {
            errorLogEvent(e,
                    Labels.withColon("request") + Labels.withSpaceBefore("bids") + Labels.withSpaceBefore("error"));
            return null;
        }
    }

    private List<BidModel> getBids(BidType type, DepartmentModel department) {
        try {
            return Database.BIDS.retrieve(type, department);
        } catch (SQLException e) {
            errorLogEvent(e,
                    Labels.withColon("request") + Labels.withSpaceBefore("bids") + Labels.withSpaceBefore("error"));
            return null;
        }
    }

    private List<BidModel> getBids(BidType type, FinanceDepartmentModel financeDepartmentModel) {
        try {
            return Database.BIDS.retrieve(type, financeDepartmentModel);
        } catch (SQLException e) {
            errorLogEvent(e,
                    Labels.withColon("request") + Labels.withSpaceBefore("bids") + Labels.withSpaceBefore("error"));
            return null;
        }
    }

    private List<BidModel> getBids(BidType type, SubdepartmentModel model) {
        try {
            //todo
            return Database.BIDS.getResults(type);
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
