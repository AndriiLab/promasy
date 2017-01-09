package gui.bids;

import model.models.BidModel;

/**
 * Listener for {@link BidsListPanel}
 */
public interface BidsListPanelListener {
    void persistModelEventOccurred(BidModel model);

    void selectAllBidsEventOccurred();
}
