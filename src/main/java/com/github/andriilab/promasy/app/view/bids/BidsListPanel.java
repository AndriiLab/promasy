package com.github.andriilab.promasy.app.view.bids;

import com.github.andriilab.promasy.app.commons.Icons;
import com.github.andriilab.promasy.app.commons.Labels;
import com.github.andriilab.promasy.app.components.EntityComboBox;
import com.github.andriilab.promasy.app.components.PJComboBox;
import com.github.andriilab.promasy.app.components.dialogs.CEDButtons;
import com.github.andriilab.promasy.app.controller.Logger;
import com.github.andriilab.promasy.app.view.MainFrame;
import com.github.andriilab.promasy.app.view.bids.status.StatusDialog;
import com.github.andriilab.promasy.app.view.bids.status.StatusDialogListener;
import com.github.andriilab.promasy.data.authorization.LoginData;
import com.github.andriilab.promasy.data.commands.DeleteCommand;
import com.github.andriilab.promasy.data.commands.ICommand;
import com.github.andriilab.promasy.data.queries.bids.GetBidsQuery;
import com.github.andriilab.promasy.data.queries.financepartment.GetFinanceDepartmentLeftAmountQuery;
import com.github.andriilab.promasy.data.queries.financepartment.GetFinanceDepartmentSpentAmountQuery;
import com.github.andriilab.promasy.data.queries.financepartment.GetFinanceDepartmentsQuery;
import com.github.andriilab.promasy.data.reports.ReportsGenerator;
import com.github.andriilab.promasy.data.reports.models.BidReportModel;
import com.github.andriilab.promasy.data.reports.models.BidsReportRequest;
import com.github.andriilab.promasy.domain.EmptyModel;
import com.github.andriilab.promasy.domain.IEntity;
import com.github.andriilab.promasy.domain.bid.entities.Bid;
import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;
import com.github.andriilab.promasy.domain.organization.entities.Department;
import com.github.andriilab.promasy.domain.organization.entities.Subdepartment;
import com.github.andriilab.promasy.domain.organization.enums.Role;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This panel view table of Bids and can Create, Modify, and Delete this entries in DB
 */
public class BidsListPanel extends JPanel {

    private final JButton createBidButton;
    private final JButton copyBidButton;
    private final JButton editBidButton;
    private final JButton deleteBidButton;
    private final JButton changeStatusButton;
    private final EntityComboBox<Department> departmentBox;
    private final EntityComboBox<Subdepartment> subdepartmentBox;
    private final EntityComboBox<FinanceDepartment> financeDepartmentBox;
    private final PJComboBox<BidType> bidTypeBox;
    private final BidsTableModel bidsTableModel;
    private final JTable bidsTable;
    private BidsListPanelListener listener;
    private final StatusDialog statusDialog;
    private final List<Bid> selectedBidModels;
    private Bid selectedBidModel;
    private final JLabel sumLabel;
    private final JLabel financeLeftLabel;
    private FinanceDepartment selectedFinanceDepartmentModel;
    private Department selectedDepartmentModel;
    private Subdepartment selectedSubdepartmentModel;
    private final MainFrame parent;
    private boolean useUserDepartment;
    private BidType selectedBidType;
    @Getter
    private final CreateBidPanel createBidPanel;
    private JSplitPane splitPane;
    private Thread bidRequestThread;
    private final ReportsGenerator reportsGenerator;

