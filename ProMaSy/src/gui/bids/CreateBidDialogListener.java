package gui.bids;

import model.BidModel;

/**
 * Created by laban on 04.06.2016.
 */
public interface CreateBidDialogListener {
    void departmentSelectionEventOccurred(long depId);
    void bidCreateEventOccurred(BidModel model);
    void bidEditEventOccurred(BidModel model);
}
