package com.github.andriilab.promasy.data.queries.financepartment;

import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;

public class GetFinanceDepartmentLeftAmountQuery {
    private FinanceDepartment model;
    private BidType type;

    public GetFinanceDepartmentLeftAmountQuery(FinanceDepartment model, BidType type) {
        this.model = model;
        this.type = type;
    }

    public FinanceDepartment getModel() {
        return model;
    }

    public BidType getType() {
        return type;
    }
}
