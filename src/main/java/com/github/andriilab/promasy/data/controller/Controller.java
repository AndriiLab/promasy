/*
  This class connects MainFrame and Database
 */
package com.github.andriilab.promasy.data.controller;

import com.github.andriilab.promasy.data.commands.CommandsHandler;
import com.github.andriilab.promasy.data.commands.CreateOrUpdateCommand;
import com.github.andriilab.promasy.data.commands.RefreshCommand;
import com.github.andriilab.promasy.data.helpers.PasswordUtils;
import com.github.andriilab.promasy.data.queries.bids.GetBidsQuery;
import com.github.andriilab.promasy.data.queries.cpv.CpvRequestQuery;
import com.github.andriilab.promasy.data.queries.employees.GetEmployeesQuery;
import com.github.andriilab.promasy.data.queries.finance.GetFinanceLeftAmountQuery;
import com.github.andriilab.promasy.data.queries.finance.GetFinanceUnassignedAmountQuery;
import com.github.andriilab.promasy.data.queries.finance.GetFinancesQuery;
import com.github.andriilab.promasy.data.queries.financepartment.GetFinanceDepartmentLeftAmountQuery;
import com.github.andriilab.promasy.data.queries.financepartment.GetFinanceDepartmentSpentAmountQuery;
import com.github.andriilab.promasy.data.queries.financepartment.GetFinanceDepartmentsQuery;
import com.github.andriilab.promasy.data.storage.ConnectionSettings;
import com.github.andriilab.promasy.data.storage.DbConnector;
import com.github.andriilab.promasy.data.storage.LocalStorage;
import com.github.andriilab.promasy.data.storage.Storage;
import com.github.andriilab.promasy.domain.EmptyModel;
import com.github.andriilab.promasy.domain.IEntity;
import com.github.andriilab.promasy.domain.bid.entities.AmountUnit;
import com.github.andriilab.promasy.domain.bid.entities.Bid;
import com.github.andriilab.promasy.domain.bid.entities.CpvAmount;
import com.github.andriilab.promasy.domain.bid.entities.ReasonForSupplierChoice;
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
import com.github.andriilab.promasy.presentation.amunits.AmUnitsDialogListener;
import com.github.andriilab.promasy.presentation.bids.BidsListPanelListener;
import com.github.andriilab.promasy.presentation.commons.Icons;
import com.github.andriilab.promasy.presentation.commons.Labels;
import com.github.andriilab.promasy.presentation.commons.SystemCommands;
import com.github.andriilab.promasy.presentation.components.panes.ErrorOptionPane;
import com.github.andriilab.promasy.presentation.conset.ConSetListener;
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
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;

public class Controller {

    private final MainFrame mainFrame;
    private Storage storage;
    private List<String> parameters;
    private CommandsHandler commandsHandler;

