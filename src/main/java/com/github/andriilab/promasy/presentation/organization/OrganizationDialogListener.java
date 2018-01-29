package com.github.andriilab.promasy.presentation.organization;

import com.github.andriilab.promasy.data.commands.CreateOrUpdateCommand;
import com.github.andriilab.promasy.domain.IEntity;

public interface OrganizationDialogListener {

    <T extends IEntity> void persistModelEventOccurred(CreateOrUpdateCommand<T> command);

    void getAllInstitutes();
}
