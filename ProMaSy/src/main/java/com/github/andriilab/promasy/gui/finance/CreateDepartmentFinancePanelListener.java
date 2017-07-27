package com.github.andriilab.promasy.gui.finance;

import com.github.andriilab.promasy.model.models.FinanceDepartmentModel;

/**
 * Listener for {@link CreateDepartmentFinancePanel}
 */
interface CreateDepartmentFinancePanelListener {
    void persistModelEventOccurred(FinanceDepartmentModel model);

    void loadDepartments();
}
