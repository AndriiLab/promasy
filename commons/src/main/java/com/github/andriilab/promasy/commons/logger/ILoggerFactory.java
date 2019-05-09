package com.github.andriilab.promasy.commons.logger;

public interface ILoggerFactory {
    ILogger getLogger(Class callerClass);
}
