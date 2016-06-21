package gui.empedit;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;

import gui.Labels;
import gui.Utils;
import model.DepartmentModel;
import model.EmployeeModel;
import model.InstituteModel;
import model.RoleModel;
import model.SubdepartmentModel;

public class CreateEmployeeDialog extends JDialog {

    private JButton okButton;
    private JButton cancelButton;
    private JFrame parent;
    private CreateEmployeeDialogListener listener;
    private JTextField nameField;
    private JTextField middleNameField;
    private JTextField lastNameField;
    private JComboBox<InstituteModel> instituteBox;
    private JComboBox<DepartmentModel> departmentBox;
    private JComboBox<SubdepartmentModel> subdepartmentBox;
    private JComboBox<RoleModel> roleBox;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JPasswordField repeatPasswordField;
    private EmployeeModel currentEmployeeModel;
    private final RoleModel emptyRoleModel = new RoleModel();
    private final InstituteModel emptyInstituteModel = new InstituteModel();
    private final DepartmentModel emptyDepartmentModel = new DepartmentModel();
    private final SubdepartmentModel emptySubdepartmentModel = new SubdepartmentModel();
    private final EmployeeModel emptyEmployeeModel = new EmployeeModel();

    public CreateEmployeeDialog(JFrame parent) {
        super(parent, Labels.getProperty("createNewEmployee"), false);
        this.parent = parent;
        setSize(600, 330);
        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        currentEmployeeModel = emptyEmployeeModel;

        Dimension comboBoxDim = new Dimension(400, 25);

        nameField = new JTextField(10);
        middleNameField = new JTextField(10);
        lastNameField = new JTextField(10);

        loginField = new JTextField(10);
        passwordField = new JPasswordField(12);
        repeatPasswordField = new JPasswordField(12);

        //Set up roles combo box
        DefaultComboBoxModel<RoleModel> roleModel = new DefaultComboBoxModel<>();
        roleBox = new JComboBox<>(roleModel);
        roleBox.addItem(emptyRoleModel);
        roleBox.setEditable(false);
        roleBox.setPreferredSize(comboBoxDim);

        //Set up institute combo box and edit buttons
        DefaultComboBoxModel<InstituteModel> instModel = new DefaultComboBoxModel<>();
        instituteBox = new JComboBox<>(instModel);
        instituteBox.addItem(emptyInstituteModel);
        instituteBox.setEditable(false);
        instituteBox.setPreferredSize(comboBoxDim);

        //Set up department combo box and edit buttons
        DefaultComboBoxModel<DepartmentModel> depModel = new DefaultComboBoxModel<>();
        departmentBox = new JComboBox<>(depModel);
        departmentBox.addItem(emptyDepartmentModel);
        departmentBox.setEditable(false);
        departmentBox.setPreferredSize(comboBoxDim);

        //Set up SubDepartment combo box and edit buttons
        DefaultComboBoxModel<SubdepartmentModel> subdepModel = new DefaultComboBoxModel<>();
        subdepartmentBox = new JComboBox<>(subdepModel);
        subdepartmentBox.addItem(emptySubdepartmentModel);
        subdepartmentBox.setEditable(false);
        subdepartmentBox.setPreferredSize(comboBoxDim);

        okButton = new JButton(Labels.getProperty("createProfile"));
        cancelButton = new JButton(Labels.getProperty("cancel"));

        layoutControls();

        instituteBox.addActionListener(e -> {
            departmentBox.removeAllItems();
            InstituteModel selectedItem = (InstituteModel) instituteBox.getSelectedItem();
            if (!selectedItem.equals(emptyInstituteModel) && listener != null) {
                listener.instSelectionEventOccurred(selectedItem.getModelId());
            }
        });
        departmentBox.addActionListener(e -> {
            if (departmentBox.getSelectedItem() == null) {
                departmentBox.addItem(emptyDepartmentModel);
            }
            subdepartmentBox.removeAllItems();
            subdepartmentBox.addItem(emptySubdepartmentModel);
            DepartmentModel selectedItem = (DepartmentModel) departmentBox.getSelectedItem();
            if (!selectedItem.equals(emptyDepartmentModel) && listener != null) {
                listener.depSelectionEventOccurred(selectedItem.getModelId());
            }
        });
        okButton.addActionListener(e -> {
            if (isValidFields() && listener != null) {
                if (currentEmployeeModel.getModelId() == 0) {
                    listener.createEmployeeEventOccurred(currentEmployeeModel);
                } else {
                    listener.editEmployeeEventOccurred(currentEmployeeModel);
                }
                clearDialog();
            }
        });
        cancelButton.addActionListener(e -> clearDialog());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                clearDialog();
            }
        });
    }

    private void clearDialog() {
        setVisible(false);
        currentEmployeeModel = emptyEmployeeModel;
        setTitle(Labels.getProperty("createNewEmployee"));
        okButton.setText(Labels.getProperty("createProfile"));
        nameField.setText("");
        middleNameField.setText("");
        lastNameField.setText("");
        instituteBox.setSelectedIndex(0);
        roleBox.setSelectedItem("");
        loginField.setText("");
        passwordField.setText("");
        repeatPasswordField.setText("");
        roleBox.setSelectedIndex(0);
        instituteBox.setSelectedIndex(0);
    }

    private boolean isValidFields() {
        String lastName = lastNameField.getText();
        if (lastName.length() < 2) {
            Utils.emptyFieldError(parent, Labels.getProperty("lastName"));
            return false;
        }
        String firstName = nameField.getText();
        if (firstName.length() < 2) {
            Utils.emptyFieldError(parent, Labels.getProperty("firstName"));
            return false;
        }
        String middleName = middleNameField.getText();
        if (middleName.length() < 2) {
            Utils.emptyFieldError(parent, Labels.getProperty("middleName"));
            return false;
        }
        String login = loginField.getText();
        if (login.length() == 0) {
            loginField.setDisabledTextColor(Color.RED);
            Utils.emptyFieldError(parent, Labels.getProperty("userName"));
            return false;
        }
        InstituteModel instituteModel = (InstituteModel) instituteBox.getSelectedItem();
        if (instituteModel.equals(emptyInstituteModel)) {
            Utils.emptyFieldError(parent, Labels.getProperty("institute"));
            return false;
        }
        DepartmentModel departmentModel = (DepartmentModel) departmentBox.getSelectedItem();
        if (departmentModel.equals(emptyDepartmentModel)) {
            Utils.emptyFieldError(parent, Labels.getProperty("department"));
            return false;
        }
        SubdepartmentModel subdepartmentModel = (SubdepartmentModel) subdepartmentBox.getSelectedItem();
        RoleModel roleModel = (RoleModel) roleBox.getSelectedItem();
        if (roleModel.equals(emptyRoleModel)) {
            Utils.emptyFieldError(parent, Labels.getProperty("role"));
            return false;
        }
        char[] password = passwordField.getPassword();
        char[] repeatPassword = repeatPasswordField.getPassword();
        // Generating new salt in case of creation of new user or password change for old user
        if (currentEmployeeModel == emptyEmployeeModel || password.length > 0 || repeatPassword.length > 0) {
            long salt = Utils.makeSalt();
            if (password.length == 0) {
                passwordField.setDisabledTextColor(Color.RED);
                Utils.emptyFieldError(parent, Labels.getProperty("password"));
                return false;
            }
            if (!Arrays.equals(password, repeatPassword)) {
                Utils.emptyFieldError(parent, Labels.getProperty("password"));
                return false;
            }
            String pass = Utils.makePass(password, salt);
            // if model empty create new user
            if (currentEmployeeModel == emptyEmployeeModel) {
                currentEmployeeModel = new EmployeeModel(firstName, middleName, lastName, departmentModel.getModelId(), subdepartmentModel.getModelId(), roleModel.getModelId(), login, pass, salt);
                return true;
            } else {
                // else - update existing
                currentEmployeeModel.setSalt(salt);
                currentEmployeeModel.setPassword(pass);
            }
        }
        // executes when all fields ok, but we don't want to change the pass
        currentEmployeeModel.setEmpFName(firstName);
        currentEmployeeModel.setEmpMName(middleName);
        currentEmployeeModel.setEmpLName(lastName);
        currentEmployeeModel.setDepId(departmentModel.getModelId());
        currentEmployeeModel.setSubdepId(subdepartmentModel.getModelId());
        currentEmployeeModel.setRoleId(roleModel.getModelId());
        currentEmployeeModel.setLogin(login);
        return true;
    }


    void setEmployeeModel(EmployeeModel model) {
        this.currentEmployeeModel = model;
        nameField.setText(currentEmployeeModel.getEmpFName());
        middleNameField.setText(currentEmployeeModel.getEmpMName());
        lastNameField.setText(currentEmployeeModel.getEmpLName());
        loginField.setText(currentEmployeeModel.getLogin());
        Utils.setBoxFromModel(roleBox, currentEmployeeModel.getRoleId());
        Utils.setBoxFromModel(instituteBox, currentEmployeeModel.getInstId());
        Utils.setBoxFromModel(departmentBox, currentEmployeeModel.getDepId());
        Utils.setBoxFromModel(subdepartmentBox, currentEmployeeModel.getSubdepId());
        setTitle(Labels.getProperty("editEmployee"));
        okButton.setText(Labels.getProperty("editEmployee"));
        setVisible(true);
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

    public void setCreateEmployeeDialogListener(CreateEmployeeDialogListener listener) {
        this.listener = listener;

    }

    private void layoutControls() {
        JPanel buttonsPanel = new JPanel();

        JPanel loginPanel = new JPanel();
        JPanel employeePanel = new JPanel();
        JPanel newEmployeePanel = new JPanel();

        JPanel institutePanel = new JPanel();

        int space = 5;
        Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
        Border employeeBorder = BorderFactory.createTitledBorder(Labels.getProperty("user"));
        Border loginBorder = BorderFactory.createTitledBorder(Labels.getProperty("loginParameters"));
        Border instituteBorder = BorderFactory.createTitledBorder(Labels.getProperty("AssociatedOrganization"));

        loginPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, loginBorder));
        employeePanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, employeeBorder));
        institutePanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, instituteBorder));

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
        employeePanel.add(new JLabel(Labels.getProperty("lastName") + ":"), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        employeePanel.add(lastNameField, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        employeePanel.add(new JLabel(Labels.getProperty("firstName") + ":"), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        employeePanel.add(nameField, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        employeePanel.add(new JLabel(Labels.getProperty("middleName") + ":"), gc);

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
        institutePanel.add(new JLabel(Labels.getProperty("institute") + ":"), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        institutePanel.add(instituteBox, gc);

        ////// Next row//////
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        institutePanel.add(new JLabel(Labels.getProperty("department") + ":"), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.NORTHWEST;
        gc.insets = smallPadding;
        institutePanel.add(departmentBox, gc);

        ////// Next row//////
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        institutePanel.add(new JLabel(Labels.getProperty("subdepartment") + ":"), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.NORTHWEST;
        gc.insets = smallPadding;
        institutePanel.add(subdepartmentBox, gc);

        ////// Next row//////
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        institutePanel.add(new JLabel(Labels.getProperty("role") + ":"), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.NORTHWEST;
        gc.insets = smallPadding;
        institutePanel.add(roleBox, gc);

        newEmployeePanel.setLayout(new BorderLayout());
        newEmployeePanel.add(loginPanel, BorderLayout.NORTH);
        newEmployeePanel.add(institutePanel, BorderLayout.CENTER);

        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5));

        Dimension btnSize = okButton.getPreferredSize();
        cancelButton.setPreferredSize(btnSize);

        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        setLayout(new BorderLayout());
        add(employeePanel, BorderLayout.NORTH);
        add(newEmployeePanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }
}
