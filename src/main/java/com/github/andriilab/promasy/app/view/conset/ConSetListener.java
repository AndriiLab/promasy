package com.github.andriilab.promasy.app.view.conset;

import com.github.andriilab.promasy.data.storage.ConnectionSettings;

public interface ConSetListener {

    void preferencesSetEventOccurred(ConnectionSettings model);

    void forceCloseEventOccurred();

}
