package com.github.andriilab.promasy.app.queries.financepartment;

import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;
import lombok.Getter;

public class GetFinanceDepartmentLeftAmountQuery {
    @Getter private FinanceDepartment model;
    @Getter private BidType type;

    public GetFinanceDepartmentLeftAmountQuery(FinanceDepartment model, BidType type) {
        this.model = model;
        this.type = type;
    }
}
