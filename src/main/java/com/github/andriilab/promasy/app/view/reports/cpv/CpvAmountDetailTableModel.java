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
        return switch (columnIndex) {
            case 0 -> bidModel.getFinances().getSubdepartment().getDepartment().toString();
            case 1 -> bidModel.getFinances().getSubdepartment().toString();
            case 2 -> bidModel.getDescription();
            case 3 -> bidModel.getOnePrice();
            case 4 -> bidModel.getAmount();
            case 5 -> bidModel.getTotalPrice();
            default -> bidModel;
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0, 2, 1 -> String.class;
            case 3, 5 -> BigDecimal.class;
            case 4 -> Integer.class;
            default -> Bid.class;
        };
    }
}
