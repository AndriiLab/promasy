package gui;

import model.enums.Role;

/**
 * Listener for {@link MainFrame}
 */
public interface MainFrameListener {
    //menu
    void searchForPerson(Role role, long selectedDepartmentId);

    void searchForPerson(Role role);
    void exitEventOccurred();

    void setMinimumVersionEventOccurred();
}
