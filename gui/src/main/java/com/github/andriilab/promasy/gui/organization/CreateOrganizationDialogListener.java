package com.github.andriilab.promasy.gui.organization;

import com.github.andriilab.promasy.domain.organization.entities.Institute;

/**
 * Listener for {@link CreateOrganizationDialog}
 */
interface CreateOrganizationDialogListener {
    void persistModelEventOccured(Institute model);
}
