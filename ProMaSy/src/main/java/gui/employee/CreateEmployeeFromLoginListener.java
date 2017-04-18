package gui.employee;

import gui.login.LoginPanel;

/**
 * Listener for {@link LoginPanel}
 */
public interface CreateEmployeeFromLoginListener {

    void newUserCreatedEvent();

    void cancelEvent();
}
