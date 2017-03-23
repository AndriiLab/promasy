package gui.finance;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import controller.Logger;
import gui.Utils;
import gui.commons.Labels;
import gui.components.PJComboBox;
import gui.components.PJOptionPane;
import model.enums.Fund;
import model.models.EmptyModel;
import model.models.FinanceModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Locale;

/**
 * Dialog for creation and updating of {@link FinanceModel}
 */
public class CreateFinanceDialog extends JDialog {

    private final LocalDate defaultStartDate = LocalDate.of(Year.now().getValue(), Month.JANUARY, 1);
    private final LocalDate defaultEndDate = LocalDate.of(Year.now().getValue(), Month.DECEMBER, 31);
    private int orderNumber;
    private Integer kpkvk;
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
    private JTextField kpkvkField;
    private PJComboBox<Fund> fundBox;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
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
        setSize(300, 295);
        setResizable(false);

        this.parent = parent;

        currentFinanceModel = new FinanceModel();

        orderNumberField = new JTextField(12);
        orderNameField = new JTextField(12);
        materialsAmountField = new JTextField(12);
        equipmentAmountField = new JTextField(12);
        servicesAmountField = new JTextField(12);
        kpkvkField = new JTextField(12);

        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setFormatForDatesCommonEra("dd.MM.yyyy");

        startDatePicker = new DatePicker(dateSettings);
        startDatePicker.setLocale(Locale.getDefault());
        startDatePicker.setDate(defaultStartDate);

        dateSettings = new DatePickerSettings();
        dateSettings.setFormatForDatesCommonEra("dd.MM.yyyy");

        endDatePicker = new DatePicker(dateSettings);
        endDatePicker.setLocale(Locale.getDefault());
        endDatePicker.setDate(defaultEndDate);

        fundBox = new PJComboBox<>(Fund.values());
        fundBox.setPreferredSize(new Dimension(136, 20));

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
                currentFinanceModel.setFundType((Fund) fundBox.getSelectedItem());
                currentFinanceModel.setKPKVK(kpkvk);
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
        String emptyString = EmptyModel.STRING;
        orderNumberField.setText(emptyString);
        orderNameField.setText(emptyString);
        materialsAmountField.setText(emptyString);
        equipmentAmountField.setText(emptyString);
        servicesAmountField.setText(emptyString);
        kpkvkField.setText(emptyString);
        startDatePicker.setDate(defaultStartDate);
        endDatePicker.setDate(defaultEndDate);
        fundBox.setSelectedIndex(0);
        orderNumber = 0;
        orderName = emptyString;
        kpkvk = null;
        startDate = null;
        endDate = null;
        materialAmount = null;
        equipmentAmount = null;
        servicesAmount = null;
        super.setTitle(Labels.getProperty("createOrder"));
        okButton.setText(Labels.getProperty("create"));
        currentFinanceModel = new FinanceModel();
        super.setVisible(false);
    }

    public void setFinanceModel(FinanceModel selectedFinanceModel) {
        this.currentFinanceModel = selectedFinanceModel;
        orderNumberField.setText(String.valueOf(currentFinanceModel.getFinanceNumber()));
        orderNameField.setText(currentFinanceModel.getFinanceName());
        materialsAmountField.setText(String.valueOf(currentFinanceModel.getTotalMaterials()));
        equipmentAmountField.setText(String.valueOf(currentFinanceModel.getTotalEquipment()));
        servicesAmountField.setText(String.valueOf(currentFinanceModel.getTotalServices()));
        startDatePicker.setDate(currentFinanceModel.getStartDate().toLocalDate());
        endDatePicker.setDate(currentFinanceModel.getEndDate().toLocalDate());
        //TODO remove if statement after patch
        if (selectedFinanceModel.getFundType() != null) {
            fundBox.setSelectedObject(selectedFinanceModel.getFundType());
        }
        kpkvkField.setText(String.valueOf(selectedFinanceModel.getKPKVK()));
        super.setTitle(Labels.getProperty("editFinance"));
        okButton.setText(Labels.getProperty("edit"));
        super.setVisible(true);
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            kpkvkField.setText(Labels.getProperty("default.kpkvk"));
        }
        super.setVisible(visible);
    }

    public void setFinanceDialogListener(FinanceDialogListener listener) {
        this.listener = listener;
    }

    private boolean checkInput() {
        try {
            orderNumber = Integer.parseInt(orderNumberField.getText());
        } catch (NumberFormatException e) {
            Logger.warnEvent(e);
            PJOptionPane.wrongFormat(parent, Labels.getProperty("financeNumber"), Labels.getProperty("integersOnly"));
            orderNumberField.requestFocusInWindow();
            return false;
        }
        orderName = orderNameField.getText();
        startDate = Date.valueOf(startDatePicker.getDate());
        endDate = Date.valueOf(endDatePicker.getDate());
        if (orderName.isEmpty()) {
            PJOptionPane.emptyField(parent, Labels.getProperty("financeName"));
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
        if (materialAmount == null) {
            return false;
        }

        equipmentAmount = Utils.parseBigDecimal(parent, equipmentAmountField, Labels.getProperty("equipmentAmount"));
        if (equipmentAmount == null) {
            return false;
        }

        kpkvk = Utils.parseInteger(parent, kpkvkField, Labels.getProperty("kpkvk"));
        if (kpkvk == null) {
            return false;
        }

        servicesAmount = Utils.parseBigDecimal(parent, servicesAmountField, Labels.getProperty("servicesAmount"));

        return servicesAmount != null;
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
        financePanel.add(new JLabel(Labels.withColon("fundType")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        financePanel.add(fundBox, gc);

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

        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        financePanel.add(new JLabel(Labels.withColon("kpkvk")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = largePadding;
        financePanel.add(kpkvkField, gc);

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
