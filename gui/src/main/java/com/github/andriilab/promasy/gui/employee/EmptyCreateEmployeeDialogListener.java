package com.github.andriilab.promasy.gui.employee;

import com.github.andriilab.promasy.app.commands.CreateOrUpdateCommand;
import com.github.andriilab.promasy.commons.persistence.IEntity;
import com.github.andriilab.promasy.gui.components.AbstractEmptyListener;

public class EmptyCreateEmployeeDialogListener extends AbstractEmptyListener implements CreateEmployeeDialogListener {
    @Override
    public <T extends IEntity> void persistModelEventOccurred(CreateOrUpdateCommand<T> command) {
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
