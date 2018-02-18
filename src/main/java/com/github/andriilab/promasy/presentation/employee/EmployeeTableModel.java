package com.github.andriilab.promasy.presentation.employee;

import com.github.andriilab.promasy.domain.EmptyModel;
import com.github.andriilab.promasy.domain.organization.entities.Employee;
import com.github.andriilab.promasy.presentation.commons.Labels;

import javax.swing.table.AbstractTableModel;
import java.util.List;

class EmployeeTableModel extends AbstractTableModel{

    private List<Employee> db;

    private final String[] colNames = {Labels.getProperty("shortName"),
            Labels.getProperty("institute"),
                                    Labels.getProperty("department"),
                                    Labels.getProperty("subdepartment"),
                                    Labels.getProperty("role"),
                                    Labels.getProperty("isActive")};

	public EmployeeTableModel() {
	}

	public String getColumnName(int column) {
		return colNames[column];
	}

	public boolean isCellEditable(int row, int col) {
		switch (col) {
		case 5:
			return true;
		default:
			return false;
		}
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

		switch(col){
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
		}

        return EmptyModel.OBJECT;
	}

	public Class<?> getColumnClass(int columnIndex) {

		switch (columnIndex) {
		case 0:
            return Employee.class;
		case 1:
			return String.class;
		case 2:
			return String.class;
		case 3:
			return String.class;
		case 4:
			return String.class;
		case 5:
			return Boolean.class;
		default:
            return Object.class;
		}
	}
}
