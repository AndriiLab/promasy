package com.github.andriilab.promasy.gui.organization;

import com.github.andriilab.promasy.app.commands.CreateOrUpdateCommand;
import com.github.andriilab.promasy.commons.persistence.IEntity;
import com.github.andriilab.promasy.gui.components.AbstractEmptyListener;

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
