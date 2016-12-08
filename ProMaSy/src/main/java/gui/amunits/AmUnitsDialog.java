package main.java.gui.amunits;

import main.java.gui.CrEdDelButtons;
import main.java.gui.Labels;
import main.java.gui.Utils;
import main.java.model.AmountUnitsModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * This Dialog Create, Modify, and Delete data of Amounts and Units entries in DB
 */
public class AmUnitsDialog extends JDialog {
    private final AmountUnitsModel emptyModel = new AmountUnitsModel();
    private JButton closeButton;
    private JButton createAmUnitButton;
    private JButton editAmUnitButton;
    private JButton deleteAmUnitButton;
    private JComboBox<AmountUnitsModel> amUnitBox;
    private AmUnitsDialogListener listener;
    private AmountUnitsModel privateModel;
    private String newName;

    public AmUnitsDialog(JFrame parent) {
        super(parent, Labels.getProperty("amUnitsDialogSuper"), true);
        setSize(271, 128);
        setResizable(false);
        setLocationRelativeTo(parent);

        privateModel = emptyModel;
        newName = "";

        CrEdDelButtons ced = new CrEdDelButtons(Labels.getProperty("amUnit_ced"));
        createAmUnitButton = ced.getCreateButton();
        editAmUnitButton = ced.getEditButton();
        deleteAmUnitButton = ced.getDeleteButton();
        editAmUnitButton.setEnabled(false);
        deleteAmUnitButton.setEnabled(false);

        closeButton = new JButton(Labels.getProperty("closeBtn"));

        Dimension comboBoxDim = new Dimension(150, 25);

        DefaultComboBoxModel<AmountUnitsModel> amUnitModel = new DefaultComboBoxModel<>();

        amUnitBox = new JComboBox<>(amUnitModel);
        amUnitBox.addItem(emptyModel);
        amUnitBox.setPreferredSize(comboBoxDim);
        amUnitBox.setEditable(true);
        amUnitBox.addActionListener(e -> {
            Object item = ((JComboBox) e.getSource()).getSelectedItem();
            if (item instanceof AmountUnitsModel) {
                privateModel = (AmountUnitsModel) item;
                if (privateModel.equals(emptyModel)) {
                    createAmUnitButton.setEnabled(true);
                    editAmUnitButton.setEnabled(false);
                    deleteAmUnitButton.setEnabled(false);
                } else {
                    createAmUnitButton.setEnabled(false);
                    editAmUnitButton.setEnabled(true);
                    deleteAmUnitButton.setEnabled(true);
                }
            } else if (item instanceof String) {
                newName = (String) item;
            }
        });

        layoutControls();

        createAmUnitButton.addActionListener(e -> {
            if (!newName.isEmpty() && privateModel.equals(emptyModel)) {
                AmountUnitsModel model = new AmountUnitsModel(newName);
                if (listener != null) {
                    amUnitBox.removeAllItems();
                    amUnitBox.addItem(emptyModel);
                    listener.createEventOccurred(model);
                }
            } else if (newName.isEmpty()) {
                Utils.emptyFieldError(parent, Labels.getProperty("amUnit"));
            }
            clearDialog();
        });

        editAmUnitButton.addActionListener(e -> {
            if (!newName.isEmpty() && !privateModel.equals(emptyModel)) {
                if (listener != null) {
                    amUnitBox.removeAllItems();
                    amUnitBox.addItem(emptyModel);
                    privateModel.setAmUnitDesc(newName);
                    listener.editEventOccurred(privateModel);
                }
            } else if (newName.isEmpty()) {
                Utils.emptyFieldError(parent, Labels.getProperty("amUnit"));
            }
            clearDialog();
        });

        deleteAmUnitButton.addActionListener(e -> {
            if (!privateModel.equals(emptyModel) && ced.deleteEntry(parent, privateModel.getAmUnitDesc()) && listener != null) {
                amUnitBox.removeAllItems();
                amUnitBox.addItem(emptyModel);
                listener.deleteEventOccurred(privateModel);
            }
            clearDialog();
        });

        closeButton.addActionListener(e -> setVisible(false));
    }

    private void clearDialog() {
        privateModel = emptyModel;
        newName = "";
    }

    public void setData(java.util.List<AmountUnitsModel> amUnitDb) {
        for (AmountUnitsModel amUnitModel : amUnitDb) {
            amUnitBox.addItem(amUnitModel);
        }
    }

    public void setListener(AmUnitsDialogListener listener) {
        this.listener = listener;
    }

    private void layoutControls() {
        JPanel amUnitPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();

        int space = 5;
        Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
        Border instituteBorder = BorderFactory.createEtchedBorder();

        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5));
        amUnitPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, instituteBorder));

        Insets smallPadding = new Insets(1, 0, 1, 5);
        Insets noPadding = new Insets(1, 0, 1, 0);

        amUnitPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();


        ////// First row//////
        gc.gridy = 0;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        amUnitPanel.add(amUnitBox, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        amUnitPanel.add(createAmUnitButton, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        amUnitPanel.add(editAmUnitButton, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        amUnitPanel.add(deleteAmUnitButton, gc);

        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(closeButton);

        setLayout(new BorderLayout());
        add(amUnitPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }
}
