package com.github.andriilab.promasy.gui.producer;

import com.github.andriilab.promasy.domain.bid.entities.ReasonForSupplierChoice;
import com.github.andriilab.promasy.gui.MainFrame;
import com.github.andriilab.promasy.gui.commons.Labels;
import com.github.andriilab.promasy.gui.components.dialogs.AbstractCEDDialog;

/**
 * Class for CRUD of item {@link ReasonForSupplierChoice}
 */
public class ReasonsDialog extends AbstractCEDDialog<ReasonForSupplierChoice, ReasonsDialogListener> {

    public ReasonsDialog(MainFrame parent) {
        super(ReasonForSupplierChoice.class, parent, Labels.getProperty("reasonForSupplierChoice"), Labels.getProperty("reason_ced"), parent.getCreateBidPanel().getReasonForSupplierChoiceBox());
    }
}
