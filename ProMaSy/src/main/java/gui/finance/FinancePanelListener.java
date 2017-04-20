package gui.finance;

import model.models.DepartmentModel;
import model.models.FinanceDepartmentModel;
import model.models.FinanceModel;

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
