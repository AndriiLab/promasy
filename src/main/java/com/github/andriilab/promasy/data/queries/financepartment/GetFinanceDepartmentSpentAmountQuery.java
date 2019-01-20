package com.github.andriilab.promasy.data.queries.financepartment;

import com.github.andriilab.promasy.data.queries.IQuery;
import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;
import lombok.Getter;

public class GetFinanceDepartmentSpentAmountQuery implements IQuery {
    @Getter private FinanceDepartment model;
    @Getter private BidType type;

    public GetFinanceDepartmentSpentAmountQuery(FinanceDepartment model, BidType type) {
        this.model = model;
        this.type = type;
    }
}