    public Controller(String[] args, MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        parameters = parseArgs(args);
        ReportsGenerator.precompileReports(mainFrame);
        initPrimaryListeners();

        // trying to get connection settings form serialized object,
        // if it doesn't exist defaults will be used
        DbConnector.INSTANCE.loadConnectionSettings(parameters.contains("tableUpdater"));
        mainFrame.setDefaultConnectionSettings(DbConnector.INSTANCE.getConnectionSettings());

        //show ConSettDialog if it was defined in command line arguments
        if (parameters.contains("connectionSettings")) {
            mainFrame.showConSettDialog();
        }
        if (parameters.contains("connectionStatistics")) {
            DbConnector.INSTANCE.showConnectionStats(mainFrame);
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
                    LocalStorage.saveConnectionSettings(model);
                } catch (IOException e) {
                    Logger.errorEvent(this.getClass(), mainFrame, e);
                }

                // trying to connect with new settings
                DbConnector.INSTANCE.loadConnectionSettings(parameters.contains("tableUpdater"));
                mainFrame.setDefaultConnectionSettings(DbConnector.INSTANCE.getConnectionSettings());
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
                String pass = PasswordUtils.makePass(password, retrieveUserSalt(user));
                if (pass.equals(EmptyModel.STRING)) {
                    ErrorOptionPane.criticalError(mainFrame);
                    close();
                }
                if (validateLogin(user, pass)) {
                    // if login was successful init MainFrame and make it visible
                    initMainFrame();
                    mainFrame.writeStatusPanelCurrentDb(DbConnector.INSTANCE.getConnectionSettings().getDatabase());
                    mainFrame.setVisible(true);
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
                Logger.infoEvent(this.getClass(), mainFrame, "Ticket number: " + registrationNumber);
                if (registrationNumber > 0) {
                    LoginData.getInstance(storage.EMPLOYEES.getUserWithId(1L));
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
        Logger.infoEvent(this.getClass(), mainFrame, "Your version: " + currentVersion.get() + " DB version: " + dbVersion.get());
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
            public List<Employee> searchForPerson(GetEmployeesQuery query) {
                return retrieveEmployees(query);
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
                mainFrame.setDepartmentModelList(retrieveDepartments(inst.getModelId()));
            }

            @Override
            public Cpv validateCpv(String cpvCode) {
                return storage.CPV.validateCode(cpvCode);
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

        mainFrame.setCpvListener(ev -> mainFrame.setCpvModelList(retrieveCpv(ev)));

        mainFrame.setEmployeeDialogListener(new EditEmployeeDialogListener() {
            @Override
            public <T extends IEntity> void persistModelEventOccurred(CreateOrUpdateCommand<T> command) {
                createOrUpdate(command);
                mainFrame.setEmployeeModelList(retrieveEmployees(new GetEmployeesQuery()));
            }

            @Override
            public void getAllEmployees() {
                mainFrame.setEmployeeModelList(retrieveEmployees(new GetEmployeesQuery()));
            }
        });

        mainFrame.setCreateEmployeeDialogListener(new CreateEmployeeDialogListener() {
            @Override
            public <T extends IEntity> void persistModelEventOccurred(CreateOrUpdateCommand<T> command) {
                createOrUpdate(command);
                mainFrame.setEmployeeModelList(retrieveEmployees(new GetEmployeesQuery()));
            }

            @Override
            public boolean checkUniqueLogin(String login) {
                return isLoginUnique(login);
            }

            @Override
            public void loadInstitutes() {
                mainFrame.setInstituteModelList(retrieveInstitutes());
            }
        });

        mainFrame.setOrganizationDialogListener(new OrganizationDialogListener() {
            @Override
            public <T extends IEntity> void persistModelEventOccurred(CreateOrUpdateCommand<T> command) {
                createOrUpdate(command);
                if (command.getObject() instanceof Institute)
                    mainFrame.setInstituteModelList(retrieveInstitutes());
                else if (command.getObject() instanceof Department)
                    mainFrame.setDepartmentModelList(
                            retrieveDepartments(((Department) command.getObject()).getInstitute().getModelId()));
                else if (command.getObject() instanceof Subdepartment)
                    mainFrame.setSubdepartmentModelList(
                            retrieveSubdepartments(((Subdepartment) command.getObject()).getDepartment().getModelId()));
            }

            @Override
            public void getAllInstitutes() {
                mainFrame.setInstituteModelList(retrieveInstitutes());
            }
        });

        mainFrame.setAmUnitsDialogListener(new AmUnitsDialogListener() {
            @Override
            public void persistModelEventOccurred(CreateOrUpdateCommand<AmountUnit> command) {
                createOrUpdate(command);
                mainFrame.setAmountUnitsModelList(retrieveAmountUnits());
            }

            @Override
            public void getAllEntries() {
                mainFrame.setAmountUnitsModelList(retrieveAmountUnits());
            }
        });

        mainFrame.setProducerDialogListener(new ProducerDialogListener() {
            @Override
            public void persistModelEventOccurred(CreateOrUpdateCommand<Producer> command) {
                createOrUpdate(command);
                mainFrame.setProducerModelList(retrieveProducers());
            }

            @Override
            public void getAllEntries() {
                mainFrame.setProducerModelList(retrieveProducers());
            }
        });

        mainFrame.setSupplierDialogListener(new SupplierDialogListener() {
            @Override
            public void persistModelEventOccurred(CreateOrUpdateCommand<Supplier> command) {
                createOrUpdate(command);
                mainFrame.setSupplierModelList(retrieveSuppliers());
            }

            @Override
            public void getAllEntries() {
                mainFrame.setSupplierModelList(retrieveSuppliers());
            }
        });

        mainFrame.setReasonsDialogListener(new ReasonsDialogListener() {
            @Override
            public void persistModelEventOccurred(CreateOrUpdateCommand<ReasonForSupplierChoice> command) {
                createOrUpdate(command);
                mainFrame.setReasonsModelList(retrieveReasons());
            }

            @Override
            public void getAllEntries() {
                mainFrame.setReasonsModelList(retrieveReasons());
            }
        });

        mainFrame.setFinancePanelListener(new FinancePanelListener() {
            @Override
            public <T extends IEntity> void persistModelEventOccurred(CreateOrUpdateCommand<T> command) {
                createOrUpdate(command);

                if (command.getObject() instanceof Finance)
                    mainFrame.setFinanceModelList(retrieveFinances(new GetFinancesQuery(mainFrame.getReportYear())));
                else if (command.getObject() instanceof FinanceDepartment)
                    mainFrame.setFinanceDepartmentModelList(retrieveDepartmentFinances(
                            new GetFinanceDepartmentsQuery(mainFrame.getReportYear(),
                                    ((FinanceDepartment) command.getObject()).getSubdepartment())));
            }

            @Override
            public void loadDepartments() {
                mainFrame.setDepartmentModelList(retrieveDepartments(LoginData.getInstance()
                        .getSubdepartment().getDepartment().getInstitute().getModelId()));
            }

            @Override
            public void getFinancesByDepartment(GetFinancesQuery query) {
                mainFrame.setFinanceModelList(retrieveFinances(query));
            }

            @Override
            public void getAllData() {
                mainFrame.setFinanceModelList(retrieveFinances(new GetFinancesQuery(mainFrame.getReportYear())));
            }

            @Override
            public BigDecimal getUnassignedAmountEvent(GetFinanceUnassignedAmountQuery query) {
                return retrieveUnassignedAmount(query);
            }

            @Override
            public BigDecimal getLeftAmountEvent(GetFinanceLeftAmountQuery query) {
                return retrieveLeftAmount(query);
            }

            @Override
            public BigDecimal getLeftAmountEvent(GetFinanceDepartmentLeftAmountQuery query) {
                return retrieveLeftAmount(query);
            }
        });

        mainFrame.setBidsListPanelListener(new BidsListPanelListener() {
            @Override
            public <T extends IEntity> void persistModelEventOccurred(CreateOrUpdateCommand<T> command) {
                createOrUpdate(command);
                mainFrame.setFinanceModelList(retrieveFinances(new GetFinancesQuery(mainFrame.getReportYear())));
            }

            @Override
            public <T extends IEntity> void refreshModelEventOccurred(RefreshCommand<T> command) {
                refresh(command);
            }

            @Override
            public void getBids(GetBidsQuery query) {
                mainFrame.setBidModelList(retrieveBids(query));
            }

            @Override
            public void getAllData() {
                mainFrame.setDepartmentModelList(retrieveDepartments(LoginData.getInstance().getSubdepartment()
                        .getDepartment().getInstitute().getModelId()));
                mainFrame.setAmountUnitsModelList(retrieveAmountUnits());
                mainFrame.setProducerModelList(retrieveProducers());
                mainFrame.setSupplierModelList(retrieveSuppliers());
                mainFrame.setReasonsModelList(retrieveReasons());
            }

            @Override
            public List<Subdepartment> getSubdepartments(long departmentId) {
                return retrieveSubdepartments(departmentId);
            }

            @Override
            public List<FinanceDepartment> getFinanceDepartments(GetFinanceDepartmentsQuery query) {
                return retrieveDepartmentFinances(query);
            }

            @Override
            public BigDecimal getLeftAmountEvent(GetFinanceDepartmentLeftAmountQuery query) {
                return retrieveLeftAmount(query);
            }

            @Override
            public BigDecimal getSpentAmountEvent(GetFinanceDepartmentSpentAmountQuery query) {
                return retrieveSpentAmount(query);
            }
        });

        mainFrame.setReportParametersDialogListener(new ReportParametersDialogListener() {
            @Override
            public void roleSelectionOccurred(Role role) {
                mainFrame.setEmployeeModelList(retrieveEmployees(new GetEmployeesQuery(role)));
            }

            @Override
            public List<Employee> getEmployees(GetEmployeesQuery query) {
                return retrieveEmployees(query);
            }

            @Override
            public void reportParametersSelectionOccurred(Map<String, Object> parameters) {
                mainFrame.bidListPrint(parameters);
            }
        });

        mainFrame.setCpvAmountDialogListener(new CpvAmountDialogListener() {
            @Override
            public void getData(int year) {
                mainFrame.setCpvAmountDialogList(retrieveCpvAmount(year));
            }

            @Override
            public String getEmployee(GetEmployeesQuery query) {
                List<Employee> models = retrieveEmployees(query);
                if (models.isEmpty()) {
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
                Logger.warnEvent(this.getClass(), e);
            }
        }
        SystemCommands.copyToClipboard(Labels.getProperty("updateUrl"));
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
        List<Employee> employees = retrieveEmployees(new GetEmployeesQuery());
        Employee firstUser;
        if (employees.isEmpty()) {
            firstUser = new Employee(Role.ADMIN);
        } else {
            firstUser = employees.get(0);
        }
        firstUser.setCreated();
        createOrUpdate(new CreateOrUpdateCommand<>(firstUser));
        LoginData.getInstance(firstUser);
        initMainFrame();
        mainFrame.getCreateEmployeeDialog().createCustomUser(LoginData.getInstance());
    }

    // connecting to DB
    private void connect() {
        DbConnector.INSTANCE.disconnect();
        try {
            DbConnector.INSTANCE.connect();
            Logger.infoEvent(this.getClass(), mainFrame, Labels.getProperty("connectedToDB"));
            closeSplashScreen();
        } catch (SQLException e) {
            Logger.errorEvent(this.getClass(), mainFrame, Labels.getProperty("noConnectionToDB"), e);
            JOptionPane.showMessageDialog(mainFrame, Labels.getProperty("noConnectionToDB"),
                    Labels.getProperty("databaseConnectionError"), JOptionPane.ERROR_MESSAGE, Icons.ERROR);
            closeSplashScreen();
            // if can't connect - call ConnectionSettingsDialog
            mainFrame.showConSettDialog();
        }
        storage = new Storage(DbConnector.INSTANCE.getEntityManager());
        commandsHandler = new CommandsHandler(storage);
    }

    // methods requesting the DB
    private Version getDBVersion() {
        try {
            return new Version(storage.VERSIONS.retrieve());
        } catch (JDBCException e) {
            // this error occurs with old settings, have to reset it to defaults
            try {
                Preferences.userRoot().node("db_con").clear();
                connect();
            } catch (BackingStoreException e1) {
                Logger.errorEvent(this.getClass(), mainFrame, e);
            }
            Logger.errorEvent(this.getClass(), mainFrame, Labels.withColon("versionRequest"), e);
        }
        return EmptyModel.VERSION;
    }

    private boolean isFirstRun() {
        try {
            return storage.EMPLOYEES.isFirstRun();
        } catch (JDBCException e) {
            Logger.errorEvent(this.getClass(), mainFrame, e);
            return false;
        }
    }

    private void setCurrentVersionAsMinimum() {
        try {
            storage.VERSIONS.updateVersion();
            Logger.infoEvent(this.getClass(), mainFrame, Labels.withColon("minimumVersionWasSet") + Labels.getVersion());
        } catch (JDBCException e) {
            Logger.errorEvent(this.getClass(), mainFrame, Labels.withColon("error") +
                    Labels.withColon("minimumVersionWasSet") + Labels.getVersion(), e);
        }
    }

    // check user login and pass
    private boolean validateLogin(String username, String password) {
        try {
            return storage.EMPLOYEES.checkLogin(username, password);
        } catch (JDBCException e) {
            Logger.errorEvent(this.getClass(), mainFrame, Labels.withColon("request") +
                    Labels.withSpaceBefore("role.user") + " :" + username, e);
            return false;
        }
    }

    private boolean isLoginUnique(String username) {
        try {
            return storage.EMPLOYEES.checkLogin(username);
        } catch (JDBCException e) {
            Logger.errorEvent(this.getClass(), mainFrame, e);
            return false;
        }
    }

    private long retrieveUserSalt(String login) {
        try {
            return storage.EMPLOYEES.getSalt(login);
        } catch (JDBCException e) {
            Logger.errorEvent(this.getClass(), mainFrame, "Salt retrieval error with login: " + login, e);
            return 0;
        }
    }

    private int registrationsLeft() {
        try {
            return storage.REGISTRATION.useRegistration();
        } catch (JDBCException e) {
            Logger.errorEvent(this.getClass(), mainFrame, e);
            return 0;
        }
    }

    private void setNumberOfRegistrations(int registrationsNumber) {
        storage.REGISTRATION.changeNumberOfRegistrationTickets(registrationsNumber);
        String message = Labels.withColon("numberOfRegistrionsAvailable") +
                storage.REGISTRATION.getRegistrationsLeft();
        Logger.infoEvent(this.getClass(), mainFrame, message);
        JOptionPane.showConfirmDialog(mainFrame,
                message,
                Labels.getProperty("notification"),
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                Icons.INFO);
    }

    // GETTERS
    private List<Cpv> retrieveCpv(CpvRequestQuery query) {
        try {
            return storage.CPV.get(query);
        } catch (JDBCException e) {
            Logger.errorEvent(this.getClass(), mainFrame, Labels.withColon("cpvRequest") + query, e);
            return Collections.emptyList();
        }
    }

    private List<Employee> retrieveEmployees(GetEmployeesQuery query) {
        try {
            return storage.EMPLOYEES.retrieve(query);
        } catch (JDBCException e) {
            Logger.errorEvent(this.getClass(), mainFrame, Labels.withColon("request") +
                    Labels.withSpaceBefore("role.user"), e);
            return Collections.emptyList();
        }
    }

    private List<Institute> retrieveInstitutes() {
        try {
            return storage.INSTITUTES.getResults();
        } catch (JDBCException e) {
            Logger.errorEvent(this.getClass(), mainFrame, Labels.withColon("request") +
                    Labels.withSpaceBefore("institute"), e);
            return Collections.emptyList();
        }
    }

    private List<Department> retrieveDepartments(long instId) {
        try {
            return storage.DEPARTMENTS.get(instId);
        } catch (JDBCException e) {
            Logger.errorEvent(this.getClass(), mainFrame, Labels.withColon("request") +
                    Labels.withSpaceBefore("department") + " inst id: " + instId, e);
            return Collections.emptyList();
        }
    }

    private List<Subdepartment> retrieveSubdepartments(long depId) {
        try {
            return storage.SUBDEPARTMENS.get(depId);
        } catch (JDBCException e) {
            Logger.errorEvent(this.getClass(), mainFrame, Labels.withColon("request") +
                    Labels.withSpaceBefore("subdepartment") + " dep id: " + depId, e);
            return Collections.emptyList();
        }
    }

    private List<AmountUnit> retrieveAmountUnits() {
        try {
            return storage.AMOUNTUNITS.getResults();
        } catch (JDBCException e) {
            Logger.errorEvent(this.getClass(), mainFrame, Labels.withColon("request") +
                    Labels.withSpaceBefore("amount"), e);
            return Collections.emptyList();
        }
    }

    private List<Producer> retrieveProducers() {
        try {
            return storage.PRODUCERS.getResults();
        } catch (JDBCException e) {
            Logger.errorEvent(this.getClass(), mainFrame, Labels.withColon("request") +
                    Labels.withSpaceBefore("producer"), e);
            return Collections.emptyList();
        }
    }

    private List<Supplier> retrieveSuppliers() {
        try {
            return storage.SUPPLIERS.getResults();
        } catch (JDBCException e) {
            Logger.errorEvent(this.getClass(), mainFrame, Labels.withColon("request") +
                    Labels.withSpaceBefore("suplBorder"), e);
            return Collections.emptyList();
        }
    }

    private List<ReasonForSupplierChoice> retrieveReasons() {
        try {
            return storage.REASONS.getResults();
        } catch (JDBCException e) {
            Logger.errorEvent(this.getClass(), mainFrame, Labels.withColon("request") +
                    Labels.withSpaceBefore("reasonForSupplierChoice"), e);
            return Collections.emptyList();
        }
    }

    private List<Finance> retrieveFinances(GetFinancesQuery query) {
        try {
            return storage.FINANCES.get(query);
        } catch (JDBCException e) {
            Logger.errorEvent(this.getClass(), mainFrame, Labels.withColon("request") +
                    Labels.withSpaceBefore("finances"), e);
            return Collections.emptyList();
        }
    }

    private BigDecimal retrieveUnassignedAmount(GetFinanceUnassignedAmountQuery query) {
        try {
            return storage.FINANCES.retrieveUnassignedAmount(query);
        } catch (JDBCException e) {
            Logger.errorEvent(this.getClass(), mainFrame, Labels.withColon("request") +
                    Labels.withSpaceBefore("finances"), e);
            return BigDecimal.ZERO;
        }
    }


    private BigDecimal retrieveLeftAmount(GetFinanceLeftAmountQuery query) {
        try {
            return storage.FINANCES.retrieveLeftAmount(query);
        } catch (JDBCException e) {
            Logger.errorEvent(this.getClass(), mainFrame, Labels.withColon("request") +
                    Labels.withSpaceBefore("finances"), e);
            return BigDecimal.ZERO;
        }
    }

    private List<FinanceDepartment> retrieveDepartmentFinances(GetFinanceDepartmentsQuery query) {
        try {
            return storage.DEPARTMENT_FINANCES.get(query);
        } catch (JDBCException e) {
            Logger.errorEvent(this.getClass(), mainFrame, Labels.withColon("request") +
                    Labels.withSpaceBefore("departmentFinances"), e);
            return Collections.emptyList();
        }
    }

    private BigDecimal retrieveLeftAmount(GetFinanceDepartmentLeftAmountQuery query) {
        try {
            return storage.DEPARTMENT_FINANCES.retrieveLeftAmount(query);
        } catch (JDBCException e) {
            Logger.errorEvent(this.getClass(), mainFrame, Labels.withColon("request") +
                    Labels.withSpaceBefore("finances"), e);
            return BigDecimal.ZERO;
        }
    }

    private BigDecimal retrieveSpentAmount(GetFinanceDepartmentSpentAmountQuery query) {
        try {
            return storage.DEPARTMENT_FINANCES.retrieveSpentAmount(query);
        } catch (JDBCException e) {
            Logger.errorEvent(this.getClass(), mainFrame, Labels.withColon("request") +
                    Labels.withSpaceBefore("finances"), e);
            return BigDecimal.ZERO;
        }
    }

    private List<Bid> retrieveBids(GetBidsQuery query) {
        try {
            return storage.BIDS.retrieve(query);
        } catch (JDBCException e) {
            Logger.errorEvent(this.getClass(), mainFrame, Labels.withColon("request") +
                    Labels.withSpaceBefore("bids"), e);
            return Collections.emptyList();
        }
    }

    private List<CpvAmount> retrieveCpvAmount(int year) {
        try {
            List<Bid> bidModels = storage.BIDS.retrieve(new GetBidsQuery(year));
            Map<String, CpvAmount> map = new HashMap<>();

            for (Bid bidModel : bidModels) {
                String cpv = bidModel.getCpv().getCpvId().substring(0, 4);
                BidType type = bidModel.getType();
                BigDecimal bidAmount = bidModel.getTotalPrice();

                String key = cpv + "0000 " + type.toString();
                if (map.containsKey(key)) {
                    CpvAmount cpvAmount = map.get(key);
                    cpvAmount.addBidModel(bidModel);
                    cpvAmount.addToTotalAmount(bidAmount);
                    map.put(key, cpvAmount);
                } else {
                    Cpv fourDigitCpv = storage.CPV.get(new CpvRequestQuery(key, 0)).get(0);
                    map.put(key, new CpvAmount(fourDigitCpv, type, bidAmount, bidModel));
                }
            }

            return Collections.unmodifiableList(map.values().stream()
                    .sorted(Comparator.comparing(CpvAmount::getType)
                            .thenComparing(m -> m.getCpv().getCpvId()))
                    .collect(Collectors.toList()));
        } catch (JDBCException e) {
            Logger.errorEvent(this.getClass(), mainFrame, Labels.withColon("request") +
                    Labels.withSpaceBefore("cpvAmounts"), e);
            return Collections.emptyList();
        }
    }

    // Create/update
    private <T extends IEntity> void createOrUpdate(CreateOrUpdateCommand<T> command) {
        try {
            commandsHandler.Handle(command);
            Logger.infoEvent(this.getClass(), mainFrame, Labels.withColon(command.getObject().getMessage()) + command.getObject().toString());
        } catch (JDBCException e) {
            Logger.errorEvent(this.getClass(), mainFrame, Labels.withColon(command.getObject().getMessage()) + command.getObject().toString(), e);
        }
    }

    private <T extends IEntity> void refresh(RefreshCommand<T> command) {
        try {
            commandsHandler.Handle(command);
            Logger.infoEvent(this.getClass(), mainFrame, Labels.withColon(command.getObject().getMessage()) + command.getObject().toString());
        } catch (JDBCException e) {
            Logger.errorEvent(this.getClass(), mainFrame, Labels.withColon(command.getObject().getMessage()) + command.getObject().toString(), e);
        }
    }

    // default close method
    private void close() {
        DbConnector.INSTANCE.disconnect();
        Logger.infoEvent(this.getClass(), mainFrame, "Disconnected successfully");
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
