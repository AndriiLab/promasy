package gui.reports.cpv;

import model.enums.Role;

/**
 * Listener for {@link CpvAmountDialog}
 */
public interface CpvAmountDialogListener {
    void getData();

    String getEmployeeName(Role role);
}
