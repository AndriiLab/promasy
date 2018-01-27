package com.github.andriilab.promasy.presentation.finance;

import com.github.andriilab.promasy.domain.finance.entities.Finance;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;
import com.github.andriilab.promasy.domain.organization.entities.Department;

/**
 * Listener for {@link FinancePanel}
 */
public interface FinancePanelListener {
    void persistModelEventOccurred(Finance model);

    void persistModelEventOccurred(FinanceDepartment model);

    void loadDepartments();

    void getFinancesByDepartment(Department department);

    void getAllData();
}
