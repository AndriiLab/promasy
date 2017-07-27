package com.github.andriilab.promasy.gui.bids;

import com.github.andriilab.promasy.model.enums.BidType;
import com.github.andriilab.promasy.model.models.*;

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