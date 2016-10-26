package main.java.gui.bids;

import main.java.gui.Labels;
import main.java.gui.MainFrame;
import main.java.gui.Utils;
import main.java.gui.bids.reports.BidsReport;
import main.java.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * This panel view table of Bids and can Create, Modify, and Delete this entries in DB
 */
public class BidsListPanel extends JPanel {
	private JButton createBidButton;
	private JButton editBidButton;
	private JButton deleteBidButton;
	private JComboBox<FinanceDepartmentModel> financeDepartmentBox;
	private JComboBox<DepartmentModel> departmentBox;
	private BidsTableModel bidsTableModel;
	private JTable bidsTable;
	private final FinanceDepartmentModel emptyFinanceDepartmentModel = new FinanceDepartmentModel();
	private final DepartmentModel emptyDepartmentModel = new DepartmentModel();
	private final BidModel emptyBidModel = new BidModel();
	private BidsListPanelListener listener;
	private CreateBidDialog createBidDialog;
	private BidModel selectedBidModel;
	private JLabel sumLabel;
	private FinanceDepartmentModel selectedFinanceModel;
	private DepartmentModel selectedDepartmentModel;
	private MainFrame parent;
    private boolean useUserDepartment = false;

	public BidsListPanel(MainFrame parent) {

		this.parent = parent;

		Dimension buttonDim = new Dimension(25, 25);

		selectedBidModel = emptyBidModel;
		selectedFinanceModel = emptyFinanceDepartmentModel;
		selectedDepartmentModel = emptyDepartmentModel;

		createBidDialog = new CreateBidDialog(parent);

		createBidButton = new JButton();
		createBidButton.setToolTipText(Labels.getProperty("createBid"));
		createBidButton.setIcon(Utils.createIcon("/images/Add16.gif"));
		createBidButton.setPreferredSize(buttonDim);
		createBidButton.setEnabled(true);

		editBidButton = new JButton();
		editBidButton.setToolTipText(Labels.getProperty("editBid"));
		editBidButton.setIcon(Utils.createIcon("/images/Edit16.gif"));
		editBidButton.setPreferredSize(buttonDim);
		editBidButton.setEnabled(true);

		deleteBidButton = new JButton();
		deleteBidButton.setToolTipText(Labels.getProperty("deleteBid"));
		deleteBidButton.setIcon(Utils.createIcon("/images/Delete16.gif"));
		deleteBidButton.setPreferredSize(buttonDim);
		deleteBidButton.setEnabled(true);

		financeDepartmentBox = new JComboBox<>();
		financeDepartmentBox.setPreferredSize(new Dimension(200, 25));
		financeDepartmentBox.addItem(emptyFinanceDepartmentModel);

		departmentBox = new JComboBox<>();
		departmentBox.setPreferredSize(new Dimension(250, 25));
		departmentBox.addItem(emptyDepartmentModel);

		bidsTableModel = new BidsTableModel();
		bidsTable = new JTable(bidsTableModel);

		sumLabel = new JLabel(0 + Labels.withSpaceBefore("uah"));

		createLayout();

		createBidButton.addActionListener(e -> {
			setIDsToCreateBidDialog();
			createBidDialog.setVisible(true);
		});

		editBidButton.addActionListener(e -> {
			if (!selectedBidModel.equals(emptyBidModel)) {
				setIDsToCreateBidDialog();
				createBidDialog.loadToDialog(selectedBidModel);
			}
			selectedBidModel = emptyBidModel;
		});

		deleteBidButton.addActionListener(e -> {
			if (selectedBidModel != emptyBidModel && listener != null) {
				if (!isSelectedFinanceDepartmentModelEmpty()) {
					listener.bidDeleteEventOccurred(selectedBidModel, selectedDepartmentModel.getModelId(),
							selectedFinanceModel.getModelId());
				} else if (!isSelectedDepartmentModelEmpty()) {
					listener.bidDeleteEventOccurred(selectedBidModel, selectedDepartmentModel.getModelId());
				} else if (isSelectedDepartmentModelEmpty()) {
					listener.bidDeleteEventOccurred(selectedBidModel);
				}

			}
			selectedBidModel = emptyBidModel;
		});

		financeDepartmentBox.addActionListener(e -> {
			if (financeDepartmentBox.getSelectedItem() != null && listener != null) {
				selectedFinanceModel = (FinanceDepartmentModel) financeDepartmentBox.getSelectedItem();
				if (!isSelectedFinanceDepartmentModelEmpty()) {
					listener.financeDepartmentSelectionEventOccurred(selectedFinanceModel.getDepId(),
							selectedFinanceModel.getModelId());
				} else if (!isSelectedDepartmentModelEmpty()) {
					listener.selectAllOrdersBidsEventOccurred(selectedDepartmentModel.getModelId());
				}
			}
		});

		departmentBox.addActionListener(e -> {
            if(listener != null){
                financeDepartmentBox.removeAllItems();
                financeDepartmentBox.addItem(emptyFinanceDepartmentModel);
                selectedDepartmentModel = (DepartmentModel) departmentBox.getSelectedItem();
                if (!isSelectedDepartmentModelEmpty()) {
                    listener.departmentSelectionEventOccurred(selectedDepartmentModel.getModelId());
                } else if (isSelectedDepartmentModelEmpty()) {
                    listener.selectAllDepartmentsBidsEventOccurred();
                }
            }
		});

		bidsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent ev) {
				int row = bidsTable.rowAtPoint(ev.getPoint());
				bidsTable.getSelectionModel().setSelectionInterval(row, row);
				if (ev.getButton() == MouseEvent.BUTTON1) {
					selectedBidModel = (BidModel) bidsTable.getValueAt(row, 0);
				}
			}
		});
	}

	private void setIDsToCreateBidDialog() {
		if (!isSelectedFinanceDepartmentModelEmpty()) {
			createBidDialog.setCurrentDepartmentId(selectedDepartmentModel.getModelId());
			createBidDialog.setCurrentFinanceDepartmentId(selectedFinanceModel.getModelId());
		} else if (!isSelectedDepartmentModelEmpty()) {
			createBidDialog.setCurrentDepartmentId(selectedDepartmentModel.getModelId());
		}
	}

	private boolean isSelectedDepartmentModelEmpty() {
		return selectedDepartmentModel.equals(emptyDepartmentModel);
	}

	private boolean isSelectedFinanceDepartmentModelEmpty() {
		return selectedFinanceModel.equals(emptyFinanceDepartmentModel);
	}

	public void setSumLabel(BigDecimal sum) {
		sumLabel.setText(sum.setScale(2, RoundingMode.CEILING) + Labels.withSpaceBefore("uah"));
	}

    public void setUseUserDepartment(){
        useUserDepartment = true;
    }

	public void setDepartmentBoxData(List<DepartmentModel> db) {
		for (DepartmentModel model : db) {
			departmentBox.addItem(model);
			createBidDialog.addToDepartmentBox(model);
		}
		if(useUserDepartment){
            Utils.setBoxFromID(departmentBox, LoginData.getInstance().getDepId());
            departmentBox.setEnabled(false);
        }
	}

	public void setFinanceDepartmentBoxData(List<FinanceDepartmentModel> db) {
		for (FinanceDepartmentModel model : db) {
			financeDepartmentBox.addItem(model);
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

	public void setProducerBoxData(java.util.List<ProducerModel> db) {
		createBidDialog.setProducerBoxData(db);
	}

	public void setSupplierBoxData(java.util.List<SupplierModel> db) {
		createBidDialog.setSupplierBoxData(db);
	}

	public void setAmUnitsBoxData(List<AmountUnitsModel> db) {
		createBidDialog.setAmUnitsBoxData(db);
	}

	public CreateBidDialog getCreateBidDialog() {
		return createBidDialog;
	}

	private void createLayout() {
		JPanel topPanel = new JPanel();
		JPanel sumPanel = new JPanel();

		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		topPanel.setBorder(BorderFactory.createEtchedBorder());
		topPanel.add(createBidButton);
		topPanel.add(editBidButton);
		topPanel.add(deleteBidButton);
		topPanel.add(new JLabel(Labels.getProperty("order")));
		topPanel.add(financeDepartmentBox);
		topPanel.add(new JLabel(Labels.getProperty("department")));
		topPanel.add(departmentBox);

		sumPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		sumPanel.add(new JLabel(Labels.withColon("totalPrice2")));
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
		for (int row = 0; row < bidsTable.getRowCount(); row++) {
			BidModel md = (BidModel) bidsTable.getValueAt(row, 0);
			BidsReportModel reportModel = new BidsReportModel(md.getDepName(), md.getFinanceName(),
					md.getCpvCode(), md.getCpvUkr(), md.getBidDesc(), md.getCreatedDate(), md.getProducerName(),
					md.getCatNum(), md.getSupplierName(), md.getAmUnitName(), md.getOnePrice(), md.getAmount());
			list.add(reportModel);
		}
		new BidsReport(Collections.unmodifiableList(list));
	}
}
