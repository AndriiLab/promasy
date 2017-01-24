package gui;

import model.enums.BidType;
import model.enums.Role;
import model.models.EmployeeModel;
import model.models.InstituteModel;

import java.util.List;

/**
 * Listener for {@link MainFrame}
 */
public interface MainFrameListener {
    //menu
    List<EmployeeModel> searchForPerson(Role role, long selectedDepartmentId);

    List<EmployeeModel> searchForPerson(Role role);

    void exitEventOccurred();

    void setMinimumVersionEventOccurred();

    void getAllDepartmentsAndFinances(InstituteModel institute);

    void getAllBids(BidType bidType);
}
