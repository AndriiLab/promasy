package gui.amunits;

import model.models.AmountUnitsModel;

/**
 * Listener for {@link AmUnitsDialog}
 */
public interface AmUnitsDialogListener {
    void persistModelEventOccurred(AmountUnitsModel model);
}
