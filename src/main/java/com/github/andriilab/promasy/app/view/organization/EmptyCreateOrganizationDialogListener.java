package com.github.andriilab.promasy.app.view.organization;

import com.github.andriilab.promasy.data.commands.ICommand;
import com.github.andriilab.promasy.domain.organization.entities.Institute;
import com.github.andriilab.promasy.app.components.AbstractEmptyListener;

public class EmptyCreateOrganizationDialogListener extends AbstractEmptyListener implements CreateOrganizationDialogListener {
    @Override
    public void persistModelEventOccurred(ICommand<Institute> command) {
        logEmptyListener(CreateOrganizationDialogListener.class);
    }
}
