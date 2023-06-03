package com.github.andriilab.promasy.app.view.login;

public interface LoginListener {

    void loginAttemptOccurred(String user, char[] password);

    void loginCancelled();

    boolean isAbleToRegister();

}
