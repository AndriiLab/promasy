package gui.prodsupl;

import model.ReasonForSupplierChoiceModel;

/**
 * Listener for {@link ReasonsDialog}
 */
public interface ReasonsDialogListener {
    void createReasonEventOccurred(ReasonForSupplierChoiceModel model);

    void editReasonEventOccurred(ReasonForSupplierChoiceModel model);

    void deleteReasonEventOccurred(ReasonForSupplierChoiceModel model);
}
