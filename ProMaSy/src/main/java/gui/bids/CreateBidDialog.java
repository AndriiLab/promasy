package gui.bids;

import controller.Logger;
import gui.MainFrame;
import gui.Utils;
import gui.commons.Icons;
import gui.commons.Labels;
import gui.components.PJComboBox;
import gui.components.PJOptionPane;
import model.enums.BidType;
import model.enums.Status;
import model.models.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.util.List;

/**
 * Dialog for creation of new bids. Associated with {@link BidsListPanel}
 */
public class CreateBidDialog extends JDialog {

    private PJComboBox<DepartmentModel> departmentBox;
    private PJComboBox<SubdepartmentModel> subdepartmentBox;
    private PJComboBox<BidType> bidTypeBox;
    private PJComboBox<FinanceDepartmentModel> financeDepartmentBox;
    private PJComboBox<ProducerModel> producerBox;
    private PJComboBox<SupplierModel> supplierBox;
    private PJComboBox<AmountUnitsModel> amUnitsBox;
    private PJComboBox<ReasonForSupplierChoiceModel> reasonForSupplierChoiceBox;
    private JButton addProducerButton;
    private JButton addSupplierButton;
    private JButton searchCPVButton;
    private JButton addAmUnitsButton;
    private JButton addReasonForSupplierChoiceButton;
    private JButton okButton;
    private JButton cancelButton;
    private JTextField catNumberField;
    private JTextField cpvField;
    private JTextField amountField;
    private JTextField oneUnitPriceField;
    private JTextField kekvField;
    private JButton kekvEditButton;
    private JTextPane descriptionPane;
    private JScrollPane descriptionScrollPane;
    private BidModel createdBidModel;
    private CPVModel selectedCPV;
    private JLabel totalPriceLabel;
    private CreateBidDialogListener listener;
    private MainFrame parent;
    private BidType currentBidType;
    private BigDecimal totalPrice;
    private BigDecimal previousSum;

