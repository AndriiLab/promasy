package com.github.andriilab.promasy.gui.employee;

import com.github.andriilab.promasy.gui.login.LoginPanel;

/**
 * Listener for {@link LoginPanel}
 */
public interface CreateEmployeeFromLoginListener {

    void newUserCreatedEvent();

    void cancelEvent();
}
