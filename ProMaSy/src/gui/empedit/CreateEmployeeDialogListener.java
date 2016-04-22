package gui.empedit;

public interface CreateEmployeeDialogListener {
	
	public void instSelelectionEventOccured(long instId);
	
	public void depSelelectionEventOccured(long depId);
	
	public void createPersonEventOccured(EmployeeEvent ev);

}
