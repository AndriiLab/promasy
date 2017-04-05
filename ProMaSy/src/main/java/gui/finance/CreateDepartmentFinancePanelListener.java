package gui.finance;

import model.models.FinanceDepartmentModel;

/**
 * Listener for {@link CreateDepartmentFinancePanel}
 */
public interface CreateDepartmentFinancePanelListener {
    void persistModelEventOccurred(FinanceDepartmentModel model);

    void loadDepartments();
}
