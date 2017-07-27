package com.github.andriilab.promasy.gui;

import com.github.andriilab.promasy.model.enums.Role;
import com.github.andriilab.promasy.model.models.CPVModel;
import com.github.andriilab.promasy.model.models.EmployeeModel;
import com.github.andriilab.promasy.model.models.InstituteModel;

import java.util.List;

/**
 * Listener for {@link MainFrame}
 */
public interface MainFrameListener {

    List<EmployeeModel> searchForPerson(Role role, long selectedDepartmentId);

    List<EmployeeModel> searchForPerson(Role role);

    void exitEventOccurred();

    void setMinimumVersionEventOccurred();

    void getAllDepartments(InstituteModel institute);

    CPVModel validateCpv(String cpvCode);

    void setNumberOfRegistrations(int regNumber);
}
