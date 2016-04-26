package gui.empedit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

import gui.Labels;
import model.DepartmentModel;
import model.EmployeeModel;
import model.InstituteModel;
import model.RoleModel;
import model.SubdepartmentModel;

class EmployeePanel extends JPanel implements ActionListener{

	private JTextField nameField;
	private JTextField middleNameField;
	private JTextField lastNameField;
	private JComboBox<InstituteModel> instituteBox;
	private JComboBox<DepartmentModel> departmentBox;
	private JComboBox<SubdepartmentModel> subdepartmentBox;
	private JComboBox<RoleModel> roleBox;
	private EditEmployeeDialogListener empListener;
	private JTextField loginField;
	private JPasswordField passwordField;
	private JPasswordField repeatPasswordField;
	private EmployeeModel empModel;
	private JLabel hint;
	private final InstituteModel emptyInstituteModel = new InstituteModel();
    private final DepartmentModel emptyDepartmentModel = new DepartmentModel();
    private final SubdepartmentModel emptySubdepartmentModel = new SubdepartmentModel();

	public EmployeePanel() {
		
		Dimension comboBoxDim = new Dimension(400, 25);

		nameField = new JTextField(10);
		middleNameField = new JTextField(10);
		lastNameField = new JTextField(10);

		loginField = new JTextField(10);
		passwordField = new JPasswordField(12);
		repeatPasswordField = new JPasswordField(12);
		repeatPasswordField.setEditable(false);

		//Set up roles combo box
		DefaultComboBoxModel<RoleModel> roleModel = new DefaultComboBoxModel<>();
		roleBox = new JComboBox<>(roleModel);
		roleBox.addItem(new RoleModel());
		roleBox.setEditable(false);
		roleBox.setPreferredSize(comboBoxDim);

		//Set up institute combo box and edit buttons
		DefaultComboBoxModel<InstituteModel> instModel = new DefaultComboBoxModel<>();
		instituteBox = new JComboBox<>(instModel);
		instituteBox.addItem(emptyInstituteModel);
		instituteBox.setEditable(false);
		instituteBox.addActionListener(this);
		instituteBox.setPreferredSize(comboBoxDim);

		//Set up department combo box and edit buttons
		DefaultComboBoxModel<DepartmentModel> depModel = new DefaultComboBoxModel<>();
		departmentBox = new JComboBox<>(depModel);
		departmentBox.addItem(emptyDepartmentModel);
		departmentBox.setEditable(false);
		departmentBox.addActionListener(this);
		departmentBox.setPreferredSize(comboBoxDim);

		//Set up SubDepartment combo box and edit buttons
		DefaultComboBoxModel<SubdepartmentModel> subdepModel = new DefaultComboBoxModel<>();
		subdepartmentBox = new JComboBox<>(subdepModel);
		subdepartmentBox.addItem(emptySubdepartmentModel);
		subdepartmentBox.setEditable(false);
		subdepartmentBox.setPreferredSize(comboBoxDim);

		hint = new JLabel();
		hint.setPreferredSize(new Dimension(200, 15));
		hint.setHorizontalTextPosition(JLabel.LEFT);
		hint.setForeground(Color.RED);

		layoutControls();
		
		passwordField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				repeatPasswordField.setEditable(passwordField.getPassword().length != 0);
				if(repeatPasswordField.isEditable()){
					hint.setText(Labels.getProperty("repeatPassword"));
				} else if (repeatPasswordField.getPassword().length == 0){
					hint.setText(Labels.getProperty("repeatPassword"));
				}
			}
		});

		repeatPasswordField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if(repeatPasswordField.getPassword().length > 0){
					hint.setText(null);
				} else if (repeatPasswordField.getPassword().length == 0){
					hint.setText(Labels.getProperty("repeatPassword"));
				}
			}
		});
	}
	
	public EmployeeModel createEmpFromFields(){
		empModel = new EmployeeModel();
		if(getEmpFromFields() != null){
			return empModel;
		} else return null;
	}
	
	public EmployeeModel getEmpFromFields() {
		if (loginField.getText().length() == 0){
			loginField.setDisabledTextColor(Color.RED);
			hint.setText(Labels.getProperty("enterLogin"));
			return null;
		}
		else if(passwordField.getPassword().length == 0){
			passwordField.setDisabledTextColor(Color.RED);
			hint.setText(Labels.getProperty("enterPassword"));
			return null;
		} 
		else if (!Arrays.equals(passwordField.getPassword(), repeatPasswordField.getPassword())){
			hint.setText(Labels.getProperty("passwordsMismatch"));
			return null;
		}
		else if (lastNameField.getText().length() < 2){
			hint.setText(Labels.getProperty("enterLName"));
			return null;
		}
		else if (nameField.getText().length() < 2){
			hint.setText(Labels.getProperty("enterFName"));
			return null;
		}
		else if (instituteBox.getSelectedItem().equals(instituteBox.getItemAt(0))){
			hint.setText(Labels.getProperty("enterInstitute"));
			return null;
		}
		else if (departmentBox.getSelectedItem().equals(departmentBox.getItemAt(0))){
			hint.setText(Labels.getProperty("enterDepartment"));
			return null;
		}
		else if (roleBox.getSelectedItem().equals(roleBox.getItemAt(0))){
			hint.setText(Labels.getProperty("enterRole"));
			return null;
		}
		else {
			hint.setText(null);
			
			empModel.setEmpFName(nameField.getText());
			empModel.setEmpMName(middleNameField.getText());
			empModel.setEmpLName(lastNameField.getText());
			empModel.setDepId(((DepartmentModel) departmentBox.getSelectedItem()).getDepId());
			empModel.setSubdepId(((SubdepartmentModel) subdepartmentBox.getSelectedItem()).getSubdepId());
			empModel.setRoleId(((RoleModel) roleBox.getSelectedItem()).getRolesId());
			empModel.setLogin(loginField.getText());
			empModel.setPassword(new String(passwordField.getPassword()));
			clearDialog();
			return empModel;
		}
	}

	public void clearDialog(){
		nameField.setText(null);
		middleNameField.setText(null);
		lastNameField.setText(null);
		instituteBox.setSelectedIndex(0);
		roleBox.setSelectedItem(null);
		loginField.setText(null);
		passwordField.setText(null);
		repeatPasswordField.setText(null);
	}

	public void setRolesData(List<RoleModel> rolesDb) {
        for (RoleModel aRolesDb : rolesDb) {
            roleBox.addItem(aRolesDb);
        }
	}

	public void setInstData(List<InstituteModel> instDb) {
        for (InstituteModel anInstDb : instDb) {
            instituteBox.addItem(anInstDb);
        }
	}

	public void setDepData(List<DepartmentModel> depDb) {
        for (DepartmentModel aDepDb : depDb) {
            departmentBox.addItem(aDepDb);
        }
	}

	public void setSubdepData(List<SubdepartmentModel> subdepDb) {
        for (SubdepartmentModel aSubdepDb : subdepDb) {
            subdepartmentBox.addItem(aSubdepDb);
        }
	}

	public void setEmployeeDialogListener(EditEmployeeDialogListener empListener){
		this.empListener = empListener;

	}

	public void actionPerformed(ActionEvent ev){
		JComboBox box  = (JComboBox) ev.getSource();

		if (box == instituteBox){
			departmentBox.removeAllItems();
			if (!instituteBox.getSelectedItem().equals(emptyInstituteModel)){
				InstituteModel model = (InstituteModel)instituteBox.getSelectedItem();
				long instId = model.getInstId();
				if(empListener != null){
					empListener.instSelectionEventOccurred(instId);
				}
			}
		} else if (box == departmentBox){
			if (box.getSelectedItem() == null){
				departmentBox.addItem(emptyDepartmentModel);
			}
			subdepartmentBox.removeAllItems();
			subdepartmentBox.addItem(emptySubdepartmentModel);
			if (!departmentBox.getSelectedItem().equals(emptyDepartmentModel)){
				DepartmentModel depModel = (DepartmentModel)departmentBox.getSelectedItem();
				long depId = depModel.getDepId();
				if(empListener != null){
					empListener.depSelectionEventOccurred(depId);
				}
			}
		}
	}

	public void setEmployee(EmployeeModel obj){
		this.empModel = obj;

		nameField.setText(empModel.getEmpFName());
		middleNameField.setText(empModel.getEmpMName());
		lastNameField.setText(empModel.getEmpLName());
		loginField.setText(empModel.getLogin());
		//TODO unsecure pass
		passwordField.setText(empModel.getPassword());
		repeatPasswordField.setText(empModel.getPassword());
		setBoxFromModel(roleBox, empModel.getRoleName());
		setBoxFromModel(instituteBox, empModel.getInstName());
		setBoxFromModel(departmentBox, empModel.getDepName());
		setBoxFromModel(subdepartmentBox, empModel.getSubdepName());

	}

	private static void setBoxFromModel(JComboBox box, String req){
		if(req != null){
			for(int i = 0; i<=box.getItemCount(); i++){
				if(box.getItemAt(i).toString().equals(req)){
					box.setSelectedIndex(i);
					break;
				}
			}
		}
	}

	private void layoutControls() {
		JPanel loginPanel = new JPanel();
		JPanel employeePanel = new JPanel();
		JPanel newEmployeePanel = new JPanel();
		JPanel hintPanel = new JPanel();

		JPanel institutePanel = new JPanel();

		int space = 5;
		Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
		Border employeeBorder = BorderFactory.createTitledBorder(Labels.getProperty("user"));
		Border loginBorder = BorderFactory.createTitledBorder(Labels.getProperty("loginParameters"));
		Border instituteBorder = BorderFactory.createTitledBorder(Labels.getProperty("AssociatedOrganization"));
		Border hintBorder = BorderFactory.createEtchedBorder();

		loginPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, loginBorder));
		employeePanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, employeeBorder));
		institutePanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, instituteBorder));
		hintPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, hintBorder));

		Insets smallPadding = new Insets(1, 0, 1, 5);
		Insets largePadding = new Insets(1, 0, 1, 15);
		Insets noPadding = new Insets(1, 0, 1, 0);

		loginPanel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		////// First row//////
		gc.gridy = 0;
		gc.fill = GridBagConstraints.NONE;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = smallPadding;
		loginPanel.add(new JLabel(Labels.getProperty("userName")), gc);

		gc.gridx++;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = largePadding;
		loginPanel.add(loginField, gc);

		gc.gridx++;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = smallPadding;
		loginPanel.add(new JLabel(Labels.getProperty("password")), gc);

		gc.gridx++;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = smallPadding;
		loginPanel.add(passwordField, gc);

		gc.gridx++;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = noPadding;
		loginPanel.add(repeatPasswordField, gc);

		employeePanel.setLayout(new GridBagLayout());
		gc = new GridBagConstraints();

		////// First row//////
		gc.gridy = 0;
		gc.fill = GridBagConstraints.NONE;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = smallPadding;
		employeePanel.add(new JLabel(Labels.getProperty("lastName")+":"), gc);

		gc.gridx++;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = largePadding;
		employeePanel.add(lastNameField, gc);

		gc.gridx++;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = smallPadding;
		employeePanel.add(new JLabel(Labels.getProperty("firstName")+":"), gc);

		gc.gridx++;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = largePadding;
		employeePanel.add(nameField, gc);

		gc.gridx++;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = smallPadding;
		employeePanel.add(new JLabel(Labels.getProperty("middleName")+":"), gc);

		gc.gridx++;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = noPadding;
		employeePanel.add(middleNameField, gc);

		institutePanel.setLayout(new GridBagLayout());
		gc = new GridBagConstraints();

		////// First row//////
		gc.gridy = 0;
		gc.fill = GridBagConstraints.NONE;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = smallPadding;
		institutePanel.add(new JLabel(Labels.getProperty("role")+":"), gc);

		gc.gridx++;
		gc.anchor = GridBagConstraints.NORTHWEST;
		gc.insets = smallPadding;
		institutePanel.add(roleBox, gc);

		////// Next row//////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = smallPadding;
		institutePanel.add(new JLabel(Labels.getProperty("institute")+":"), gc);

		gc.gridx++;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = smallPadding;
		institutePanel.add(instituteBox, gc);

		////// Next row//////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = smallPadding;
		institutePanel.add(new JLabel(Labels.getProperty("department")+":"), gc);

		gc.gridx++;
		gc.anchor = GridBagConstraints.NORTHWEST;
		gc.insets = smallPadding;
		institutePanel.add(departmentBox, gc);

		////// Next row//////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = smallPadding;
		institutePanel.add(new JLabel(Labels.getProperty("subdepartment")+":"), gc);

		gc.gridx++;
		gc.anchor = GridBagConstraints.NORTHWEST;
		gc.insets = smallPadding;
		institutePanel.add(subdepartmentBox, gc);
		
		hintPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		hintPanel.add(hint);

		newEmployeePanel.setLayout(new BorderLayout());
		newEmployeePanel.add(loginPanel, BorderLayout.NORTH);
		newEmployeePanel.add(institutePanel, BorderLayout.CENTER);
		newEmployeePanel.add(employeePanel, BorderLayout.SOUTH);

		setLayout(new BorderLayout());
		add(hintPanel, BorderLayout.NORTH);
		add(newEmployeePanel, BorderLayout.CENTER);
	}
}
