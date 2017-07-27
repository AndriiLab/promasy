package com.github.andriilab.promasy.gui.reports.cpv;

import com.github.andriilab.promasy.gui.commons.Labels;
import com.github.andriilab.promasy.model.models.report.CpvAmountReportModel;

import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.util.List;

/**
 * Table com.github.andriilab.promasy.model for {@link com.github.andriilab.promasy.model.models.CpvAmountModel}
 */
class CpvAmountTableModel extends AbstractTableModel {

    private List<CpvAmountReportModel> db;

    private final String[] colNames = {Labels.getProperty("cpv"),
            Labels.getProperty("bidType"),
            Labels.getProperty("totalPrice2"),
            Labels.getProperty("cpvAmountReport.col4Title").toLowerCase(),
            Labels.getProperty("cpvAmountReport.col5Title").toLowerCase(),
            Labels.getProperty("cpvAmountReport.col6Title").toLowerCase()};

    public CpvAmountTableModel() {
    }

    public void setData(List<CpvAmountReportModel> db) {
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
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CpvAmountReportModel model = db.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return model.getCpvNumber() + " " + model.getCpvName();
            case 1:
                return model.getBidType();
            case 2:
                return model.getTotalPrice();
            case 3:
                return model.getProcurementProcedure();
            case 4:
                return model.getStartDate();
            case 5:
                return model.getNotation();
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
            case 3:
                return String.class;
            case 4:
                return String.class;
            case 5:
                return String.class;
            default:
                return CpvAmountReportModel.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex > 2;
    }

    @Override
    public void setValueAt(Object object, int rowIndex, int columnIndex) {
        CpvAmountReportModel model = db.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return;
            case 1:
                return;
            case 2:
                return;
            case 3:
                model.setProcurementProcedure(object.toString());
                return;
            case 4:
                model.setStartDate((String) object);
                return;
            case 5:
                model.setNotation((String) object);
        }
    }
}
