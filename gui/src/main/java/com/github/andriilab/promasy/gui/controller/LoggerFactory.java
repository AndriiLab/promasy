package com.github.andriilab.promasy.gui.controller;

import com.github.andriilab.promasy.commons.logger.ILogger;
import com.github.andriilab.promasy.commons.logger.ILoggerFactory;
import com.github.andriilab.promasy.gui.MainFrame;

public class LoggerFactory implements ILoggerFactory {

    private MainFrame mainFrame;

    public LoggerFactory(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public ILogger getLogger(Class callerClass) {
        return new Logger(mainFrame ,callerClass);
    }
}
