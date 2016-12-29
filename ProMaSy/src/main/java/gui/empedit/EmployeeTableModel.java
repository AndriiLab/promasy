package gui.empedit;

import gui.Labels;
import model.EmployeeModel;

import javax.swing.table.AbstractTableModel;
import java.util.List;

class EmployeeTableModel extends AbstractTableModel{

	private List<EmployeeModel> db;

	private String[] colNames = {Labels.getProperty("shortName"),
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

	public void setData(List<EmployeeModel> db){
		this.db = db;
	}

	public int getColumnCount() {
		return 6;
	}

	public int getRowCount() {
		return db.size();
	}

	public Object getValueAt(int row, int col) {
		EmployeeModel model = db.get(row);

		switch(col){
		case 0:
			return model;
		case 1:
			return model.getInstName();
		case 2:
			return model.getDepName();
		case 3:
			return model.getSubdepName();
		case 4:
			return model.getRoleName();
		case 5:
			return model.isActive();
		}

		return null;
	}

	public Class<?> getColumnClass(int columnIndex) {

		switch (columnIndex) {
		case 0:
			return EmployeeModel.class;
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
			return null;
		}
	}
}
