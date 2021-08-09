package com.exem.util.logger;


public class LevelFilteredLogger implements MyLogger {

    private final Integer level;
    private final MyLogger myLogger;

    public LevelFilteredLogger(Integer level, MyLogger myLogger) {
        this.level = level;
        this.myLogger = myLogger;
    }

    @Override
    public void error(String message) {
        Integer code = 5;
        if(level >= code) {
            myLogger.error(message);
        }
        //레벨이 높아도 에러 로그는 나오게
        myLogger.error(message);
    }

    @Override
    public void warn(String message) {
        Integer code = 4;
        if(level >= code) {
            myLogger.warn(message);
        }
    }

    @Override
    public void info(String message) {
        Integer code = 3;
        if(level >= code) {
            myLogger.info(message);
        }
    }

    @Override
    public void debug(String message) {
        Integer code = 2;
        if(level >= code) {
            myLogger.debug(message);
        }
    }

    @Override
    public void trace(String message) {
        Integer code = 1;
        if(level >= code) {
            myLogger.trace(message);
        }
    }

}
