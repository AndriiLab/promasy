package com.github.andriilab.promasy.presentation.login;

public interface LoginListener {

    void loginAttemptOccurred(String user, char[] password);

    void loginCancelled();

    boolean isAbleToRegister();

}
