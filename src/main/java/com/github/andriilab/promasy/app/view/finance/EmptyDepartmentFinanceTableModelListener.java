package com.github.andriilab.promasy.app.view.finance;

import com.github.andriilab.promasy.data.queries.financepartment.GetFinanceDepartmentLeftAmountQuery;
import com.github.andriilab.promasy.app.components.AbstractEmptyListener;

import java.math.BigDecimal;

public class EmptyDepartmentFinanceTableModelListener extends AbstractEmptyListener implements DepartmentFinanceTableModelListener {
    @Override
    public BigDecimal getLeftAmount(GetFinanceDepartmentLeftAmountQuery query) {
        logEmptyListener(DepartmentFinanceTableModelListener.class);
        return BigDecimal.ZERO;
    }
}
