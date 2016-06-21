package gui.bids;

import model.BidModel;

/**
 * Created by laban on 26.05.2016.
 */
public interface BidsListPanelListener {
    void departmentSelectionEventOccurred(long departmentId);
    void financeDepartmentSelectionEventOccurred(long departmentId, long orderId);
    void bidDeleteEventOccurred(BidModel model);
    void bidDeleteEventOccurred(BidModel model, long departmentId);
    void bidDeleteEventOccurred(BidModel model, long departmentId, long orderId);
    void selectAllDepartmentsBidsEventOccurred();
    void selectAllOrdersBidsEventOccurred(long departmentId);
}
