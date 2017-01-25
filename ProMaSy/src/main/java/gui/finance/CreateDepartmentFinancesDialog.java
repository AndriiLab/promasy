package gui.finance;

import gui.Labels;
import gui.Utils;
import model.enums.BidType;
import model.models.DepartmentModel;
import model.models.FinanceDepartmentModel;
import model.models.FinanceModel;
import model.models.SubdepartmentModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Dialog for creation and updating of {@link FinanceDepartmentModel}
 */
public class CreateDepartmentFinancesDialog extends JDialog {

    private final DepartmentModel emptyDepartmentModel = new DepartmentModel();
    private final SubdepartmentModel emptySubdepartmentModel = new SubdepartmentModel();

    private SubdepartmentModel selectedSubdepartment;
    private FinanceModel currentFinanceModel;
    private FinanceDepartmentModel currentFinanceDepartmentModel;

    private BigDecimal depMaterialAmount;
    private BigDecimal depEquipmentAmount;
    private BigDecimal depServicesAmount;
    private FinanceDepartmentDialogListener listener;

    private JFrame parent;
    private JComboBox<DepartmentModel> departmentBox;
    private JComboBox<SubdepartmentModel> subdepartmentBox;
    private JTextField depMaterialsAmountField;
    private JTextField depEquipmentAmountField;
    private JTextField depServicesAmountField;
    private JLabel materialsLeft;
    private JLabel equpmetLeft;
    private JLabel servicesLeft;
    private JButton okButton;
    private JButton cancelButton;
    private boolean isCreateMode;

