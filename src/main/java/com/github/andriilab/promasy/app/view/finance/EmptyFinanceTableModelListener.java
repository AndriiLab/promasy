package com.github.andriilab.promasy.app.view.finance;

import com.github.andriilab.promasy.data.queries.finance.GetFinanceLeftAmountQuery;
import com.github.andriilab.promasy.app.components.AbstractEmptyListener;

import java.math.BigDecimal;

public class EmptyFinanceTableModelListener extends AbstractEmptyListener implements FinanceTableModelListener {
    @Override
    public BigDecimal getLeftAmount(GetFinanceLeftAmountQuery query) {
        logEmptyListener(FinanceTableModelListener.class);
        return BigDecimal.ZERO;
    }
}
