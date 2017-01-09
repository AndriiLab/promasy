package gui.bids;

import gui.*;
import gui.bids.reports.BidsReport;
import gui.bids.status.StatusDialog;
import model.dao.LoginData;
import model.models.BidModel;
import model.models.BidsReportModel;
import model.models.DepartmentModel;
import model.models.FinanceDepartmentModel;

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
    private final BidModel emptyBidModel = new BidModel();
    private JButton createBidButton;
    private JButton editBidButton;
    private JButton deleteBidButton;
    private JButton changeStatusButton;
    private JComboBox<FinanceDepartmentModel> financeDepartmentBox;
    private JComboBox<DepartmentModel> departmentBox;
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
    private MainFrame parent;
    private boolean useUserDepartment = false;

    public BidsListPanel(MainFrame parent) {

        this.parent = parent;

        Dimension buttonDim = new Dimension(25, 25);

        selectedBidModel = emptyBidModel;
        selectedBidModels = new LinkedList<>();
        selectedFinanceDepartmentModel = emptyFinanceDepartmentModel;
        selectedDepartmentModel = emptyDepartmentModel;

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

        financeDepartmentBox = new JComboBox<>();
        financeDepartmentBox.setPreferredSize(new Dimension(200, 25));
        financeDepartmentBox.addItem(emptyFinanceDepartmentModel);

        departmentBox = new JComboBox<>();
        departmentBox.setPreferredSize(new Dimension(250, 25));
        departmentBox.addItem(emptyDepartmentModel);

        bidsTableModel = new BidsTableModel();
        bidsTable = new JTable(bidsTableModel);
        // setting multiple selection mode
        bidsTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        sumLabel = new JLabel(0 + Labels.withSpaceBefore("uah"));
        financeLeftLabel = new JLabel();

        createLayout();

        createBidButton.addActionListener(e -> {
            setIDsToCreateBidDialog();
            parent.getCreateBidDialog().setVisible(true);
        });

        editBidButton.addActionListener(e -> {
            if (!selectedBidModel.equals(emptyBidModel)) {
                setIDsToCreateBidDialog();
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
                    listener.persistModelEventOccurred(selectedBidModel);
                }
            } else {
                JOptionPane.showMessageDialog(parent, Labels.getProperty("noOrManyBidsSelected"), Labels.getProperty("cannotPerformOperation"), JOptionPane.ERROR_MESSAGE);
            }
            selectedBidModel = emptyBidModel;
        });

        financeDepartmentBox.addActionListener(e -> {
            editBidButton.setEnabled(false);
            deleteBidButton.setEnabled(false);
            changeStatusButton.setEnabled(false);
            if (financeDepartmentBox.getSelectedItem() != null && listener != null) {
                selectedFinanceDepartmentModel = (FinanceDepartmentModel) financeDepartmentBox.getSelectedItem();
                List<BidModel> bids = selectedFinanceDepartmentModel.getBids();
                if (isSelectedFinanceDepartmentModelEmpty() && !isSelectedDepartmentModelEmpty()) {
                    List<FinanceDepartmentModel> finances = selectedDepartmentModel.getDepartmentFinances();
                    for (FinanceDepartmentModel finance : finances) {
                        bids.addAll(finance.getBids());
                    }
                }
                this.setBidsTableData(bids);
                selectedBidModel = emptyBidModel;
                selectedBidModels.clear();
            }
        });

        departmentBox.addActionListener(e -> {
            editBidButton.setEnabled(false);
            deleteBidButton.setEnabled(false);
            changeStatusButton.setEnabled(false);
            if (listener != null) {
                financeDepartmentBox.removeAllItems();
                financeDepartmentBox.addItem(emptyFinanceDepartmentModel);
                selectedDepartmentModel = (DepartmentModel) departmentBox.getSelectedItem();
                if (!isSelectedDepartmentModelEmpty()) {
                    this.setFinanceDepartmentBoxData(selectedDepartmentModel.getDepartmentFinances());
                } else if (isSelectedDepartmentModelEmpty()) {
                    listener.selectAllBidsEventOccurred();
                }
                selectedBidModel = emptyBidModel;
                selectedBidModels.clear();
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
                listener.persistModelEventOccurred(model);
            }
        });
    }

    private void setIDsToCreateBidDialog() {
        if (!isSelectedFinanceDepartmentModelEmpty()) {
            parent.getCreateBidDialog().setCurrentDepartment(selectedDepartmentModel);
            parent.getCreateBidDialog().setCurrentFinanceDepartment(selectedFinanceDepartmentModel);
        } else if (!isSelectedDepartmentModelEmpty()) {
            parent.getCreateBidDialog().setCurrentDepartment(selectedDepartmentModel);
        }
    }

    private boolean isSelectedDepartmentModelEmpty() {
        return selectedDepartmentModel.equals(emptyDepartmentModel);
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

    public void setBidsTableData(List<BidModel> db) {
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
        JSeparator separatorTopPanel = new JSeparator(SwingConstants.VERTICAL);
        separatorTopPanel.setPreferredSize(new Dimension(10, (int) createBidButton.getPreferredSize().getHeight()));

        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBorder(BorderFactory.createEtchedBorder());
        topPanel.add(createBidButton);
        topPanel.add(editBidButton);
        topPanel.add(deleteBidButton);
        topPanel.add(separatorTopPanel);
        topPanel.add(new JLabel(Labels.getProperty("finance")));
        topPanel.add(financeDepartmentBox);
        topPanel.add(new JLabel(Labels.getProperty("department")));
        topPanel.add(departmentBox);
        topPanel.add(separatorTopPanel);
        topPanel.add(changeStatusButton);

        JSeparator separatorSumPanel = new JSeparator(SwingConstants.VERTICAL);
        separatorSumPanel.setPreferredSize(new Dimension(10, (int) sumLabel.getPreferredSize().getHeight()));
        sumPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        sumPanel.add(financeLeftLabel);
        sumPanel.add(separatorSumPanel);
        sumPanel.add(sumLabel);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(bidsTable), BorderLayout.CENTER);
        add(sumPanel, BorderLayout.SOUTH);
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
                BidsReportModel reportModel = new BidsReportModel(md.getDepartment().getDepName(), md.getDepartmrntFinances().getFinances().getFinanceName(),
                        md.getCpv().getCpvId(), md.getCpv().getCpvUkr(), md.getBidDesc(), md.getCreatedDate(), md.getProducer().getBrandName(),
                        md.getCatNum(), md.getSupplier().getSupplierName(), md.getAmountUnit().getAmUnitDesc(), md.getOnePrice(), md.getAmount(),
                        md.getReasonForSupplierChoice().getReason());
                list.add(reportModel);
            }
        }
        // if 1 bid selected
        else if (selectedBidModels.isEmpty()) {
            BidModel md = selectedBidModel;
            BidsReportModel reportModel = new BidsReportModel(md.getDepartment().getDepName(), md.getDepartmrntFinances().getFinances().getFinanceName(),
                    md.getCpv().getCpvId(), md.getCpv().getCpvUkr(), md.getBidDesc(), md.getCreatedDate(), md.getProducer().getBrandName(),
                    md.getCatNum(), md.getSupplier().getSupplierName(), md.getAmountUnit().getAmUnitDesc(), md.getOnePrice(), md.getAmount(),
                    md.getReasonForSupplierChoice().getReason());
            list.add(reportModel);
        }
        // if multiple bids selected
        else {
            for (BidModel md : selectedBidModels) {
                BidsReportModel reportModel = new BidsReportModel(md.getDepartment().getDepName(), md.getDepartmrntFinances().getFinances().getFinanceName(),
                        md.getCpv().getCpvId(), md.getCpv().getCpvUkr(), md.getBidDesc(), md.getCreatedDate(), md.getProducer().getBrandName(),
                        md.getCatNum(), md.getSupplier().getSupplierName(), md.getAmountUnit().getAmUnitDesc(), md.getOnePrice(), md.getAmount(),
                        md.getReasonForSupplierChoice().getReason());
                list.add(reportModel);
            }
        }
        new BidsReport(Collections.unmodifiableList(list), parent);
    }
}
