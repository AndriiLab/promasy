package main.java.gui.empedit;

import main.java.gui.Icons;
import main.java.gui.Labels;
import main.java.gui.MainFrame;
import main.java.gui.Utils;
import main.java.model.*;
import main.java.model.enums.Role;
import org.jdesktop.swingx.prompt.PromptSupport;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.List;

public class CreateEmployeeDialog extends JDialog {

    private final RoleModel emptyRoleModel = new RoleModel();
    private final InstituteModel emptyInstituteModel = new InstituteModel();
    private final DepartmentModel emptyDepartmentModel = new DepartmentModel();
    private final SubdepartmentModel emptySubdepartmentModel = new SubdepartmentModel();
    private final EmployeeModel emptyEmployeeModel = new EmployeeModel();
    private JButton okButton;
    private JButton cancelButton;
    private JButton addOrganizationButton;
    private JFrame parent;
    private CreateEmployeeDialogListener listener;
    private CreateEmployeeFromLoginListener loginListener;
    private JTextField nameField;
    private JTextField middleNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField phoneMainField;
    private JTextField phoneReserveField;
    private JComboBox<InstituteModel> instituteBox;
    private JComboBox<DepartmentModel> departmentBox;
    private JComboBox<SubdepartmentModel> subdepartmentBox;
    private JComboBox<RoleModel> roleBox;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JPasswordField repeatPasswordField;
    private EmployeeModel currentEmployeeModel;

    public CreateEmployeeDialog(MainFrame parent) {
        super(parent, Labels.getProperty("createNewUser"), true);
        this.parent = parent;
        setSize(780, 320);
        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        currentEmployeeModel = emptyEmployeeModel;

        Dimension comboBoxDim = new Dimension(400, 25);

        nameField = new JTextField(10);
        middleNameField = new JTextField(10);
        lastNameField = new JTextField(10);

        emailField = new JTextField(10);
        phoneMainField = new JTextField(10);
        phoneReserveField = new JTextField(10);

        loginField = new JTextField(10);
        passwordField = new JPasswordField(10);
        repeatPasswordField = new JPasswordField(10);
        PromptSupport.setPrompt(Labels.getProperty("repeatPassword"), repeatPasswordField);
        PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.HIGHLIGHT_PROMPT, repeatPasswordField);

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
        departmentBox.setEnabled(false);
        departmentBox.setPreferredSize(comboBoxDim);

        //Set up SubDepartment combo box and edit buttons
        DefaultComboBoxModel<SubdepartmentModel> subdepModel = new DefaultComboBoxModel<>();
        subdepartmentBox = new JComboBox<>(subdepModel);
        subdepartmentBox.addItem(emptySubdepartmentModel);
        subdepartmentBox.setEditable(false);
        subdepartmentBox.setEnabled(false);
        subdepartmentBox.setPreferredSize(comboBoxDim);

        okButton = new JButton(Labels.getProperty("createUser"));
        cancelButton = new JButton(Labels.getProperty("cancel"));
        addOrganizationButton = new JButton(Icons.CREATE);
        addOrganizationButton.setToolTipText(Labels.getProperty("addInstitute"));

        layoutControls();

        instituteBox.addActionListener(e -> {
            departmentBox.removeAllItems();
            InstituteModel selectedItem = (InstituteModel) instituteBox.getSelectedItem();
            if (!selectedItem.equals(emptyInstituteModel) && listener != null) {
                listener.instSelectionEventOccurred(selectedItem.getModelId());
                departmentBox.setEnabled(true);
                subdepartmentBox.setEnabled(false);
            } else {
                departmentBox.setEnabled(false);
                subdepartmentBox.setEnabled(false);
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
                subdepartmentBox.setEnabled(true);
            } else {
                subdepartmentBox.setEnabled(false);
            }
        });

