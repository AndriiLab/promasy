package com.github.andriilab.promasy.presentation.employee;

import com.github.andriilab.promasy.data.commands.CreateOrUpdateCommand;
import com.github.andriilab.promasy.domain.IEntity;

public interface CreateEmployeeDialogListener {
    <T extends IEntity> void persistModelEventOccurred(CreateOrUpdateCommand<T> command);

    boolean checkUniqueLogin(String login);

    void loadInstitutes();
}
