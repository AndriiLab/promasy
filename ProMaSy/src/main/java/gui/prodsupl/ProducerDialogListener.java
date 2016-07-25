package main.java.gui.prodsupl;

import main.java.model.ProducerModel;

/**
 * Created by laban on 04.06.2016.
 */
public interface ProducerDialogListener {
    void createProdEventOccurred(ProducerModel model);
    void editProdEventOccurred(ProducerModel model);
    void deleteProdEventOccurred(ProducerModel model);
}
