package gui.bids.reports;

import gui.Labels;
import gui.MainFrame;
import gui.Utils;
import model.enums.Role;
import model.models.EmployeeModel;
import model.models.EmptyModel;
import model.models.ReportParametersData;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.List;

/**
 * Dialog for report parameters setup
 */
public class ReportParametersDialog extends JDialog {

    private JComboBox<Role> roleBox;
    private JComboBox<EmployeeModel> headBox;
    private JComboBox<EmployeeModel> departmentHeadBox;
    private JComboBox<EmployeeModel> personallyLiableEmpBox;
    private JComboBox<EmployeeModel> accountantBox;
    private JComboBox<EmployeeModel> economistBox;
    private JComboBox<EmployeeModel> headTenderBox;
    private JButton okButton;
    private JButton cancelButton;
    private ReportParametersDialogListener listener;
    private String roleName;
    private String headName;
    private String departmentHeadName;
    private String personallyLiableEmpName;
    private String accountantName;
    private String economistName;
    private String headTenderName;

    public ReportParametersDialog(MainFrame parent) {
        super(parent, Labels.getProperty("reportParameters"), true);
        setSize(330, 390);
        setResizable(false);
        setLocationRelativeTo(parent);

        Dimension preferredFieldDim = new Dimension(235, 15);

        roleBox = new JComboBox<>();
        roleBox.setSize(preferredFieldDim);
        roleBox.addItem(Role.DIRECTOR);
        roleBox.setEnabled(false);

        headBox = new JComboBox<>();
        headBox.setSize(preferredFieldDim);
        headBox.addItem(EmptyModel.EMPLOYEE);

        departmentHeadBox = new JComboBox<>();
        departmentHeadBox.setSize(preferredFieldDim);
        departmentHeadBox.addItem(EmptyModel.EMPLOYEE);

        personallyLiableEmpBox = new JComboBox<>();
        personallyLiableEmpBox.setSize(preferredFieldDim);
        personallyLiableEmpBox.addItem(EmptyModel.EMPLOYEE);

        accountantBox = new JComboBox<>();
        accountantBox.setSize(preferredFieldDim);
        accountantBox.addItem(EmptyModel.EMPLOYEE);

        economistBox = new JComboBox<>();
        economistBox.setSize(preferredFieldDim);
        economistBox.addItem(EmptyModel.EMPLOYEE);

        headTenderBox = new JComboBox<>();
        headTenderBox.setSize(preferredFieldDim);
        headTenderBox.addItem(EmptyModel.EMPLOYEE);

        okButton = new JButton(Labels.getProperty("print"));

        cancelButton = new JButton(Labels.getProperty("cancel"));

        createLayout();

        roleBox.addActionListener(e -> {
            if (listener != null) {
                Role role = (Role) roleBox.getSelectedItem();
                listener.roleSelectionOccurred(role);
            }
        });

        okButton.addActionListener(e -> {
            if (listener != null) {
                setEmployeesData();
                listener.reportParametersSelectionOccurred();
                setVisible(false);
                clear();
            }
        });

        cancelButton.addActionListener(e -> {
            setVisible(false);
            clear();
        });
    }

    private void setEmployeesData() {
        roleName = roleBox.getSelectedItem().toString();
        headName = headBox.getSelectedItem().toString();
        departmentHeadName = departmentHeadBox.getSelectedItem().toString();
        personallyLiableEmpName = personallyLiableEmpBox.getSelectedItem().toString();
        accountantName = accountantBox.getSelectedItem().toString();
        economistName = economistBox.getSelectedItem().toString();
        headTenderName = headTenderBox.getSelectedItem().toString();

        ReportParametersData.getInstance().setData(roleName, headName, departmentHeadName,
                personallyLiableEmpName, accountantName, economistName, headTenderName);
    }

    private void clear() {
        roleName = EmptyModel.STRING;
        headName = EmptyModel.STRING;
        departmentHeadName = EmptyModel.STRING;
        personallyLiableEmpName = EmptyModel.STRING;
        accountantName = EmptyModel.STRING;
        economistName = EmptyModel.STRING;
        headTenderName = EmptyModel.STRING;
    }

    public void setHeadBoxData(List<EmployeeModel> db) {
        Utils.setBoxData(headBox, db, EmptyModel.EMPLOYEE, false);
    }

    public void setDepartmentHeadBoxData(List<EmployeeModel> db) {
        Utils.setBoxData(departmentHeadBox, db, EmptyModel.EMPLOYEE, false);
    }

    public void setPersonallyLiableEmpBoxData(List<EmployeeModel> db) {
        Utils.setBoxData(personallyLiableEmpBox, db, EmptyModel.EMPLOYEE, false);
    }

    public void setAccountantBoxData(List<EmployeeModel> db) {
        Utils.setBoxData(accountantBox, db, EmptyModel.EMPLOYEE, false);
    }

    public void setEconomistBoxData(List<EmployeeModel> db) {
        Utils.setBoxData(economistBox, db, EmptyModel.EMPLOYEE, false);
    }

    public void setHeadTenderBoxData(List<EmployeeModel> db) {
        Utils.setBoxData(headTenderBox, db, EmptyModel.EMPLOYEE, false);
    }

    public void setListener(ReportParametersDialogListener listener) {
        this.listener = listener;
    }

    private void createLayout() {

        int space = 5;
        Border emptyBorder = BorderFactory.createEmptyBorder(space, space, space, space);
        Border etchedBorder = BorderFactory.createEtchedBorder();
        Border compoundBorder = BorderFactory.createCompoundBorder(emptyBorder, etchedBorder);

        JPanel selectionPanel = new JPanel();
        selectionPanel.setBorder(compoundBorder);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5));

        selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.Y_AXIS));
        selectionPanel.add(new JLabel(Labels.getProperty("headPostion")));
        selectionPanel.add(roleBox);
        selectionPanel.add(new JLabel(Labels.getProperty("headOrganization")));
        selectionPanel.add(headBox);
        selectionPanel.add(new JLabel(Labels.getProperty("role.headOfDepartment")));
        selectionPanel.add(departmentHeadBox);
        selectionPanel.add(new JLabel(Labels.getProperty("role.personallyLiableEmployee")));
        selectionPanel.add(personallyLiableEmpBox);
        selectionPanel.add(new JLabel(Labels.getProperty("role.accountant")));
        selectionPanel.add(accountantBox);
        selectionPanel.add(new JLabel(Labels.getProperty("role.economist")));
        selectionPanel.add(economistBox);
        selectionPanel.add(new JLabel(Labels.getProperty("role.secretaryOfTenderCommittee")));
        selectionPanel.add(headTenderBox);

        okButton.setPreferredSize(cancelButton.getPreferredSize());
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        setLayout(new BorderLayout());
        add(selectionPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }
}
