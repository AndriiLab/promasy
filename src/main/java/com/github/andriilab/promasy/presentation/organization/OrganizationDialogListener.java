package com.github.andriilab.promasy.presentation.organization;

import com.github.andriilab.promasy.domain.organization.entities.Department;
import com.github.andriilab.promasy.domain.organization.entities.Institute;
import com.github.andriilab.promasy.domain.organization.entities.Subdepartment;

public interface OrganizationDialogListener {

    void persistModelEventOccurred(Institute model);

    void persistModelEventOccurred(Department model);

    void persistModelEventOccurred(Subdepartment model);

    void getAllInstitutes();
}
