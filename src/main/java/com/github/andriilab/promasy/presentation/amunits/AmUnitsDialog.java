package com.github.andriilab.promasy.presentation.amunits;

import com.github.andriilab.promasy.domain.bid.entities.AmountUnit;
import com.github.andriilab.promasy.presentation.MainFrame;
import com.github.andriilab.promasy.presentation.commons.Labels;
import com.github.andriilab.promasy.presentation.components.dialogs.AbstractCEDDialog;

/**
 * This Dialog Create, Modify, and Delete data of Amounts and Units entries in DB
 */

public class AmUnitsDialog extends AbstractCEDDialog<AmountUnit, AmUnitsDialogListener> {

    public AmUnitsDialog(MainFrame parent) {
        super(AmountUnit.class, parent,
                Labels.getProperty("amUnitsDialogSuper"),
                Labels.getProperty("amUnit_ced"),
                parent.getCreateBidPanel().getAmUnitsBox());
    }
}
