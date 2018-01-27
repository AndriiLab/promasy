package com.github.andriilab.promasy.presentation;

import com.github.andriilab.promasy.domain.cpv.entities.Cpv;
import com.github.andriilab.promasy.domain.organization.entities.Employee;
import com.github.andriilab.promasy.domain.organization.entities.Institute;
import com.github.andriilab.promasy.domain.organization.enums.Role;

import java.util.List;

/**
 * Listener for {@link MainFrame}
 */
public interface MainFrameListener {

    List<Employee> searchForPerson(Role role, long selectedDepartmentId);

    List<Employee> searchForPerson(Role role);

    void exitEventOccurred();

    void setMinimumVersionEventOccurred();

    void getAllDepartments(Institute institute);

    Cpv validateCpv(String cpvCode);

    void setNumberOfRegistrations(int regNumber);

    void visitUpdatesSite();
}
