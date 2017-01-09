package gui.bids.status;

import gui.Labels;
import model.enums.Status;
import model.models.BidModel;
import model.models.BidStatusModel;

import javax.swing.table.AbstractTableModel;
import java.sql.Timestamp;

/**
 * TableModel for holding {@link Status} data
 */
public class StatusTableModel extends AbstractTableModel {

    private BidModel bidModel;

    private String[] colNames = {Labels.getProperty("dateModified"),
            Labels.getProperty("status"),
            Labels.getProperty("user")};

    public StatusTableModel() {

    }

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    public void setBidModel(BidModel bidModel) {
        this.bidModel = bidModel;
    }

    @Override
    public int getRowCount() {
        return bidModel.getStatuses().size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        BidStatusModel model = bidModel.getStatuses().get(rowIndex);
        switch (columnIndex) {
            case 0:
                return model.getLastEditDate();
            case 1:
                return model.getStatus().getStatusDesc();
            case 2:
                return model.getLastEditPersonName();
        }
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Timestamp.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            default:
                return null;
        }
    }
}
