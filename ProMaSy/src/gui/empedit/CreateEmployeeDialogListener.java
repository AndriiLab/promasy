package gui.empedit;

public interface CreateEmployeeDialogListener {
	
	void instSelectionEventOccurred(long instId);
	
	void deaSelectionEventOccurred(long depId);
	
	void createPersonEventOccurred(EmployeeEvent ev);

}
