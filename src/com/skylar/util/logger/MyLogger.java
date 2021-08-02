package com.skylar.util.logger;

public interface MyLogger {

    void error(String message);
    void warn(String message);
    void info(String message);
    void debug(String message);
    void trace(String message);

}
