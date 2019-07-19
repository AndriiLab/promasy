package com.github.andriilab.promasy.app.view.finance;

import com.github.andriilab.promasy.data.commands.ICommand;
import com.github.andriilab.promasy.data.queries.finance.GetFinanceUnassignedAmountQuery;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;
import com.github.andriilab.promasy.app.components.AbstractEmptyListener;

import java.math.BigDecimal;

public class EmptyCreateDepartmentFinancePanelListener extends AbstractEmptyListener implements CreateDepartmentFinancePanelListener {
    @Override
    public void persistModelEventOccurred(ICommand<FinanceDepartment>  command) {
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
