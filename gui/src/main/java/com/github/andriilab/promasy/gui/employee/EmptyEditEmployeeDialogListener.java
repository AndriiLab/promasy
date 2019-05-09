package com.github.andriilab.promasy.gui.employee;

import com.github.andriilab.promasy.app.commands.CreateOrUpdateCommand;
import com.github.andriilab.promasy.commons.persistence.IEntity;
import com.github.andriilab.promasy.gui.components.AbstractEmptyListener;

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
