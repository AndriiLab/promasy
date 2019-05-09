package com.github.andriilab.promasy.gui.bids.status;

import com.github.andriilab.promasy.domain.bid.entities.BidStatus;

/**
 * Listener for {@link StatusDialog}
 */
public interface StatusDialogListener {
    void persistModelEventOccurred(BidStatus bidModel);
}
