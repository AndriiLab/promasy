package com.github.andriilab.promasy.gui.finance;

import com.github.andriilab.promasy.model.models.DepartmentModel;
import com.github.andriilab.promasy.model.models.FinanceDepartmentModel;
import com.github.andriilab.promasy.model.models.FinanceModel;

/**
 * Listener for {@link FinancePanel}
 */
public interface FinancePanelListener {
    void persistModelEventOccurred(FinanceModel model);

    void persistModelEventOccurred(FinanceDepartmentModel model);

    void loadDepartments();

    void getFinancesByDepartment(DepartmentModel department);

    void getAllData();
}
