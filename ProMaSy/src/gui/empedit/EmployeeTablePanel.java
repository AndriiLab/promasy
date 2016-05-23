package gui.empedit;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.EmployeeModel;

class EmployeeTablePanel extends JPanel {
	
	private JTable table;
	private EmployeeTableModel tableModel;
	private EmployeeTableListener listener;

	public EmployeeTablePanel() {
		tableModel = new EmployeeTableModel();
		table = new JTable(tableModel);
		
		setLayout(new BorderLayout());
		add(new JScrollPane(table), BorderLayout.CENTER);
		
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent ev) {
				int row = table.rowAtPoint(ev.getPoint());
				table.getSelectionModel().setSelectionInterval(row, row);
				
				if (ev.getButton() == MouseEvent.BUTTON1) {
					EmployeeModel selectedModel = (EmployeeModel) table.getValueAt(row, 0);
					if(listener !=null){
						listener.employeeSelectionOccured(selectedModel);
					}
				}
			}
		});
	}
	
	public void setData(List<EmployeeModel> db){
		tableModel.setData(db);
	}
	
	public void setEmployeeTableListener(EmployeeTableListener lisntener){
		this.listener = lisntener;
	}
	public EmployeeTableModel getTableModel() {
		return tableModel;
	}
}
