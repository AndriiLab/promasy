package main.java.gui.prodsupl;

import main.java.model.ProducerModel;

/**
 * Listener for {@link ReasonsDialog}
 */
public interface ProducerDialogListener {
    void createProdEventOccurred(ProducerModel model);
    void editProdEventOccurred(ProducerModel model);
    void deleteProdEventOccurred(ProducerModel model);
}
