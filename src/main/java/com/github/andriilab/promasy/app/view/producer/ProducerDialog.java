package com.github.andriilab.promasy.app.view.producer;

import com.github.andriilab.promasy.domain.item.entities.Producer;
import com.github.andriilab.promasy.app.view.MainFrame;
import com.github.andriilab.promasy.app.commons.Labels;
import com.github.andriilab.promasy.app.components.dialogs.AbstractCEDDialog;

/**
 * Class for CRUD of item Producers
 */
public class ProducerDialog extends AbstractCEDDialog<Producer, ProducerDialogListener> {

    public ProducerDialog(MainFrame parent) {
        super(Producer.class, parent, Labels.getProperty("prodDialogSuper"), Labels.getProperty("producer_ced"), parent.getCreateBidPanel().getProducerBox());
    }
}
