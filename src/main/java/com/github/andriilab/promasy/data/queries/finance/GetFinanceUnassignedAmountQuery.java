package com.github.andriilab.promasy.data.queries.finance;

import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.finance.entities.Finance;

public class GetFinanceUnassignedAmountQuery {
    private Finance model;
    private BidType type;

    public GetFinanceUnassignedAmountQuery(Finance model, BidType type) {
        this.model = model;
        this.type = type;
    }

    public Finance getModel() {
        return model;
    }

    public BidType getType() {
        return type;
    }
}
