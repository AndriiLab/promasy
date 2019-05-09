package com.github.andriilab.promasy.gui.finance;

import com.github.andriilab.promasy.app.queries.financepartment.GetFinanceDepartmentLeftAmountQuery;

import java.math.BigDecimal;

public interface DepartmentFinanceTableModelListener {
    BigDecimal getLeftAmount(GetFinanceDepartmentLeftAmountQuery query);
}
