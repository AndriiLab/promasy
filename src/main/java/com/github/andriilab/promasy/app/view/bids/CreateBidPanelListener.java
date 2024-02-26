package com.github.andriilab.promasy.app.view.bids;

import com.github.andriilab.promasy.data.commands.ICommand;
import com.github.andriilab.promasy.data.queries.financepartment.GetFinanceDepartmentLeftAmountQuery;
import com.github.andriilab.promasy.domain.IEntity;

import java.math.BigDecimal;

/**
 * Listener for {@link CreateBidPanel}
 */
interface CreateBidPanelListener {
    <T extends IEntity> void persistModelEventOccurred(ICommand<T> command);

    void getAllData();

    BigDecimal getLeftAmount(GetFinanceDepartmentLeftAmountQuery query);
}
