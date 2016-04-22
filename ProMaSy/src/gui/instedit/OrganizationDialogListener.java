package gui.instedit;

import model.DepartmentModel;
import model.InstituteModel;
import model.SubdepartmentModel;

public interface OrganizationDialogListener {
	
	public void instSelelectionEventOccured(long instId);
	
	public void depSelelectionEventOccured(long depId);

	public void createInstEventOccured(InstituteModel model);

	public void editInstEventOccured(InstituteModel model);

	public void deleteInstEventOccured(InstituteModel model);

	public void createDepEventOccured(DepartmentModel model);

	public void editDepEventOccured(DepartmentModel model);
	
	public void deleteDepEventOccured(DepartmentModel model);
	
	public void createSubdepEventOccured(SubdepartmentModel model);

	public void editSubdepEventOccured(SubdepartmentModel model);
	
	public void deleteSubdepEventOccured(SubdepartmentModel model);

}
