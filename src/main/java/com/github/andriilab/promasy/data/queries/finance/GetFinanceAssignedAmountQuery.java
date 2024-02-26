package com.github.andriilab.promasy.data.queries.finance;

import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.finance.entities.Finance;

public record GetFinanceAssignedAmountQuery(Finance model, BidType type) {
}
