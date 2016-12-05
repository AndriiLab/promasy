package main.java.gui.prodsupl;

import main.java.gui.Icons;
import main.java.gui.Labels;
import main.java.model.ReasonForSupplierChoiceModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by laban on 02.12.2016.
 */
public class ReasonsDialog extends JDialog {
    private final ReasonForSupplierChoiceModel emptyReasonsModel = new ReasonForSupplierChoiceModel();
    private JButton okButton;
    private JButton createReason;
    private JButton editReason;
    private JButton deleteReason;
    private JComboBox<ReasonForSupplierChoiceModel> reasonBox;
    private ReasonsDialogListener listener;
    private ReasonForSupplierChoiceModel privateReasonModel;
    private String newReasonName;

    public ReasonsDialog(JFrame parent) {
        super(parent, Labels.getProperty("reasonForSupplierChoice"), false);
        setSize(280, 150);
        setLocationRelativeTo(parent);

        Dimension buttonDim = new Dimension(25, 25);
        Dimension comboBoxDim = new Dimension(150, 25);

        privateReasonModel = emptyReasonsModel;

        DefaultComboBoxModel<ReasonForSupplierChoiceModel> prodModel = new DefaultComboBoxModel<>();
        reasonBox = new JComboBox<>(prodModel);
        reasonBox.addItem(emptyReasonsModel);
        reasonBox.setPreferredSize(comboBoxDim);
        reasonBox.setEditable(true);

        createReason = new JButton();
        createReason.setToolTipText(Labels.getProperty("addReasonForSupplierChoice"));
        createReason.setIcon(Icons.CREATE);
        createReason.setPreferredSize(buttonDim);
        createReason.setEnabled(true);
        editReason = new JButton();
        editReason.setToolTipText(Labels.getProperty("editReasonForSupplierChoice"));
        editReason.setIcon(Icons.EDIT);
        editReason.setPreferredSize(buttonDim);
        editReason.setEnabled(true);
        deleteReason = new JButton();
        deleteReason.setToolTipText(Labels.getProperty("deleteReasonForSupplierChoice"));
        deleteReason.setIcon(Icons.DELETE);
        deleteReason.setPreferredSize(buttonDim);
        deleteReason.setEnabled(true);

        okButton = new JButton(Labels.getProperty("closeBtn"));

        layoutControls();

        reasonBox.addActionListener(e -> {
            Object item = reasonBox.getSelectedItem();
            if (item instanceof ReasonForSupplierChoiceModel && !item.equals(emptyReasonsModel)) {
                privateReasonModel = (ReasonForSupplierChoiceModel) item;
            } else if (item instanceof String && !item.equals("")) {
                newReasonName = (String) item;
            }
        });

        createReason.addActionListener(e -> {
            if (!newReasonName.equals("")) {
                ReasonForSupplierChoiceModel model = new ReasonForSupplierChoiceModel(newReasonName);
                if (listener != null) {
                    reasonBox.removeAllItems();
                    reasonBox.addItem(emptyReasonsModel);
                    listener.createReasonEventOccurred(model);
                }
            }
            privateReasonModel = emptyReasonsModel;
            newReasonName = "";
        });

        editReason.addActionListener(e -> {
            if (newReasonName != null && !newReasonName.equals("") && !privateReasonModel.equals(emptyReasonsModel)) {
                if (listener != null) {
                    reasonBox.removeAllItems();
                    reasonBox.addItem(emptyReasonsModel);
                    privateReasonModel.setReason(newReasonName);
                    listener.editReasonEventOccurred(privateReasonModel);
                }
            }
            privateReasonModel = emptyReasonsModel;
            newReasonName = "";
        });

        deleteReason.addActionListener(e -> {
            if (!privateReasonModel.equals(emptyReasonsModel) && listener != null) {
                reasonBox.removeAllItems();
                reasonBox.addItem(emptyReasonsModel);
                listener.deleteReasonEventOccurred(privateReasonModel);
            }
            privateReasonModel = emptyReasonsModel;
            newReasonName = "";
        });

        okButton.addActionListener(e -> setVisible(false));
    }

    public void setReasonData(java.util.List<ReasonForSupplierChoiceModel> prodDb) {
        for (ReasonForSupplierChoiceModel prodModel : prodDb) {
            reasonBox.addItem(prodModel);
        }
    }

    public void setListener(ReasonsDialogListener listener) {
        this.listener = listener;
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
