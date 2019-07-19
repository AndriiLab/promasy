package com.github.andriilab.promasy.app.view.organization;

import com.github.andriilab.promasy.data.commands.ICommand;
import com.github.andriilab.promasy.domain.organization.entities.Institute;

/**
 * Listener for {@link CreateOrganizationDialog}
 */
interface CreateOrganizationDialogListener {
    void persistModelEventOccurred(ICommand<Institute> model);
}
