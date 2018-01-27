package com.github.andriilab.promasy.presentation.bids;

import com.github.andriilab.promasy.data.controller.Logger;
import com.github.andriilab.promasy.data.controller.LoginData;
import com.github.andriilab.promasy.data.controller.ReportsGenerator;
import com.github.andriilab.promasy.domain.EmptyModel;
import com.github.andriilab.promasy.domain.bid.entities.Bid;
import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.bid.reports.BidsReportModel;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;
import com.github.andriilab.promasy.domain.organization.entities.Department;
import com.github.andriilab.promasy.domain.organization.entities.Subdepartment;
import com.github.andriilab.promasy.domain.organization.enums.Role;
import com.github.andriilab.promasy.presentation.MainFrame;
import com.github.andriilab.promasy.presentation.bids.status.StatusDialog;
import com.github.andriilab.promasy.presentation.commons.Icons;
import com.github.andriilab.promasy.presentation.commons.Labels;
import com.github.andriilab.promasy.presentation.components.CEDButtons;
import com.github.andriilab.promasy.presentation.components.PJComboBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.List;

/**
 * This panel view table of Bids and can Create, Modify, and Delete this entries in DB
 */
public class BidsListPanel extends JPanel {

    private final JButton createBidButton;
    private final JButton copyBidButton;
    private final JButton editBidButton;
    private final JButton deleteBidButton;
    private final JButton changeStatusButton;
    private final PJComboBox<Department> departmentBox;
    private final PJComboBox<Subdepartment> subdepartmentBox;
    private final PJComboBox<FinanceDepartment> financeDepartmentBox;
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
    private final CreateBidPanel createBidPanel;
    private JSplitPane splitPane;
    private Thread bidRequestThread;

