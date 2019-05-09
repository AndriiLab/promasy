package com.github.andriilab.promasy.gui;

import com.github.andriilab.promasy.app.queries.employees.GetEmployeesQuery;
import com.github.andriilab.promasy.domain.EmptyModel;
import com.github.andriilab.promasy.domain.cpv.entities.Cpv;
import com.github.andriilab.promasy.domain.organization.entities.Employee;
import com.github.andriilab.promasy.domain.organization.entities.Institute;
import com.github.andriilab.promasy.gui.components.AbstractEmptyListener;

import java.util.Collections;
import java.util.List;

public class EmptyMainFrameListener extends AbstractEmptyListener implements MainFrameListener {
    @Override
    public List<Employee> searchForPerson(GetEmployeesQuery query) {
        logEmptyListener(MainFrameListener.class);
        return Collections.emptyList();
    }

    @Override
    public void exitEventOccurred() {
        logEmptyListener(MainFrameListener.class);
    }

    @Override
    public void setMinimumVersionEventOccurred() {
        logEmptyListener(MainFrameListener.class);
    }

    @Override
    public void getAllDepartments(Institute institute) {
        logEmptyListener(MainFrameListener.class);
    }

    @Override
    public Cpv validateCpv(String cpvCode) {
        logEmptyListener(MainFrameListener.class);
        return EmptyModel.CPV;
    }

    @Override
    public void setNumberOfRegistrations(int regNumber) {
        logEmptyListener(MainFrameListener.class);
    }

    @Override
    public void visitUpdatesSite() {
        logEmptyListener(MainFrameListener.class);
    }
}
