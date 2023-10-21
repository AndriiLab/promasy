package com.github.andriilab.promasy.app.controller;

import com.github.andriilab.promasy.app.view.MainFrame;
import com.github.andriilab.promasy.app.commons.Colors;
import com.github.andriilab.promasy.app.commons.Labels;
import org.apache.logging.log4j.LogManager;

/**
 * Implementation of log4j with internal logger in com.github.andriilab.promasy.app
 */
public final class Logger {

    private Logger() {
        throw new IllegalStateException("Utility class");
    }

    public static <T> void infoEvent(Class<T> callerClass, MainFrame mainFrame, String message) {
        LogManager.getLogger(callerClass).info(message);
        if (mainFrame != null) {
            mainFrame.logEvent(message, Colors.GREEN);
        }
    }

    public static <T> void warnEvent(Class<T> callerClass, Throwable throwable) {
        LogManager.getLogger(callerClass).warn(throwable);
    }

    public static <T> void warnEvent(Class<T> callerClass, String message) {
        LogManager.getLogger(callerClass).warn(message);
    }

    public static <T> void errorEvent(Class<T> callerClass, MainFrame mainFrame, String message, Exception exception) {
        message = Labels.withColon("error") + message;
        LogManager.getLogger(callerClass).error(message, exception);
        if (mainFrame != null) {
            mainFrame.logError(exception, message);
        }
    }

    public static <T> void errorEvent(Class<T> callerClass, MainFrame mainFrame, Exception exception) {
        errorEvent(callerClass, mainFrame, exception.getMessage(), exception);
    }
}
