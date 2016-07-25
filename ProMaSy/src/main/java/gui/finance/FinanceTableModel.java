package main.java.gui.finance;

import main.java.gui.Labels;
import main.java.gui.Utils;
import main.java.model.FinanceModel;

import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.util.List;

/**
 * Created by laban on 04.05.2016.
 */
class FinanceTableModel extends AbstractTableModel {

    private List<FinanceModel> db;

    private String[] colNames = {Labels.getProperty("orderNumber"),
            Labels.getProperty("orderName"),
            Labels.getProperty("financeAmount"),
            Labels.getProperty("financeLeft"),
            Labels.getProperty("dateStart"),
            Labels.getProperty("dateEnd"),};

    public FinanceTableModel(){

    }

    public String getColumnName(int column) {
        return colNames[column];
    }

    public void setData(List<FinanceModel> db) {
        this.db = db;
    }

    @Override
    public int getRowCount() {
        return db.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        FinanceModel model = db.get(rowIndex);
        switch (columnIndex){
            case 0:
                return model.getOrderNumber();
            case 1:
                return model;
            case 2:
                BigDecimal totalAmount = model.getTotalAmount();
                if (totalAmount == null){
                    return Utils.ZERO_VALUE;
                } else return totalAmount.setScale(2, RoundingMode.CEILING);
            case 3:
                BigDecimal leftAmount = model.getLeftAmount();
                if (leftAmount == null){
                    return Utils.ZERO_VALUE;
                } else return leftAmount.setScale(2, RoundingMode.CEILING);
            case 4:
                return model.getStartDate();
            case 5:
                return model.getEndDate();
        }
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex){
            case 0:
                return String.class;
            case 1:
                return FinanceModel.class;
            case 2:
                return BigDecimal.class;
            case 3:
                return BigDecimal.class;
            case 4:
                return Date.class;
            case 5:
                return Date.class;
            default:
                return null;
        }
    }
}
