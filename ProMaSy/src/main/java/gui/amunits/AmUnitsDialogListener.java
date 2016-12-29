package gui.amunits;

import model.AmountUnitsModel;

/**
 * Listener for {@link AmUnitsDialog}
 */
public interface AmUnitsDialogListener {
    void createEventOccurred(AmountUnitsModel model);
    void editEventOccurred(AmountUnitsModel model);
    void deleteEventOccurred(AmountUnitsModel model);
}
