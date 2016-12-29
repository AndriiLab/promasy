package gui;

/**
 * Listener for {@link MainFrame}
 */
public interface MainFrameListener {
    //menu
    void searchForPerson(int roleId, long selectedDepartmentId);
    void searchForPerson(int roleId);
    void exitEventOccurred();

    void setMinimumVersionEventOccurred();
}
