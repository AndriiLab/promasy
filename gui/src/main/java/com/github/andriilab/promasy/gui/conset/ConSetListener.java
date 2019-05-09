package com.github.andriilab.promasy.gui.conset;

import com.github.andriilab.promasy.persistence.storage.ConnectionSettings;

public interface ConSetListener {

    void preferencesSetEventOccurred(ConnectionSettings model);

    void forceCloseEventOccurred();

}