        addOrganizationButton.addActionListener(e -> parent.getEditOrgDialog().setVisible(true));
        okButton.addActionListener(e -> {
            if (isValidFields() && listener != null) {
                if (currentEmployeeModel.getModelId() == 0) {
                    listener.createEmployeeEventOccurred(currentEmployeeModel);
                } else {
                    listener.editEmployeeEventOccurred(currentEmployeeModel);
                }
                clearDialog();
                if (loginListener != null) {
                    loginListener.newUserCreatedEvent();
                }
            }
        });
        cancelButton.addActionListener(e -> {
            clearDialog();
            if (loginListener != null) {
                loginListener.cancelEvent();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                clearDialog();
                if (loginListener != null) {
                    loginListener.cancelEvent();
                }
            }
        });
    }

    private void clearDialog() {
        setVisible(false);
        currentEmployeeModel = emptyEmployeeModel;
        setTitle(Labels.getProperty("createNewUser"));
        okButton.setText(Labels.getProperty("createUser"));
        nameField.setText("");
        middleNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
        phoneMainField.setText("");
        phoneReserveField.setText("");
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

        String email = emailField.getText();
        if (email.length() < 2) {
            Utils.emptyFieldError(parent, Labels.getProperty("email"));
            return false;
        }
        //TODO add email validator
        String phoneMain = phoneMainField.getText();
        if (phoneMain.length() < 2) {
            Utils.emptyFieldError(parent, Labels.getProperty("phoneMain"));
            return false;
        }
        String phoneReserve = phoneReserveField.getText();
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
        if (currentEmployeeModel.equals(emptyEmployeeModel) || password.length > 0 || repeatPassword.length > 0) {
            if (!Arrays.equals(password, repeatPassword)) {
                JOptionPane.showMessageDialog(parent, Labels.getProperty("passwordsDoNotMatch"), Labels.getProperty("error"), JOptionPane.ERROR_MESSAGE);
                return false;
            }
            boolean isUniqueUser = listener.checkUniqueLogin(login);
            long salt = Utils.makeSalt();
            String pass = Utils.makePass(password, salt);
            // if model empty create new user
            if (currentEmployeeModel.equals(emptyEmployeeModel) && isUniqueUser) {
                currentEmployeeModel = new EmployeeModel(firstName, middleName, email, phoneMain, phoneReserve, lastName, departmentModel.getModelId(), subdepartmentModel.getModelId(), roleModel.getRoleId(), login, pass, salt);
                return true;
            } else if (currentEmployeeModel.equals(emptyEmployeeModel) && !isUniqueUser) {
                JOptionPane.showMessageDialog(parent, Labels.getProperty("nonUniqueUser"), Labels.getProperty("error"), JOptionPane.ERROR_MESSAGE);
                return false;
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
        currentEmployeeModel.setEmail(email);
        currentEmployeeModel.setPhoneMain(phoneMain);
        currentEmployeeModel.setPhoneReserve(phoneReserve);
        currentEmployeeModel.setDepId(departmentModel.getModelId());
        currentEmployeeModel.setSubdepId(subdepartmentModel.getModelId());
        currentEmployeeModel.setRoleId(roleModel.getRoleId());
        currentEmployeeModel.setLogin(login);
        return true;
    }

    public void setEmployeeModel(EmployeeModel model) {
        if (LoginData.getInstance().getRoleId() != Role.ADMIN.getRoleId()) {
            roleBox.setEnabled(false);
        }
        this.currentEmployeeModel = model;
        nameField.setText(currentEmployeeModel.getEmpFName());
        middleNameField.setText(currentEmployeeModel.getEmpMName());
        lastNameField.setText(currentEmployeeModel.getEmpLName());
        emailField.setText(currentEmployeeModel.getEmail());
        phoneMainField.setText(currentEmployeeModel.getPhoneMain());
        phoneReserveField.setText(currentEmployeeModel.getPhoneReserve());
        loginField.setText(currentEmployeeModel.getLogin());
        Utils.setBoxFromID(roleBox, currentEmployeeModel.getRoleId());
        Utils.setBoxFromID(instituteBox, currentEmployeeModel.getInstId());
        Utils.setBoxFromID(departmentBox, currentEmployeeModel.getDepId());
        Utils.setBoxFromID(subdepartmentBox, currentEmployeeModel.getSubdepId());
        setTitle(Labels.getProperty("editEmployee"));
        okButton.setText(Labels.getProperty("edit"));
        setVisible(true);
    }

    public void setRoleBox(boolean state, int roleId) {
        Utils.setBoxFromID(roleBox, roleId);
        roleBox.setEnabled(state);
        if (roleId == Role.ADMIN.getRoleId()) {
            addOrganizationButton.setEnabled(true);
        } else {
            addOrganizationButton.setEnabled(false);
        }
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

    public void setLoginListener(CreateEmployeeFromLoginListener loginListener) {
        this.loginListener = loginListener;
    }

    private void layoutControls() {
        JPanel employeePanel = new JPanel();
        JPanel loginAndAffiliationsPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();

        JPanel institutePanel = new JPanel();

        int space = 5;
        Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
        Border employeeBorder = BorderFactory.createTitledBorder(Labels.getProperty("user"));
        Border instituteBorder = BorderFactory.createTitledBorder(Labels.getProperty("associatedOrganization"));

        employeePanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, employeeBorder));
        institutePanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, instituteBorder));

        Insets smallPadding = new Insets(1, 0, 1, 5);
        Insets largePadding = new Insets(1, 0, 1, 15);
        Insets noPadding = new Insets(1, 0, 1, 0);

        employeePanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        ////// First row//////
        gc.gridy = 0;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        employeePanel.add(new JLabel(Labels.withColon("lastName")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        employeePanel.add(lastNameField, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        employeePanel.add(new JLabel(Labels.withColon("firstName")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        employeePanel.add(nameField, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        employeePanel.add(new JLabel(Labels.withColon("middleName")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        employeePanel.add(middleNameField, gc);

        ////// Next row//////
        gc.gridy++;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        employeePanel.add(new JLabel(Labels.withColon("email")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        employeePanel.add(emailField, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        employeePanel.add(new JLabel(Labels.withColon("phoneMain")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        employeePanel.add(phoneMainField, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        employeePanel.add(new JLabel(Labels.withColon("phoneReserve")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        employeePanel.add(phoneReserveField, gc);

        ////// Next row//////
        gc.gridy++;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        employeePanel.add(new JLabel(Labels.withColon("userName")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        employeePanel.add(loginField, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        employeePanel.add(new JLabel(Labels.withColon("password")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        employeePanel.add(passwordField, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        employeePanel.add(repeatPasswordField, gc);


        institutePanel.setLayout(new GridBagLayout());
        gc = new GridBagConstraints();

        ////// Next row//////
        gc.gridy = 0;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        institutePanel.add(new JLabel(Labels.withColon("institute")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        institutePanel.add(instituteBox, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        institutePanel.add(addOrganizationButton, gc);

        ////// Next row//////
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        institutePanel.add(new JLabel(Labels.withColon("department")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.NORTHWEST;
        gc.insets = smallPadding;
        institutePanel.add(departmentBox, gc);

        ////// Next row//////
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        institutePanel.add(new JLabel(Labels.withColon("subdepartment")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.NORTHWEST;
        gc.insets = smallPadding;
        institutePanel.add(subdepartmentBox, gc);

        ////// Next row//////
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        institutePanel.add(new JLabel(Labels.withColon("role")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.NORTHWEST;
        gc.insets = smallPadding;
        institutePanel.add(roleBox, gc);

        loginAndAffiliationsPanel.setLayout(new BorderLayout());
        loginAndAffiliationsPanel.add(employeePanel, BorderLayout.CENTER);
        loginAndAffiliationsPanel.add(institutePanel, BorderLayout.SOUTH);

        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5));

        Dimension btnSize = okButton.getPreferredSize();
        cancelButton.setPreferredSize(btnSize);

        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        setLayout(new BorderLayout());
        add(loginAndAffiliationsPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }
}
