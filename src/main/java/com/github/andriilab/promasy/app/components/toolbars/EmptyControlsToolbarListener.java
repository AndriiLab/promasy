package com.github.andriilab.promasy.app.components.toolbars;

import com.github.andriilab.promasy.app.components.AbstractEmptyListener;

public class EmptyControlsToolbarListener extends AbstractEmptyListener implements ControlsToolbarListener {
    @Override
    public void reportYearChanged() {
        logEmptyListener(ControlsToolbarListener.class);
    }
}
