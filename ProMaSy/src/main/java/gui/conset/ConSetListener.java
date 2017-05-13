package gui.conset;

import model.models.ConnectionSettingsModel;

public interface ConSetListener {

	void preferencesSetEventOccurred(ConnectionSettingsModel model);

    void forceCloseEventOccurred();

}
