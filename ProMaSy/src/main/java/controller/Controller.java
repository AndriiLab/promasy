/**
 * This class connects MainFrame and Database
 */
package main.java.controller;

import main.java.gui.Labels;
import main.java.gui.MainFrame;
import main.java.gui.Utils;
import main.java.gui.amunits.AmUnitsDialogListener;
import main.java.gui.bids.BidsListPanelListener;
import main.java.gui.bids.CreateBidDialogListener;
import main.java.gui.bids.reports.ReportParametersDialogListener;
import main.java.gui.bids.reports.ReportParametersEvent;
import main.java.gui.empedit.CreateEmployeeDialogListener;
import main.java.gui.finance.FinancePanelListener;
import main.java.gui.instedit.OrganizationDialogListener;
import main.java.gui.login.LoginAttemptEvent;
import main.java.gui.login.LoginListener;
import main.java.gui.prodsupl.ProducerDialogListener;
import main.java.gui.prodsupl.SupplierDialogListener;
import main.java.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.prefs.Preferences;

public class Controller {

    private Properties conSet;
    private MainFrame mainFrame;
    private Preferences prefs;
    private List<InstituteModel> instModelList;

    public Controller(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        mainFrame.setVisible(false);

        // adding implementation for closing operation via X-button on window
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                closeDialog();
            }
        });

        // loginDialog appears first, before the MainFrame
        mainFrame.getLoginDialog().setLoginListener(new LoginListener() {
            public void usernameEntered(String username) {
                mainFrame.getLoginDialog().setSalt(getSalt(username));
            }

            public void loginAttemptOccurred(LoginAttemptEvent ev) {
                if (checkLogin(ev.getUsername(), ev.getPassword())) {
                    // if login was successful init MainFrame and make visible
                    mainFrame.initialize(LoginData.getInstance().getRoleId(), LoginData.getInstance().getDepId());
                    initListeners();
                    mainFrame.setVisible(true);
                    mainFrame.getLoginDialog().setVisible(false);
                    mainFrame.getStatusPanel().setCurrentUserLabel(LoginData.getInstance().getShortName());
                    // post login requests to DB
                    // setting to FinancePanel departments data relative to
                    // login person
                    getDepartments(LoginData.getInstance().getInstId());
                    List<DepartmentModel> departmentsList = Database.DEPARTMENTS.getList();
                    mainFrame.getFinancePanel().setDepartmentBoxData(departmentsList);
                    mainFrame.getBidsListPanel().setDepartmentBoxData(departmentsList);
                } else if (!checkLogin(ev.getUsername(), ev.getPassword())) {
                    // if login wasn't successful showing error dialog
                    JOptionPane.showMessageDialog(mainFrame, Labels.getProperty("wrongCredentialsPlsCheck"),
                            Labels.getProperty("loginError"), JOptionPane.ERROR_MESSAGE);
                }
            }

            // if user changed his mind about login call close method
            public void loginCancelled() {
                close();
            }
        });

        // Preferences class used for storage of connection settings to DB
        prefs = Preferences.userRoot().node("db_con");

        // trying to get connection settings form prefs object,
        // if it doesn't exist defaults will be used
        String server = prefs.get("server", "localhost");
        String database = prefs.get("database", "inst2016test");
        String schema = prefs.get("schema", "inst_db");
        String user = prefs.get("user", "postcoder");
        String password = prefs.get("password", "codetest");
        int portNumber = prefs.getInt("port", 5432);

        // if user entered new settings for connection to DB - putting them to Prefs
        mainFrame.getConSettDialog().setPrefsListener(e -> {
            prefs.put("server", e.getServer());
            prefs.put("host", e.getDatabase());
            prefs.put("schema", e.getSchema());
            prefs.putInt("port", e.getPortNumber());
            prefs.put("user", e.getUser());
            prefs.put("password", e.getPassword());
            setConnectionSettings(e.getServer(), e.getDatabase(), e.getSchema(), Integer.toString(e.getPortNumber()),
                    e.getUser(), e.getPassword());

            // trying to connect with new settings
            disconnect();
            connect();
        });
        // loading connection values to ConnectionSettingsDialog and Controller
        mainFrame.getConSettDialog().setDefaults(server, database, schema, portNumber, user, password);
        setConnectionSettings(server, database, schema, Integer.toString(portNumber), user, password);

        connect();
        checkVersion();
        mainFrame.getLoginDialog().setVisible(true);
    }

    private void initListeners(){
        // connecting with DB and loading default data into models
        getCpvRequest("", true);
        getRolesRequest();
        getInstRequest();
        getEmployees();
        getAmUnits();
        getProd();
        getSupl();
        getFinances();
        getBids();
        getDepartmentFinancesByOrder(0);
        // passing loaded data to view
        mainFrame.getCpvDialog().setData(Database.CPV.getList());
        mainFrame.getEditOrgDialog().setInstData(Database.INSTITUTES.getList());
        mainFrame.getEditEmpDialog().getCreateEmployeeDialog().setInstData(Database.INSTITUTES.getList());
        mainFrame.getEditEmpDialog().setEmpTableData(Database.EMPLOYEES.getList());
        mainFrame.getEditEmpDialog().getCreateEmployeeDialog().setRolesData(Database.ROLES.getList());
        mainFrame.getAmUnitsDialog().setData(Database.AMOUNTUNITS.getList());
        mainFrame.getProducerDialog().setProdData(Database.PRODUCERS.getList());
        mainFrame.getSupplierDialog().setSuplData(Database.SUPPLIERS.getList());
        mainFrame.getFinancePanel().setFinanceTableData(Database.FINANCES.getList());
        mainFrame.getFinancePanel().setDepartmentFinanceTableData(Database.DEPARTMENT_FINANCES.getList());
        mainFrame.getBidsListPanel().setBidsTableData(Database.BIDS.getList());
        mainFrame.getBidsListPanel().setProducerBoxData(Database.PRODUCERS.getList());
        mainFrame.getBidsListPanel().setSupplierBoxData(Database.SUPPLIERS.getList());
        mainFrame.getBidsListPanel().setAmUnitsBoxData(Database.AMOUNTUNITS.getList());
        mainFrame.getBidsListPanel().setSumLabel(getBidsSum());

        // setting listeners to frames and dialogs
        mainFrame.setMainFrameMenuListener(this::printBidList);

        mainFrame.getCpvDialog().setCpvListener(ev -> {
            getCpvRequest(ev.getCpvRequest(), ev.isSameLvlShow());
            mainFrame.getCpvDialog().refresh();
        });

        mainFrame.getToolbar().setToolbarListener(this::printBidList);

        mainFrame.getEditEmpDialog().setEmployeeDialogListener(model -> {
            deleteEmployee(model);
            getEmployees();
            mainFrame.getEditEmpDialog().setEmpTableData(Database.EMPLOYEES.getList());
            mainFrame.getEditEmpDialog().refresh();
        });

        mainFrame.getEditEmpDialog().getCreateEmployeeDialog()
                .setCreateEmployeeDialogListener(new CreateEmployeeDialogListener() {
                    public void instSelectionEventOccurred(long instId) {
                        getDepartments(instId);
                        mainFrame.getEditEmpDialog().getCreateEmployeeDialog()
                                .setDepData(Database.DEPARTMENTS.getList());
                    }

                    public void depSelectionEventOccurred(long depId) {
                        getSubdepRequest(depId);
                        mainFrame.getEditEmpDialog().getCreateEmployeeDialog()
                                .setSubdepData(Database.SUBDEPARTMENS.getList());
                    }

                    public void createEmployeeEventOccurred(EmployeeModel model) {
                        setCreated(model);
                        createEmployee(model);
                        getEmployees();
                        mainFrame.getEditEmpDialog().setEmpTableData(Database.EMPLOYEES.getList());
                        mainFrame.getEditEmpDialog().refresh();
                    }

                    public void editEmployeeEventOccurred(EmployeeModel model) {
                        setModified(model);
                        editEmployee(model);
                        getEmployees();
                        mainFrame.getEditEmpDialog().setEmpTableData(Database.EMPLOYEES.getList());
                        mainFrame.getEditEmpDialog().refresh();
                    }
                });

        mainFrame.getEditOrgDialog().setOrganizationDialogListener(new OrganizationDialogListener() {

            public void instSelectionEventOccurred(long instId) {
                getDepartments(instId);
                mainFrame.getEditOrgDialog().setDepData(Database.DEPARTMENTS.getList());
            }

            public void depSelectionEventOccurred(long depId) {
                getSubdepRequest(depId);
                mainFrame.getEditOrgDialog().setSubdepData(Database.SUBDEPARTMENS.getList());
            }

            public void createInstEventOccurred(InstituteModel instModel) {
                createInstitute(instModel);
                getInstRequest();
                instModelList = Database.INSTITUTES.getList();
                mainFrame.getEditOrgDialog().setInstData(instModelList);
                mainFrame.getEditEmpDialog().getCreateEmployeeDialog().setInstData(instModelList);
            }

            public void editInstEventOccurred(InstituteModel instModel) {
                editInstitute(instModel);
                getInstRequest();
                instModelList = Database.INSTITUTES.getList();
                mainFrame.getEditOrgDialog().setInstData(instModelList);
                mainFrame.getEditEmpDialog().getCreateEmployeeDialog().setInstData(instModelList);
            }

            public void deleteInstEventOccurred(InstituteModel instModel) {
                deleteInstitute(instModel);
                getInstRequest();
                instModelList = Database.INSTITUTES.getList();
                mainFrame.getEditOrgDialog().setInstData(instModelList);
                mainFrame.getEditEmpDialog().getCreateEmployeeDialog().setInstData(instModelList);
            }

            public void createDepEventOccurred(DepartmentModel model) {
                createDepartment(model);
                getDepartments(model.getInstId());
                mainFrame.getEditOrgDialog().setDepData(Database.DEPARTMENTS.getList());
            }

            public void editDepEventOccurred(DepartmentModel model) {
                editDepartment(model);
                getDepartments(model.getInstId());
                mainFrame.getEditOrgDialog().setDepData(Database.DEPARTMENTS.getList());
            }

            public void deleteDepEventOccurred(DepartmentModel model) {
                deleteDepartment(model);
                getDepartments(model.getInstId());
                mainFrame.getEditOrgDialog().setDepData(Database.DEPARTMENTS.getList());
            }

            public void createSubdepEventOccurred(SubdepartmentModel model) {
                createSubdepartment(model);
                getSubdepRequest(model.getDepId());
                mainFrame.getEditOrgDialog().setSubdepData(Database.SUBDEPARTMENS.getList());
            }

            public void editSubdepEventOccurred(SubdepartmentModel model) {
                editSubdepartment(model);
                getSubdepRequest(model.getDepId());
                mainFrame.getEditOrgDialog().setSubdepData(Database.SUBDEPARTMENS.getList());
            }

            public void deleteSubdepEventOccurred(SubdepartmentModel model) {
                deleteSubdepartment(model);
                getSubdepRequest(model.getDepId());
                mainFrame.getEditOrgDialog().setSubdepData(Database.SUBDEPARTMENS.getList());
            }
        });

        mainFrame.getAmUnitsDialog().setListener(new AmUnitsDialogListener() {
            public void createEventOccurred(AmountUnitsModel model) {
                createAmUnit(model);
                getAmUnits();
                mainFrame.getAmUnitsDialog().setData(Database.AMOUNTUNITS.getList());
                mainFrame.getBidsListPanel().getCreateBidDialog().setAmUnitsBoxData(Database.AMOUNTUNITS.getList());
            }

            public void editEventOccurred(AmountUnitsModel model) {
                editAmUnit(model);
                getAmUnits();
                mainFrame.getAmUnitsDialog().setData(Database.AMOUNTUNITS.getList());
            }

            public void deleteEventOccurred(AmountUnitsModel model) {
                deleteAmUnit(model);
                getAmUnits();
                mainFrame.getAmUnitsDialog().setData(Database.AMOUNTUNITS.getList());
            }
        });

        mainFrame.getProducerDialog().setListener(new ProducerDialogListener() {
            public void createProdEventOccurred(ProducerModel model) {
                createProd(model);
                getProd();
                mainFrame.getProducerDialog().setProdData(Database.PRODUCERS.getList());
                mainFrame.getBidsListPanel().getCreateBidDialog().setProducerBoxData(Database.PRODUCERS.getList());
            }

            public void editProdEventOccurred(ProducerModel model) {
                editProd(model);
                getProd();
                mainFrame.getProducerDialog().setProdData(Database.PRODUCERS.getList());
            }

            public void deleteProdEventOccurred(ProducerModel model) {
                deleteProd(model);
                getProd();
                mainFrame.getProducerDialog().setProdData(Database.PRODUCERS.getList());
            }
        });

        mainFrame.getSupplierDialog().setListener(new SupplierDialogListener() {
            public void createSuplEventOccurred(SupplierModel model) {
                createSupl(model);
                getSupl();
                mainFrame.getSupplierDialog().setSuplData(Database.SUPPLIERS.getList());
                mainFrame.getBidsListPanel().getCreateBidDialog().setSupplierBoxData(Database.SUPPLIERS.getList());
            }

            public void editSuplEventOccurred(SupplierModel model) {
                editSupl(model);
                getSupl();
                mainFrame.getSupplierDialog().setSuplData(Database.SUPPLIERS.getList());
            }

            public void deleteSuplEventOccurred(SupplierModel model) {
                deleteSupl(model);
                getSupl();
                mainFrame.getSupplierDialog().setSuplData(Database.SUPPLIERS.getList());
            }
        });
        mainFrame.getFinancePanel().setFinancePanelListener(new FinancePanelListener() {
            public void createOrderEventOccurred(FinanceModel model) {
                createFinance(model);
                getFinances();
                mainFrame.getFinancePanel().setFinanceTableData(Database.FINANCES.getList());
                mainFrame.getFinancePanel().refreshFinanceTable();
            }

            public void editOrderEventOccurred(FinanceModel model) {
                editFinance(model);
                getFinances();
                mainFrame.getFinancePanel().setFinanceTableData(Database.FINANCES.getList());
                mainFrame.getFinancePanel().refreshFinanceTable();
            }

            public void deleteOrderEventOccurred(FinanceModel model) {
                deleteFinance(model);
                getFinances();
                mainFrame.getFinancePanel().setFinanceTableData(Database.FINANCES.getList());
                mainFrame.getFinancePanel().refreshFinanceTable();

            }

            public void departmentSelectionEventOccurred(long departmentId) {
                getEmployees(departmentId);
                mainFrame.getFinancePanel().setEmployeeBoxData(Database.EMPLOYEES.getList());
            }

            public void orderSelectionEventOccurred(long orderId) {
                getDepartmentFinancesByOrder(orderId);
                mainFrame.getFinancePanel().setDepartmentFinanceTableData(Database.DEPARTMENT_FINANCES.getList());
                mainFrame.getFinancePanel().refreshDepartmentFinanceTable();
            }

            public void createDepOrderEventOccurred(FinanceDepartmentModel model) {
                createDepartmentFinances(model);
                getDepartmentFinancesByOrder(model.getModelId());
                mainFrame.getFinancePanel().setDepartmentFinanceTableData(Database.DEPARTMENT_FINANCES.getList());
                mainFrame.getFinancePanel().refreshDepartmentFinanceTable();

            }

            public void editDepOrderEventOccurred(FinanceDepartmentModel model) {
                editDepartmentFinances(model);
                getDepartmentFinancesByOrder(model.getModelId());
                mainFrame.getFinancePanel().setDepartmentFinanceTableData(Database.DEPARTMENT_FINANCES.getList());
                mainFrame.getFinancePanel().refreshDepartmentFinanceTable();
            }

            public void deleteDepOrderEventOccurred(FinanceDepartmentModel model) {
                deleteDepartmentFinances(model);
                getDepartmentFinancesByOrder(model.getModelId());
                mainFrame.getFinancePanel().setDepartmentFinanceTableData(Database.DEPARTMENT_FINANCES.getList());
                mainFrame.getFinancePanel().refreshDepartmentFinanceTable();
            }
        });

        mainFrame.getBidsListPanel().setBidsListPanelListener(new BidsListPanelListener() {
            public void departmentSelectionEventOccurred(long departmentId) {
                getDepartmentFinancesByDepartment(departmentId);
                getBids(departmentId);
                mainFrame.getBidsListPanel().setFinanceDepartmentBoxData(Database.DEPARTMENT_FINANCES.getList());
                mainFrame.getBidsListPanel().setBidsTableData(Database.BIDS.getList());
                mainFrame.getBidsListPanel().refreshBidsTableData();
                mainFrame.getBidsListPanel().setSumLabel(getBidsSum(departmentId));
            }

            public void financeDepartmentSelectionEventOccurred(long departmentId, long orderId) {
                getBids(departmentId, orderId);
                mainFrame.getBidsListPanel().setBidsTableData(Database.BIDS.getList());
                mainFrame.getBidsListPanel().refreshBidsTableData();
                mainFrame.getBidsListPanel().setSumLabel(getBidsSum(departmentId, orderId));
            }

            public void bidDeleteEventOccurred(BidModel model) {
                setInactive(model);
                deleteBid(model);
                getBids();
                getFinances();
                mainFrame.getFinancePanel().setFinanceTableData(Database.FINANCES.getList());
                mainFrame.getFinancePanel().refreshFinanceTable();
                mainFrame.getBidsListPanel().setBidsTableData(Database.BIDS.getList());
                mainFrame.getBidsListPanel().refreshBidsTableData();
                mainFrame.getBidsListPanel().setSumLabel(getBidsSum());
            }

            @Override
            public void bidDeleteEventOccurred(BidModel model, long departmentId) {
                setInactive(model);
                deleteBid(model);
                getBids(departmentId);
                getFinances();
                mainFrame.getFinancePanel().setFinanceTableData(Database.FINANCES.getList());
                mainFrame.getFinancePanel().refreshFinanceTable();
                mainFrame.getBidsListPanel().setBidsTableData(Database.BIDS.getList());
                mainFrame.getBidsListPanel().refreshBidsTableData();
                mainFrame.getBidsListPanel().setSumLabel(getBidsSum(departmentId));
            }

            @Override
            public void bidDeleteEventOccurred(BidModel model, long departmentId, long orderId) {
                setInactive(model);
                deleteBid(model);
                getBids(departmentId, orderId);
                getFinances();
                mainFrame.getFinancePanel().setFinanceTableData(Database.FINANCES.getList());
                mainFrame.getFinancePanel().refreshFinanceTable();
                mainFrame.getBidsListPanel().setBidsTableData(Database.BIDS.getList());
                mainFrame.getBidsListPanel().refreshBidsTableData();
                mainFrame.getBidsListPanel().setSumLabel(getBidsSum(departmentId, orderId));
            }

            public void selectAllDepartmentsBidsEventOccurred() {
                getBids();
                mainFrame.getBidsListPanel().setBidsTableData(Database.BIDS.getList());
                mainFrame.getBidsListPanel().refreshBidsTableData();
                mainFrame.getBidsListPanel().setSumLabel(getBidsSum());
            }

            public void selectAllOrdersBidsEventOccurred(long departmentId) {
                getBids(departmentId);
                mainFrame.getBidsListPanel().setBidsTableData(Database.BIDS.getList());
                mainFrame.getBidsListPanel().refreshBidsTableData();
                mainFrame.getBidsListPanel().setSumLabel(getBidsSum(departmentId));
            }
        });

        mainFrame.getBidsListPanel().getCreateBidDialog().setCreateBidDialogListener(new CreateBidDialogListener() {
            public void departmentSelectionEventOccurred(long depId) {
                getDepartmentFinancesByDepartment(depId);
                mainFrame.getBidsListPanel().getCreateBidDialog()
                        .setFinanceDepartmentBoxData(Database.DEPARTMENT_FINANCES.getList());
            }

            public void bidCreateEventOccurred(BidModel model) {
                setCreated(model);
                createBid(model);
                getBids();
                getFinances();
                mainFrame.getFinancePanel().setFinanceTableData(Database.FINANCES.getList());
                mainFrame.getFinancePanel().refreshFinanceTable();
                mainFrame.getBidsListPanel().setBidsTableData(Database.BIDS.getList());
                mainFrame.getBidsListPanel().refreshBidsTableData();
                mainFrame.getBidsListPanel().setSumLabel(getBidsSum());
            }

            @Override
            public void bidCreateEventOccurred(BidModel model, long departmentId) {
                setCreated(model);
                createBid(model);
                getBids(departmentId);
                getFinances();
                mainFrame.getFinancePanel().setFinanceTableData(Database.FINANCES.getList());
                mainFrame.getFinancePanel().refreshFinanceTable();
                mainFrame.getBidsListPanel().setBidsTableData(Database.BIDS.getList());
                mainFrame.getBidsListPanel().refreshBidsTableData();
                mainFrame.getBidsListPanel().setSumLabel(getBidsSum(departmentId));
            }

            @Override
            public void bidCreateEventOccurred(BidModel model, long departmentId, long orderId) {
                setCreated(model);
                createBid(model);
                getBids(departmentId, orderId);
                getFinances();
                mainFrame.getFinancePanel().setFinanceTableData(Database.FINANCES.getList());
                mainFrame.getFinancePanel().refreshFinanceTable();
                mainFrame.getBidsListPanel().setBidsTableData(Database.BIDS.getList());
                mainFrame.getBidsListPanel().refreshBidsTableData();
                mainFrame.getBidsListPanel().setSumLabel(getBidsSum(departmentId, orderId));
            }

            public void bidEditEventOccurred(BidModel model) {
                setModified(model);
                editBid(model);
                getBids();
                getFinances();
                mainFrame.getFinancePanel().setFinanceTableData(Database.FINANCES.getList());
                mainFrame.getFinancePanel().refreshFinanceTable();
                mainFrame.getBidsListPanel().setBidsTableData(Database.BIDS.getList());
                mainFrame.getBidsListPanel().refreshBidsTableData();
                mainFrame.getBidsListPanel().setSumLabel(getBidsSum());
            }

            @Override
            public void bidEditEventOccurred(BidModel model, long departmentId) {
                setModified(model);
                editBid(model);
                getBids(departmentId);
                getFinances();
                mainFrame.getFinancePanel().setFinanceTableData(Database.FINANCES.getList());
                mainFrame.getFinancePanel().refreshFinanceTable();
                mainFrame.getBidsListPanel().setBidsTableData(Database.BIDS.getList());
                mainFrame.getBidsListPanel().refreshBidsTableData();
                mainFrame.getBidsListPanel().setSumLabel(getBidsSum(departmentId));
            }

            @Override
            public void bidEditEventOccurred(BidModel model, long departmentId, long orderId) {
                setModified(model);
                editBid(model);
                getBids(departmentId, orderId);
                getFinances();
                mainFrame.getFinancePanel().setFinanceTableData(Database.FINANCES.getList());
                mainFrame.getFinancePanel().refreshFinanceTable();
                mainFrame.getBidsListPanel().setBidsTableData(Database.BIDS.getList());
                mainFrame.getBidsListPanel().refreshBidsTableData();
                mainFrame.getBidsListPanel().setSumLabel(getBidsSum(departmentId, orderId));
            }
        });

        mainFrame.getReportParametersDialog().setListener(new ReportParametersDialogListener() {
            public void roleSelectionOccurred(int roleId) {
                getEmployees(roleId);
                mainFrame.getReportParametersDialog().setHeadBoxData(Database.EMPLOYEES.getList());
            }

            public void ReportParametersSelectionOccurred(ReportParametersEvent ev) {
                mainFrame.getBidsListPanel().printBidList(ev.getHeadPosition(), ev.getHead(), ev.getDepartmentHead(),
                        ev.getPersonallyLiableEmpl(), ev.getAccountant(), ev.getEconomist());
            }
        });

        mainFrame.getExitItem().addActionListener(ev -> closeDialog());
    }

    private void checkVersion() {
        Version currentVersion = new Version(Labels.getProperty("versionNumber"));
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

    private void printBidList() {
        if (mainFrame.getBidsListPanel().isReadyForPrint()) {
            long selectedDepartmentId = mainFrame.getBidsListPanel().getSelectedDepartmentId();
            mainFrame.getReportParametersDialog().setRoleBoxData(Database.ROLES.getList());
            // search for heads of department (id 5000) in department
            getEmployees(5000, selectedDepartmentId);
            mainFrame.getReportParametersDialog().setDepartmentHeadBoxData(Database.EMPLOYEES.getList());
            // search for personally liable employee (id 6000) in department
            getEmployees(6000, selectedDepartmentId);
            mainFrame.getReportParametersDialog().setPersonallyLiableEmpBoxData(Database.EMPLOYEES.getList());
            // search for chief accountant (id 4000)
            getEmployees(4000);
            mainFrame.getReportParametersDialog().setAccountantBoxData(Database.EMPLOYEES.getList());
            // search for chief economist (id 3000)
            getEmployees(3000);
            mainFrame.getReportParametersDialog().setEconomistBoxData(Database.EMPLOYEES.getList());
            // show dialog with selectors for director, head of department, PLE, accountant, economist
            mainFrame.getReportParametersDialog().setVisible(true);
        }
    }

    private void logEvent(String message, Color color) {
        mainFrame.getStatusPanel().setStatus(message, color);
        mainFrame.getLoggerDialog().addToLog(message, color);
    }

    private void errorLogEvent(Exception exception, String message) {
        exception.printStackTrace();
        mainFrame.getStatusPanel().setStatus(message, Utils.RED);
        mainFrame.getLoggerDialog().addToLog(message, Utils.RED);
        mainFrame.getLoggerDialog().addToLog(exception.toString(), Utils.RED);
    }

    // sets connection settings to Properties object
    private void setConnectionSettings(String host, String database, String schema, String port, String user,
                                       String password) {
        if (conSet == null) {
            conSet = new Properties();
        }
        conSet.setProperty("host", host);
        conSet.setProperty("port", port);
        conSet.setProperty("database", database);
        conSet.setProperty("user", user);
        conSet.setProperty("password", password);
        conSet.setProperty("currentSchema", schema);
    }

    // connecting to DB
    private void connect() {
        try {
            Database.DB.connect(conSet);
            logEvent(Labels.getProperty("connectedToDB"), Utils.GREEN);
        } catch (Exception e) {
            e.printStackTrace();
            logEvent(Labels.getProperty("NoConnectionToDB"), Utils.RED);
            JOptionPane.showMessageDialog(mainFrame, Labels.getProperty("NoConnectionToDB"),
                    Labels.getProperty("DatabaseConnectionError"), JOptionPane.ERROR_MESSAGE);
            // if can't connect - call ConnectionSettingsDialog
            mainFrame.getConSettDialog().setVisible(true);
        }
    }

    // disconnecting from DB
    private void disconnect() {
        Database.DB.disconnect();
    }

    // general methods for modifications in DB entries
    private <T extends AbstractModel> void setCreated(T model) {
        model.setCreatedBy(LoginData.getInstance().getEmpId());
        model.setCreatedDate(Utils.getCurrentTime());
    }

    private <T extends AbstractModel> void setModified(T model) {
        model.setModifiedBy(LoginData.getInstance().getEmpId());
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
            logEvent(Labels.withColon("cpvRequest") + cpvRequest + Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("cpvRequest") + cpvRequest + Labels.withSpaceBefore("error"));
        }
    }

    private Version getDBVersion(){
        try {
            return new Version(Database.VERSION_QUERIES.retrive());
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("versionRequest") + Labels.withSpaceBefore("error"));
        }
        return null;
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

    private void getFinances() {
        try {
            Database.FINANCES.retrieve();
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

    // CRUD Employees
    private void createEmployee(EmployeeModel model) {
        setCreated(model);
        try {
            Database.EMPLOYEES.create(model);
            logEvent(Labels.withColon("createNewEmployee") + model.toString() + Labels.withSpaceBefore("success"),
                    Utils.GREEN);
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
                    Utils.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("editEmployee") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void deleteEmployee(EmployeeModel model) {
        setInactive(model);
        try {
            Database.EMPLOYEES.delete(model);
            logEvent(Labels.withColon("deleteEmployee") + model.toString() + Labels.withSpaceBefore("success"),
                    Utils.GREEN);
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
                    Utils.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("addInstitute") + instModel.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void editInstitute(InstituteModel instModel) {
        setModified(instModel);
        try {
            Database.INSTITUTES.update(instModel);
            logEvent(Labels.withColon("editInstitite") + instModel.toString() + Labels.withSpaceBefore("success"),
                    Utils.GREEN);
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
                    Utils.GREEN);
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
                    Utils.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("addDepartment") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void editDepartment(DepartmentModel model) {
        setModified(model);
        try {
            Database.DEPARTMENTS.update(model);
            logEvent(Labels.withColon("editDepartment") + model.toString() + Labels.withSpaceBefore("success"),
                    Utils.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("editDepartment") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void deleteDepartment(DepartmentModel model) {
        setInactive(model);
        try {
            Database.DEPARTMENTS.delete(model);
            logEvent(Labels.withColon("delDepartment") + model.toString() + Labels.withSpaceBefore("success"),
                    Utils.GREEN);
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
                    Utils.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("addSubdepartment") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void editSubdepartment(SubdepartmentModel model) {
        setModified(model);
        try {
            Database.SUBDEPARTMENS.update(model);
            logEvent(Labels.withColon("editSubdepartment") + model.toString() + Labels.withSpaceBefore("success"),
                    Utils.GREEN);
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
                    Utils.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("delSubdepartment") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    // CRUD Amount and Units
    private void createAmUnit(AmountUnitsModel model) {
        setCreated(model);
        try {
            Database.AMOUNTUNITS.create(model);
            logEvent(Labels.withColon("addAmUnit") + model.toString() + Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("addAmUnit") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void editAmUnit(AmountUnitsModel model) {
        setModified(model);
        try {
            Database.AMOUNTUNITS.update(model);
            logEvent(Labels.withColon("editAmUnit") + model.toString() + Labels.withSpaceBefore("success"),
                    Utils.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("editAmUnit") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void deleteAmUnit(AmountUnitsModel model) {
        setInactive(model);
        try {
            Database.AMOUNTUNITS.delete(model);
            logEvent(Labels.withColon("delAmUnit") + model.toString() + Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("delAmUnit") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    // CRUD Producers
    private void createProd(ProducerModel model) {
        setCreated(model);
        try {
            Database.PRODUCERS.create(model);
            logEvent(Labels.withColon("addProd") + model.toString() + Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("addProd") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void editProd(ProducerModel model) {
        setModified(model);
        try {
            Database.PRODUCERS.update(model);
            logEvent(Labels.withColon("editProd") + model.toString() + Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("editProd") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void deleteProd(ProducerModel model) {
        setInactive(model);
        try {
            Database.PRODUCERS.delete(model);
            logEvent(Labels.withColon("delProd") + model.toString() + Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("delProd") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    // CRUD Suppliers
    private void createSupl(SupplierModel model) {
        setCreated(model);
        try {
            Database.SUPPLIERS.create(model);
            logEvent(Labels.withColon("addSupl") + model.toString() + Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("addSupl") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void editSupl(SupplierModel model) {
        setModified(model);
        try {
            Database.SUPPLIERS.update(model);
            logEvent(Labels.withColon("editSupl") + model.toString() + Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("editSupl") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void deleteSupl(SupplierModel model) {
        setInactive(model);
        try {
            Database.SUPPLIERS.delete(model);
            logEvent(Labels.withColon("delSupl") + model.toString() + Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("delSupl") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    // CRUD Finances
    private void createFinance(FinanceModel model) {
        setCreated(model);
        try {
            Database.FINANCES.create(model);
            logEvent(Labels.withColon("createOrder") + model.toString() + Labels.withSpaceBefore("success"),
                    Utils.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("createOrder") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void editFinance(FinanceModel model) {
        setModified(model);
        try {
            Database.FINANCES.update(model);
            logEvent(Labels.withColon("editOrder") + model.toString() + Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("editOrder") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void deleteFinance(FinanceModel model) {
        setInactive(model);
        try {
            Database.FINANCES.delete(model);
            logEvent(Labels.withColon("deleteOrder") + model.toString() + Labels.withSpaceBefore("success"),
                    Utils.GREEN);
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
                    Utils.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("addDepOrder") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void editDepartmentFinances(FinanceDepartmentModel model) {
        setModified(model);
        try {
            Database.DEPARTMENT_FINANCES.update(model);
            logEvent(Labels.withColon("editDepOrder") + model.toString() + Labels.withSpaceBefore("success"),
                    Utils.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("editDepOrder") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void deleteDepartmentFinances(FinanceDepartmentModel model) {
        setInactive(model);
        try {
            Database.DEPARTMENT_FINANCES.delete(model);
            logEvent(Labels.withColon("deleteDepOrder") + model.toString() + Labels.withSpaceBefore("success"),
                    Utils.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("deleteDepOrder") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    // CRUD Bids
    private void createBid(BidModel model) {
        setCreated(model);
        try {
            Database.BIDS.create(model);
            logEvent(Labels.withColon("createBid") + model.toString() + Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("createBid") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void editBid(BidModel model) {
        setModified(model);
        try {
            Database.BIDS.update(model);
            logEvent(Labels.withColon("editBid") + model.toString() + Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("editBid") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    private void deleteBid(BidModel model) {
        setInactive(model);
        try {
            Database.BIDS.delete(model);
            logEvent(Labels.withColon("deleteBid") + model.toString() + Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            errorLogEvent(e, Labels.withColon("deleteBid") + model.toString() + Labels.withSpaceBefore("error"));
        }
    }

    // default close method
    private void close() {
        Database.DB.disconnect();
        mainFrame.dispose();
    }

    // but it calls only in this close() method (except close in login dialog)
    private void closeDialog() {
        int action = JOptionPane.showConfirmDialog(this.mainFrame, Labels.getProperty("WantExit"),
                Labels.getProperty("ConfirmExit"), JOptionPane.OK_CANCEL_OPTION);
        if (action == JOptionPane.OK_OPTION) {
            close();
        }
    }
}
