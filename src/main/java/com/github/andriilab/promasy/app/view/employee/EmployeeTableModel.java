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

        return switch (col) {
            case 0 -> model;
            case 1 -> model.getSubdepartment().getDepartment().getInstitute().getInstName();
            case 2 -> model.getSubdepartment().getDepartment().getDepName();
            case 3 -> model.getSubdepartment().getSubdepName();
            case 4 -> model.getRole().toString();
            case 5 -> model.isActive();
            default -> EmptyModel.OBJECT;
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {

        return switch (columnIndex) {
            case 0 -> Employee.class;
            case 1, 2, 3, 4 -> String.class;
            case 5 -> Boolean.class;
            default -> Object.class;
        };
    }
}
