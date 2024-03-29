package com.github.andriilab.promasy.app.view.finance;

import com.github.andriilab.promasy.data.commands.ICommand;
import com.github.andriilab.promasy.data.queries.finance.GetFinanceUnassignedAmountQuery;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;

import java.math.BigDecimal;

/**
 * Listener for {@link CreateDepartmentFinancePanel}
 */
public interface CreateDepartmentFinancePanelListener {
    void persistModelEventOccurred(ICommand<FinanceDepartment> model);

    void loadDepartments();

    BigDecimal getUnassignedAmountEvent(GetFinanceUnassignedAmountQuery getFinanceUnassignedAmountQuery);
}
