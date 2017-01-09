package gui.finance;

import gui.CrEdDelButtons;
import gui.Labels;
import gui.Utils;
import model.dao.LoginData;
import model.models.*;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Panel displays data about current finances and its relation to departments.
 */
public class FinancePanel extends JPanel {

    private final DepartmentModel emptyDepartmentModel = new DepartmentModel();
    private final EmployeeModel emptyEmployeeModel = new EmployeeModel();
    private final java.util.Date defaultStartDate = (new GregorianCalendar(Year.now().getValue(), 0, 1)).getTime();
    private final java.util.Date defaultEndDate = (new GregorianCalendar(Year.now().getValue(), 11, 31)).getTime();
    private final FinanceModel emptyFinanceModel = new FinanceModel();
    private final FinanceDepartmentModel emptyFinanceDepartmentModel = new FinanceDepartmentModel();
    private JTextField orderNumberField;
    private JTextField orderNameField;
    private JTextField financeAmountField;
    private JXDatePicker startDatePicker;
    private JXDatePicker endDatePicker;
    private JButton createOrderButton;
    private JButton editOrderButton;
    private JButton deleteOrderButton;
    private JTable financeTable;
    private FinanceTableModel financeTableModel;
    private JComboBox<DepartmentModel> departmentBox;
    private JComboBox<EmployeeModel> employeeBox;
    private JTextField financeDepAmountField;
    private JButton createDepOrderButton;
    private JButton editDepOrderButton;
    private JButton deleteDepOrderButton;
    private JTable depFinanceTable;
    private DepartmentFinanceTableModel departmentFinanceTableModel;
    private FinancePanelListener listener;
    private JFrame parent;
    private int orderNumber;
    private String orderName;
    private Date startDate;
    private Date endDate;
    private BigDecimal financeAmount;
    private FinanceModel selectedFinanceModel;
    private long selectedOrder = 0;
    private DepartmentModel selectedDepartment;
    private EmployeeModel selectedEmployee;
    private BigDecimal depFinanceAmount;
    private FinanceDepartmentModel selectedDepFinModel;
    private boolean useUserDepartment = false;

