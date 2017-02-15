/*
  This class connects MainFrame and Database
 */
package controller;

import gui.Labels;
import gui.MainFrame;
import gui.MainFrameListener;
import gui.Utils;
import gui.amunits.AmUnitsDialogListener;
import gui.bids.BidsListPanelListener;
import gui.bids.reports.ReportParametersDialogListener;
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
import model.dao.Database;
import model.dao.LoginData;
import model.enums.BidType;
import model.enums.Role;
import model.models.*;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
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
                Logger.errorEvent(mainFrame, e);
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
                    mainFrame.setVisible(true);
                    mainFrame.writeStatusPanelCurrentUser(LoginData.getInstance().getShortName());
                } else {
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
                    LoginData.getInstance(Database.EMPLOYEES.getUserWithId(1L));
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
            public List<EmployeeModel> searchForPerson(Role role, long selectedDepartmentId) {
                return getEmployees(role, selectedDepartmentId);
            }

            @Override
            public List<EmployeeModel> searchForPerson(Role role) {
                return getEmployees(role);
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
            public void getAllDepartmentsAndFinances(InstituteModel institute) {
                mainFrame.setDepartmentModelList(getDepartments(institute.getModelId()));
                mainFrame.setFinanceModelList(getFinances());
            }

            @Override
            public void getAllBids(BidType bidType) {
                mainFrame.setBidModelList(getBids(bidType));
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

            @Override
            public void getFinancesByDepartment(DepartmentModel department) {
                mainFrame.setFinanceModelList(getFinanceByDepartment(department));
            }
        });

        mainFrame.setBidsListPanelListener(new BidsListPanelListener() {
            @Override
            public void persistModelEventOccurred(BidModel model) {
                createOrUpdate(model);
                mainFrame.setFinanceModelList(getFinances());
            }

            @Override
            public void persistModelEventOccurred(BidStatusModel model) {
                createOrUpdate(model);
                mainFrame.setFinanceModelList(getFinances());
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

            public void reportParametersSelectionOccurred() {
                mainFrame.bidListPrint();
            }
        });
    }

    private void checkVersion() {
        Version currentVersion = new Version(Labels.getVersion());
        Version dbVersion = getDBVersion();
        Logger.infoEvent(null, "Your vesrion: " + currentVersion.get() + " DB version: " + dbVersion.get());
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
        mainFrame.getCreateEmployeeDialog().createCustomUser(LoginData.getInstance());
    }

    // sets connection settings to Properties object
    private void setConnectionSettings() {
        ConnectionSettingsModel model = null;
        try {
            model = Utils.loadConnectionSettings();
            Logger.infoEvent(null, "Connection Settings loaded from file");
        } catch (FileNotFoundException e) {
            Logger.infoEvent(null, "Connection Settings used from defaults");
            model = new ConnectionSettingsModel(Labels.getProperty("connectionSettings.server"), Labels.getProperty("connectionSettings.database"), Labels.getProperty("connectionSettings.schema"), Labels.getInt("connectionSettings.port"), Labels.getProperty("connectionSettings.user"), Labels.getProperty("connectionSettings.password"));
        } catch (IOException | ClassNotFoundException e) {
            Logger.errorEvent(mainFrame, e);
        }
        setConnectionSettings(model);
    }

    private void setConnectionSettings(ConnectionSettingsModel model) {
        this.conSet = model;
        mainFrame.setDefaultConnectionSettings(model);
    }

    private void closeSplashScreen() {
        if (mainFrame.getSplashScreen().isVisible()) {
            mainFrame.getSplashScreen().close();
        }
    }

    // connecting to DB
    private void connect() {
        try {
            Database.DB.connect(conSet);
            Logger.infoEvent(mainFrame, Labels.getProperty("connectedToDB"));
            closeSplashScreen();
        } catch (Exception e) {
            Logger.errorEvent(mainFrame, Labels.getProperty("noConnectionToDB"), e);
            JOptionPane.showMessageDialog(mainFrame, Labels.getProperty("noConnectionToDB"),
                    Labels.getProperty("databaseConnectionError"), JOptionPane.ERROR_MESSAGE);
            closeSplashScreen();
            // if can't connect - call ConnectionSettingsDialog
            mainFrame.showConSettDialog();
        }
    }

    // disconnecting from DB
    private void disconnect() {
        Database.DB.disconnect();
        Logger.infoEvent(mainFrame, "Disconnected successfully");
    }

    // methods requesting the DB
    // GETTERS
    private List<CPVModel> getCpvRequest(String cpvRequest) {
        try {
            return Database.CPV.retrieve(cpvRequest);
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("cpvRequest") + cpvRequest, e);
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
                Logger.errorEvent(mainFrame, e);
            }
            Logger.errorEvent(mainFrame, Labels.withColon("versionRequest"), e);
        }
        return null;
    }

    private void setCurrentVersionAsMinimum() {
        try {
            Database.VERSIONS.updateVersion();
            Logger.infoEvent(mainFrame, Labels.withColon("minimumVersionWasSet") + Labels.getVersion());
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("error") + Labels.withColon("minimumVersionWasSet") + Labels.getVersion(), e);
        }
    }

    private boolean isFirstRun() {
        try {
            return Database.EMPLOYEES.isFirstRun();
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, e);
            return false;
        }
    }

    private List<EmployeeModel> getEmployees() {
        try {
            return Database.EMPLOYEES.getResults();
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") + Labels.withSpaceBefore("role.user"), e);
            return null;
        }
    }

    private List<EmployeeModel> getEmployees(Role role) {
        try {
            return Database.EMPLOYEES.retrieve(role);
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") + Labels.withSpaceBefore("role.user") + " role: " + role, e);
            return null;
        }
    }

    private List<EmployeeModel> getEmployees(Role role, long depId) {
        try {
            return Database.EMPLOYEES.retrieve(role, depId);
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") + Labels.withSpaceBefore("role.user") + " role: " + role + " dep id: " + depId, e);
            return null;
        }
    }

    private long getSalt(String login) {
        try {
            return Database.EMPLOYEES.getSalt(login);
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, "Salt retrieval error with login: " + login, e);
            return 0;
        }
    }

    private List<InstituteModel> getInstRequest() {
        try {
            return Database.INSTITUTES.getResults();
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") + Labels.withSpaceBefore("institute"), e);
            return null;
        }
    }

    private List<DepartmentModel> getDepartments(long instId) {
        try {
            return Database.DEPARTMENTS.retrieve(instId);
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") + Labels.withSpaceBefore("department") + " inst id: " + instId, e);
            return null;
        }
    }

    private List<SubdepartmentModel> getSubdepRequest(long depId) {
        try {
            return Database.SUBDEPARTMENS.retrieve(depId);
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") + Labels.withSpaceBefore("subdepartment") + " dep id: " + depId, e);
            return null;
        }
    }

    private List<AmountUnitsModel> getAmUnits() {
        try {
            return Database.AMOUNTUNITS.getResults();
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") + Labels.withSpaceBefore("amount"), e);
            return null;
        }
    }

    private List<ProducerModel> getProd() {
        try {
            return Database.PRODUCERS.getResults();
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") + Labels.withSpaceBefore("producer"), e);
            return null;
        }
    }

    private List<SupplierModel> getSupl() {
        try {
            return Database.SUPPLIERS.getResults();
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") + Labels.withSpaceBefore("suplBorder"), e);
            return null;
        }
    }

    private List<ReasonForSupplierChoiceModel> getReasons() {
        try {
            return Database.REASONS.getResults();
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") + Labels.withSpaceBefore("reasonForSupplierChoice"), e);
            return null;
        }
    }

    private List<FinanceModel> getFinances() {
        try {
            return Database.FINANCES.getResults();
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") + Labels.withSpaceBefore("finances"), e);
            return null;
        }
    }

    private List<FinanceModel> getFinanceByDepartment(DepartmentModel departmentModel) {
        try {
            return Database.FINANCES.retrieveByDepartmentId(departmentModel.getModelId());
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") + Labels.withSpaceBefore("finances"), e);
            return null;
        }
    }

    private List<FinanceDepartmentModel> getDepartmentFinancesByOrder(long orderId) {
        try {
            return Database.DEPARTMENT_FINANCES.retrieveByFinanceId(orderId);
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") + Labels.withSpaceBefore("departmentFinances") + " order Id: " + orderId, e);
            return null;
        }
    }

    private List<FinanceDepartmentModel> getDepartmentFinancesByDepartment(DepartmentModel departmentModel) {
        try {
            return Database.DEPARTMENT_FINANCES.retrieveByDepartmentId(departmentModel.getModelId());
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") + Labels.withSpaceBefore("departmentFinances"), e);
            return null;
        }
    }

    // check user login and pass
    private boolean checkLogin(String username, String password) {
        try {
            return Database.EMPLOYEES.checkLogin(username, password);
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") + Labels.withSpaceBefore("role.user") + " :" + username, e);
            return false;
        }
    }

    private boolean isLoginUnique(String username) {
        try {
            return Database.EMPLOYEES.checkLogin(username);
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, e);
            return false;
        }
    }

    private List<BidModel> getBids(BidType type) {
        try {
            return Database.BIDS.getResults(type);
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") + Labels.withSpaceBefore("bids"), e);
            return null;
        }
    }

    private List<BidModel> getBids(BidType type, DepartmentModel department) {
        try {
            return Database.BIDS.retrieve(type, department);
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") + Labels.withSpaceBefore("bids"), e);
            return null;
        }
    }

    private List<BidModel> getBids(BidType type, FinanceDepartmentModel financeDepartmentModel) {
        try {
            return Database.BIDS.retrieve(type, financeDepartmentModel);
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") + Labels.withSpaceBefore("bids"), e);
            return null;
        }
    }

    private List<BidModel> getBids(BidType type, SubdepartmentModel model) {
        try {
            return Database.BIDS.retrieve(type, model);
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") + Labels.withSpaceBefore("bids"), e);
            return new LinkedList<>();
        }
    }

    private void createOrUpdate(EmployeeModel model) {
        try {
            Database.EMPLOYEES.createOrUpdate(model);
            Logger.infoEvent(mainFrame, Labels.withColon("createOrUpdateUser") + model.toString());
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("createOrUpdateUser") + model.toString(), e);
        }
    }

    private void createOrUpdate(InstituteModel instModel) {
        try {
            Database.INSTITUTES.createOrUpdate(instModel);
            Logger.infoEvent(mainFrame, Labels.withColon("addOrUpdateInstitute") + instModel.toString());
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("addOrUpdateInstitute") + instModel.toString(), e);
        }
    }

    private void createOrUpdate(DepartmentModel model) {
        try {
            Database.DEPARTMENTS.createOrUpdate(model);
            Logger.infoEvent(mainFrame, Labels.withColon("addOrUpdateDepartment") + model.toString());
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("addOrUpdateDepartment") + model.toString(), e);
        }
    }

    private void createOrUpdate(SubdepartmentModel model) {
        try {
            Database.SUBDEPARTMENS.createOrUpdate(model);
            Logger.infoEvent(mainFrame, Labels.withColon("addOrUpdateSubdepartment") + model.toString());
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("addOrUpdateSubdepartment") + model.toString(), e);
        }
    }

    private void createOrUpdate(AmountUnitsModel model) {
        try {
            Database.AMOUNTUNITS.createOrUpdate(model);
            Logger.infoEvent(mainFrame, Labels.withColon("addOrUpdateAmUnit") + model.toString());
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("addOrUpdateAmUnit") + model.toString(), e);
        }
    }

    private void createOrUpdate(ProducerModel model) {
        try {
            Database.PRODUCERS.createOrUpdate(model);
            Logger.infoEvent(mainFrame, Labels.withColon("addOrUpdateProd") + model.toString());
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("addOrUpdateProd") + model.toString(), e);
        }
    }

    private void createOrUpdate(SupplierModel model) {
        try {
            Database.SUPPLIERS.createOrUpdate(model);
            Logger.infoEvent(mainFrame, Labels.withColon("addOrUpdateSupl") + model.toString());
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("addOrUpdateSupl") + model.toString(), e);
        }
    }

    private void createOrUpdate(ReasonForSupplierChoiceModel model) {
        try {
            Database.REASONS.createOrUpdate(model);
            Logger.infoEvent(mainFrame, Labels.withColon("addOrUpdateReasonForSupplierChoice") + model.toString());
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("addOrUpdateReasonForSupplierChoice") + model.toString(), e);
        }
    }

    private void createOrUpdate(FinanceModel model) {
        try {
            Database.FINANCES.createOrUpdate(model);
            Logger.infoEvent(mainFrame, Labels.withColon("createOrUpdateOrder") + model.toString());
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("createOrUpdateOrder") + model.toString(), e);
        }
    }

    private void createOrUpdate(FinanceDepartmentModel model) {
        try {
            Database.DEPARTMENT_FINANCES.createOrUpdate(model);
            Logger.infoEvent(mainFrame, Labels.withColon("addOrUpdateDepOrder") + model.toString());
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("addOrUpdateDepOrder") + model.toString(), e);
        }
    }


    private void createOrUpdate(BidModel model) {
        try {
            Database.BIDS.createOrUpdate(model);
            Logger.infoEvent(mainFrame, Labels.withColon("createOrUpdateUserBid") + model.toString());
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("createOrUpdateUserBid") + model.toString(), e);
        }
    }

    private void createOrUpdate(BidStatusModel model) {
        try {
            Database.BID_STATUSES.createOrUpdate(model);
            Logger.infoEvent(mainFrame, Labels.withColon("statusWasSet") + model.getStatus().toString());
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("statusWasSet") + model.getStatus().toString(), e);
        }
    }

    private int registrationsLeft() {
        try {
            return Database.REGISTRATION.getRegistrationNumber();
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, e);
            return 0;
        }
    }

    // default close method
    private void close() {
        disconnect();
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
