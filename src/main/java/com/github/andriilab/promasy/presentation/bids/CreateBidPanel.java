package com.github.andriilab.promasy.presentation.bids;

import com.github.andriilab.promasy.data.controller.Logger;
import com.github.andriilab.promasy.data.queries.financepartment.GetFinanceDepartmentLeftAmountQuery;
import com.github.andriilab.promasy.domain.EmptyModel;
import com.github.andriilab.promasy.domain.bid.entities.AmountUnit;
import com.github.andriilab.promasy.domain.bid.entities.Bid;
import com.github.andriilab.promasy.domain.bid.entities.BidStatus;
import com.github.andriilab.promasy.domain.bid.entities.ReasonForSupplierChoice;
import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.bid.enums.Status;
import com.github.andriilab.promasy.domain.cpv.entities.Cpv;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;
import com.github.andriilab.promasy.domain.item.entities.Producer;
import com.github.andriilab.promasy.domain.item.entities.Supplier;
import com.github.andriilab.promasy.domain.organization.entities.Department;
import com.github.andriilab.promasy.domain.organization.entities.Subdepartment;
import com.github.andriilab.promasy.presentation.MainFrame;
import com.github.andriilab.promasy.presentation.commons.Colors;
import com.github.andriilab.promasy.presentation.commons.Icons;
import com.github.andriilab.promasy.presentation.commons.Labels;
import com.github.andriilab.promasy.presentation.commons.Utils;
import com.github.andriilab.promasy.presentation.components.PJComboBox;
import com.github.andriilab.promasy.presentation.components.dialogs.CEDButtons;
import com.github.andriilab.promasy.presentation.components.panes.ErrorOptionPane;
import com.github.andriilab.promasy.presentation.validator.Validator;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.zinternaltools.HighlightInformation;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

/**
 * Panel for data about new bids. Associated with {@link BidsListPanel}
 */
public class CreateBidPanel extends JPanel {

    private final PJComboBox<Department> departmentBox;
    private final PJComboBox<Subdepartment> subdepartmentBox;
    private final PJComboBox<BidType> bidTypeBox;
    private final PJComboBox<FinanceDepartment> financeDepartmentBox;
    private final PJComboBox<Producer> producerBox;
    private final PJComboBox<Supplier> supplierBox;
    private final PJComboBox<AmountUnit> amUnitsBox;
    private final PJComboBox<ReasonForSupplierChoice> reasonForSupplierChoiceBox;
    private final JButton addProducerButton;
    private final JButton addSupplierButton;
    private final JButton searchCPVButton;
    private final JButton addAmUnitsButton;
    private final JButton addReasonForSupplierChoiceButton;
    private final JButton okButton;
    private final JButton cancelButton;
    private final JButton closeButton;
    private final JButton kekvEditButton;
    private final JButton procDateAddButton;
    private final JTextField catNumberField;
    private final JTextField cpvField;
    private final JTextField amountField;
    private final JTextField oneUnitPriceField;
    private final JTextField kekvField;
    private final JCheckBox taxCheckBox;
    private JLabel oneUnitPriceLabel;
    private JLabel totalPriceDescLabel;
    private boolean isWithTax;
    private final JTextPane descriptionPane;
    private final JScrollPane descriptionScrollPane;
    private final DatePicker procurementStartDatePicker;
    private Bid createdBidModel;
    private Cpv selectedCPV;
    private final JLabel totalPriceLabel;
    private CreateBidPanelListener listener;
    private final MainFrame parent;
    private boolean isEditMode;

