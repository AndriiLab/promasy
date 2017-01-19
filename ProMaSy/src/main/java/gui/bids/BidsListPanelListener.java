package gui.bids;

import model.enums.BidType;
import model.models.BidModel;
import model.models.DepartmentModel;

/**
 * Listener for {@link BidsListPanel}
 */
public interface BidsListPanelListener {
    void persistModelEventOccurred(BidModel model, BidType type);

    void selectAllBidsEventOccurred(BidType type);

    void getBidsByDepartment(DepartmentModel selectedDepartmentModel, BidType type);

}
