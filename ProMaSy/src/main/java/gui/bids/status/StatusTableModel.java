package gui.bids.status;

import gui.Labels;
import model.StatusModel;

import javax.swing.table.AbstractTableModel;
import java.sql.Timestamp;
import java.util.List;

/**
 * TableModel for holding {@link StatusModel} data
 */
public class StatusTableModel extends AbstractTableModel {

    private List<StatusModel> db;

    private String[] colNames = {Labels.getProperty("dateModified"),
            Labels.getProperty("status"),
            Labels.getProperty("user")};

    public StatusTableModel() {

    }

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    public void setData(List<StatusModel> db) {
        this.db = db;
    }

    @Override
    public int getRowCount() {
        return db.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        StatusModel model = db.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return model.getCreatedDate();
            case 1:
                return model.getStatusDesc();
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
