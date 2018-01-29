package com.github.andriilab.promasy.data.queries.financepartment;

import com.github.andriilab.promasy.data.queries.IQuery;
import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;

public class GetFinanceDepartmentSpentAmountQuery implements IQuery {
    private FinanceDepartment model;
    private BidType type;

    public GetFinanceDepartmentSpentAmountQuery(FinanceDepartment model, BidType type) {
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
