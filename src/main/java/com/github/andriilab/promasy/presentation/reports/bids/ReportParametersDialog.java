package com.github.andriilab.promasy.presentation.reports.bids;

import com.github.andriilab.promasy.data.controller.LoginData;
import com.github.andriilab.promasy.data.queries.employees.GetEmployeesQuery;
import com.github.andriilab.promasy.domain.EmptyModel;
import com.github.andriilab.promasy.domain.organization.entities.Employee;
import com.github.andriilab.promasy.domain.organization.enums.Role;
import com.github.andriilab.promasy.presentation.MainFrame;
import com.github.andriilab.promasy.presentation.commons.Labels;
import com.github.andriilab.promasy.presentation.components.PJComboBox;

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

    private final PJComboBox<Role> roleBox;
    private final PJComboBox<Employee> headBox;
    private final PJComboBox<Employee> departmentHeadBox;
    private final PJComboBox<Employee> personallyLiableEmpBox;
    private final PJComboBox<Employee> accountantBox;
    private final PJComboBox<Employee> economistBox;
    private final PJComboBox<Employee> headTenderBox;
    private final JButton okButton;
    private final JButton cancelButton;
    private ReportParametersDialogListener listener;
    private final Map<String, Object> parameters;
    private MainFrame mainFrame;

    public ReportParametersDialog(MainFrame parent) {
        super(parent, Labels.getProperty("reportParameters"), true);
        this.mainFrame = parent;
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
                listener.roleSelectionOccurred((Role) roleBox.getSelectedItem());
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
        parameters.put("generatedDate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    private void setHeadBoxData(List<Employee> db) {
        headBox.setBoxData(db, EmptyModel.EMPLOYEE, false);
    }

    private void setDepartmentHeadBoxData(List<Employee> db) {
        departmentHeadBox.setBoxData(db, EmptyModel.EMPLOYEE, false);
    }

    private void setPersonallyLiableEmpBoxData(List<Employee> db) {
        personallyLiableEmpBox.setBoxData(db, EmptyModel.EMPLOYEE, false);
    }

    private void setAccountantBoxData(List<Employee> db) {
        accountantBox.setBoxData(db, EmptyModel.EMPLOYEE, false);
    }

    private void setEconomistBoxData(List<Employee> db) {
        economistBox.setBoxData(db, EmptyModel.EMPLOYEE, false);
    }

    private void setHeadTenderBoxData(List<Employee> db) {
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

    @Override
    public void setVisible(boolean b) {
        GetEmployeesQuery query;
        // search for heads of department in department
        query = new GetEmployeesQuery(Role.HEAD_OF_DEPARTMENT,
                mainFrame.getBidsListPanel().getSelectedDepartment().getModelId());
        setDepartmentHeadBoxData(listener.getEmployees(query));

        // search for personally liable employee in department
        query = new GetEmployeesQuery(Role.PERSONALLY_LIABLE_EMPLOYEE,
                mainFrame.getBidsListPanel().getSelectedDepartment().getModelId());
        setPersonallyLiableEmpBoxData(listener.getEmployees(query));

        // search for chief accountant
        query = new GetEmployeesQuery(Role.ACCOUNTANT);
        setAccountantBoxData(listener.getEmployees(query));

        // search for chief economist
        query = new GetEmployeesQuery(Role.ECONOMIST);
        setEconomistBoxData(listener.getEmployees(query));

        // search for SECRETARY OF TENDER COMMITTEE
        query = new GetEmployeesQuery(Role.SECRETARY_OF_TENDER_COMMITTEE);
        setHeadTenderBoxData(listener.getEmployees(query));

        // search for director
        query = new GetEmployeesQuery(Role.DIRECTOR);
        setHeadBoxData(listener.getEmployees(query));

        // show dialog with selectors for director, head of department, PLE, accountant, economist, SECRETARY OF TENDER COMMITTEE
        super.setVisible(b);
    }
}
