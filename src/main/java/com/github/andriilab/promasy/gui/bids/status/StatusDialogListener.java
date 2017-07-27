package com.github.andriilab.promasy.gui.bids.status;

import com.github.andriilab.promasy.model.models.BidStatusModel;

/**
 * Listener for {@link StatusDialog}
 */
public interface StatusDialogListener {
    void persistModelEventOccurred(BidStatusModel bidModel);
}
