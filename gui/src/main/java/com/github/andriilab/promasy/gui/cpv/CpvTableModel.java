package com.github.andriilab.promasy.gui.cpv;

import com.github.andriilab.promasy.domain.EmptyModel;
import com.github.andriilab.promasy.domain.cpv.entities.Cpv;
import com.github.andriilab.promasy.gui.commons.Labels;

import javax.swing.table.AbstractTableModel;
import java.util.List;

class CpvTableModel extends AbstractTableModel {

    private List<Cpv> db;

    private final String[] colNames = {Labels.getProperty("cpv"),
            Labels.getProperty("ukrainianName"),
            Labels.getProperty("englishName")};

    public CpvTableModel() {
    }

    public String getColumnName(int column) {
        return colNames[column];
    }

    public void setData(List<Cpv> db) {
        this.db = db;
    }

    public int getColumnCount() {
        return 3;
    }

    public int getRowCount() {
        return db.size();
    }

    public Object getValueAt(int row, int col) {
        Cpv cpv = db.get(row);
        switch (col) {
            case 0:
                return cpv.getCpvId();
            case 1:
                return cpv;
            case 2:
                return cpv.getCpvEng();
        }
        return EmptyModel.OBJECT;
    }

}
