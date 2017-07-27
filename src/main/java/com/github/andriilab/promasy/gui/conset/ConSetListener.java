package com.github.andriilab.promasy.gui.conset;

import com.github.andriilab.promasy.model.models.ConnectionSettingsModel;

public interface ConSetListener {

	void preferencesSetEventOccurred(ConnectionSettingsModel model);

    void forceCloseEventOccurred();

}
