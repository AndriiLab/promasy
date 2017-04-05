package gui.prodsupl;

import gui.MainFrame;
import gui.commons.Labels;
import gui.components.AbstractCEDDialog;
import model.models.ProducerModel;

/**
 * Class for CRUD of item Producers
 */
public class ProducerDialog extends AbstractCEDDialog<ProducerModel, ProducerDialogListener> {

    public ProducerDialog(MainFrame parent) {
        super(ProducerModel.class, parent, Labels.getProperty("prodDialogSuper"), Labels.getProperty("producer_ced"), parent.getCreateBidPanel().getProducerBox());
    }
}
