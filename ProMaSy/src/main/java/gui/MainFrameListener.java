package gui;

import model.enums.Role;
import model.models.InstituteModel;

/**
 * Listener for {@link MainFrame}
 */
public interface MainFrameListener {
    //menu
    void searchForPerson(Role role, long selectedDepartmentId);

    void searchForPerson(Role role);
    void exitEventOccurred();

    void setMinimumVersionEventOccurred();

    void selectAllDepartmentsAndFinances(InstituteModel institute);
}
