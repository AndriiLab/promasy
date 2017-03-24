package gui.cpvAmount;

import model.enums.Role;

/**
 * Listener for {@link CpvAmountDialog}
 */
public interface CpvAmountDialogListener {
    void getData();

    String getEmployeeName(Role role);
}
