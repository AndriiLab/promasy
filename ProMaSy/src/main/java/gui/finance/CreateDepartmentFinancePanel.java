package gui.finance;

import gui.Utils;
import gui.commons.Icons;
import gui.commons.Labels;
import gui.components.CEDButtons;
import gui.components.PJComboBox;
import gui.components.PJOptionPane;
import model.enums.BidType;
import model.models.*;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Dialog for creation and updating of {@link FinanceDepartmentModel}
 */
public class CreateDepartmentFinancePanel extends JPanel {

    private SubdepartmentModel selectedSubdepartment;
    private FinanceModel currentFinanceModel;
    private FinanceDepartmentModel currentFinanceDepartmentModel;

    private BigDecimal depMaterialAmount;
    private BigDecimal depEquipmentAmount;
    private BigDecimal depServicesAmount;
    private CreateDepartmentFinancePanelListener listener;

    private JFrame parent;
    private PJComboBox<DepartmentModel> departmentBox;
    private PJComboBox<SubdepartmentModel> subdepartmentBox;
    private JTextField depMaterialsAmountField;
    private JTextField depEquipmentAmountField;
    private JTextField depServicesAmountField;
    private JLabel materialsLeft;
    private JLabel equpmetLeft;
    private JLabel servicesLeft;
    private JButton okButton;
    private JButton cancelButton;
    private JButton closeButton;
    private boolean isCreateMode;

    public CreateDepartmentFinancePanel(JFrame parent) {

        this.parent = parent;

        currentFinanceDepartmentModel = new FinanceDepartmentModel();
        isCreateMode = true;

        materialsLeft = new JLabel();
        equpmetLeft = new JLabel();
        servicesLeft = new JLabel();

        departmentBox = new PJComboBox<>();
        departmentBox.setPreferredSize(PJComboBox.COMBOBOX_DIMENSION);
        departmentBox.addItem(EmptyModel.DEPARTMENT);
        departmentBox.addActionListener(e -> {
            DepartmentModel selectedDepartmentModel = (DepartmentModel) departmentBox.getSelectedItem();
            if (selectedDepartmentModel != null) {
                setSubdepartmentBoxData(selectedDepartmentModel.getSubdepartments());
            }
            subdepartmentBox.setEnabled(true);
        });

        subdepartmentBox = new PJComboBox<>();
        subdepartmentBox.setPreferredSize(PJComboBox.COMBOBOX_DIMENSION);
        subdepartmentBox.setEnabled(false);
        subdepartmentBox.addItem(EmptyModel.SUBDEPARTMENT);

        depMaterialsAmountField = new JTextField(10);
        depEquipmentAmountField = new JTextField(10);
        depServicesAmountField = new JTextField(10);


        okButton = new JButton(Labels.getProperty("create"));
        okButton.setIcon(Icons.OK);
        cancelButton = new JButton(Labels.getProperty("cancel"));
        cancelButton.setIcon(Icons.CANCEL);
        closeButton = CEDButtons.getCloseButton();

        layoutControls();

        okButton.addActionListener(e -> {
            if (checkInput() && listener != null) {
                currentFinanceDepartmentModel.setFinances(currentFinanceModel);
                currentFinanceDepartmentModel.setSubdepartment(selectedSubdepartment);
                currentFinanceDepartmentModel.setTotalMaterialsAmount(depMaterialAmount);
                currentFinanceDepartmentModel.setTotalEquipmentAmount(depEquipmentAmount);
                currentFinanceDepartmentModel.setTotalServicesAmount(depServicesAmount);
                if (currentFinanceDepartmentModel.getModelId() == 0) {
                    currentFinanceDepartmentModel.setCreated();
                } else {
                    currentFinanceDepartmentModel.setUpdated();
                }
                currentFinanceModel.addFinanceDepartmentModel(currentFinanceDepartmentModel);
                listener.persistModelEventOccurred(currentFinanceDepartmentModel);
                clear();
                setVisible(false);
            }
        });

        cancelButton.addActionListener(e -> {
            clear();
            setVisible(false);
        });

        closeButton.addActionListener(e -> {
            clear();
            setVisible(false);
        });
    }

    void clear() {
        String emptyString = EmptyModel.STRING;
        departmentBox.setSelectedIndex(0);
        subdepartmentBox.setSelectedIndex(0);
        depMaterialsAmountField.setText(emptyString);
        depEquipmentAmountField.setText(emptyString);
        depServicesAmountField.setText(emptyString);
        depMaterialAmount = null;
        depEquipmentAmount = null;
        depServicesAmount = null;
        selectedSubdepartment = EmptyModel.SUBDEPARTMENT;
        currentFinanceModel = null;
        currentFinanceDepartmentModel = new FinanceDepartmentModel();
    }

    private boolean checkInput() {
        DepartmentModel selectedDepartment = (DepartmentModel) departmentBox.getSelectedItem();
        if (selectedDepartment.equals(EmptyModel.DEPARTMENT)) {
            PJOptionPane.emptyField(parent, Labels.getProperty("department"));
            departmentBox.requestFocusInWindow();
            return false;
        }

        selectedSubdepartment = (SubdepartmentModel) subdepartmentBox.getSelectedItem();
        if (selectedSubdepartment.equals(EmptyModel.SUBDEPARTMENT)) {
            PJOptionPane.emptyField(parent, Labels.getProperty("subdepartment"));
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

    public void setListener(CreateDepartmentFinancePanelListener listener) {
        this.listener = listener;
    }


    public void setDepartmentBoxData(java.util.List<DepartmentModel> db) {
        departmentBox.setBoxData(db, EmptyModel.DEPARTMENT, true);
    }

    public void setSubdepartmentBoxData(java.util.List<SubdepartmentModel> db) {
        subdepartmentBox.setBoxData(db, EmptyModel.SUBDEPARTMENT, true);
    }

    public void setVisible(FinanceModel model, boolean isCreate) {
        this.isCreateMode = isCreate;
        this.currentFinanceModel = model;
        this.setLeftAmounts();
        if (isCreateMode) {
            okButton.setText(Labels.getProperty("create"));
        }
        super.setVisible(true);
    }

    public void editDepartmentModel(FinanceDepartmentModel selectedDepFinModel) {
        this.currentFinanceDepartmentModel = selectedDepFinModel;
        departmentBox.setSelectedModel(selectedDepFinModel.getSubdepartment().getDepartment());
        subdepartmentBox.setSelectedModel(selectedDepFinModel.getSubdepartment());
        depMaterialsAmountField.setText(selectedDepFinModel.getTotalMaterialsAmount().toString());
        depEquipmentAmountField.setText(selectedDepFinModel.getTotalEquipmentAmount().toString());
        depServicesAmountField.setText(selectedDepFinModel.getTotalServicesAmount().toString());
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

        gc.gridx++;
        gc.anchor = GridBagConstraints.NORTHEAST;
        gc.insets = new Insets(0, 0, 0, 0);
        departmentPanel.add(closeButton, gc);

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
        financesPanel.add(new JLabel(EmptyModel.STRING), gc);

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

        Dimension btnDim = new Dimension(120, 25);
        okButton.setPreferredSize(btnDim);
        cancelButton.setPreferredSize(btnDim);

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
