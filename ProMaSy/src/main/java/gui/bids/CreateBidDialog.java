package main.java.gui.bids;

import main.java.gui.Icons;
import main.java.gui.Labels;
import main.java.gui.MainFrame;
import main.java.gui.Utils;
import main.java.model.*;
import main.java.model.enums.Status;

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
    private final BidModel emptyBidModel = new BidModel();
    private final ReasonForSupplierChoiceModel emptyReasonForSupplierChoiceModel = new ReasonForSupplierChoiceModel();
    private JComboBox<DepartmentModel> departmentBox;
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
    private JLabel totalPriceLabel;
    private CreateBidDialogListener listener;
    private MainFrame parent;
    private Long currentDepartmentId = 0L;
    private Long currentFinanceDepartmentId = 0L;
    private BigDecimal totalPrice = BigDecimal.ZERO;

    public CreateBidDialog(MainFrame parent) {
        super(parent, Labels.getProperty("createBid"), false);
        this.parent = parent;
        setSize(440, 495);
        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        createdBidModel = emptyBidModel;

        totalPriceLabel = new JLabel("0" + Labels.withSpaceBefore("uah"));
        totalPriceLabel.setForeground(Color.RED);

        Dimension preferredFieldDim = new Dimension(235, 25);

        departmentBox = new JComboBox<>();
        departmentBox.setPreferredSize(preferredFieldDim);
        departmentBox.addItem(emptyDepartmentModel);

        financeDepartmentBox = new JComboBox<>();
        financeDepartmentBox.setPreferredSize(preferredFieldDim);
        financeDepartmentBox.addItem(emptyFinanceDepartmentModel);

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
            if (!selectedModel.equals(emptyDepartmentModel)) {
                listener.departmentSelectionEventOccurred(selectedModel.getModelId());
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
        searchCPVButton.addActionListener(e -> parent.showCpvDialog());

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
                        if (!currentFinanceDepartmentId.equals(0L)) {
                            if (createdBidModel.getModelId() == 0) {
                                listener.bidCreateEventOccurred(createdBidModel, currentDepartmentId, currentFinanceDepartmentId);
                            } else {
                                listener.bidEditEventOccurred(createdBidModel, currentDepartmentId, currentFinanceDepartmentId);
                            }
                        } else if (!currentDepartmentId.equals(0L)) {
                            if (createdBidModel.getModelId() == 0) {
                                listener.bidCreateEventOccurred(createdBidModel, currentDepartmentId);
                            } else {
                                listener.bidEditEventOccurred(createdBidModel, currentDepartmentId);
                            }
                        } else {
                            if (createdBidModel.getModelId() == 0) {
                                listener.bidCreateEventOccurred(createdBidModel);
                            } else {
                                listener.bidEditEventOccurred(createdBidModel);
                            }
                        }
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

    void setCurrentDepartmentId(long currentDepartmentId) {
        this.currentDepartmentId = currentDepartmentId;
        Utils.setBoxFromID(departmentBox, currentDepartmentId);
    }

    void setCurrentFinanceDepartmentId(long currentFinanceDepartmentId) {
        this.currentFinanceDepartmentId = currentFinanceDepartmentId;
        Utils.setBoxFromID(financeDepartmentBox, currentFinanceDepartmentId);
    }

    void setEnabledDepartmentBox(boolean state) {
        departmentBox.setEnabled(state);
    }

    private void clearFieldsAndSetTitle() {
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
        currentDepartmentId = 0L;
        currentFinanceDepartmentId = 0L;
    }

    private void calculateTotalPrice() {
        if (!oneUnitPriceField.getText().isEmpty() && !amountField.getText().isEmpty()) {
            try {
                BigDecimal onePrice = new BigDecimal(oneUnitPriceField.getText());
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
            financeDepartmentBox.addItem(model);
        }
    }

    void addToDepartmentBox(DepartmentModel model) {
        departmentBox.addItem(model);
    }

    public void setProducerBoxData(List<ProducerModel> db) {
        producerBox.removeAllItems();
        producerBox.addItem(emptyProducerModel);
        for (ProducerModel model : db) {
            producerBox.addItem(model);
        }
        producerBox.repaint();
    }

    public void setSupplierBoxData(List<SupplierModel> db) {
        supplierBox.removeAllItems();
        supplierBox.addItem(emptySupplierModel);
        for (SupplierModel model : db) {
            supplierBox.addItem(model);
        }
        supplierBox.repaint();
    }

    public void setReasonForSupplierChoiceBoxData(List<ReasonForSupplierChoiceModel> db) {
        reasonForSupplierChoiceBox.removeAllItems();
        reasonForSupplierChoiceBox.addItem(emptyReasonForSupplierChoiceModel);
        for (ReasonForSupplierChoiceModel model : db) {
            reasonForSupplierChoiceBox.addItem(model);
        }
    }

    public void setAmUnitsBoxData(List<AmountUnitsModel> db) {
        amUnitsBox.removeAllItems();
        amUnitsBox.addItem(emptyAmountUnitsModel);
        for (AmountUnitsModel model : db) {
            amUnitsBox.addItem(model);
        }
        amUnitsBox.repaint();
    }

    private boolean checkFields() {
        DepartmentModel selectedDepartmentModel = (DepartmentModel) departmentBox.getSelectedItem();
        if (selectedDepartmentModel.equals(emptyDepartmentModel)) {
            Utils.emptyFieldError(parent, Labels.getProperty("department"));
            return false;
        }
        FinanceDepartmentModel selectedFinanceDepartmentModel = (FinanceDepartmentModel) financeDepartmentBox.getSelectedItem();
        if (selectedFinanceDepartmentModel.equals(emptyFinanceDepartmentModel)) {
            Utils.emptyFieldError(parent, Labels.getProperty("order"));
            return false;
        }
        ProducerModel selectedProducerModel = (ProducerModel) producerBox.getSelectedItem();
        String selectedCatNum = catNumberField.getText();
        String selectedCPV = cpvField.getText();
        if (selectedCPV.isEmpty()) {
            Utils.emptyFieldError(parent, Labels.getProperty("CPVCode"));
            return false;
        } else if (selectedCPV.length() != 10) {
            Utils.wrongFormatError(parent, Labels.getProperty("CPVCode"), Labels.getProperty("wrongFormatCPV"));
            return false;
        }
        String selectedDescription = descriptionPane.getText();
        if (selectedDescription.isEmpty()) {
            Utils.emptyFieldError(parent, Labels.getProperty("description"));
            return false;
        }
        SupplierModel selectedSupplierModel = (SupplierModel) supplierBox.getSelectedItem();
        ReasonForSupplierChoiceModel selectedReasonModel = (ReasonForSupplierChoiceModel) reasonForSupplierChoiceBox.getSelectedItem();
        if (!selectedSupplierModel.equals(emptySupplierModel) && selectedReasonModel.equals(emptyReasonForSupplierChoiceModel)) {
            Utils.emptyFieldError(parent, Labels.getProperty("reasonForSupplierChoice"));
            return false;
        }
        AmountUnitsModel selectedAmountUnitsModel = (AmountUnitsModel) amUnitsBox.getSelectedItem();
        if (selectedAmountUnitsModel.equals(emptyAmountUnitsModel)) {
            Utils.emptyFieldError(parent, Labels.getProperty("packing"));
            return false;
        }
        String amountString = amountField.getText();
        if (amountString.isEmpty()) {
            Utils.emptyFieldError(parent, Labels.getProperty("amount"));
            return false;
        }

        int amount;
        try {
            amount = Integer.parseInt(amountString);
        } catch (NumberFormatException ex) {
            Utils.wrongFormatError(parent, Labels.getProperty("amount"), Labels.getProperty("integersOnly"));
            return false;
        }

        String onePriceString = oneUnitPriceField.getText();
        if (onePriceString.equals("")) {
            Utils.emptyFieldError(parent, Labels.getProperty("oneUnitPrice"));
            return false;
        }

        BigDecimal onePrice;
        try {
            onePrice = new BigDecimal(onePriceString);
        } catch (NumberFormatException ex) {
            Utils.wrongFormatError(parent, Labels.getProperty("oneUnitPrice"),  Labels.getProperty("wrongFloatFormat"));
            return false;
        }

        if (selectedFinanceDepartmentModel.getLeftAmount().compareTo(totalPrice) < 0) {
            JOptionPane.showMessageDialog(parent, Labels.getProperty("insufficientFundsMessage"), Labels.getProperty("insufficientFunds"), JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (createdBidModel == emptyBidModel) {
            createdBidModel = new BidModel(selectedDepartmentModel.getModelId(), selectedProducerModel.getModelId(), selectedCatNum, selectedDescription, selectedCPV, onePrice, amount, selectedAmountUnitsModel.getModelId(), selectedFinanceDepartmentModel.getModelId(), selectedSupplierModel.getModelId(), Status.CREATED.getStatusId(), selectedReasonModel.getModelId(), selectedReasonModel.getReason());

        } else {
            createdBidModel.setDepId(selectedDepartmentModel.getModelId());
            createdBidModel.setProducerId(selectedProducerModel.getModelId());
            createdBidModel.setCatNum(selectedCatNum);
            createdBidModel.setBidDesc(selectedDescription);
            createdBidModel.setCpvCode(selectedCPV);
            createdBidModel.setOnePrice(onePrice);
            createdBidModel.setAmount(amount);
            createdBidModel.setAmUnitId(selectedAmountUnitsModel.getModelId());
            createdBidModel.setFinanceId(selectedFinanceDepartmentModel.getModelId());
            createdBidModel.setSupplierId(selectedSupplierModel.getModelId());
            createdBidModel.setReasonId(selectedReasonModel.getModelId());
            createdBidModel.setReasonName(selectedReasonModel.getReason());
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
        setTitle(Labels.getProperty("editBid"));
        okButton.setText(Labels.getProperty("editBid"));
        createdBidModel = model;
        setVisible(true);
        Utils.setBoxFromID(departmentBox, createdBidModel.getDepId());
        Utils.setBoxFromID(financeDepartmentBox, createdBidModel.getFinanceId());
        Utils.setBoxFromID(producerBox, createdBidModel.getProducerId());
        cpvField.setText(createdBidModel.getCpvCode());
        catNumberField.setText(createdBidModel.getCatNum());
        descriptionPane.setText(model.getBidDesc());
        Utils.setBoxFromID(supplierBox, createdBidModel.getSupplierId());
        Utils.setBoxFromID(reasonForSupplierChoiceBox, createdBidModel.getReasonId());
        Utils.setBoxFromID(amUnitsBox, createdBidModel.getAmUnitId());
        amountField.setText(Integer.toString(createdBidModel.getAmount()));
        oneUnitPriceField.setText(createdBidModel.getOnePrice().toString());
        calculateTotalPrice();
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
        depOrdersPanel.add(new JLabel(Labels.withColon("order")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        depOrdersPanel.add(financeDepartmentBox, gc);

        // create new bid panel
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
        createBidPanel.add(new JLabel(Labels.withColon("CPVCode")), gc);

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
        add(depOrdersPanel, BorderLayout.NORTH);
        add(createBidPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }
}