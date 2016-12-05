package main.java.gui.prodsupl;

import main.java.model.ReasonForSupplierChoiceModel;

/**
 * Created by laban on 02.12.2016.
 */
public interface ReasonsDialogListener {
    void createReasonEventOccurred(ReasonForSupplierChoiceModel model);

    void editReasonEventOccurred(ReasonForSupplierChoiceModel model);

    void deleteReasonEventOccurred(ReasonForSupplierChoiceModel model);
}
