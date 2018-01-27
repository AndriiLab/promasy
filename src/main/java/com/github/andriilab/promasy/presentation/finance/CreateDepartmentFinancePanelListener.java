package com.github.andriilab.promasy.presentation.finance;

import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;

/**
 * Listener for {@link CreateDepartmentFinancePanel}
 */
interface CreateDepartmentFinancePanelListener {
    void persistModelEventOccurred(FinanceDepartment model);

    void loadDepartments();
}
