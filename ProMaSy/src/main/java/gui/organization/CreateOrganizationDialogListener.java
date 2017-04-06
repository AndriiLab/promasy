package gui.organization;

import model.models.InstituteModel;

/**
 * Listener for {@link CreateOrganizationDialog}
 */
public interface CreateOrganizationDialogListener {
    void persistModelEventOccured(InstituteModel model);
}
