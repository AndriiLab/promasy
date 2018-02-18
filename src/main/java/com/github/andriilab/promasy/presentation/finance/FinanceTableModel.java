package com.github.andriilab.promasy.presentation.finance;

import com.github.andriilab.promasy.data.queries.finance.GetFinanceLeftAmountQuery;
import com.github.andriilab.promasy.domain.EmptyModel;
import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.finance.entities.Finance;
import com.github.andriilab.promasy.presentation.commons.Labels;

import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * TableModel for Finance table
 */
class FinanceTableModel extends AbstractTableModel {

    private List<Finance> db = new LinkedList<>();

    private FinanceTableModelListener listener;

    private final String[] colNames = {
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
        listener = new EmptyFinanceTableModelListener();
    }

    public void setListener(FinanceTableModelListener listener) {
        this.listener = listener;
    }

    public String getColumnName(int column) {
        return colNames[column];
    }

    public List<Finance> getData() {
        return db;
    }

    public void setData(List<Finance> db) {
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
        Finance model = db.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return model.toString();
            case 1:
                BigDecimal materialsAmount = model.getTotalAmount(BidType.MATERIALS);
                if (materialsAmount == null) {
                    return BigDecimal.ZERO;
                } else {
                    return materialsAmount.setScale(2, RoundingMode.CEILING);
                }
            case 2:
                BigDecimal materialsLeft = listener.getLeftAmount(new GetFinanceLeftAmountQuery(model, BidType.MATERIALS));
                return materialsLeft.setScale(2, RoundingMode.CEILING);
            case 3:
                BigDecimal equipmentAmount = model.getTotalAmount(BidType.EQUIPMENT);
                if (equipmentAmount == null) {
                    return BigDecimal.ZERO;
                } else {
                    return equipmentAmount.setScale(2, RoundingMode.CEILING);
                }
            case 4:
                BigDecimal equipmentLeft = listener.getLeftAmount(new GetFinanceLeftAmountQuery(model, BidType.EQUIPMENT));
                return equipmentLeft.setScale(2, RoundingMode.CEILING);
            case 5:
                BigDecimal servicesAmount = model.getTotalAmount(BidType.SERVICES);
                if (servicesAmount == null) {
                    return BigDecimal.ZERO;
                } else {
                    return servicesAmount.setScale(2, RoundingMode.CEILING);
                }
            case 6:
                BigDecimal servicesLeft = listener.getLeftAmount(new GetFinanceLeftAmountQuery(model, BidType.SERVICES));
                    return servicesLeft.setScale(2, RoundingMode.CEILING);
            case 7:
                return model.getStartDate();
            case 8:
                return model.getEndDate();
            case 9:
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
                return BigDecimal.class;
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
                return Date.class;
            case 8:
                return Date.class;
            case 9:
                return Finance.class;
            default:
                return Object.class;
        }
    }
}
