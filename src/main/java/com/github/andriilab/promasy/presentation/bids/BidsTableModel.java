package com.github.andriilab.promasy.presentation.bids;

import com.github.andriilab.promasy.domain.EmptyModel;
import com.github.andriilab.promasy.domain.bid.entities.Bid;
import com.github.andriilab.promasy.domain.bid.enums.Status;
import com.github.andriilab.promasy.presentation.commons.Labels;

import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

/**
 * Table com.github.andriilab.promasy.domain.model for {@link BidsListPanel}
 */
class BidsTableModel extends AbstractTableModel {

    private List<Bid> db = new LinkedList<>();

    private final String[] colNames = {Labels.getProperty("description"),
            Labels.getProperty("amount"),
            Labels.getProperty("oneUnitPrice"),
            Labels.getProperty("totalPrice"),
            Labels.getProperty("role.user"),
            Labels.getProperty("dateModified"),
            Labels.getProperty("status")};

    public BidsTableModel() {

    }

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    public List<Bid> getData() {
        return db;
    }

    public void setData(List<Bid> db) {
        this.db = db;
    }

    @Override
    public int getRowCount() {
        return db.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Bid model = db.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return model;
            case 1:
                return model.getAmount();
            case 2:
                return model.getOnePrice().setScale(2, RoundingMode.CEILING);
            case 3:
                return (model.getOnePrice().multiply(BigDecimal.valueOf(model.getAmount()))).setScale(2, RoundingMode.CEILING);
            case 4:
                return model.getLastEditPersonName();
            case 5:
                return model.getLastEditDate();
            case 6:
                return model.getLastBidStatusModel().getStatus();
        }

        return EmptyModel.OBJECT;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Bid.class;
            case 1:
                return Integer.class;
            case 2:
                return BigDecimal.class;
            case 3:
                return BigDecimal.class;
            case 4:
                return String.class;
            case 5:
                return Timestamp.class;
            case 6:
                return Status.class;
            default:
                return Object.class;
        }
    }
}
