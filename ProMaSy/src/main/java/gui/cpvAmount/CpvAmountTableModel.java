package gui.cpvAmount;

import gui.commons.Labels;
import model.models.CpvAmountModel;

import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.util.List;

/**
 * Table model for {@link model.models.CpvAmountModel}
 */
public class CpvAmountTableModel extends AbstractTableModel {

    private List<CpvAmountModel> db;

    private String[] colNames = {Labels.getProperty("cpv"),
            Labels.getProperty("bidType"),
            Labels.getProperty("totalPrice2")};

    public CpvAmountTableModel() {
    }

    public void setData(List<CpvAmountModel> db) {
        this.db = db;
    }

    public String getColumnName(int column) {
        return colNames[column];
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
        CpvAmountModel model = db.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return model.getCpv().getCpvId() + " " + model.getCpv().getCpvUkr();
            case 1:
                return model.getType().getBidTypeName();
            case 2:
                return model.getTotalAmount();
            default:
                return model;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return BigDecimal.class;
            default:
                return CpvAmountModel.class;
        }
    }
}
