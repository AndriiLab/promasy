package com.github.andriilab.promasy.app.view.bids;

import com.github.andriilab.promasy.domain.EmptyModel;
import com.github.andriilab.promasy.domain.bid.entities.Bid;
import com.github.andriilab.promasy.domain.bid.enums.Status;
import com.github.andriilab.promasy.app.commons.Labels;

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
        return switch (columnIndex) {
            case 0 -> model;
            case 1 -> model.getAmount();
            case 2 -> model.getOnePrice().setScale(2, RoundingMode.CEILING);
            case 3 ->
                    (model.getOnePrice().multiply(BigDecimal.valueOf(model.getAmount()))).setScale(2, RoundingMode.CEILING);
            case 4 -> model.getLastEditPersonName();
            case 5 -> model.getLastEditDate();
            case 6 -> model.getLastBidStatusModel().getStatus();
            default -> EmptyModel.OBJECT;
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> Bid.class;
            case 1 -> Integer.class;
            case 2, 3 -> BigDecimal.class;
            case 4 -> String.class;
            case 5 -> Timestamp.class;
            case 6 -> Status.class;
            default -> Object.class;
        };
    }
}
