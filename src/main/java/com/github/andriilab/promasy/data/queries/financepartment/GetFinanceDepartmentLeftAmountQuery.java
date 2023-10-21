package com.github.andriilab.promasy.data.queries.financepartment;

import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;
import lombok.Getter;

@Getter
public class GetFinanceDepartmentLeftAmountQuery {
    private final FinanceDepartment model;
    private final BidType type;

    public GetFinanceDepartmentLeftAmountQuery(FinanceDepartment model, BidType type) {
        this.model = model;
        this.type = type;
    }
}
