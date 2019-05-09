package com.github.andriilab.promasy.gui.conset;

import com.github.andriilab.promasy.gui.components.AbstractEmptyListener;
import com.github.andriilab.promasy.persistence.storage.ConnectionSettings;

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
