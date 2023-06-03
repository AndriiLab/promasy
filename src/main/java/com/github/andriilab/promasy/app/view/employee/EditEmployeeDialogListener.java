package com.github.andriilab.promasy.app.view.employee;

import com.github.andriilab.promasy.data.commands.ICommand;
import com.github.andriilab.promasy.domain.IEntity;

public interface EditEmployeeDialogListener {
    <T extends IEntity> void persistModelEventOccurred(ICommand<T> command);

    void getAllEmployees();
}
