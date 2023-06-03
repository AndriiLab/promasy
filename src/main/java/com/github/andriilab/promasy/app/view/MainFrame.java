/*
  This class makes a base for interface of Promasy. It instantiates all parent frames
  and dialog boxes.
 */

package com.github.andriilab.promasy.app.view;

import com.github.andriilab.promasy.app.controller.Logger;
import com.github.andriilab.promasy.data.authorization.LoginData;
import com.github.andriilab.promasy.app.controller.TableGenerator;
import com.github.andriilab.promasy.data.storage.ConnectionSettings;
import com.github.andriilab.promasy.domain.bid.entities.AmountUnit;
import com.github.andriilab.promasy.domain.bid.entities.Bid;
import com.github.andriilab.promasy.domain.bid.entities.CpvAmount;
import com.github.andriilab.promasy.domain.bid.entities.ReasonForSupplierChoice;
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
import com.github.andriilab.promasy.app.view.amunits.AmUnitsDialog;
import com.github.andriilab.promasy.app.view.amunits.AmUnitsDialogListener;
import com.github.andriilab.promasy.app.view.bids.BidsListPanel;
import com.github.andriilab.promasy.app.view.bids.BidsListPanelListener;
import com.github.andriilab.promasy.app.view.bids.CreateBidPanel;
import com.github.andriilab.promasy.app.commons.Colors;
import com.github.andriilab.promasy.app.commons.Icons;
import com.github.andriilab.promasy.app.commons.Labels;
import com.github.andriilab.promasy.app.components.DrawSplashScreen;
import com.github.andriilab.promasy.app.components.MainFrameMenuBar;
import com.github.andriilab.promasy.app.components.MenuBarListener;
import com.github.andriilab.promasy.app.components.StatusPanel;
import com.github.andriilab.promasy.app.components.dialogs.*;
import com.github.andriilab.promasy.app.components.toolbars.ButtonsToolbar;
import com.github.andriilab.promasy.app.components.toolbars.ButtonsToolbarListener;
import com.github.andriilab.promasy.app.components.toolbars.ControlsToolbar;
import com.github.andriilab.promasy.app.view.conset.ConSetDialog;
import com.github.andriilab.promasy.app.view.conset.ConSetListener;
import com.github.andriilab.promasy.app.view.cpv.CpvDialog;
import com.github.andriilab.promasy.app.view.cpv.CpvSearchListener;
import com.github.andriilab.promasy.app.view.employee.CreateEmployeeDialog;
import com.github.andriilab.promasy.app.view.employee.CreateEmployeeDialogListener;
import com.github.andriilab.promasy.app.view.employee.EditEmployeeDialog;
import com.github.andriilab.promasy.app.view.employee.EditEmployeeDialogListener;
import com.github.andriilab.promasy.app.view.finance.FinancePanel;
import com.github.andriilab.promasy.app.view.finance.FinancePanelListener;
import com.github.andriilab.promasy.app.view.login.LoginListener;
import com.github.andriilab.promasy.app.view.login.LoginPanel;
import com.github.andriilab.promasy.app.view.organization.OrganizationDialog;
import com.github.andriilab.promasy.app.view.organization.OrganizationDialogListener;
import com.github.andriilab.promasy.app.view.producer.ProducerDialog;
import com.github.andriilab.promasy.app.view.producer.ProducerDialogListener;
import com.github.andriilab.promasy.app.view.producer.ReasonsDialog;
import com.github.andriilab.promasy.app.view.producer.ReasonsDialogListener;
import com.github.andriilab.promasy.app.view.reports.bids.ReportParametersDialog;
import com.github.andriilab.promasy.app.view.reports.bids.ReportParametersDialogListener;
import com.github.andriilab.promasy.app.view.reports.cpv.CpvAmountDialog;
import com.github.andriilab.promasy.app.view.reports.cpv.CpvAmountDialogListener;
import com.github.andriilab.promasy.app.view.supplier.SupplierDialog;
import com.github.andriilab.promasy.app.view.supplier.SupplierDialogListener;
import jiconfont.icons.font_awesome.FontAwesome;
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
    private final ButtonsToolbar buttonsToolbar;
    private final ControlsToolbar controlsToolbar;
    private MainFrameMenuBar mainFrameMenuBar;
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
    private final HtmlViewerDialog manualViewer;
    private final FileSavedDialog fileSavedDialog;

    public MainFrame() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        System.out.println("ProMasy - Procurement Management System version " + Labels.getVersion() + "\n" +
                "Copyright (C) 2016-" + Labels.getBuildYear() + " Andrii Labyntsev et al.");

        splashScreen = new DrawSplashScreen();
        splashScreen.start();

        // registering font for icons
        IconFontSwing.register(FontAwesome.getIconFont());

        //initializing buttonsToolbar, login and connection settings windows and other common windows
        listener = new EmptyMainFrameListener();
        buttonsToolbar = new ButtonsToolbar();
        controlsToolbar = new ControlsToolbar();
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
        manualViewer = new HtmlViewerDialog(this, Labels.getProperty("manual"), "/MANUAL.html", false);
        fileSavedDialog = new FileSavedDialog(this, true);

        setLayout(new BorderLayout());
    }

    public void initialize() {
        //removing login panel
        setVisible(false);
        remove(loginPanel);
        //marking login panel for gc
        loginPanel = null;

        JPanel toolbarsPanel = new JPanel(new BorderLayout());
        toolbarsPanel.add(buttonsToolbar, BorderLayout.CENTER);
        toolbarsPanel.add(controlsToolbar, BorderLayout.LINE_END);

        //adding components of main window
        add(toolbarsPanel, BorderLayout.PAGE_START);
        add(statusPanel, BorderLayout.SOUTH);

        Role role = LoginData.getInstance().getRole();

        //creating and setting menu bar
        mainFrameMenuBar = new MainFrameMenuBar(role);
        setJMenuBar(mainFrameMenuBar);

        // init main pane and menu bar according to user roles
        switch (role) {
            case ADMIN:
            case DIRECTOR:
            case DEPUTY_DIRECTOR:
            case ACCOUNTANT:
            case ECONOMIST:
                createTabPane();
                break;
            case HEAD_OF_TENDER_COMMITTEE:
            case SECRETARY_OF_TENDER_COMMITTEE:
                createTabPane();
                financePanel.setVisibleCed(false);
                break;
            case HEAD_OF_DEPARTMENT:
            case PERSONALLY_LIABLE_EMPLOYEE:
                useUserDepartment();
                createTabPane();
                financePanel.setVisibleCed(false);
                break;
            case USER:
            default:
                useUserDepartment();
                add(bidsListPanel, BorderLayout.CENTER);
                financePanel.setVisibleCed(false);
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

        buttonsToolbar.setButtonsToolbarListener(new ButtonsToolbarListener() {
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
                displayCalculator();
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

        controlsToolbar.setListener(this::updateYearInVisibleComponent);

        mainFrameMenuBar.setMenuBarListener(new MenuBarListener() {
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
                listener.visitUpdatesSite();
            }

            @Override
            public void showCalculator() {
                displayCalculator();
            }

            @Override
            public void showCpvSelectorDialog() {
                cpvDialog.setVisible(true);
            }

            @Override
            public void exitAction() {
                listener.exitEventOccurred();
            }

            @Override
            public void setCurrentVersionAsMinimum() {
                int action = JOptionPane.showConfirmDialog(MainFrame.this,
                        Labels.getProperty("setMinimumVersionLong") + " " + Labels.getVersion() + "?",
                        Labels.getProperty("confirmAction"),
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, Icons.QUESTION);
                if (action == JOptionPane.OK_OPTION) {
                    listener.setMinimumVersionEventOccurred();
                }
            }
        });
    }

    private void updateYearInVisibleComponent() {
        if (tabPane != null)
            financePanel.refreshYear();

        bidsListPanel.refreshYear();
    }

    private void displayCalculator() {
        //trying to execute system calculator
        try {
            Runtime.getRuntime().exec("calc");
        } catch (IOException e) {
            Logger.warnEvent(this.getClass(), e);
            calculatorDialog.setVisible(true);
        }
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
            listener.setNumberOfRegistrations(regNumber);
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
        java.util.List<Bid> reportList = null;
        String name = "";
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
    public void setDefaultConnectionSettings(ConnectionSettings model) {
        conSettDialog.setDefaults(model);
    }

    public void setBidsListPanelSelectedModel(FinanceDepartment model) {
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
        if (bidsListPanel.isReadyForPrint())
            reportParametersDialog.setVisible(true);
    }

    public void showFileSavedDialog(String file) {
        fileSavedDialog.setVisible(file);
    }

    public void bidListPrint(Map<String, Object> parameters) {
        bidsListPanel.printBidList(parameters);
    }

    // Status panel writers
    public void writeStatusPanelCurrentDb(String dbName) {
        statusPanel.setCurrentDb(dbName);
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
        loginPanel.setListener(loginListener);
    }

    public void setConSetListener(ConSetListener listener) {
        conSettDialog.setConSetListener(listener);
    }

    public void setCpvListener(CpvSearchListener listener) {
        cpvDialog.setListener(listener);
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

    //IEntity lists
    public void setCpvModelList(java.util.List<Cpv> cpvModelList) {
        cpvDialog.setData(cpvModelList);
    }

    public void setAmountUnitsModelList(java.util.List<AmountUnit> amountUnitsModelList) {
        amUnitsDialog.setData(amountUnitsModelList);
        bidsListPanel.getCreateBidPanel().setAmUnitsBoxData(amountUnitsModelList);
    }

    public void setInstituteModelList(List<Institute> instituteModelList) {
        editOrgDialog.setInstData(instituteModelList);
        createEmployeeDialog.setInstData(instituteModelList);
    }

    public void setDepartmentModelList(List<Department> departmentsList) {
        financePanel.getCreateDepartmentFinancePanel().setDepartmentBoxData(departmentsList);
        bidsListPanel.setDepartmentBoxData(departmentsList);
        createEmployeeDialog.setDepData(departmentsList);
        editOrgDialog.setDepData(departmentsList);
    }

    public void setSubdepartmentModelList(List<Subdepartment> subdepartmentModelList) {
        createEmployeeDialog.setSubdepData(subdepartmentModelList);
        financePanel.getCreateDepartmentFinancePanel().setSubdepartmentBoxData(subdepartmentModelList);
        editOrgDialog.setSubdepData(subdepartmentModelList);
    }

    public void setEmployeeModelList(List<Employee> employeeModelList) {
        editEmpDialog.setEmpTableData(employeeModelList);
    }

    public void setProducerModelList(List<Producer> producerModelList) {
        producerDialog.setData(producerModelList);
        bidsListPanel.getCreateBidPanel().setProducerBoxData(producerModelList);
    }

    public void setSupplierModelList(List<Supplier> supplierModelList) {
        supplierDialog.setData(supplierModelList);
        bidsListPanel.getCreateBidPanel().setSupplierBoxData(supplierModelList);
    }

    public void setReasonsModelList(List<ReasonForSupplierChoice> reasonsModelList) {
        bidsListPanel.getCreateBidPanel().setReasonForSupplierChoiceBoxData(reasonsModelList);
        reasonsDialog.setData(reasonsModelList);
    }

    public void setFinanceModelList(List<Finance> financeModelList) {
        financePanel.setFinanceTableData(financeModelList);
    }

    public void setFinanceDepartmentModelList(List<FinanceDepartment> financeDepartmentModelList) {
        financePanel.setDepartmentFinanceTableData(financeDepartmentModelList);
        bidsListPanel.setFinanceDepartmentBoxData(financeDepartmentModelList);
        bidsListPanel.getCreateBidPanel().setFinanceDepartmentBoxData(financeDepartmentModelList);
    }

    public void setCpvAmountDialogList(List<CpvAmount> cpvAmounts) {
        cpvAmountDialog.setTableData(cpvAmounts);
    }

    public void setBidModelList(List<Bid> bidModelList) {
        bidsListPanel.setBidsTableData(bidModelList);
    }

    public void setCpvModel(Cpv cpvModel) {
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

    public BidsListPanel getBidsListPanel() {
        return bidsListPanel;
    }

    public void saveLog() {
        loggerDialog.saveLog();
    }

    public int getReportYear() {
        return controlsToolbar.getSelectedYear();
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            String user = LoginData.getInstance().getShortName() + " (" + LoginData.getInstance().getRole().getRoleName()
                    + ")";
            Logger.infoEvent(this.getClass(), this, Labels.withColon("role.user") + user);
            setTitle(user + " - " + Labels.withSpaceAfter("mainFrameSuper") + Labels.getVersion());
            setMinimumSize(new Dimension(1000, 700));
            setSize(1000, 700);
            setResizable(true);
            positionOnScreenCenter();
            listener.getAllDepartments(LoginData.getInstance().getSubdepartment().getDepartment().getInstitute());
            bidsListPanel.getBids();
        }
        super.setVisible(visible);
    }

    public Cpv validateCpv(String cpvCode) {
        return listener.validateCpv(cpvCode);
    }
}
