package main.java.gui.instedit;

import main.java.model.DepartmentModel;
import main.java.model.InstituteModel;
import main.java.model.SubdepartmentModel;

public interface OrganizationDialogListener {
	
	void instSelectionEventOccurred(long instId);
	
	void depSelectionEventOccurred(long depId);

	void createInstEventOccurred(InstituteModel model);

	void editInstEventOccurred(InstituteModel model);

	void deleteInstEventOccurred(InstituteModel model);

	void createDepEventOccurred(DepartmentModel model);

	void editDepEventOccurred(DepartmentModel model);
	
	void deleteDepEventOccurred(DepartmentModel model);
	
	void createSubdepEventOccurred(SubdepartmentModel model);

	void editSubdepEventOccurred(SubdepartmentModel model);
	
	void deleteSubdepEventOccurred(SubdepartmentModel model);

}