    CreateBidPanel(MainFrame parent) {
        this.parent = parent;

        listener = new EmptyCreateBidPanelListener();
        totalPriceLabel = new JLabel("0" + Labels.withSpaceBefore("uah"));
        totalPriceLabel.setForeground(Color.RED);

        Dimension preferredFieldDim = new Dimension(235, 20);

        departmentBox = new PJComboBox<>();
        departmentBox.setPreferredSize(preferredFieldDim);
        departmentBox.addItem(EmptyModel.DEPARTMENT);

        subdepartmentBox = new PJComboBox<>();
        subdepartmentBox.setPreferredSize(preferredFieldDim);
        subdepartmentBox.addItem(EmptyModel.SUBDEPARTMENT);

        financeDepartmentBox = new PJComboBox<>();
        financeDepartmentBox.setPreferredSize(preferredFieldDim);
        financeDepartmentBox.addItem(EmptyModel.FINANCE_DEPARTMENT);

        bidTypeBox = new PJComboBox<>(BidType.values());
        bidTypeBox.setPreferredSize(preferredFieldDim);
        bidTypeBox.setSelectedIndex(0);

        preferredFieldDim.setSize(170, 25);

        producerBox = new PJComboBox<>();
        producerBox.setPreferredSize(preferredFieldDim);
        producerBox.addItem(EmptyModel.PRODUCER);

        supplierBox = new PJComboBox<>();
        supplierBox.setPreferredSize(preferredFieldDim);
        supplierBox.addItem(EmptyModel.SUPPLIER);

        reasonForSupplierChoiceBox = new PJComboBox<>();
        reasonForSupplierChoiceBox.setPreferredSize(preferredFieldDim);
        reasonForSupplierChoiceBox.addItem(EmptyModel.REASON_FOR_SUPPLIER_CHOICE);

        amUnitsBox = new PJComboBox<>();
        amUnitsBox.setPreferredSize(preferredFieldDim);

        Dimension buttonDim = new Dimension(25, 25);

        addProducerButton = new JButton(Icons.CREATE);
        addProducerButton.setToolTipText(Labels.getProperty("addProd"));
        addProducerButton.setPreferredSize(buttonDim);
        addProducerButton.setEnabled(true);

        addSupplierButton = new JButton(Icons.CREATE);
        addSupplierButton.setToolTipText(Labels.getProperty("addSupl"));
        addSupplierButton.setPreferredSize(buttonDim);
        addSupplierButton.setEnabled(true);

        addReasonForSupplierChoiceButton = new JButton(Icons.CREATE);
        addReasonForSupplierChoiceButton.setToolTipText(Labels.getProperty("addReasonForSupplierChoice"));
        addReasonForSupplierChoiceButton.setPreferredSize(buttonDim);
        addReasonForSupplierChoiceButton.setEnabled(false);

        addAmUnitsButton = new JButton(Icons.CREATE);
        addAmUnitsButton.setToolTipText(Labels.getProperty("addAmUnit"));
        addAmUnitsButton.setPreferredSize(buttonDim);
        addAmUnitsButton.setEnabled(true);

        selectedCPV = EmptyModel.CPV;

        searchCPVButton = new JButton(Icons.SEARCH);
        searchCPVButton.setToolTipText(Labels.getProperty("cpvPanelTab"));
        searchCPVButton.setPreferredSize(buttonDim);
        searchCPVButton.setEnabled(true);

        okButton = new JButton(Labels.getProperty("create"));
        okButton.setIcon(Icons.OK);
        cancelButton = new JButton(Labels.getProperty("cancel"));
        cancelButton.setIcon(Icons.CANCEL);

        closeButton = CEDButtons.getCloseButton();

        catNumberField = new JTextField();
        catNumberField.setPreferredSize(preferredFieldDim);
        catNumberField.setEnabled(false);
        cpvField = new JTextField();
        cpvField.setPreferredSize(preferredFieldDim);
        amountField = new JTextField();
        amountField.setPreferredSize(preferredFieldDim);
        oneUnitPriceField = new JTextField();
        oneUnitPriceField.setPreferredSize(preferredFieldDim);

        oneUnitPriceLabel = new JLabel(Labels.withColon("oneUnitPrice"));
        totalPriceDescLabel = new JLabel(Labels.withColon("totalPrice"));

        isWithTax = false;
        taxCheckBox = new JCheckBox(Labels.getProperty("tax"));
        taxCheckBox.setSelected(isWithTax);

        kekvField = new JTextField();
        kekvField.setPreferredSize(preferredFieldDim);
        kekvField.setEnabled(false);
        kekvEditButton = new JButton(Icons.EDIT);
        kekvEditButton.setPreferredSize(buttonDim);
        kekvEditButton.setToolTipText(Labels.withSpaceAfter("edit") + Labels.quoted("kekv"));

        descriptionPane = new JTextPane();
        descriptionPane.setPreferredSize(new Dimension(168, 25));
        descriptionPane.setEditable(true);
        descriptionScrollPane = new JScrollPane(descriptionPane);
        descriptionScrollPane.setPreferredSize(preferredFieldDim);

        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setFormatForDatesCommonEra("dd.MM.yyyy");
        procurementStartDatePicker = new DatePicker(dateSettings);
        procurementStartDatePicker.setLocale(Locale.getDefault());
        procurementStartDatePicker.setEnabled(false);
        dateSettings.setHighlightPolicy(date -> (date.compareTo(LocalDate.now().plusDays(15)) > -1) ? new HighlightInformation(Colors.GREEN_LIGHT_SELECTED) : null);
        dateSettings.setVetoPolicy(date -> date.compareTo(LocalDate.now().plusDays(15)) > -1);
        procDateAddButton = new JButton(Icons.EDIT);
        procDateAddButton.setPreferredSize(buttonDim);
        procDateAddButton.setToolTipText(Labels.withSpaceAfter("edit") + Labels.quoted("procurementStartDate"));

        createLayout();

        departmentBox.addActionListener(e -> {
            Department selectedModel = (Department) departmentBox.getSelectedItem();
            subdepartmentBox.removeAllItems();
            subdepartmentBox.addItem(EmptyModel.SUBDEPARTMENT);
            if (selectedModel != null) {
                selectedModel.getSubdepartments().forEach(subdepartmentBox::addItem);
            }
        });

        subdepartmentBox.addActionListener(e -> {
            Subdepartment subdepartmentModel = (Subdepartment) subdepartmentBox.getSelectedItem();
            financeDepartmentBox.removeAllItems();
            financeDepartmentBox.addItem(EmptyModel.FINANCE_DEPARTMENT);
            if (subdepartmentModel != null) {
                subdepartmentModel.getFinanceDepartments().forEach(financeDepartmentBox::addItem);
            }
        });

        producerBox.addActionListener(e -> {
            if (producerBox.getItemCount() > 0) {
                Producer model = (Producer) producerBox.getSelectedItem();
                if (!model.equals(EmptyModel.PRODUCER)) {
                    catNumberField.setEnabled(true);
                } else {
                    catNumberField.setEnabled(false);
                    catNumberField.setText(EmptyModel.STRING);
                }
            }
        });

        supplierBox.addActionListener(e -> {
            if (supplierBox.getItemCount() > 0) {
                Supplier model = (Supplier) supplierBox.getSelectedItem();
                if (!model.equals(EmptyModel.SUPPLIER)) {
                    addReasonForSupplierChoiceButton.setEnabled(true);
                    reasonForSupplierChoiceBox.setEnabled(true);
                } else {
                    addReasonForSupplierChoiceButton.setEnabled(false);
                    reasonForSupplierChoiceBox.setEnabled(false);
                    reasonForSupplierChoiceBox.setSelectedIndex(0);
                }
            }
        });

        bidTypeBox.addActionListener(e -> {
            kekvField.setText(String.valueOf(((BidType) bidTypeBox.getSelectedItem()).getKEKV()));
            kekvField.setEnabled(false);
        });

        kekvEditButton.addActionListener(e -> kekvField.setEnabled(!kekvField.isEnabled()));
        procDateAddButton.addActionListener(e -> procurementStartDatePicker.setEnabled(!procurementStartDatePicker.isEnabled()));

        addProducerButton.addActionListener(e -> parent.showProducerDialog());
        addReasonForSupplierChoiceButton.addActionListener(e -> parent.showReasonsDialog());
        addSupplierButton.addActionListener(e -> parent.showSupplierDialog());
        addAmUnitsButton.addActionListener(e -> parent.showAmUnitsDialog());
        searchCPVButton.addActionListener(e -> {
            String cpvText = cpvField.getText();
            if (cpvText.isEmpty()) {
                parent.showCpvDialog();
            } else {
                parent.showCpvDialog(cpvText);
            }
        });

        oneUnitPriceField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                calculateTotalPrice();
            }
        });
        amountField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                calculateTotalPrice();
            }
        });

        taxCheckBox.addActionListener(e -> {
            isWithTax = taxCheckBox.isSelected();
            calculateTotalPrice();
            setTaxLabels();
        });

        okButton.addActionListener(e -> {
            if (checkFields()) {
                        if (createdBidModel.getModelId() != 0L) {
                            createdBidModel.setUpdated();
                        } else createdBidModel.setCreated();
                        FinanceDepartment finDepModel = (FinanceDepartment) financeDepartmentBox.getSelectedItem();
                        finDepModel.addBid(createdBidModel);
                        listener.persistModelEventOccurred(createdBidModel);
                        clear();
                        setVisible(false);
                    }
                }
        );

        cancelButton.addActionListener(e -> {
            clear();
            setVisible(false);
        });

        closeButton.addActionListener(e -> {
            clear();
            setVisible(false);
        });
    }

    void setTaxLabels() {
        oneUnitPriceLabel.setText(isWithTax ? Labels.withColon("oneUnitPrice") : Labels.withSpaceAfter("oneUnitPrice") + Labels.withColon("withoutTax"));
        totalPriceDescLabel.setText(isWithTax ? Labels.withColon("totalPrice") : Labels.withSpaceAfter("totalPrice") + Labels.withColon("withTax"));
    }

    void setCurrentDepartment(Department currentDepartment) {
        departmentBox.setSelectedModel(currentDepartment);
    }

    void setCurrentSubdepartment(Subdepartment subdepartmentModel) {
        subdepartmentBox.setSelectedModel(subdepartmentModel);
    }

    void setCurrentFinanceDepartment(FinanceDepartment currentFinanceDepartment) {
        financeDepartmentBox.setSelectedModel(currentFinanceDepartment);
    }

    void setCurrentBidType(BidType type) {
        bidTypeBox.setSelectedItem(type);
    }

    void setEnabledDepartmentBox(boolean state) {
        departmentBox.setEnabled(state);
    }

    void setEnabledSubdepartmentBox(boolean state) {
        subdepartmentBox.setEnabled(state);
    }

    void clear() {
        try {
            departmentBox.setSelectedIndex(0);
            financeDepartmentBox.setSelectedIndex(0);
            producerBox.setSelectedIndex(0);
            supplierBox.setSelectedIndex(0);
            amUnitsBox.setSelectedIndex(0);
        } catch (IllegalArgumentException ex) {
            listener.getAllData();
        }
        cpvField.setText(EmptyModel.STRING);
        catNumberField.setText(EmptyModel.STRING);
        descriptionPane.setText(EmptyModel.STRING);
        amountField.setText(EmptyModel.STRING);
        oneUnitPriceField.setText(EmptyModel.STRING);
        kekvField.setText(EmptyModel.STRING);
        kekvField.setEnabled(false);
        calculateTotalPrice();
        okButton.setText(Labels.getProperty("create"));
        isEditMode = false;
        procurementStartDatePicker.setEnabled(false);
        procurementStartDatePicker.clear();
    }

    private BigDecimal calculateTotalPrice() {
        BigDecimal totalPrice = null;
        String onePriceString = oneUnitPriceField.getText();
        String amountString = amountField.getText();
        if (!onePriceString.isEmpty() && !amountString.isEmpty()) {
            try {
                onePriceString = Utils.formatFinanceString(onePriceString);
                BigDecimal onePrice = new BigDecimal(onePriceString);
                BigDecimal amount = new BigDecimal(amountString);

                if (!isWithTax) {
                    onePrice = onePrice.multiply(BigDecimal.valueOf(1.2));
                }
                totalPrice = onePrice.multiply(amount);

                totalPriceLabel.setText(totalPrice + Labels.withSpaceBefore("uah"));
                oneUnitPriceField.setText(onePriceString);
            } catch (NumberFormatException ex) {
                Logger.warnEvent(this.getClass(), ex);
                totalPriceLabel.setText(Labels.getProperty("wrongFormat"));
            }
        } else {
            totalPriceLabel.setText(BigDecimal.ZERO + Labels.withSpaceBefore("uah"));
        }
        descriptionPane.setPreferredSize(new Dimension(168, 25));
        return totalPrice;
    }

    public void setFinanceDepartmentBoxData(List<FinanceDepartment> db) {
        financeDepartmentBox.setBoxData(db, EmptyModel.FINANCE_DEPARTMENT, false);
    }

    void addToDepartmentBox(Department model) {
        departmentBox.addItem(model);
    }

    public void setProducerBoxData(List<Producer> db) {
        producerBox.setBoxData(db, EmptyModel.PRODUCER, true);
    }

    public void setSupplierBoxData(List<Supplier> db) {
        supplierBox.setBoxData(db, EmptyModel.SUPPLIER, true);
    }

    public void setReasonForSupplierChoiceBoxData(List<ReasonForSupplierChoice> db) {
        reasonForSupplierChoiceBox.setBoxData(db, EmptyModel.REASON_FOR_SUPPLIER_CHOICE, true);
    }

    public void setAmUnitsBoxData(List<AmountUnit> db) {
        amUnitsBox.setBoxData(db, EmptyModel.AMOUNT_UNITS, true);
    }

    private boolean checkFields() {
        if (Validator.isEmptyComboBox(parent, departmentBox, Labels.getProperty("department"))) {
            return false;
        }
        if (Validator.isEmptyComboBox(parent, subdepartmentBox, Labels.getProperty("subdepartment"))) {
            return false;
        }
        if (Validator.isEmptyComboBox(parent, financeDepartmentBox, Labels.getProperty("finance"))) {
            return false;
        }

        Department selectedDepartmentModel = (Department) departmentBox.getSelectedItem();
        Subdepartment selectedSubdepartmentModel = (Subdepartment) subdepartmentBox.getSelectedItem();
        FinanceDepartment selectedFinanceDepartmentModel = (FinanceDepartment) financeDepartmentBox.getSelectedItem();

        boolean financeChanged = !selectedFinanceDepartmentModel.equals(createdBidModel.getFinances());

        Producer selectedProducerModel = (Producer) producerBox.getSelectedItem();
        if (selectedProducerModel.equals(EmptyModel.PRODUCER)) {
            selectedProducerModel = null;
        }
        String selectedCatNum = catNumberField.getText().isEmpty() ? null : catNumberField.getText();
        String cpvCode = cpvField.getText();
        if (cpvCode.isEmpty()) {
            ErrorOptionPane.emptyField(parent, Labels.getProperty("cpv"));
            searchCPVButton.requestFocusInWindow();
            return false;
        }
        // matches codes like '02340000-0' and '20340000-0', do not match '02300000-0'
        if (selectedCPV.equals(EmptyModel.CPV) && cpvCode.matches("^\\d{2}[1-9]{2}\\d{4}-\\d$")) {
            selectedCPV = parent.validateCpv(cpvCode);
        }

        if (selectedCPV.equals(EmptyModel.CPV)) {
            ErrorOptionPane.wrongFormat(parent, Labels.getProperty("cpv"), Labels.getProperty("wrongFormatCPV"));
            searchCPVButton.requestFocusInWindow();
            return false;
        }

        String selectedDescription = descriptionPane.getText();
        if (selectedDescription.isEmpty()) {
            ErrorOptionPane.emptyField(parent, Labels.getProperty("description"));
            descriptionPane.requestFocusInWindow();
            return false;
        }
        Supplier selectedSupplierModel = (Supplier) supplierBox.getSelectedItem();
        if (selectedSupplierModel.equals(EmptyModel.SUPPLIER)) {
            selectedSupplierModel = null;
        }
        ReasonForSupplierChoice selectedReasonModel = (ReasonForSupplierChoice) reasonForSupplierChoiceBox.getSelectedItem();
        if (selectedSupplierModel != null && !selectedSupplierModel.equals(EmptyModel.SUPPLIER) && selectedReasonModel.equals(EmptyModel.REASON_FOR_SUPPLIER_CHOICE)) {
            ErrorOptionPane.emptyField(parent, Labels.getProperty("reasonForSupplierChoice"));
            supplierBox.requestFocusInWindow();
            return false;
        } else if (selectedSupplierModel == null) {
            selectedReasonModel = null;
        }

        AmountUnit selectedAmountUnitsModel = (AmountUnit) amUnitsBox.getSelectedItem();
        if (Validator.isEmptyComboBox(parent, amUnitsBox, Labels.getProperty("packing"))) {
            return false;
        }

        String amountString = amountField.getText();
        if (amountString.isEmpty()) {
            ErrorOptionPane.emptyField(parent, Labels.getProperty("amount"));
            amountField.requestFocusInWindow();
            return false;
        }

        int amount;
        try {
            amount = Integer.parseInt(amountString);
        } catch (NumberFormatException ex) {
            Logger.warnEvent(this.getClass(), ex);
            ErrorOptionPane.wrongFormat(parent, Labels.getProperty("amount"), Labels.getProperty("integersOnly"));
            amountField.requestFocusInWindow();
            return false;
        }

        String onePriceString = oneUnitPriceField.getText();
        if (onePriceString.equals(EmptyModel.STRING)) {
            ErrorOptionPane.emptyField(parent, Labels.getProperty("oneUnitPrice"));
            oneUnitPriceField.requestFocusInWindow();
            return false;
        }

        BigDecimal onePrice;
        try {
            onePrice = new BigDecimal(onePriceString);
        } catch (NumberFormatException ex) {
            Logger.warnEvent(this.getClass(), ex);
            ErrorOptionPane.wrongFormat(parent, Labels.getProperty("oneUnitPrice"), Labels.getProperty("wrongIntegerFormat"));
            oneUnitPriceField.requestFocusInWindow();
            return false;
        }

        BidType currentBidType = (BidType) bidTypeBox.getSelectedItem();

        Integer kekv = Utils.parseInteger(parent, kekvField, Labels.getProperty("kekv"));
        if (kekv == null) {
            return false;
        }

        BigDecimal financeLeft;
        if (isEditMode && !financeChanged) {
            BigDecimal previousSum = createdBidModel.getTotalPrice();
            financeLeft = listener.getLeftAmount(new GetFinanceDepartmentLeftAmountQuery(selectedFinanceDepartmentModel, currentBidType)).add(previousSum);
        } else {
            financeLeft = listener.getLeftAmount(new GetFinanceDepartmentLeftAmountQuery(selectedFinanceDepartmentModel, currentBidType));
        }
        BigDecimal totalPrice = calculateTotalPrice();
        if (totalPrice == null) {
            ErrorOptionPane.emptyField(parent, Labels.getProperty("totalPrice2"));
            return false;
        }

        if (financeLeft.compareTo(totalPrice) < 0) {
            ErrorOptionPane.insufficientFunds(parent, financeLeft, currentBidType);
            amountField.requestFocusInWindow();
            return false;
        }

        if (createdBidModel.equals(EmptyModel.BID)) {
            List<BidStatus> statuses = createdBidModel.getStatuses();
            BidStatus statusModel = new BidStatus(Status.CREATED, createdBidModel);
            statusModel.setCreated();
            statuses.add(statusModel);
        }

        LocalDate procStartDate = procurementStartDatePicker.getDate();
        if (procStartDate != null) {
            createdBidModel.setProcurementStartDate(Date.valueOf(procStartDate));
        }

        createdBidModel.setProducer(selectedProducerModel);
        createdBidModel.setCatNum(selectedCatNum);
        createdBidModel.setBidDesc(selectedDescription);
        createdBidModel.setCpv(selectedCPV);
        createdBidModel.setOnePrice(onePrice);
        createdBidModel.setAmount(amount);
        createdBidModel.setAmountUnit(selectedAmountUnitsModel);
        selectedFinanceDepartmentModel.addBid(createdBidModel);
        createdBidModel.setSupplier(selectedSupplierModel);
        createdBidModel.setReasonForSupplierChoice(selectedReasonModel);
        createdBidModel.setType(currentBidType);
        createdBidModel.setKEKV(kekv);

        return true;
    }

    public PJComboBox<AmountUnit> getAmUnitsBox() {
        return amUnitsBox;
    }

    public PJComboBox<Producer> getProducerBox() {
        return producerBox;
    }

    public PJComboBox<Supplier> getSupplierBox() {
        return supplierBox;
    }

    public PJComboBox<ReasonForSupplierChoice> getReasonForSupplierChoiceBox() {
        return reasonForSupplierChoiceBox;
    }

    void setCreateBidDialogListener(CreateBidPanelListener listener) {
        this.listener = listener;
    }

    void loadToDialog(Bid model, boolean isEditMode) {
        this.isEditMode = isEditMode;
        listener.getAllData();
        setCurrentBidType(model.getType());
        departmentBox.setSelectedModel(model.getFinances().getSubdepartment().getDepartment());
        subdepartmentBox.setSelectedModel(model.getFinances().getSubdepartment());
        financeDepartmentBox.setSelectedModel(model.getFinances());
        amUnitsBox.setSelectedModel(model.getAmountUnit());
        if (model.getProducer() != null) {
            producerBox.setSelectedModel(model.getProducer());
        }
        if (model.getSupplier() != null) {
            supplierBox.setSelectedModel(model.getSupplier());
        }
        if (model.getReasonForSupplierChoice() != null) {
            reasonForSupplierChoiceBox.setSelectedModel(model.getReasonForSupplierChoice());
        }
        selectedCPV = model.getCpv();
        cpvField.setText(model.getCpv().getCpvId());
        catNumberField.setText(model.getCatNum());
        descriptionPane.setText(model.getBidDesc());
        kekvField.setText(String.valueOf(model.getKEKV()));
        amountField.setText(Integer.toString(model.getAmount()));
        oneUnitPriceField.setText(model.getOnePrice().toString());
        calculateTotalPrice();
        Date procStartDate = model.getProcurementStartDate();
        procurementStartDatePicker.setDate((procStartDate != null) ? procStartDate.toLocalDate() : null);
        if (isEditMode) {
            createdBidModel = model;
            okButton.setText(Labels.getProperty("edit"));
        } else {
            createdBidModel = new Bid();
        }
        super.setVisible(true);
    }

    public void setSelectedCPV(Cpv selectedCPV) {
        this.selectedCPV = selectedCPV;
        cpvField.setText(selectedCPV.getCpvId());
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            listener.getAllData();
            setTaxLabels();
        }
        kekvField.setText(String.valueOf(((BidType) bidTypeBox.getSelectedItem()).getKEKV()));
        createdBidModel = new Bid();
        super.setVisible(visible);
    }

    private void createLayout() {
        Insets smallPadding = new Insets(1, 0, 1, 5);
        Insets mediumPadding = new Insets(10, 1, 1, 5);

        JPanel createBidPanel = new JPanel();
        createBidPanel.setBorder(new EtchedBorder());
        createBidPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        ////First row/////
        gc.gridy = 0;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        createBidPanel.add(new JLabel(Labels.withColon("department")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        createBidPanel.add(departmentBox, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.NORTHEAST;
        gc.insets = new Insets(0, 0, 0, 0);
        createBidPanel.add(closeButton, gc);

        /// Next row///
        gc.gridy++;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        createBidPanel.add(new JLabel(Labels.withColon("subdepartment")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        createBidPanel.add(subdepartmentBox, gc);

        /// Next row///
        gc.gridy++;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        createBidPanel.add(new JLabel(Labels.withColon("finance")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        createBidPanel.add(financeDepartmentBox, gc);

        /// Next row///
        gc.gridy++;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        createBidPanel.add(new JLabel(Labels.withColon("bidType")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        createBidPanel.add(bidTypeBox, gc);

        ////next row/////
        gc.gridy++;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        createBidPanel.add(new JLabel(Labels.withColon("producer")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        createBidPanel.add(producerBox, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        createBidPanel.add(addProducerButton, gc);

        /// Next row///
        gc.gridy++;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        createBidPanel.add(new JLabel(Labels.withColon("catNumber")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        createBidPanel.add(catNumberField, gc);

        /// Next row///
        gc.gridy++;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        createBidPanel.add(new JLabel(Labels.withColon("cpv")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        createBidPanel.add(cpvField, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        createBidPanel.add(searchCPVButton, gc);

        /// Next row///
        gc.gridy++;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = smallPadding;
        createBidPanel.add(new JLabel(Labels.withColon("description")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.gridwidth = 3;
        gc.ipady = 50;
        gc.insets = smallPadding;
        createBidPanel.add(descriptionScrollPane, gc);

        /// Next row///
        gc.gridy++;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.gridwidth = 1;
        gc.ipady = 0;
        gc.insets = smallPadding;
        createBidPanel.add(new JLabel(Labels.withColon("supplier")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        createBidPanel.add(supplierBox, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        createBidPanel.add(addSupplierButton, gc);

        /// Next row///
        gc.gridy++;

        JLabel reasonLabel = new JLabel(Labels.withColon("reasonForChoice"));
        reasonLabel.setToolTipText(Labels.getProperty("reasonForSupplierChoice"));

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.gridwidth = 1;
        gc.ipady = 0;
        gc.insets = smallPadding;
        createBidPanel.add(reasonLabel, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        createBidPanel.add(reasonForSupplierChoiceBox, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        createBidPanel.add(addReasonForSupplierChoiceButton, gc);

        /// Next row///
        gc.gridy++;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        createBidPanel.add(new JLabel(Labels.withColon("packing")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        createBidPanel.add(amUnitsBox, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        createBidPanel.add(addAmUnitsButton, gc);

        /// Next row///
        gc.gridy++;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        createBidPanel.add(new JLabel(Labels.withColon("amount")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        createBidPanel.add(amountField, gc);

        /// Next row///
        gc.gridy++;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        createBidPanel.add(oneUnitPriceLabel, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        createBidPanel.add(oneUnitPriceField, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        createBidPanel.add(taxCheckBox, gc);

        /// Next row///
        gc.gridy++;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        createBidPanel.add(totalPriceDescLabel, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        createBidPanel.add(totalPriceLabel, gc);

        /// Next row///
        gc.gridy++;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.gridwidth = 1;
        gc.ipady = 0;
        gc.insets = smallPadding;
        createBidPanel.add(new JLabel(Labels.withColon("kekv")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        createBidPanel.add(kekvField, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        createBidPanel.add(kekvEditButton, gc);

        /// Next row///
        gc.gridy++;

        JLabel procurementStartDateLabel = new JLabel(Labels.withColon("procurementDate"));
        procurementStartDateLabel.setToolTipText(Labels.getProperty("procurementStartDate"));

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = mediumPadding;
        createBidPanel.add(procurementStartDateLabel, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = mediumPadding;
        createBidPanel.add(procurementStartDatePicker, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = mediumPadding;
        createBidPanel.add(procDateAddButton, gc);

        //buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        Dimension btnDim = new Dimension(120, 25);
        okButton.setPreferredSize(btnDim);
        cancelButton.setPreferredSize(btnDim);
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        setLayout(new BorderLayout());
        add(createBidPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }
}