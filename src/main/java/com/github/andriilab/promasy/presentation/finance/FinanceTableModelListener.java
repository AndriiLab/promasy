package com.github.andriilab.promasy.presentation.finance;

import com.github.andriilab.promasy.data.queries.finance.GetFinanceLeftAmountQuery;

import java.math.BigDecimal;

public interface FinanceTableModelListener {
    BigDecimal getLeftAmount(GetFinanceLeftAmountQuery query);
}
