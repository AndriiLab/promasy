package gui.bids;

import model.enums.BidType;
import model.models.BidModel;
import model.models.DepartmentModel;
import model.models.FinanceDepartmentModel;
import model.models.SubdepartmentModel;

/**
 * Listener for {@link BidsListPanel}
 */
public interface BidsListPanelListener {
    void persistModelEventOccurred(BidModel model);

    void selectAllBidsEventOccurred(BidType type);

    void getBidsByDepartment(BidType type, DepartmentModel selectedDepartmentModel);

    void getBidsBySubdepartment(BidType selectedBidType, SubdepartmentModel selectedSubdepartmentModel);

    void getBidsByFinanceDepartment(BidType selectedBidType, FinanceDepartmentModel selectedFinanceDepartmentModel);

    void getAllData();
}
