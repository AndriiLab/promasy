package gui.prodsupl;

import gui.AbstractComboCEDDialog;
import gui.Labels;
import gui.Utils;
import model.models.ProducerModel;

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
                    Utils.setCreated(model);
                    listener.persistModelEventOccurred(model);
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
                    Utils.setUpdated(privateModel);
                    listener.persistModelEventOccurred(privateModel);
                }
            } else {
                Utils.emptyFieldError(parent, Labels.getProperty("producer"));
            }
            clearDialog();
        });

        deleteButton.addActionListener(e -> {
            if (!privateModel.equals(emptyModel) && ced.deleteEntry(parent, privateModel.getBrandName()) && listener != null) {
                Utils.setDeleted(privateModel);
                listener.persistModelEventOccurred(privateModel);
            }
            clearDialog();
        });
    }

    public void setListener(ProducerDialogListener listener) {
        this.listener = listener;
    }

}
