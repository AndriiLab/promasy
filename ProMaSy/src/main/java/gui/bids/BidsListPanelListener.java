package main.java.gui.bids;

import main.java.model.BidModel;
import main.java.model.StatusModel;

/**
 * Listener for {@link BidsListPanel}
 */
public interface BidsListPanelListener {
    void departmentSelectionEventOccurred(long departmentId);
    void financeDepartmentSelectionEventOccurred(long departmentId, long orderId);
    void bidDeleteEventOccurred(BidModel model);
    void bidDeleteEventOccurred(BidModel model, long departmentId);
    void bidDeleteEventOccurred(BidModel model, long departmentId, long orderId);
    void selectAllDepartmentsBidsEventOccurred();
    void selectAllOrdersBidsEventOccurred(long departmentId);

    void showBidStatusesEventOccured(long modelId);

    void statusChangeEventOccured(StatusModel model);

    void statusChangeEventOccured(StatusModel model, long departmentId);

    void statusChangeEventOccured(StatusModel model, long departmentId, long orderId);
}
