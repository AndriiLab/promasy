package gui.finance;

import gui.Labels;
import model.enums.BidType;
import model.models.FinanceModel;

import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.util.List;

/**
 * TableModel for Finance table
 */
class FinanceTableModel extends AbstractTableModel {

    private List<FinanceModel> db;

    private String[] colNames = {
            Labels.getProperty("financeNumber"),
            Labels.getProperty("financeName"),
            Labels.getProperty("materialsAmount"),
            Labels.getProperty("materialsLeft"),
            Labels.getProperty("equipmentAmount"),
            Labels.getProperty("equipmentLeft"),
            Labels.getProperty("servicesAmount"),
            Labels.getProperty("servicesLeft"),
            Labels.getProperty("dateStart"),
            Labels.getProperty("dateEnd"),};

    public FinanceTableModel() {

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
        return 10;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        FinanceModel model = db.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return model.getFinanceNumber();
            case 1:
                return model;
            case 2:
                BigDecimal materialsAmount = model.getTotalAmount(BidType.MATERIALS);
                if (materialsAmount == null) {
                    return BigDecimal.ZERO;
                } else return materialsAmount.setScale(2, RoundingMode.CEILING);
            case 3:
                BigDecimal materialsLeft = model.getLeftAmount(BidType.MATERIALS);
                if (materialsLeft == null) {
                    return BigDecimal.ZERO;
                } else return materialsLeft.setScale(2, RoundingMode.CEILING);
            case 4:
                BigDecimal equipmentAmount = model.getTotalAmount(BidType.EQUIPMENT);
                if (equipmentAmount == null) {
                    return BigDecimal.ZERO;
                } else return equipmentAmount.setScale(2, RoundingMode.CEILING);
            case 5:
                BigDecimal equipmentLeft = model.getLeftAmount(BidType.EQUIPMENT);
                if (equipmentLeft == null) {
                    return BigDecimal.ZERO;
                } else return equipmentLeft.setScale(2, RoundingMode.CEILING);
            case 6:
                BigDecimal servicesAmount = model.getTotalAmount(BidType.SERVICES);
                if (servicesAmount == null) {
                    return BigDecimal.ZERO;
                } else return servicesAmount.setScale(2, RoundingMode.CEILING);
            case 7:
                BigDecimal servicesLeft = model.getLeftAmount(BidType.SERVICES);
                if (servicesLeft == null) {
                    return BigDecimal.ZERO;
                } else return servicesLeft.setScale(2, RoundingMode.CEILING);
            case 8:
                return model.getStartDate();
            case 9:
                return model.getEndDate();
            default:
                return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return FinanceModel.class;
            case 2:
                return BigDecimal.class;
            case 3:
                return BigDecimal.class;
            case 4:
                return BigDecimal.class;
            case 5:
                return BigDecimal.class;
            case 6:
                return BigDecimal.class;
            case 7:
                return BigDecimal.class;
            case 8:
                return Date.class;
            case 9:
                return Date.class;
            default:
                return null;
        }
    }
}