    public BidsListPanel(MainFrame parent) {
        this.parent = parent;
        Dimension buttonDim = new Dimension(25, 25);
        Dimension comboDim = new Dimension(200, 20);
        reportsGenerator = new ReportsGenerator(parent);

        listener = new EmptyBidsListPanelListener();
        useUserDepartment = false;
        createBidPanel = new CreateBidPanel(parent);
        createBidPanel.setVisible(false);

        selectedBidModel = EmptyModel.BID;
        selectedBidModels = new LinkedList<>();
        selectedFinanceDepartmentModel = EmptyModel.FINANCE_DEPARTMENT;
        selectedDepartmentModel = EmptyModel.DEPARTMENT;
        selectedSubdepartmentModel = EmptyModel.SUBDEPARTMENT;
        selectedBidType = BidType.MATERIALS;

        statusDialog = new StatusDialog(parent);

        CEDButtons ced = new CEDButtons(Labels.getProperty("bid_ced"));
        createBidButton = ced.getCreateButton();
        editBidButton = ced.getEditButton();
        deleteBidButton = ced.getDeleteButton();
        copyBidButton = new JButton(Icons.COPY);
        copyBidButton.setToolTipText(Labels.withSpaceAfter("copyBid"));
        copyBidButton.setPreferredSize(buttonDim);
        editBidButton.setEnabled(false);
        copyBidButton.setEnabled(false);
        deleteBidButton.setEnabled(false);

        changeStatusButton = new JButton();
        changeStatusButton.setToolTipText(Labels.getProperty("changeStatus"));
        changeStatusButton.setIcon(Icons.SET_STATUS);
        changeStatusButton.setPreferredSize(buttonDim);
        changeStatusButton.setEnabled(false);

        departmentBox = new EntityComboBox<>();
        departmentBox.setPreferredSize(comboDim);
        departmentBox.addItem(EmptyModel.DEPARTMENT);

        subdepartmentBox = new EntityComboBox<>();
        subdepartmentBox.setPreferredSize(comboDim);
        subdepartmentBox.addItem(EmptyModel.SUBDEPARTMENT);

        financeDepartmentBox = new EntityComboBox<>();
        financeDepartmentBox.setPreferredSize(comboDim);
        financeDepartmentBox.addItem(EmptyModel.FINANCE_DEPARTMENT);

        bidTypeBox = new PJComboBox<>(BidType.values());
        bidTypeBox.setPreferredSize(comboDim);
        bidTypeBox.setSelectedItem(BidType.MATERIALS);

        bidsTableModel = new BidsTableModel();
        bidsTable = new JTable(bidsTableModel);
        // setting preferred sizes for table columns
        bidsTable.getColumnModel().getColumn(0).setPreferredWidth(300);
        bidsTable.getColumnModel().getColumn(1).setPreferredWidth(70);
        bidsTable.getColumnModel().getColumn(2).setPreferredWidth(90);
        bidsTable.getColumnModel().getColumn(3).setPreferredWidth(90);
        bidsTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        bidsTable.getColumnModel().getColumn(5).setPreferredWidth(100);
        bidsTable.getColumnModel().getColumn(6).setPreferredWidth(100);
        // setting multiple selection mode
        bidsTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        sumLabel = new JLabel(0 + Labels.withSpaceBefore("uah"));
        financeLeftLabel = new JLabel();

        createLayout();

        int dividerLocation = 380;

        createBidButton.addActionListener(e -> {
            setDataToCreateBidDialog();
            createBidPanel.setVisible(true);
            splitPane.setDividerLocation(dividerLocation);
            activateButtons(false);
            clearSelectedBidModels();
        });

        copyBidButton.addActionListener(e -> {
            if (!selectedBidModel.equals(EmptyModel.BID)) {
                setDataToCreateBidDialog();
                createBidPanel.loadToDialog(selectedBidModel, false);
                splitPane.setDividerLocation(dividerLocation);
                activateButtons(false);
            } else {
                JOptionPane.showMessageDialog(parent, Labels.getProperty("noOrManyBidsSelected"), Labels.getProperty("cannotPerformOperation"), JOptionPane.ERROR_MESSAGE, Icons.ERROR);
            }
            clearSelectedBidModels();
        });

        editBidButton.addActionListener(e -> {
            if (!selectedBidModel.equals(EmptyModel.BID)) {
                setDataToCreateBidDialog();
                createBidPanel.loadToDialog(selectedBidModel, true);
                splitPane.setDividerLocation(dividerLocation);
                activateButtons(false);
            } else {
                JOptionPane.showMessageDialog(parent, Labels.getProperty("noOrManyBidsSelected"), Labels.getProperty("cannotPerformOperation"), JOptionPane.ERROR_MESSAGE, Icons.ERROR);
            }
            clearSelectedBidModels();
        });

        deleteBidButton.addActionListener(e -> {
            if (!selectedBidModel.equals(EmptyModel.BID)) {
                if (ced.deleteEntry(parent, selectedBidModel.getDescription())) {
                    selectedFinanceDepartmentModel.addBid(selectedBidModel);
                    listener.persistModelEventOccurred(new DeleteCommand<>(selectedBidModel));
                    getBids();
                }
                activateButtons(false);
            } else {
                JOptionPane.showMessageDialog(parent, Labels.getProperty("noOrManyBidsSelected"), Labels.getProperty("cannotPerformOperation"), JOptionPane.ERROR_MESSAGE, Icons.ERROR);
            }
            clearSelectedBidModels();
        });

        departmentBox.addActionListener(e -> {
            activateButtons(false);
            selectedDepartmentModel = (Department) departmentBox.getSelectedItem();
            subdepartmentBox.removeAllItems();
            subdepartmentBox.addItem(EmptyModel.SUBDEPARTMENT);
            if (!isSelectedDepartmentModelEmpty())
                setSubdepartmentBoxData(listener.getSubdepartments(selectedDepartmentModel.getModelId()));
            getBids();
            clearSelectedBidModels();
        });

        subdepartmentBox.addActionListener(e -> {
            activateButtons(false);
            financeDepartmentBox.removeAllItems();
            financeDepartmentBox.addItem(EmptyModel.FINANCE_DEPARTMENT);
            if (subdepartmentBox.getSelectedItem() != null) {
                selectedSubdepartmentModel = (Subdepartment) subdepartmentBox.getSelectedItem();
                if (isSelectedSubdepartmentModelEmpty()) {
                    getBids();
                } else {
                    setFinanceDepartmentBoxData(listener.getFinanceDepartments(new GetFinanceDepartmentsQuery(parent.getReportYear(), selectedSubdepartmentModel)));
                }

                clearSelectedBidModels();
            }
        });

        financeDepartmentBox.addActionListener(e -> {
            activateButtons(false);
            if (financeDepartmentBox.getSelectedItem() != null) {
                selectedFinanceDepartmentModel = (FinanceDepartment) financeDepartmentBox.getSelectedItem();
                List<Bid> bids = selectedFinanceDepartmentModel.getBids(selectedBidType);
                if (isSelectedFinanceDepartmentModelEmpty()) {
                    getBids();
                } else {
                    setBidsTableData(bids);
                }
                clearSelectedBidModels();
            }
        });

        bidTypeBox.addActionListener(e -> {
            selectedBidType = (BidType) bidTypeBox.getSelectedItem();
            getBids();
            clearSelectedBidModels();
        });

        changeStatusButton.addActionListener(e -> {
            if (!selectedBidModel.equals(EmptyModel.BID)) {
                statusDialog.setVisible(true, selectedBidModel);
            } else {
                JOptionPane.showMessageDialog(parent, Labels.getProperty("noOrManyBidsSelected"), Labels.getProperty("cannotPerformOperation"), JOptionPane.ERROR_MESSAGE, Icons.ERROR);
            }
        });

        bidsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ev) {
                int[] selectedRows = bidsTable.getSelectedRows();
                clearSelectedBidModels();
                if (ev.getButton() == MouseEvent.BUTTON1) {
                    if (selectedRows.length == 1) {
                        activateButtons(true);

                        selectedBidModel = (Bid) bidsTable.getValueAt(selectedRows[0], 0);
                    } else if (selectedRows.length > 1) {
                        activateButtons(false);

                        for (int row : selectedRows) {
                            selectedBidModels.add((Bid) bidsTable.getValueAt(row, 0));
                        }
                        selectedBidModel = EmptyModel.BID;
                    }
                }
            }
        });

        statusDialog.setStatusDialogListener(new StatusDialogListener() {
            @Override
            public <T extends IEntity> void persistModelEventOccurred(ICommand<T> command) {
                listener.persistModelEventOccurred(command);
                getBids();
            }
        });

        createBidPanel.setCreateBidDialogListener(new CreateBidPanelListener() {
            @Override
            public <T extends IEntity> void persistModelEventOccurred(ICommand<T> cmd) {
                listener.persistModelEventOccurred(cmd);
                getBids();
            }

            @Override
            public void getAllData() {
                FinanceDepartment selectedModel = selectedFinanceDepartmentModel;
                listener.getAllData();
                if (!selectedModel.equals(EmptyModel.FINANCE_DEPARTMENT)) {
                    financeDepartmentBox.setSelectedItem(selectedModel);
                }
            }

            @Override
            public BigDecimal getLeftAmount(GetFinanceDepartmentLeftAmountQuery query) {
                return listener.getLeftAmountEvent(query);
            }
        });
    }

    private void clearSelectedBidModels() {
        selectedBidModel = EmptyModel.BID;
        selectedBidModels.clear();
    }

    private void activateButtons(boolean state) {
        editBidButton.setEnabled(state);
        copyBidButton.setEnabled(state);
        deleteBidButton.setEnabled(state);
        changeStatusButton.setEnabled(state);
    }

    public void getBids() {
        if (bidRequestThread == null || !bidRequestThread.isAlive()) {
            bidRequestThread = new Thread(() -> {
                try {
                    Thread.sleep(250);
                    GetBidsQuery query;
                    if (isSelectedDepartmentModelEmpty()) {
                        query = new GetBidsQuery(selectedBidType, parent.getReportYear());
                    } else if (isSelectedSubdepartmentModelEmpty()) {
                        query = new GetBidsQuery(selectedBidType, parent.getReportYear(), selectedDepartmentModel);
                    } else if (isSelectedFinanceDepartmentModelEmpty()) {
                        query = new GetBidsQuery(selectedBidType, parent.getReportYear(), selectedSubdepartmentModel);
                    } else if (!isSelectedFinanceDepartmentModelEmpty()) {
                        query = new GetBidsQuery(selectedBidType, parent.getReportYear(), selectedFinanceDepartmentModel);
                    } else {
                        return;
                    }
                    listener.getBids(query);
                } catch (InterruptedException e) {
                    Logger.warnEvent(this.getClass(), e);
                }
            });
            bidRequestThread.start();
        }
    }

    private void setDataToCreateBidDialog() {
        createBidPanel.clear();
        createBidPanel.setCurrentBidType(selectedBidType);
        if (!isSelectedDepartmentModelEmpty()) {
            createBidPanel.setCurrentDepartment(selectedDepartmentModel);
        }
        if (!isSelectedSubdepartmentModelEmpty()) {
            createBidPanel.setCurrentSubdepartment(selectedSubdepartmentModel);
        }
        if (!isSelectedFinanceDepartmentModelEmpty()) {
            createBidPanel.setCurrentFinanceDepartment(selectedFinanceDepartmentModel);
        }
    }

    private boolean isSelectedDepartmentModelEmpty() {
        return selectedDepartmentModel.equals(EmptyModel.DEPARTMENT);
    }

    private boolean isSelectedSubdepartmentModelEmpty() {
        return isSelectedDepartmentModelEmpty() || selectedSubdepartmentModel.equals(EmptyModel.SUBDEPARTMENT);
    }

    private boolean isSelectedFinanceDepartmentModelEmpty() {
        return isSelectedSubdepartmentModelEmpty() || selectedFinanceDepartmentModel.equals(EmptyModel.FINANCE_DEPARTMENT);
    }

    private void setFinanceLabels() {
        if (selectedFinanceDepartmentModel.getTotalAmount(selectedBidType) != null) {
            BigDecimal sum = listener.getSpentAmountEvent(new GetFinanceDepartmentSpentAmountQuery(selectedFinanceDepartmentModel, selectedBidType)); //todo compare with foreach for bids
            BigDecimal financeLeft = listener.getLeftAmountEvent(new GetFinanceDepartmentLeftAmountQuery(selectedFinanceDepartmentModel, selectedBidType));
            sumLabel.setText(Labels.withColon("totalPrice2") + " " + sum.setScale(2, RoundingMode.CEILING) + Labels.withSpaceBefore("uah"));
            financeLeftLabel.setText(Labels.withColon("financeLeftByTema") + " " + financeLeft.setScale(2, RoundingMode.CEILING) + Labels.withSpaceBefore("uah"));
        } else {
            sumLabel.setText("");
            financeLeftLabel.setText("");
        }
    }

    public void setUseUserDepartment() {
        useUserDepartment = true;
        departmentBox.setEnabled(false);
        createBidPanel.setEnabledDepartmentBox(false);
        if (LoginData.getInstance().getRole().equals(Role.USER)) {
            subdepartmentBox.setEnabled(false);
            createBidPanel.setEnabledSubdepartmentBox(false);
        }
    }

    public void setDepartmentBoxData(List<Department> db) {
        for (Department model : db) {
            if (model.isActive()) {
                departmentBox.addItem(model);
                createBidPanel.addToDepartmentBox(model);
                if (useUserDepartment && LoginData.getInstance().getSubdepartment().getDepartment().equals(model)) {
                    departmentBox.setSelectedItem(model);
                }
            }
        }

    }

    private void setSubdepartmentBoxData(List<Subdepartment> db) {
        for (Subdepartment model : db) {
            if (model.isActive()) {
                subdepartmentBox.addItem(model);
                if (useUserDepartment && LoginData.getInstance().getSubdepartment().equals(model)) {
                    subdepartmentBox.setSelectedItem(model);
                }
            }
        }
    }

    public void setFinanceDepartmentBoxData(List<FinanceDepartment> db) {
        financeDepartmentBox.setBoxData(db, EmptyModel.FINANCE_DEPARTMENT, true);
    }

    public Department getSelectedDepartment() {
        return (Department) departmentBox.getSelectedItem();
    }

    public void setBidsTableData(List<Bid> db) {
        List<Bid> models = new ArrayList<>();
        for (Bid model : db) {
            if (model.isActive()) {
                models.add(model);
            }
        }
        bidsTableModel.setData(models);
        setFinanceLabels();
        bidsTable.setAutoCreateRowSorter(true);
        bidsTableModel.fireTableDataChanged();
    }

    public void setListener(BidsListPanelListener listener) {
        this.listener = listener;
    }

    public void setSelectedModel(FinanceDepartment model) {
        if (model.getTotalAmount(BidType.MATERIALS).compareTo(BigDecimal.ZERO) > 0) {
            bidTypeBox.setSelectedItem(BidType.MATERIALS);
        } else if (model.getTotalAmount(BidType.EQUIPMENT).compareTo(BigDecimal.ZERO) > 0) {
            bidTypeBox.setSelectedItem(BidType.EQUIPMENT);
        } else {
            bidTypeBox.setSelectedItem(BidType.SERVICES);
        }

        departmentBox.setSelectedModel(model.getSubdepartment().getDepartment());
        subdepartmentBox.setSelectedModel(model.getSubdepartment());
        financeDepartmentBox.setSelectedModel(model);

        getBids();
    }

    private void createLayout() {
        JPanel topPanel = new JPanel();
        JPanel sumPanel = new JPanel();
        JPanel departmentSelectorPanel = new JPanel();
        JPanel downPanel = new JPanel();
        JSeparator separatorTopPanel = new JSeparator(SwingConstants.VERTICAL);
        separatorTopPanel.setPreferredSize(new Dimension(10, (int) createBidButton.getPreferredSize().getHeight()));

        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBorder(BorderFactory.createEtchedBorder());
        topPanel.add(createBidButton);
        topPanel.add(editBidButton);
        topPanel.add(copyBidButton);
        topPanel.add(deleteBidButton);
        topPanel.add(separatorTopPanel);
        topPanel.add(new JLabel(Labels.withColon("finance")));
        topPanel.add(financeDepartmentBox);
        topPanel.add(new JLabel(Labels.withColon("bidType")));
        topPanel.add(bidTypeBox);

        topPanel.add(separatorTopPanel);
        topPanel.add(changeStatusButton);

        JSeparator separatorSumPanel = new JSeparator(SwingConstants.VERTICAL);
        separatorSumPanel.setPreferredSize(new Dimension(10, (int) sumLabel.getPreferredSize().getHeight()));
        sumPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        sumPanel.add(financeLeftLabel);
        sumPanel.add(separatorSumPanel);
        sumPanel.add(sumLabel);

        departmentSelectorPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        departmentSelectorPanel.add(new JLabel(Labels.withColon("department")));
        departmentSelectorPanel.add(departmentBox);
        departmentSelectorPanel.add(new JLabel(Labels.withColon("subdepartment")));
        departmentSelectorPanel.add(subdepartmentBox);

        downPanel.setLayout(new BorderLayout());
        downPanel.add(departmentSelectorPanel, BorderLayout.WEST);
        downPanel.add(sumPanel, BorderLayout.EAST);

        JPanel generalPanel = new JPanel(new BorderLayout());
        generalPanel.add(topPanel, BorderLayout.NORTH);
        generalPanel.add(new JScrollPane(bidsTable), BorderLayout.CENTER);
        generalPanel.add(downPanel, BorderLayout.SOUTH);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, createBidPanel, generalPanel);
        splitPane.setEnabled(false);

        setLayout(new BorderLayout());
        add(splitPane, BorderLayout.CENTER);
    }

    public boolean isReadyForPrint() {
        if (bidsTable.getRowCount() < 1) {
            JOptionPane.showMessageDialog(parent, Labels.getProperty("emptyTableErrorExtended"),
                    Labels.getProperty("emptyTableError"), JOptionPane.ERROR_MESSAGE, Icons.ERROR);
            return false;
        }
        return true;
    }

    public void printBidList(Map<String, Object> parameters) {
        List<BidReportModel> list = getSelectedBids().stream().map(BidReportModel::new).toList();
        reportsGenerator.showPrintDialog(new BidsReportRequest(parameters, list));
    }

    public List<Bid> getSelectedBids() {
        List<Bid> list = new LinkedList<>();
        //if no bids selected adding all bids
        if (selectedBidModel.equals(EmptyModel.BID) && selectedBidModels.isEmpty()) {
            for (int row = 0; row < bidsTable.getRowCount(); row++) {
                Bid md = (Bid) bidsTable.getValueAt(row, 0);
                list.add(md);
            }
        }
        // if 1 bid selected
        else if (selectedBidModels.isEmpty()) {
            Bid md = selectedBidModel;
            list.add(md);
        }
        // if multiple bids selected
        else {
            list.addAll(selectedBidModels);
        }
        return list;
    }

    public void refreshYear() {
        if (!isSelectedSubdepartmentModelEmpty())
            setFinanceDepartmentBoxData(listener.getFinanceDepartments(new GetFinanceDepartmentsQuery(parent.getReportYear(), selectedSubdepartmentModel)));
        setButtonsActive(false);
        getBids();
    }

    public void refresh() {
        getBids();
    }

    @Override
    public void setVisible(boolean visible) {
        refresh();
        super.setVisible(visible);
    }

    private void setButtonsActive(boolean isActive) {
        copyBidButton.setEnabled(isActive);
        editBidButton.setEnabled(isActive);
        deleteBidButton.setEnabled(isActive);
        changeStatusButton.setEnabled(isActive);
    }
}
