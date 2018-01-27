package com.github.andriilab.promasy.presentation.producer;

import com.github.andriilab.promasy.domain.bid.entities.ReasonForSupplierChoice;
import com.github.andriilab.promasy.presentation.MainFrame;
import com.github.andriilab.promasy.presentation.commons.Labels;
import com.github.andriilab.promasy.presentation.components.AbstractCEDDialog;

/**
 * Class for CRUD of item {@link ReasonForSupplierChoice}
 */
public class ReasonsDialog extends AbstractCEDDialog<ReasonForSupplierChoice, ReasonsDialogListener> {

    public ReasonsDialog(MainFrame parent) {
        super(ReasonForSupplierChoice.class, parent, Labels.getProperty("reasonForSupplierChoice"), Labels.getProperty("reason_ced"), parent.getCreateBidPanel().getReasonForSupplierChoiceBox());
    }
}
