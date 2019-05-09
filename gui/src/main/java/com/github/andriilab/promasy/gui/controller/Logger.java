package com.github.andriilab.promasy.gui.controller;

import com.github.andriilab.promasy.commons.logger.ILogger;
import com.github.andriilab.promasy.gui.MainFrame;
import com.github.andriilab.promasy.gui.commons.Colors;
import com.github.andriilab.promasy.gui.commons.Labels;
import org.apache.log4j.LogManager;

/**
 * Implementation of log4j with internal logger in com.github.andriilab.promasy.gui
 */
public class Logger implements ILogger {

    private MainFrame mainFrame;
    private Class caller;

    public Logger(MainFrame mainFrame, Class caller) {
        this.mainFrame = mainFrame;
        this.caller = caller;
    }

    public void infoEvent(String message) {
        LogManager.getLogger(caller).info(message);
        if (mainFrame != null) {
            mainFrame.logEvent(message, Colors.GREEN);
        }
    }

    public void warnEvent(Throwable throwable) {
        LogManager.getLogger(caller).warn(throwable);
    }

    public void warnEvent(String message) {
        LogManager.getLogger(caller).warn(message);
    }

    public void errorEvent(String message, Exception exception) {
        message = Labels.withColon("error") + message;
        LogManager.getLogger(caller).error(message, exception);
        if (mainFrame != null) {
            mainFrame.logError(exception, message);
        }
    }

    public void errorEvent(Exception exception) {
        errorEvent(exception.getMessage(), exception);
    }
}
