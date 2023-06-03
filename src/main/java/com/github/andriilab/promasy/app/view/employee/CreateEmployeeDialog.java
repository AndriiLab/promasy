package com.github.andriilab.promasy.app.view.employee;

import com.github.andriilab.promasy.data.commands.CreateCommand;
import com.github.andriilab.promasy.data.authorization.LoginData;
import com.github.andriilab.promasy.data.authorization.PasswordUtils;
import com.github.andriilab.promasy.data.commands.ICommand;
import com.github.andriilab.promasy.data.commands.UpdateCommand;
import com.github.andriilab.promasy.data.repositories.ServerRepository;
import com.github.andriilab.promasy.domain.EmptyModel;
import com.github.andriilab.promasy.domain.organization.entities.Department;
import com.github.andriilab.promasy.domain.organization.entities.Employee;
import com.github.andriilab.promasy.domain.organization.entities.Institute;
import com.github.andriilab.promasy.domain.organization.entities.Subdepartment;
import com.github.andriilab.promasy.domain.organization.enums.Role;
import com.github.andriilab.promasy.app.view.MainFrame;
import com.github.andriilab.promasy.app.commons.Icons;
import com.github.andriilab.promasy.app.commons.Labels;
import com.github.andriilab.promasy.app.components.EntityComboBox;
import com.github.andriilab.promasy.app.components.PJComboBox;
import com.github.andriilab.promasy.app.components.panes.ErrorOptionPane;
import org.jdesktop.swingx.prompt.PromptSupport;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.List;

public class CreateEmployeeDialog extends JDialog {

    private final JButton okButton;
    private final JButton cancelButton;
    private final JButton addOrganizationButton;
    private final JFrame parent;
    private CreateEmployeeDialogListener listener;
    private CreateEmployeeFromLoginListener loginListener;
    private final JTextField nameField;
    private final JTextField middleNameField;
    private final JTextField lastNameField;
    private final JTextField emailField;
    private final JTextField phoneMainField;
    private final JTextField phoneReserveField;
    private final EntityComboBox<Institute> instituteBox;
    private final EntityComboBox<Department> departmentBox;
    private final EntityComboBox<Subdepartment> subdepartmentBox;
    private final PJComboBox<Role> roleBox;
    private final JTextField loginField;
    private final JPasswordField passwordField;
    private final JPasswordField repeatPasswordField;
    private Employee currentEmployeeModel;

