package com.github.andriilab.promasy.app.view.employee;

import com.github.andriilab.promasy.data.commands.ICommand;
import com.github.andriilab.promasy.domain.IEntity;

public interface CreateEmployeeDialogListener {
    <T extends IEntity> void persistModelEventOccurred(ICommand<T> command);

    boolean checkUniqueLogin(String login);

    void loadInstitutes();
}
