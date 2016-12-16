/*
  This class makes a base for interface of Promasy. It instantiates all parent frames
  and dialog boxes.
 */

package main.java.gui;

import jiconfont.icons.FontAwesome;
import jiconfont.swing.IconFontSwing;
import main.java.gui.amunits.AmUnitsDialog;
import main.java.gui.amunits.AmUnitsDialogListener;
import main.java.gui.bids.BidsListPanel;
import main.java.gui.bids.BidsListPanelListener;
import main.java.gui.bids.CreateBidDialog;
import main.java.gui.bids.CreateBidDialogListener;
import main.java.gui.bids.reports.ReportParametersDialog;
import main.java.gui.bids.reports.ReportParametersDialogListener;
import main.java.gui.conset.ConSetDialog;
import main.java.gui.conset.ConSetListener;
import main.java.gui.cpv.CpvDialog;
import main.java.gui.cpv.CpvSearchListener;
import main.java.gui.empedit.CreateEmployeeDialog;
import main.java.gui.empedit.CreateEmployeeDialogListener;
import main.java.gui.empedit.EditEmployeeDialog;
import main.java.gui.empedit.EditEmployeeDialogListener;
import main.java.gui.finance.FinancePanel;
import main.java.gui.finance.FinancePanelListener;
import main.java.gui.instedit.OrganizationDialog;
import main.java.gui.instedit.OrganizationDialogListener;
import main.java.gui.login.LoginDialog;
import main.java.gui.login.LoginListener;
import main.java.gui.prodsupl.*;
import main.java.model.*;
import main.java.model.enums.Role;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.List;

public class MainFrame extends JFrame {

    private LoginDialog loginDialog;
    private Toolbar toolbar;
    private ConSetDialog conSettDialog;
    private OrganizationDialog editOrgDialog;
    private EditEmployeeDialog editEmpDialog;
    private CreateEmployeeDialog createEmployeeDialog;
    private InfoDialog infoDialog;
    private CpvDialog cpvDialog;
    private StatusPanel statusPanel;
    private AmUnitsDialog amUnitsDialog;
    private ProducerDialog producerDialog;
    private SupplierDialog supplierDialog;
    private ReasonsDialog reasonsDialog;
    private FinancePanel financePanel;
    private BidsListPanel bidsListPanel;
    private CreateBidDialog createBidDialog;
    private LoggerDialog loggerDialog;
    private ReportParametersDialog reportParametersDialog;
    private JTabbedPane tabPane;
    private MainFrameListener listener;

    public MainFrame() {
        // Setting name of the window and its parameters
        super(Labels.getProperty("mainFrameSuper"));
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // registering font for icons
        IconFontSwing.register(FontAwesome.getIconFont());

        // set location relative to the screen center
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        //initializing toolbar, login and connection settings windows, logger
        toolbar = new Toolbar();
        loginDialog = new LoginDialog(this);
        conSettDialog = new ConSetDialog(this);
        loggerDialog = new LoggerDialog(this);
        statusPanel = new StatusPanel(this);

        //initializing other common windows
        amUnitsDialog = new AmUnitsDialog(this);
        producerDialog = new ProducerDialog(this);
        supplierDialog = new SupplierDialog(this);
        reasonsDialog = new ReasonsDialog(this);
        infoDialog = new InfoDialog(this);
        cpvDialog = new CpvDialog(this);
        bidsListPanel = new BidsListPanel(this);
        createBidDialog = new CreateBidDialog(this);
        financePanel = new FinancePanel(this);
        editOrgDialog = new OrganizationDialog(this);
        editEmpDialog = new EditEmployeeDialog(this);
        createEmployeeDialog = new CreateEmployeeDialog(this);
        reportParametersDialog = new ReportParametersDialog(this);
    }

