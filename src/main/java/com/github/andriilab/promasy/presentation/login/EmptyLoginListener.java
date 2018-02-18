package com.github.andriilab.promasy.presentation.login;

import com.github.andriilab.promasy.presentation.components.AbstractEmptyListener;

public class EmptyLoginListener extends AbstractEmptyListener implements LoginListener {
    @Override
    public void loginAttemptOccurred(String user, char[] password) {
        logEmptyListener(LoginListener.class);
    }

    @Override
    public void loginCancelled() {
        logEmptyListener(LoginListener.class);

    }

    @Override
    public boolean isAbleToRegister() {
        logEmptyListener(LoginListener.class);
        return false;
    }
}
