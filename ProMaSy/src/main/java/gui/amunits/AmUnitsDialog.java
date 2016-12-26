package main.java.gui.amunits;

import main.java.gui.AbstractComboCEDDialog;
import main.java.gui.Labels;
import main.java.gui.Utils;
import main.java.model.AmountUnitsModel;

import javax.swing.*;

/**
 * This Dialog Create, Modify, and Delete data of Amounts and Units entries in DB
 */

public class AmUnitsDialog extends AbstractComboCEDDialog<AmountUnitsModel> {
    private AmUnitsDialogListener listener;

    public AmUnitsDialog(JFrame parent) {
        super(parent, new AmountUnitsModel(), Labels.getProperty("amUnitsDialogSuper"), Labels.getProperty("amUnit_ced"));

        comboBox.addActionListener(e -> {
            Object item = ((JComboBox) e.getSource()).getSelectedItem();
            if (item instanceof AmountUnitsModel) {
                privateModel = (AmountUnitsModel) item;
                if (privateModel.equals(emptyModel)) {
                    editButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                } else {
                    editButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                }
            } else if (item instanceof String) {
                newName = (String) item;
            }
        });

        createButton.addActionListener(e -> {
            if (!newName.isEmpty() && privateModel.equals(emptyModel)) {
                AmountUnitsModel model = new AmountUnitsModel(newName);
                if (listener != null) {
                    listener.createEventOccurred(model);
                }
            } else if (newName.isEmpty()) {
                Utils.emptyFieldError(parent, Labels.getProperty("amUnit"));
            }
            clearDialog();
        });

        editButton.addActionListener(e -> {
            if (!newName.isEmpty() && !privateModel.equals(emptyModel)) {
                if (listener != null) {
                    privateModel.setAmUnitDesc(newName);
                    listener.editEventOccurred(privateModel);
                }
            } else if (newName.isEmpty()) {
                Utils.emptyFieldError(parent, Labels.getProperty("amUnit"));
            }
            clearDialog();
        });

        deleteButton.addActionListener(e -> {
            if (!privateModel.equals(emptyModel) && ced.deleteEntry(parent, privateModel.getAmUnitDesc()) && listener != null) {
                listener.deleteEventOccurred(privateModel);
            }
            clearDialog();
        });
    }

    public void setListener(AmUnitsDialogListener listener) {
        this.listener = listener;
    }
}
