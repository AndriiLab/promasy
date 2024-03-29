package com.github.andriilab.promasy.app.view.reports.cpv;

import com.github.andriilab.promasy.domain.bid.entities.Bid;
import com.github.andriilab.promasy.domain.bid.entities.CpvAmount;
import com.github.andriilab.promasy.app.commons.Labels;

import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;

/**
 * Table for detailed view of bids from {@link CpvAmount}
 */
class CpvAmountDetailTableModel extends AbstractTableModel {

    private CpvAmount model;

    private final String[] colNames = {Labels.getProperty("department"),
            Labels.getProperty("subdepartment"),
            Labels.getProperty("description"),
            Labels.getProperty("oneUnitPrice"),
            Labels.getProperty("amount"),
            Labels.getProperty("bidsReport.totPrice")};

    public void setData(CpvAmount model) {
        this.model = model;
    }

    @Override
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
        Bid bidModel = model.getBids().get(rowIndex);
        switch (columnIndex) {
            case 0:
                return bidModel.getFinances().getSubdepartment().getDepartment().toString();
            case 1:
                return bidModel.getFinances().getSubdepartment().toString();
            case 2:
                return bidModel.getDescription();
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
            case 2:
            case 1:
                return String.class;
            case 3:
            case 5:
                return BigDecimal.class;
            case 4:
                return Integer.class;
            default:
                return Bid.class;
        }
    }
}
