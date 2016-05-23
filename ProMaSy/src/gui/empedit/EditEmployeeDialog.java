package gui.empedit;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import gui.Labels;
import model.DepartmentModel;
import model.EmployeeModel;
import model.InstituteModel;
import model.RoleModel;
import model.SubdepartmentModel;

public class EditEmployeeDialog extends JDialog implements EditEmployeeDialogListener {

	private JButton okButton;
	private EditEmployeeDialogListener empListener;
	private EmployeeTablePanel empTablePanel;
	private EmployeePanel empPanel;

	public EditEmployeeDialog(JFrame parent) {
		super(parent, Labels.getProperty("addEmployee"), false);
		setSize(600, 600);
		setResizable(false);
		setLocationRelativeTo(parent);
		
		empPanel = new EmployeePanel();
		
		empTablePanel = new EmployeeTablePanel();
		empTablePanel.setEmployeeTableListener(this::setEmployee);

		okButton = new JButton(Labels.getProperty("changeProfile"));
		JButton cancelButton = new JButton(Labels.getProperty("cancelBtn"));
		
		empPanel.setEmployeeDialogListener(this);

		okButton.addActionListener(e -> {
			EmployeeModel empModel = empPanel.getEmpFromFields();
			if (empModel != null) {
				EmployeeEvent ev = new EmployeeEvent(this, empModel);
				if (empListener != null) {
					empListener.editPersonEventOccurred(ev);
				}
				empModel = null;
				setVisible(false);
			}
		});
		cancelButton.addActionListener(e -> {
            empPanel.clearDialog();
            setVisible(false);
        });
		
		JPanel buttonsPanel = new JPanel();

		buttonsPanel.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5));

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, empPanel, 
				new JScrollPane(empTablePanel));
		
		splitPane.setDividerLocation(300);
		splitPane.setEnabled(false);

		Dimension btnSize = okButton.getPreferredSize();
		cancelButton.setPreferredSize(btnSize);

		buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonsPanel.add(okButton);
		buttonsPanel.add(cancelButton);

		setLayout(new BorderLayout());
		add(splitPane, BorderLayout.CENTER);
		add(buttonsPanel, BorderLayout.SOUTH);
	}
	
	@Override
	public void instSelectionEventOccurred(long instId) {
		if(empListener != null){
			empListener.instSelectionEventOccurred(instId);
		}
	}

	@Override
	public void depSelectionEventOccurred(long depId) {
		if(empListener != null){
			empListener.depSelectionEventOccurred(depId);
		}
	}

	@Override
	public void editPersonEventOccurred(EmployeeEvent ev) {
		// TODO Auto-generated method stub
		
	}
	

	public void setRolesData(List<RoleModel> rolesDb) {
		empPanel.setRolesData(rolesDb);
	}

	public void setInstData(List<InstituteModel> instDb) {
		empPanel.setInstData(instDb);
	}

	public void setDepData(List<DepartmentModel> depDb) {
		empPanel.setDepData(depDb);
	}

	public void setSubdepData(List<SubdepartmentModel> subdepDb) {
		empPanel.setSubdepData(subdepDb);
	}
	
	public void setEmpTableData(List<EmployeeModel> db){
		empTablePanel.setData(db);
	}

	public void setEmployeeDialogListener(EditEmployeeDialogListener empListener){
		this.empListener = empListener;

	}

    public void refresh(){
        empTablePanel.getTableModel().fireTableDataChanged();
    }
	
	private void setEmployee(EmployeeModel obj){
		empPanel.setEmployee(obj);
		okButton.setEnabled(true);
	}

}
