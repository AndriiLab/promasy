package com.github.andriilab.promasy.gui.employee;

import com.github.andriilab.promasy.app.commands.CreateOrUpdateCommand;
import com.github.andriilab.promasy.commons.persistence.IEntity;

public interface EditEmployeeDialogListener {
    <T extends IEntity> void persistModelEventOccurred(CreateOrUpdateCommand<T> command);

    void getAllEmployees();
}
