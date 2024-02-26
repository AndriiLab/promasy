package com.github.andriilab.promasy.app.view.finance;

import com.github.andriilab.promasy.data.commands.ICommand;
import com.github.andriilab.promasy.data.queries.finance.GetFinanceLeftAmountQuery;
import com.github.andriilab.promasy.data.queries.finance.GetFinanceUnassignedAmountQuery;
import com.github.andriilab.promasy.data.queries.finance.GetFinancesQuery;
import com.github.andriilab.promasy.data.queries.financepartment.GetFinanceDepartmentLeftAmountQuery;
import com.github.andriilab.promasy.domain.IEntity;

import java.math.BigDecimal;

/**
 * Listener for {@link FinancePanel}
 */
public interface FinancePanelListener {
    <T extends IEntity> void persistModelEventOccurred(ICommand<T> command);

    void loadDepartments();

    void getFinancesByDepartment(GetFinancesQuery query);

    void getAllData();

    BigDecimal getUnassignedAmountEvent(GetFinanceUnassignedAmountQuery query);

    BigDecimal getLeftAmountEvent(GetFinanceLeftAmountQuery query);

    BigDecimal getLeftAmountEvent(GetFinanceDepartmentLeftAmountQuery query);
}
