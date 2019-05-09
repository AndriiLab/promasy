package com.github.andriilab.promasy.gui.finance;

import com.github.andriilab.promasy.app.commands.CreateOrUpdateCommand;
import com.github.andriilab.promasy.app.queries.finance.GetFinanceLeftAmountQuery;
import com.github.andriilab.promasy.app.queries.finance.GetFinanceUnassignedAmountQuery;
import com.github.andriilab.promasy.app.queries.finance.GetFinancesQuery;
import com.github.andriilab.promasy.app.queries.financepartment.GetFinanceDepartmentLeftAmountQuery;
import com.github.andriilab.promasy.commons.persistence.IEntity;

import java.math.BigDecimal;

/**
 * Listener for {@link FinancePanel}
 */
public interface FinancePanelListener {
    <T extends IEntity> void persistModelEventOccurred(CreateOrUpdateCommand<T> command);

    void loadDepartments();

    void getFinancesByDepartment(GetFinancesQuery query);

    void getAllData();

    BigDecimal getUnassignedAmountEvent(GetFinanceUnassignedAmountQuery query);

    BigDecimal getLeftAmountEvent(GetFinanceLeftAmountQuery query);

    BigDecimal getLeftAmountEvent(GetFinanceDepartmentLeftAmountQuery query);
}
