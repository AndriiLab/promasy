package com.github.andriilab.promasy.gui.finance;

import com.github.andriilab.promasy.app.queries.finance.GetFinanceLeftAmountQuery;

import java.math.BigDecimal;

public interface FinanceTableModelListener {
    BigDecimal getLeftAmount(GetFinanceLeftAmountQuery query);
}
