package com.github.andriilab.promasy.gui.organization;

import com.github.andriilab.promasy.model.models.InstituteModel;

/**
 * Listener for {@link CreateOrganizationDialog}
 */
interface CreateOrganizationDialogListener {
    void persistModelEventOccured(InstituteModel model);
}
