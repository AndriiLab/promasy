package com.github.andriilab.promasy.presentation.bids;

import com.github.andriilab.promasy.domain.bid.entities.Bid;
import com.github.andriilab.promasy.domain.bid.entities.BidStatus;
import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;
import com.github.andriilab.promasy.domain.organization.entities.Department;
import com.github.andriilab.promasy.domain.organization.entities.Subdepartment;

/**
 * Listener for {@link BidsListPanel}
 */
public interface BidsListPanelListener {
    void persistModelEventOccurred(Bid model);

    void persistModelEventOccurred(BidStatus model);

    void selectAllBidsEventOccurred(BidType type);

    void getBidsByDepartment(BidType type, Department selectedDepartmentModel);

    void getBidsBySubdepartment(BidType selectedBidType, Subdepartment selectedSubdepartmentModel);

    void getBidsByFinanceDepartment(BidType selectedBidType, FinanceDepartment selectedFinanceDepartmentModel);

    void getAllData();
}
