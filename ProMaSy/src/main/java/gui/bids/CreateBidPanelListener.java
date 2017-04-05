package gui.bids;

import model.models.BidModel;

/**
 * Listener for {@link CreateBidPanel}
 */
public interface CreateBidPanelListener {
    void persistModelEventOccurred(BidModel model);
    void getAllData();
}
