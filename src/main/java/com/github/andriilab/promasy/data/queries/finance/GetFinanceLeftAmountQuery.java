package com.github.andriilab.promasy.data.queries.finance;

import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.finance.entities.Finance;
import lombok.Getter;

public class GetFinanceLeftAmountQuery {
    private @Getter
    Finance model;
    private @Getter
    BidType type;

    public GetFinanceLeftAmountQuery(Finance model, BidType type) {
        this.model = model;
        this.type = type;
    }
}
