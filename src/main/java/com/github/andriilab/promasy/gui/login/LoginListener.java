package com.github.andriilab.promasy.gui.login;

public interface LoginListener {

    void loginAttemptOccurred(String user, char[] password);

    void loginCancelled();

    boolean isAbleToRegister();

}
