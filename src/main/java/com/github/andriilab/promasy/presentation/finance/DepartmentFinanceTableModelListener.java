package com.github.andriilab.promasy.presentation.finance;

import com.github.andriilab.promasy.data.queries.financepartment.GetFinanceDepartmentLeftAmountQuery;

import java.math.BigDecimal;

public interface DepartmentFinanceTableModelListener {
    BigDecimal getLeftAmount(GetFinanceDepartmentLeftAmountQuery query);
}
