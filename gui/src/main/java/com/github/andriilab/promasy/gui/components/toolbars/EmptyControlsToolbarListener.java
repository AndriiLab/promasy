package com.github.andriilab.promasy.gui.components.toolbars;

import com.github.andriilab.promasy.gui.components.AbstractEmptyListener;

public class EmptyControlsToolbarListener extends AbstractEmptyListener implements ControlsToolbarListener {
    @Override
    public void reportYearChanged() {
        logEmptyListener(ControlsToolbarListener.class);
    }
}
