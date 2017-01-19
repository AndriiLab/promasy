package gui.finance;

import com.sun.istack.internal.Nullable;
import gui.CrEdDelButtons;
import gui.Labels;
import gui.Utils;
import model.dao.LoginData;
import model.enums.BidType;
import model.models.DepartmentModel;
import model.models.FinanceDepartmentModel;
import model.models.FinanceModel;
import model.models.SubdepartmentModel;
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
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Panel displays data about current finances and its relation to departments.
 */
public class FinancePanel extends JPanel {

    private final DepartmentModel emptyDepartmentModel = new DepartmentModel();
    private final SubdepartmentModel emptySubdepartmentModel = new SubdepartmentModel();
    private final java.util.Date defaultStartDate = (new GregorianCalendar(Year.now().getValue(), 0, 1)).getTime();
    private final java.util.Date defaultEndDate = (new GregorianCalendar(Year.now().getValue(), 11, 31)).getTime();
    private final FinanceModel emptyFinanceModel = new FinanceModel();
    private final FinanceDepartmentModel emptyFinanceDepartmentModel = new FinanceDepartmentModel();
    private final List<FinanceDepartmentModel> emptyDepartmentFinancesList = new ArrayList<>();
    private final String emptyString = "";
    private JTextField orderNumberField;
    private JTextField orderNameField;
    private JTextField materialsAmountField;
    private JTextField equipmentAmountField;
    private JTextField servicesAmountField;
    private JXDatePicker startDatePicker;
    private JXDatePicker endDatePicker;
    private JButton createOrderButton;
    private JButton editOrderButton;
    private JButton deleteOrderButton;
    private JTable financeTable;
    private FinanceTableModel financeTableModel;
    private JComboBox<DepartmentModel> departmentBox;
    private JComboBox<SubdepartmentModel> subdepartmentBox;
    private JTextField depMaterialsAmountField;
    private JTextField depEquipmentAmountField;
    private JTextField depServicesAmountField;
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
    private BigDecimal materialAmount;
    private BigDecimal equipmentAmount;
    private BigDecimal servicesAmount;
    private FinanceModel selectedFinanceModel;
    private DepartmentModel selectedDepartment;
    private SubdepartmentModel selectedSubdepartment;
    private BigDecimal depMaterialAmount;
    private BigDecimal depEquipmentAmount;
    private BigDecimal depServicesAmount;
    private FinanceDepartmentModel selectedDepFinModel;
    private boolean useUserDepartment = false;

