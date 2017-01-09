package gui.bids;

import model.models.BidModel;

/**
 * Listener for {@link CreateBidDialog}
 */
public interface CreateBidDialogListener {
    void persistModelEventOccurred(BidModel createdBidModel);
}
