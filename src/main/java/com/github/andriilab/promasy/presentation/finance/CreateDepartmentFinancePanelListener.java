package com.github.andriilab.promasy.presentation.finance;

import com.github.andriilab.promasy.data.queries.finance.GetFinanceUnassignedAmountQuery;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;

import java.math.BigDecimal;

/**
 * Listener for {@link CreateDepartmentFinancePanel}
 */
interface CreateDepartmentFinancePanelListener {
    void persistModelEventOccurred(FinanceDepartment model);

    void loadDepartments();

    BigDecimal getUnassignedAmountEvent(GetFinanceUnassignedAmountQuery getFinanceUnassignedAmountQuery);
}
