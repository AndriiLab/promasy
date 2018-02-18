package com.github.andriilab.promasy.presentation.finance;

import com.github.andriilab.promasy.data.queries.finance.GetFinanceUnassignedAmountQuery;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;
import com.github.andriilab.promasy.presentation.components.AbstractEmptyListener;

import java.math.BigDecimal;

public class EmptyCreateDepartmentFinancePanelListener extends AbstractEmptyListener implements CreateDepartmentFinancePanelListener {
    @Override
    public void persistModelEventOccurred(FinanceDepartment model) {
        logEmptyListener(CreateDepartmentFinancePanelListener.class);
    }

    @Override
    public void loadDepartments() {
        logEmptyListener(CreateDepartmentFinancePanelListener.class);
    }

    @Override
    public BigDecimal getUnassignedAmountEvent(GetFinanceUnassignedAmountQuery getFinanceUnassignedAmountQuery) {
        logEmptyListener(CreateDepartmentFinancePanelListener.class);
        return BigDecimal.ZERO;
    }
}