    public CreateDepartmentFinancesDialog(JFrame parent) {
        super(parent, Labels.getProperty("addDepartmentForFinance"), true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setSize(340, 210);
        setResizable(false);

        this.parent = parent;

        currentFinanceDepartmentModel = new FinanceDepartmentModel();
        isCreateMode = true;

        materialsLeft = new JLabel();
        equpmetLeft = new JLabel();
        servicesLeft = new JLabel();

        departmentBox = new JComboBox<>();
        departmentBox.setPreferredSize(Utils.COMBOBOX_DIMENSION);
        departmentBox.addItem(emptyDepartmentModel);
        departmentBox.addActionListener(e -> {
            DepartmentModel selectedDepartmentModel = (DepartmentModel) departmentBox.getSelectedItem();
            if (selectedDepartmentModel != null) {
                setSubdepartmentBoxData(selectedDepartmentModel.getSubdepartments());
            }
            subdepartmentBox.setEnabled(true);
        });

        subdepartmentBox = new JComboBox<>();
        subdepartmentBox.setPreferredSize(Utils.COMBOBOX_DIMENSION);
        subdepartmentBox.setEnabled(false);
        subdepartmentBox.addItem(emptySubdepartmentModel);

        depMaterialsAmountField = new JTextField(10);
        depEquipmentAmountField = new JTextField(10);
        depServicesAmountField = new JTextField(10);


        okButton = new JButton(Labels.getProperty("create"));
        cancelButton = new JButton(Labels.getProperty("cancel"));

        layoutControls();

        okButton.addActionListener(e -> {
            if (checkInput() && listener != null) {
                currentFinanceDepartmentModel.setFinances(currentFinanceModel);
                currentFinanceDepartmentModel.setSubdepartment(selectedSubdepartment);
                currentFinanceDepartmentModel.setTotalMaterialsAmount(depMaterialAmount);
                currentFinanceDepartmentModel.setTotalEqupmentAmount(depEquipmentAmount);
                currentFinanceDepartmentModel.setTotalServicesAmount(depServicesAmount);
                if (currentFinanceDepartmentModel.getModelId() == 0) {
                    currentFinanceDepartmentModel.setCreated();
                } else {
                    currentFinanceDepartmentModel.setUpdated();
                }
                currentFinanceModel.addFinanceDepartmentModel(currentFinanceDepartmentModel);
                listener.persistModelEventOccurred(currentFinanceDepartmentModel);
                clear();
            }
        });

        cancelButton.addActionListener(e -> clear());

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                clear();
            }
        });

    }

    private void clear() {
        super.setTitle(Labels.withSpaceAfter("addDepartmentForFinance") + currentFinanceModel.toString());
        String emptyString = "";
        departmentBox.setSelectedIndex(0);
        subdepartmentBox.setSelectedIndex(0);
        depMaterialsAmountField.setText(emptyString);
        depEquipmentAmountField.setText(emptyString);
        depServicesAmountField.setText(emptyString);
        depMaterialAmount = null;
        depEquipmentAmount = null;
        depServicesAmount = null;
        selectedSubdepartment = emptySubdepartmentModel;
        currentFinanceModel = null;
        currentFinanceDepartmentModel = new FinanceDepartmentModel();
        this.setVisible(false);
    }

    private boolean checkInput() {
        DepartmentModel selectedDepartment = (DepartmentModel) departmentBox.getSelectedItem();
        if (selectedDepartment.equals(emptyDepartmentModel)) {
            Utils.emptyFieldError(parent, Labels.getProperty("department"));
            departmentBox.requestFocusInWindow();
            return false;
        }

        selectedSubdepartment = (SubdepartmentModel) subdepartmentBox.getSelectedItem();
        if (selectedSubdepartment.equals(emptySubdepartmentModel)) {
            Utils.emptyFieldError(parent, Labels.getProperty("subdepartment"));
            subdepartmentBox.requestFocusInWindow();
            return false;
        }

        depMaterialAmount = Utils.parseSubdepartmentBigDecimal(isCreateMode, parent, currentFinanceModel, depMaterialsAmountField, Labels.getProperty("materialsAmount"), BidType.MATERIALS);
        if (depMaterialAmount == null) {
            return false;
        }
        depEquipmentAmount = Utils.parseSubdepartmentBigDecimal(isCreateMode, parent, currentFinanceModel, depEquipmentAmountField, Labels.getProperty("equipmentAmount"), BidType.EQUIPMENT);
        if (depEquipmentAmount == null) {
            return false;
        }

        depServicesAmount = Utils.parseSubdepartmentBigDecimal(isCreateMode, parent, currentFinanceModel, depServicesAmountField, Labels.getProperty("servicesAmount"), BidType.SERVICES);
        return depServicesAmount != null;
    }

    public void setListener(FinanceDepartmentDialogListener listener) {
        this.listener = listener;
    }


    public void setDepartmentBoxData(java.util.List<DepartmentModel> db) {
        Utils.setBoxData(departmentBox, db, emptyDepartmentModel, false);
    }

    public void setSubdepartmentBoxData(java.util.List<SubdepartmentModel> db) {
        Utils.setBoxData(subdepartmentBox, db, emptySubdepartmentModel, true);
    }

    public void setVisible(FinanceModel model, boolean isCreate) {
        this.isCreateMode = isCreate;
        if (listener != null) {
            listener.loadDepartments();
        }
        this.currentFinanceModel = model;
        this.setLeftAmounts();
        if (isCreateMode) {
            super.setTitle(Labels.withSpaceAfter("addDepartmentForFinance") + currentFinanceModel.toString());
            okButton.setText(Labels.getProperty("create"));
        }
        super.setVisible(true);
    }

    public void editDepartmentModel(FinanceDepartmentModel selectedDepFinModel) {
        isCreateMode = false;
        this.currentFinanceDepartmentModel = selectedDepFinModel;
        Utils.setBoxFromModel(departmentBox, selectedDepFinModel.getSubdepartment().getDepartment());
        Utils.setBoxFromModel(subdepartmentBox, selectedDepFinModel.getSubdepartment());
        depMaterialsAmountField.setText(selectedDepFinModel.getTotalMaterialsAmount().toString());
        depEquipmentAmountField.setText(selectedDepFinModel.getTotalEqupmentAmount().toString());
        depServicesAmountField.setText(selectedDepFinModel.getTotalServicesAmount().toString());
        super.setTitle(Labels.withSpaceAfter("editDepartmentForFinance") + selectedDepFinModel.getFinances().toString());
        okButton.setText(Labels.getProperty("edit"));
        this.setVisible(this.currentFinanceDepartmentModel.getFinances(), false);
    }

    private void setLeftAmounts() {
        materialsLeft.setText(this.getUnassignedAmount(BidType.MATERIALS));
        equpmetLeft.setText(this.getUnassignedAmount(BidType.EQUIPMENT));
        servicesLeft.setText(this.getUnassignedAmount(BidType.SERVICES));
    }

    private String getUnassignedAmount(BidType type) {
        BigDecimal unassignedAmount = currentFinanceModel.getUnassignedAmount(type);
        if (currentFinanceDepartmentModel.getTotalAmount(type) != null) {
            unassignedAmount = unassignedAmount.add(currentFinanceDepartmentModel.getTotalAmount(type));
        }
        return unassignedAmount.setScale(2, RoundingMode.CEILING).toString();
    }

    private void layoutControls() {
        JPanel departmentPanel = new JPanel();
        departmentPanel.setLayout(new GridBagLayout());

        Insets largePadding = new Insets(1, 0, 1, 15);
        GridBagConstraints gc = new GridBagConstraints();

        ////// First row//////
        gc.gridy = 0;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        departmentPanel.add(new JLabel(Labels.withColon("department")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        departmentPanel.add(departmentBox, gc);

        ////// Next row//////
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        departmentPanel.add(new JLabel(Labels.withColon("subdepartment")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        departmentPanel.add(subdepartmentBox, gc);

        JPanel financesPanel = new JPanel();
        financesPanel.setLayout(new GridBagLayout());

        gc = new GridBagConstraints();

        ////// First row//////
        gc.gridy = 0;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        financesPanel.add(new JLabel(""), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        financesPanel.add(new JLabel(Labels.getProperty("financeAvailable")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        financesPanel.add(new JLabel(Labels.getProperty("financeAssigned")), gc);

        ////// Next row//////
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        financesPanel.add(new JLabel(Labels.withColon("materialsAmount")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        financesPanel.add(materialsLeft, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        financesPanel.add(depMaterialsAmountField, gc);

        ////// Next row//////
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        financesPanel.add(new JLabel(Labels.withColon("equipmentAmount")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        financesPanel.add(equpmetLeft, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        financesPanel.add(depEquipmentAmountField, gc);

        ////// Next row//////
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        financesPanel.add(new JLabel(Labels.withColon("servicesAmount")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        financesPanel.add(servicesLeft, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        financesPanel.add(depServicesAmountField, gc);

        JPanel buttonsPanel = new JPanel();

        Utils.setPreferredButtonSizes(okButton, cancelButton);

        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        JPanel departmentFinancesPanel = new JPanel();
        departmentFinancesPanel.setLayout(new BorderLayout());
        departmentFinancesPanel.setBorder(BorderFactory.createEtchedBorder());
        departmentFinancesPanel.add(departmentPanel, BorderLayout.NORTH);
        departmentFinancesPanel.add(financesPanel, BorderLayout.CENTER);

        this.setLayout(new BorderLayout());
        this.add(departmentFinancesPanel, BorderLayout.CENTER);
        this.add(buttonsPanel, BorderLayout.SOUTH);
    }
}
