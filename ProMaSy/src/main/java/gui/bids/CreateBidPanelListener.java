package gui.bids;

import model.models.BidModel;

/**
 * Listener for {@link CreateBidPanel}
 */
interface CreateBidPanelListener {
    void persistModelEventOccurred(BidModel model);
    void getAllData();
}
