/*
  This class makes a base for interface of Promasy. It instantiates all parent frames
  and dialog boxes.
 */

package gui;

import controller.TableGenerator;
import gui.amunits.AmUnitsDialog;
import gui.amunits.AmUnitsDialogListener;
import gui.bids.BidsListPanel;
import gui.bids.BidsListPanelListener;
import gui.bids.CreateBidPanel;
import gui.commons.Colors;
import gui.commons.Icons;
import gui.commons.Labels;
import gui.components.CalculatorDialog;
import gui.conset.ConSetDialog;
import gui.conset.ConSetListener;
import gui.cpv.CpvDialog;
import gui.cpv.CpvSearchListener;
import gui.employee.CreateEmployeeDialog;
import gui.employee.CreateEmployeeDialogListener;
import gui.employee.EditEmployeeDialog;
import gui.employee.EditEmployeeDialogListener;
import gui.finance.FinancePanel;
import gui.finance.FinancePanelListener;
import gui.login.LoginListener;
import gui.login.LoginPanel;
import gui.organization.OrganizationDialog;
import gui.organization.OrganizationDialogListener;
import gui.producer.ProducerDialog;
import gui.producer.ProducerDialogListener;
import gui.producer.ReasonsDialog;
import gui.producer.ReasonsDialogListener;
import gui.reports.bids.ReportParametersDialog;
import gui.reports.bids.ReportParametersDialogListener;
import gui.reports.cpv.CpvAmountDialog;
import gui.reports.cpv.CpvAmountDialogListener;
import gui.supplier.SupplierDialog;
import gui.supplier.SupplierDialogListener;
import jiconfont.icons.FontAwesome;
import jiconfont.swing.IconFontSwing;
import model.dao.LoginData;
import model.enums.Role;
import model.models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;

public class MainFrame extends JFrame {

    private LoginPanel loginPanel;
    private Toolbar toolbar;
    private MenuBar menuBar;
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
    private CpvAmountDialog cpvAmountDialog;
    private LoggerDialog loggerDialog;
    private ReportParametersDialog reportParametersDialog;
    private JTabbedPane tabPane;
    private CalculatorDialog calculatorDialog;
    private MainFrameListener listener;
    private DrawSplashScreen splashScreen;
    private TableGenerator tg;

    public MainFrame() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        splashScreen = new DrawSplashScreen();
        splashScreen.start();

        // registering font for icons
        IconFontSwing.register(FontAwesome.getIconFont());

        //initializing toolbar, login and connection settings windows and other common windows
        toolbar = new Toolbar();
        loginPanel = new LoginPanel(this);
        conSettDialog = new ConSetDialog(this);
        loggerDialog = new LoggerDialog(this);
        statusPanel = new StatusPanel(this);
        calculatorDialog = new CalculatorDialog(this);
        bidsListPanel = new BidsListPanel(this);
        amUnitsDialog = new AmUnitsDialog(this);
        producerDialog = new ProducerDialog(this);
        supplierDialog = new SupplierDialog(this);
        reasonsDialog = new ReasonsDialog(this);
        infoDialog = new InfoDialog(this);
        cpvDialog = new CpvDialog(this);
        cpvAmountDialog = new CpvAmountDialog(this);
        financePanel = new FinancePanel(this);
        editOrgDialog = new OrganizationDialog(this);
        editEmpDialog = new EditEmployeeDialog(this);
        createEmployeeDialog = new CreateEmployeeDialog(this);
        reportParametersDialog = new ReportParametersDialog(this);
        tg = new TableGenerator(this);

