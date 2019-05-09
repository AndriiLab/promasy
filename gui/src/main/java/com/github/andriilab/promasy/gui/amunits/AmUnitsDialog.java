package com.github.andriilab.promasy.gui.amunits;

import com.github.andriilab.promasy.domain.bid.entities.AmountUnit;
import com.github.andriilab.promasy.gui.MainFrame;
import com.github.andriilab.promasy.gui.commons.Labels;
import com.github.andriilab.promasy.gui.components.dialogs.AbstractCEDDialog;

/**
 * This Dialog Create, Modify, and Delete persistence of Amounts and Units entries in DB
 */

public class AmUnitsDialog extends AbstractCEDDialog<AmountUnit, AmUnitsDialogListener> {

    public AmUnitsDialog(MainFrame parent) {
        super(AmountUnit.class, parent,
                Labels.getProperty("amUnitsDialogSuper"),
                Labels.getProperty("amUnit_ced"),
                parent.getCreateBidPanel().getAmUnitsBox());
    }
}
