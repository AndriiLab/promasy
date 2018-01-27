package com.github.andriilab.promasy.presentation.conset;

import com.github.andriilab.promasy.data.storage.ConnectionSettings;

public interface ConSetListener {

    void preferencesSetEventOccurred(ConnectionSettings model);

    void forceCloseEventOccurred();

}
