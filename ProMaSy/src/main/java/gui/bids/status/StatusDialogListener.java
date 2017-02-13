package gui.bids.status;

import model.models.BidStatusModel;

/**
 * Listener for {@link StatusDialog}
 */
public interface StatusDialogListener {
    void persistModelEventOccurred(BidStatusModel bidModel);
}