        setLayout(new BorderLayout());
    }

    public void initialize() {
        //removing login panel
        setVisible(false);
        remove(loginPanel);
        //marking login panel for gc
        loginPanel = null;

        //adding components of main window
        add(toolbar, BorderLayout.PAGE_START);
        add(statusPanel, BorderLayout.SOUTH);

        Role role = LoginData.getInstance().getRole();

        //creating and setting menu bar
        menuBar = new MenuBar(role);
        setJMenuBar(menuBar.getMenuBar());

        // init main pane and menu bar according to user roles
        switch (role) {
            case ADMIN:
                createTabPane();
                break;
            case DIRECTOR:
                createTabPane();
                break;
            case DEPUTY_DIRECTOR:
                createTabPane();
                break;
            case ACCOUNTANT:
                createTabPane();
                break;
            case ECONOMIST:
                createTabPane();
                break;
            case HEAD_OF_TENDER_COMMITTEE:
                createTabPane();
                financePanel.hideCed();
                break;
            case SECRETARY_OF_TENDER_COMMITTEE:
                createTabPane();
                financePanel.hideCed();
                break;
            case HEAD_OF_DEPARTMENT:
                useUserDepartment();
                createTabPane();
                financePanel.hideCed();
                break;
            case PERSONALLY_LIABLE_EMPLOYEE:
                useUserDepartment();
                createTabPane();
                financePanel.hideCed();
                break;
            case USER:
                useUserDepartment();
                add(bidsListPanel, BorderLayout.CENTER);
                financePanel.hideCed();
                break;
            default:
                useUserDepartment();
                add(bidsListPanel, BorderLayout.CENTER);
                financePanel.hideCed();
                break;
        }

        setListeners();
    }

    private void positionOnScreenCenter() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
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

    private void setListeners() {
        statusPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loggerDialog.setVisible(true);
            }
        });

        toolbar.setToolbarListener(new ToolbarListener() {
            @Override
            public void printEventOccurred() {
                onPrintClick();
            }
            @Override
            public void showCpvSearchDialog() {
                cpvDialog.setVisible(true);
            }
            @Override
            public void showCalculator() {
                calculatorDialog.setVisible(true);
            }
            @Override
            public void exportToTableEventOccurred() {
                onExportToTableClick();
            }
            @Override
            public void refreshTable() {
                onRefreshClick();
            }
        });

        menuBar.setMenuBarListener(new MenuBarListener() {
            @Override
            public void showEmpEditDialog() {
                editEmpDialog.setVisible(true);
            }
            @Override
            public void showEditOrgDialog() {
                editOrgDialog.setVisible(true);
            }
            @Override
            public void showConSetDialog() {
                conSettDialog.setVisible(true);
            }
            @Override
            public void printAction() {
                onPrintClick();
            }
            @Override
            public void exportToTableAction() {
                onExportToTableClick();
            }
            @Override
            public void showCpvAmountDialog() {
                cpvAmountDialog.setVisible(true);
            }
            @Override
            public void showAmUnitsDialog() {
                amUnitsDialog.setVisible(true);
            }
            @Override
            public void showProducerDialog() {
                producerDialog.setVisible(true);
            }
            @Override
            public void showSupplierDialog() {
                supplierDialog.setVisible(true);
            }
            @Override
            public void editCurrentUserAction() {
                createEmployeeDialog.setEmployeeModel(LoginData.getInstance());
            }
            @Override
            public void showInfoDialog() {
                infoDialog.setVisible(true);
            }
            @Override
            public void exitAction() {
                if (listener != null) {
                    listener.exitEventOccurred();
                }
            }
            @Override
            public void setCurrentVersionAsMinimum() {
                {
                    int action = JOptionPane.showConfirmDialog(MainFrame.this, Labels.getProperty("setMinimumVersionLong") + " " + Labels.getVersion() + "?", Labels.getProperty("confirmAction"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, Icons.QUESTION);
                    if (action == JOptionPane.OK_OPTION && listener != null) {
                        listener.setMinimumVersionEventOccurred();
                    }
                }
            }
        });
    }

    private void onExportToTableClick() {
        List<BidModel> reportList = null;
        String name = EmptyModel.STRING;
        if (tabPane != null) {
            if (tabPane.getSelectedComponent().equals(bidsListPanel)) {
                reportList = bidsListPanel.getSelectedBids();
                name = "bids";
            } else if (tabPane.getSelectedComponent().equals(financePanel)) {
                FinancePanel.FinanceReport report = financePanel.getReportsList();
                reportList = report.getList();
                name = report.getName();
            }
        } else {
            reportList = bidsListPanel.getSelectedBids();
            name = "bids";
        }
        if (reportList != null) {
            tg.generateReport(reportList, name);
        }
    }

    private void onRefreshClick() {
        if (tabPane != null) {
            if (tabPane.getSelectedComponent().equals(bidsListPanel)) {
                bidsListPanel.refresh();
            } else if (tabPane.getSelectedComponent().equals(financePanel)) {
                financePanel.refresh();
            }
        } else {
            bidsListPanel.refresh();
        }
    }

    private void onPrintClick() {
        if (tabPane != null) {
            if (tabPane.getSelectedComponent().equals(bidsListPanel)) {
                showReportParametersDialog();
            } else if (tabPane.getSelectedComponent().equals(financePanel)) {
                cpvAmountDialog.setVisible(true);
            }
        } else {
            showReportParametersDialog();
        }
    }

    //setters
    public void setDefaultConnectionSettings(ConnectionSettingsModel model) {
        conSettDialog.setDefaults(model);
    }

    //windows controls
    public void showLoginPane() {
        add(loginPanel, BorderLayout.CENTER);
        positionOnScreenCenter();
        super.setVisible(true);
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
        cpvDialog.showWithCode(cpv);
    }

    private void showReportParametersDialog() {
        if (bidsListPanel.isReadyForPrint() && listener != null) {
            // search for heads of department in department
            reportParametersDialog.setDepartmentHeadBoxData(
                    listener.searchForPerson(Role.HEAD_OF_DEPARTMENT, bidsListPanel.getSelectedDepartment().getModelId()));
            // search for personally liable employee in department
            reportParametersDialog.setPersonallyLiableEmpBoxData(
                    listener.searchForPerson(Role.PERSONALLY_LIABLE_EMPLOYEE, bidsListPanel.getSelectedDepartment().getModelId()));
            // search for chief accountant
            reportParametersDialog.setAccountantBoxData(
                    listener.searchForPerson(Role.ACCOUNTANT));
            // search for chief economist
            reportParametersDialog.setEconomistBoxData(
                    listener.searchForPerson(Role.ECONOMIST));
            // search for SECRETARY OF TENDER COMMITTEE
            reportParametersDialog.setHeadTenderBoxData(
                    listener.searchForPerson(Role.SECRETARY_OF_TENDER_COMMITTEE));
            // search for director
            reportParametersDialog.setHeadBoxData(
                    listener.searchForPerson(Role.DIRECTOR));
            // show dialog with selectors for director, head of department, PLE, accountant, economist, SECRETARY OF TENDER COMMITTEE
            reportParametersDialog.setVisible(true);
        }
    }

    public void bidListPrint(Map<String, Object> parameters) {
        bidsListPanel.printBidList(parameters);
    }

    // Status panel writers
    public void writeStatusPanelCurrentUser(String userName) {
        statusPanel.setCurrentUserLabel(userName);
    }

    public void logEvent(String message, Color color) {
        statusPanel.setStatus(message, color);
        loggerDialog.addToLog(message, color);
    }

    public void logError(Exception exception, String message) {
        statusPanel.setStatus(message, Colors.RED);
        loggerDialog.addToLog(message, Colors.RED);
        loggerDialog.addToLog(exception.toString(), Colors.RED);
    }

    //Listeners
    public void setMainFrameListener(MainFrameListener listener) {
        this.listener = listener;
    }
    public void setLoginListener(LoginListener loginListener) {
        loginPanel.setLoginListener(loginListener);
    }
    public void setConSetListener(ConSetListener listener) {
        conSettDialog.setConSetListener(listener);
    }
    public void setCpvListener(CpvSearchListener listener) {
        cpvDialog.setCpvListener(listener);
    }
    public void setEmployeeDialogListener(EditEmployeeDialogListener listener) {
        editEmpDialog.setListener(listener);
    }
    public void setCreateEmployeeDialogListener(CreateEmployeeDialogListener listener) {
        createEmployeeDialog.setListener(listener);
    }
    public void setOrganizationDialogListener(OrganizationDialogListener listener) {
        editOrgDialog.setListener(listener);
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
        financePanel.setListener(listener);
    }
    public void setBidsListPanelListener(BidsListPanelListener listener) {
        bidsListPanel.setListener(listener);
    }
    public void setReportParametersDialogListener(ReportParametersDialogListener listener) {
        reportParametersDialog.setListener(listener);
    }
    public void setCpvAmountDialogListener(CpvAmountDialogListener listener) {
        cpvAmountDialog.setListener(listener);
    }

    //Model lists
    public void setCpvModelList(List<CPVModel> cpvModelList) {
        cpvDialog.setData(cpvModelList);
    }

    public void setAmountUnitsModelList(List<AmountUnitsModel> amountUnitsModelList) {
        amUnitsDialog.setData(amountUnitsModelList);
        bidsListPanel.getCreateBidPanel().setAmUnitsBoxData(amountUnitsModelList);
    }

    public void setInstituteModelList(List<InstituteModel> instituteModelList) {
        editOrgDialog.setInstData(instituteModelList);
        createEmployeeDialog.setInstData(instituteModelList);
    }

    public void setDepartmentModelList(List<DepartmentModel> departmentsList) {
        financePanel.getCreateDepartmentFinancePanel().setDepartmentBoxData(departmentsList);
        bidsListPanel.setDepartmentBoxData(departmentsList);
        createEmployeeDialog.setDepData(departmentsList);
        editOrgDialog.setDepData(departmentsList);
    }

    public void setSubdepartmentModelList(List<SubdepartmentModel> subdepartmentModelList) {
        createEmployeeDialog.setSubdepData(subdepartmentModelList);
        financePanel.getCreateDepartmentFinancePanel().setSubdepartmentBoxData(subdepartmentModelList);
        editOrgDialog.setSubdepData(subdepartmentModelList);
    }

    public void setEmployeeModelList(List<EmployeeModel> employeeModelList) {
        editEmpDialog.setEmpTableData(employeeModelList);
    }

    public void setProducerModelList(List<ProducerModel> producerModelList) {
        producerDialog.setData(producerModelList);
        bidsListPanel.getCreateBidPanel().setProducerBoxData(producerModelList);
    }

    public void setSupplierModelList(List<SupplierModel> supplierModelList) {
        supplierDialog.setData(supplierModelList);
        bidsListPanel.getCreateBidPanel().setSupplierBoxData(supplierModelList);
    }

    public void setReasonsModelList(List<ReasonForSupplierChoiceModel> reasonsModelList) {
        bidsListPanel.getCreateBidPanel().setReasonForSupplierChoiceBoxData(reasonsModelList);
        reasonsDialog.setData(reasonsModelList);
    }

    public void setFinanceModelList(List<FinanceModel> financeModelList) {
        financePanel.setFinanceTableData(financeModelList);
    }

    public void setFinanceDepartmentModelList(List<FinanceDepartmentModel> financeDepartmentModelList) {
        financePanel.setDepartmentFinanceTableData(financeDepartmentModelList);
        bidsListPanel.setFinanceDepartmentBoxData(financeDepartmentModelList);
        bidsListPanel.getCreateBidPanel().setFinanceDepartmentBoxData(financeDepartmentModelList);
    }

    public void setCpvAmountDialogList(List<CpvAmountModel> cpvAmounts) {
        cpvAmountDialog.setTableData(cpvAmounts);
    }
    public void setBidModelList(List<BidModel> bidModelList) {
        bidsListPanel.setBidsTableData(bidModelList);
    }
    public void setCpvModel(CPVModel cpvModel) {
        this.bidsListPanel.getCreateBidPanel().setSelectedCPV(cpvModel);
    }

    public void setEditOrgDialogVisible() {
        editOrgDialog.setVisible(true);
    }

    public CreateEmployeeDialog getCreateEmployeeDialog() {
        return createEmployeeDialog;
    }
    public CreateBidPanel getCreateBidPanel() {
        return bidsListPanel.getCreateBidPanel();
    }
    public DrawSplashScreen getSplashScreen() {
        return splashScreen;
    }

    public void saveLog() {
        loggerDialog.saveLog();
    }

    @Override
    public void setVisible(boolean visible) {
        if (listener != null && visible) {
            setTitle(Labels.getProperty("mainFrameSuper"));
            setSize(1000, 700);
            setResizable(true);
            positionOnScreenCenter();
            listener.getAllDepartments(LoginData.getInstance().getSubdepartment().getDepartment().getInstitute());
            bidsListPanel.getBids();
        }
        super.setVisible(visible);
    }

    public CPVModel validateCpv(String cpvCode) {
        if (listener != null) {
            return listener.validateCpv(cpvCode);
        }
        return EmptyModel.CPV;
    }
}
