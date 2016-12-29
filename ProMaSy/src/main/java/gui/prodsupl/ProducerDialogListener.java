package gui.prodsupl;

import model.ProducerModel;

/**
 * Listener for {@link ReasonsDialog}
 */
public interface ProducerDialogListener {
    void createProdEventOccurred(ProducerModel model);
    void editProdEventOccurred(ProducerModel model);
    void deleteProdEventOccurred(ProducerModel model);
}
