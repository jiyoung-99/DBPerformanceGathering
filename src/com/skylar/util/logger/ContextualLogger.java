package com.skylar.util.logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ContextualLogger implements MyLogger {

    private final MyLogger myLogger;
    private final String callerClass;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ContextualLogger(String callerClass, MyLogger myLogger) {
        this.callerClass = callerClass;
        this.myLogger = myLogger;
    }

    @Override
    public void error(String message) {
        this.myLogger.error("[ERROR] "+LocalDateTime.now().format(formatter) + " [CLASS NAME]" + this.callerClass + " [MESSAGE]" + message);
    }

    @Override
    public void warn(String message) {
        this.myLogger.warn("[WARN] "+LocalDateTime.now().format(formatter) + " [CLASS NAME]" + this.callerClass + " [MESSAGE]" + message);
    }

    @Override
    public void info(String message) {
        this.myLogger.info("[INFO] "+LocalDateTime.now().format(formatter) + " [CLASS NAME]" + this.callerClass + " [MESSAGE]" + message);
    }

    @Override
    public void debug(String message) {
        this.myLogger.debug("[DEBUG] "+LocalDateTime.now().format(formatter) + " [CLASS NAME]" + this.callerClass + " [MESSAGE]" + message);
    }

    @Override
    public void trace(String message) {
        this.myLogger.trace("[TRACE] "+LocalDateTime.now().format(formatter) + " [CLASS NAME]" + this.callerClass + " [MESSAGE]" + message);
    }
}
