package gui.empedit;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import gui.LabelsLocale;
import model.DepartmentModel;
import model.EmployeeModel;
import model.InstituteModel;
import model.RoleModel;
import model.SubdepartmentModel;

public class CreateEmployeeDialog extends JDialog implements EditEmployeeDialogListener{

	private JButton okButton;
	private JButton cancelButton;
	private CreateEmployeeDialogListener empListener;
	private EmployeePanel empPanel;

	public CreateEmployeeDialog(JFrame parent) {
		super(parent, LabelsLocale.getProperty("createEmployeeDialogSuper"), false);
		setSize(600, 370);
		setResizable(false);
		setLocationRelativeTo(parent);
		
		empPanel = new EmployeePanel();
		empPanel.setEmployeeDialogListener(this);

		okButton = new JButton(LabelsLocale.getProperty("createEmployeeDialogOkBtn"));
		cancelButton = new JButton(LabelsLocale.getProperty("cancelBtn"));

		layoutControls();

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EmployeeModel empModel = empPanel.createEmpFromFields();
				if(empModel != null){

					EmployeeEvent ev = new EmployeeEvent(this, empModel);

					if(empListener != null){
						empListener.createPersonEventOccured(ev);
					}
					setVisible(false);
				}
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				empPanel.clearDialog();
				setVisible(false);
			}
		});
	}
	
	@Override
	public void instSelelectionEventOccured(long instId) {
		if(empListener != null){
			empListener.instSelelectionEventOccured(instId);
		}
	}

	@Override
	public void depSelelectionEventOccured(long depId) {
		if(empListener != null){
			empListener.depSelelectionEventOccured(depId);
		}
	}

	@Override
	public void editPersonEventOccured(EmployeeEvent ev) {
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

	public void setEmployeeDialogListener(CreateEmployeeDialogListener empListener){
		this.empListener = empListener;

	}

	private void layoutControls() {
		JPanel buttonsPanel = new JPanel();

		buttonsPanel.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5));

		Dimension btnSize = okButton.getPreferredSize();
		cancelButton.setPreferredSize(btnSize);

		buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonsPanel.add(okButton);
		buttonsPanel.add(cancelButton);

		setLayout(new BorderLayout());
		add(empPanel, BorderLayout.CENTER);
		add(buttonsPanel, BorderLayout.SOUTH);
	}

}
