package gui.prodsupl;

import gui.CrEdDelButtons;
import gui.Labels;
import gui.Utils;
import model.models.EmptyModel;
import model.models.ReasonForSupplierChoiceModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Class for CRUD of item {@link ReasonForSupplierChoiceModel}
 */
public class ReasonsDialog extends JDialog {
    private JButton okButton;
    private JButton createReason;
    private JButton editReason;
    private JButton deleteReason;
    private JComboBox<ReasonForSupplierChoiceModel> reasonBox;
    private ReasonsDialogListener listener;
    private ReasonForSupplierChoiceModel privateReasonModel;
    private String newReasonName;

    public ReasonsDialog(JFrame parent) {
        super(parent, Labels.getProperty("reasonForSupplierChoice"), true);
        setSize(280, 150);
        setLocationRelativeTo(parent);

        Dimension comboBoxDim = new Dimension(150, 25);

        privateReasonModel = EmptyModel.REASON_FOR_SUPPLIER_CHOICE;

        DefaultComboBoxModel<ReasonForSupplierChoiceModel> prodModel = new DefaultComboBoxModel<>();
        reasonBox = new JComboBox<>(prodModel);
        reasonBox.addItem(EmptyModel.REASON_FOR_SUPPLIER_CHOICE);
        reasonBox.setPreferredSize(comboBoxDim);
        reasonBox.setEditable(true);

        CrEdDelButtons ced = new CrEdDelButtons(Labels.getProperty("reason_ced"));
        createReason = ced.getCreateButton();
        editReason = ced.getEditButton();
        deleteReason = ced.getDeleteButton();

        okButton = new JButton(Labels.getProperty("closeBtn"));

        layoutControls();

        reasonBox.addActionListener(e -> {
            Object item = reasonBox.getSelectedItem();
            if (item instanceof ReasonForSupplierChoiceModel && !item.equals(EmptyModel.REASON_FOR_SUPPLIER_CHOICE)) {
                privateReasonModel = (ReasonForSupplierChoiceModel) item;
            } else if (item instanceof String && !item.equals(EmptyModel.STRING)) {
                newReasonName = (String) item;
            }
        });

        createReason.addActionListener(e -> {
            if (!newReasonName.equals(EmptyModel.STRING)) {
                ReasonForSupplierChoiceModel model = new ReasonForSupplierChoiceModel(newReasonName);
                if (listener != null) {
                    reasonBox.removeAllItems();
                    reasonBox.addItem(EmptyModel.REASON_FOR_SUPPLIER_CHOICE);
                    model.setCreated();
                    listener.persistModelEventOccurred(model);
                }
            }
            privateReasonModel = EmptyModel.REASON_FOR_SUPPLIER_CHOICE;
            newReasonName = EmptyModel.STRING;
        });

        editReason.addActionListener(e -> {
            if (newReasonName != null && !newReasonName.equals(EmptyModel.STRING) && !privateReasonModel.equals(EmptyModel.REASON_FOR_SUPPLIER_CHOICE)) {
                if (listener != null) {
                    reasonBox.removeAllItems();
                    reasonBox.addItem(EmptyModel.REASON_FOR_SUPPLIER_CHOICE);
                    privateReasonModel.setReason(newReasonName);
                    privateReasonModel.setUpdated();
                    listener.persistModelEventOccurred(privateReasonModel);
                }
            }
            privateReasonModel = EmptyModel.REASON_FOR_SUPPLIER_CHOICE;
            newReasonName = EmptyModel.STRING;
        });

        deleteReason.addActionListener(e -> {
            if (!privateReasonModel.equals(EmptyModel.REASON_FOR_SUPPLIER_CHOICE) && ced.deleteEntry(parent, privateReasonModel.getReason()) && listener != null) {
                reasonBox.removeAllItems();
                reasonBox.addItem(EmptyModel.REASON_FOR_SUPPLIER_CHOICE);
                privateReasonModel.setDeleted();
                listener.persistModelEventOccurred(privateReasonModel);
            }
            privateReasonModel = EmptyModel.REASON_FOR_SUPPLIER_CHOICE;
            newReasonName = EmptyModel.STRING;
        });

        okButton.addActionListener(e -> setVisible(false));
    }

    public void setReasonData(java.util.List<ReasonForSupplierChoiceModel> prodDb) {
        Utils.setBoxData(reasonBox, prodDb, EmptyModel.REASON_FOR_SUPPLIER_CHOICE, false);
    }

    public void setListener(ReasonsDialogListener listener) {
        this.listener = listener;
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible && listener != null) {
            listener.getReasonsForSupplierChoice();
        }
        super.setVisible(visible);
    }

    private void layoutControls() {
        JPanel prodPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();

        int space = 5;
        Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
        Border prodBorder = BorderFactory.createTitledBorder(Labels.getProperty("reason"));

        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5));
        prodPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, prodBorder));

        Insets smallPadding = new Insets(1, 0, 1, 5);
        Insets noPadding = new Insets(1, 0, 1, 0);

        prodPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();


        ////// First row//////
        gc.gridy = 0;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        prodPanel.add(reasonBox, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        prodPanel.add(createReason, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        prodPanel.add(editReason, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        prodPanel.add(deleteReason, gc);

        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(okButton);

        setLayout(new BorderLayout());
        add(prodPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }
}
