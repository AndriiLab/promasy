package com.github.andriilab.promasy.presentation.finance;

import com.github.andriilab.promasy.data.queries.financepartment.GetFinanceDepartmentLeftAmountQuery;
import com.github.andriilab.promasy.domain.EmptyModel;
import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;
import com.github.andriilab.promasy.presentation.commons.Labels;

import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;

/**
 * TableModel for Department Finance table
 */
class DepartmentFinanceTableModel extends AbstractTableModel {
    private DepartmentFinanceTableModelListener listener;

    private List<FinanceDepartment> db = new LinkedList<>();
    private final String[] colNames = {
            Labels.getProperty("department"),
            Labels.getProperty("subdepartment"),
            Labels.getProperty("materialsAmount"),
            Labels.getProperty("materialsLeft"),
            Labels.getProperty("equipmentAmount"),
            Labels.getProperty("equipmentLeft"),
            Labels.getProperty("servicesAmount"),
            Labels.getProperty("servicesLeft"),
            Labels.getProperty("financeName")
    };

    public DepartmentFinanceTableModel() {
        listener = new EmptyDepartmentFinanceTableModelListener();
    }

    public void setListener(DepartmentFinanceTableModelListener listener) {
        this.listener = listener;
    }

    public String getColumnName(int column) {
        return colNames[column];
    }

    public void setData(List<FinanceDepartment> db) {
        this.db = db;
    }

    @Override
    public int getRowCount() {
        return db.size();
    }

    @Override
    public int getColumnCount() {
        return 9;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        FinanceDepartment model = db.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return model.getSubdepartment().getDepartment().getDepName();
            case 1:
                return model.getSubdepartment().getSubdepName();
            case 2:
                BigDecimal materialsAmount = model.getTotalAmount(BidType.MATERIALS);
                if (materialsAmount == null) {
                    return BigDecimal.ZERO;
                } else {
                    return materialsAmount.setScale(2, RoundingMode.CEILING);
                }
            case 3:
                BigDecimal materialsLeft = listener.getLeftAmount(new GetFinanceDepartmentLeftAmountQuery(model, BidType.MATERIALS));
                return materialsLeft.setScale(2, RoundingMode.CEILING);
            case 4:
                BigDecimal equipmentAmount = model.getTotalAmount(BidType.EQUIPMENT);
                if (equipmentAmount == null) {
                    return BigDecimal.ZERO;
                } else {
                    return equipmentAmount.setScale(2, RoundingMode.CEILING);
                }
            case 5:
                BigDecimal equipmentLeft = listener.getLeftAmount(new GetFinanceDepartmentLeftAmountQuery(model, BidType.EQUIPMENT));
                return equipmentLeft.setScale(2, RoundingMode.CEILING);
            case 6:
                BigDecimal servicesAmount = model.getTotalAmount(BidType.SERVICES);
                if (servicesAmount == null) {
                    return BigDecimal.ZERO;
                } else {
                    return servicesAmount.setScale(2, RoundingMode.CEILING);
                }
            case 7:
                BigDecimal servicesLeft = listener.getLeftAmount(new GetFinanceDepartmentLeftAmountQuery(model, BidType.SERVICES));
                return servicesLeft.setScale(2, RoundingMode.CEILING);
            case 8:
                return model;
            default:
                return EmptyModel.OBJECT;
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
                return FinanceDepartment.class;
            default:
                return Object.class;
        }
    }
}
