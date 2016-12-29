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
import gui.empedit.CreateEmployeeDialogListener;
import gui.empedit.CreateEmployeeFromLoginListener;
import gui.finance.FinancePanelListener;
import gui.instedit.OrganizationDialogListener;
import gui.login.LoginListener;
import gui.prodsupl.ProducerDialogListener;
import gui.prodsupl.ReasonsDialogListener;
import gui.prodsupl.SupplierDialogListener;
import model.*;
import model.enums.Role;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
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
            checkFirstRun();
            checkVersion();
        });

        connect();
        checkFirstRun();
        checkVersion();

        // init LoginListener here, because loginDialog appears before the MainFrame
        mainFrame.setLoginListener(new LoginListener() {
            public void loginAttemptOccurred(String user, char[] password) {
                String pass = Utils.makePass(password, getSalt(user));
                boolean isLoginDataValid = checkLogin(user, pass);
                if (isLoginDataValid) {
                    // if login was successful init MainFrame and make it visible
                    mainFrame.initialize();
                    initListeners();
                    // if role lower than "Head of department" load data according to department
                    if (LoginData.getInstance().getRoleId() < Role.HEAD_OF_DEPARTMENT.getRoleId()) {
                        loadDataToView();
                    } else loadDataToView(LoginData.getInstance().getDepId());

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
                    LoginData.getInstance(new EmployeeModel(registrationNumber, Role.USER.getRoleId()));
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
        //loading default data into models
        getCpvRequest("", true);
        getRolesRequest();
        getInstRequest();
        getDepartments(LoginData.getInstance().getInstId());
        getEmployees();
        getAmUnits();
        getProd();
        getSupl();
        getReasons();
        getFinances();
        getBids();
        getDepartmentFinancesByOrder(0);

        loadToView();
    }

    private void loadDataToView(long departmentId) {
        //loading default data into models
        getCpvRequest("", true);
        getRolesRequest();
        getInstRequest();
        getDepartments(LoginData.getInstance().getInstId());
        getEmployees(departmentId);
        getAmUnits();
        getProd();
        getSupl();
        getReasons();
        getFinances(departmentId);
        getBids(departmentId);
        getDepartmentFinancesByOrder(0);

        loadToView();
    }

    private void loadToView() {
        // passing loaded data to view
        mainFrame.setRoleModelList(Database.ROLES.getList());
        mainFrame.setAmountUnitsModelList(Database.AMOUNTUNITS.getList());
        mainFrame.setCpvModelList(Database.CPV.getList());
        mainFrame.setInstituteModelList(Database.INSTITUTES.getList());
        mainFrame.setDepartmentModelList(Database.DEPARTMENTS.getList());
        mainFrame.setEmployeeModelList(Database.EMPLOYEES.getList());
        mainFrame.setProducerModelList(Database.PRODUCERS.getList());
        mainFrame.setSupplierModelList(Database.SUPPLIERS.getList());
        mainFrame.setReasonsModelList(Database.REASONS.getList());
        mainFrame.setFinanceModelList(Database.FINANCES.getList());
        mainFrame.setFinanceDepartmentModelList(Database.DEPARTMENT_FINANCES.getList());
        mainFrame.setBidModelList(Database.BIDS.getList());
        mainFrame.setBidsPanelSum(getBidsSum(), BigDecimal.ZERO);
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
            public void searchForPerson(int roleId, long selectedDepartmentId) {
                getEmployees(roleId, selectedDepartmentId);
                mainFrame.setEmployeeModelList(Database.EMPLOYEES.getList());
            }

            @Override
            public void searchForPerson(int roleId) {
                getEmployees(roleId);
                mainFrame.setEmployeeModelList(Database.EMPLOYEES.getList());
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
            deleteEmployee(model);
            getEmployees();
            mainFrame.setEmployeeModelList(Database.EMPLOYEES.getList());
        });

        mainFrame.setCreateEmployeeDialogListener(new CreateEmployeeDialogListener() {
            public void instSelectionEventOccurred(long instId) {
                getDepartments(instId);
                mainFrame.setDepartmentModelList(Database.DEPARTMENTS.getList());
            }

            public void depSelectionEventOccurred(long depId) {
                getSubdepRequest(depId);
                mainFrame.setSubdepartmentModelList(Database.SUBDEPARTMENS.getList());
            }

            public void createEmployeeEventOccurred(EmployeeModel model) {
                setCreated(model);
                createEmployee(model);
                getEmployees();
                mainFrame.setEmployeeModelList(Database.EMPLOYEES.getList());
            }

            public void editEmployeeEventOccurred(EmployeeModel model) {
                setModified(model);
                editEmployee(model);
                getEmployees();
                mainFrame.setEmployeeModelList(Database.EMPLOYEES.getList());
            }

            @Override
            public boolean checkUniqueLogin(String login) {
                return isLoginUnique(login);
            }
        });

        mainFrame.setOrganizationDialogListener(new OrganizationDialogListener() {

            public void instSelectionEventOccurred(long instId) {
                getDepartments(instId);
                mainFrame.setDepartmentModelList(Database.DEPARTMENTS.getList());
            }

            public void depSelectionEventOccurred(long depId) {
                getSubdepRequest(depId);
                mainFrame.setSubdepartmentModelList(Database.SUBDEPARTMENS.getList());
            }

            public void createInstEventOccurred(InstituteModel instModel) {
                createInstitute(instModel);
                getInstRequest();
                mainFrame.setInstituteModelList(Database.INSTITUTES.getList());
            }

            public void editInstEventOccurred(InstituteModel instModel) {
                editInstitute(instModel);
                getInstRequest();
                mainFrame.setInstituteModelList(Database.INSTITUTES.getList());
            }

            public void deleteInstEventOccurred(InstituteModel instModel) {
                deleteInstitute(instModel);
                getInstRequest();
                mainFrame.setInstituteModelList(Database.INSTITUTES.getList());
            }

            public void createDepEventOccurred(DepartmentModel model) {
                createDepartment(model);
                getDepartments(model.getInstId());
                mainFrame.setDepartmentModelList(Database.DEPARTMENTS.getList());
            }

            public void editDepEventOccurred(DepartmentModel model) {
                editDepartment(model);
                getDepartments(model.getInstId());
                mainFrame.setDepartmentModelList(Database.DEPARTMENTS.getList());
            }

            public void deleteDepEventOccurred(DepartmentModel model) {
                deleteDepartment(model);
                getDepartments(model.getInstId());
                mainFrame.setDepartmentModelList(Database.DEPARTMENTS.getList());
            }

            public void createSubdepEventOccurred(SubdepartmentModel model) {
                createSubdepartment(model);
                getSubdepRequest(model.getDepId());
                mainFrame.setSubdepartmentModelList(Database.SUBDEPARTMENS.getList());
            }

            public void editSubdepEventOccurred(SubdepartmentModel model) {
                editSubdepartment(model);
                getSubdepRequest(model.getDepId());
                mainFrame.setSubdepartmentModelList(Database.SUBDEPARTMENS.getList());
            }

            public void deleteSubdepEventOccurred(SubdepartmentModel model) {
                deleteSubdepartment(model);
                getSubdepRequest(model.getDepId());
                mainFrame.setSubdepartmentModelList(Database.SUBDEPARTMENS.getList());
            }
        });

        mainFrame.setAmUnitsDialogListener(new AmUnitsDialogListener() {
            public void createEventOccurred(AmountUnitsModel model) {
                createAmUnit(model);
                getAmUnits();
                mainFrame.setAmountUnitsModelList(Database.AMOUNTUNITS.getList());
            }

            public void editEventOccurred(AmountUnitsModel model) {
                editAmUnit(model);
                getAmUnits();
                mainFrame.setAmountUnitsModelList(Database.AMOUNTUNITS.getList());
            }

            public void deleteEventOccurred(AmountUnitsModel model) {
                deleteAmUnit(model);
                getAmUnits();
                mainFrame.setAmountUnitsModelList(Database.AMOUNTUNITS.getList());
            }
        });

        mainFrame.setProducerDialogListener(new ProducerDialogListener() {
            public void createProdEventOccurred(ProducerModel model) {
                createProd(model);
                getProd();
                mainFrame.setProducerModelList(Database.PRODUCERS.getList());
            }

            public void editProdEventOccurred(ProducerModel model) {
                editProd(model);
                getProd();
                mainFrame.setProducerModelList(Database.PRODUCERS.getList());
            }

            public void deleteProdEventOccurred(ProducerModel model) {
                deleteProd(model);
                getProd();
                mainFrame.setProducerModelList(Database.PRODUCERS.getList());
            }
        });

        mainFrame.setSupplierDialogListener(new SupplierDialogListener() {
            public void createSuplEventOccurred(SupplierModel model) {
                createSupl(model);
                getSupl();
                mainFrame.setSupplierModelList(Database.SUPPLIERS.getList());
            }

            public void editSuplEventOccurred(SupplierModel model) {
                editSupl(model);
                getSupl();
                mainFrame.setSupplierModelList(Database.SUPPLIERS.getList());
            }

            public void deleteSuplEventOccurred(SupplierModel model) {
                deleteSupl(model);
                getSupl();
                mainFrame.setSupplierModelList(Database.SUPPLIERS.getList());
            }
        });
        mainFrame.setReasonsDialogListener(new ReasonsDialogListener() {
            @Override
            public void createReasonEventOccurred(ReasonForSupplierChoiceModel model) {
                createReason(model);
                getReasons();
                mainFrame.setReasonsModelList(Database.REASONS.getList());
            }

            @Override
            public void editReasonEventOccurred(ReasonForSupplierChoiceModel model) {
                editReason(model);
                getReasons();
                mainFrame.setReasonsModelList(Database.REASONS.getList());
            }

            @Override
            public void deleteReasonEventOccurred(ReasonForSupplierChoiceModel model) {
                deleteReason(model);
                getReasons();
                mainFrame.setReasonsModelList(Database.REASONS.getList());
            }
        });
        mainFrame.setFinancePanelListener(new FinancePanelListener() {
            public void createOrderEventOccurred(FinanceModel model) {
                createFinance(model);
                getFinances();
                mainFrame.setFinanceModelList(Database.FINANCES.getList());
            }

            public void editOrderEventOccurred(FinanceModel model) {
                editFinance(model);
                getFinances();
                mainFrame.setFinanceModelList(Database.FINANCES.getList());
            }

            public void deleteOrderEventOccurred(FinanceModel model) {
                deleteFinance(model);
                getFinances();
                mainFrame.setFinanceModelList(Database.FINANCES.getList());

            }

            public void departmentSelectionEventOccurred(long departmentId) {
                getEmployees(departmentId);
                mainFrame.setEmployeeModelList(Database.EMPLOYEES.getList());
            }

            public void orderSelectionEventOccurred(long orderId) {
                getDepartmentFinancesByOrder(orderId);
                mainFrame.setFinanceDepartmentModelList(Database.DEPARTMENT_FINANCES.getList());
            }

            public void createDepOrderEventOccurred(FinanceDepartmentModel model) {
                createDepartmentFinances(model);
                getDepartmentFinancesByOrder(model.getModelId());
                mainFrame.setFinanceDepartmentModelList(Database.DEPARTMENT_FINANCES.getList());
            }

            public void editDepOrderEventOccurred(FinanceDepartmentModel model) {
                editDepartmentFinances(model);
                getDepartmentFinancesByOrder(model.getModelId());
                mainFrame.setFinanceDepartmentModelList(Database.DEPARTMENT_FINANCES.getList());
            }

            public void deleteDepOrderEventOccurred(FinanceDepartmentModel model) {
                deleteDepartmentFinances(model);
                getDepartmentFinancesByOrder(model.getModelId());
                mainFrame.setFinanceDepartmentModelList(Database.DEPARTMENT_FINANCES.getList());
            }
        });

        mainFrame.setBidsListPanelListener(new BidsListPanelListener() {
            public void departmentSelectionEventOccurred(long departmentId) {
                getDepartmentFinancesByDepartment(departmentId);
                getBids(departmentId);
                mainFrame.setFinanceDepartmentModelList(Database.DEPARTMENT_FINANCES.getList());
                mainFrame.setBidModelList(Database.BIDS.getList());
                mainFrame.setBidsPanelSum(getBidsSum(departmentId), BigDecimal.ZERO);
            }

            public void financeDepartmentSelectionEventOccurred(long departmentId, long orderId) {
                getBids(departmentId, orderId);
                mainFrame.setBidModelList(Database.BIDS.getList());
                mainFrame.setBidsPanelSum(getBidsSum(departmentId, orderId), getFinancesLeft(departmentId, orderId));
            }

            public void bidDeleteEventOccurred(BidModel model) {
                setInactive(model);
                deleteBid(model);
                getBids();
                getFinances();
                mainFrame.setFinanceModelList(Database.FINANCES.getList());
                mainFrame.setBidModelList(Database.BIDS.getList());
                mainFrame.setBidsPanelSum(getBidsSum(), BigDecimal.ZERO);
            }

            @Override
            public void bidDeleteEventOccurred(BidModel model, long departmentId) {
                setInactive(model);
                deleteBid(model);
                getBids(departmentId);
                getFinances();
                mainFrame.setFinanceModelList(Database.FINANCES.getList());
                mainFrame.setBidModelList(Database.BIDS.getList());
                mainFrame.setBidsPanelSum(getBidsSum(departmentId), BigDecimal.ZERO);
            }

            @Override
            public void bidDeleteEventOccurred(BidModel model, long departmentId, long orderId) {
                setInactive(model);
                deleteBid(model);
                getBids(departmentId, orderId);
                getFinances();
                mainFrame.setFinanceModelList(Database.FINANCES.getList());
                mainFrame.setBidModelList(Database.BIDS.getList());
                mainFrame.setBidsPanelSum(getBidsSum(departmentId, orderId), getFinancesLeft(departmentId, orderId));
            }

            public void selectAllDepartmentsBidsEventOccurred() {
                getBids();
                mainFrame.setBidModelList(Database.BIDS.getList());
                mainFrame.setBidsPanelSum(getBidsSum(), BigDecimal.ZERO);
            }

            public void selectAllOrdersBidsEventOccurred(long departmentId) {
                getBids(departmentId);
                mainFrame.setBidModelList(Database.BIDS.getList());
                mainFrame.setBidsPanelSum(getBidsSum(departmentId), BigDecimal.ZERO);
            }

            public void showBidStatusesEventOccured(long modelId) {
                getBidStatuses(modelId);
                mainFrame.setBidStatusList(Database.STATUSES.getList());
            }

            public void statusChangeEventOccured(StatusModel model) {
                createBidStatus(model);
                getBidStatuses(model.getBidId());
                getBids();
                mainFrame.setBidStatusList(Database.STATUSES.getList());
                mainFrame.setBidModelList(Database.BIDS.getList());
            }


            public void statusChangeEventOccured(StatusModel model, long departmentId) {
                createBidStatus(model);
                getBidStatuses(model.getBidId());
                getBids(departmentId);
                mainFrame.setBidStatusList(Database.STATUSES.getList());
                mainFrame.setBidModelList(Database.BIDS.getList());
            }

            public void statusChangeEventOccured(StatusModel model, long departmentId, long orderId) {
                createBidStatus(model);
                getBidStatuses(model.getBidId());
                getBids(departmentId, orderId);
                mainFrame.setBidStatusList(Database.STATUSES.getList());
                mainFrame.setBidModelList(Database.BIDS.getList());
            }
        });

        mainFrame.setCreateBidDialogListener(new CreateBidDialogListener() {
            public void departmentSelectionEventOccurred(long depId) {
                getDepartmentFinancesByDepartment(depId);
                mainFrame.setFinanceDepartmentModelListToBidDialog(Database.DEPARTMENT_FINANCES.getList());
            }

            public void bidCreateEventOccurred(BidModel model) {
                setCreated(model);
                createBid(model);
                getBids();
                getFinances();
                mainFrame.setFinanceModelList(Database.FINANCES.getList());
                mainFrame.setBidModelList(Database.BIDS.getList());
                mainFrame.setBidsPanelSum(getBidsSum(), BigDecimal.ZERO);
            }

            @Override
            public void bidCreateEventOccurred(BidModel model, long departmentId) {
                setCreated(model);
                createBid(model);
                getBids(departmentId);
                getFinances();
                mainFrame.setFinanceModelList(Database.FINANCES.getList());
                mainFrame.setBidModelList(Database.BIDS.getList());
                mainFrame.setBidsPanelSum(getBidsSum(departmentId), BigDecimal.ZERO);
            }

            @Override
            public void bidCreateEventOccurred(BidModel model, long departmentId, long orderId) {
                setCreated(model);
                createBid(model);
                getBids(departmentId, orderId);
                getFinances();
                mainFrame.setFinanceModelList(Database.FINANCES.getList());
                mainFrame.setBidModelList(Database.BIDS.getList());
                mainFrame.setBidsPanelSum(getBidsSum(departmentId, orderId), getFinancesLeft(departmentId, orderId));
            }

            public void bidEditEventOccurred(BidModel model) {
                setModified(model);
                editBid(model);
                getBids();
                getFinances();
                mainFrame.setFinanceModelList(Database.FINANCES.getList());
                mainFrame.setBidModelList(Database.BIDS.getList());
                mainFrame.setBidsPanelSum(getBidsSum(), BigDecimal.ZERO);
            }

            @Override
            public void bidEditEventOccurred(BidModel model, long departmentId) {
                setModified(model);
                editBid(model);
                getBids(departmentId);
                getFinances();
                mainFrame.setFinanceModelList(Database.FINANCES.getList());
                mainFrame.setBidModelList(Database.BIDS.getList());
                mainFrame.setBidsPanelSum(getBidsSum(departmentId), BigDecimal.ZERO);
            }

            @Override
            public void bidEditEventOccurred(BidModel model, long departmentId, long orderId) {
                setModified(model);
                editBid(model);
                getBids(departmentId, orderId);
                getFinances();
                mainFrame.setFinanceModelList(Database.FINANCES.getList());
                mainFrame.setBidModelList(Database.BIDS.getList());
                mainFrame.setBidsPanelSum(getBidsSum(departmentId, orderId), getFinancesLeft(departmentId, orderId));
            }
        });

        mainFrame.setReportParametersDialogListener(new ReportParametersDialogListener() {
            public void roleSelectionOccurred(int roleId) {
                getEmployees(roleId);
                mainFrame.setEmployeeModelList(Database.EMPLOYEES.getList());
            }

            public void reportParametersSelectionOccurred(ReportParametersEvent ev) {
                ReportParametersData.getInstance().setData(ev.getHeadPosition(), ev.getHead(), ev.getDepartmentHead(),
                        ev.getPersonallyLiableEmpl(), ev.getAccountant(), ev.getEconomist(), ev.getHeadTender());
                mainFrame.bidListPrint();
            }
        });
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
        LoginData.getInstance(new EmployeeModel(0, Role.ADMIN.getRoleId()));
        mainFrame.initialize();
        initListeners();
        loadDataToView();
        mainFrame.getCreateEmployeeDialog().setRoleBox(false, Role.ADMIN.getRoleId());
        mainFrame.getCreateEmployeeDialog().setVisible(true);
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

    // general methods for modifications in DB entries
    private <T extends AbstractModel> void setCreated(T model) {
        model.setCreatedBy(LoginData.getInstance().getModelId());
        model.setCreatedDate(Utils.getCurrentTime());
    }

    private <T extends AbstractModel> void setModified(T model) {
        model.setModifiedBy(LoginData.getInstance().getModelId());
        model.setModifiedDate(Utils.getCurrentTime());
    }

    private <T extends AbstractModel> void setInactive(T model) {
        setModified(model);
        model.setActive(false);
    }

    // methods requesting the DB
    // GETTERS
    private void getCpvRequest(String cpvRequest, boolean sameLvlShow) {
        try {
            Database.CPV.retrieve(cpvRequest, sameLvlShow);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("cpvRequest") + cpvRequest + Labels.withSpaceBefore("error"));
        }
    }

    private Version getDBVersion() {
        try {
            return new Version(Database.VERSIONS.retrieve());
        } catch (SQLException e) {
            // this error occurs with old settings, so reset it to defaults
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

    private void getRolesRequest() {
        try {
            Database.ROLES.retrieve();
        } catch (SQLException e) {
            errorLogEvent(e,
                    Labels.withColon("request") + Labels.withSpaceBefore("role") + Labels.withSpaceBefore("error"));
        }
    }

    private void getEmployees() {
        try {
            Database.EMPLOYEES.retrieve();
        } catch (SQLException e) {
            errorLogEvent(e,
                    Labels.withColon("request") + Labels.withSpaceBefore("user") + Labels.withSpaceBefore("error"));
        }
    }

    private void getEmployees(int roleId) {
        try {
            Database.EMPLOYEES.retrieve(roleId);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("request") + Labels.withSpaceBefore("user") + " role id: " + roleId
                    + Labels.withSpaceBefore("error"));
        }
    }

    private void getEmployees(long depId) {
        try {
            Database.EMPLOYEES.retrieve(depId);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("request") + Labels.withSpaceBefore("user") + " dep id: " + depId
                    + Labels.withSpaceBefore("error"));
        }
    }

    private void getEmployees(int roleId, long depId) {
        try {
            Database.EMPLOYEES.retrieve(roleId, depId);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("request") + Labels.withSpaceBefore("user") + " role id: " + roleId
                    + " dep id: " + depId + Labels.withSpaceBefore("error"));
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

    private void getInstRequest() {
        try {
            Database.INSTITUTES.retrieve();
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("request") + Labels.withSpaceBefore("institute")
                    + Labels.withSpaceBefore("error"));
        }
    }

    private void getDepartments(long instId) {
        try {
            Database.DEPARTMENTS.retrieve(instId);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("request") + Labels.withSpaceBefore("department") + " inst id: " + instId
                    + Labels.withSpaceBefore("error"));
        }
    }

    private void getSubdepRequest(long depId) {
        try {
            Database.SUBDEPARTMENS.retrieve(depId);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("request") + Labels.withSpaceBefore("subdepartment") + " dep id: " + depId
                    + Labels.withSpaceBefore("error"));
        }
    }

    private void getAmUnits() {
        try {
            Database.AMOUNTUNITS.retrieve();
        } catch (SQLException e) {
            errorLogEvent(e,
                    Labels.withColon("request") + Labels.withSpaceBefore("amount") + Labels.withSpaceBefore("error"));
        }
    }

    private void getProd() {
        try {
            Database.PRODUCERS.retrieve();
        } catch (SQLException e) {
            errorLogEvent(e,
                    Labels.withColon("request") + Labels.withSpaceBefore("producer") + Labels.withSpaceBefore("error"));
        }
    }

    private void getSupl() {
        try {
            Database.SUPPLIERS.retrieve();
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("request") + Labels.withSpaceBefore("suplBorder")
                    + Labels.withSpaceBefore("error"));
        }
    }

    private void getReasons() {
        try {
            Database.REASONS.retrieve();
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("request") + Labels.withSpaceBefore("reasonForSupplierChoice")
                    + Labels.withSpaceBefore("error"));
        }
    }

    private void getFinances() {
        try {
            Database.FINANCES.retrieve();
        } catch (SQLException e) {
            errorLogEvent(e,
                    Labels.withColon("request") + Labels.withSpaceBefore("finances") + Labels.withSpaceBefore("error"));
        }
    }

    private void getFinances(long departmentId) {
        try {
            Database.FINANCES.retrieve(departmentId);
        } catch (SQLException e) {
            errorLogEvent(e,
                    Labels.withColon("request") + Labels.withSpaceBefore("finances") + Labels.withSpaceBefore("error"));
        }
    }

    private void getDepartmentFinancesByOrder(long orderId) {
        try {
            Database.DEPARTMENT_FINANCES.retrieveByOrderID(orderId);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("request") + Labels.withSpaceBefore("departmentFinances") + " order Id: "
                    + orderId + Labels.withSpaceBefore("error"));
        }
    }

    private void getDepartmentFinancesByDepartment(long departmentId) {
        try {
            Database.DEPARTMENT_FINANCES.retrieveByDepartmentID(departmentId);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("request") + Labels.withSpaceBefore("departmentFinances")
                    + " department Id: " + departmentId + Labels.withSpaceBefore("error"));
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

    private void getBids() {
        try {
            Database.BIDS.retrieve();
        } catch (SQLException e) {
            errorLogEvent(e,
                    Labels.withColon("request") + Labels.withSpaceBefore("bids") + Labels.withSpaceBefore("error"));
        }
    }

    private void getBids(long departmentId) {
        try {
            Database.BIDS.retrieve(departmentId);
        } catch (SQLException e) {
            errorLogEvent(e,
                    Labels.withColon("request") + Labels.withSpaceBefore("bids") + Labels.withSpaceBefore("error"));
        }
    }

    private void getBids(long departmentId, long orderId) {
        try {
            Database.BIDS.retrieve(departmentId, orderId);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("request") + Labels.withSpaceBefore("bids") + " order ID: " + orderId
                    + " department ID: " + departmentId + Labels.withSpaceBefore("error"));
        }
    }

    private BigDecimal getBidsSum() {
        try {
            return Database.BIDS.getSum();
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("request") + Labels.withSpaceBefore("totalPrice2")
                    + Labels.withSpaceBefore("error"));
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal getBidsSum(long departmentId) {
        try {
            return Database.BIDS.getSum(departmentId);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("request") + Labels.withSpaceBefore("totalPrice2")
                    + Labels.withSpaceBefore("error"));
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal getBidsSum(long departmentId, long orderId) {
        try {
            return Database.BIDS.getSum(departmentId, orderId);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("request") + Labels.withSpaceBefore("totalPrice2")
                    + Labels.withSpaceBefore("error"));
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal getFinancesLeft(long departmentId, long orderId) {
        try {
            return FinanceQueries.financeLeft(orderId, departmentId);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("financeLeftByTema") + Labels.withSpaceBefore("error"));
        }
        return BigDecimal.ZERO;
    }

    // CRUD Employees
    private void createEmployee(EmployeeModel model) {
        setCreated(model);
        try {
            Database.EMPLOYEES.create(model);
            logEvent(Labels.withColon("createNewEmployee") + model.toString() + Labels.withSpaceBefore("success"),
                    Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e,
                    Labels.withColon("createNewEmployee") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void editEmployee(EmployeeModel model) {
        setModified(model);
        try {
            Database.EMPLOYEES.update(model);
            logEvent(Labels.withColon("editEmployee") + model.toString() + Labels.withSpaceBefore("success"),
                    Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("editEmployee") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void deleteEmployee(EmployeeModel model) {
        setInactive(model);
        try {
            Database.EMPLOYEES.delete(model);
            logEvent(Labels.withColon("deleteEmployee") + model.toString() + Labels.withSpaceBefore("success"),
                    Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("deleteEmployee") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    // CRUD Institutes
    private void createInstitute(InstituteModel instModel) {
        setCreated(instModel);
        try {
            Database.INSTITUTES.create(instModel);
            logEvent(Labels.withColon("addInstitute") + instModel.toString() + Labels.withSpaceBefore("success"),
                    Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("addInstitute") + instModel.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void editInstitute(InstituteModel instModel) {
        setModified(instModel);
        try {
            Database.INSTITUTES.update(instModel);
            logEvent(Labels.withColon("editInstitite") + instModel.toString() + Labels.withSpaceBefore("success"),
                    Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e,
                    Labels.withColon("editInstitite") + instModel.toString() + Labels.withSpaceBefore("error"));
        }

    }

    private void deleteInstitute(InstituteModel instModel) {
        setInactive(instModel);
        try {
            Database.INSTITUTES.delete(instModel);
            logEvent(Labels.withColon("delInstitite") + instModel.toString() + Labels.withSpaceBefore("success"),
                    Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("delInstitite") + instModel.toString() + Labels.withSpaceBefore("error"));
        }
    }

    // CRUD Departments
    private void createDepartment(DepartmentModel model) {
        setCreated(model);
        try {
            Database.DEPARTMENTS.create(model);
            logEvent(Labels.withColon("addDepartment") + model.toString() + Labels.withSpaceBefore("success"),
                    Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("addDepartment") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void editDepartment(DepartmentModel model) {
        setModified(model);
        try {
            Database.DEPARTMENTS.update(model);
            logEvent(Labels.withColon("editDepartment") + model.toString() + Labels.withSpaceBefore("success"),
                    Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("editDepartment") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void deleteDepartment(DepartmentModel model) {
        setInactive(model);
        try {
            Database.DEPARTMENTS.delete(model);
            logEvent(Labels.withColon("delDepartment") + model.toString() + Labels.withSpaceBefore("success"),
                    Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("delDepartment") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    // CRUD Subdepartments
    private void createSubdepartment(SubdepartmentModel model) {
        setCreated(model);
        try {
            Database.SUBDEPARTMENS.create(model);
            logEvent(Labels.withColon("addSubdepartment") + model.toString() + Labels.withSpaceBefore("success"),
                    Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("addSubdepartment") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void editSubdepartment(SubdepartmentModel model) {
        setModified(model);
        try {
            Database.SUBDEPARTMENS.update(model);
            logEvent(Labels.withColon("editSubdepartment") + model.toString() + Labels.withSpaceBefore("success"),
                    Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e,
                    Labels.withColon("editSubdepartment") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void deleteSubdepartment(SubdepartmentModel model) {
        setInactive(model);
        try {
            Database.SUBDEPARTMENS.delete(model);
            logEvent(Labels.withColon("delSubdepartment") + model.toString() + Labels.withSpaceBefore("success"),
                    Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("delSubdepartment") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    // CRUD Amount and Units
    private void createAmUnit(AmountUnitsModel model) {
        setCreated(model);
        try {
            Database.AMOUNTUNITS.create(model);
            logEvent(Labels.withColon("addAmUnit") + model.toString() + Labels.withSpaceBefore("success"), Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("addAmUnit") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void editAmUnit(AmountUnitsModel model) {
        setModified(model);
        try {
            Database.AMOUNTUNITS.update(model);
            logEvent(Labels.withColon("editAmUnit") + model.toString() + Labels.withSpaceBefore("success"),
                    Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("editAmUnit") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void deleteAmUnit(AmountUnitsModel model) {
        setInactive(model);
        try {
            Database.AMOUNTUNITS.delete(model);
            logEvent(Labels.withColon("delAmUnit") + model.toString() + Labels.withSpaceBefore("success"), Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("delAmUnit") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    // CRUD Producers
    private void createProd(ProducerModel model) {
        setCreated(model);
        try {
            Database.PRODUCERS.create(model);
            logEvent(Labels.withColon("addProd") + model.toString() + Labels.withSpaceBefore("success"), Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("addProd") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void editProd(ProducerModel model) {
        setModified(model);
        try {
            Database.PRODUCERS.update(model);
            logEvent(Labels.withColon("editProd") + model.toString() + Labels.withSpaceBefore("success"), Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("editProd") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void deleteProd(ProducerModel model) {
        setInactive(model);
        try {
            Database.PRODUCERS.delete(model);
            logEvent(Labels.withColon("delProd") + model.toString() + Labels.withSpaceBefore("success"), Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("delProd") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    // CRUD Suppliers
    private void createSupl(SupplierModel model) {
        setCreated(model);
        try {
            Database.SUPPLIERS.create(model);
            logEvent(Labels.withColon("addSupl") + model.toString() + Labels.withSpaceBefore("success"), Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("addSupl") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void editSupl(SupplierModel model) {
        setModified(model);
        try {
            Database.SUPPLIERS.update(model);
            logEvent(Labels.withColon("editSupl") + model.toString() + Labels.withSpaceBefore("success"), Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("editSupl") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void deleteSupl(SupplierModel model) {
        setInactive(model);
        try {
            Database.SUPPLIERS.delete(model);
            logEvent(Labels.withColon("delSupl") + model.toString() + Labels.withSpaceBefore("success"), Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("delSupl") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    // CRUD for Reasons for reasons why buyer choose selected supplier
    private void createReason(ReasonForSupplierChoiceModel model) {
        setCreated(model);
        try {
            Database.REASONS.create(model);
            logEvent(Labels.withColon("addReasonForSupplierChoice") + model.toString() + Labels.withSpaceBefore("success"), Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("addReasonForSupplierChoice") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void editReason(ReasonForSupplierChoiceModel model) {
        setModified(model);
        try {
            Database.REASONS.update(model);
            logEvent(Labels.withColon("editReasonForSupplierChoice") + model.toString() + Labels.withSpaceBefore("success"), Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("editReasonForSupplierChoice") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void deleteReason(ReasonForSupplierChoiceModel model) {
        setInactive(model);
        try {
            Database.REASONS.delete(model);
            logEvent(Labels.withColon("deleteReasonForSupplierChoice") + model.toString() + Labels.withSpaceBefore("success"), Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("deleteReasonForSupplierChoice") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    // CRUD Finances
    private void createFinance(FinanceModel model) {
        setCreated(model);
        try {
            Database.FINANCES.create(model);
            logEvent(Labels.withColon("createOrder") + model.toString() + Labels.withSpaceBefore("success"),
                    Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("createOrder") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void editFinance(FinanceModel model) {
        setModified(model);
        try {
            Database.FINANCES.update(model);
            logEvent(Labels.withColon("editOrder") + model.toString() + Labels.withSpaceBefore("success"), Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("editOrder") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void deleteFinance(FinanceModel model) {
        setInactive(model);
        try {
            Database.FINANCES.delete(model);
            logEvent(Labels.withColon("deleteOrder") + model.toString() + Labels.withSpaceBefore("success"),
                    Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("deleteOrder") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    // CRUD Department Finances
    private void createDepartmentFinances(FinanceDepartmentModel model) {
        setCreated(model);
        try {
            Database.DEPARTMENT_FINANCES.create(model);
            logEvent(Labels.withColon("addDepOrder") + model.toString() + Labels.withSpaceBefore("success"),
                    Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("addDepOrder") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void editDepartmentFinances(FinanceDepartmentModel model) {
        setModified(model);
        try {
            Database.DEPARTMENT_FINANCES.update(model);
            logEvent(Labels.withColon("editDepOrder") + model.toString() + Labels.withSpaceBefore("success"),
                    Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("editDepOrder") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void deleteDepartmentFinances(FinanceDepartmentModel model) {
        setInactive(model);
        try {
            Database.DEPARTMENT_FINANCES.delete(model);
            logEvent(Labels.withColon("deleteDepOrder") + model.toString() + Labels.withSpaceBefore("success"),
                    Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("deleteDepOrder") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    // CRUD Bids
    private void createBid(BidModel model) {
        setCreated(model);
        try {
            Database.BIDS.create(model);
            logEvent(Labels.withColon("createBid") + model.toString() + Labels.withSpaceBefore("success"), Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("createBid") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void editBid(BidModel model) {
        setModified(model);
        try {
            Database.BIDS.update(model);
            logEvent(Labels.withColon("editBid") + model.toString() + Labels.withSpaceBefore("success"), Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("editBid") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void deleteBid(BidModel model) {
        setInactive(model);
        try {
            Database.BIDS.delete(model);
            logEvent(Labels.withColon("deleteBid") + model.toString() + Labels.withSpaceBefore("success"), Colors.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("deleteBid") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void createBidStatus(StatusModel model) {
        setCreated(model);
        try {
            Database.STATUSES.create(model);
            logEvent(Labels.withColon("setStatus") + model.getStatusDesc() + " " + LoginData.getInstance().getShortName() + Labels.withSpaceBefore("success"), Colors.GREEN);
        } catch (SQLException e) {
            e.printStackTrace();
            errorLogEvent(e, Labels.withColon("setStatus") + model.getStatusDesc() + " " + LoginData.getInstance().getShortName() + Labels.withSpaceBefore("error"));
        }
    }

    private void getBidStatuses(long bidId) {
        try {
            Database.STATUSES.retrieve(bidId);
        } catch (SQLException e) {
            e.printStackTrace();
            errorLogEvent(e, Labels.withColon("status") + bidId + Labels.withSpaceBefore("error"));
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
