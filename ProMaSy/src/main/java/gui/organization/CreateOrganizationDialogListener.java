package gui.organization;

import model.models.InstituteModel;

/**
 * Listener for {@link CreateOrganizationDialog}
 */
interface CreateOrganizationDialogListener {
    void persistModelEventOccured(InstituteModel model);
}
