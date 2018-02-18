package com.github.andriilab.promasy.presentation.toolbars;

import com.github.andriilab.promasy.presentation.components.AbstractEmptyListener;

public class EmptyButtonsToolbarListener extends AbstractEmptyListener implements ButtonsToolbarListener {
    @Override
    public void printEventOccurred() {
        logEmptyListener(ButtonsToolbarListener.class);
    }

    @Override
    public void showCpvSearchDialog() {
        logEmptyListener(ButtonsToolbarListener.class);
    }

    @Override
    public void showCalculator() {
        logEmptyListener(ButtonsToolbarListener.class);
    }

    @Override
    public void exportToTableEventOccurred() {
        logEmptyListener(ButtonsToolbarListener.class);
    }

    @Override
    public void refreshTable() {
        logEmptyListener(ButtonsToolbarListener.class);
    }
}
