package gui.amunits;

import model.AmountUnitsModel;

/**
 * Created by A on 27.04.2016.
 */
public interface AmUnitsDialogListener {
    void createEventOccurred(AmountUnitsModel model);
    void editEventOccurred(AmountUnitsModel model);
    void deleteEventOccurred(AmountUnitsModel model);
}
