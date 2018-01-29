package com.github.andriilab.promasy.presentation;

import com.github.andriilab.promasy.data.queries.employees.GetEmployeesQuery;
import com.github.andriilab.promasy.domain.cpv.entities.Cpv;
import com.github.andriilab.promasy.domain.organization.entities.Employee;
import com.github.andriilab.promasy.domain.organization.entities.Institute;

import java.util.List;

/**
 * Listener for {@link MainFrame}
 */
public interface MainFrameListener {

    List<Employee> searchForPerson(GetEmployeesQuery query);

    void exitEventOccurred();

    void setMinimumVersionEventOccurred();

    void getAllDepartments(Institute institute);

    Cpv validateCpv(String cpvCode);

    void setNumberOfRegistrations(int regNumber);

    void visitUpdatesSite();
}
