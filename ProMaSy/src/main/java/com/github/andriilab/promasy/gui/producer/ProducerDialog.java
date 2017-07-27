package com.github.andriilab.promasy.gui.producer;

import com.github.andriilab.promasy.gui.MainFrame;
import com.github.andriilab.promasy.gui.commons.Labels;
import com.github.andriilab.promasy.gui.components.AbstractCEDDialog;
import com.github.andriilab.promasy.model.models.ProducerModel;

/**
 * Class for CRUD of item Producers
 */
public class ProducerDialog extends AbstractCEDDialog<ProducerModel, ProducerDialogListener> {

    public ProducerDialog(MainFrame parent) {
        super(ProducerModel.class, parent, Labels.getProperty("prodDialogSuper"), Labels.getProperty("producer_ced"), parent.getCreateBidPanel().getProducerBox());
    }
}
