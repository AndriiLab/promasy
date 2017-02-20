package gui.prodsupl;

import gui.MainFrame;
import gui.commons.Labels;
import gui.components.AbstractCEDDialog;
import model.models.ReasonForSupplierChoiceModel;

/**
 * Class for CRUD of item {@link ReasonForSupplierChoiceModel}
 */
public class ReasonsDialog extends AbstractCEDDialog<ReasonForSupplierChoiceModel, ReasonsDialogListener> {

    public ReasonsDialog(MainFrame parent) {
        super(ReasonForSupplierChoiceModel.class, parent, Labels.getProperty("reasonForSupplierChoice"), Labels.getProperty("reason_ced"), parent.getCreateBidDialog().getReasonForSupplierChoiceBox());
    }
}
