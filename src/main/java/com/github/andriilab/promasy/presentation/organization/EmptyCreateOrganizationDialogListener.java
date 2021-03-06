package com.github.andriilab.promasy.presentation.organization;

import com.github.andriilab.promasy.domain.organization.entities.Institute;
import com.github.andriilab.promasy.presentation.components.AbstractEmptyListener;

public class EmptyCreateOrganizationDialogListener extends AbstractEmptyListener implements CreateOrganizationDialogListener {
    @Override
    public void persistModelEventOccured(Institute model) {
        logEmptyListener(CreateOrganizationDialogListener.class);
    }
}
