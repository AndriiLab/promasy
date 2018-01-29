package com.github.andriilab.promasy.presentation.bids;

import com.github.andriilab.promasy.data.queries.financepartment.GetFinanceDepartmentLeftAmountQuery;
import com.github.andriilab.promasy.domain.bid.entities.Bid;

import java.math.BigDecimal;

/**
 * Listener for {@link CreateBidPanel}
 */
interface CreateBidPanelListener {
    void persistModelEventOccurred(Bid model);
    void getAllData();

    BigDecimal getLeftAmount(GetFinanceDepartmentLeftAmountQuery query);
}
