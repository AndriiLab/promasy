package gui.finance;

import model.models.FinanceModel;

/**
 * Listener for {@link CreateDepartmentFinancesDialog}
 */
public interface FinanceDepartmentDialogListener {
    void persistModelEventOccurred(FinanceModel model);

    void loadDepartments();
}
