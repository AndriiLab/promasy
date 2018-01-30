package com.github.andriilab.promasy.presentation.finance;

import com.github.andriilab.promasy.data.commands.CreateOrUpdateCommand;
import com.github.andriilab.promasy.data.controller.LoginData;
import com.github.andriilab.promasy.data.queries.finance.GetFinanceUnassignedAmountQuery;
import com.github.andriilab.promasy.data.queries.finance.GetFinancesQuery;
import com.github.andriilab.promasy.domain.EmptyModel;
import com.github.andriilab.promasy.domain.bid.entities.Bid;
import com.github.andriilab.promasy.domain.finance.entities.Finance;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;
import com.github.andriilab.promasy.domain.finance.enums.Fund;
import com.github.andriilab.promasy.presentation.MainFrame;
import com.github.andriilab.promasy.presentation.Utils;
import com.github.andriilab.promasy.presentation.commons.Icons;
import com.github.andriilab.promasy.presentation.commons.Labels;
import com.github.andriilab.promasy.presentation.components.CEDButtons;
import com.github.andriilab.promasy.presentation.components.PJOptionPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * Panel displays data about current finances and its relation to departments.
 */
public class FinancePanel extends JPanel {

    private final List<FinanceDepartment> emptyDepartmentFinancesList = new LinkedList<>();
    private final MainFrame parent;
    private final JButton createOrderButton;
    private final JButton editOrderButton;
    private final JButton deleteOrderButton;
    private final JTable financeTable;
    private final FinanceTableModel financeTableModel;
    private JButton createDepOrderButton;
    private JButton editDepOrderButton;
    private JButton deleteDepOrderButton;
    private JButton showBidsButton;
    private final JTable depFinanceTable;
    private final DepartmentFinanceTableModel departmentFinanceTableModel;
    private FinancePanelListener listener;

    private Finance selectedFinanceModel;
    private FinanceDepartment selectedDepFinModel;
    private final CreateFinancePanel createFinancePanel;
    private final CreateDepartmentFinancePanel createDepartmentFinancePanel;
    private JSplitPane financeSplitPane;
    private JSplitPane departmentFinanceSplitPane;
    private boolean useUserDepartment;

