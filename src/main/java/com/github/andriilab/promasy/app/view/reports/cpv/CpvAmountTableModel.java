package com.github.andriilab.promasy.app.view.reports.cpv;

import com.github.andriilab.promasy.data.reports.models.CpvAmountReportModel;
import com.github.andriilab.promasy.domain.bid.entities.CpvAmount;
import com.github.andriilab.promasy.app.commons.Labels;

import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.util.List;

/**
 * Table com.github.andriilab.promasy.domain.model for {@link CpvAmount}
 */
class CpvAmountTableModel extends AbstractTableModel {

    private List<CpvAmountReportModel> db;

    private final String[] colNames = {Labels.getProperty("cpv"),
            Labels.getProperty("bidType"),
            Labels.getProperty("totalPrice2"),
            Labels.getProperty("cpvAmountReport.col4Title").toLowerCase(),
            Labels.getProperty("cpvAmountReport.col5Title").toLowerCase(),
            Labels.getProperty("cpvAmountReport.col6Title").toLowerCase()};

    public void setData(List<CpvAmountReportModel> db) {
        this.db = db;
    }

    @Override
    public String getColumnName(int column) {
        return colNames[column];
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
        CpvAmountReportModel model = db.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> model.getCpvNumber() + " " + model.getCpvName();
            case 1 -> model.getBidType();
            case 2 -> model.getTotalPrice();
            case 3 -> model.getProcurementProcedure();
            case 4 -> model.getStartDate();
            case 5 -> model.getNotation();
            default -> model;
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0, 5, 4, 3, 1 -> String.class;
            case 2 -> BigDecimal.class;
            default -> CpvAmountReportModel.class;
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex > 2;
    }

    @Override
    public void setValueAt(Object object, int rowIndex, int columnIndex) {
        CpvAmountReportModel model = db.get(rowIndex);
        switch (columnIndex) {
            case 3:
                model.setProcurementProcedure(object.toString());
                return;
            case 4:
                model.setStartDate((String) object);
                return;
            case 5:
                model.setNotation((String) object);
                return;
            default:
                return;
        }
    }
}
