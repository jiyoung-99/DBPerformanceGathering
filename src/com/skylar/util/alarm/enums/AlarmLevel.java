package com.exem.util.alarm.enums;

public enum AlarmLevel {

    warning("warning", 10000000), critical("critical", 1000000);

    private String levelName;
    private int value;


    AlarmLevel(String levelName) {
        this.levelName = levelName;
    }

    AlarmLevel(int value) {
        this.value = value;
    }

    AlarmLevel(String levelName, int value) {
        this.levelName = levelName;
        this.value = value;
    }

    public String getLevelName() { return levelName; }

    public int getValue() {
        return value;
    }

}
