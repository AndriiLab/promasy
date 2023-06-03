package com.github.andriilab.promasy.app.view.organization;

import com.github.andriilab.promasy.data.commands.CreateCommand;
import com.github.andriilab.promasy.data.commands.ICommand;
import com.github.andriilab.promasy.domain.IEntity;
import com.github.andriilab.promasy.app.components.AbstractEmptyListener;

public class EmptyOrganizationDialogListener extends AbstractEmptyListener implements OrganizationDialogListener {
    @Override
    public <T extends IEntity> void persistModelEventOccurred(ICommand<T> command) {
        logEmptyListener(OrganizationDialogListener.class);
    }

    @Override
    public void getAllInstitutes() {
        logEmptyListener(OrganizationDialogListener.class);
    }
}
