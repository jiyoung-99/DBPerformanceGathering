package com.skylar.util.alarm.enums;

public enum ConnectColor {

    WARNING("#FE141B"), CRICTICAL("#FED12C");

    private String color;

    ConnectColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
