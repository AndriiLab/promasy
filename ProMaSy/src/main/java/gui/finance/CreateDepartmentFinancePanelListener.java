package gui.finance;

import model.models.FinanceDepartmentModel;

/**
 * Listener for {@link CreateDepartmentFinancePanel}
 */
interface CreateDepartmentFinancePanelListener {
    void persistModelEventOccurred(FinanceDepartmentModel model);

    void loadDepartments();
}
