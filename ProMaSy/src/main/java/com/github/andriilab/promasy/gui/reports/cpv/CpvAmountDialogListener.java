package com.github.andriilab.promasy.gui.reports.cpv;

import com.github.andriilab.promasy.model.enums.Role;

/**
 * Listener for {@link CpvAmountDialog}
 */
public interface CpvAmountDialogListener {
    void getData();

    String getEmployeeName(Role role);
}
