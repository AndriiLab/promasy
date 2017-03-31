package gui.bids;

import gui.MainFrame;
import gui.bids.status.StatusDialog;
import gui.commons.Icons;
import gui.commons.Labels;
import gui.components.CEDButtons;
import gui.components.PJComboBox;
import gui.reports.ReportsGenerator;
import model.dao.LoginData;
import model.enums.BidType;
import model.enums.Role;
import model.models.*;
import model.models.report.BidsReportModel;

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

    private JButton createBidButton;
    private JButton copyBidButton;
    private JButton editBidButton;
    private JButton deleteBidButton;
    private JButton changeStatusButton;
    private PJComboBox<DepartmentModel> departmentBox;
    private PJComboBox<SubdepartmentModel> subdepartmentBox;
    private PJComboBox<FinanceDepartmentModel> financeDepartmentBox;
    private PJComboBox<BidType> bidTypeBox;
    private BidsTableModel bidsTableModel;
    private JTable bidsTable;
    private BidsListPanelListener listener;
    private StatusDialog statusDialog;
    private BidModel selectedBidModel;
    private List<BidModel> selectedBidModels;
    private JLabel sumLabel;
    private JLabel financeLeftLabel;
    private FinanceDepartmentModel selectedFinanceDepartmentModel;
    private DepartmentModel selectedDepartmentModel;
    private SubdepartmentModel selectedSubdepartmentModel;
    private MainFrame parent;
    private boolean useUserDepartment;
    private BidType selectedBidType;

    public BidsListPanel(MainFrame parent) {

        this.parent = parent;

        Dimension buttonDim = new Dimension(25, 25);
        Dimension comboDim = new Dimension(200, 20);

        useUserDepartment = false;

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
        // setting multiple selection mode
        bidsTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        sumLabel = new JLabel(0 + Labels.withSpaceBefore("uah"));
        financeLeftLabel = new JLabel();

        createLayout();

        createBidButton.addActionListener(e -> {
            setDataToCreateBidDialog();
            parent.getCreateBidDialog().setVisible(true);
            activateButtons(false);
            clearSelectedBidModels();
        });

        copyBidButton.addActionListener(e -> {
            if (!selectedBidModel.equals(EmptyModel.BID)) {
                setDataToCreateBidDialog();
                parent.getCreateBidDialog().loadToDialog(selectedBidModel, false);
                activateButtons(false);
            } else {
                JOptionPane.showMessageDialog(parent, Labels.getProperty("noOrManyBidsSelected"), Labels.getProperty("cannotPerformOperation"), JOptionPane.ERROR_MESSAGE, Icons.ERROR);
            }
            clearSelectedBidModels();
        });

        editBidButton.addActionListener(e -> {
            if (!selectedBidModel.equals(EmptyModel.BID)) {
                setDataToCreateBidDialog();
                parent.getCreateBidDialog().loadToDialog(selectedBidModel, true);
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
            selectedDepartmentModel = (DepartmentModel) departmentBox.getSelectedItem();
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
                selectedSubdepartmentModel = (SubdepartmentModel) subdepartmentBox.getSelectedItem();
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
                selectedFinanceDepartmentModel = (FinanceDepartmentModel) financeDepartmentBox.getSelectedItem();
                List<BidModel> bids = selectedFinanceDepartmentModel.getBids(selectedBidType);
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

                        selectedBidModel = (BidModel) bidsTable.getValueAt(selectedRows[0], 0);
                    } else if (selectedRows.length > 1) {
                        activateButtons(false);

                        for (int row : selectedRows) {
                            selectedBidModels.add((BidModel) bidsTable.getValueAt(row, 0));
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

        parent.getCreateBidDialog().setCreateBidDialogListener(new CreateBidDialogListener() {
            @Override
            public void persistModelEventOccurred(BidModel model) {
                if (listener != null) {
                    listener.persistModelEventOccurred(model);
                    getBids();
                }
            }

            @Override
            public void getAllData() {
                FinanceDepartmentModel selectedModel = selectedFinanceDepartmentModel;
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

    private void getBids() {
        if (isSelectedDepartmentModelEmpty()) {
            listener.selectAllBidsEventOccurred(selectedBidType);
        } else if (isSelectedSubepartmentModelEmpty()) {
            listener.getBidsByDepartment(selectedBidType, selectedDepartmentModel);
        } else if (isSelectedFinanceDepartmentModelEmpty()) {
            listener.getBidsBySubdepartment(selectedBidType, selectedSubdepartmentModel);
        } else if (!isSelectedFinanceDepartmentModelEmpty()) {
            listener.getBidsByFinanceDepartment(selectedBidType, selectedFinanceDepartmentModel);
        }
    }

    public BidType getSelectedBidType() {
        return selectedBidType;
    }

    private void setDataToCreateBidDialog() {
        parent.getCreateBidDialog().setCurrentBidType(selectedBidType);
        if (!isSelectedDepartmentModelEmpty()) {
            parent.getCreateBidDialog().setCurrentDepartment(selectedDepartmentModel);
        }
        if (!isSelectedSubepartmentModelEmpty()) {
            parent.getCreateBidDialog().setCurrentSubdepartment(selectedSubdepartmentModel);
        }
        if (!isSelectedFinanceDepartmentModelEmpty()) {
            parent.getCreateBidDialog().setCurrentFinanceDepartment(selectedFinanceDepartmentModel);
        }
    }

    private boolean isSelectedDepartmentModelEmpty() {
        return selectedDepartmentModel.equals(EmptyModel.DEPARTMENT);
    }

    private boolean isSelectedSubepartmentModelEmpty() {
        return selectedSubdepartmentModel.equals(EmptyModel.SUBDEPARTMENT) && !isSelectedDepartmentModelEmpty();
    }

    private boolean isSelectedFinanceDepartmentModelEmpty() {
        return selectedFinanceDepartmentModel.equals(EmptyModel.FINANCE_DEPARTMENT) && !isSelectedSubepartmentModelEmpty();
    }

    private void setFinanceLabels() {
        if (selectedFinanceDepartmentModel.getTotalAmount(selectedBidType) != null) {
            BigDecimal sum = selectedFinanceDepartmentModel.getTotalAmount(selectedBidType).subtract(selectedFinanceDepartmentModel.getLeftAmount(selectedBidType));
            BigDecimal financeLeft = selectedFinanceDepartmentModel.getLeftAmount(selectedBidType);
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
        parent.getCreateBidDialog().setEnabledDepartmentBox(false);
        if (LoginData.getInstance().getRole().equals(Role.USER)) {
            subdepartmentBox.setEnabled(false);
            parent.getCreateBidDialog().setEnabledSubdepartmentBox(false);
        }
    }

    public void setDepartmentBoxData(List<DepartmentModel> db) {
        for (DepartmentModel model : db) {
            if (model.isActive()) {
                departmentBox.addItem(model);
                parent.getCreateBidDialog().addToDepartmentBox(model);
                if (useUserDepartment && LoginData.getInstance().getSubdepartment().getDepartment().equals(model)) {
                    departmentBox.setSelectedItem(model);
                }
            }
        }

    }

    private void setSubdepartmentBoxData(List<SubdepartmentModel> db) {
        for (SubdepartmentModel model : db) {
            if (model.isActive()) {
                subdepartmentBox.addItem(model);
                if (useUserDepartment && LoginData.getInstance().getSubdepartment().equals(model)) {
                    subdepartmentBox.setSelectedItem(model);
                }
            }
        }
    }

    public void setFinanceDepartmentBoxData(List<FinanceDepartmentModel> db) {
        financeDepartmentBox.setBoxData(db, EmptyModel.FINANCE_DEPARTMENT, true);
    }

    public DepartmentModel getSelectedDepartment() {
        return (DepartmentModel) departmentBox.getSelectedItem();
    }

    public void setBidsTableData(List<BidModel> db) {
        List<BidModel> models = new ArrayList<>();
        for (BidModel model : db) {
            if (model.isActive()) {
                models.add(model);
            }
        }
        bidsTableModel.setData(models);
        setFinanceLabels();
        bidsTable.setAutoCreateRowSorter(true);
        bidsTableModel.fireTableDataChanged();
    }

    public void setBidsListPanelListener(BidsListPanelListener listener) {
        this.listener = listener;
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

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(bidsTable), BorderLayout.CENTER);
        add(downPanel, BorderLayout.SOUTH);
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
        List<BidsReportModel> list = new ArrayList<>();
        //if no bids selected printing all bids
        if (selectedBidModel.equals(EmptyModel.BID) && selectedBidModels.isEmpty()) {
            for (int row = 0; row < bidsTable.getRowCount(); row++) {
                BidModel md = (BidModel) bidsTable.getValueAt(row, 0);
                list.add(md.generateReportModel());
            }
        }
        // if 1 bid selected
        else if (selectedBidModels.isEmpty()) {
            BidModel md = selectedBidModel;
            list.add(md.generateReportModel());
        }
        // if multiple bids selected
        else {
            selectedBidModels.forEach(md -> list.add(md.generateReportModel()));
        }
        new ReportsGenerator(ReportsGenerator.BIDS_REPORT, parameters, Collections.unmodifiableList(list), parent);
    }
}
