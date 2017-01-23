package gui.finance;

import gui.CrEdDelButtons;
import gui.Labels;
import gui.MainFrame;
import gui.Utils;
import model.models.FinanceDepartmentModel;
import model.models.FinanceModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel displays data about current finances and its relation to departments.
 */
public class FinancePanel extends JPanel {

    private final FinanceModel emptyFinanceModel = new FinanceModel();
    private final FinanceDepartmentModel emptyFinanceDepartmentModel = new FinanceDepartmentModel();
    private final List<FinanceDepartmentModel> emptyDepartmentFinancesList = new ArrayList<>();

    private JButton createOrderButton;
    private JButton editOrderButton;
    private JButton deleteOrderButton;
    private JTable financeTable;
    private FinanceTableModel financeTableModel;

    private JButton createDepOrderButton;
    private JButton editDepOrderButton;
    private JButton deleteDepOrderButton;
    private JTable depFinanceTable;
    private DepartmentFinanceTableModel departmentFinanceTableModel;
    private FinancePanelListener listener;

    private FinanceModel selectedFinanceModel;
    private FinanceDepartmentModel selectedDepFinModel;

    public FinancePanel(MainFrame parent) {

        selectedFinanceModel = emptyFinanceModel;

        CrEdDelButtons cedFinance = new CrEdDelButtons(Labels.getProperty("finance_ced"));

        createOrderButton = cedFinance.getCreateButton();
        editOrderButton = cedFinance.getEditButton();
        deleteOrderButton = cedFinance.getDeleteButton();

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
                    selectedFinanceModel = (FinanceModel) financeTable.getValueAt(row, 0);
                    setDepartmentFinanceTableData(selectedFinanceModel.getFinanceDepartmentModels());
                    if (!selectedFinanceModel.equals(emptyFinanceModel)) {
                        editOrderButton.setEnabled(true);
                        deleteOrderButton.setEnabled(true);
                        createDepOrderButton.setEnabled(true);
                        editDepOrderButton.setEnabled(false);
                        deleteDepOrderButton.setEnabled(false);
                    }
                }
            }
        });

        CrEdDelButtons cedDepartmentFinances = new CrEdDelButtons(Labels.getProperty("dep_order_ced"));
        createDepOrderButton = cedDepartmentFinances.getCreateButton();
        editDepOrderButton = cedDepartmentFinances.getEditButton();
        deleteDepOrderButton = cedDepartmentFinances.getDeleteButton();

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
                    if (!selectedDepFinModel.equals(emptyFinanceDepartmentModel)) {
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

        createOrderButton.addActionListener(e -> {
            parent.getCreateFinanceDialog().setVisible(true);
        });

        editOrderButton.addActionListener(e -> {
            if (!selectedFinanceModel.equals(emptyFinanceModel)) {
                parent.getCreateFinanceDialog().setFinanceModel(selectedFinanceModel);
            }
        });

        deleteOrderButton.addActionListener(e -> {
            if (!selectedFinanceModel.equals(emptyFinanceModel) && cedFinance.deleteEntry(parent, selectedFinanceModel.getFinanceName()) && listener != null) {
                selectedFinanceModel.setDeleted();
                listener.persistModelEventOccurred(selectedFinanceModel);
                setDepartmentFinanceTableData(emptyDepartmentFinancesList);
                selectedFinanceModel = emptyFinanceModel;
            }
        });

        parent.getCreateFinanceDialog().setFinanceDialogListener(model -> {
            if (listener != null) {
                listener.persistModelEventOccurred(model);
            }
        });

        parent.getCreateDepartmentFinancesDialog().setListener(new FinanceDepartmentDialogListener() {
            @Override
            public void persistModelEventOccurred(FinanceModel model) {
                int selectedFinanceRow = financeTable.getSelectedRow();
                if (listener != null) {
                    listener.persistModelEventOccurred(model);
                }
                financeTable.getSelectionModel().setSelectionInterval(selectedFinanceRow, selectedFinanceRow);
                selectedFinanceModel = (FinanceModel) financeTable.getValueAt(selectedFinanceRow, 0);
                setDepartmentFinanceTableData(selectedFinanceModel.getFinanceDepartmentModels());
            }


            @Override
            public void loadDepartments() {
                if (listener != null) {
                    listener.loadDepartments();
                }
            }
        });

        createDepOrderButton.addActionListener(e -> {
            if (selectedFinanceModel.equals(emptyFinanceModel)) {
                Utils.emptyFieldError(parent, Labels.getProperty("finance"));
            } else {
                parent.getCreateDepartmentFinancesDialog().setVisible(selectedFinanceModel, true);
            }
        });

        editDepOrderButton.addActionListener(e -> {
            if (selectedDepFinModel.equals(emptyFinanceDepartmentModel)) {
                parent.getCreateDepartmentFinancesDialog().setVisible(selectedFinanceModel, true);
            } else {
                parent.getCreateDepartmentFinancesDialog().editDepartmentModel(selectedDepFinModel);
                selectedDepFinModel = emptyFinanceDepartmentModel;
            }
        });

        deleteDepOrderButton.addActionListener(e -> {
            if (!selectedDepFinModel.equals(emptyFinanceDepartmentModel) && cedDepartmentFinances.deleteEntry(parent, selectedDepFinModel.getFinances().getFinanceName() + "' " + Labels.getProperty("for_department") + " '" + selectedDepFinModel.getSubdepartment().getDepartment().getDepName()) && listener != null) {
                selectedDepFinModel.setDeleted();
                selectedFinanceModel.addFinanceDepartmentModel(selectedDepFinModel);
                listener.persistModelEventOccurred(selectedDepFinModel);
                setDepartmentFinanceTableData(selectedFinanceModel.getFinanceDepartmentModels());
                selectedDepFinModel = emptyFinanceDepartmentModel;
            }
        });
    }

    public void setFinanceTableData(List<FinanceModel> db) {
        //removing all inactive items from list
        List<FinanceModel> activeList = new ArrayList<>();
        for (FinanceModel model : db) {
            if (model.isActive()) {
                activeList.add(model);
            }
        }
        financeTableModel.setData(activeList);
        refreshFinanceTable();
    }

    private void refreshFinanceTable() {
        financeTableModel.fireTableDataChanged();
    }

    public void setDepartmentFinanceTableData(List<FinanceDepartmentModel> db) {
        //removing all inactive items from list
        List<FinanceDepartmentModel> activeList = new ArrayList<>();
        for (FinanceDepartmentModel model : db) {
            if (model.isActive()) {
                activeList.add(model);
            }
        }
        departmentFinanceTableModel.setData(activeList);
        refreshDepartmentFinanceTable();
    }

    private void refreshDepartmentFinanceTable() {
        departmentFinanceTableModel.fireTableDataChanged();
    }

    public void setFinancePanelListener(FinancePanelListener listener) {
        this.listener = listener;
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

        financePanel.add(addOrderButtonPanel, BorderLayout.NORTH);
        financePanel.add(new JScrollPane(financeTable), BorderLayout.CENTER);

        JPanel addDepOrderButtonPanel = new JPanel();
        addDepOrderButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        addDepOrderButtonPanel.add(createDepOrderButton);
        addDepOrderButtonPanel.add(editDepOrderButton);
        addDepOrderButtonPanel.add(deleteDepOrderButton);

        depFinancePanel.add(addDepOrderButtonPanel, BorderLayout.NORTH);
        depFinancePanel.add(new JScrollPane(depFinanceTable), BorderLayout.CENTER);

        setLayout(new BorderLayout());
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, financePanel, depFinancePanel);
        splitPane.setResizeWeight(0.65);
        splitPane.setEnabled(false);
        add(splitPane, BorderLayout.CENTER);
    }
}
