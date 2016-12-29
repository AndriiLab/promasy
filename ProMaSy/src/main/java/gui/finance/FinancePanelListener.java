package gui.finance;

import model.FinanceDepartmentModel;
import model.FinanceModel;

/**
 * Listener for {@link FinancePanel}
 */
public interface FinancePanelListener {
    void createOrderEventOccurred(FinanceModel model);
    void editOrderEventOccurred(FinanceModel model);
    void deleteOrderEventOccurred(FinanceModel model);

    void departmentSelectionEventOccurred(long departmentId);
    void orderSelectionEventOccurred(long orderId);

    void createDepOrderEventOccurred(FinanceDepartmentModel model);
    void editDepOrderEventOccurred(FinanceDepartmentModel model);
    void deleteDepOrderEventOccurred(FinanceDepartmentModel model);

}
