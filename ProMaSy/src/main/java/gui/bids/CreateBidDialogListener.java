package gui.bids;

import model.enums.BidType;
import model.models.BidModel;

/**
 * Listener for {@link CreateBidDialog}
 */
public interface CreateBidDialogListener {
    void persistModelEventOccurred(BidModel model, BidType type);
    void getAllData();
}
