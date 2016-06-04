package gui.bids;

import gui.Labels;
import gui.MainFrame;
import gui.Utils;
import model.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by laban on 30.05.2016.
 */
public class CreateBidDialog extends JDialog {

    private JComboBox<DepartmentModel> departmentBox;
    private JComboBox<FinanceDepartmentModel> financeDepartmentBox;
    private JComboBox<ProducerModel> producerBox;
    private JComboBox<SupplierModel> supplierBox;
    private JComboBox<AmountUnitsModel> amUntisBox;
    private JButton addProducerButton;
    private JButton addSupplierButton;
    private JButton searchCPVButton;
    private JButton addAmUnitsButton;
    private JButton okButton;
    private JButton cancelButton;
    private JTextField catNumberField;
    private JTextField cpvField;
    private JTextField amountField;
    private JTextField oneUnitPriceField;
    private JTextPane descriptionPane;
    private final DepartmentModel emptyDepartmentModel = new DepartmentModel();
    private final FinanceDepartmentModel emptyFinanceDepartmentModel = new FinanceDepartmentModel();
    private JLabel totalPriceLabel;
    private CreateBidDialogListener listener;

    public CreateBidDialog(MainFrame parent) {
        super(parent, Labels.getProperty("createBid"), false);
        setSize(330, 470);
        setResizable(false);
        setLocationRelativeTo(parent);

        totalPriceLabel = new JLabel("0"+Labels.withSpaceBefore("uah"));
        totalPriceLabel.setForeground(Color.RED);

        Dimension preferredFieldDim = new Dimension(235, 25);

        departmentBox = new JComboBox<>();
        departmentBox.setPreferredSize(new Dimension(230, 25));
        departmentBox.addItem(emptyDepartmentModel);

        financeDepartmentBox = new JComboBox<>();
        financeDepartmentBox.setPreferredSize(preferredFieldDim);
        financeDepartmentBox.addItem(emptyFinanceDepartmentModel);

        preferredFieldDim = new Dimension(150, 25);

        producerBox = new JComboBox<>();
        producerBox.setPreferredSize(preferredFieldDim);
        ProducerModel emptyProducerModel = new ProducerModel();
        producerBox.addItem(emptyProducerModel);

        supplierBox = new JComboBox<>();
        supplierBox.setPreferredSize(preferredFieldDim);
        SupplierModel emptySupplierModel = new SupplierModel();
        supplierBox.addItem(emptySupplierModel);

        amUntisBox = new JComboBox<>();
        amUntisBox.setPreferredSize(preferredFieldDim);
        AmountUnitsModel emptyAmountUnitsModel = new AmountUnitsModel();
        amUntisBox.addItem(emptyAmountUnitsModel);

        Dimension buttonDim = new Dimension(25, 25);

        addProducerButton = new JButton();
        addProducerButton.setToolTipText(Labels.getProperty("addProd"));
        addProducerButton.setIcon(Utils.createIcon("/images/Add16.gif"));
        addProducerButton.setPreferredSize(buttonDim);
        addProducerButton.setEnabled(true);

        addSupplierButton = new JButton();
        addSupplierButton.setToolTipText(Labels.getProperty("addSupl"));
        addSupplierButton.setIcon(Utils.createIcon("/images/Add16.gif"));
        addSupplierButton.setPreferredSize(buttonDim);
        addSupplierButton.setEnabled(true);

        addAmUnitsButton = new JButton();
        addAmUnitsButton.setToolTipText(Labels.getProperty("addAmUnit"));
        addAmUnitsButton.setIcon(Utils.createIcon("/images/Add16.gif"));
        addAmUnitsButton.setPreferredSize(buttonDim);
        addAmUnitsButton.setEnabled(true);

        searchCPVButton = new JButton();
        searchCPVButton.setToolTipText(Labels.getProperty("cpvPanelTab"));
        searchCPVButton.setIcon(Utils.createIcon("/images/Find16.gif"));
        searchCPVButton.setPreferredSize(buttonDim);
        searchCPVButton.setEnabled(true);

        okButton = new JButton(Labels.getProperty("createBid"));

        cancelButton = new JButton(Labels.getProperty("cancelBtn"));

        catNumberField = new JTextField();
        catNumberField.setPreferredSize(preferredFieldDim);
        cpvField = new JTextField();
        cpvField.setPreferredSize(preferredFieldDim);
        amountField = new JTextField();
        amountField.setPreferredSize(preferredFieldDim);
        oneUnitPriceField = new JTextField();
        oneUnitPriceField.setPreferredSize(preferredFieldDim);
        descriptionPane = new JTextPane();
        descriptionPane.setPreferredSize(new Dimension(147, 25));
        descriptionPane.setEditable(true);

        createLayout();

        departmentBox.addActionListener(e -> {
            financeDepartmentBox.removeAllItems();
            financeDepartmentBox.addItem(emptyFinanceDepartmentModel);
            DepartmentModel selctedModel = (DepartmentModel) departmentBox.getSelectedItem();
            if(!selctedModel.equals(emptyDepartmentModel)){
                listener.departmentSelectionEventOccurred(selctedModel.getDepId());
            }
        });

        addProducerButton.addActionListener(e -> parent.getProducerDialog().setVisible(true));
        addSupplierButton.addActionListener(e -> parent.getSupplierDialog().setVisible(true));
        addAmUnitsButton.addActionListener(e -> parent.getAmUnitsDialog().setVisible(true));
        searchCPVButton.addActionListener(e -> parent.getCpvDialog().setVisible(true));

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
                    //TODO create bid
                    setVisible(false);
                }
        );

        cancelButton.addActionListener(e -> setVisible(false));

    }

    private void calculateTotalPrice() {
        if (!oneUnitPriceField.getText().isEmpty() && !amountField.getText().isEmpty()) {
            //TODO check numbers validity
            BigDecimal onePrice = new BigDecimal(oneUnitPriceField.getText());
            BigDecimal amount = new BigDecimal(amountField.getText());
            totalPriceLabel.setText(onePrice.multiply(amount) + Labels.withSpaceBefore("uah"));
        } else totalPriceLabel.setText("0"+Labels.withSpaceBefore("uah"));
}

    public void setFinanceDepartmentBoxData(List<FinanceDepartmentModel> db){
        for(FinanceDepartmentModel model : db){
            financeDepartmentBox.addItem(model);
        }
    }

    void addToDepartmentBox(DepartmentModel model) {
        departmentBox.addItem(model);
    }

    void setProducerBoxData(List<ProducerModel> db) {
        for (ProducerModel model : db) {
            producerBox.addItem(model);
        }
    }

    void setSupplierBoxData(List<SupplierModel> db) {
        for (SupplierModel model : db) {
            supplierBox.addItem(model);
        }
    }

    void setAmUnitsBoxData(List<AmountUnitsModel> db) {
        for (AmountUnitsModel model : db) {
            amUntisBox.addItem(model);
        }
    }

    public void setCreateBidDialogListener(CreateBidDialogListener listener){
        this.listener = listener;
    }

    public void setCPVField(String cpvCode) {
        cpvField.setText(cpvCode);
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
        createBidPanel.add(new JLabel(Labels.withColon("prodBorder")), gc);

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
        gc.insets = smallPadding;
        createBidPanel.add(new JLabel(Labels.withColon("packing")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        createBidPanel.add(amUntisBox, gc);

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