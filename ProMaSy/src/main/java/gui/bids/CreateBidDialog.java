package gui.bids;

import gui.Icons;
import gui.Labels;
import gui.MainFrame;
import gui.Utils;
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

    private final DepartmentModel emptyDepartmentModel = new DepartmentModel();
    private final FinanceDepartmentModel emptyFinanceDepartmentModel = new FinanceDepartmentModel();
    private final ProducerModel emptyProducerModel = new ProducerModel();
    private final SupplierModel emptySupplierModel = new SupplierModel();
    private final AmountUnitsModel emptyAmountUnitsModel = new AmountUnitsModel();
    private final BidModel emptyBidModel = null;//TODO
    private final ReasonForSupplierChoiceModel emptyReasonForSupplierChoiceModel = new ReasonForSupplierChoiceModel();
    private final CPVModel emptyCpvModel = new CPVModel();
    private final SubdepartmentModel emptySubdepartmentModel = new SubdepartmentModel();
    private JComboBox<DepartmentModel> departmentBox;
    private JComboBox<SubdepartmentModel> subdepartmentBox;
    private JComboBox<BidType> bidTypeBox;
    private JComboBox<FinanceDepartmentModel> financeDepartmentBox;
    private JComboBox<ProducerModel> producerBox;
    private JComboBox<SupplierModel> supplierBox;
    private JComboBox<AmountUnitsModel> amUnitsBox;
    private JComboBox<ReasonForSupplierChoiceModel> reasonForSupplierChoiceBox;
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
    private JTextPane descriptionPane;
    private BidModel createdBidModel;
    private CPVModel selectedCPV;
    private JLabel totalPriceLabel;
    private CreateBidDialogListener listener;
    private MainFrame parent;
    private DepartmentModel currentDepartment;
    private FinanceDepartmentModel currentFinanceDepartment;
    private BidType currentBidType;
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private boolean inBidEditMode = false;

    public CreateBidDialog(MainFrame parent) {
        super(parent, Labels.getProperty("createBid"), true);
        this.parent = parent;
        setSize(440, 495);
        setResizable(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        createdBidModel = emptyBidModel;

        totalPriceLabel = new JLabel("0" + Labels.withSpaceBefore("uah"));
        totalPriceLabel.setForeground(Color.RED);

        Dimension preferredFieldDim = new Dimension(235, 25);

        departmentBox = new JComboBox<>();
        departmentBox.setPreferredSize(preferredFieldDim);
        departmentBox.addItem(emptyDepartmentModel);

        subdepartmentBox = new JComboBox<>();
        subdepartmentBox.setPreferredSize(preferredFieldDim);
        subdepartmentBox.addItem(emptySubdepartmentModel);

        financeDepartmentBox = new JComboBox<>();
        financeDepartmentBox.setPreferredSize(preferredFieldDim);
        financeDepartmentBox.addItem(emptyFinanceDepartmentModel);

        bidTypeBox = new JComboBox<>(BidType.values());
        bidTypeBox.setPreferredSize(preferredFieldDim);
        currentBidType = BidType.MATERIALS;

        preferredFieldDim = new Dimension(170, 25);

        producerBox = new JComboBox<>();
        producerBox.setPreferredSize(preferredFieldDim);
        producerBox.addItem(emptyProducerModel);

        supplierBox = new JComboBox<>();
        supplierBox.setPreferredSize(preferredFieldDim);
        supplierBox.addItem(emptySupplierModel);

        reasonForSupplierChoiceBox = new JComboBox<>();
        reasonForSupplierChoiceBox.setPreferredSize(preferredFieldDim);
        reasonForSupplierChoiceBox.addItem(emptyReasonForSupplierChoiceModel);

        amUnitsBox = new JComboBox<>();
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
        amountField = new JTextField();
        amountField.setPreferredSize(preferredFieldDim);
        oneUnitPriceField = new JTextField();
        oneUnitPriceField.setPreferredSize(preferredFieldDim);
        descriptionPane = new JTextPane();
        descriptionPane.setPreferredSize(new Dimension(168, 25));
        descriptionPane.setEditable(true);

        createLayout();

        departmentBox.addActionListener(e -> {
            financeDepartmentBox.removeAllItems();
            financeDepartmentBox.addItem(emptyFinanceDepartmentModel);
            DepartmentModel selectedModel = (DepartmentModel) departmentBox.getSelectedItem();
            for (SubdepartmentModel model : selectedModel.getSubdepartments()) {
                for (FinanceDepartmentModel financeDepartmentModel : model.getFinanceDepartments()) {
                    financeDepartmentBox.addItem(financeDepartmentModel);
                }
            }
        });

        producerBox.addActionListener(e -> {
            if (producerBox.getItemCount() > 0) {
                ProducerModel model = (ProducerModel) producerBox.getSelectedItem();
                if (!model.equals(emptyProducerModel)) {
                    catNumberField.setEnabled(true);
                } else {
                    catNumberField.setEnabled(false);
                    catNumberField.setText("");
                }
            }
        });

        supplierBox.addActionListener(e -> {
            if (supplierBox.getItemCount() > 0) {
                SupplierModel model = (SupplierModel) supplierBox.getSelectedItem();
                if (!model.equals(emptySupplierModel)) {
                    addReasonForSupplierChoiceButton.setEnabled(true);
                    reasonForSupplierChoiceBox.setEnabled(true);
                } else {
                    addReasonForSupplierChoiceButton.setEnabled(false);
                    reasonForSupplierChoiceBox.setEnabled(false);
                    reasonForSupplierChoiceBox.setSelectedIndex(0);
                }
            }
        });

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
                        if (inBidEditMode) {
                            createdBidModel.setUpdated();
                        } else createdBidModel.setCreated();

                        listener.persistModelEventOccurred(createdBidModel, (BidType) bidTypeBox.getSelectedItem());
                        clearFieldsAndSetTitle();
                    }
                }
        );

        cancelButton.addActionListener(e -> {
            clearFieldsAndSetTitle();
            System.out.println(getPreferredSize().toString());
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                clearFieldsAndSetTitle();
            }
        });

    }

    void setCurrentDepartment(DepartmentModel currentDepartment) {
        this.currentDepartment = currentDepartment;
        Utils.setBoxFromModel(departmentBox, currentDepartment);
    }

    void setCurrentFinanceDepartment(FinanceDepartmentModel currentFinanceDepartment) {
        this.currentFinanceDepartment = currentFinanceDepartment;
        Utils.setBoxFromModel(financeDepartmentBox, currentFinanceDepartment);
    }

    void setCurrentBidType(BidType type) {
        this.currentBidType = type;
        bidTypeBox.setSelectedItem(currentBidType);
    }

    void setEnabledDepartmentBox(boolean state) {
        departmentBox.setEnabled(state);
    }

    private void clearFieldsAndSetTitle() {
        inBidEditMode = false;
        createdBidModel = emptyBidModel;
        setVisible(false);
        departmentBox.setSelectedIndex(0);
        financeDepartmentBox.setSelectedIndex(0);
        producerBox.setSelectedIndex(0);
        cpvField.setText("");
        catNumberField.setText("");
        descriptionPane.setText("");
        supplierBox.setSelectedIndex(0);
        amUnitsBox.setSelectedIndex(0);
        amountField.setText("");
        oneUnitPriceField.setText("");
        calculateTotalPrice();
        setTitle(Labels.getProperty("createBid"));
        okButton.setText(Labels.getProperty("createBid"));
        currentDepartment = null;
        currentFinanceDepartment = null;
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
                totalPrice = BigDecimal.ZERO;
                totalPriceLabel.setText(Labels.getProperty("wrongFormat"));
            }
        } else {
            totalPrice = BigDecimal.ZERO;
            totalPriceLabel.setText(totalPrice + Labels.withSpaceBefore("uah"));
        }
    }

    public void setFinanceDepartmentBoxData(List<FinanceDepartmentModel> db) {
        for (FinanceDepartmentModel model : db) {
            if (model.isActive()) {
                financeDepartmentBox.addItem(model);
            }
        }
    }

    void addToDepartmentBox(DepartmentModel model) {
        departmentBox.addItem(model);
    }

    public void setProducerBoxData(List<ProducerModel> db) {
        producerBox.removeAllItems();
        producerBox.addItem(emptyProducerModel);
        for (ProducerModel model : db) {
            if (model.isActive()) {
                producerBox.addItem(model);
            }
        }
        producerBox.repaint();
    }

    public void setSupplierBoxData(List<SupplierModel> db) {
        supplierBox.removeAllItems();
        supplierBox.addItem(emptySupplierModel);
        for (SupplierModel model : db) {
            if (model.isActive()) {
                supplierBox.addItem(model);
            }
        }
        supplierBox.repaint();
    }

    public void setReasonForSupplierChoiceBoxData(List<ReasonForSupplierChoiceModel> db) {
        reasonForSupplierChoiceBox.removeAllItems();
        reasonForSupplierChoiceBox.addItem(emptyReasonForSupplierChoiceModel);
        for (ReasonForSupplierChoiceModel model : db) {
            if (model.isActive()) {
                reasonForSupplierChoiceBox.addItem(model);
            }
        }
    }

    public void setAmUnitsBoxData(List<AmountUnitsModel> db) {
        amUnitsBox.removeAllItems();
        amUnitsBox.addItem(emptyAmountUnitsModel);
        for (AmountUnitsModel model : db) {
            if (model.isActive()) {
                amUnitsBox.addItem(model);
            }
        }
        amUnitsBox.repaint();
    }

    private boolean checkFields() {
        DepartmentModel selectedDepartmentModel = (DepartmentModel) departmentBox.getSelectedItem();
        if (selectedDepartmentModel.equals(emptyDepartmentModel)) {
            Utils.emptyFieldError(parent, Labels.getProperty("department"));
            departmentBox.requestFocusInWindow();
            return false;
        }

        SubdepartmentModel selectedSubdepartmentModel = (SubdepartmentModel) subdepartmentBox.getSelectedItem();
        if (selectedSubdepartmentModel.equals(emptySubdepartmentModel)) {
            Utils.emptyFieldError(parent, Labels.getProperty("subdepartment"));
            subdepartmentBox.requestFocusInWindow();
            return false;
        }
        FinanceDepartmentModel selectedFinanceDepartmentModel = (FinanceDepartmentModel) financeDepartmentBox.getSelectedItem();
        if (selectedFinanceDepartmentModel.equals(emptyFinanceDepartmentModel)) {
            Utils.emptyFieldError(parent, Labels.getProperty("order"));
            financeDepartmentBox.requestFocusInWindow();
            return false;
        }
        ProducerModel selectedProducerModel = (ProducerModel) producerBox.getSelectedItem();
        String selectedCatNum = catNumberField.getText();
        if (selectedCPV.equals(emptyCpvModel)) {
            Utils.emptyFieldError(parent, Labels.getProperty("cpvCode"));
            searchCPVButton.requestFocusInWindow();
            return false;
        } else if (selectedCPV.getCpvId().length() != 10) {
            Utils.wrongFormatError(parent, Labels.getProperty("cpvCode"), Labels.getProperty("wrongFormatCPV"));
            searchCPVButton.requestFocusInWindow();
            return false;
        }
        String selectedDescription = descriptionPane.getText();
        if (selectedDescription.isEmpty()) {
            Utils.emptyFieldError(parent, Labels.getProperty("description"));
            descriptionPane.requestFocusInWindow();
            return false;
        }
        SupplierModel selectedSupplierModel = (SupplierModel) supplierBox.getSelectedItem();
        ReasonForSupplierChoiceModel selectedReasonModel = (ReasonForSupplierChoiceModel) reasonForSupplierChoiceBox.getSelectedItem();
        if (!selectedSupplierModel.equals(emptySupplierModel) && selectedReasonModel.equals(emptyReasonForSupplierChoiceModel)) {
            Utils.emptyFieldError(parent, Labels.getProperty("reasonForSupplierChoice"));
            supplierBox.requestFocusInWindow();
            return false;
        }
        AmountUnitsModel selectedAmountUnitsModel = (AmountUnitsModel) amUnitsBox.getSelectedItem();
        if (selectedAmountUnitsModel.equals(emptyAmountUnitsModel)) {
            Utils.emptyFieldError(parent, Labels.getProperty("packing"));
            amUnitsBox.requestFocusInWindow();
            return false;
        }
        String amountString = amountField.getText();
        if (amountString.isEmpty()) {
            Utils.emptyFieldError(parent, Labels.getProperty("amount"));
            amountField.requestFocusInWindow();
            return false;
        }

        int amount;
        try {
            amount = Integer.parseInt(amountString);
        } catch (NumberFormatException ex) {
            Utils.wrongFormatError(parent, Labels.getProperty("amount"), Labels.getProperty("integersOnly"));
            amountField.requestFocusInWindow();
            return false;
        }

        String onePriceString = oneUnitPriceField.getText();
        if (onePriceString.equals("")) {
            Utils.emptyFieldError(parent, Labels.getProperty("oneUnitPrice"));
            oneUnitPriceField.requestFocusInWindow();
            return false;
        }

        BigDecimal onePrice;
        try {
            onePrice = new BigDecimal(onePriceString);
        } catch (NumberFormatException ex) {
            Utils.wrongFormatError(parent, Labels.getProperty("oneUnitPrice"), Labels.getProperty("wrongFloatFormat"));
            oneUnitPriceField.requestFocusInWindow();
            return false;
        }

        BidType selectedBidType = (BidType) bidTypeBox.getSelectedItem();

        if (selectedFinanceDepartmentModel.getLeftAmount(selectedBidType).compareTo(totalPrice) < 0) {
            JOptionPane.showMessageDialog(parent, Labels.getProperty("insufficientFundsMessage"), Labels.getProperty("insufficientFunds"), JOptionPane.ERROR_MESSAGE);
            amountField.requestFocusInWindow();
            return false;
        }

        if (createdBidModel == emptyBidModel) {
            List<BidStatusModel<? extends BidModel>> statuses = createdBidModel.getStatuses();
            statuses.add(new BidStatusModel(Status.CREATED, createdBidModel));
            createdBidModel = new BidEquipmentModel(selectedDepartmentModel, selectedProducerModel, selectedCatNum, selectedDescription, selectedCPV, onePrice, amount, selectedAmountUnitsModel, selectedFinanceDepartmentModel, selectedSupplierModel, statuses, selectedReasonModel);// TODO
        } else {
            createdBidModel.setDepartment(selectedDepartmentModel);
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
        }
        return true;
    }

    public void setCreateBidDialogListener(CreateBidDialogListener listener) {
        this.listener = listener;
    }

    public void setCPVField(String cpvCode) {
        cpvField.setText(cpvCode);
    }

    void loadToDialog(BidModel model) {
        if (model instanceof BidMaterialModel) {
            setCurrentBidType(BidType.MATERIALS);
        } else if (model instanceof BidEquipmentModel) {
            setCurrentBidType(BidType.EQUIPMENT);
        } else if (model instanceof BidServiceModel) {
            setCurrentBidType(BidType.SERVICES);
        }
        inBidEditMode = true;
        setTitle(Labels.getProperty("editBid"));
        okButton.setText(Labels.getProperty("editBid"));
        Utils.setBoxFromModel(departmentBox, model.getDepartment());
        Utils.setBoxFromModel(financeDepartmentBox, model.getFinances());
        Utils.setBoxFromModel(producerBox, model.getProducer());
        Utils.setBoxFromModel(supplierBox, model.getSupplier());
        Utils.setBoxFromModel(reasonForSupplierChoiceBox, model.getReasonForSupplierChoice());
        Utils.setBoxFromModel(amUnitsBox, model.getAmountUnit());
        createdBidModel = model;
        cpvField.setText(createdBidModel.getCpv().getCpvId());
        catNumberField.setText(createdBidModel.getCatNum());
        descriptionPane.setText(model.getBidDesc());
        amountField.setText(Integer.toString(createdBidModel.getAmount()));
        oneUnitPriceField.setText(createdBidModel.getOnePrice().toString());
        calculateTotalPrice();
        setVisible(true);
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible && listener != null) {
            listener.getAllData();
        }
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

        /// Next row///
        gc.gridy++;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
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
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        createBidPanel.add(new JLabel(Labels.withColon("description")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.gridwidth = 3;
        gc.ipady = 50;
        gc.insets = smallPadding;
        createBidPanel.add(new JScrollPane(descriptionPane), gc);

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
        Dimension btnSize = okButton.getPreferredSize();
        cancelButton.setPreferredSize(btnSize);
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        setLayout(new BorderLayout());
        add(depOrdersPanel, BorderLayout.WEST);
        add(createBidPanel, BorderLayout.EAST);
        add(buttonsPanel, BorderLayout.SOUTH);
    }
}