package gui.bids;

import gui.*;
import gui.bids.reports.BidsReport;
import gui.bids.status.StatusDialog;
import model.dao.LoginData;
import model.enums.BidType;
import model.models.*;

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
    private final FinanceDepartmentModel emptyFinanceDepartmentModel = new FinanceDepartmentModel();
    private final DepartmentModel emptyDepartmentModel = new DepartmentModel();
    private final SubdepartmentModel emptySubdepartmentModel = new SubdepartmentModel();
    private final BidModel emptyBidModel = null; //TODO
    private JButton createBidButton;
    private JButton editBidButton;
    private JButton deleteBidButton;
    private JButton changeStatusButton;
    private JComboBox<DepartmentModel> departmentBox;
    private JComboBox<SubdepartmentModel> subdepartmentBox;
    private JComboBox<FinanceDepartmentModel> financeDepartmentBox;
    private JComboBox<BidType> bidTypeBox;
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
    private boolean useUserDepartment = false;
    private BidType selectedBidType;

    public BidsListPanel(MainFrame parent) {

        this.parent = parent;

        Dimension buttonDim = new Dimension(25, 25);
        Dimension comboDim = new Dimension(200, 20);

        selectedBidModel = emptyBidModel;
        selectedBidModels = new LinkedList<>();
        selectedFinanceDepartmentModel = emptyFinanceDepartmentModel;
        selectedDepartmentModel = emptyDepartmentModel;
        selectedSubdepartmentModel = emptySubdepartmentModel;
        selectedBidType = BidType.MATERIALS;

        statusDialog = new StatusDialog(parent);

        CrEdDelButtons ced = new CrEdDelButtons(Labels.getProperty("bid_ced"));
        createBidButton = ced.getCreateButton();
        editBidButton = ced.getEditButton();
        deleteBidButton = ced.getDeleteButton();
        editBidButton.setEnabled(false);
        deleteBidButton.setEnabled(false);

        changeStatusButton = new JButton();
        changeStatusButton.setToolTipText(Labels.getProperty("changeStatus"));
        changeStatusButton.setIcon(Icons.SET_STATUS);
        changeStatusButton.setPreferredSize(buttonDim);
        changeStatusButton.setEnabled(false);

        departmentBox = new JComboBox<>();
        departmentBox.setPreferredSize(comboDim);
        departmentBox.addItem(emptyDepartmentModel);

        subdepartmentBox = new JComboBox<>();
        subdepartmentBox.setPreferredSize(comboDim);
        subdepartmentBox.addItem(emptySubdepartmentModel);

        financeDepartmentBox = new JComboBox<>();
        financeDepartmentBox.setPreferredSize(comboDim);
        financeDepartmentBox.addItem(emptyFinanceDepartmentModel);

        bidTypeBox = new JComboBox<>(BidType.values());
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
        });

        editBidButton.addActionListener(e -> {
            if (!selectedBidModel.equals(emptyBidModel)) {
                setDataToCreateBidDialog();
                parent.getCreateBidDialog().loadToDialog(selectedBidModel);
            } else {
                JOptionPane.showMessageDialog(parent, Labels.getProperty("noOrManyBidsSelected"), Labels.getProperty("cannotPerformOperation"), JOptionPane.ERROR_MESSAGE);
            }
            selectedBidModel = emptyBidModel;
        });

        deleteBidButton.addActionListener(e -> {
            if (!selectedBidModel.equals(emptyBidModel) && listener != null) {
                if (ced.deleteEntry(parent, selectedBidModel.getBidDesc())) {
                    selectedBidModel.setDeleted();
                    listener.persistModelEventOccurred(selectedBidModel, selectedBidType);
                }
            } else {
                JOptionPane.showMessageDialog(parent, Labels.getProperty("noOrManyBidsSelected"), Labels.getProperty("cannotPerformOperation"), JOptionPane.ERROR_MESSAGE);
            }
            selectedBidModel = emptyBidModel;
        });

        departmentBox.addActionListener(e -> {
            editBidButton.setEnabled(false);
            deleteBidButton.setEnabled(false);
            changeStatusButton.setEnabled(false);
            if (listener != null) {
                financeDepartmentBox.removeAllItems();
                financeDepartmentBox.addItem(emptyFinanceDepartmentModel);
                selectedDepartmentModel = (DepartmentModel) departmentBox.getSelectedItem();
                setSubdepartmentBoxData(selectedDepartmentModel.getSubdepartments());
                if (isSelectedDepartmentModelEmpty()) {
                    listener.selectAllBidsEventOccurred(selectedBidType);
                }
                selectedBidModel = emptyBidModel;
                selectedBidModels.clear();
            }
        });

        subdepartmentBox.addActionListener(e -> {
            editBidButton.setEnabled(false);
            deleteBidButton.setEnabled(false);
            changeStatusButton.setEnabled(false);
            financeDepartmentBox.removeAllItems();
            financeDepartmentBox.addItem(emptyFinanceDepartmentModel);
            selectedSubdepartmentModel = (SubdepartmentModel) subdepartmentBox.getSelectedItem();
            setFinanceDepartmentBoxData(selectedSubdepartmentModel.getFinanceDepartments());
            if (listener != null && isSelectedSubepartmentModelEmpty()) {
                listener.getBidsByDepartment(selectedDepartmentModel, selectedBidType);
            }
            selectedBidModel = emptyBidModel;
            selectedBidModels.clear();
        });

        financeDepartmentBox.addActionListener(e -> {
            editBidButton.setEnabled(false);
            deleteBidButton.setEnabled(false);
            changeStatusButton.setEnabled(false);
            if (financeDepartmentBox.getSelectedItem() != null) {
                selectedFinanceDepartmentModel = (FinanceDepartmentModel) financeDepartmentBox.getSelectedItem();

                List<? extends BidModel> bids = selectedFinanceDepartmentModel.getBids(selectedBidType);
                if (isSelectedFinanceDepartmentModelEmpty() && !isSelectedDepartmentModelEmpty() && listener != null) {
                    listener.getBidsByDepartment(selectedDepartmentModel, selectedBidType);
                }
                this.setBidsTableData(bids);
                selectedBidModel = emptyBidModel;
                selectedBidModels.clear();
            }
        });

        bidTypeBox.addActionListener(e -> {
            selectedBidType = (BidType) bidTypeBox.getSelectedItem();
            if (listener != null) {
                if (isSelectedDepartmentModelEmpty()) {
                    listener.selectAllBidsEventOccurred(selectedBidType);
                } else if (isSelectedFinanceDepartmentModelEmpty()) {
                    listener.getBidsByDepartment(selectedDepartmentModel, selectedBidType);
                }
            }
        });

        changeStatusButton.addActionListener(e -> {
            if (!selectedBidModel.equals(emptyBidModel)) {
                statusDialog.setVisible(true, selectedBidModel);
            } else {
                JOptionPane.showMessageDialog(parent, Labels.getProperty("noOrManyBidsSelected"), Labels.getProperty("cannotPerformOperation"), JOptionPane.ERROR_MESSAGE);
            }
        });

        bidsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ev) {
                int[] selectedRows = bidsTable.getSelectedRows();
                selectedBidModels.clear();
                if (ev.getButton() == MouseEvent.BUTTON1) {
                    if (selectedRows.length == 1) {
                        editBidButton.setEnabled(true);
                        deleteBidButton.setEnabled(true);
                        changeStatusButton.setEnabled(true);

                        selectedBidModel = (BidModel) bidsTable.getValueAt(selectedRows[0], 0);
                    } else if (selectedRows.length > 1) {
                        editBidButton.setEnabled(false);
                        deleteBidButton.setEnabled(false);
                        changeStatusButton.setEnabled(false);

                        for (int row : selectedRows) {
                            selectedBidModels.add((BidModel) bidsTable.getValueAt(row, 0));
                        }
                        selectedBidModel = emptyBidModel;
                    }
                }
            }
        });

        statusDialog.setStatusDialogListener(model -> {
            if (listener != null) {
                listener.persistModelEventOccurred(model, selectedBidType);
            }
        });
    }

    private void setDataToCreateBidDialog() {
        if (!isSelectedFinanceDepartmentModelEmpty()) {
            parent.getCreateBidDialog().setCurrentDepartment(selectedDepartmentModel);
            parent.getCreateBidDialog().setCurrentBidType(selectedBidType);
            parent.getCreateBidDialog().setCurrentFinanceDepartment(selectedFinanceDepartmentModel);
        } else if (!isSelectedDepartmentModelEmpty()) {
            parent.getCreateBidDialog().setCurrentDepartment(selectedDepartmentModel);
            parent.getCreateBidDialog().setCurrentBidType(selectedBidType);
        }
    }

    private boolean isSelectedDepartmentModelEmpty() {
        return selectedDepartmentModel.equals(emptyDepartmentModel);
    }

    private boolean isSelectedSubepartmentModelEmpty() {
        return selectedSubdepartmentModel.equals(emptySubdepartmentModel);
    }

    private boolean isSelectedFinanceDepartmentModelEmpty() {
        return selectedFinanceDepartmentModel.equals(emptyFinanceDepartmentModel);
    }

    public void setFinanceLabels(BigDecimal sum, BigDecimal financeLeft) {
        sumLabel.setText(Labels.withColon("totalPrice2") + " " + sum.setScale(2, RoundingMode.CEILING) + Labels.withSpaceBefore("uah"));
        if (!isSelectedFinanceDepartmentModelEmpty()) {
            financeLeftLabel.setText(Labels.withColon("financeLeftByTema") + " " + financeLeft.setScale(2, RoundingMode.CEILING) + Labels.withSpaceBefore("uah"));
        } else {
            financeLeftLabel.setText("");
        }
    }

    public void setUseUserDepartment() {
        useUserDepartment = true;
        departmentBox.setEnabled(false);
        subdepartmentBox.setEnabled(false);
        parent.getCreateBidDialog().setEnabledDepartmentBox(false);
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

    public void setSubdepartmentBoxData(List<SubdepartmentModel> db) {
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
        // bugfix for multiple identical items in combobox
        if (financeDepartmentBox.getItemCount() > 1) {
            financeDepartmentBox.removeAllItems();
            financeDepartmentBox.addItem(emptyFinanceDepartmentModel);
        }
        for (FinanceDepartmentModel model : db) {
            if (model.isActive()) {
                financeDepartmentBox.addItem(model);
            }
        }
    }

    public long getSelectedDepartmentId() {
        return ((DepartmentModel) departmentBox.getSelectedItem()).getModelId();
    }

    public void setBidsTableData(List<? extends BidModel> db) {
        bidsTableModel.setData(db);
        refreshBidsTableData();
    }

    public void refreshBidsTableData() {
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
                    Labels.getProperty("emptyTableError"), JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (Objects.equals(departmentBox.getSelectedItem().toString(), "")) {
            Utils.emptyFieldError(parent, Labels.getProperty("department"));
            return false;
        } else if (financeDepartmentBox.getSelectedItem().toString() == null) {
            Utils.emptyFieldError(parent, Labels.getProperty("order"));
            return false;
        }
        return true;
    }

    public void printBidList() {
        List<BidsReportModel> list = new ArrayList<>();
        //if no bids selected
        if (selectedBidModel.equals(emptyBidModel) && selectedBidModels.isEmpty()) {
            for (int row = 0; row < bidsTable.getRowCount(); row++) {
                BidModel md = (BidModel) bidsTable.getValueAt(row, 0);
                BidsReportModel reportModel = new BidsReportModel(md.getDepartment().getDepName(), md.getFinances().getFinances().getFinanceName(),
                        md.getCpv().getCpvId(), md.getCpv().getCpvUkr(), md.getBidDesc(), md.getCreatedDate(), md.getProducer().getBrandName(),
                        md.getCatNum(), md.getSupplier().getSupplierName(), md.getAmountUnit().getAmUnitDesc(), md.getOnePrice(), md.getAmount(),
                        md.getReasonForSupplierChoice().getReason());
                list.add(reportModel);
            }
        }
        // if 1 bid selected
        else if (selectedBidModels.isEmpty()) {
            BidModel md = selectedBidModel;
            BidsReportModel reportModel = new BidsReportModel(md.getDepartment().getDepName(), md.getFinances().getFinances().getFinanceName(),
                    md.getCpv().getCpvId(), md.getCpv().getCpvUkr(), md.getBidDesc(), md.getCreatedDate(), md.getProducer().getBrandName(),
                    md.getCatNum(), md.getSupplier().getSupplierName(), md.getAmountUnit().getAmUnitDesc(), md.getOnePrice(), md.getAmount(),
                    md.getReasonForSupplierChoice().getReason());
            list.add(reportModel);
        }
        // if multiple bids selected
        else {
            for (BidModel md : selectedBidModels) {
                BidsReportModel reportModel = new BidsReportModel(md.getDepartment().getDepName(), md.getFinances().getFinances().getFinanceName(),
                        md.getCpv().getCpvId(), md.getCpv().getCpvUkr(), md.getBidDesc(), md.getCreatedDate(), md.getProducer().getBrandName(),
                        md.getCatNum(), md.getSupplier().getSupplierName(), md.getAmountUnit().getAmUnitDesc(), md.getOnePrice(), md.getAmount(),
                        md.getReasonForSupplierChoice().getReason());
                list.add(reportModel);
            }
        }
        new BidsReport(Collections.unmodifiableList(list), parent);
    }
}
