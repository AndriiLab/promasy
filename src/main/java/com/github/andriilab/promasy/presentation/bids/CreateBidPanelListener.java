package com.github.andriilab.promasy.presentation.bids;

import com.github.andriilab.promasy.domain.bid.entities.Bid;

/**
 * Listener for {@link CreateBidPanel}
 */
interface CreateBidPanelListener {
    void persistModelEventOccurred(Bid model);
    void getAllData();
}
