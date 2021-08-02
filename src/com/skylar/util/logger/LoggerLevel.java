package com.skylar.util.logger;

public enum LoggerLevel {

    TRACE(1), DEBUG(2), INFO(3), WARN(4), ERROR(5);

    private final Integer value;

    private LoggerLevel(Integer i) {
        this.value = i;
    }

    public int getValue() {
        return value;
    }

}
