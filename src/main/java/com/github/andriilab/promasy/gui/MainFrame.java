/*
  This class makes a base for interface of Promasy. It instantiates all parent frames
  and dialog boxes.
 */

package com.github.andriilab.promasy.gui;

import com.github.andriilab.promasy.controller.Logger;
import com.github.andriilab.promasy.controller.TableGenerator;
import com.github.andriilab.promasy.gui.amunits.AmUnitsDialog;
import com.github.andriilab.promasy.gui.amunits.AmUnitsDialogListener;
import com.github.andriilab.promasy.gui.bids.BidsListPanel;
import com.github.andriilab.promasy.gui.bids.BidsListPanelListener;
import com.github.andriilab.promasy.gui.bids.CreateBidPanel;
import com.github.andriilab.promasy.gui.commons.Colors;
import com.github.andriilab.promasy.gui.commons.Icons;
import com.github.andriilab.promasy.gui.commons.Labels;
import com.github.andriilab.promasy.gui.components.CalculatorDialog;
import com.github.andriilab.promasy.gui.conset.ConSetDialog;
import com.github.andriilab.promasy.gui.conset.ConSetListener;
import com.github.andriilab.promasy.gui.cpv.CpvDialog;
import com.github.andriilab.promasy.gui.cpv.CpvSearchListener;
import com.github.andriilab.promasy.gui.employee.CreateEmployeeDialog;
import com.github.andriilab.promasy.gui.employee.CreateEmployeeDialogListener;
import com.github.andriilab.promasy.gui.employee.EditEmployeeDialog;
import com.github.andriilab.promasy.gui.employee.EditEmployeeDialogListener;
import com.github.andriilab.promasy.gui.finance.FinancePanel;
import com.github.andriilab.promasy.gui.finance.FinancePanelListener;
import com.github.andriilab.promasy.gui.login.LoginListener;
import com.github.andriilab.promasy.gui.login.LoginPanel;
import com.github.andriilab.promasy.gui.organization.OrganizationDialog;
import com.github.andriilab.promasy.gui.organization.OrganizationDialogListener;
import com.github.andriilab.promasy.gui.producer.ProducerDialog;
import com.github.andriilab.promasy.gui.producer.ProducerDialogListener;
import com.github.andriilab.promasy.gui.producer.ReasonsDialog;
import com.github.andriilab.promasy.gui.producer.ReasonsDialogListener;
import com.github.andriilab.promasy.gui.reports.bids.ReportParametersDialog;
import com.github.andriilab.promasy.gui.reports.bids.ReportParametersDialogListener;
import com.github.andriilab.promasy.gui.reports.cpv.CpvAmountDialog;
import com.github.andriilab.promasy.gui.reports.cpv.CpvAmountDialogListener;
import com.github.andriilab.promasy.gui.supplier.SupplierDialog;
import com.github.andriilab.promasy.gui.supplier.SupplierDialogListener;
import com.github.andriilab.promasy.model.dao.LoginData;
import com.github.andriilab.promasy.model.enums.Role;
import com.github.andriilab.promasy.model.models.*;
import jiconfont.icons.FontAwesome;
import jiconfont.swing.IconFontSwing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MainFrame extends JFrame {

    private LoginPanel loginPanel;
    private final Toolbar toolbar;
    private MenuBar menuBar;
    private final ConSetDialog conSettDialog;
    private final OrganizationDialog editOrgDialog;
    private final EditEmployeeDialog editEmpDialog;
    private final CreateEmployeeDialog createEmployeeDialog;
    private final InfoDialog infoDialog;
    private final CpvDialog cpvDialog;
    private final StatusPanel statusPanel;
    private final AmUnitsDialog amUnitsDialog;
    private final ProducerDialog producerDialog;
    private final SupplierDialog supplierDialog;
    private final ReasonsDialog reasonsDialog;
    private final FinancePanel financePanel;
    private final BidsListPanel bidsListPanel;
    private final CpvAmountDialog cpvAmountDialog;
    private final LoggerDialog loggerDialog;
    private final ReportParametersDialog reportParametersDialog;
    private JTabbedPane tabPane;
    private final CalculatorDialog calculatorDialog;
    private MainFrameListener listener;
    private final DrawSplashScreen splashScreen;
    private final TableGenerator tg;
    private final HTMLViewer manualViewer;

    public MainFrame() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        System.out.println("ProMasy - Procurement Management System version " + Labels.getVersion() + "\n" +
                "Copyright (C) 2016-" + Labels.getBuildYear() + " Andrii Labyntsev et al.");

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
        manualViewer = new HTMLViewer(this, Labels.getProperty("manual"), "/MANUAL.html", false);

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
                //trying to execute system calculator
                try {
                    Runtime.getRuntime().exec("calc");
                } catch (IOException e) {
                    Logger.warnEvent(e);
                    calculatorDialog.setVisible(true);
                }
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
            public void changeRegistrationsNumber() {
                changeNumberOfRegistrations();
            }

            @Override
            public void showManual() {
                manualViewer.setVisible(true);
            }

            @Override
            public void visitUpdatesSite() {
                if (listener != null) {
                    listener.visitUpdatesSite();
                }
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

    private void changeNumberOfRegistrations() {
        String regNumberStr = (String) JOptionPane.showInputDialog(
                this,
                Labels.withColon("enterNumberOfRegistrions"),
                Labels.getProperty("changeRegistrationsNumber"),
                JOptionPane.PLAIN_MESSAGE,
                Icons.QUESTION,
                null,
                null);
        if (regNumberStr == null) return;
        try {
            int regNumber = Integer.parseInt(regNumberStr);
            regNumber = regNumber >= 0 ? regNumber : 0;
            if (listener != null) {
                listener.setNumberOfRegistrations(regNumber);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showConfirmDialog(this,
                    Labels.getProperty("wrongIntegerFormat"),
                    Labels.getProperty("error"),
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.ERROR_MESSAGE,
                    Icons.ERROR);
            changeNumberOfRegistrations();
        }

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
                if (report != null) {
                    reportList = report.getList();
                    name = report.getName();
                }
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

    public void setBidsListPanelSelectedModel(FinanceDepartmentModel model) {
        bidsListPanel.setSelectedModel(model);
        tabPane.setSelectedComponent(bidsListPanel);
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
    public void writeStatusPanelCurrentDb(String dbName) {
        statusPanel.setCurrentDb(dbName);
    }

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
            setTitle(Labels.withSpaceAfter("mainFrameSuper") + Labels.getVersion());
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
