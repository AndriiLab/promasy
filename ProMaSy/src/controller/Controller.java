/**
 * This class connects MainFrame and Database
 */
package controller;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.prefs.Preferences;

import javax.swing.JOptionPane;

import gui.Utils;
import gui.amunits.AmUnitsDialogListener;
import gui.Labels;
import gui.MainFrame;
import gui.bids.BidsListPanelListener;
import gui.bids.CreateBidDialogListener;
import gui.finance.FinancePanelListener;
import gui.empedit.CreateEmployeeDialogListener;
import gui.instedit.OrganizationDialogListener;
import gui.login.LoginAttemptEvent;
import gui.login.LoginListener;
import gui.prodsupl.ProducerDialogListener;
import gui.prodsupl.SupplierDialogListener;
import model.*;

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

        // loginDialog appears first, right before the MainFrame
        mainFrame.getLoginDialog().setVisible(true);
        mainFrame.getLoginDialog().setLoginListener(new LoginListener() {
            @Override
            public void usernameEntered(String username) {
                mainFrame.getLoginDialog().setSalt(getSalt(username));
            }

            public void loginAttemptOccurred(LoginAttemptEvent ev) {
                if (checkLogin(ev.getUsername(), ev.getPassword())) {
                    // if login was successful setting MainFrame visible
                    mainFrame.setVisible(true);
                    mainFrame.getLoginDialog().setVisible(false);
                    mainFrame.getStatusPanel().setCurrentUserLabel(LoginData.getInstance().getShortName());
                    //post login requests to DB
                    //setting to FinancePanel departments data relative to login person
                    getDepartments(LoginData.getInstance().getInstId());
                    List<DepartmentModel> departmentsList = Database.DEPARTMENTS.getList();
                    mainFrame.getFinancePanel().setDepartmentBoxData(departmentsList);
                    mainFrame.getBidsListPanel().setDepartmentBoxData(departmentsList);
                } else if (!checkLogin(ev.getUsername(), ev.getPassword())) {
                    // if login wasn't successful showing error dialog
                    JOptionPane.showMessageDialog(mainFrame,
                            Labels.getProperty("wrongCredentialsPlsCheck"), Labels.getProperty("loginError"),
                            JOptionPane.ERROR_MESSAGE);
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
            setConnectionSettings(e.getServer(), e.getDatabase(),
                    e.getSchema(), Integer.toString(e.getPortNumber()), e.getUser(), e.getPassword());

            // trying to connect with new settings
            disconnect();
            connect();
        });
        // loading connection values to ConnectionSettingsDialog and Controller
        mainFrame.getConSettDialog().setDefaults(server, database, schema,
                portNumber, user, password);
        setConnectionSettings(server, database, schema,
                Integer.toString(portNumber), user, password);

        //connecting with DB and loading default data to frames and dialogs
        connect();
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
        mainFrame.getCpvDialog().setData(Database.CPV.getList());
        instModelList = Database.INSTITUTES.getList();
        mainFrame.getEditOrgDialog().setInstData(instModelList);
        mainFrame.getEditEmpDialog().getCreateEmployeeDialog().setInstData(instModelList);
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

        //setting listeners to frames and dialogs
        mainFrame.getCpvDialog().setCpvListener(ev -> {
            getCpvRequest(ev.getCpvRequest(), ev.isSameLvlShow());
            mainFrame.getCpvDialog().refresh();
        });

        mainFrame.getToolbar().setToolbarListener(() -> {
            //TODO
            System.out.println("test for 'print' button");
        });

        mainFrame.getEditEmpDialog().setEmployeeDialogListener(model -> {
            deleteEmployee(model);
            getEmployees();
            mainFrame.getEditEmpDialog().setEmpTableData(Database.EMPLOYEES.getList());
            mainFrame.getEditEmpDialog().refresh();
        });

        mainFrame.getEditEmpDialog().getCreateEmployeeDialog().setCreateEmployeeDialogListener(new CreateEmployeeDialogListener() {
            public void instSelectionEventOccurred(long instId) {
                getDepartments(instId);
                mainFrame.getEditEmpDialog().getCreateEmployeeDialog().setDepData(Database.DEPARTMENTS.getList());
            }

            public void depSelectionEventOccurred(long depId) {
                getSubdepRequest(depId);
                mainFrame.getEditEmpDialog().getCreateEmployeeDialog().setSubdepData(Database.SUBDEPARTMENS.getList());
            }

            @Override
            public void createEmployeeEventOccurred(EmployeeModel model) {
                setCreated(model);
                createEmployee(model);
                getEmployees();
                mainFrame.getEditEmpDialog().setEmpTableData(Database.EMPLOYEES.getList());
                mainFrame.getEditEmpDialog().refresh();
            }

            @Override
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
            @Override
            public void createEventOccurred(AmountUnitsModel model) {
                createAmUnit(model);
                getAmUnits();
                mainFrame.getAmUnitsDialog().setData(Database.AMOUNTUNITS.getList());
                mainFrame.getBidsListPanel().getCreateBidDialog().setAmUnitsBoxData(Database.AMOUNTUNITS.getList());
            }

            @Override
            public void editEventOccurred(AmountUnitsModel model) {
                editAmUnit(model);
                getAmUnits();
                mainFrame.getAmUnitsDialog().setData(Database.AMOUNTUNITS.getList());
            }

            @Override
            public void deleteEventOccurred(AmountUnitsModel model) {
                deleteAmUnit(model);
                getAmUnits();
                mainFrame.getAmUnitsDialog().setData(Database.AMOUNTUNITS.getList());
            }
        });

        mainFrame.getProducerDialog().setListener(new ProducerDialogListener() {
            @Override
            public void createProdEventOccurred(ProducerModel model) {
                createProd(model);
                getProd();
                mainFrame.getProducerDialog().setProdData(Database.PRODUCERS.getList());
                mainFrame.getBidsListPanel().getCreateBidDialog().setProducerBoxData(Database.PRODUCERS.getList());
            }

            @Override
            public void editProdEventOccurred(ProducerModel model) {
                editProd(model);
                getProd();
                mainFrame.getProducerDialog().setProdData(Database.PRODUCERS.getList());
            }

            @Override
            public void deleteProdEventOccurred(ProducerModel model) {
                deleteProd(model);
                getProd();
                mainFrame.getProducerDialog().setProdData(Database.PRODUCERS.getList());
            }
        });

        mainFrame.getSupplierDialog().setListener(new SupplierDialogListener() {
            @Override
            public void createSuplEventOccurred(SupplierModel model) {
                createSupl(model);
                getSupl();
                mainFrame.getSupplierDialog().setSuplData(Database.SUPPLIERS.getList());
                mainFrame.getBidsListPanel().getCreateBidDialog().setSupplierBoxData(Database.SUPPLIERS.getList());
            }

            @Override
            public void editSuplEventOccurred(SupplierModel model) {
                editSupl(model);
                getSupl();
                mainFrame.getSupplierDialog().setSuplData(Database.SUPPLIERS.getList());
            }

            @Override
            public void deleteSuplEventOccurred(SupplierModel model) {
                deleteSupl(model);
                getSupl();
                mainFrame.getSupplierDialog().setSuplData(Database.SUPPLIERS.getList());
            }
        });
        mainFrame.getFinancePanel().setFinancePanelListener(new FinancePanelListener() {
            @Override
            public void createOrderEventOccurred(FinanceModel model) {
                createFinance(model);
                getFinances();
                mainFrame.getFinancePanel().setFinanceTableData(Database.FINANCES.getList());
                mainFrame.getFinancePanel().refreshFinanceTable();
            }

            @Override
            public void editOrderEventOccurred(FinanceModel model) {
                editFinance(model);
                getFinances();
                mainFrame.getFinancePanel().setFinanceTableData(Database.FINANCES.getList());
                mainFrame.getFinancePanel().refreshFinanceTable();
            }

            @Override
            public void deleteOrderEventOccurred(FinanceModel model) {
                deleteFinance(model);
                getFinances();
                mainFrame.getFinancePanel().setFinanceTableData(Database.FINANCES.getList());
                mainFrame.getFinancePanel().refreshFinanceTable();

            }

            @Override
            public void departmentSelectionEventOccurred(long departmentId) {
                getEmployees(departmentId);
                mainFrame.getFinancePanel().setEmployeeBoxData(Database.EMPLOYEES.getList());
            }

            @Override
            public void orderSelectionEventOccurred(long orderId) {
                getDepartmentFinancesByOrder(orderId);
                mainFrame.getFinancePanel().setDepartmentFinanceTableData(Database.DEPARTMENT_FINANCES.getList());
                mainFrame.getFinancePanel().refreshDepartmentFinanceTable();
            }

            @Override
            public void createDepOrderEventOccurred(FinanceDepartmentModel model) {
                createDepartmentFinances(model);
                getDepartmentFinancesByOrder(model.getModelId());
                mainFrame.getFinancePanel().setDepartmentFinanceTableData(Database.DEPARTMENT_FINANCES.getList());
                mainFrame.getFinancePanel().refreshDepartmentFinanceTable();

            }

            @Override
            public void editDepOrderEventOccurred(FinanceDepartmentModel model) {
                editDepartmentFinances(model);
                getDepartmentFinancesByOrder(model.getModelId());
                mainFrame.getFinancePanel().setDepartmentFinanceTableData(Database.DEPARTMENT_FINANCES.getList());
                mainFrame.getFinancePanel().refreshDepartmentFinanceTable();
            }

            @Override
            public void deleteDepOrderEventOccurred(FinanceDepartmentModel model) {
                deleteDepartmentFinances(model);
                getDepartmentFinancesByOrder(model.getModelId());
                mainFrame.getFinancePanel().setDepartmentFinanceTableData(Database.DEPARTMENT_FINANCES.getList());
                mainFrame.getFinancePanel().refreshDepartmentFinanceTable();
            }
        });

        mainFrame.getBidsListPanel().setBidsListPanelListener(new BidsListPanelListener() {
            @Override
            public void departmentSelectionEventOccurred(long departmentId) {
                getDepartmentFinancesByDepartment(departmentId);
                mainFrame.getBidsListPanel().setFinanceDepartmentBoxData(Database.DEPARTMENT_FINANCES.getList());
            }

            @Override
            public void financeDepartmentSelectionEventOccurred(long departmentId, long orderId) {
                getBids(departmentId, orderId);
                mainFrame.getBidsListPanel().setBidsTableData(Database.BIDS.getList());
                mainFrame.getBidsListPanel().refreshBidsTableData();
            }

            @Override
            public void bidDeleteEventOccurred(BidModel model) {
                setInactive(model);
                deleteBid(model);
                getBids();
                mainFrame.getBidsListPanel().setBidsTableData(Database.BIDS.getList());
                mainFrame.getBidsListPanel().refreshBidsTableData();
            }
        });

        mainFrame.getBidsListPanel().getCreateBidDialog().setCreateBidDialogListener(new CreateBidDialogListener() {
            @Override
            public void departmentSelectionEventOccurred(long depId) {
                getDepartmentFinancesByDepartment(depId);
                mainFrame.getBidsListPanel().getCreateBidDialog().setFinanceDepartmentBoxData(Database.DEPARTMENT_FINANCES.getList());
            }

            @Override
            public void bidCreateEventOccurred(BidModel model) {
                setCreated(model);
                createBid(model);
                getBids();
                mainFrame.getBidsListPanel().setBidsTableData(Database.BIDS.getList());
                mainFrame.getBidsListPanel().refreshBidsTableData();
            }

            @Override
            public void bidEditEventOccurred(BidModel model) {
                setModified(model);
                editBid(model);
                getBids();
                mainFrame.getBidsListPanel().setBidsTableData(Database.BIDS.getList());
                mainFrame.getBidsListPanel().refreshBidsTableData();
            }
        });

        mainFrame.getExitItem().addActionListener(ev -> closeDialog());
    }

    private void logEvent(String message, Color color){
        mainFrame.getStatusPanel().setStatus(message, color);
        mainFrame.getLoggerDialog().addToLog(message, color);
    }

    private void extendedErrorLog(String message){
        mainFrame.getLoggerDialog().addToLog(message, Utils.RED);
    }


    // sets connection settings to Properties object
    private void setConnectionSettings(String host, String database, String schema,
                                       String port, String user, String password) {
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

    //connecting to DB
    private void connect() {
        try {
            Database.DB.connect(conSet);
            logEvent(Labels.getProperty("connectedToDB"), Utils.GREEN);
        } catch (Exception e) {
            e.printStackTrace();
            logEvent(Labels.getProperty("NoConnectionToDB"), Utils.RED);
            JOptionPane.showMessageDialog(mainFrame,
                    Labels.getProperty("NoConnectionToDB"),
                    Labels.getProperty("DatabaseConnectionError"),
                    JOptionPane.ERROR_MESSAGE);
            // if can't connect - call ConnectionSettingsDialog
            mainFrame.getConSettDialog().setVisible(true);
        }
    }

    //disconnecting from DB
    private void disconnect() {
        Database.DB.disconnect();
    }

    //general methods for loging modifications in DB entries
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

    //methods requesting the DB
    //GETTERS
    private void getCpvRequest(String cpvRequest, boolean sameLvlShow) {
        try {
            Database.CPV.retrieve(cpvRequest, sameLvlShow);
            logEvent(Labels.withColon("cpvRequest")+cpvRequest+Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("cpvRequest")+cpvRequest+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    private void getRolesRequest() {
        try {
            Database.ROLES.retrieve();
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("request")+Labels.withSpaceBefore("role")+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    private void getEmployees() {
        try {
            Database.EMPLOYEES.retrieve();
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("request")+Labels.withSpaceBefore("user")+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    private void getEmployees(long depId) {
        try {
            Database.EMPLOYEES.retrieve(depId);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("request")+Labels.withSpaceBefore("user")+" dep id: "+depId+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    private long getSalt(String login){
        try {
            return Database.EMPLOYEES.getSalt(login);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent("Salt retrieval error with login: "+login, Utils.RED);
            extendedErrorLog(e.toString());
            return 0;
        }
    }

    private void getInstRequest() {
        try {
            Database.INSTITUTES.retrieve();
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("request")+Labels.withSpaceBefore("institute")+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    private void getDepartments(long instId) {
        try {
            Database.DEPARTMENTS.retrieve(instId);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("request")+Labels.withSpaceBefore("department")+" inst id: "+instId+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    private void getSubdepRequest(long depId) {
        try {
            Database.SUBDEPARTMENS.retrieve(depId);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("request")+Labels.withSpaceBefore("subdepartment")+" dep id: "+depId+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    private void getAmUnits() {
        try {
            Database.AMOUNTUNITS.retrieve();
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("request")+Labels.withSpaceBefore("amount")+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    private void getProd() {
        try {
            Database.PRODUCERS.retrieve();
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("request")+Labels.withSpaceBefore("producer")+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    private void getSupl() {
        try {
            Database.SUPPLIERS.retrieve();
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("request")+Labels.withSpaceBefore("suplBorder")+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    private void getFinances() {
        try {
            Database.FINANCES.retrieve();
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("request")+Labels.withSpaceBefore("finances")+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    private void getDepartmentFinances() {
        try {
            Database.DEPARTMENT_FINANCES.retrieve();
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("request")+Labels.withSpaceBefore("departmentFinances")+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }


    private void getDepartmentFinancesByOrder(long orderId) {
        try {
            Database.DEPARTMENT_FINANCES.retrieveByOrderID(orderId);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("request")+Labels.withSpaceBefore("departmentFinances")+" order Id: "+orderId+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    private void getDepartmentFinancesByDepartment(long departmentId) {
        try {
            Database.DEPARTMENT_FINANCES.retrieveByDepartmentID(departmentId);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("request")+Labels.withSpaceBefore("departmentFinances")+" department Id: "+departmentId+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    // check user login and pass
    private boolean checkLogin(String username, String password) {
        try {
            return Database.EMPLOYEES.checkLogin(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("request")+Labels.withSpaceBefore("user")+" :"+username+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
        return false;
    }

    private void getBids() {
        try {
            Database.BIDS.retrieve();
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("request")+Labels.withSpaceBefore("bids")+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    private void getBids(long departmentId, long orderId) {
        try {
            Database.BIDS.retrieve(departmentId, orderId);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("request")+Labels.withSpaceBefore("bids")+" order ID: "+orderId+" department ID: "+departmentId+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    //CRUD Employees
    private void createEmployee(EmployeeModel model) {
        setCreated(model);
        try {
            Database.EMPLOYEES.create(model);
            logEvent(Labels.withColon("createNewEmployee")+model.toString()+Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("createNewEmployee")+model.toString()+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    private void editEmployee(EmployeeModel model) {
        setModified(model);
        try {
            Database.EMPLOYEES.update(model);
            logEvent(Labels.withColon("editEmployee")+model.toString()+Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("editEmployee")+model.toString()+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    private void deleteEmployee(EmployeeModel model) {
        setInactive(model);
        try {
            Database.EMPLOYEES.delete(model);
            logEvent(Labels.withColon("deleteEmployee")+model.toString()+Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("deleteEmployee")+model.toString()+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    //CRUD Institutes
    private void createInstitute(InstituteModel instModel) {
        setCreated(instModel);
        try {
            Database.INSTITUTES.create(instModel);
            logEvent(Labels.withColon("addInstitute")+instModel.toString()+Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("addInstitute")+instModel.toString()+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    private void editInstitute(InstituteModel instModel) {
        setModified(instModel);
        try {
            Database.INSTITUTES.update(instModel);
            logEvent(Labels.withColon("editInstitite")+instModel.toString()+Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("editInstitite")+instModel.toString()+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }

    }

    private void deleteInstitute(InstituteModel instModel) {
        setInactive(instModel);
        try {
            Database.INSTITUTES.delete(instModel);
            logEvent(Labels.withColon("delInstitite")+instModel.toString()+Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("delInstitite")+instModel.toString()+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    //CRUD Departments
    private void createDepartment(DepartmentModel model) {
        setCreated(model);
        try {
            Database.DEPARTMENTS.create(model);
            logEvent(Labels.withColon("addDepartment")+model.toString()+Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("addDepartment")+model.toString()+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    private void editDepartment(DepartmentModel model) {
        setModified(model);
        try {
            Database.DEPARTMENTS.update(model);
            logEvent(Labels.withColon("editDepartment")+model.toString()+Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("editDepartment")+model.toString()+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    private void deleteDepartment(DepartmentModel model) {
        setInactive(model);
        try {
            Database.DEPARTMENTS.delete(model);
            logEvent(Labels.withColon("delDepartment")+model.toString()+Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("delDepartment")+model.toString()+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    //CRUD Subdepartments
    private void createSubdepartment(SubdepartmentModel model) {
        setCreated(model);
        try {
            Database.SUBDEPARTMENS.create(model);
            logEvent(Labels.withColon("addSubdepartment")+model.toString()+Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("addSubdepartment")+model.toString()+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    private void editSubdepartment(SubdepartmentModel model) {
        setModified(model);
        try {
            Database.SUBDEPARTMENS.update(model);
            logEvent(Labels.withColon("editSubdepartment")+model.toString()+Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("editSubdepartment")+model.toString()+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    private void deleteSubdepartment(SubdepartmentModel model) {
        setInactive(model);
        try {
            Database.SUBDEPARTMENS.delete(model);
            logEvent(Labels.withColon("delSubdepartment")+model.toString()+Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("delSubdepartment")+model.toString()+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    //CRUD Amount and Units
    private void createAmUnit(AmountUnitsModel model) {
        setCreated(model);
        try {
            Database.AMOUNTUNITS.create(model);
            logEvent(Labels.withColon("addAmUnit")+model.toString()+Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("addAmUnit")+model.toString()+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    private void editAmUnit(AmountUnitsModel model) {
        setModified(model);
        try {
            Database.AMOUNTUNITS.update(model);
            logEvent(Labels.withColon("editAmUnit")+model.toString()+Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("editAmUnit")+model.toString()+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    private void deleteAmUnit(AmountUnitsModel model) {
        setInactive(model);
        try {
            Database.AMOUNTUNITS.delete(model);
            logEvent(Labels.withColon("delAmUnit")+model.toString()+Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("delAmUnit")+model.toString()+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    //CRUD Producers
    private void createProd(ProducerModel model) {
        setCreated(model);
        try {
            Database.PRODUCERS.create(model);
            logEvent(Labels.withColon("addProd")+model.toString()+Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("addProd")+model.toString()+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    private void editProd(ProducerModel model) {
        setModified(model);
        try {
            Database.PRODUCERS.update(model);
            logEvent(Labels.withColon("editProd")+model.toString()+Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("editProd")+model.toString()+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    private void deleteProd(ProducerModel model) {
        setInactive(model);
        try {
            Database.PRODUCERS.delete(model);
            logEvent(Labels.withColon("delProd")+model.toString()+Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("delProd")+model.toString()+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    //CRUD Suppliers
    private void createSupl(SupplierModel model) {
        setCreated(model);
        try {
            Database.SUPPLIERS.create(model);
            logEvent(Labels.withColon("addSupl")+model.toString()+Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("addSupl")+model.toString()+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    private void editSupl(SupplierModel model) {
        setModified(model);
        try {
            Database.SUPPLIERS.update(model);
            logEvent(Labels.withColon("editSupl")+model.toString()+Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("editSupl")+model.toString()+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    private void deleteSupl(SupplierModel model) {
        setInactive(model);
        try {
            Database.SUPPLIERS.delete(model);
            logEvent(Labels.withColon("delSupl")+model.toString()+Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("delSupl")+model.toString()+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    //CRUD Finances
    private void createFinance(FinanceModel model) {
        setCreated(model);
        try {
            Database.FINANCES.create(model);
            logEvent(Labels.withColon("createOrder")+model.toString()+Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("createOrder")+model.toString()+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    private void editFinance(FinanceModel model) {
        setModified(model);
        try {
            Database.FINANCES.update(model);
            logEvent(Labels.withColon("editOrder")+model.toString()+Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("editOrder")+model.toString()+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    private void deleteFinance(FinanceModel model) {
        setInactive(model);
        try {
            Database.FINANCES.delete(model);
            logEvent(Labels.withColon("deleteOrder")+model.toString()+Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("deleteOrder")+model.toString()+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }
    //CRUD Department Finances
    private void createDepartmentFinances(FinanceDepartmentModel model) {
        setCreated(model);
        try {
            Database.DEPARTMENT_FINANCES.create(model);
            logEvent(Labels.withColon("addDepOrder")+model.toString()+Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("addDepOrder")+model.toString()+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    private void editDepartmentFinances(FinanceDepartmentModel model) {
        setModified(model);
        try {
            Database.DEPARTMENT_FINANCES.update(model);
            logEvent(Labels.withColon("editDepOrder")+model.toString()+Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("editDepOrder")+model.toString()+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    private void deleteDepartmentFinances(FinanceDepartmentModel model) {
        setInactive(model);
        try {
            Database.DEPARTMENT_FINANCES.delete(model);
            logEvent(Labels.withColon("deleteDepOrder")+model.toString()+Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("deleteDepOrder")+model.toString()+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    //CRUD Bids
    private void createBid(BidModel model) {
        setCreated(model);
        try {
            Database.BIDS.create(model);
            logEvent(Labels.withColon("createBid")+model.toString()+Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("createBid")+model.toString()+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    private void editBid(BidModel model) {
        setModified(model);
        try {
            Database.BIDS.update(model);
            logEvent(Labels.withColon("editBid")+model.toString()+Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("editBid")+model.toString()+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    private void deleteBid(BidModel model) {
        setInactive(model);
        try {
            Database.BIDS.delete(model);
            logEvent(Labels.withColon("deleteBid")+model.toString()+Labels.withSpaceBefore("success"), Utils.GREEN);
        } catch (SQLException e) {
            e.printStackTrace();
            logEvent(Labels.withColon("deleteBid")+model.toString()+Labels.withSpaceBefore("error"), Utils.RED);
            extendedErrorLog(e.toString());
        }
    }

    //default close method
    private void close() {
        Database.DB.disconnect();
        mainFrame.dispose();
    }

    //but it calls only in this close() method (except close in login dialog)
    private void closeDialog() {
        int action = JOptionPane.showConfirmDialog(this.mainFrame,
                Labels.getProperty("WantExit"),
                Labels.getProperty("ConfirmExit"),
                JOptionPane.OK_CANCEL_OPTION);
        if (action == JOptionPane.OK_OPTION) {
            close();
        }
    }
}
