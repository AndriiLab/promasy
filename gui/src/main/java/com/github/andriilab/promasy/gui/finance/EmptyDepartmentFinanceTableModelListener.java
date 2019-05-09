package com.github.andriilab.promasy.gui.finance;

import com.github.andriilab.promasy.app.queries.financepartment.GetFinanceDepartmentLeftAmountQuery;
import com.github.andriilab.promasy.gui.components.AbstractEmptyListener;

import java.math.BigDecimal;

public class EmptyDepartmentFinanceTableModelListener extends AbstractEmptyListener implements DepartmentFinanceTableModelListener {
    @Override
    public BigDecimal getLeftAmount(GetFinanceDepartmentLeftAmountQuery query) {
        logEmptyListener(DepartmentFinanceTableModelListener.class);
        return BigDecimal.ZERO;
    }
}
