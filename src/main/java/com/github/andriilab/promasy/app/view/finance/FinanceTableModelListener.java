package com.github.andriilab.promasy.app.view.finance;

import com.github.andriilab.promasy.data.queries.finance.GetFinanceLeftAmountQuery;

import java.math.BigDecimal;

public interface FinanceTableModelListener {
    BigDecimal getLeftAmount(GetFinanceLeftAmountQuery query);
}
