package gui.bids.status;

import model.models.BidModel;

/**
 * Listener for {@link StatusDialog}
 */
public interface StatusDialogListener {
    void persistModelEventOccurred(BidModel bidModel);
}
