package com.github.andriilab.promasy.app.view.employee;

import com.github.andriilab.promasy.app.components.AbstractEmptyListener;
import com.github.andriilab.promasy.data.commands.ICommand;
import com.github.andriilab.promasy.domain.IEntity;

public class EmptyEditEmployeeDialogListener extends AbstractEmptyListener implements EditEmployeeDialogListener {
    @Override
    public <T extends IEntity> void persistModelEventOccurred(ICommand<T> command) {
        logEmptyListener(EditEmployeeDialogListener.class);
    }

    @Override
    public void getAllEmployees() {
        logEmptyListener(EditEmployeeDialogListener.class);
    }
}
