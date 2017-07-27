package com.github.andriilab.promasy.gui.producer;

import com.github.andriilab.promasy.gui.MainFrame;
import com.github.andriilab.promasy.gui.commons.Labels;
import com.github.andriilab.promasy.gui.components.AbstractCEDDialog;
import com.github.andriilab.promasy.model.models.ReasonForSupplierChoiceModel;

/**
 * Class for CRUD of item {@link ReasonForSupplierChoiceModel}
 */
public class ReasonsDialog extends AbstractCEDDialog<ReasonForSupplierChoiceModel, ReasonsDialogListener> {

    public ReasonsDialog(MainFrame parent) {
        super(ReasonForSupplierChoiceModel.class, parent, Labels.getProperty("reasonForSupplierChoice"), Labels.getProperty("reason_ced"), parent.getCreateBidPanel().getReasonForSupplierChoiceBox());
    }
}
