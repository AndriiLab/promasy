package com.github.andriilab.promasy.gui.bids;

import com.github.andriilab.promasy.model.models.BidModel;

/**
 * Listener for {@link CreateBidPanel}
 */
interface CreateBidPanelListener {
    void persistModelEventOccurred(BidModel model);
    void getAllData();
}
