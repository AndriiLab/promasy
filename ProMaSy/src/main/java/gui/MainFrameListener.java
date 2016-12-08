package main.java.gui;

/**
 * Created by laban on 24.06.2016.
 */
public interface MainFrameListener {
    //menu
    void searchForPerson(int roleId, long selectedDepartmentId);
    void searchForPerson(int roleId);
    void exitEventOccurred();

    void setMinimumVersionEventOccurred();
}
