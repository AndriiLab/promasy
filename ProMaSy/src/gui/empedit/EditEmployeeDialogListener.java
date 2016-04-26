package gui.empedit;

public interface EditEmployeeDialogListener {
	
    void instSelectionEventOccurred(long instId);
	
	void depSelectionEventOccurred(long depId);
	
	void editPersonEventOccurred(EmployeeEvent ev);

}
