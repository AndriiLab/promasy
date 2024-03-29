package com.github.andriilab.promasy.app.view.employee;

import com.github.andriilab.promasy.domain.EmptyModel;
import com.github.andriilab.promasy.domain.organization.entities.Employee;
import com.github.andriilab.promasy.app.commons.Labels;

import javax.swing.table.AbstractTableModel;
import java.util.List;

class EmployeeTableModel extends AbstractTableModel {

    private List<Employee> db;

    private final String[] colNames = {Labels.getProperty("shortName"),
            Labels.getProperty("institute"),
            Labels.getProperty("department"),
            Labels.getProperty("subdepartment"),
            Labels.getProperty("role"),
            Labels.getProperty("isActive")};

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return col == 5;
    }

    public void setData(List<Employee> db) {
        this.db = db;
    }

    public int getColumnCount() {
        return 6;
    }

    public int getRowCount() {
        return db.size();
    }

    public Object getValueAt(int row, int col) {
        Employee model = db.get(row);

        switch (col) {
            case 0:
                return model;
            case 1:
                return model.getSubdepartment().getDepartment().getInstitute().getInstName();
            case 2:
                return model.getSubdepartment().getDepartment().getDepName();
            case 3:
                return model.getSubdepartment().getSubdepName();
            case 4:
                return model.getRole().toString();
            case 5:
                return model.isActive();
            default:
                return EmptyModel.OBJECT;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {

        switch (columnIndex) {
            case 0:
                return Employee.class;
            case 1:
            case 2:
            case 3:
            case 4:
                return String.class;
            case 5:
                return Boolean.class;
            default:
                return Object.class;
        }
    }
}