    public FinancePanel(JFrame parent) {
        this.parent = parent;

        selectedFinanceModel = emptyFinanceModel;

        orderNumberField = new JTextField(10);
        orderNameField = new JTextField(15);
        materialsAmountField = new JTextField(10);
        equipmentAmountField = new JTextField(10);
        servicesAmountField = new JTextField(10);
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
                    setDepartmentFinanceTableData(selectedFinanceModel.getFinanceDepartmentModels());
                    clearDepFinPanel();
                    if (!selectedFinanceModel.equals(emptyFinanceModel)) {
                        editOrderButton.setEnabled(true);
                        deleteOrderButton.setEnabled(true);
                        createDepOrderButton.setEnabled(true);
                        editDepOrderButton.setEnabled(false);
                        deleteDepOrderButton.setEnabled(false);
                        departmentBox.setEnabled(true);
                        orderNumberField.setText(String.valueOf(selectedFinanceModel.getFinanceNumber()));
                        orderNameField.setText(selectedFinanceModel.getFinanceName());
                        materialsAmountField.setText(selectedFinanceModel.getTotalAmount(BidType.MATERIALS).setScale(2, RoundingMode.CEILING).toString());
                        equipmentAmountField.setText(selectedFinanceModel.getTotalAmount(BidType.EQUIPMENT).setScale(2, RoundingMode.CEILING).toString());
                        servicesAmountField.setText(selectedFinanceModel.getTotalAmount(BidType.SERVICES).setScale(2, RoundingMode.CEILING).toString());
                        startDatePicker.setDate(selectedFinanceModel.getStartDate());
                        endDatePicker.setDate(selectedFinanceModel.getEndDate());
                    }
                }
            }
        });

        departmentBox = new JComboBox<>();
        departmentBox.setPreferredSize(new Dimension(270, 25));
        departmentBox.addItem(emptyDepartmentModel);
        departmentBox.setEnabled(false);
        departmentBox.addActionListener(e -> {
            DepartmentModel selectedDepartmentModel = (DepartmentModel) departmentBox.getSelectedItem();
            setSubdepartmentBoxData(selectedDepartmentModel.getSubdepartments());
            subdepartmentBox.setEnabled(true);
        });

        subdepartmentBox = new JComboBox<>();
        subdepartmentBox.setPreferredSize(new Dimension(150, 25));
        subdepartmentBox.setEnabled(false);
        subdepartmentBox.addItem(emptySubdepartmentModel);

        depMaterialsAmountField = new JTextField(10);
        depEquipmentAmountField = new JTextField(10);
        depServicesAmountField = new JTextField(10);

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
                        Utils.setBoxFromModel(departmentBox, selectedDepFinModel.getSubdepartment().getDepartment());
                        Utils.setBoxFromModel(subdepartmentBox, selectedDepFinModel.getSubdepartment());
                        depMaterialsAmountField.setText(selectedDepFinModel.getTotalAmount(BidType.MATERIALS).setScale(2, RoundingMode.CEILING).toString());
                        depEquipmentAmountField.setText(selectedDepFinModel.getTotalAmount(BidType.EQUIPMENT).setScale(2, RoundingMode.CEILING).toString());
                        depServicesAmountField.setText(selectedDepFinModel.getTotalAmount(BidType.SERVICES).setScale(2, RoundingMode.CEILING).toString());
                    }
                }
            }
        });

        createLayout();

        createOrderButton.addActionListener(e -> {
            if (checkFinanceInput() && listener != null) {
                FinanceModel model = new FinanceModel(orderNumber, orderName, materialAmount, equipmentAmount, servicesAmount, startDate, endDate);
                model.setCreated();
                listener.persistModelEventOccurred(model);
                setDepartmentFinanceTableData(emptyDepartmentFinancesList);
                clearFinancePanel();
                clearDepFinPanel();
            }
        });

        editOrderButton.addActionListener(e -> {
            if (!selectedFinanceModel.equals(emptyFinanceModel) && checkFinanceInput() && listener != null) {
                selectedFinanceModel.setFinanceNumber(orderNumber);
                selectedFinanceModel.setFinanceName(orderName);
                selectedFinanceModel.setTotalMaterials(materialAmount);
                selectedFinanceModel.setTotalEquipment(equipmentAmount);
                selectedFinanceModel.setTotalServices(servicesAmount);
                selectedFinanceModel.setStartDate(startDate);
                selectedFinanceModel.setEndDate(endDate);
                selectedFinanceModel.setUpdated();
                listener.persistModelEventOccurred(selectedFinanceModel);
                setDepartmentFinanceTableData(selectedFinanceModel.getFinanceDepartmentModels());
                selectedFinanceModel = emptyFinanceModel;
                clearFinancePanel();
                clearDepFinPanel();
            }
        });

        deleteOrderButton.addActionListener(e -> {
            if (!selectedFinanceModel.equals(emptyFinanceModel) && cedFinance.deleteEntry(parent, selectedFinanceModel.getFinanceName()) && listener != null) {
                selectedFinanceModel.setDeleted();
                listener.persistModelEventOccurred(selectedFinanceModel);
                setDepartmentFinanceTableData(emptyDepartmentFinancesList);
                selectedFinanceModel = emptyFinanceModel;
                clearFinancePanel();
                clearDepFinPanel();
            }
        });

        createDepOrderButton.addActionListener(e -> {
            if (checkFinanceInput() && checkDepFinanceInput() && listener != null) {
                FinanceDepartmentModel model = new FinanceDepartmentModel(selectedFinanceModel, selectedSubdepartment, depMaterialAmount, depEquipmentAmount, depServicesAmount);
                model.setCreated();
                selectedFinanceModel.addFinanceDepartmentModel(model);
                selectedSubdepartment.addFinanceDepartmentModel(model);
                listener.persistModelEventOccurred(model);
                setDepartmentFinanceTableData(selectedFinanceModel.getFinanceDepartmentModels());
                clearDepFinPanel();
            }
        });

        editDepOrderButton.addActionListener(e -> {
            if (!selectedDepFinModel.equals(emptyFinanceDepartmentModel) && checkFinanceInput() && checkDepFinanceInput() && listener != null) {
                selectedDepFinModel.setSubdepartment(selectedSubdepartment);
                selectedDepFinModel.setTotalMaterialsAmount(depMaterialAmount);
                selectedDepFinModel.setTotalEqupmentAmount(depEquipmentAmount);
                selectedDepFinModel.setTotalServicesAmount(depServicesAmount);
                selectedDepFinModel.setUpdated();
                selectedFinanceModel.addFinanceDepartmentModel(selectedDepFinModel);
                listener.persistModelEventOccurred(selectedDepFinModel);
                setDepartmentFinanceTableData(selectedFinanceModel.getFinanceDepartmentModels());
                selectedDepFinModel = emptyFinanceDepartmentModel;
                clearDepFinPanel();
            }
        });

        deleteDepOrderButton.addActionListener(e -> {
            if (!selectedDepFinModel.equals(emptyFinanceDepartmentModel) && cedDepartmentFinances.deleteEntry(parent, selectedDepFinModel.getFinances().getFinanceName() + "' " + Labels.getProperty("for_department") + " '" + selectedDepFinModel.getSubdepartment().getDepartment().getDepName()) && listener != null) {
                selectedDepFinModel.setDeleted();
                selectedFinanceModel.addFinanceDepartmentModel(selectedDepFinModel);
                listener.persistModelEventOccurred(selectedDepFinModel);
                setDepartmentFinanceTableData(selectedFinanceModel.getFinanceDepartmentModels());
                selectedDepFinModel = emptyFinanceDepartmentModel;
                clearDepFinPanel();
            }
        });
    }

    private void clearFinancePanel() {
        orderNumberField.setText(emptyString);
        orderNameField.setText(emptyString);
        materialsAmountField.setText(emptyString);
        equipmentAmountField.setText(emptyString);
        servicesAmountField.setText(emptyString);
        startDatePicker.setDate(defaultStartDate);
        endDatePicker.setDate(defaultEndDate);
        orderNumber = 0;
        orderName = emptyString;
        startDate = null;
        endDate = null;
        materialAmount = null;
        equipmentAmount = null;
        servicesAmount = null;
    }

    private void clearDepFinPanel() {
        departmentBox.setSelectedIndex(0);
        subdepartmentBox.setSelectedIndex(0);
        depMaterialsAmountField.setText(emptyString);
        depEquipmentAmountField.setText(emptyString);
        depServicesAmountField.setText(emptyString);
        depMaterialAmount = null;
        depEquipmentAmount = null;
        depServicesAmount = null;
        selectedDepartment = emptyDepartmentModel;
        selectedSubdepartment = emptySubdepartmentModel;
    }

    @Nullable
    private BigDecimal parseBigDecimal(JTextField jTextField, String fieldName) {
        String targetBigDecimalText = Utils.formatBigDecimal(jTextField.getText());
        if (targetBigDecimalText.isEmpty()) {
            Utils.emptyFieldError(parent, fieldName);
            jTextField.requestFocusInWindow();
            return null;
        }

        jTextField.setText(targetBigDecimalText);

        BigDecimal targetBigDecimal;
        try {
            targetBigDecimal = new BigDecimal(targetBigDecimalText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(parent,
                    Labels.getProperty("financeNumberFormatException"),
                    Labels.getProperty("fieldErr"),
                    JOptionPane.ERROR_MESSAGE);

            jTextField.requestFocusInWindow();
            return null;
        }
        if (targetBigDecimal.compareTo(BigDecimal.ZERO) < 0) {
            JOptionPane.showMessageDialog(parent,
                    Labels.getProperty("financeNumberCannotBeLessZero"),
                    Labels.getProperty("fieldErr"),
                    JOptionPane.ERROR_MESSAGE);
            jTextField.requestFocusInWindow();
            return null;
        }
        return targetBigDecimal;
    }

    @Nullable
    private BigDecimal parseSubdepartmentBigDecimal(JTextField jTextField, String fieldName, BidType bidType) {
        BigDecimal targetBigDecimal = parseBigDecimal(jTextField, fieldName);
        if (targetBigDecimal == null) {
            return null;
        } else if (targetBigDecimal.compareTo(selectedFinanceModel.getTotalAmount(bidType)) == 1) {
            JOptionPane.showMessageDialog(parent,
                    Labels.getProperty("depFinanceAmountGreaterThanFinanceAmount"),
                    Labels.getProperty("fieldErr"),
                    JOptionPane.ERROR_MESSAGE);
            jTextField.requestFocusInWindow();
            return null;
        } else if (targetBigDecimal.compareTo(selectedFinanceModel.getUnassignedAmount(bidType)) == 1) {
            BigDecimal unassignedAmount = selectedFinanceModel.getUnassignedAmount(bidType);
            JOptionPane.showMessageDialog(parent, Labels.getProperty("depFinanceAmountGreaterThanAvailableFinanceAmount") + ".\n" + Labels.withColon("unassignedFinanceAmount") + " " + unassignedAmount + Labels.withSpaceBefore("uah"), Labels.getProperty("fieldErr"), JOptionPane.ERROR_MESSAGE);
            jTextField.setText(unassignedAmount.toString());
            jTextField.requestFocusInWindow();
            return null;
        } else
            return targetBigDecimal;
    }

    private boolean checkFinanceInput() {
        try {
            orderNumber = Integer.parseInt(orderNumberField.getText());
        } catch (NumberFormatException e) {
            Utils.wrongFormatError(parent, Labels.getProperty("financeNumber"), Labels.getProperty("integersOnly"));
            orderNumberField.requestFocusInWindow();
            return false;
        }
        orderName = orderNameField.getText();
        startDate = new java.sql.Date(startDatePicker.getDate().getTime());
        endDate = new java.sql.Date(endDatePicker.getDate().getTime());
        if (orderName.isEmpty()) {
            Utils.emptyFieldError(parent, Labels.getProperty("financeName"));
            orderNameField.requestFocusInWindow();
            return false;
        } else if (startDate.after(endDate)) {
            JOptionPane.showMessageDialog(parent,
                    Labels.getProperty("startDateAfterEndDate"),
                    Labels.getProperty("enteredDateError"),
                    JOptionPane.ERROR_MESSAGE);
            endDatePicker.requestFocusInWindow();
            return false;
        }
        materialAmount = parseBigDecimal(materialsAmountField, Labels.getProperty("materialsAmount"));
        equipmentAmount = parseBigDecimal(equipmentAmountField, Labels.getProperty("equipmentAmount"));
        servicesAmount = parseBigDecimal(servicesAmountField, Labels.getProperty("servicesAmount"));

        return (materialAmount != null && equipmentAmount != null && servicesAmount != null);
    }

    private boolean checkDepFinanceInput() {
        selectedDepartment = (DepartmentModel) departmentBox.getSelectedItem();
        selectedSubdepartment = (SubdepartmentModel) subdepartmentBox.getSelectedItem();
        if (selectedDepartment.equals(emptyDepartmentModel)) {
            Utils.emptyFieldError(parent, Labels.getProperty("department"));
            departmentBox.requestFocusInWindow();
            return false;
        } else if (selectedSubdepartment.equals(emptySubdepartmentModel)) {
            Utils.emptyFieldError(parent, Labels.getProperty("subdepartment"));
            subdepartmentBox.requestFocusInWindow();
            return false;
        }
        depMaterialAmount = parseSubdepartmentBigDecimal(depMaterialsAmountField, Labels.getProperty("materialsAmount"), BidType.MATERIALS);
        depEquipmentAmount = parseSubdepartmentBigDecimal(depEquipmentAmountField, Labels.getProperty("equipmentAmount"), BidType.EQUIPMENT);
        depServicesAmount = parseSubdepartmentBigDecimal(depServicesAmountField, Labels.getProperty("servicesAmount"), BidType.SERVICES);
        return (depMaterialAmount != null && depEquipmentAmount != null && depServicesAmount != null);
    }

    public void setFinanceTableData(List<FinanceModel> db) {
        //removing all inactive items from list
        List<FinanceModel> activeList = new ArrayList<>();
        for (FinanceModel model : db) {
            if (model.isActive()) {
                activeList.add(model);
            }
        }
        financeTableModel.setData(activeList);
        refreshFinanceTable();
    }

    private void refreshFinanceTable() {
        financeTableModel.fireTableDataChanged();
    }

    public void setDepartmentFinanceTableData(List<FinanceDepartmentModel> db) {
        //removing all inactive items from list
        List<FinanceDepartmentModel> activeList = new ArrayList<>();
        for (FinanceDepartmentModel model : db) {
            if (model.isActive()) {
                activeList.add(model);
            }
        }
        departmentFinanceTableModel.setData(activeList);
        refreshDepartmentFinanceTable();
    }

    private void refreshDepartmentFinanceTable() {
        departmentFinanceTableModel.fireTableDataChanged();
    }

    public void setUseUserDepartment() {
        useUserDepartment = true;
        departmentBox.setEnabled(false);
        subdepartmentBox.setEnabled(false);
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

    public void setSubdepartmentBoxData(List<SubdepartmentModel> db) {
        subdepartmentBox.removeAllItems();
        subdepartmentBox.addItem(emptySubdepartmentModel);
        for (SubdepartmentModel model : db) {
            if (model.isActive()) {
                subdepartmentBox.addItem(model);
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
        addOrderPanel.add(new JLabel(Labels.withColon("financeNumber")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        addOrderPanel.add(orderNumberField, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        addOrderPanel.add(new JLabel(Labels.withColon("financeName")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        addOrderPanel.add(orderNameField, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        addOrderPanel.add(new JLabel(Labels.withColon("materialsAmount")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        addOrderPanel.add(materialsAmountField, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        addOrderPanel.add(new JLabel(Labels.withColon("equipmentAmount")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        addOrderPanel.add(equipmentAmountField, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        addOrderPanel.add(new JLabel(Labels.withColon("servicesAmount")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        addOrderPanel.add(servicesAmountField, gc);

        /////Next row////
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        addOrderPanel.add(new JLabel(Labels.withColon("dateStart")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        addOrderPanel.add(startDatePicker, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        addOrderPanel.add(new JLabel(Labels.withColon("dateEnd")), gc);

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
        addDepOrderPanel.add(new JLabel(Labels.withColon("department")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        addDepOrderPanel.add(departmentBox, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        addDepOrderPanel.add(new JLabel(Labels.withColon("subdepartment")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        addDepOrderPanel.add(subdepartmentBox, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        addDepOrderPanel.add(new JLabel(Labels.withColon("materialsAmount")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        addDepOrderPanel.add(depMaterialsAmountField, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        addDepOrderPanel.add(new JLabel(Labels.withColon("equipmentAmount")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        addDepOrderPanel.add(depEquipmentAmountField, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        addDepOrderPanel.add(new JLabel(Labels.withColon("servicesAmount")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        addDepOrderPanel.add(depServicesAmountField, gc);

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
