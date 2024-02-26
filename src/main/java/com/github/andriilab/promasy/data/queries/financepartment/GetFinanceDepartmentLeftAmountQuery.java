package com.github.andriilab.promasy.data.queries.financepartment;

import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;

public record GetFinanceDepartmentLeftAmountQuery(FinanceDepartment model, BidType type) {
}
