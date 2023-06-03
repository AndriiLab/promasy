package com.github.andriilab.promasy.app.view.employee;

import com.github.andriilab.promasy.app.view.login.LoginPanel;

/**
 * Listener for {@link LoginPanel}
 */
public interface CreateEmployeeFromLoginListener {
    void newUserCreatedEvent();

    void cancelEvent();
}
