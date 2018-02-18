package com.github.andriilab.promasy.presentation.organization;

import com.github.andriilab.promasy.data.commands.CreateOrUpdateCommand;
import com.github.andriilab.promasy.domain.IEntity;
import com.github.andriilab.promasy.presentation.components.AbstractEmptyListener;

public class EmptyOrganizationDialogListener extends AbstractEmptyListener implements OrganizationDialogListener {
    @Override
    public <T extends IEntity> void persistModelEventOccurred(CreateOrUpdateCommand<T> command) {
        logEmptyListener(OrganizationDialogListener.class);
    }

    @Override
    public void getAllInstitutes() {
        logEmptyListener(OrganizationDialogListener.class);
    }
}
