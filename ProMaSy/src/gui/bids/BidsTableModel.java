package gui.bids;

import gui.Labels;
import model.BidModel;

import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

/**
 * Created by laban on 26.05.2016.
 */
class BidsTableModel extends AbstractTableModel {

    private List<BidModel> db;

    private String[] colNames = {Labels.getProperty("description"),
                                    Labels.getProperty("dateModified"),
                                    Labels.getProperty("isReceived"),
                                    Labels.getProperty("supplier"),
                                    Labels.getProperty("amount"),
                                    Labels.getProperty("oneUnitPrice"),
                                    Labels.getProperty("totalPrice"),
                                    Labels.getProperty("customer")};

    public BidsTableModel(){

    }

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    public void setData(List<BidModel> db) {
        this.db = db;
    }

    @Override
    public int getRowCount() {
        return db.size();
    }

    @Override
    public int getColumnCount() {
        return 8;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        BidModel model = db.get(rowIndex);
        switch (columnIndex){
            case 0:
                return model;
            case 1:
                //TODO appropriate function to "date modified"
                return model.getCreatedDate();
            case 2:
                return model.isReceived();
            case 3:
                return model.getSupplierName();
            case 4:
                return model.getAmount();
            case 5:
                return model.getOnePrice();
            case 6:
                //could be slow
                return (model.getOnePrice().multiply(new BigDecimal(model.getAmount())));
            case 7:
                return model.getCustormerName();
        }

        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex){
            case 0:
                return String.class;
            case 1:
                return Date.class;
            case 2:
                return Boolean.class;
            case 3:
                return String.class;
            case 4:
                return Integer.class;
            case 5:
                return BigDecimal.class;
            case 6:
                return BigDecimal.class;
            case 7:
                return String.class;
            default:
                return null;
        }
    }
}