    public CreateEmployeeDialog(MainFrame parent) {
        super(parent, Labels.getProperty("createNewUser"), true);
        this.parent = parent;
        setSize(780, 320);
        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        Dimension comboBoxDim = new Dimension(400, 25);

        listener = new EmptyCreateEmployeeDialogListener();
        loginListener = new EmptyCreateEmployeeFromLoginListener();
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
        roleBox = new PJComboBox<>(Role.values());
        roleBox.setEditable(false);
        roleBox.setPreferredSize(comboBoxDim);

        //Set up institute combo box and edit buttons
        DefaultComboBoxModel<Institute> instModel = new DefaultComboBoxModel<>();
        instituteBox = new EntityComboBox<>(instModel);
        instituteBox.addItem(EmptyModel.INSTITUTE);
        instituteBox.setEditable(false);
        instituteBox.setPreferredSize(comboBoxDim);

        //Set up department combo box and edit buttons
        DefaultComboBoxModel<Department> depModel = new DefaultComboBoxModel<>();
        departmentBox = new EntityComboBox<>(depModel);
        departmentBox.addItem(EmptyModel.DEPARTMENT);
        departmentBox.setEditable(false);
        departmentBox.setEnabled(false);
        departmentBox.setPreferredSize(comboBoxDim);

        //Set up SubDepartment combo box and edit buttons
        DefaultComboBoxModel<Subdepartment> subdepModel = new DefaultComboBoxModel<>();
        subdepartmentBox = new EntityComboBox<>(subdepModel);
        subdepartmentBox.addItem(EmptyModel.SUBDEPARTMENT);
        subdepartmentBox.setEditable(false);
        subdepartmentBox.setEnabled(false);
        subdepartmentBox.setPreferredSize(comboBoxDim);

        okButton = new JButton(Labels.getProperty("createUser"));
        cancelButton = new JButton(Labels.getProperty("cancel"));
        addOrganizationButton = new JButton(Icons.CREATE);
        addOrganizationButton.setToolTipText(Labels.getProperty("addInstitute"));

        layoutControls();

        instituteBox.addActionListener(e -> {
            Institute selectedItem = (Institute) instituteBox.getSelectedItem();
            if (selectedItem != null) {
                if (!selectedItem.equals(EmptyModel.INSTITUTE)) {
                    this.setDepData(selectedItem.getDepartments());
                    departmentBox.setEnabled(true);
                    subdepartmentBox.setEnabled(false);
                } else {
                    departmentBox.setEnabled(false);
                    subdepartmentBox.setEnabled(false);
                }
            }
        });
        departmentBox.addActionListener(e -> {
            Department selectedItem = (Department) departmentBox.getSelectedItem();
            if (selectedItem != null) {
                if (!selectedItem.equals(EmptyModel.DEPARTMENT)) {
                    this.setSubdepData(selectedItem.getSubdepartments());
                    subdepartmentBox.setEnabled(true);
                } else {
                    subdepartmentBox.setEnabled(false);
                }
            }
        });

        addOrganizationButton.addActionListener(e -> parent.setEditOrgDialogVisible());
        okButton.addActionListener(e -> {
            if (isValidFields()) {
                ICommand<Employee> command = currentEmployeeModel.getModelId() == 0 ? new CreateCommand<>(currentEmployeeModel) : new UpdateCommand<>(currentEmployeeModel);
                listener.persistModelEventOccurred(command);
                clearDialog();
                loginListener.newUserCreatedEvent();
            }
        });

        cancelButton.addActionListener(e -> {
            clearDialog();
            loginListener.cancelEvent();
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                clearDialog();
                loginListener.cancelEvent();
            }
        });
    }

    private void clearDialog() {
        setVisible(false);
        currentEmployeeModel = EmptyModel.EMPLOYEE;
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
            ErrorOptionPane.emptyField(parent, Labels.getProperty("lastName"));
            lastNameField.requestFocusInWindow();
            return false;
        }
        String firstName = nameField.getText();
        if (firstName.length() < 2) {
            ErrorOptionPane.emptyField(parent, Labels.getProperty("firstName"));
            nameField.requestFocusInWindow();
            return false;
        }
        String middleName = middleNameField.getText();
        if (middleName.length() < 2) {
            ErrorOptionPane.emptyField(parent, Labels.getProperty("middleName"));
            middleNameField.requestFocusInWindow();
            return false;
        }
        //TODO email validator
        String email = emailField.getText();
        if (email.length() < 2) {
            ErrorOptionPane.emptyField(parent, Labels.getProperty("email"));
            emailField.requestFocusInWindow();
            return false;
        }
        String phoneMain = phoneMainField.getText();
        if (phoneMain.length() < 2) {
            ErrorOptionPane.emptyField(parent, Labels.getProperty("phoneMain"));
            phoneMainField.requestFocusInWindow();
            return false;
        }
        String phoneReserve = phoneReserveField.getText();
        String login = loginField.getText();
        if (login.length() == 0) {
            loginField.setDisabledTextColor(Color.RED);
            ErrorOptionPane.emptyField(parent, Labels.getProperty("userName"));
            phoneReserveField.requestFocusInWindow();
            return false;
        }
        Institute instituteModel = (Institute) instituteBox.getSelectedItem();
        if (instituteModel.equals(EmptyModel.INSTITUTE)) {
            ErrorOptionPane.emptyField(parent, Labels.getProperty("institute"));
            instituteBox.requestFocusInWindow();
            return false;
        }
        Department departmentModel = (Department) departmentBox.getSelectedItem();
        if (departmentModel.equals(EmptyModel.DEPARTMENT)) {
            ErrorOptionPane.emptyField(parent, Labels.getProperty("department"));
            departmentBox.requestFocusInWindow();
            return false;
        }
        departmentModel.setInstitute(instituteModel);
        Subdepartment subdepartmentModel = (Subdepartment) subdepartmentBox.getSelectedItem();
        if (subdepartmentModel.equals(EmptyModel.SUBDEPARTMENT)) {
            ErrorOptionPane.emptyField(parent, Labels.getProperty("subdepartment"));
            subdepartmentBox.requestFocusInWindow();
            return false;
        }
        subdepartmentModel.setDepartment(departmentModel);
        Role roleModel = (Role) roleBox.getSelectedItem();
        char[] password = passwordField.getPassword();
        char[] repeatPassword = repeatPasswordField.getPassword();
        // Generating new salt in case of creation of new user or password change for old user
        if (currentEmployeeModel.equals(EmptyModel.EMPLOYEE) || password.length > 0 || repeatPassword.length > 0) {
            if (!Arrays.equals(password, repeatPassword)) {
                JOptionPane.showMessageDialog(parent, Labels.getProperty("passwordsDoNotMatch"), Labels.getProperty("error"), JOptionPane.ERROR_MESSAGE, Icons.ERROR);
                repeatPasswordField.requestFocusInWindow();
                return false;
            }
            boolean isUniqueUser = listener.checkUniqueLogin(login);
            long salt = PasswordUtils.makeSalt();
            String pass = PasswordUtils.makePass(password, salt);
            if (pass == null) {
                ErrorOptionPane.criticalError(parent);
                loginListener.cancelEvent();
                return false;
            }
            // if com.github.andriilab.promasy.domain.model empty createOrUpdate new user
            if (currentEmployeeModel.equals(EmptyModel.EMPLOYEE) && isUniqueUser) {
                currentEmployeeModel = new Employee(firstName, middleName, lastName, email, phoneMain, phoneReserve, subdepartmentModel, roleModel, login, pass, salt);
                return true;
            } else if (currentEmployeeModel.equals(EmptyModel.EMPLOYEE) && !isUniqueUser) {
                JOptionPane.showMessageDialog(parent, Labels.getProperty("nonUniqueUser"), Labels.getProperty("error"), JOptionPane.ERROR_MESSAGE, Icons.ERROR);
                loginField.requestFocusInWindow();
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
        currentEmployeeModel.setSubdepartment(subdepartmentModel);
        currentEmployeeModel.setRole(roleModel);
        currentEmployeeModel.setLogin(login);
        currentEmployeeModel.setActive(true, LoginData.getInstance(), ServerRepository.getServerTimestamp());
        return true;
    }

    public void setEmployeeModel(Employee model) {
        this.currentEmployeeModel = model;
        nameField.setText(currentEmployeeModel.getEmpFName());
        middleNameField.setText(currentEmployeeModel.getEmpMName());
        lastNameField.setText(currentEmployeeModel.getEmpLName());
        emailField.setText(currentEmployeeModel.getEmail());
        phoneMainField.setText(currentEmployeeModel.getPhoneMain());
        phoneReserveField.setText(currentEmployeeModel.getPhoneReserve());
        loginField.setText(currentEmployeeModel.getLogin());
        setTitle(Labels.getProperty("editEmployee"));
        okButton.setText(Labels.getProperty("edit"));
        listener.loadInstitutes();
        roleBox.setSelectedItem(currentEmployeeModel.getRole());
        instituteBox.setSelectedModel(currentEmployeeModel.getSubdepartment().getDepartment().getInstitute());
        departmentBox.setSelectedModel(currentEmployeeModel.getSubdepartment().getDepartment());
        subdepartmentBox.setSelectedModel(currentEmployeeModel.getSubdepartment());

        if (LoginData.getInstance().getRole() != Role.ADMIN) {
            roleBox.setEnabled(false);
            addOrganizationButton.setEnabled(false);
            if (!LoginData.getInstance().getSubdepartment().equals(EmptyModel.SUBDEPARTMENT)) {
                instituteBox.setEnabled(false);
                departmentBox.setEnabled(false);
                subdepartmentBox.setEnabled(false);
            }
        }
        super.setVisible(true);
    }

    public void setRoleBox(Role role) {
        roleBox.setSelectedItem(role);
        roleBox.setEnabled(false);
        addOrganizationButton.setEnabled(role == Role.ADMIN);
    }

    public void setInstData(List<Institute> instDb) {
        instituteBox.setBoxData(instDb, EmptyModel.INSTITUTE, true);
    }

    public void setDepData(List<Department> depDb) {
        departmentBox.setBoxData(depDb, EmptyModel.DEPARTMENT, true);
    }

    public void setSubdepData(List<Subdepartment> subdepDb) {
        subdepartmentBox.setBoxData(subdepDb, EmptyModel.SUBDEPARTMENT, true);
    }

    public void setListener(CreateEmployeeDialogListener listener) {
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
        Border employeeBorder = BorderFactory.createTitledBorder(Labels.getProperty("role.user"));
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

    public void createCustomUser(Employee model) {
        currentEmployeeModel = model;
        setRoleBox(model.getRole());
        listener.loadInstitutes();
        super.setVisible(true);
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            listener.loadInstitutes();
        }
        currentEmployeeModel = new Employee();
        super.setVisible(visible);
    }
}