    public FinancePanel(JFrame parent) {
        this.parent = parent;

        selectedFinanceModel = emptyFinanceModel;

        orderNumberField = new JTextField(10);
        orderNameField = new JTextField(15);
        financeAmountField = new JTextField(10);
        startDatePicker = new JXDatePicker(Locale.getDefault());
        startDatePicker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        startDatePicker.setDate(defaultStartDate);
        endDatePicker = new JXDatePicker(Locale.getDefault());
        endDatePicker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        endDatePicker.setDate(defaultEndDate);

        CrEdDelButtons cedFinance = new CrEdDelButtons(Labels.getProperty("finance_ced"));

        createOrderButton = cedFinance.getCreateButton();
        editOrderButton = cedFinance.getEditButton();
        deleteOrderButton = cedFinance.getDeleteButton();

        editOrderButton.setEnabled(false);
        deleteOrderButton.setEnabled(false);

        selectedOrder = 0;

        financeTableModel = new FinanceTableModel();
        financeTable = new JTable(financeTableModel);
        financeTable.getColumnModel().getColumn(0).setMaxWidth(150);
        financeTable.getColumnModel().getColumn(1).setMinWidth(200);
        financeTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ev) {
                int row = financeTable.rowAtPoint(ev.getPoint());
                financeTable.getSelectionModel().setSelectionInterval(row, row);

                if (ev.getButton() == MouseEvent.BUTTON1) {
                    selectedFinanceModel = (FinanceModel) financeTable.getValueAt(row, 1);
                    setDepartmentFinanceTableData(selectedFinanceModel.getDepartmentModels());
                    clearDepFinPanel();
                    if (!selectedFinanceModel.equals(emptyFinanceModel)) {
                        editOrderButton.setEnabled(true);
                        deleteOrderButton.setEnabled(true);
                        createDepOrderButton.setEnabled(true);
                        editDepOrderButton.setEnabled(false);
                        deleteDepOrderButton.setEnabled(false);
                        orderNumberField.setText(String.valueOf(selectedFinanceModel.getFinanceNumber()));
                        orderNameField.setText(selectedFinanceModel.getFinanceName());
                        financeAmountField.setText(selectedFinanceModel.getTotalAmount().setScale(2, RoundingMode.CEILING).toString());
                        startDatePicker.setDate(selectedFinanceModel.getStartDate());
                        endDatePicker.setDate(selectedFinanceModel.getEndDate());
                        selectedOrder = selectedFinanceModel.getModelId();
                    }
                }
            }
        });

        departmentBox = new JComboBox<>();
        departmentBox.setPreferredSize(new Dimension(270, 25));
        departmentBox.addItem(emptyDepartmentModel);
        departmentBox.addActionListener(e -> {
            DepartmentModel selectedDepartmentModel = (DepartmentModel) departmentBox.getSelectedItem();
            List<EmployeeModel> employees = new LinkedList<>();
            for (SubdepartmentModel model : selectedDepartmentModel.getSubdepartments()) {
                employees.addAll(model.getEmployees());
            }
            setEmployeeBoxData(employees);
        });

        employeeBox = new JComboBox<>();
        employeeBox.setPreferredSize(new Dimension(150, 25));
        employeeBox.addItem(emptyEmployeeModel);

        financeDepAmountField = new JTextField(10);

        CrEdDelButtons cedDepartmentFinances = new CrEdDelButtons(Labels.getProperty("dep_order_ced"));
        createDepOrderButton = cedDepartmentFinances.getCreateButton();
        editDepOrderButton = cedDepartmentFinances.getEditButton();
        deleteDepOrderButton = cedDepartmentFinances.getDeleteButton();

        createDepOrderButton.setEnabled(false);
        editDepOrderButton.setEnabled(false);
        deleteDepOrderButton.setEnabled(false);

        departmentFinanceTableModel = new DepartmentFinanceTableModel();
        depFinanceTable = new JTable(departmentFinanceTableModel);
        depFinanceTable.getColumnModel().getColumn(0).setMinWidth(230);
        depFinanceTable.getColumnModel().removeColumn(depFinanceTable.getColumnModel().getColumn(4));
        depFinanceTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ev) {
                int row = depFinanceTable.rowAtPoint(ev.getPoint());
                depFinanceTable.getSelectionModel().setSelectionInterval(row, row);

                if (ev.getButton() == MouseEvent.BUTTON1) {
                    selectedDepFinModel = (FinanceDepartmentModel) depFinanceTable.getModel().getValueAt(row, 4);
                    if (!selectedDepFinModel.equals(emptyFinanceDepartmentModel)) {
                        editOrderButton.setEnabled(true);
                        deleteOrderButton.setEnabled(true);
                        createDepOrderButton.setEnabled(true);
                        editDepOrderButton.setEnabled(true);
                        deleteDepOrderButton.setEnabled(true);
                        Utils.setBoxFromModel(departmentBox, selectedDepFinModel.getDepartment());
                        Utils.setBoxFromModel(employeeBox, selectedDepFinModel.getResponsibleEmployee());
                        financeDepAmountField.setText(selectedDepFinModel.getTotalAmount().setScale(2, RoundingMode.CEILING).toString());
                    }
                }
            }
        });

        createLayout();

        createOrderButton.addActionListener(e -> {
            if (checkFinanceInput() && listener != null) {
                FinanceModel model = new FinanceModel(orderNumber, orderName, financeAmount, startDate, endDate);
                model.setCreated();
                listener.persistModelEventOccurred(model);
            }
            clearFinancePanel();
            clearDepFinPanel();
        });

        editOrderButton.addActionListener(e -> {
            if (!selectedFinanceModel.equals(emptyFinanceModel)) {
                if (checkFinanceInput() && listener != null) {
                    selectedFinanceModel.setFinanceNumber(orderNumber);
                    selectedFinanceModel.setFinanceName(orderName);
                    selectedFinanceModel.setTotalAmount(financeAmount);
                    selectedFinanceModel.setStartDate(startDate);
                    selectedFinanceModel.setEndDate(endDate);
                    selectedFinanceModel.setUpdated();
                    listener.persistModelEventOccurred(selectedFinanceModel);
                }
                selectedFinanceModel = emptyFinanceModel;
            }
            clearFinancePanel();
            clearDepFinPanel();
        });

        deleteOrderButton.addActionListener(e -> {
            if (!selectedFinanceModel.equals(emptyFinanceModel) && cedFinance.deleteEntry(parent, selectedFinanceModel.getFinanceName()) && listener != null) {
                selectedFinanceModel.setDeleted();
                listener.persistModelEventOccurred(selectedFinanceModel);
            }
            selectedFinanceModel = emptyFinanceModel;
            clearFinancePanel();
            clearDepFinPanel();
        });

        createDepOrderButton.addActionListener(e -> {
            if (checkFinanceInput() && checkDepFinanceInput() && listener != null) {
                FinanceDepartmentModel model = new FinanceDepartmentModel(selectedOrder, selectedDepartment, selectedEmployee, depFinanceAmount);
                model.setCreated();
                listener.persistModelEventOccurred(model);
            }
            clearDepFinPanel();
        });

        editDepOrderButton.addActionListener(e -> {
            if (!selectedDepFinModel.equals(emptyFinanceDepartmentModel)) {
                if (checkFinanceInput() && checkDepFinanceInput() && listener != null) {
                    selectedDepFinModel.setDepartment(selectedDepartment);
                    selectedDepFinModel.setResponsibleEmployee(selectedEmployee);
                    selectedDepFinModel.setTotalAmount(depFinanceAmount);
                    selectedDepFinModel.setUpdated();
                    listener.persistModelEventOccurred(selectedDepFinModel);
                }
                selectedDepFinModel = emptyFinanceDepartmentModel;
            }
            clearDepFinPanel();
        });

        deleteDepOrderButton.addActionListener(e -> {
            if (!selectedDepFinModel.equals(emptyFinanceDepartmentModel) && cedDepartmentFinances.deleteEntry(parent, selectedDepFinModel.getFinances().getFinanceName() + "' " + Labels.getProperty("for_department") + " '" + selectedDepFinModel.getDepartment().getDepName()) && listener != null) {
                selectedDepFinModel.setDeleted();
                listener.persistModelEventOccurred(selectedDepFinModel);
            }
            selectedDepFinModel = emptyFinanceDepartmentModel;
            clearDepFinPanel();
        });
    }

    private void clearFinancePanel() {
        orderNumberField.setText("");
        orderNameField.setText("");
        financeAmountField.setText("");
        startDatePicker.setDate(defaultStartDate);
        endDatePicker.setDate(defaultEndDate);
        orderNumber = 0;
        orderName = "";
        startDate = null;
        endDate = null;
        financeAmount = null;
    }

    private void clearDepFinPanel() {
        departmentBox.setSelectedIndex(0);
        employeeBox.setSelectedIndex(0);
        financeDepAmountField.setText("");
        depFinanceAmount = null;
        selectedDepartment = emptyDepartmentModel;
        selectedEmployee = emptyEmployeeModel;
    }

    private boolean checkFinanceInput() {
        try {
            orderNumber = Integer.parseInt(orderNumberField.getText());
        } catch (NumberFormatException e) {
            Utils.wrongFormatError(parent, Labels.getProperty("orderNumber"), Labels.getProperty("integersOnly"));
            return false;
        }
        orderName = orderNameField.getText();
        startDate = new java.sql.Date(startDatePicker.getDate().getTime());
        endDate = new java.sql.Date(endDatePicker.getDate().getTime());
        String financeAmountText = financeAmountField.getText();
        if (financeAmountText.contains(",")) {
            financeAmountText = financeAmountText.replace(",", ".");
            financeAmountField.setText(financeAmountText);
        }
        if (orderName.length() < 1) {
            Utils.emptyFieldError(parent, Labels.getProperty("orderName"));
            return false;
        } else if (startDate.after(endDate)) {
            JOptionPane.showMessageDialog(parent,
                    Labels.getProperty("startDateAfterEndDate"),
                    Labels.getProperty("enteredDateError"),
                    JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (financeAmountText.length() < 1) {
            Utils.emptyFieldError(parent, Labels.getProperty("financeAmount"));
            return false;
        }
        try {
            financeAmount = new BigDecimal(financeAmountText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(parent,
                    Labels.getProperty("financeNumberFormatException"),
                    Labels.getProperty("fieldErr"),
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean checkDepFinanceInput() {
        selectedDepartment = (DepartmentModel) departmentBox.getSelectedItem();
        selectedEmployee = (EmployeeModel) employeeBox.getSelectedItem();
        String inpDepFinanceAmount = financeDepAmountField.getText();
        if (inpDepFinanceAmount.contains(",")) {
            inpDepFinanceAmount = inpDepFinanceAmount.replace(",", ".");
            financeDepAmountField.setText(inpDepFinanceAmount);
        }
        if (selectedDepartment.equals(emptyDepartmentModel)) {
            Utils.emptyFieldError(parent, Labels.getProperty("department"));
            return false;
        } else if (selectedEmployee.equals(emptyEmployeeModel)) {
            Utils.emptyFieldError(parent, Labels.getProperty("manager"));
            return false;
        } else if (inpDepFinanceAmount.length() < 1) {
            Utils.emptyFieldError(parent, Labels.getProperty("financeAmount"));
            return false;
        }
        try {
            depFinanceAmount = new BigDecimal(inpDepFinanceAmount);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(parent,
                    Labels.getProperty("financeNumberFormatException"),
                    Labels.getProperty("fieldErr"),
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public void setFinanceTableData(List<FinanceModel> db) {
        financeTableModel.setData(db);
        refreshFinanceTable();
    }

    private void refreshFinanceTable() {
        financeTableModel.fireTableDataChanged();
    }

    public void setDepartmentFinanceTableData(List<FinanceDepartmentModel> db) {
        departmentFinanceTableModel.setData(db);
        refreshDepartmentFinanceTable();
    }

    private void refreshDepartmentFinanceTable() {
        departmentFinanceTableModel.fireTableDataChanged();
    }

    public void setUseUserDepartment() {
        useUserDepartment = true;
        departmentBox.setEnabled(false);
        employeeBox.setEnabled(false);
        createDepOrderButton.setEnabled(false);
        editDepOrderButton.setEnabled(false);
        deleteDepOrderButton.setEnabled(false);
        createOrderButton.setEnabled(false);
        editOrderButton.setEnabled(false);
        deleteOrderButton.setEnabled(false);
    }

    public void setDepartmentBoxData(List<DepartmentModel> db) {
        for (DepartmentModel model : db) {
            if (model.isActive()) {
                departmentBox.addItem(model);
                if (useUserDepartment && LoginData.getInstance().getSubdepartment().getDepartment().equals(model)) {
                    departmentBox.setSelectedItem(model);
                }
            }
        }

    }

    public void setEmployeeBoxData(List<EmployeeModel> db) {
        employeeBox.removeAllItems();
        employeeBox.addItem(emptyEmployeeModel);
        for (EmployeeModel model : db) {
            if (model.isActive()) {
                employeeBox.addItem(model);
            }
        }
    }

    public void setFinancePanelListener(FinancePanelListener listener) {
        this.listener = listener;
    }

    private void createLayout() {
        JPanel financePanel = new JPanel();
        JPanel depFinancePanel = new JPanel();
        financePanel.setLayout(new BorderLayout());
        depFinancePanel.setLayout(new BorderLayout());

        Insets smallPadding = new Insets(1, 0, 1, 5);
        Insets largePadding = new Insets(1, 0, 1, 15);

        JPanel addOrderPanel = new JPanel();
        addOrderPanel.setLayout(new GridBagLayout());
        addOrderPanel.setBorder(BorderFactory.createEtchedBorder());

        JPanel addOrderButtonPanel = new JPanel();
        addOrderButtonPanel.setLayout(new FlowLayout());
        addOrderButtonPanel.add(createOrderButton);
        addOrderButtonPanel.add(editOrderButton);
        addOrderButtonPanel.add(deleteOrderButton);

        GridBagConstraints gc = new GridBagConstraints();

        ////// First row//////
        gc.gridy = 0;
        gc.fill = GridBagConstraints.FIRST_LINE_START;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        addOrderPanel.add(new JLabel(Labels.getProperty("financeNumber") + ":"), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        addOrderPanel.add(orderNumberField, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        addOrderPanel.add(new JLabel(Labels.getProperty("financeName") + ":"), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        addOrderPanel.add(orderNameField, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        addOrderPanel.add(new JLabel(Labels.getProperty("financeAmount") + ":"), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        addOrderPanel.add(financeAmountField, gc);

        /////Next row////
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        addOrderPanel.add(new JLabel(Labels.getProperty("dateStart") + ":"), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        addOrderPanel.add(startDatePicker, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        addOrderPanel.add(new JLabel(Labels.getProperty("dateEnd") + ":"), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        addOrderPanel.add(endDatePicker, gc);

        gc.gridx = 5;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        addOrderPanel.add(addOrderButtonPanel, gc);

        financePanel.add(addOrderPanel, BorderLayout.NORTH);
        financePanel.add(new JScrollPane(financeTable), BorderLayout.CENTER);

        JPanel addDepOrderPanel = new JPanel();
        addDepOrderPanel.setLayout(new GridBagLayout());
        addDepOrderPanel.setBorder(BorderFactory.createEtchedBorder());

        JPanel addDepOrderButtonPanel = new JPanel();
        addDepOrderButtonPanel.setLayout(new FlowLayout());
        addDepOrderButtonPanel.add(createDepOrderButton);
        addDepOrderButtonPanel.add(editDepOrderButton);
        addDepOrderButtonPanel.add(deleteDepOrderButton);

        gc = new GridBagConstraints();

        ////// First row//////
        gc.gridy++;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        addDepOrderPanel.add(new JLabel(Labels.getProperty("department") + ":"), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        addDepOrderPanel.add(departmentBox, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        addDepOrderPanel.add(new JLabel(Labels.getProperty("manager") + ":"), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        addDepOrderPanel.add(employeeBox, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        addDepOrderPanel.add(new JLabel(Labels.getProperty("financeAmount") + ":"), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        addDepOrderPanel.add(financeDepAmountField, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        addDepOrderPanel.add(addDepOrderButtonPanel, gc);

        depFinancePanel.add(addDepOrderPanel, BorderLayout.NORTH);
        depFinancePanel.add(new JScrollPane(depFinanceTable), BorderLayout.CENTER);

        setLayout(new BorderLayout());
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, financePanel, depFinancePanel);
        splitPane.setResizeWeight(0.65);
        splitPane.setEnabled(false);
        add(splitPane, BorderLayout.CENTER);
    }
}
