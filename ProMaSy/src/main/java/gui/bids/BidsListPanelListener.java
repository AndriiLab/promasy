package gui.bids;

import model.enums.BidType;
import model.models.*;

/**
 * Listener for {@link BidsListPanel}
 */
public interface BidsListPanelListener {
    void persistModelEventOccurred(BidModel model);

    void persistModelEventOccurred(BidStatusModel model);

    void selectAllBidsEventOccurred(BidType type);

    void getBidsByDepartment(BidType type, DepartmentModel selectedDepartmentModel);

    void getBidsBySubdepartment(BidType selectedBidType, SubdepartmentModel selectedSubdepartmentModel);

    void getBidsByFinanceDepartment(BidType selectedBidType, FinanceDepartmentModel selectedFinanceDepartmentModel);

    void getAllData();
}
