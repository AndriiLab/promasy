package com.github.andriilab.promasy.app.view.organization;

import com.github.andriilab.promasy.data.commands.ICommand;
import com.github.andriilab.promasy.domain.IEntity;

public interface OrganizationDialogListener {
    <T extends IEntity> void persistModelEventOccurred(ICommand<T> command);

    void getAllInstitutes();
}
