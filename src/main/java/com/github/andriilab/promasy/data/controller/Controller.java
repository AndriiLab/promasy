/*
  This class connects MainFrame and Database
 */
package com.github.andriilab.promasy.data.controller;

import com.github.andriilab.promasy.data.storage.ConnectionSettings;
import com.github.andriilab.promasy.data.storage.Database;
import com.github.andriilab.promasy.domain.EmptyModel;
import com.github.andriilab.promasy.domain.IEntity;
import com.github.andriilab.promasy.domain.bid.entities.*;
import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.cpv.entities.Cpv;
import com.github.andriilab.promasy.domain.finance.entities.Finance;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;
import com.github.andriilab.promasy.domain.item.entities.Producer;
import com.github.andriilab.promasy.domain.item.entities.Supplier;
import com.github.andriilab.promasy.domain.organization.entities.Department;
import com.github.andriilab.promasy.domain.organization.entities.Employee;
import com.github.andriilab.promasy.domain.organization.entities.Institute;
import com.github.andriilab.promasy.domain.organization.entities.Subdepartment;
import com.github.andriilab.promasy.domain.organization.enums.Role;
import com.github.andriilab.promasy.domain.versioning.entities.Version;
import com.github.andriilab.promasy.presentation.MainFrame;
import com.github.andriilab.promasy.presentation.MainFrameListener;
import com.github.andriilab.promasy.presentation.Utils;
import com.github.andriilab.promasy.presentation.amunits.AmUnitsDialogListener;
import com.github.andriilab.promasy.presentation.bids.BidsListPanelListener;
import com.github.andriilab.promasy.presentation.commons.Icons;
import com.github.andriilab.promasy.presentation.commons.Labels;
import com.github.andriilab.promasy.presentation.components.PJOptionPane;
import com.github.andriilab.promasy.presentation.conset.ConSetListener;
import com.github.andriilab.promasy.presentation.cpv.CpvRequestContainer;
import com.github.andriilab.promasy.presentation.employee.CreateEmployeeDialogListener;
import com.github.andriilab.promasy.presentation.employee.CreateEmployeeFromLoginListener;
import com.github.andriilab.promasy.presentation.employee.EditEmployeeDialogListener;
import com.github.andriilab.promasy.presentation.finance.FinancePanelListener;
import com.github.andriilab.promasy.presentation.login.LoginListener;
import com.github.andriilab.promasy.presentation.organization.OrganizationDialogListener;
import com.github.andriilab.promasy.presentation.producer.ProducerDialogListener;
import com.github.andriilab.promasy.presentation.producer.ReasonsDialogListener;
import com.github.andriilab.promasy.presentation.reports.bids.ReportParametersDialogListener;
import com.github.andriilab.promasy.presentation.reports.cpv.CpvAmountDialogListener;
import com.github.andriilab.promasy.presentation.supplier.SupplierDialogListener;
import org.hibernate.JDBCException;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class Controller {

    private final MainFrame mainFrame;
    private List<String> parameters;

    public Controller(String[] args, MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        parameters = parseArgs(args);
        ReportsGenerator.precompileReports(mainFrame);
        initPrimaryListeners();

        // trying to get connection settings form serialized object,
        // if it doesn't exist defaults will be used
        Database.CONNECTOR.loadConnectionSettings(parameters.contains("tableUpdater"));
        mainFrame.setDefaultConnectionSettings(Database.CONNECTOR.getConnectionSettings());

        //show ConSettDialog if it was defined in command line arguments
        if (parameters.contains("connectionSettings")) {
            mainFrame.showConSettDialog();
        }
        if (parameters.contains("connectionStatistics")) {
            Database.CONNECTOR.showConnectionStats(mainFrame);
        }

        connect();
        checkVersion();
        checkFirstRun();

        mainFrame.showLoginPane();
    }

    // parser for command-line arguments
    private static List<String> parseArgs(String[] args) {
        List<String> parameters = new ArrayList<>();
        for (String arg : args) {
            if (arg.contains("-c") || arg.contains("--config")) {
                parameters.add("connectionSettings");
            }
            if (arg.contains("-g") || arg.contains("--generate")) {
                parameters.add("tableUpdater");
            }
            if (arg.contains("-s") || arg.contains("--statistics")) {
                parameters.add("connectionStatistics");
            }
            if (arg.contains("-l") || arg.contains("--log")) {
                parameters.add("logSave");
            }
        }
        return parameters;
    }

    private void initPrimaryListeners() {
        // if user entered new settings for connection to DB - putting them to Prefs
        mainFrame.setConSetListener(new ConSetListener() {
            @Override
            public void preferencesSetEventOccurred(ConnectionSettings model) {
                try {
                    Utils.saveConnectionSettings(model);
                } catch (IOException e) {
                    Logger.errorEvent(mainFrame, e);
                }

                // trying to connect with new settings
                Database.CONNECTOR.loadConnectionSettings(parameters.contains("tableUpdater"));
                mainFrame.setDefaultConnectionSettings(Database.CONNECTOR.getConnectionSettings());
                connect();
            }

            @Override
            public void forceCloseEventOccurred() {
                close();
            }
        });

        // init LoginListener here, because loginDialog appears before the MainFrame
        mainFrame.setLoginListener(new LoginListener() {
            public void loginAttemptOccurred(String user, char[] password) {
                String pass = Utils.makePass(password, getSalt(user));
                if (pass == null) {
                    PJOptionPane.criticalError(mainFrame);
                    close();
                }
                if (validateLogin(user, pass)) {
                    // if login was successful init MainFrame and make it visible
                    initMainFrame();
                    mainFrame.setVisible(true);
                    mainFrame.writeStatusPanelCurrentDb(Database.CONNECTOR.getConnectionSettings().getDatabase());
                    mainFrame.writeStatusPanelCurrentUser(LoginData.getInstance().getShortName());
                } else {
                    // if login wasn't successful showing error dialog
                    JOptionPane.showMessageDialog(mainFrame, Labels.getProperty("wrongCredentialsPlsCheck"),
                            Labels.getProperty("loginError"), JOptionPane.ERROR_MESSAGE, Icons.ERROR);
                }
            }

            // if user do not want login call close method
            public void loginCancelled() {
                close();
            }

            // creating new user
            public boolean isAbleToRegister() {
                int registrationNumber = registrationsLeft();
                Logger.infoEvent(mainFrame, "Ticket number: " + registrationNumber);
                if (registrationNumber > 0) {
                    LoginData.getInstance(Database.EMPLOYEES.getUserWithId(1L));
                    initMainFrame();
                    return true;
                } else return false;
            }
        });
    }

    private void checkVersion() {
        Object[] options = {Labels.getProperty("okBtn"), Labels.getProperty("downloadFromSite")};
        Version currentVersion = new Version(Labels.getVersion());
        Version dbVersion = getDBVersion();
        Logger.infoEvent(mainFrame, "Your version: " + currentVersion.get() + " DB version: " + dbVersion.get());
        if (currentVersion.compareTo(dbVersion) < 0) {
            int selectedOption = JOptionPane.showOptionDialog(mainFrame,
                    Labels.withDot("oldVersionOfApp") + "\n" +
                            Labels.withColon("yourVersion") + currentVersion.get() + "\n" +
                            Labels.withColon("minVersion") + dbVersion.get() + "\n" +
                            Labels.withColon("askAdminAboutUpdate") + "\n" +
                            Labels.getProperty("updateUrl"),
                    Labels.getProperty("youCantUseThisVersion"),
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.ERROR_MESSAGE,
                    Icons.ERROR,
                    options,
                    null);
            if (selectedOption == 1) {
                visitUpdatesPage();
            }
            close();
        }
    }

    private void initMainFrame() {
        mainFrame.initialize();
        // adding implementation for closing operation via X-button on window
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                closeDialog();
            }
        });

        // setting listener to MainFrame
        mainFrame.setMainFrameListener(new MainFrameListener() {
            @Override
            public List<Employee> searchForPerson(Role role, long depId) {
                return getEmployees(role, depId);
            }

            @Override
            public List<Employee> searchForPerson(Role role) {
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
            public void getAllDepartments(Institute inst) {
                mainFrame.setDepartmentModelList(getDepartments(inst.getModelId()));
            }

            @Override
            public Cpv validateCpv(String cpvCode) {
                return Database.CPV.validateCode(cpvCode);
            }

            @Override
            public void setNumberOfRegistrations(int regNumber) {
                Controller.this.setNumberOfRegistrations(regNumber);
            }

            @Override
            public void visitUpdatesSite() {
                visitUpdatesPage();
            }
        });

        mainFrame.setCpvListener(ev -> mainFrame.setCpvModelList(getCpvRequest(ev)));

        mainFrame.setEmployeeDialogListener(new EditEmployeeDialogListener() {
            @Override
            public void persistModelEventOccurred(Employee model) {
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
            public void persistModelEventOccurred(Employee model) {
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
            public void persistModelEventOccurred(Institute model) {
                createOrUpdate(model);
                mainFrame.setInstituteModelList(getInstRequest());
            }

            @Override
            public void persistModelEventOccurred(Department model) {
                createOrUpdate(model);
                mainFrame.setDepartmentModelList(getDepartments(model.getInstitute().getModelId()));
            }

            @Override
            public void persistModelEventOccurred(Subdepartment model) {
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
            public void persistModelEventOccurred(AmountUnit model) {
                createOrUpdate(model);
                mainFrame.setAmountUnitsModelList(getAmUnits());
            }

            @Override
            public void getAllEntries() {
                mainFrame.setAmountUnitsModelList(getAmUnits());
            }
        });

        mainFrame.setProducerDialogListener(new ProducerDialogListener() {
            @Override
            public void persistModelEventOccurred(Producer model) {
                createOrUpdate(model);
                mainFrame.setProducerModelList(getProd());
            }

            @Override
            public void getAllEntries() {
                mainFrame.setProducerModelList(getProd());
            }
        });

        mainFrame.setSupplierDialogListener(new SupplierDialogListener() {
            @Override
            public void persistModelEventOccurred(Supplier model) {
                createOrUpdate(model);
                mainFrame.setSupplierModelList(getSupl());
            }

            @Override
            public void getAllEntries() {
                mainFrame.setSupplierModelList(getSupl());
            }
        });

        mainFrame.setReasonsDialogListener(new ReasonsDialogListener() {
            @Override
            public void persistModelEventOccurred(ReasonForSupplierChoice model) {
                createOrUpdate(model);
                mainFrame.setReasonsModelList(getReasons());
            }

            @Override
            public void getAllEntries() {
                mainFrame.setReasonsModelList(getReasons());
            }
        });

        mainFrame.setFinancePanelListener(new FinancePanelListener() {
            @Override
            public void persistModelEventOccurred(Finance model) {
                createOrUpdate(model);
                mainFrame.setFinanceModelList(getFinances());
            }

            @Override
            public void persistModelEventOccurred(FinanceDepartment model) {
                createOrUpdate(model);
                mainFrame.setFinanceDepartmentModelList(getDepartmentFinancesByOrder(model.getModelId()));
            }

            @Override
            public void loadDepartments() {
                mainFrame.setDepartmentModelList(getDepartments(LoginData.getInstance()
                        .getSubdepartment().getDepartment().getInstitute().getModelId()));
            }

            @Override
            public void getFinancesByDepartment(Department department) {
                mainFrame.setFinanceModelList(getFinanceByDepartment(department));
            }

            @Override
            public void getAllData() {
                mainFrame.setFinanceModelList(getFinances());
            }
        });

        mainFrame.setBidsListPanelListener(new BidsListPanelListener() {
            @Override
            public void persistModelEventOccurred(Bid model) {
                createOrUpdate(model);
                mainFrame.setFinanceModelList(getFinances());
            }

            @Override
            public void persistModelEventOccurred(BidStatus model) {
                createOrUpdate(model);
                mainFrame.setFinanceModelList(getFinances());
            }

            @Override
            public void selectAllBidsEventOccurred(BidType type) {
                mainFrame.setBidModelList(getBids(type));
            }

            @Override
            public void getBidsByDepartment(BidType type, Department selectedDepartmentModel) {
                mainFrame.setBidModelList(getBids(type, selectedDepartmentModel));
            }

            @Override
            public void getBidsBySubdepartment(BidType type, Subdepartment selectedSubdepartmentModel) {
                mainFrame.setBidModelList(getBids(type, selectedSubdepartmentModel));
            }

            @Override
            public void getBidsByFinanceDepartment(BidType type, FinanceDepartment selectedFinanceDepartmentModel) {
                mainFrame.setBidModelList(getBids(type, selectedFinanceDepartmentModel));
            }

            @Override
            public void getAllData() {
                mainFrame.setDepartmentModelList(getDepartments(LoginData.getInstance().getSubdepartment()
                        .getDepartment().getInstitute().getModelId()));
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

            @Override
            public void reportParametersSelectionOccurred(Map<String, Object> parameters) {
                mainFrame.bidListPrint(parameters);
            }
        });

        mainFrame.setCpvAmountDialogListener(new CpvAmountDialogListener() {
            @Override
            public void getData() {
                mainFrame.setCpvAmountDialogList(getCpvAmount());
            }

            @Override
            public String getEmployeeName(Role role) {
                List<Employee> models = getEmployees(role);
                if (models == null || models.isEmpty()) {
                    return EmptyModel.STRING;
                } else {
                    return models.get(0).getShortName();
                }
            }
        });
    }

    private void visitUpdatesPage() {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(URI.create(Labels.getProperty("updateUrl")));
                return;
            } catch (IOException e) {
                Logger.warnEvent(e);
            }
        }
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                new StringSelection(Labels.getProperty("updateUrl")), null);
        JOptionPane optPane = new JOptionPane(Labels.getProperty("urlCopiedToClipboard"),
                JOptionPane.INFORMATION_MESSAGE,
                JOptionPane.DEFAULT_OPTION,
                Icons.INFO);
        JDialog dialog = optPane.createDialog(Labels.getProperty("dataCopiedToClipboard"));

        // autoclosable functionality in case user forgot to close notification window
        dialog.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                new Timer(5000, e1 -> {
                    if (dialog.isVisible()) dialog.setVisible(false);
                }).start();
            }
        });
        dialog.setVisible(true);
    }

    private void checkFirstRun() {
        if (isFirstRun()) {
            int option = JOptionPane.showConfirmDialog(mainFrame, Labels.getProperty("firstRunLong"),
                    Labels.getProperty("firstRun"), JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE, Icons.QUESTION);
            if (option == JOptionPane.YES_OPTION) {
                createAdmin();
            } else if (option == JOptionPane.NO_OPTION) {
                closeDialog();
            }
        }
    }

    private void closeSplashScreen() {
        if (mainFrame.getSplashScreen().isVisible()) {
            mainFrame.getSplashScreen().close();
        }
    }

    private void createAdmin() {
        mainFrame.getCreateEmployeeDialog().setLoginListener(new CreateEmployeeFromLoginListener() {
            @Override
            public void newUserCreatedEvent() {
                JOptionPane.showMessageDialog(mainFrame, Labels.getProperty("youCanLoginAfterRestart"),
                        Labels.getProperty("accountCreated"), JOptionPane.INFORMATION_MESSAGE, Icons.INFO);
                close();
            }

            @Override
            public void cancelEvent() {
                close();
            }
        });
        List<Employee> employees = getEmployees();
        Employee firstUser;
        if (employees == null || employees.isEmpty()) {
            firstUser = new Employee(Role.ADMIN);
        } else {
            firstUser = employees.get(0);
        }
        firstUser.setCreated();
        createOrUpdate(firstUser);
        LoginData.getInstance(firstUser);
        initMainFrame();
        mainFrame.getCreateEmployeeDialog().createCustomUser(LoginData.getInstance());
    }

    // connecting to DB
    private void connect() {
        Database.CONNECTOR.disconnect();
        try {
            Database.CONNECTOR.connect();
            Logger.infoEvent(mainFrame, Labels.getProperty("connectedToDB"));
            closeSplashScreen();
        } catch (SQLException e) {
            Logger.errorEvent(mainFrame, Labels.getProperty("noConnectionToDB"), e);
            JOptionPane.showMessageDialog(mainFrame, Labels.getProperty("noConnectionToDB"),
                    Labels.getProperty("databaseConnectionError"), JOptionPane.ERROR_MESSAGE, Icons.ERROR);
            closeSplashScreen();
            // if can't connect - call ConnectionSettingsDialog
            mainFrame.showConSettDialog();
        }
    }

    private Version getDBVersion() {
        try {
            return new Version(Database.VERSIONS.retrieve());
        } catch (JDBCException e) {
            // this error occurs with old settings, have to reset it to defaults
            try {
                Preferences.userRoot().node("db_con").clear();
                connect();
            } catch (BackingStoreException e1) {
                Logger.errorEvent(mainFrame, e);
            }
            Logger.errorEvent(mainFrame, Labels.withColon("versionRequest"), e);
        }
        return null;
    }

    // methods requesting the DB
    // GETTERS
    private List<Cpv> getCpvRequest(CpvRequestContainer cpvRequest) {
        try {
            return Database.CPV.retrieve(cpvRequest);
        } catch (JDBCException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("cpvRequest") + cpvRequest, e);
            return null;
        }
    }

    private boolean isFirstRun() {
        try {
            return Database.EMPLOYEES.isFirstRun();
        } catch (JDBCException e) {
            Logger.errorEvent(mainFrame, e);
            return false;
        }
    }

    private void setCurrentVersionAsMinimum() {
        try {
            Database.VERSIONS.updateVersion();
            Logger.infoEvent(mainFrame, Labels.withColon("minimumVersionWasSet") + Labels.getVersion());
        } catch (JDBCException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("error") +
                    Labels.withColon("minimumVersionWasSet") + Labels.getVersion(), e);
        }
    }

    private List<Employee> getEmployees() {
        try {
            return Database.EMPLOYEES.getResults();
        } catch (JDBCException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") +
                    Labels.withSpaceBefore("role.user"), e);
            return null;
        }
    }

    private List<Employee> getEmployees(Role role) {
        try {
            return Database.EMPLOYEES.retrieve(role);
        } catch (JDBCException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") +
                    Labels.withSpaceBefore("role.user") + " role: " + role, e);
            return null;
        }
    }

    private long getSalt(String login) {
        try {
            return Database.EMPLOYEES.getSalt(login);
        } catch (JDBCException e) {
            Logger.errorEvent(mainFrame, "Salt retrieval error with login: " + login, e);
            return 0;
        }
    }

    private List<Employee> getEmployees(Role role, long depId) {
        try {
            return Database.EMPLOYEES.retrieve(role, depId);
        } catch (JDBCException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") +
                    Labels.withSpaceBefore("role.user") + " role: " + role + " dep id: " + depId, e);
            return null;
        }
    }

    private List<Institute> getInstRequest() {
        try {
            return Database.INSTITUTES.getResults();
        } catch (JDBCException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") +
                    Labels.withSpaceBefore("institute"), e);
            return null;
        }
    }

    private List<Department> getDepartments(long instId) {
        try {
            return Database.DEPARTMENTS.retrieve(instId);
        } catch (JDBCException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") +
                    Labels.withSpaceBefore("department") + " inst id: " + instId, e);
            return null;
        }
    }

    private List<Subdepartment> getSubdepRequest(long depId) {
        try {
            return Database.SUBDEPARTMENS.retrieve(depId);
        } catch (JDBCException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") +
                    Labels.withSpaceBefore("subdepartment") + " dep id: " + depId, e);
            return null;
        }
    }

    private List<AmountUnit> getAmUnits() {
        try {
            return Database.AMOUNTUNITS.getResults();
        } catch (JDBCException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") +
                    Labels.withSpaceBefore("amount"), e);
            return null;
        }
    }

    private List<Producer> getProd() {
        try {
            return Database.PRODUCERS.getResults();
        } catch (JDBCException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") +
                    Labels.withSpaceBefore("producer"), e);
            return null;
        }
    }

    private List<Supplier> getSupl() {
        try {
            return Database.SUPPLIERS.getResults();
        } catch (JDBCException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") +
                    Labels.withSpaceBefore("suplBorder"), e);
            return null;
        }
    }

    private List<ReasonForSupplierChoice> getReasons() {
        try {
            return Database.REASONS.getResults();
        } catch (JDBCException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") +
                    Labels.withSpaceBefore("reasonForSupplierChoice"), e);
            return null;
        }
    }

    private List<Finance> getFinances() {
        try {
            return Database.FINANCES.getResults();
        } catch (JDBCException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") +
                    Labels.withSpaceBefore("finances"), e);
            return null;
        }
    }

    private List<Finance> getFinanceByDepartment(Department departmentModel) {
        try {
            return Database.FINANCES.retrieveByDepartmentId(departmentModel.getModelId());
        } catch (JDBCException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") +
                    Labels.withSpaceBefore("finances"), e);
            return null;
        }
    }

    private List<FinanceDepartment> getDepartmentFinancesByOrder(long orderId) {
        try {
            return Database.DEPARTMENT_FINANCES.retrieveByFinanceId(orderId);
        } catch (JDBCException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") +
                    Labels.withSpaceBefore("departmentFinances") + " order Id: " + orderId, e);
            return null;
        }
    }

    private boolean isLoginUnique(String username) {
        try {
            return Database.EMPLOYEES.checkLogin(username);
        } catch (JDBCException e) {
            Logger.errorEvent(mainFrame, e);
            return false;
        }
    }

    // check user login and pass
    private boolean validateLogin(String username, String password) {
        try {
            return Database.EMPLOYEES.checkLogin(username, password);
        } catch (JDBCException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") +
                    Labels.withSpaceBefore("role.user") + " :" + username, e);
            return false;
        }
    }

    private List<Bid> getBids(BidType type) {
        try {
            return Database.BIDS.getResults(type);
        } catch (JDBCException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") +
                    Labels.withSpaceBefore("bids"), e);
            return null;
        }
    }

    private List<Bid> getBids(BidType type, Department department) {
        try {
            return Database.BIDS.retrieve(type, department);
        } catch (JDBCException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") +
                    Labels.withSpaceBefore("bids"), e);
            return null;
        }
    }

    private List<Bid> getBids(BidType type, FinanceDepartment financeDepartmentModel) {
        try {
            return Database.BIDS.retrieve(type, financeDepartmentModel);
        } catch (JDBCException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") +
                    Labels.withSpaceBefore("bids"), e);
            return null;
        }
    }

    private List<Bid> getBids(BidType type, Subdepartment model) {
        try {
            return Database.BIDS.retrieve(type, model);
        } catch (JDBCException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") +
                    Labels.withSpaceBefore("bids"), e);
            return new LinkedList<>();
        }
    }

    private List<CpvAmount> getCpvAmount() {
        try {
            return Database.BIDS.getCpvAmount();
        } catch (JDBCException e) {
            Logger.errorEvent(mainFrame, Labels.withColon("request") +
                    Labels.withSpaceBefore("cpvAmounts"), e);
            return new LinkedList<>();
        }
    }

    private int registrationsLeft() {
        try {
            return Database.REGISTRATION.useRegistration();
        } catch (JDBCException e) {
            Logger.errorEvent(mainFrame, e);
            return 0;
        }
    }

    private <T extends IEntity> void createOrUpdate(T model) {
        try {
            model.createOrUpdate();
            Logger.infoEvent(mainFrame, Labels.withColon(model.getMessage()) + model.toString());
        } catch (JDBCException e) {
            Logger.errorEvent(mainFrame, Labels.withColon(model.getMessage()) + model.toString(), e);
        }
    }

    private void setNumberOfRegistrations(int registrationsNumber) {
        Database.REGISTRATION.changeNumberOfRegistrationTickets(registrationsNumber);
        String message = Labels.withColon("numberOfRegistrionsAvailable") +
                Database.REGISTRATION.getRegistrationsLeft();
        Logger.infoEvent(mainFrame, message);
        JOptionPane.showConfirmDialog(mainFrame,
                message,
                Labels.getProperty("notification"),
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                Icons.INFO);
    }

    // default close method
    private void close() {
        Database.CONNECTOR.disconnect();
        Logger.infoEvent(mainFrame, "Disconnected successfully");
        if (parameters.contains("logSave")) {
            mainFrame.saveLog();
        }
        mainFrame.dispose();
        System.exit(0);
    }

    // but it calls only in this close() method (except close in login dialog)
    private void closeDialog() {
        int action = JOptionPane.showConfirmDialog(this.mainFrame, Labels.getProperty("doYouWantExit"),
                Labels.getProperty("exitFromProgram"), JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, Icons.QUESTION);
        if (action == JOptionPane.YES_OPTION) {
            close();
        }
    }
}
