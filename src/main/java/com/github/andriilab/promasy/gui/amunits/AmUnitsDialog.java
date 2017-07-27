package com.github.andriilab.promasy.gui.amunits;

import com.github.andriilab.promasy.gui.MainFrame;
import com.github.andriilab.promasy.gui.commons.Labels;
import com.github.andriilab.promasy.gui.components.AbstractCEDDialog;
import com.github.andriilab.promasy.model.models.AmountUnitsModel;

/**
 * This Dialog Create, Modify, and Delete data of Amounts and Units entries in DB
 */

public class AmUnitsDialog extends AbstractCEDDialog<AmountUnitsModel, AmUnitsDialogListener> {

    public AmUnitsDialog(MainFrame parent) {
        super(AmountUnitsModel.class, parent, Labels.getProperty("amUnitsDialogSuper"), Labels.getProperty("amUnit_ced"), parent.getCreateBidPanel().getAmUnitsBox());
    }
}
