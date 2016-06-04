package gui.bids;

import gui.Labels;
import gui.MainFrame;
import gui.Utils;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by laban on 26.05.2016.
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

    public BidsListPanel (MainFrame parent){

        Dimension buttonDim = new Dimension(25, 25);

        createBidDialog = new CreateBidDialog(parent);

        createBidButton = new JButton();
        createBidButton.setToolTipText(Labels.getProperty("createBid"));
        createBidButton.setIcon(Utils.createIcon("/images/Add16.gif"));
        createBidButton.setPreferredSize(buttonDim);
        createBidButton.setEnabled(true);
        createBidButton.addActionListener(e -> createBidDialog.setVisible(true));

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
        financeDepartmentBox.setPreferredSize(new Dimension(150,25));
        financeDepartmentBox.addItem(emptyFinanceDepartmentModel);
        financeDepartmentBox.addActionListener(e -> {
            if(financeDepartmentBox.getSelectedItem() != null) {
                FinanceDepartmentModel selectedFinanceModel = (FinanceDepartmentModel) financeDepartmentBox.getSelectedItem();
                if (!selectedFinanceModel.equals(emptyFinanceDepartmentModel)) {
                    listener.financeDepartmentSelectionEventOccurred(selectedFinanceModel.getDepId(), selectedFinanceModel.getOrderId());
                }
            }
        });

        departmentBox = new JComboBox<>();
        departmentBox.setPreferredSize(new Dimension(200,25));
        departmentBox.addItem(emptyDepartmentModel);
        departmentBox.addActionListener(e -> {
            financeDepartmentBox.removeAllItems();
            financeDepartmentBox.addItem(emptyFinanceDepartmentModel);
            DepartmentModel selctedModel = (DepartmentModel) departmentBox.getSelectedItem();
            if(!selctedModel.equals(emptyDepartmentModel)){
                listener.departmentSelectionEventOccurred(selctedModel.getDepId());
            }
        });

        bidsTableModel = new BidsTableModel();
        bidsTable = new JTable(bidsTableModel);

        createLayout();


    }

    public void setDepartmentBoxData(java.util.List<DepartmentModel> db){
        for(DepartmentModel model : db){
            departmentBox.addItem(model);
            createBidDialog.addToDepartmentBox(model);
        }

    }

    public void setFinanceDepartmentBoxData(List<FinanceDepartmentModel> db){
        for(FinanceDepartmentModel model : db){
            financeDepartmentBox.addItem(model);
        }
    }

    public void setBidsTableData(List<BidModel> db){
        bidsTableModel.setData(db);
    }

    public void refreshBidsTableData(){
        bidsTableModel.fireTableDataChanged();
    }

    public void setBidsListPanelListener(BidsListPanelListener listener){
        this.listener = listener;
    }

    public void setProducerBoxData(java.util.List<ProducerModel> db){
        createBidDialog.setProducerBoxData(db);
    }

    public void setSupplierBoxData(java.util.List<SupplierModel> db){
        createBidDialog.setSupplierBoxData(db);
    }

    public void setAmUnitsBoxData(List<AmountUnitsModel> db){
        createBidDialog.setAmUnitsBoxData(db);
    }

    public CreateBidDialog getCreateBidDialog() {
        return createBidDialog;
    }

    private void createLayout() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        panel.setBorder(BorderFactory.createEtchedBorder());

        panel.add(createBidButton);
        panel.add(editBidButton);
        panel.add(deleteBidButton);
        panel.add(new JLabel(Labels.getProperty("order")));
        panel.add(financeDepartmentBox);
        panel.add(new JLabel(Labels.getProperty("department")));
        panel.add(departmentBox);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(bidsTable), BorderLayout.CENTER);
    }
}
