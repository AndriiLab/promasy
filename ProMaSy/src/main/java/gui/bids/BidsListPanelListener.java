package gui.bids;

import model.models.BidModel;
import model.models.DepartmentModel;

/**
 * Listener for {@link BidsListPanel}
 */
public interface BidsListPanelListener {
    void persistModelEventOccurred(BidModel model);

    void selectAllBidsEventOccurred();

    void getBidsByDepartment(DepartmentModel selectedDepartmentModel);

    void getDepartmentFinances(DepartmentModel selectedDepartmentModel);
}
