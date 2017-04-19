package gui.finance;

import gui.MainFrame;
import gui.Utils;
import gui.commons.Icons;
import gui.commons.Labels;
import gui.components.CEDButtons;
import gui.components.PJOptionPane;
import model.dao.LoginData;
import model.models.EmptyModel;
import model.models.FinanceDepartmentModel;
import model.models.FinanceModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

/**
 * Panel displays data about current finances and its relation to departments.
 */
public class FinancePanel extends JPanel {

    private final List<FinanceDepartmentModel> emptyDepartmentFinancesList = new LinkedList<>();

    private JButton createOrderButton;
    private JButton editOrderButton;
    private JButton deleteOrderButton;
    private JButton refreshOrderButton;
    private JTable financeTable;
    private FinanceTableModel financeTableModel;

    private JButton createDepOrderButton;
    private JButton editDepOrderButton;
    private JButton deleteDepOrderButton;
    private JButton refreshDepOrderButton;
    private JTable depFinanceTable;
    private DepartmentFinanceTableModel departmentFinanceTableModel;
    private FinancePanelListener listener;

    private FinanceModel selectedFinanceModel;
    private FinanceDepartmentModel selectedDepFinModel;
    private CreateFinancePanel createFinancePanel;
    private CreateDepartmentFinancePanel createDepartmentFinancePanel;
    private JSplitPane financeSplitPane;
    private JSplitPane departmentFinanceSplitPane;
    private boolean useUserDepartment;

