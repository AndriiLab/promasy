package com.github.andriilab.promasy.data.authorization;

import com.github.andriilab.promasy.domain.organization.entities.Employee;

public final class LoginData {

    private static volatile Employee instance;

    public static Employee getInstance(Employee model) {
        Employee localInstance = instance;
        if (localInstance == null) {
            synchronized (Employee.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = model;
                }
            }
        }
        return localInstance;
    }

    public static Employee getInstance() {
        return instance;
    }
}
