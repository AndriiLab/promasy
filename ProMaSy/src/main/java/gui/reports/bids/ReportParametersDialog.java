package gui.reports.bids;

import gui.MainFrame;
import gui.commons.Labels;
import gui.components.PJComboBox;
import model.dao.LoginData;
import model.enums.Role;
import model.models.EmployeeModel;
import model.models.EmptyModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dialog for report parameters setup
 */
public class ReportParametersDialog extends JDialog {

    private PJComboBox<Role> roleBox;
    private PJComboBox<EmployeeModel> headBox;
    private PJComboBox<EmployeeModel> departmentHeadBox;
    private PJComboBox<EmployeeModel> personallyLiableEmpBox;
    private PJComboBox<EmployeeModel> accountantBox;
    private PJComboBox<EmployeeModel> economistBox;
    private PJComboBox<EmployeeModel> headTenderBox;
    private JButton okButton;
    private JButton cancelButton;
    private ReportParametersDialogListener listener;
    private Map<String, Object> parameters;

    public ReportParametersDialog(MainFrame parent) {
        super(parent, Labels.getProperty("reportParameters"), true);
        setSize(330, 390);
        setResizable(false);
        setLocationRelativeTo(parent);

        Dimension preferredFieldDim = new Dimension(235, 15);
        parameters = new HashMap<>();

        roleBox = new PJComboBox<>();
        roleBox.setSize(preferredFieldDim);
        roleBox.addItem(Role.DIRECTOR);
        roleBox.setEnabled(false);

        headBox = new PJComboBox<>();
        headBox.setSize(preferredFieldDim);
        headBox.addItem(EmptyModel.EMPLOYEE);

        departmentHeadBox = new PJComboBox<>();
        departmentHeadBox.setSize(preferredFieldDim);
        departmentHeadBox.addItem(EmptyModel.EMPLOYEE);

        personallyLiableEmpBox = new PJComboBox<>();
        personallyLiableEmpBox.setSize(preferredFieldDim);
        personallyLiableEmpBox.addItem(EmptyModel.EMPLOYEE);

        accountantBox = new PJComboBox<>();
        accountantBox.setSize(preferredFieldDim);
        accountantBox.addItem(EmptyModel.EMPLOYEE);

        economistBox = new PJComboBox<>();
        economistBox.setSize(preferredFieldDim);
        economistBox.addItem(EmptyModel.EMPLOYEE);

        headTenderBox = new PJComboBox<>();
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
                getPrintData();
                listener.reportParametersSelectionOccurred(parameters);
                setVisible(false);
            }
        });

        cancelButton.addActionListener(e -> setVisible(false));
    }

    private void getPrintData() {
        parameters.clear();

        parameters.put("headRoleName", roleBox.getSelectedItem().toString());
        parameters.put("headName", headBox.getSelectedItem().toString());
        parameters.put("departmentHeadName", departmentHeadBox.getSelectedItem().toString());
        parameters.put("personallyLiableEmpName", personallyLiableEmpBox.getSelectedItem().toString());
        parameters.put("accountantName", accountantBox.getSelectedItem().toString());
        parameters.put("economistName", economistBox.getSelectedItem().toString());
        parameters.put("headTenderName", headTenderBox.getSelectedItem().toString());
        parameters.put("customerName", LoginData.getInstance().getShortName());
        parameters.put("customerPhone", LoginData.getInstance().getPhoneMain());
        parameters.put("customerPhone", LoginData.getInstance().getPhoneMain());
        parameters.put("generatedDate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    public void setHeadBoxData(List<EmployeeModel> db) {
        headBox.setBoxData(db, EmptyModel.EMPLOYEE, false);
    }

    public void setDepartmentHeadBoxData(List<EmployeeModel> db) {
        departmentHeadBox.setBoxData(db, EmptyModel.EMPLOYEE, false);
    }

    public void setPersonallyLiableEmpBoxData(List<EmployeeModel> db) {
        personallyLiableEmpBox.setBoxData(db, EmptyModel.EMPLOYEE, false);
    }

    public void setAccountantBoxData(List<EmployeeModel> db) {
        accountantBox.setBoxData(db, EmptyModel.EMPLOYEE, false);
    }

    public void setEconomistBoxData(List<EmployeeModel> db) {
        economistBox.setBoxData(db, EmptyModel.EMPLOYEE, false);
    }

    public void setHeadTenderBoxData(List<EmployeeModel> db) {
        headTenderBox.setBoxData(db, EmptyModel.EMPLOYEE, false);
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
