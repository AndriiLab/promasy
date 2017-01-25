package gui.finance;

import model.models.FinanceDepartmentModel;

/**
 * Listener for {@link CreateDepartmentFinancesDialog}
 */
public interface FinanceDepartmentDialogListener {
    void persistModelEventOccurred(FinanceDepartmentModel model);

    void loadDepartments();
}
