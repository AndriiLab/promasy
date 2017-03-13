package gui.cpvAmount;

import gui.commons.Labels;
import model.models.BidModel;
import model.models.CpvAmountModel;

import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;

/**
 * Table for detailed view of bids from {@link model.models.CpvAmountModel}
 */
public class CpvAmountDetailTableModel extends AbstractTableModel {

    private CpvAmountModel model;

    private String[] colNames = {Labels.getProperty("department"),
            Labels.getProperty("subdepartment"),
            Labels.getProperty("description"),
            Labels.getProperty("oneUnitPrice"),
            Labels.getProperty("amount"),
            Labels.getProperty("bidsReport.totPrice")};

    public CpvAmountDetailTableModel() {
    }

    public void setData(CpvAmountModel model) {
        this.model = model;
    }

    public String getColumnName(int column) {
        return colNames[column];
    }

    @Override
    public int getRowCount() {
        return model.getBids().size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        BidModel bidModel = model.getBids().get(rowIndex);
        switch (columnIndex) {
            case 0:
                return bidModel.getFinances().getSubdepartment().getDepartment().toString();
            case 1:
                return bidModel.getFinances().getSubdepartment().toString();
            case 2:
                return bidModel.getBidDesc();
            case 3:
                return bidModel.getOnePrice();
            case 4:
                return bidModel.getAmount();
            case 5:
                return bidModel.getTotalPrice();
            default:
                return bidModel;
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
                return String.class;
            case 3:
                return BigDecimal.class;
            case 4:
                return Integer.class;
            case 5:
                return BigDecimal.class;
            default:
                return BidModel.class;
        }
    }
}
