package com.github.andriilab.promasy.gui.organization;

import com.github.andriilab.promasy.model.models.DepartmentModel;
import com.github.andriilab.promasy.model.models.InstituteModel;
import com.github.andriilab.promasy.model.models.SubdepartmentModel;

public interface OrganizationDialogListener {

    void persistModelEventOccurred(InstituteModel model);

    void persistModelEventOccurred(DepartmentModel model);

    void persistModelEventOccurred(SubdepartmentModel model);

    void getAllInstitutes();
}
