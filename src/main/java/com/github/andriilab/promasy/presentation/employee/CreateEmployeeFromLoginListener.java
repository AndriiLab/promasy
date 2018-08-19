package com.github.andriilab.promasy.presentation.employee;

import com.github.andriilab.promasy.presentation.login.LoginPanel;

/**
 * Listener for {@link LoginPanel}
 */
public interface CreateEmployeeFromLoginListener {
    void newUserCreatedEvent();

    void cancelEvent();
}
