package com.github.andriilab.promasy.gui.controller;

import com.github.andriilab.promasy.app.interfaces.ILoginData;
import com.github.andriilab.promasy.domain.organization.entities.Employee;

public class LoginData implements ILoginData {

    private volatile Employee user;

    public LoginData(Employee user) {
        this.user = user;
    }

    @Override
    public Employee getCurrentUser() {
        return user;
    }
}
