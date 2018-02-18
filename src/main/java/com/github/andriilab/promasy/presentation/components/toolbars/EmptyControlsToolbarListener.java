package com.github.andriilab.promasy.presentation.components.toolbars;

import com.github.andriilab.promasy.presentation.components.AbstractEmptyListener;

public class EmptyControlsToolbarListener extends AbstractEmptyListener implements ControlsToolbarListener {
    @Override
    public void reportYearChanged() {
        logEmptyListener(ControlsToolbarListener.class);
    }
}