    public FinancePanel(MainFrame parent) {
        createFinancePanel = new CreateFinancePanel(parent);
        createFinancePanel.setVisible(false);

        createDepartmentFinancePanel = new CreateDepartmentFinancePanel(parent);
        createDepartmentFinancePanel.setVisible(false);

        selectedFinanceModel = EmptyModel.FINANCE;
        useUserDepartment = false;

        CEDButtons cedFinance = new CEDButtons(Labels.getProperty("finance_ced"));

        createOrderButton = cedFinance.getCreateButton();
        editOrderButton = cedFinance.getEditButton();
        deleteOrderButton = cedFinance.getDeleteButton();
        refreshOrderButton = new JButton(Icons.REFRESH);
        refreshOrderButton.setPreferredSize(new Dimension(25, 25));
        refreshOrderButton.setToolTipText(Labels.getProperty("refreshOrders"));

        editOrderButton.setEnabled(false);
        deleteOrderButton.setEnabled(false);

        financeTableModel = new FinanceTableModel();
        financeTable = new JTable(financeTableModel);
        financeTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ev) {
                int row = financeTable.rowAtPoint(ev.getPoint());
                financeTable.getSelectionModel().setSelectionInterval(row, row);

                if (ev.getButton() == MouseEvent.BUTTON1) {
                    selectedFinanceModel = (FinanceModel) financeTableModel.getValueAt(row, 9);
                    setDepartmentFinanceTableData(selectedFinanceModel.getFinanceDepartmentModelsSorted());
                    if (!selectedFinanceModel.equals(EmptyModel.FINANCE)) {
                        editOrderButton.setEnabled(true);
                        deleteOrderButton.setEnabled(true);
                        createDepOrderButton.setEnabled(true);
                        editDepOrderButton.setEnabled(false);
                        deleteDepOrderButton.setEnabled(false);
                    }
                }
            }
        });

        CEDButtons cedDepartmentFinances = new CEDButtons(Labels.getProperty("dep_order_ced"));
        createDepOrderButton = cedDepartmentFinances.getCreateButton();
        editDepOrderButton = cedDepartmentFinances.getEditButton();
        deleteDepOrderButton = cedDepartmentFinances.getDeleteButton();
        refreshDepOrderButton = new JButton(Icons.REFRESH);
        refreshDepOrderButton.setPreferredSize(new Dimension(25, 25));
        refreshDepOrderButton.setToolTipText(Labels.getProperty("refreshDepOrders"));

        createDepOrderButton.setEnabled(false);
        editDepOrderButton.setEnabled(false);
        deleteDepOrderButton.setEnabled(false);

        departmentFinanceTableModel = new DepartmentFinanceTableModel();
        depFinanceTable = new JTable(departmentFinanceTableModel);
        depFinanceTable.getColumnModel().getColumn(0).setMinWidth(150);
        depFinanceTable.getColumnModel().getColumn(1).setMinWidth(100);
        depFinanceTable.getColumnModel().removeColumn(depFinanceTable.getColumnModel().getColumn(8));
        depFinanceTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ev) {
                int row = depFinanceTable.rowAtPoint(ev.getPoint());
                depFinanceTable.getSelectionModel().setSelectionInterval(row, row);

                if (ev.getButton() == MouseEvent.BUTTON1) {
                    selectedDepFinModel = (FinanceDepartmentModel) depFinanceTable.getModel().getValueAt(row, 8);
                    if (!selectedDepFinModel.equals(EmptyModel.FINANCE_DEPARTMENT)) {
                        editOrderButton.setEnabled(true);
                        deleteOrderButton.setEnabled(true);
                        createDepOrderButton.setEnabled(true);
                        editDepOrderButton.setEnabled(true);
                        deleteDepOrderButton.setEnabled(true);
                    }
                }
            }
        });

        createLayout();

        int finDividerLocation = 310;

        createOrderButton.addActionListener(e -> {
            createFinancePanel.clear();
            createFinancePanel.setVisible(true);
            financeSplitPane.setDividerLocation(finDividerLocation);
        });

        editOrderButton.addActionListener(e -> {
            if (!selectedFinanceModel.equals(EmptyModel.FINANCE)) {
                createFinancePanel.clear();
                createFinancePanel.setFinanceModel(selectedFinanceModel);
                financeSplitPane.setDividerLocation(finDividerLocation);
            }
        });

        deleteOrderButton.addActionListener(e -> {
            if (!selectedFinanceModel.equals(EmptyModel.FINANCE) && cedFinance.deleteEntry(parent, selectedFinanceModel.getFinanceName()) && listener != null) {
                selectedFinanceModel.setDeleted();
                listener.persistModelEventOccurred(selectedFinanceModel);
                setDepartmentFinanceTableData(emptyDepartmentFinancesList);
                selectedFinanceModel = EmptyModel.FINANCE;
            }
        });

        refreshOrderButton.addActionListener(e -> {
            for (int row = 0; row < financeTableModel.getRowCount(); row++) {
                ((FinanceModel) financeTableModel.getValueAt(row, 9)).calculateLeftAmount();
            }
            financeTableModel.fireTableDataChanged();
        });

        createFinancePanel.setFinanceDialogListener(model -> {
            if (listener != null) {
                listener.persistModelEventOccurred(model);
            }
        });

        createDepartmentFinancePanel.setListener(new CreateDepartmentFinancePanelListener() {
            @Override
            public void persistModelEventOccurred(FinanceDepartmentModel model) {
                int selectedFinanceRow = Utils.getRowWithObject(financeTable, 0, selectedFinanceModel);
                if (listener != null) {
                    listener.persistModelEventOccurred(model);
                }
                financeTable.getSelectionModel().setSelectionInterval(selectedFinanceRow, selectedFinanceRow);
                selectedFinanceModel = (FinanceModel) financeTable.getValueAt(selectedFinanceRow, 0);
                setDepartmentFinanceTableData(selectedFinanceModel.getFinanceDepartmentModelsSorted());
            }


            @Override
            public void loadDepartments() {
                if (listener != null) {
                    listener.loadDepartments();
                }
            }
        });

        int depDividerLocation = 350;

        createDepOrderButton.addActionListener(e -> {
            if (selectedFinanceModel.equals(EmptyModel.FINANCE)) {
                PJOptionPane.emptyField(parent, Labels.getProperty("finance"));
            } else {
                createDepartmentFinancePanel.clear();
                createDepartmentFinancePanel.setVisible(selectedFinanceModel, true);
                departmentFinanceSplitPane.setDividerLocation(depDividerLocation);
            }
        });

        editDepOrderButton.addActionListener(e -> {
            if (!selectedDepFinModel.equals(EmptyModel.FINANCE_DEPARTMENT)) {
                createDepartmentFinancePanel.clear();
                createDepartmentFinancePanel.editDepartmentModel(selectedDepFinModel);
                departmentFinanceSplitPane.setDividerLocation(depDividerLocation);
                selectedDepFinModel = EmptyModel.FINANCE_DEPARTMENT;
            }
        });

        deleteDepOrderButton.addActionListener(e -> {
            if (!selectedDepFinModel.equals(EmptyModel.FINANCE_DEPARTMENT) && cedDepartmentFinances.deleteEntry(parent, selectedDepFinModel.getFinances().getFinanceName() + "' " + Labels.getProperty("for_department") + " '" + selectedDepFinModel.getSubdepartment().getDepartment().getDepName()) && listener != null) {
                selectedDepFinModel.setDeleted();
                selectedFinanceModel.addFinanceDepartmentModel(selectedDepFinModel);
                listener.persistModelEventOccurred(selectedDepFinModel);
                setDepartmentFinanceTableData(selectedFinanceModel.getFinanceDepartmentModelsSorted());
                selectedDepFinModel = EmptyModel.FINANCE_DEPARTMENT;
            }
        });

        refreshDepOrderButton.addActionListener(e -> {
            for (int row = 0; row < departmentFinanceTableModel.getRowCount(); row++) {
                ((FinanceDepartmentModel) departmentFinanceTableModel.getValueAt(row, 8)).calculateLeftAmount();
            }
            departmentFinanceTableModel.fireTableDataChanged();
        });
    }

    public void setFinanceTableData(List<FinanceModel> db) {
        //removing all inactive items from list
        List<FinanceModel> activeList = new LinkedList<>();
        for (FinanceModel model : db) {
            if (model.isActive()) {
                activeList.add(model);
            }
        }
        financeTableModel.setData(activeList);
        financeTable.setAutoCreateRowSorter(true);
        financeTableModel.fireTableDataChanged();
    }

    public void setDepartmentFinanceTableData(List<FinanceDepartmentModel> db) {
        //removing all inactive items from list
        List<FinanceDepartmentModel> activeList = new LinkedList<>();
        for (FinanceDepartmentModel model : db) {
            if (model.isActive()) {
                if (!useUserDepartment || (useUserDepartment && model.getSubdepartment().getDepartment().equals(LoginData.getInstance().getSubdepartment().getDepartment()))) {
                    activeList.add(model);
                }
            }
        }
        departmentFinanceTableModel.setData(activeList);
        depFinanceTable.setAutoCreateRowSorter(true);
        departmentFinanceTableModel.fireTableDataChanged();
    }

    public void setFinancePanelListener(FinancePanelListener listener) {
        this.listener = listener;
    }

    public void hideCed() {
        createOrderButton.setVisible(false);
        editOrderButton.setVisible(false);
        deleteOrderButton.setVisible(false);
        createDepOrderButton.setVisible(false);
        editDepOrderButton.setVisible(false);
        deleteDepOrderButton.setVisible(false);
    }


    public void setUseUserDepartment() {
        useUserDepartment = true;
        if (listener != null) {
            listener.getFinancesByDepartment(LoginData.getInstance().getSubdepartment().getDepartment());
        }
    }

    public CreateDepartmentFinancePanel getCreateDepartmentFinancePanel() {
        return createDepartmentFinancePanel;
    }

    private void createLayout() {
        JPanel financePanel = new JPanel();
        JPanel depFinancePanel = new JPanel();
        financePanel.setLayout(new BorderLayout());
        depFinancePanel.setLayout(new BorderLayout());

        JPanel addOrderButtonPanel = new JPanel();
        addOrderButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        addOrderButtonPanel.add(createOrderButton);
        addOrderButtonPanel.add(editOrderButton);
        addOrderButtonPanel.add(deleteOrderButton);
        addOrderButtonPanel.add(refreshOrderButton);

        financePanel.add(addOrderButtonPanel, BorderLayout.NORTH);
        financePanel.add(new JScrollPane(financeTable), BorderLayout.CENTER);

        JPanel addDepOrderButtonPanel = new JPanel();
        addDepOrderButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        addDepOrderButtonPanel.add(createDepOrderButton);
        addDepOrderButtonPanel.add(editDepOrderButton);
        addDepOrderButtonPanel.add(deleteDepOrderButton);
        addDepOrderButtonPanel.add(refreshDepOrderButton);

        depFinancePanel.add(addDepOrderButtonPanel, BorderLayout.NORTH);
        depFinancePanel.add(new JScrollPane(depFinanceTable), BorderLayout.CENTER);

        financeSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, createFinancePanel, financePanel);
        financeSplitPane.setEnabled(false);

        departmentFinanceSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, createDepartmentFinancePanel, depFinancePanel);
        departmentFinanceSplitPane.setEnabled(false);

        setLayout(new BorderLayout());
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, financeSplitPane, departmentFinanceSplitPane);
        splitPane.setEnabled(false);
        add(splitPane, BorderLayout.CENTER);
    }

    public FinanceModel getSelectedFinances() {
        return selectedFinanceModel;
    }
}
