package com.github.andriilab.promasy.gui.employee;

import com.github.andriilab.promasy.gui.components.AbstractEmptyListener;

public class EmptyCreateEmployeeFromLoginListener extends AbstractEmptyListener implements CreateEmployeeFromLoginListener {
    @Override
    public void newUserCreatedEvent() {
        logEmptyListener(CreateEmployeeFromLoginListener.class);
    }

    @Override
    public void cancelEvent() {
        logEmptyListener(CreateEmployeeFromLoginListener.class);
    }
}