    public CreateBidDialog(MainFrame parent) {
        super(parent, Labels.getProperty("createBid"), true);
        this.parent = parent;
        setSize(516, 500);
        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        totalPrice = BigDecimal.ZERO;

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
        currentBidType = BidType.MATERIALS;

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

        okButton = new JButton(Labels.getProperty("createBid"));

        cancelButton = new JButton(Labels.getProperty("cancel"));

        catNumberField = new JTextField();
        catNumberField.setPreferredSize(preferredFieldDim);
        catNumberField.setEnabled(false);
        cpvField = new JTextField();
        cpvField.setPreferredSize(preferredFieldDim);
        cpvField.setEnabled(false);
        amountField = new JTextField();
        amountField.setPreferredSize(preferredFieldDim);
        oneUnitPriceField = new JTextField();
        oneUnitPriceField.setPreferredSize(preferredFieldDim);

        kekvField = new JTextField();
        kekvField.setPreferredSize(preferredFieldDim);
        kekvField.setEnabled(false);
        kekvEditButton = new JButton(Icons.EDIT);
        kekvEditButton.setPreferredSize(buttonDim);
        kekvEditButton.setToolTipText(Labels.withSpaceAfter("edit") + Labels.getProperty("kekv"));

        descriptionPane = new JTextPane();
        descriptionPane.setPreferredSize(new Dimension(168, 25));
        descriptionPane.setEditable(true);
        descriptionScrollPane = new JScrollPane(descriptionPane);
        descriptionScrollPane.setPreferredSize(preferredFieldDim);

        createLayout();

        departmentBox.addActionListener(e -> {
            DepartmentModel selectedModel = (DepartmentModel) departmentBox.getSelectedItem();
            subdepartmentBox.removeAllItems();
            subdepartmentBox.addItem(EmptyModel.SUBDEPARTMENT);
            if (selectedModel != null) {
                selectedModel.getSubdepartments().forEach(subdepartmentBox::addItem);
            }
        });

        subdepartmentBox.addActionListener(e -> {
            SubdepartmentModel subdepartmentModel = (SubdepartmentModel) subdepartmentBox.getSelectedItem();
            financeDepartmentBox.removeAllItems();
            financeDepartmentBox.addItem(EmptyModel.FINANCE_DEPARTMENT);
            if (subdepartmentModel != null) {
                subdepartmentModel.getFinanceDepartments().forEach(financeDepartmentBox::addItem);
            }
        });

        producerBox.addActionListener(e -> {
            if (producerBox.getItemCount() > 0) {
                ProducerModel model = (ProducerModel) producerBox.getSelectedItem();
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
                SupplierModel model = (SupplierModel) supplierBox.getSelectedItem();
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

        addProducerButton.addActionListener(e -> parent.showProducerDialog());
        addReasonForSupplierChoiceButton.addActionListener(e -> parent.showReasonsDialog());
        addSupplierButton.addActionListener(e -> parent.showSupplierDialog());
        addAmUnitsButton.addActionListener(e -> parent.showAmUnitsDialog());
        searchCPVButton.addActionListener(e -> {
            if (cpvField.getText().isEmpty()) {
                parent.showCpvDialog();
            } else {
                parent.showCpvDialog(cpvField.getText());
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


        okButton.addActionListener(e -> {
                    if (checkFields() && listener != null) {
                        if (createdBidModel.getModelId() != 0L) {
                            createdBidModel.setUpdated();
                        } else createdBidModel.setCreated();
                        FinanceDepartmentModel finDepModel = (FinanceDepartmentModel) financeDepartmentBox.getSelectedItem();
                        finDepModel.addBid(createdBidModel);
                        listener.persistModelEventOccurred(createdBidModel);
                        clearFieldsAndSetTitle();
                    }
                }
        );

        cancelButton.addActionListener(e -> clearFieldsAndSetTitle());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                clearFieldsAndSetTitle();
            }
        });

    }

    void setCurrentDepartment(DepartmentModel currentDepartment) {
        departmentBox.setSelectedModel(currentDepartment);
    }

    void setCurrentSubdepartment(SubdepartmentModel subdepartmentModel) {
        subdepartmentBox.setSelectedModel(subdepartmentModel);
    }

    void setCurrentFinanceDepartment(FinanceDepartmentModel currentFinanceDepartment) {
        financeDepartmentBox.setSelectedModel(currentFinanceDepartment);
    }

    void setCurrentBidType(BidType type) {
        this.currentBidType = type;
        bidTypeBox.setSelectedItem(currentBidType);
    }

    void setEnabledDepartmentBox(boolean state) {
        departmentBox.setEnabled(state);
    }

    void setEnabledSubdepartmentBox(boolean state) {
        subdepartmentBox.setEnabled(state);
    }

    private void clearFieldsAndSetTitle() {
        setVisible(false);
        departmentBox.setSelectedIndex(0);
        financeDepartmentBox.setSelectedIndex(0);
        producerBox.setSelectedIndex(0);
        cpvField.setText(EmptyModel.STRING);
        catNumberField.setText(EmptyModel.STRING);
        descriptionPane.setText(EmptyModel.STRING);
        supplierBox.setSelectedIndex(0);
        amUnitsBox.setSelectedIndex(0);
        amountField.setText(EmptyModel.STRING);
        oneUnitPriceField.setText(EmptyModel.STRING);
        kekvField.setText(EmptyModel.STRING);
        calculateTotalPrice();
        setTitle(Labels.getProperty("createBid"));
        okButton.setText(Labels.getProperty("createBid"));
    }

    private void calculateTotalPrice() {
        if (!oneUnitPriceField.getText().isEmpty() && !amountField.getText().isEmpty()) {
            try {
                String onePriceString = oneUnitPriceField.getText();
                if (onePriceString.contains(",")) {
                    onePriceString = onePriceString.replace(",", ".");
                    oneUnitPriceField.setText(onePriceString);
                }
                BigDecimal onePrice = new BigDecimal(onePriceString);
                BigDecimal amount = new BigDecimal(amountField.getText());
                totalPrice = onePrice.multiply(amount);
                totalPriceLabel.setText(totalPrice + Labels.withSpaceBefore("uah"));
            } catch (NumberFormatException ex) {
                Logger.warnEvent(ex);
                totalPrice = BigDecimal.ZERO;
                totalPriceLabel.setText(Labels.getProperty("wrongFormat"));
            }
        } else {
            totalPrice = BigDecimal.ZERO;
            totalPriceLabel.setText(totalPrice + Labels.withSpaceBefore("uah"));
        }
        descriptionPane.setPreferredSize(new Dimension(168, 25));
    }

    public void setFinanceDepartmentBoxData(List<FinanceDepartmentModel> db) {
        financeDepartmentBox.setBoxData(db, EmptyModel.FINANCE_DEPARTMENT, false);
    }

    void addToDepartmentBox(DepartmentModel model) {
        departmentBox.addItem(model);
    }

    public void setProducerBoxData(List<ProducerModel> db) {
        producerBox.setBoxData(db, EmptyModel.PRODUCER, true);
    }

    public void setSupplierBoxData(List<SupplierModel> db) {
        supplierBox.setBoxData(db, EmptyModel.SUPPLIER, true);
    }

    public void setReasonForSupplierChoiceBoxData(List<ReasonForSupplierChoiceModel> db) {
        reasonForSupplierChoiceBox.setBoxData(db, EmptyModel.REASON_FOR_SUPPLIER_CHOICE, true);
    }

    public void setAmUnitsBoxData(List<AmountUnitsModel> db) {
        amUnitsBox.setBoxData(db, EmptyModel.AMOUNT_UNITS, true);
    }

    private boolean checkFields() {
        DepartmentModel selectedDepartmentModel = (DepartmentModel) departmentBox.getSelectedItem();
        if (selectedDepartmentModel.equals(EmptyModel.DEPARTMENT)) {
            PJOptionPane.emptyField(parent, Labels.getProperty("department"));
            departmentBox.requestFocusInWindow();
            return false;
        }

        SubdepartmentModel selectedSubdepartmentModel = (SubdepartmentModel) subdepartmentBox.getSelectedItem();
        if (selectedSubdepartmentModel.equals(EmptyModel.SUBDEPARTMENT)) {
            PJOptionPane.emptyField(parent, Labels.getProperty("subdepartment"));
            subdepartmentBox.requestFocusInWindow();
            return false;
        }
        FinanceDepartmentModel selectedFinanceDepartmentModel = (FinanceDepartmentModel) financeDepartmentBox.getSelectedItem();
        if (selectedFinanceDepartmentModel.equals(EmptyModel.FINANCE_DEPARTMENT)) {
            PJOptionPane.emptyField(parent, Labels.getProperty("finance"));
            financeDepartmentBox.requestFocusInWindow();
            return false;
        }
        ProducerModel selectedProducerModel = (ProducerModel) producerBox.getSelectedItem();
        if (selectedProducerModel.equals(EmptyModel.PRODUCER)) {
            selectedProducerModel = null;
        }
        String selectedCatNum = catNumberField.getText().isEmpty() ? null : catNumberField.getText();
        if (selectedCPV.equals(EmptyModel.CPV)) {
            PJOptionPane.emptyField(parent, Labels.getProperty("cpv"));
            searchCPVButton.requestFocusInWindow();
            return false;
        } else if (selectedCPV.getCpvId().length() != 10) {
            PJOptionPane.wrongFormat(parent, Labels.getProperty("cpv"), Labels.getProperty("wrongFormatCPV"));
            searchCPVButton.requestFocusInWindow();
            return false;
        }
        String selectedDescription = descriptionPane.getText();
        if (selectedDescription.isEmpty()) {
            PJOptionPane.emptyField(parent, Labels.getProperty("description"));
            descriptionPane.requestFocusInWindow();
            return false;
        }
        SupplierModel selectedSupplierModel = (SupplierModel) supplierBox.getSelectedItem();
        if (selectedSupplierModel.equals(EmptyModel.SUPPLIER)) {
            selectedSupplierModel = null;
        }
        ReasonForSupplierChoiceModel selectedReasonModel = (ReasonForSupplierChoiceModel) reasonForSupplierChoiceBox.getSelectedItem();
        if (selectedSupplierModel != null && !selectedSupplierModel.equals(EmptyModel.SUPPLIER) && selectedReasonModel.equals(EmptyModel.REASON_FOR_SUPPLIER_CHOICE)) {
            PJOptionPane.emptyField(parent, Labels.getProperty("reasonForSupplierChoice"));
            supplierBox.requestFocusInWindow();
            return false;
        } else if (selectedSupplierModel == null) {
            selectedReasonModel = null;
        }

        AmountUnitsModel selectedAmountUnitsModel = (AmountUnitsModel) amUnitsBox.getSelectedItem();
        if (selectedAmountUnitsModel.equals(EmptyModel.AMOUNT_UNITS)) {
            PJOptionPane.emptyField(parent, Labels.getProperty("packing"));
            amUnitsBox.requestFocusInWindow();
            return false;
        }
        String amountString = amountField.getText();
        if (amountString.isEmpty()) {
            PJOptionPane.emptyField(parent, Labels.getProperty("amount"));
            amountField.requestFocusInWindow();
            return false;
        }

        int amount;
        try {
            amount = Integer.parseInt(amountString);
        } catch (NumberFormatException ex) {
            Logger.warnEvent(ex);
            PJOptionPane.wrongFormat(parent, Labels.getProperty("amount"), Labels.getProperty("integersOnly"));
            amountField.requestFocusInWindow();
            return false;
        }

        String onePriceString = oneUnitPriceField.getText();
        if (onePriceString.equals(EmptyModel.STRING)) {
            PJOptionPane.emptyField(parent, Labels.getProperty("oneUnitPrice"));
            oneUnitPriceField.requestFocusInWindow();
            return false;
        }

        BigDecimal onePrice;
        try {
            onePrice = new BigDecimal(onePriceString);
        } catch (NumberFormatException ex) {
            Logger.warnEvent(ex);
            PJOptionPane.wrongFormat(parent, Labels.getProperty("oneUnitPrice"), Labels.getProperty("wrongFloatFormat"));
            oneUnitPriceField.requestFocusInWindow();
            return false;
        }

        currentBidType = (BidType) bidTypeBox.getSelectedItem();

        Integer kekv = Utils.parseInteger(parent, kekvField, Labels.getProperty("kekv"));
        if (kekv == null) {
            return false;
        }

        BigDecimal financeLeft;
        if (previousSum != null) { //if editing bid
            financeLeft = selectedFinanceDepartmentModel.getLeftAmount(currentBidType).add(previousSum);
        } else {
            financeLeft = selectedFinanceDepartmentModel.getLeftAmount(currentBidType);
        }
        if (financeLeft.compareTo(totalPrice) < 0) {
            PJOptionPane.insufficientFunds(parent, financeLeft, currentBidType);
            amountField.requestFocusInWindow();
            return false;
        }

        if (createdBidModel.equals(EmptyModel.BID)) {
            List<BidStatusModel> statuses = createdBidModel.getStatuses();
            BidStatusModel statusModel = new BidStatusModel(Status.CREATED, createdBidModel);
            statusModel.setCreated();
            statuses.add(statusModel);
        }

        createdBidModel.setProducer(selectedProducerModel);
        createdBidModel.setCatNum(selectedCatNum);
        createdBidModel.setBidDesc(selectedDescription);
        createdBidModel.setCpv(selectedCPV);
        createdBidModel.setOnePrice(onePrice);
        createdBidModel.setAmount(amount);
        createdBidModel.setAmountUnit(selectedAmountUnitsModel);
        createdBidModel.setFinances(selectedFinanceDepartmentModel);
        createdBidModel.setSupplier(selectedSupplierModel);
        createdBidModel.setReasonForSupplierChoice(selectedReasonModel);
        createdBidModel.setType(currentBidType);
        createdBidModel.setKEKV(kekv);

        return true;
    }

    public PJComboBox<AmountUnitsModel> getAmUnitsBox() {
        return amUnitsBox;
    }

    public PJComboBox<ProducerModel> getProducerBox() {
        return producerBox;
    }

    public PJComboBox<SupplierModel> getSupplierBox() {
        return supplierBox;
    }

    public PJComboBox<ReasonForSupplierChoiceModel> getReasonForSupplierChoiceBox() {
        return reasonForSupplierChoiceBox;
    }

    public void setCreateBidDialogListener(CreateBidDialogListener listener) {
        this.listener = listener;
    }

    void loadToDialog(BidModel model, boolean isEditMode) {
        if (listener != null) {
            listener.getAllData();
        }
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
        if (isEditMode) {
            previousSum = model.getTotalPrice();
            createdBidModel = model;
            setTitle(Labels.getProperty("editBid"));
            okButton.setText(Labels.getProperty("editBid"));
        } else {
            previousSum = null;
            createdBidModel = new BidModel();
        }
        super.setVisible(true);
    }

    public void setSelectedCPV(CPVModel selectedCPV) {
        this.selectedCPV = selectedCPV;
        cpvField.setText(selectedCPV.getCpvId());
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible && listener != null) {
            listener.getAllData();
        }
        kekvField.setText(String.valueOf(((BidType) bidTypeBox.getSelectedItem()).getKEKV()));
        createdBidModel = new BidModel();
        previousSum = null;
        super.setVisible(visible);
    }

    private void createLayout() {

        int space = 5;
        Border emptyBorder = BorderFactory.createEmptyBorder(space, space, space, space);
        Border etchedBorder = BorderFactory.createEtchedBorder();
        Border compoundBorder = BorderFactory.createCompoundBorder(emptyBorder, etchedBorder);

        Insets smallPadding = new Insets(1, 0, 1, 5);

        // department and orders panel
        JPanel depOrdersPanel = new JPanel();
        depOrdersPanel.setBorder(compoundBorder);
        depOrdersPanel.setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        ////First row/////
        gc.gridy = 0;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        depOrdersPanel.add(new JLabel(Labels.withColon("department")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        depOrdersPanel.add(departmentBox, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        depOrdersPanel.add(new JLabel(Labels.withColon("finance")), gc);


        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        depOrdersPanel.add(financeDepartmentBox, gc);

        /// Next row///
        gc.gridy++;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        depOrdersPanel.add(new JLabel(Labels.withColon("subdepartment")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        depOrdersPanel.add(subdepartmentBox, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        depOrdersPanel.add(new JLabel(Labels.withColon("bidType")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        depOrdersPanel.add(bidTypeBox, gc);

        // createOrUpdate new bid panel
        JPanel createBidPanel = new JPanel();
        createBidPanel.setBorder(compoundBorder);
        createBidPanel.setLayout(new GridBagLayout());

        ////next row/////
        gc.gridy++;
        gc.fill = GridBagConstraints.NONE;

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
        gc.fill = GridBagConstraints.NONE;

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
        gc.fill = GridBagConstraints.NONE;

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
        gc.fill = GridBagConstraints.NONE;

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
        gc.fill = GridBagConstraints.NONE;

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
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.gridwidth = 1;
        gc.ipady = 0;
        gc.insets = smallPadding;
        createBidPanel.add(new JLabel(Labels.withColon("reasonForSupplierChoice")), gc);

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
        gc.fill = GridBagConstraints.NONE;

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
        gc.fill = GridBagConstraints.NONE;

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
        gc.fill = GridBagConstraints.NONE;

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
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        createBidPanel.add(new JLabel(Labels.withColon("oneUnitPrice")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        createBidPanel.add(oneUnitPriceField, gc);

        /// Next row///
        gc.gridy++;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = new Insets(10, 1, 1, 5);
        createBidPanel.add(new JLabel(Labels.withColon("totalPrice")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = new Insets(10, 1, 1, 5);
        createBidPanel.add(totalPriceLabel, gc);

        //buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5));
        Utils.setPreferredButtonSizes(okButton, cancelButton);
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        setLayout(new BorderLayout());
        add(depOrdersPanel, BorderLayout.NORTH);
        add(createBidPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }
}