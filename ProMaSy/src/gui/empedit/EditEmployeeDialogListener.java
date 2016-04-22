package gui.empedit;

public interface EditEmployeeDialogListener {
	
    public void instSelelectionEventOccured(long instId);
	
	public void depSelelectionEventOccured(long depId);
	
	public void editPersonEventOccured(EmployeeEvent ev);

}
