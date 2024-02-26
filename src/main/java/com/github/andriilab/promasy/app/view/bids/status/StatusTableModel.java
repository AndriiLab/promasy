package com.github.andriilab.promasy.app.view.bids.status;

import com.github.andriilab.promasy.domain.EmptyModel;
import com.github.andriilab.promasy.domain.bid.entities.Bid;
import com.github.andriilab.promasy.domain.bid.entities.BidStatus;
import com.github.andriilab.promasy.domain.bid.enums.Status;
import com.github.andriilab.promasy.app.commons.Labels;

import javax.swing.table.AbstractTableModel;
import java.sql.Timestamp;

/**
 * TableModel for holding {@link Status} data
 */
class StatusTableModel extends AbstractTableModel {

    private Bid bidModel;

    private final String[] colNames = {Labels.getProperty("dateModified"),
            Labels.getProperty("status"),
            Labels.getProperty("role.user")};

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    public void setBidModel(Bid bidModel) {
        this.bidModel = bidModel;
    }

    @Override
    public int getRowCount() {
        return bidModel.getStatuses().size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        BidStatus model = bidModel.getStatuses().get(rowIndex);
        return switch (columnIndex) {
            case 0 -> model.getLastEditDate();
            case 1 -> model.getStatus().getStatusDesc();
            case 2 -> model.getLastEditPersonName();
            default -> EmptyModel.OBJECT;
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> Timestamp.class;
            case 1, 2 -> String.class;
            default -> Object.class;
        };
    }
}
