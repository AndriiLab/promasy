package com.github.andriilab.promasy.presentation.conset;

import com.github.andriilab.promasy.data.storage.ConnectionSettings;
import com.github.andriilab.promasy.presentation.components.AbstractEmptyListener;

public class EmptyConSetListener extends AbstractEmptyListener implements ConSetListener {
    @Override
    public void preferencesSetEventOccurred(ConnectionSettings model) {
        logEmptyListener(ConSetListener.class);
    }

    @Override
    public void forceCloseEventOccurred() {
        logEmptyListener(ConSetListener.class);
    }
}
