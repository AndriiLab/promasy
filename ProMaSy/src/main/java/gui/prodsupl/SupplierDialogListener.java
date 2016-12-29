package gui.prodsupl;

import model.SupplierModel;

/**
 * Listener for {@link SupplierDialog}
 */
public interface SupplierDialogListener {
    void createSuplEventOccurred(SupplierModel model);
    void editSuplEventOccurred(SupplierModel model);
    void deleteSuplEventOccurred(SupplierModel model);
}
