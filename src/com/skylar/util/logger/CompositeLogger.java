package com.exem.util.logger;


public class CompositeLogger implements MyLogger {

    private final MyLogger myLogger;
    private final MyLogger fileLogger;

    public CompositeLogger(MyLogger myLogger, MyLogger fileLogger) {
        this.myLogger = myLogger;
        this.fileLogger = fileLogger;
    }

    @Override
    public void error(String message) {
        this.myLogger.error(message);
        this.fileLogger.error(message);
    }


    @Override
    public void warn(String message) {
        this.myLogger.warn(message);
        this.fileLogger.warn(message);
    }

    @Override
    public void info(String message) {
        this.myLogger.info(message);
        this.fileLogger.info(message);
    }

    @Override
    public void debug(String message) {
        this.myLogger.debug(message);
        this.fileLogger.debug(message);
    }

    @Override
    public void trace(String message) {
        this.myLogger.debug(message);
        this.fileLogger.debug(message);
    }
}
