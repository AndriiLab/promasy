package gui.prodsupl;

import model.ProducerModel;
import model.SupplierModel;

/**
 * Created by A on 27.04.2016.
 */
public interface ProdSuplDialogListener {

    void createProdEventOccurred(ProducerModel model);

    void editProdEventOccurred(ProducerModel model);

    void deleteProdEventOccurred(ProducerModel model);

    void createSuplEventOccurred(SupplierModel model);

    void editSuplEventOccurred(SupplierModel model);

    void deleteSuplEventOccurred(SupplierModel model);
}
