package com.github.andriilab.promasy.controller;

import com.github.andriilab.promasy.gui.MainFrame;
import com.github.andriilab.promasy.gui.commons.Colors;
import com.github.andriilab.promasy.gui.commons.Labels;
import org.apache.log4j.LogManager;

/**
 * Implementation of log4j with internal logger in com.github.andriilab.promasy.gui
 */
public class Logger {
    private static final org.apache.log4j.Logger LOGGER = LogManager.getLogger(Controller.class);

    public static void infoEvent(MainFrame mainFrame, String message) {
        LOGGER.info(message);
        if (mainFrame != null) {
            mainFrame.logEvent(message, Colors.GREEN);
        }
    }

    public static void warnEvent(Throwable throwable) {
        LOGGER.warn(throwable);
    }

    public static void errorEvent(MainFrame mainFrame, String message, Exception exception) {
        message = Labels.withColon("error") + message;
        LOGGER.error(message, exception);
        if (mainFrame != null) {
            mainFrame.logError(exception, message);
        }
    }

    public static void errorEvent(MainFrame mainFrame, Exception exception) {
        errorEvent(mainFrame, exception.getMessage(), exception);
    }
}
