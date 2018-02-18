package com.github.andriilab.promasy.presentation.components;

import com.github.andriilab.promasy.data.controller.Logger;

public class AbstractEmptyListener {

    protected <L> void logEmptyListener(Class<L> listenerClass) {
        Logger.warnEvent(listenerClass, "Called empty listener for class " + listenerClass.getSimpleName());
    }
}
