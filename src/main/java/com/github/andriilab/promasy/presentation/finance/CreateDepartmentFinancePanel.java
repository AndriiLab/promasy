package com.github.andriilab.promasy.presentation.finance;

import com.github.andriilab.promasy.data.queries.finance.GetFinanceUnassignedAmountQuery;
import com.github.andriilab.promasy.domain.EmptyModel;
import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.finance.entities.Finance;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;
import com.github.andriilab.promasy.domain.organization.entities.Department;
import com.github.andriilab.promasy.domain.organization.entities.Subdepartment;
import com.github.andriilab.promasy.presentation.Utils;
import com.github.andriilab.promasy.presentation.commons.Icons;
import com.github.andriilab.promasy.presentation.commons.Labels;
import com.github.andriilab.promasy.presentation.components.CEDButtons;
import com.github.andriilab.promasy.presentation.components.ErrorOptionPane;
import com.github.andriilab.promasy.presentation.components.PJComboBox;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Dialog for creation and updating of {@link FinanceDepartment}
 */
public class CreateDepartmentFinancePanel extends JPanel {

    private final PJComboBox<Department> departmentBox;
    private Subdepartment selectedSubdepartment;
    private Finance currentFinanceModel;

    private BigDecimal depMaterialAmount;
    private BigDecimal depEquipmentAmount;
    private BigDecimal depServicesAmount;
    private CreateDepartmentFinancePanelListener listener;

    private final JFrame parent;
    private FinanceDepartment currentFinanceDepartmentModel;
    private PJComboBox<Subdepartment> subdepartmentBox;
    private final JTextField depMaterialsAmountField;
    private final JTextField depEquipmentAmountField;
    private final JTextField depServicesAmountField;
    private final JLabel materialsLeft;
    private final JLabel equpmetLeft;
    private final JLabel servicesLeft;
    private final JButton okButton;
    private final JButton cancelButton;
    private final JButton closeButton;
    private boolean isCreateMode;

    public CreateDepartmentFinancePanel(JFrame parent) {

        this.parent = parent;

        currentFinanceDepartmentModel = new FinanceDepartment();
        isCreateMode = true;

        materialsLeft = new JLabel();
        equpmetLeft = new JLabel();
        servicesLeft = new JLabel();

        departmentBox = new PJComboBox<>();
        departmentBox.setPreferredSize(PJComboBox.COMBOBOX_DIMENSION);
        departmentBox.addItem(EmptyModel.DEPARTMENT);
        departmentBox.addActionListener(e -> {
            Department selectedDepartmentModel = (Department) departmentBox.getSelectedItem();
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
        currentFinanceDepartmentModel = new FinanceDepartment();
    }

    private boolean checkInput() {
        Department selectedDepartment = (Department) departmentBox.getSelectedItem();
        if (selectedDepartment.equals(EmptyModel.DEPARTMENT)) {
            ErrorOptionPane.emptyField(parent, Labels.getProperty("department"));
            departmentBox.requestFocusInWindow();
            return false;
        }

        selectedSubdepartment = (Subdepartment) subdepartmentBox.getSelectedItem();
        if (selectedSubdepartment.equals(EmptyModel.SUBDEPARTMENT)) {
            ErrorOptionPane.emptyField(parent, Labels.getProperty("subdepartment"));
            subdepartmentBox.requestFocusInWindow();
            return false;
        }

        BigDecimal unassignedAmount = listener.getUnassignedAmountEvent(new GetFinanceUnassignedAmountQuery(currentFinanceModel, BidType.MATERIALS));
        depMaterialAmount = Utils.parseSubdepartmentBigDecimal(isCreateMode, parent, currentFinanceModel, unassignedAmount, depMaterialsAmountField, Labels.getProperty("materialsAmount"), BidType.MATERIALS);
        if (depMaterialAmount == null) {
            return false;
        }

        unassignedAmount = listener.getUnassignedAmountEvent(new GetFinanceUnassignedAmountQuery(currentFinanceModel, BidType.EQUIPMENT));
        depEquipmentAmount = Utils.parseSubdepartmentBigDecimal(isCreateMode, parent, currentFinanceModel, unassignedAmount, depEquipmentAmountField, Labels.getProperty("equipmentAmount"), BidType.EQUIPMENT);
        if (depEquipmentAmount == null) {
            return false;
        }

        unassignedAmount = listener.getUnassignedAmountEvent(new GetFinanceUnassignedAmountQuery(currentFinanceModel, BidType.SERVICES));
        depServicesAmount = Utils.parseSubdepartmentBigDecimal(isCreateMode, parent, currentFinanceModel, unassignedAmount, depServicesAmountField, Labels.getProperty("servicesAmount"), BidType.SERVICES);
        return depServicesAmount != null;
    }

    public void setListener(CreateDepartmentFinancePanelListener listener) {
        this.listener = listener;
    }


    public void setDepartmentBoxData(java.util.List<Department> db) {
        departmentBox.setBoxData(db, EmptyModel.DEPARTMENT, true);
    }

    public void setSubdepartmentBoxData(java.util.List<Subdepartment> db) {
        subdepartmentBox.setBoxData(db, EmptyModel.SUBDEPARTMENT, true);
    }

    public void setVisible(Finance model, boolean isCreate) {
        this.isCreateMode = isCreate;
        this.currentFinanceModel = model;
        this.setLeftAmounts();
        if (isCreateMode) {
            okButton.setText(Labels.getProperty("create"));
        }
        super.setVisible(true);
    }

    public void editDepartmentModel(FinanceDepartment selectedDepFinModel) {
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
        BigDecimal unassignedAmount = listener.getUnassignedAmountEvent(new GetFinanceUnassignedAmountQuery(currentFinanceModel, type));
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
