package com.github.andriilab.promasy.presentation.employee;

import com.github.andriilab.promasy.data.commands.CreateOrUpdateCommand;
import com.github.andriilab.promasy.domain.IEntity;
import com.github.andriilab.promasy.presentation.components.AbstractEmptyListener;

public class EmptyEditEmployeeDialogListener extends AbstractEmptyListener implements EditEmployeeDialogListener {
    @Override
    public <T extends IEntity> void persistModelEventOccurred(CreateOrUpdateCommand<T> command) {
        logEmptyListener(EditEmployeeDialogListener.class);
    }

    @Override
    public void getAllEmployees() {
        logEmptyListener(EditEmployeeDialogListener.class);
    }
}
