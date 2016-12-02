package main.java.gui.cpv;

import main.java.gui.Labels;
import main.java.model.CPVModel;

import javax.swing.table.AbstractTableModel;
import java.util.List;

class CpvTableModel extends AbstractTableModel {

	private List<CPVModel> db;

	private String[] colNames = {Labels.getProperty("CPVCode"),
									Labels.getProperty("UkrainianName"),
									Labels.getProperty("EnglishName")};

	public CpvTableModel() {
	}

	public String getColumnName(int column) {
		return colNames[column];
	}

	public void setData(List<CPVModel> db) {
		this.db = db;
	}

	public int getColumnCount() {
		return 3;
	}

	public int getRowCount() {
		return db.size();
	}

	public Object getValueAt(int row, int col) {
		CPVModel cpv = db.get(row);
		switch (col) {
		case 0:
			return cpv.getCpvId();
		case 1:
			return cpv;
		case 2:
			return cpv.getCpvEng();
		}
		return null;
	}

}