    public BidsListPanel(MainFrame parent) {
        this.parent = parent;

        Dimension buttonDim = new Dimension(25, 25);
        Dimension comboDim = new Dimension(200, 20);

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

        departmentBox = new PJComboBox<>();
        departmentBox.setPreferredSize(comboDim);
        departmentBox.addItem(EmptyModel.DEPARTMENT);

        subdepartmentBox = new PJComboBox<>();
        subdepartmentBox.setPreferredSize(comboDim);
        subdepartmentBox.addItem(EmptyModel.SUBDEPARTMENT);

        financeDepartmentBox = new PJComboBox<>();
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

        int dividerLocation = 350;

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
            if (!selectedBidModel.equals(EmptyModel.BID) && listener != null) {
                if (ced.deleteEntry(parent, selectedBidModel.getBidDesc())) {
                    selectedBidModel.setDeleted();
                    selectedFinanceDepartmentModel.addBid(selectedBidModel);
                    listener.persistModelEventOccurred(selectedBidModel);
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
            setSubdepartmentBoxData(selectedDepartmentModel.getSubdepartments());
            if (listener != null) {
                getBids();
            }
            clearSelectedBidModels();
        });

        subdepartmentBox.addActionListener(e -> {
            activateButtons(false);
            financeDepartmentBox.removeAllItems();
            financeDepartmentBox.addItem(EmptyModel.FINANCE_DEPARTMENT);
            if (subdepartmentBox.getSelectedItem() != null) {
                selectedSubdepartmentModel = (Subdepartment) subdepartmentBox.getSelectedItem();
                if (isSelectedSubepartmentModelEmpty() && listener != null) {
                    getBids();
                } else {
                    setFinanceDepartmentBoxData(selectedSubdepartmentModel.getFinanceDepartments());
                }
                clearSelectedBidModels();
            }
        });

        financeDepartmentBox.addActionListener(e -> {
            activateButtons(false);
            if (financeDepartmentBox.getSelectedItem() != null) {
                selectedFinanceDepartmentModel = (FinanceDepartment) financeDepartmentBox.getSelectedItem();
                List<Bid> bids = selectedFinanceDepartmentModel.getBids(selectedBidType);
                if (isSelectedFinanceDepartmentModelEmpty() && listener != null) {
                    getBids();
                } else {
                    setBidsTableData(bids);
                }
                clearSelectedBidModels();
            }
        });

        bidTypeBox.addActionListener(e -> {
            selectedBidType = (BidType) bidTypeBox.getSelectedItem();
            if (listener != null) {
                getBids();
            }
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

        statusDialog.setStatusDialogListener(model -> {
            if (listener != null) {
                listener.persistModelEventOccurred(model);
                getBids();
            }
        });

        createBidPanel.setCreateBidDialogListener(new CreateBidPanelListener() {
            @Override
            public void persistModelEventOccurred(Bid model) {
                if (listener != null) {
                    listener.persistModelEventOccurred(model);
                    getBids();
                }
            }

            @Override
            public void getAllData() {
                FinanceDepartment selectedModel = selectedFinanceDepartmentModel;
                if (listener != null) {
                    listener.getAllData();
                }
                if (!selectedModel.equals(EmptyModel.FINANCE_DEPARTMENT)) {
                    financeDepartmentBox.setSelectedItem(selectedModel);
                }
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
                    if (isSelectedDepartmentModelEmpty()) {
                        listener.selectAllBidsEventOccurred(selectedBidType);
                    } else if (isSelectedSubepartmentModelEmpty()) {
                        listener.getBidsByDepartment(selectedBidType, selectedDepartmentModel);
                    } else if (isSelectedFinanceDepartmentModelEmpty()) {
                        listener.getBidsBySubdepartment(selectedBidType, selectedSubdepartmentModel);
                    } else if (!isSelectedFinanceDepartmentModelEmpty()) {
                        listener.getBidsByFinanceDepartment(selectedBidType, selectedFinanceDepartmentModel);
                    }
                } catch (InterruptedException e) {
                    Logger.warnEvent(e);
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
        if (!isSelectedSubepartmentModelEmpty()) {
            createBidPanel.setCurrentSubdepartment(selectedSubdepartmentModel);
        }
        if (!isSelectedFinanceDepartmentModelEmpty()) {
            createBidPanel.setCurrentFinanceDepartment(selectedFinanceDepartmentModel);
        }
    }

    private boolean isSelectedDepartmentModelEmpty() {
        return selectedDepartmentModel.equals(EmptyModel.DEPARTMENT);
    }

    private boolean isSelectedSubepartmentModelEmpty() {
        return isSelectedDepartmentModelEmpty() || selectedSubdepartmentModel.equals(EmptyModel.SUBDEPARTMENT);
    }

    private boolean isSelectedFinanceDepartmentModelEmpty() {
        return isSelectedSubepartmentModelEmpty() || selectedFinanceDepartmentModel.equals(EmptyModel.FINANCE_DEPARTMENT);
    }

    private void setFinanceLabels() {
        if (selectedFinanceDepartmentModel.getTotalAmount(selectedBidType) != null) {
            BigDecimal sum = selectedFinanceDepartmentModel.getTotalAmount(selectedBidType).subtract(selectedFinanceDepartmentModel.getUpdatedLeftAmount(selectedBidType));
            BigDecimal financeLeft = selectedFinanceDepartmentModel.getUpdatedLeftAmount(selectedBidType);
            sumLabel.setText(Labels.withColon("totalPrice2") + " " + sum.setScale(2, RoundingMode.CEILING) + Labels.withSpaceBefore("uah"));
            financeLeftLabel.setText(Labels.withColon("financeLeftByTema") + " " + financeLeft.setScale(2, RoundingMode.CEILING) + Labels.withSpaceBefore("uah"));
        } else {
            sumLabel.setText(EmptyModel.STRING);
            financeLeftLabel.setText(EmptyModel.STRING);
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

    public CreateBidPanel getCreateBidPanel() {
        return createBidPanel;
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
        List<BidsReportModel> list = new LinkedList<>();
        getSelectedBids().forEach(bid -> list.add(bid.generateReportModel()));
        new ReportsGenerator(ReportsGenerator.BIDS_REPORT, parameters, Collections.unmodifiableList(list), parent);
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


    public void refresh() {
        //if subdepartment selected - refreshing, else - retrieving all bids
        if (!isSelectedSubepartmentModelEmpty()) {
            FinanceDepartment selected = (FinanceDepartment) financeDepartmentBox.getSelectedItem();
            selectedSubdepartmentModel.refresh();
            financeDepartmentBox.setBoxData(selectedSubdepartmentModel.getFinanceDepartments(), EmptyModel.FINANCE_DEPARTMENT, true);
            financeDepartmentBox.setSelectedModel(selected);
        } else {
            getBids();
        }
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible && listener != null) {
            refresh();
        }
        super.setVisible(visible);
    }
}
