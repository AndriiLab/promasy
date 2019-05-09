package com.github.andriilab.promasy.gui.organization;

import com.github.andriilab.promasy.app.commands.CreateOrUpdateCommand;
import com.github.andriilab.promasy.commons.persistence.IEntity;

public interface OrganizationDialogListener {
    <T extends IEntity> void persistModelEventOccurred(CreateOrUpdateCommand<T> command);

    void getAllInstitutes();
}
