package com.github.andriilab.promasy.app.view.employee;

import com.github.andriilab.promasy.data.commands.CreateCommand;
import com.github.andriilab.promasy.data.commands.ICommand;
import com.github.andriilab.promasy.domain.IEntity;
import com.github.andriilab.promasy.app.components.AbstractEmptyListener;

public class EmptyCreateEmployeeDialogListener extends AbstractEmptyListener implements CreateEmployeeDialogListener {
    @Override
    public <T extends IEntity> void persistModelEventOccurred(ICommand<T> command) {
        logEmptyListener(CreateEmployeeDialogListener.class);
    }

    @Override
    public boolean checkUniqueLogin(String login) {
        logEmptyListener(CreateEmployeeDialogListener.class);
        return false;
    }

    @Override
    public void loadInstitutes() {
        logEmptyListener(CreateEmployeeDialogListener.class);
    }
}
