package com.github.andriilab.promasy.presentation.producer;

import com.github.andriilab.promasy.domain.item.entities.Producer;
import com.github.andriilab.promasy.presentation.MainFrame;
import com.github.andriilab.promasy.presentation.commons.Labels;
import com.github.andriilab.promasy.presentation.components.dialogs.AbstractCEDDialog;

/**
 * Class for CRUD of item Producers
 */
public class ProducerDialog extends AbstractCEDDialog<Producer, ProducerDialogListener> {

    public ProducerDialog(MainFrame parent) {
        super(Producer.class, parent, Labels.getProperty("prodDialogSuper"), Labels.getProperty("producer_ced"), parent.getCreateBidPanel().getProducerBox());
    }
}
