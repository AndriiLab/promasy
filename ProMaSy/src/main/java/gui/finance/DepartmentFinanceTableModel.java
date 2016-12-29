package gui.finance;

import gui.Labels;
import model.FinanceDepartmentModel;

import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * TableModel for Department Finance table
 */
class DepartmentFinanceTableModel extends AbstractTableModel {

    private List<FinanceDepartmentModel> db;
    private String[] colNames = {Labels.getProperty("department"),
            Labels.getProperty("manager"),
            Labels.getProperty("financeAmount"),
            Labels.getProperty("financeLeft"),
            "main/java/model"
    };

    public DepartmentFinanceTableModel(){

    }

    public String getColumnName(int column) {
        return colNames[column];
    }

    public void setData(List<FinanceDepartmentModel> db) {
        this.db = db;
    }

    @Override
    public int getRowCount() {
        return db.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        FinanceDepartmentModel model = db.get(rowIndex);
        switch (columnIndex){
            case 0:
                return model.getDepName();
            case 1:
                return model.getEmpName();
            case 2:
                BigDecimal totalAmount = model.getTotalAmount();
                if (totalAmount == null){
                    return BigDecimal.ZERO;
                } else return totalAmount.setScale(2, RoundingMode.CEILING);
            case 3:
                BigDecimal leftAmount = model.getLeftAmount();
                if (leftAmount == null){
                    return BigDecimal.ZERO;
                } else return leftAmount.setScale(2, RoundingMode.CEILING);
            case 4:
                return model;
        }
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex){
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return BigDecimal.class;
            case 3:
                return BigDecimal.class;
            case 4:
                return FinanceDepartmentModel.class;
            default:
                return null;
        }
    }
}
