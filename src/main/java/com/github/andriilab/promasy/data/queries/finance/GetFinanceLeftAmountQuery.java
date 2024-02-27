package com.github.andriilab.promasy.data.queries.finance;

import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.finance.entities.Finance;
import lombok.Getter;

@Getter
public class GetFinanceLeftAmountQuery {
    private final Finance model;
    private final BidType type;

    public GetFinanceLeftAmountQuery(Finance model, BidType type) {
        this.model = model;
        this.type = type;
    }
}
