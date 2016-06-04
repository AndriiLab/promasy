package gui.bids;

import model.DepartmentModel;

/**
 * Created by laban on 26.05.2016.
 */
public interface BidsListPanelListener {

    void departmentSelectionEventOccurred(long departmentId);

    void financeDepartmentSelectionEventOccurred(long departmentId, long orderId);
}
