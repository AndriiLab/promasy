package gui.instedit;

import model.models.DepartmentModel;
import model.models.InstituteModel;
import model.models.SubdepartmentModel;

public interface OrganizationDialogListener {

    void persistModelEventOccurred(InstituteModel model);

    void persistModelEventOccurred(DepartmentModel model);

    void persistModelEventOccurred(SubdepartmentModel model);

}
