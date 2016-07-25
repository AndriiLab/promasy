package main.java.gui.bids;

import main.java.model.BidModel;

/**
 * Created by laban on 04.06.2016.
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
