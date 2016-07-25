package main.java.gui.prodsupl;

import main.java.model.SupplierModel;

/**
 * Created by laban on 04.06.2016.
 */
public interface SupplierDialogListener {
    void createSuplEventOccurred(SupplierModel model);
    void editSuplEventOccurred(SupplierModel model);
    void deleteSuplEventOccurred(SupplierModel model);
}
