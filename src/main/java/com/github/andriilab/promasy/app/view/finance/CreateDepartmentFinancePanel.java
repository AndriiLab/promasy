package com.github.andriilab.promasy.app.view.finance;

import com.github.andriilab.promasy.data.commands.CreateCommand;
import com.github.andriilab.promasy.data.commands.ICommand;
import com.github.andriilab.promasy.data.commands.UpdateCommand;
import com.github.andriilab.promasy.data.queries.finance.GetFinanceUnassignedAmountQuery;
import com.github.andriilab.promasy.domain.EmptyModel;
import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.finance.entities.Finance;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;
import com.github.andriilab.promasy.domain.organization.entities.Department;
import com.github.andriilab.promasy.domain.organization.entities.Subdepartment;
import com.github.andriilab.promasy.app.commons.Icons;
import com.github.andriilab.promasy.app.commons.Labels;
import com.github.andriilab.promasy.app.commons.Parsers;
import com.github.andriilab.promasy.app.commons.Validator;
import com.github.andriilab.promasy.app.components.EntityComboBox;
import com.github.andriilab.promasy.app.components.PJComboBox;
import com.github.andriilab.promasy.app.components.dialogs.CEDButtons;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Dialog for creation and updating of {@link FinanceDepartment}
 */
public class CreateDepartmentFinancePanel extends JPanel {

    private final EntityComboBox<Department> departmentBox;
    private Subdepartment selectedSubdepartment;
    private Finance currentFinanceModel;

    private BigDecimal depMaterialAmount;
    private BigDecimal depEquipmentAmount;
    private BigDecimal depServicesAmount;
    private CreateDepartmentFinancePanelListener listener;

    private final JFrame parent;
    private FinanceDepartment currentFinanceDepartmentModel;
    private EntityComboBox<Subdepartment> subdepartmentBox;
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

        listener = new EmptyCreateDepartmentFinancePanelListener();
        currentFinanceDepartmentModel = new FinanceDepartment();
        isCreateMode = true;

        materialsLeft = new JLabel();
        equpmetLeft = new JLabel();
        servicesLeft = new JLabel();

        departmentBox = new EntityComboBox<>();
        departmentBox.setPreferredSize(PJComboBox.COMBOBOX_DIMENSION);
        departmentBox.addItem(EmptyModel.DEPARTMENT);
        departmentBox.addActionListener(e -> {
            Department selectedDepartmentModel = (Department) departmentBox.getSelectedItem();
            if (selectedDepartmentModel != null) {
                setSubdepartmentBoxData(selectedDepartmentModel.getSubdepartments());
            }
            subdepartmentBox.setEnabled(true);
        });

        subdepartmentBox = new EntityComboBox<>();
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
            if (checkInput()) {
                currentFinanceDepartmentModel.setFinances(currentFinanceModel);
                currentFinanceDepartmentModel.setSubdepartment(selectedSubdepartment);
                currentFinanceDepartmentModel.setTotalMaterialsAmount(depMaterialAmount);
                currentFinanceDepartmentModel.setTotalEquipmentAmount(depEquipmentAmount);
                currentFinanceDepartmentModel.setTotalServicesAmount(depServicesAmount);
                ICommand<FinanceDepartment> command = currentFinanceDepartmentModel.getModelId() == 0 ? new CreateCommand<>(currentFinanceDepartmentModel) : new UpdateCommand<>(currentFinanceDepartmentModel);
                currentFinanceModel.addFinanceDepartmentModel(currentFinanceDepartmentModel);
                listener.persistModelEventOccurred(command);
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
        String emptyString = "";
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
        if (Validator.isEmptyComboBox(parent, departmentBox, Labels.getProperty("department"))) {
            return false;
        }

        selectedSubdepartment = (Subdepartment) subdepartmentBox.getSelectedItem();
        if (Validator.isEmptyComboBox(parent, subdepartmentBox, Labels.getProperty("subdepartment"))) {
            return false;
        }

        BigDecimal unassignedAmount = listener.getUnassignedAmountEvent(new GetFinanceUnassignedAmountQuery(currentFinanceModel, BidType.MATERIALS));
        if (!isCreateMode) {
            unassignedAmount = unassignedAmount.add(currentFinanceDepartmentModel.getTotalAmount(BidType.MATERIALS));
        }
        depMaterialAmount = Parsers.parseSubdepartmentBigDecimal(parent, unassignedAmount, depMaterialsAmountField, Labels.getProperty("materialsAmount"));
        if (depMaterialAmount == null) {
            return false;
        }

        unassignedAmount = listener.getUnassignedAmountEvent(new GetFinanceUnassignedAmountQuery(currentFinanceModel, BidType.EQUIPMENT));
        if (!isCreateMode) {
            unassignedAmount = unassignedAmount.add(currentFinanceDepartmentModel.getTotalAmount(BidType.EQUIPMENT));
        }
        depEquipmentAmount = Parsers.parseSubdepartmentBigDecimal(parent, unassignedAmount, depEquipmentAmountField, Labels.getProperty("equipmentAmount"));
        if (depEquipmentAmount == null) {
            return false;
        }

        unassignedAmount = listener.getUnassignedAmountEvent(new GetFinanceUnassignedAmountQuery(currentFinanceModel, BidType.SERVICES));
        if (!isCreateMode) {
            unassignedAmount = unassignedAmount.add(currentFinanceDepartmentModel.getTotalAmount(BidType.SERVICES));
        }
        depServicesAmount = Parsers.parseSubdepartmentBigDecimal(parent, unassignedAmount, depServicesAmountField, Labels.getProperty("servicesAmount"));
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
