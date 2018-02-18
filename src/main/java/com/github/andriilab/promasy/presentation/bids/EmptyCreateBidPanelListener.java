package com.github.andriilab.promasy.presentation.bids;

import com.github.andriilab.promasy.data.queries.financepartment.GetFinanceDepartmentLeftAmountQuery;
import com.github.andriilab.promasy.domain.bid.entities.Bid;
import com.github.andriilab.promasy.presentation.components.AbstractEmptyListener;

import java.math.BigDecimal;

public class EmptyCreateBidPanelListener extends AbstractEmptyListener implements CreateBidPanelListener {
    @Override
    public void persistModelEventOccurred(Bid model) {
        logEmptyListener(CreateBidPanelListener.class);
    }

    @Override
    public void getAllData() {
        logEmptyListener(CreateBidPanelListener.class);
    }

    @Override
    public BigDecimal getLeftAmount(GetFinanceDepartmentLeftAmountQuery query) {
        logEmptyListener(CreateBidPanelListener.class);
        return BigDecimal.ZERO;
    }
}
