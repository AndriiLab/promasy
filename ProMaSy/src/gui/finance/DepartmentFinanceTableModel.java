package gui.finance;

import gui.Labels;
import model.FinanceDepartmentModel;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Created by laban on 04.05.2016.
 */
public class DepartmentFinanceTableModel extends AbstractTableModel {

    private List<FinanceDepartmentModel> db;
    private String[] colNames = {Labels.getProperty("department"),
            Labels.getProperty("manager"),
            Labels.getProperty("financeAmount"),
            Labels.getProperty("financeLeft")
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
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        FinanceDepartmentModel model = db.get(rowIndex);
        switch (columnIndex){
            case 0:
                return model;
            case 1:
                return model.getEmpName();
            case 2:
                return model.getTotalAmount();
            case 3:
                return model.getLeftAmount();
        }
        return null;
    }
}
