package gui.bids;

import gui.Labels;
import model.BidModel;

import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by laban on 26.05.2016.
 */
public class BidsTableModel extends AbstractTableModel {

    private List<BidModel> db;

    private String[] colNames = {Labels.getProperty("description"),
                                    Labels.getProperty("dateModified"),
                                    Labels.getProperty("isReceived"),
                                    Labels.getProperty("supplier"),
                                    Labels.getProperty("amount"),
                                    Labels.getProperty("oneUnitPrice"),
                                    Labels.getProperty("totalPrice")};

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
        return 7;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        BidModel model = db.get(rowIndex);
        switch (columnIndex){
            case 0:
                return model.getBidDesc();
            case 1:
                //TODO approprivate function to "date modified"
                return model.getCreatedDate();
            case 2:
                return model.isReceived();
            case 3:
                // TODO get supplier name
                return model.getSupplierId();
            case 4:
                return model.getAmount();
            case 5:
                return model.getOnePrice();
            case 6:
                //could be slow
                return (model.getOnePrice().multiply(new BigDecimal(model.getAmount())));
        }

        return null;
    }
}
