package gui.bids;

import model.BidModel;

/**
 * Listener for {@link CreateBidDialog}
 */
public interface CreateBidDialogListener {
    void departmentSelectionEventOccurred(long depId);
    void bidCreateEventOccurred(BidModel model);
    void bidCreateEventOccurred(BidModel model, long departmentId);
    void bidCreateEventOccurred(BidModel model, long departmentId, long orderId);
    void bidEditEventOccurred(BidModel model);
    void bidEditEventOccurred(BidModel model, long departmentId);
    void bidEditEventOccurred(BidModel model, long departmentId, long orderId);
}
