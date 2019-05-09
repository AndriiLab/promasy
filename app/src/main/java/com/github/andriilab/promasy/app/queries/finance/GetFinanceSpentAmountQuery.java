package com.github.andriilab.promasy.app.queries.finance;

import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.finance.entities.Finance;
import lombok.Getter;

public class GetFinanceSpentAmountQuery {
    @Getter private Finance model;
    @Getter private BidType type;

    public GetFinanceSpentAmountQuery(Finance model, BidType type) {
        this.model = model;
        this.type = type;
    }
}
