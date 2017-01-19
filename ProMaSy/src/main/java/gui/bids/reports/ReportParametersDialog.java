package gui.bids.reports;

import gui.Labels;
import gui.MainFrame;
import model.enums.Role;
import model.models.EmployeeModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.List;

/**
 * Dialog for report parameters setup
 */
public class ReportParametersDialog extends JDialog {

    private final EmployeeModel emptyEmployeeModel = new EmployeeModel();
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
    private MainFrame parent;

    public ReportParametersDialog(MainFrame parent) {
        super(parent, Labels.getProperty("reportParameters"), true);
        this.parent = parent;
        setSize(330, 390);
        setResizable(false);
        setLocationRelativeTo(this.parent);

        Dimension preferredFieldDim = new Dimension(235, 15);

        roleBox = new JComboBox<>(Role.values());
        roleBox.setSize(preferredFieldDim);
        roleBox.addItem(Role.DIRECTOR);

        headBox = new JComboBox<>();
        headBox.setSize(preferredFieldDim);
        headBox.addItem(emptyEmployeeModel);

        departmentHeadBox = new JComboBox<>();
        departmentHeadBox.setSize(preferredFieldDim);
        departmentHeadBox.addItem(emptyEmployeeModel);

        personallyLiableEmpBox = new JComboBox<>();
        personallyLiableEmpBox.setSize(preferredFieldDim);
        personallyLiableEmpBox.addItem(emptyEmployeeModel);

        accountantBox = new JComboBox<>();
        accountantBox.setSize(preferredFieldDim);
        accountantBox.addItem(emptyEmployeeModel);

        economistBox = new JComboBox<>();
        economistBox.setSize(preferredFieldDim);
        economistBox.addItem(emptyEmployeeModel);

        headTenderBox = new JComboBox<>();
        headTenderBox.setSize(preferredFieldDim);
        headTenderBox.addItem(emptyEmployeeModel);

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
            if (checkBoxes() && listener != null) {
                listener.reportParametersSelectionOccurred(new ReportParametersEvent(roleName, headName,
                        departmentHeadName, personallyLiableEmpName, accountantName, economistName, headTenderName));
                setVisible(false);
                clear();
            }
        });

        cancelButton.addActionListener(e -> {

            setVisible(false);
            clear();
        });
    }

    private boolean checkBoxes() {
        roleName = roleBox.getSelectedItem().toString();

        headName = headBox.getSelectedItem().toString();

        departmentHeadName = departmentHeadBox.getSelectedItem().toString();

        personallyLiableEmpName = personallyLiableEmpBox.getSelectedItem().toString();

        accountantName = accountantBox.getSelectedItem().toString();

        economistName = economistBox.getSelectedItem().toString();

        headTenderName = headTenderBox.getSelectedItem().toString();

        return true;
    }

    private void clear() {
        roleName = "";
        headName = "";
        departmentHeadName = "";
        personallyLiableEmpName = "";
        accountantName = "";
        economistName = "";
        headTenderName = "";
    }

    public void setHeadBoxData(List<EmployeeModel> db) {
        headBox.removeAllItems();
        if (db.isEmpty()) {
            headBox.addItem(emptyEmployeeModel);
        } else {
            for (EmployeeModel model : db) {
                if (model.isActive()) {
                    headBox.addItem(model);
                }
            }
        }
    }

    public void setDepartmentHeadBoxData(List<EmployeeModel> db) {
        departmentHeadBox.removeAllItems();
        if (db.isEmpty()) {
            departmentHeadBox.addItem(emptyEmployeeModel);
        } else {
            for (EmployeeModel model : db) {
                departmentHeadBox.addItem(model);
            }
        }
    }

    public void setPersonallyLiableEmpBoxData(List<EmployeeModel> db) {
        personallyLiableEmpBox.removeAllItems();
        if (db.isEmpty()) {
            personallyLiableEmpBox.addItem(emptyEmployeeModel);
        } else {
            for (EmployeeModel model : db) {
                personallyLiableEmpBox.addItem(model);
            }
        }
    }

    public void setAccountantBoxData(List<EmployeeModel> db) {
        accountantBox.removeAllItems();
        if (db.isEmpty()) {
            accountantBox.addItem(emptyEmployeeModel);
        } else {
            for (EmployeeModel model : db) {
                accountantBox.addItem(model);
            }
        }
    }

    public void setEconomistBoxData(List<EmployeeModel> db) {
        economistBox.removeAllItems();
        if (db.isEmpty()) {
            economistBox.addItem(emptyEmployeeModel);
        } else {
            for (EmployeeModel model : db) {
                economistBox.addItem(model);
            }
        }
    }

    public void setHeadTenderBoxData(List<EmployeeModel> db) {
        headTenderBox.removeAllItems();
        if (db.isEmpty()) {
            headTenderBox.addItem(emptyEmployeeModel);
        } else {
            for (EmployeeModel model : db) {
                headTenderBox.addItem(model);
            }
        }
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