    public FinancePanel(MainFrame parent) {
        this.parent = parent;
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

        editOrderButton.setEnabled(false);
        deleteOrderButton.setEnabled(false);

        financeTableModel = new FinanceTableModel();
        financeTableModel.setListener(query -> listener.getLeftAmountEvent(query));

        financeTable = new JTable(financeTableModel);
        financeTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ev) {
                int row = financeTable.rowAtPoint(ev.getPoint());
                financeTable.getSelectionModel().setSelectionInterval(row, row);

                if (ev.getButton() == MouseEvent.BUTTON1) {
                    selectedFinanceModel = (Finance) financeTableModel.getValueAt(row, 9);
                    setDepartmentFinanceTableData(selectedFinanceModel.getFinanceDepartmentModelsSorted());
                    if (!selectedFinanceModel.equals(EmptyModel.FINANCE)) {
                        editOrderButton.setEnabled(true);
                        deleteOrderButton.setEnabled(true);
                        createDepOrderButton.setEnabled(true);
                        editDepOrderButton.setEnabled(false);
                        deleteDepOrderButton.setEnabled(false);
                        showBidsButton.setEnabled(false);
                    }
                }
            }
        });

        CEDButtons cedDepartmentFinances = new CEDButtons(Labels.getProperty("dep_order_ced"));
        createDepOrderButton = cedDepartmentFinances.getCreateButton();
        editDepOrderButton = cedDepartmentFinances.getEditButton();
        deleteDepOrderButton = cedDepartmentFinances.getDeleteButton();
        showBidsButton = new JButton(Icons.ARROW_RIGHT);
        showBidsButton.setToolTipText(Labels.getProperty("showAssociatedBids"));

        createDepOrderButton.setEnabled(false);
        editDepOrderButton.setEnabled(false);
        deleteDepOrderButton.setEnabled(false);
        showBidsButton.setEnabled(false);

        departmentFinanceTableModel = new DepartmentFinanceTableModel();
        departmentFinanceTableModel.setListener(query -> listener.getLeftAmountEvent(query));
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
                    selectedDepFinModel = (FinanceDepartment) depFinanceTable.getModel().getValueAt(row, 8);
                    if (!selectedDepFinModel.equals(EmptyModel.FINANCE_DEPARTMENT)) {
                        editOrderButton.setEnabled(true);
                        deleteOrderButton.setEnabled(true);
                        createDepOrderButton.setEnabled(true);
                        editDepOrderButton.setEnabled(true);
                        deleteDepOrderButton.setEnabled(true);
                        showBidsButton.setEnabled(true);
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
                listener.persistModelEventOccurred(new CreateOrUpdateCommand<>(selectedFinanceModel));
                setDepartmentFinanceTableData(emptyDepartmentFinancesList);
                selectedFinanceModel = EmptyModel.FINANCE;
            }
        });

        createFinancePanel.setFinanceDialogListener(model -> {
            if (listener != null) {
                listener.persistModelEventOccurred(new CreateOrUpdateCommand<>(model));
            }
        });

        createDepartmentFinancePanel.setListener(new CreateDepartmentFinancePanelListener() {
            @Override
            public void persistModelEventOccurred(FinanceDepartment model) {
                int selectedFinanceRow = Utils.getRowWithObject(financeTable, 0, selectedFinanceModel);
                if (listener != null) {
                    listener.persistModelEventOccurred(new CreateOrUpdateCommand<>(model));
                }
                financeTable.getSelectionModel().setSelectionInterval(selectedFinanceRow, selectedFinanceRow);
                try {
                    selectedFinanceModel = (Finance) financeTable.getValueAt(selectedFinanceRow, 0);
                } catch (IndexOutOfBoundsException ex) {
                    //won't change the selected com.github.andriilab.promasy.domain.model
                }

                setDepartmentFinanceTableData(selectedFinanceModel.getFinanceDepartmentModelsSorted());
            }


            @Override
            public void loadDepartments() {
                if (listener != null) {
                    listener.loadDepartments();
                }
            }

            @Override
            public BigDecimal getUnassignedAmountEvent(GetFinanceUnassignedAmountQuery query) {
                return listener.getUnassignedAmountEvent(query);
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
                listener.persistModelEventOccurred(new CreateOrUpdateCommand<>(selectedDepFinModel));
                setDepartmentFinanceTableData(selectedFinanceModel.getFinanceDepartmentModelsSorted());
                selectedDepFinModel = EmptyModel.FINANCE_DEPARTMENT;
            }
        });

        showBidsButton.addActionListener(e -> showAssociatedBids());
    }

    private static List<Bid> getBidsList(Finance financeModel) {
        List<Bid> bids = new LinkedList<>();
        financeModel.getFinanceDepartmentModels().forEach(model -> bids.addAll(model.getActiveBids()));
        return bids;
    }

    public void setFinanceTableData(List<Finance> db) {
        //removing all inactive items from list
        List<Finance> activeList = new LinkedList<>();
        for (Finance model : db) {
            if (model.isActive()) {
                activeList.add(model);
            }
        }
        financeTableModel.setData(activeList);
        financeTable.setAutoCreateRowSorter(true);
        financeTableModel.fireTableDataChanged();
    }

    public void setDepartmentFinanceTableData(List<FinanceDepartment> db) {
        //removing all inactive items from list
        List<FinanceDepartment> activeList = new LinkedList<>();
        for (FinanceDepartment model : db) {
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

    public void setListener(FinancePanelListener listener) {
        this.listener = listener;
    }

    public void hideCed() {
        createOrderButton.setVisible(false);
        editOrderButton.setVisible(false);
        deleteOrderButton.setVisible(false);
        createDepOrderButton.setVisible(false);
        editDepOrderButton.setVisible(false);
        deleteDepOrderButton.setVisible(false);
        showBidsButton.setVisible(false);
    }

    public void setUseUserDepartment() {
        useUserDepartment = true;
        if (listener != null) {
            listener.getFinancesByDepartment(new GetFinancesQuery(parent.getReportYear(), LoginData.getInstance().getSubdepartment().getDepartment()));
        }
    }

    public CreateDepartmentFinancePanel getCreateDepartmentFinancePanel() {
        return createDepartmentFinancePanel;
    }

    private void showAssociatedBids() {
        parent.setBidsListPanelSelectedModel(selectedDepFinModel);
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
        addDepOrderButtonPanel.add(showBidsButton);

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

    public void refresh() {
        listener.getAllData();
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible && listener != null) {
            listener.getAllData();
        }
        super.setVisible(visible);
    }

    public FinanceReport getReportsList() {
        List<String> optionsList = new LinkedList<>();
        if (selectedFinanceModel != null && !selectedFinanceModel.equals(EmptyModel.FINANCE)) {
            optionsList.add(Labels.withColon("selectedFinance") + selectedFinanceModel.toString());
        }
        optionsList.add(Labels.getProperty("fund.commonFund"));
        optionsList.add(Labels.getProperty("fund.specialFund"));

        Object[] options = optionsList.toArray();

        String reportType = (String) JOptionPane.showInputDialog(parent,
                Labels.withColon("createReportFor"),
                Labels.getProperty("exportToTableFile"),
                JOptionPane.PLAIN_MESSAGE,
                Icons.QUESTION,
                options,
                options[0]);
        if (reportType == null) {
            return null;
        } else if (reportType.startsWith(Labels.getProperty("selectedFinance"))) {
            return new FinanceReport(selectedFinanceModel.toString(), getBidsList(selectedFinanceModel));
        } else if (reportType.equals(Labels.getProperty("fund.commonFund"))) {
            List<Bid> bidModels = new LinkedList<>();
            financeTableModel.getData().forEach(financeModel -> {
                if (financeModel.getFundType().equals(Fund.COMMON_FUND)) bidModels.addAll(getBidsList(financeModel));
            });
            return new FinanceReport(reportType, bidModels);
        } else {
            List<Bid> bidModels = new LinkedList<>();
            financeTableModel.getData().forEach(financeModel -> {
                if (financeModel.getFundType().equals(Fund.SPECIAL_FUND)) bidModels.addAll(getBidsList(financeModel));
            });
            return new FinanceReport(reportType, bidModels);
        }
    }

    public class FinanceReport {
        private final String name;
        private final List<Bid> list;

        FinanceReport(String name, List<Bid> list) {
            this.name = name;
            this.list = list;
        }

        public String getName() {
            return name;
        }

        public List<Bid> getList() {
            return list;
        }
    }
}