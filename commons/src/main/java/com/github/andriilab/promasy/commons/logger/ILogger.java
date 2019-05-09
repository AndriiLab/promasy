package com.github.andriilab.promasy.commons.logger;

public interface ILogger {
    void infoEvent(String message);
    void warnEvent(Throwable throwable);
    void warnEvent(String message);
    void errorEvent(String message, Exception exception);
    void errorEvent(Exception exception);
}