    public void initialize() {
        int roleId = LoginData.getInstance().getRoleId();
        //setting layout
        setLayout(new BorderLayout());

        // init panes according to user roles
        if (roleId == Role.ADMIN.getRoleId()) {
            createTabPane();
            setJMenuBar(createMenuBar(true));
        } else if (roleId == Role.DIRECTOR.getRoleId()) {
            createTabPane();
            setJMenuBar(createMenuBar(true));
        } else if (roleId == Role.DEPUTY_DIRECTOR.getRoleId()) {
            createTabPane();
            setJMenuBar(createMenuBar(true));
        } else if (roleId == Role.HEAD_OF_TENDER_COMMITTEE.getRoleId()) {
            createTabPane();
            setJMenuBar(createMenuBar(true));
        } else if (roleId == Role.ACCOUNTANT.getRoleId()) {
            createTabPane();
            setJMenuBar(createMenuBar(true));
        } else if (roleId == Role.ECONOMIST.getRoleId()) {
            createTabPane();
            setJMenuBar(createMenuBar(true));
        } else if (roleId == Role.HEAD_OF_DEPARTMENT.getRoleId()) {
            useUserDepartment();
            createTabPane();
            setJMenuBar(createMenuBar(true));
        } else if (roleId == Role.PERSONALLY_LIABLE_EMPLOYEE.getRoleId()) {
            useUserDepartment();
            createTabPane();
            setJMenuBar(createMenuBar(true));
        } else if (roleId == Role.USER.getRoleId()) {
            useUserDepartment();
            setJMenuBar(createMenuBar(false));
            add(bidsListPanel, BorderLayout.CENTER);
        }

        // creating MenuBar


        // setting layout and formating frames on mainframe
        add(toolbar, BorderLayout.PAGE_START);
        add(statusPanel, BorderLayout.SOUTH);

        statusPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loggerDialog.setVisible(true);
            }
        });

        toolbar.setToolbarListener(this::printEventOccurred);

        //hiding login dialog and showing mainframe
        loginDialog.setVisible(false);
    }

    private void useUserDepartment() {
        bidsListPanel.setUseUserDepartment();
        financePanel.setUseUserDepartment();
    }

    private void createTabPane() {
        tabPane = new JTabbedPane();
        tabPane.addTab(Labels.getProperty("bids"), bidsListPanel);
        tabPane.addTab(Labels.getProperty("finances"), financePanel);
        add(tabPane, BorderLayout.CENTER);
    }

    //This method generates menubar
    private JMenuBar createMenuBar(boolean isAdvanced) {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu(Labels.getProperty("file"));
        JMenuItem printItem = new JMenuItem(Labels.getProperty("print"));
        printItem.setIcon(Icons.PRINT);
        JMenuItem exitItem = new JMenuItem(Labels.getProperty("exit"));
        fileMenu.add(printItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        JMenu editMenu = new JMenu(Labels.getProperty("edit"));
        JMenuItem editAmUnitsItem = new JMenuItem(Labels.withThreeDots("amUnitsDialogSuper"));
        editAmUnitsItem.setIcon(Icons.UNITS);
        JMenuItem editProdItem = new JMenuItem(Labels.withThreeDots("prodDialogSuper"));
        editProdItem.setIcon(Icons.PRODUCER);
        JMenuItem editSuplItem = new JMenuItem(Labels.withThreeDots("suplDialogSuper"));
        editSuplItem.setIcon(Icons.SUPPLIER);
        JMenuItem editCurrentUserItem = new JMenuItem(Labels.withThreeDots("editCurrentUser"));
        editCurrentUserItem.setIcon(Icons.USER);
        editMenu.add(editAmUnitsItem);
        editMenu.add(editProdItem);
        editMenu.add(editSuplItem);
        editMenu.addSeparator();
        editMenu.add(editCurrentUserItem);

        JMenu helpMenu = new JMenu(Labels.getProperty("help"));
        JMenuItem infoItem = new JMenuItem(Labels.withThreeDots("aboutSoftware"));
        infoItem.setIcon(Icons.ABOUT);
        helpMenu.add(infoItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        printItem.addActionListener(e -> printEventOccurred());

        exitItem.addActionListener(e -> {
            if (listener != null) {
                listener.exitEventOccurred();
            }
        });

        editAmUnitsItem.addActionListener(e -> amUnitsDialog.setVisible(true));
        editProdItem.addActionListener(e -> producerDialog.setVisible(true));
        editSuplItem.addActionListener(e -> supplierDialog.setVisible(true));
        editCurrentUserItem.addActionListener(e -> createEmployeeDialog.setEmployeeModel(LoginData.getInstance()));
        infoItem.addActionListener(e -> infoDialog.setVisible(true));

        // advanced menu for non users
        if (isAdvanced) {
            JMenuItem editOrgItem = new JMenuItem(Labels.withThreeDots("editOrganizationsDepartments"));
            editOrgItem.setIcon(Icons.ORGANIZATION);

            editMenu.add(editOrgItem);


            JMenu settingsMenu = new JMenu(Labels.getProperty("settings"));
            JMenuItem conSettItem = new JMenuItem(Labels.withThreeDots("ConnectionWithDBSettings"));
            conSettItem.setIcon(Icons.CONNECTION_SETTINGS);

            settingsMenu.add(conSettItem);

            if (LoginData.getInstance().getRoleId() == Role.ADMIN.getRoleId()) {
                JMenuItem editEmpItem = new JMenuItem(Labels.withThreeDots("editEmployees"));
                editEmpItem.setIcon(Icons.USERS);
                editMenu.add(editEmpItem);

                JMenuItem setCurrentVersionAsMinimum = new JMenuItem(Labels.getProperty("setMinimumVersion"));
                settingsMenu.addSeparator();
                settingsMenu.add(setCurrentVersionAsMinimum);
                setCurrentVersionAsMinimum.addActionListener(e -> {
                    int action = JOptionPane.showConfirmDialog(this, Labels.getProperty("setMinimumVersionLong") + Labels.withSpaceBefore("versionNumber") + "?", Labels.getProperty("confirmAction"), JOptionPane.OK_CANCEL_OPTION);
                    if (action == JOptionPane.OK_OPTION && listener != null) {
                        listener.setMinimumVersionEventOccurred();
                    }
                });
                editEmpItem.addActionListener(e -> editEmpDialog.setVisible(true));
            }

            menuBar.add(settingsMenu);

            editOrgItem.addActionListener(e -> editOrgDialog.setVisible(true));

            conSettItem.addActionListener(e -> conSettDialog.setVisible(true));
        }

        menuBar.add(helpMenu);

        return menuBar;
    }

    private void printEventOccurred() {
        if (tabPane != null) {
            if (tabPane.getSelectedComponent().equals(bidsListPanel)) {
                showReportParametersDialog();
            } else {
                JOptionPane.showMessageDialog(this, Labels.getProperty("printSupportedOnlyInBids"), Labels.getProperty("printError"), JOptionPane.ERROR_MESSAGE);
            }
        } else {
            showReportParametersDialog();
        }
    }

    //setters
    public void setDefaultConnectionSettings(String server, String database, String schema, int portNumber, String user) {
        conSettDialog.setDefaults(server, database, schema, portNumber, user);
    }

    public void setBidsPanelSum(BigDecimal sum, BigDecimal financeLeft) {
        bidsListPanel.setFinanceLabels(sum, financeLeft);
    }

    public void setCpvCode(String selectedCPV) {
        createBidDialog.setCPVField(selectedCPV);
    }

    //windows controls
    public void showLoginDialog() {
        loginDialog.setVisible(true);
    }

    public void showConSettDialog() {
        conSettDialog.setVisible(true);
    }

    public void showProducerDialog() {
        producerDialog.setVisible(true);
    }

    public void showSupplierDialog() {
        supplierDialog.setVisible(true);
    }

    public void showReasonsDialog() {
        reasonsDialog.setVisible(true);
    }

    public void showAmUnitsDialog() {
        amUnitsDialog.setVisible(true);
    }

    public void showCpvDialog() {
        cpvDialog.setVisible(true);
    }

    public void showCpvDialog(String cpv) {
        cpvDialog.makeCpvQuery(cpv, true);
        cpvDialog.setVisible(true);
    }

    private void showReportParametersDialog() {
        if (bidsListPanel.isReadyForPrint() && listener != null) {
            // search for heads of department (id 5000) in department
            listener.searchForPerson(5000, bidsListPanel.getSelectedDepartmentId());
            reportParametersDialog.setDepartmentHeadBoxData(Database.EMPLOYEES.getList());
            // search for personally liable employee (id 6000) in department
            listener.searchForPerson(6000, bidsListPanel.getSelectedDepartmentId());
            reportParametersDialog.setPersonallyLiableEmpBoxData(Database.EMPLOYEES.getList());
            // search for chief accountant (id 4000)
            listener.searchForPerson(4000);
            reportParametersDialog.setAccountantBoxData(Database.EMPLOYEES.getList());
            // search for chief economist (id 3000)
            listener.searchForPerson(3000);
            reportParametersDialog.setEconomistBoxData(Database.EMPLOYEES.getList());
            // show dialog with selectors for director, head of department, PLE, accountant, economist
            reportParametersDialog.setVisible(true);
        }
    }

    public void bidListPrint() {
        bidsListPanel.printBidList();
    }

    // Status panel writers
    public void writeStatusPanelCurrentUser(String userName) {
        statusPanel.setCurrentUserLabel(userName);
    }

    public void logEvent(String message, Color color) {
        statusPanel.setStatus(message, color);
        loggerDialog.addToLog(message, color);
    }

    public void logEvent(Exception exception, String message) {
        exception.printStackTrace();
        statusPanel.setStatus(message, Utils.RED);
        loggerDialog.addToLog(message, Utils.RED);
        loggerDialog.addToLog(exception.toString(), Utils.RED);
    }

    //Listeners
    public void setMainFrameListener(MainFrameListener listener) {
        this.listener = listener;
    }

    public void setLoginListener(LoginListener loginListener) {
        loginDialog.setLoginListener(loginListener);
    }

    public void setConSetListener(ConSetListener listener) {
        conSettDialog.setPrefsListener(listener);
    }

    public void setCpvListener(CpvSearchListener listener) {
        cpvDialog.setCpvListener(listener);
    }

    public void setEmployeeDialogListener(EditEmployeeDialogListener listener) {
        editEmpDialog.setEmployeeDialogListener(listener);
    }

    public void setCreateEmployeeDialogListener(CreateEmployeeDialogListener listener) {
        createEmployeeDialog.setCreateEmployeeDialogListener(listener);
    }

    public void setOrganizationDialogListener(OrganizationDialogListener listener) {
        editOrgDialog.setOrganizationDialogListener(listener);
    }

    public void setAmUnitsDialogListener(AmUnitsDialogListener listener) {
        amUnitsDialog.setListener(listener);
    }

    public void setProducerDialogListener(ProducerDialogListener listener) {
        producerDialog.setListener(listener);
    }

    public void setSupplierDialogListener(SupplierDialogListener listener) {
        supplierDialog.setListener(listener);
    }

    public void setReasonsDialogListener(ReasonsDialogListener listener) {
        reasonsDialog.setListener(listener);
    }

    public void setFinancePanelListener(FinancePanelListener listener) {
        financePanel.setFinancePanelListener(listener);
    }

    public void setBidsListPanelListener(BidsListPanelListener listener) {
        bidsListPanel.setBidsListPanelListener(listener);
    }

    public void setCreateBidDialogListener(CreateBidDialogListener listener) {
        createBidDialog.setCreateBidDialogListener(listener);
    }

    public void setReportParametersDialogListener(ReportParametersDialogListener listener) {
        reportParametersDialog.setListener(listener);
    }

    //Model lists
    public void setCpvModelList(List<CPVModel> cpvModelList) {
        cpvDialog.setData(cpvModelList);
    }

    public void setAmountUnitsModelList(List<AmountUnitsModel> amountUnitsModelList) {
        amUnitsDialog.setData(amountUnitsModelList);
        createBidDialog.setAmUnitsBoxData(amountUnitsModelList);
    }

    public void setInstituteModelList(List<InstituteModel> instituteModelList) {
        editOrgDialog.setInstData(instituteModelList);
        createEmployeeDialog.setInstData(instituteModelList);
    }

    public void setDepartmentModelList(List<DepartmentModel> departmentsList) {
        financePanel.setDepartmentBoxData(departmentsList);
        bidsListPanel.setDepartmentBoxData(departmentsList);
        createEmployeeDialog.setDepData(departmentsList);
        editOrgDialog.setDepData(departmentsList);
    }

    public void setSubdepartmentModelList(List<SubdepartmentModel> subdepartmentModelList) {
        createEmployeeDialog.setSubdepData(subdepartmentModelList);
        editOrgDialog.setSubdepData(subdepartmentModelList);
    }

    public void setEmployeeModelList(List<EmployeeModel> employeeModelList) {
        editEmpDialog.setEmpTableData(employeeModelList);
        financePanel.setEmployeeBoxData(employeeModelList);
        reportParametersDialog.setHeadBoxData(employeeModelList);
    }

    public void setRoleModelList(List<RoleModel> roleModelList) {
        createEmployeeDialog.setRolesData(roleModelList);
        reportParametersDialog.setRoleBoxData(roleModelList);
    }

    public void setProducerModelList(List<ProducerModel> producerModelList) {
        producerDialog.setProdData(producerModelList);
        createBidDialog.setProducerBoxData(producerModelList);
    }

    public void setSupplierModelList(List<SupplierModel> supplierModelList) {
        supplierDialog.setSuplData(supplierModelList);
        createBidDialog.setSupplierBoxData(supplierModelList);
    }

    public void setReasonsModelList(List<ReasonForSupplierChoiceModel> reasonsModelList) {
        createBidDialog.setReasonForSupplierChoiceBoxData(reasonsModelList);
        reasonsDialog.setReasonData(reasonsModelList);
    }

    public void setFinanceModelList(List<FinanceModel> financeModelList) {
        financePanel.setFinanceTableData(financeModelList);
    }

    public void setFinanceDepartmentModelList(List<FinanceDepartmentModel> financeDepartmentModelList) {
        financePanel.setDepartmentFinanceTableData(financeDepartmentModelList);
        bidsListPanel.setFinanceDepartmentBoxData(financeDepartmentModelList);
        createBidDialog.setFinanceDepartmentBoxData(financeDepartmentModelList);
    }

    public void setFinanceDepartmentModelListToBidDialog(List<FinanceDepartmentModel> list) {
        createBidDialog.setFinanceDepartmentBoxData(list);
    }

    public void setBidModelList(List<BidModel> bidModelList) {
        bidsListPanel.setBidsTableData(bidModelList);
    }

    public void setBidStatusList(List<StatusModel> list) {
        bidsListPanel.setBidStatusTableData(list);
    }

    public CreateEmployeeDialog getCreateEmployeeDialog() {
        return createEmployeeDialog;
    }

    public CreateBidDialog getCreateBidDialog() {
        return createBidDialog;
    }

    public OrganizationDialog getEditOrgDialog() {
        return editOrgDialog;
    }
}
