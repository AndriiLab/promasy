package main.java.gui.prodsupl;

import main.java.gui.AbstractComboCEDDialog;
import main.java.gui.Labels;
import main.java.gui.Utils;
import main.java.model.ProducerModel;

import javax.swing.*;

/**
 * Class for CRUD of item Producers
 */
public class ProducerDialog extends AbstractComboCEDDialog<ProducerModel> {
    private ProducerDialogListener listener;

    public ProducerDialog(JFrame parent) {
        super(parent, new ProducerModel(), Labels.getProperty("prodDialogSuper"), Labels.getProperty("producer_ced"));

        comboBox.addActionListener(e -> {
            Object item = comboBox.getSelectedItem();
            if (item instanceof ProducerModel && !item.equals(emptyModel)) {
                privateModel = (ProducerModel) item;
                if (privateModel.equals(emptyModel)) {
                    editButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                } else {
                    editButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                }
            } else if (item instanceof String && !item.equals("")) {
                newName = (String) item;
            }
        });

        createButton.addActionListener(e -> {
            if (!newName.isEmpty()) {
                ProducerModel model = new ProducerModel(newName);
                if (listener != null) {
                    listener.createProdEventOccurred(model);
                }
            } else {
                Utils.emptyFieldError(parent, Labels.getProperty("producer"));
            }
            clearDialog();
        });

        editButton.addActionListener(e -> {
            if (!newName.isEmpty() && !privateModel.equals(emptyModel)) {
                if (listener != null) {
                    privateModel.setBrandName(newName);
                    listener.editProdEventOccurred(privateModel);
                }
            } else {
                Utils.emptyFieldError(parent, Labels.getProperty("producer"));
            }
            clearDialog();
        });

        deleteButton.addActionListener(e -> {
            if (!privateModel.equals(emptyModel) && ced.deleteEntry(parent, privateModel.getBrandName()) && listener != null) {
                listener.deleteProdEventOccurred(privateModel);
            }
            clearDialog();
        });
    }

    public void setListener(ProducerDialogListener listener) {
        this.listener = listener;
    }

}
