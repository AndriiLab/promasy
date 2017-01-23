package gui.finance;

import gui.Labels;
import gui.Utils;
import model.models.FinanceModel;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Dialog for creation and updating of {@link FinanceModel}
 */
public class CreateFinanceDialog extends JDialog {

    private final java.util.Date defaultStartDate = (new GregorianCalendar(Year.now().getValue(), 0, 1)).getTime();
    private final java.util.Date defaultEndDate = (new GregorianCalendar(Year.now().getValue(), 11, 31)).getTime();
    private int orderNumber;
    private String orderName;
    private Date startDate;
    private Date endDate;
    private BigDecimal materialAmount;
    private BigDecimal equipmentAmount;
    private BigDecimal servicesAmount;
    private FinanceModel currentFinanceModel;
    private JFrame parent;
    private JTextField orderNumberField;
    private JTextField orderNameField;
    private JXDatePicker startDatePicker;
    private JXDatePicker endDatePicker;
    private JTextField materialsAmountField;
    private JTextField equipmentAmountField;
    private JTextField servicesAmountField;
    private JButton okButton;
    private JButton cancelButton;
    private FinanceDialogListener listener;

    public CreateFinanceDialog(JFrame parent) {
        super(parent, Labels.getProperty("createOrder"), true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setSize(265, 245);
        setResizable(false);

        this.parent = parent;

        currentFinanceModel = new FinanceModel();

        orderNumberField = new JTextField(10);
        orderNameField = new JTextField(10);
        materialsAmountField = new JTextField(10);
        equipmentAmountField = new JTextField(10);
        servicesAmountField = new JTextField(10);
        startDatePicker = new JXDatePicker(Locale.getDefault());
        startDatePicker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        startDatePicker.setDate(defaultStartDate);
        endDatePicker = new JXDatePicker(Locale.getDefault());
        endDatePicker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        endDatePicker.setDate(defaultEndDate);


        okButton = new JButton(Labels.getProperty("create"));
        cancelButton = new JButton(Labels.getProperty("cancel"));

        layoutControls();

        okButton.addActionListener(e -> {
            if (checkInput() && listener != null) {
                currentFinanceModel.setFinanceNumber(orderNumber);
                currentFinanceModel.setFinanceName(orderName);
                currentFinanceModel.setStartDate(startDate);
                currentFinanceModel.setEndDate(endDate);
                currentFinanceModel.setTotalMaterials(materialAmount);
                currentFinanceModel.setTotalEquipment(equipmentAmount);
                currentFinanceModel.setTotalServices(servicesAmount);
                if (currentFinanceModel.getModelId() == 0) {
                    currentFinanceModel.setCreated();
                } else {
                    currentFinanceModel.setUpdated();
                }
                listener.persistModelEventOccurred(currentFinanceModel);
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
        String emptyString = "";
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
        super.setTitle(Labels.getProperty("createOrder"));
        okButton.setText(Labels.getProperty("create"));
        currentFinanceModel = new FinanceModel();
        this.setVisible(false);
    }

    public void setFinanceModel(FinanceModel selectedFinanceModel) {
        this.currentFinanceModel = selectedFinanceModel;
        orderNumberField.setText(String.valueOf(currentFinanceModel.getFinanceNumber()));
        orderNameField.setText(currentFinanceModel.getFinanceName());
        materialsAmountField.setText(String.valueOf(currentFinanceModel.getTotalMaterials()));
        equipmentAmountField.setText(String.valueOf(currentFinanceModel.getTotalEquipment()));
        servicesAmountField.setText(String.valueOf(currentFinanceModel.getTotalServices()));
        startDatePicker.setDate(currentFinanceModel.getStartDate());
        endDatePicker.setDate(currentFinanceModel.getEndDate());
        super.setTitle(Labels.getProperty("editFinance"));
        okButton.setText(Labels.getProperty("edit"));
        this.setVisible(true);
    }

    public void setFinanceDialogListener(FinanceDialogListener listener) {
        this.listener = listener;
    }

    private boolean checkInput() {
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
        materialAmount = Utils.parseBigDecimal(parent, materialsAmountField, Labels.getProperty("materialsAmount"));
        equipmentAmount = Utils.parseBigDecimal(parent, equipmentAmountField, Labels.getProperty("equipmentAmount"));
        servicesAmount = Utils.parseBigDecimal(parent, servicesAmountField, Labels.getProperty("servicesAmount"));

        return (materialAmount != null && equipmentAmount != null && servicesAmount != null);
    }

    private void layoutControls() {
        JPanel financePanel = new JPanel();
        JPanel buttonsPanel = new JPanel();
        financePanel.setLayout(new GridBagLayout());
        financePanel.setBorder(BorderFactory.createEtchedBorder());

        Insets smallPadding = new Insets(1, 0, 1, 5);
        Insets largePadding = new Insets(1, 0, 1, 15);

        GridBagConstraints gc = new GridBagConstraints();

        gc.gridy = 0;
        gc.fill = GridBagConstraints.FIRST_LINE_START;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        financePanel.add(new JLabel(Labels.withColon("financeNumber")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        financePanel.add(orderNumberField, gc);

        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        financePanel.add(new JLabel(Labels.withColon("financeName")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        financePanel.add(orderNameField, gc);

        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        financePanel.add(new JLabel(Labels.withColon("materialsAmount")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        financePanel.add(materialsAmountField, gc);

        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        financePanel.add(new JLabel(Labels.withColon("equipmentAmount")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        financePanel.add(equipmentAmountField, gc);

        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        financePanel.add(new JLabel(Labels.withColon("servicesAmount")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        financePanel.add(servicesAmountField, gc);

        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        financePanel.add(new JLabel(Labels.withColon("dateStart")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        financePanel.add(startDatePicker, gc);

        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        financePanel.add(new JLabel(Labels.withColon("dateEnd")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        financePanel.add(endDatePicker, gc);

        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5));

        Utils.setPreferredButtonSizes(okButton, cancelButton);

        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        this.setLayout(new BorderLayout());
        this.add(financePanel, BorderLayout.CENTER);
        this.add(buttonsPanel, BorderLayout.SOUTH);
    }
}
