package gui;

import model.enums.Role;
import model.models.CPVModel;
import model.models.EmployeeModel;
import model.models.InstituteModel;

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
