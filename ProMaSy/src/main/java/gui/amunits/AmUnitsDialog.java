package gui.amunits;

import gui.MainFrame;
import gui.commons.Labels;
import gui.components.AbstractCEDDialog;
import model.models.AmountUnitsModel;

/**
 * This Dialog Create, Modify, and Delete data of Amounts and Units entries in DB
 */

public class AmUnitsDialog extends AbstractCEDDialog<AmountUnitsModel, AmUnitsDialogListener> {

    public AmUnitsDialog(MainFrame parent) {
        super(AmountUnitsModel.class, parent, Labels.getProperty("amUnitsDialogSuper"), Labels.getProperty("amUnit_ced"), parent.getCreateBidDialog().getAmUnitsBox());
    }
}
