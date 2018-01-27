package com.github.andriilab.promasy.presentation.organization;

import com.github.andriilab.promasy.domain.organization.entities.Institute;

/**
 * Listener for {@link CreateOrganizationDialog}
 */
interface CreateOrganizationDialogListener {
    void persistModelEventOccured(Institute model);
}
