package gui.prodsupl;

import model.models.ReasonForSupplierChoiceModel;

/**
 * Listener for {@link ReasonsDialog}
 */
public interface ReasonsDialogListener {

    void persistModelEventOccurred(ReasonForSupplierChoiceModel model);

    void getReasonsForSupplierChoice();
}
