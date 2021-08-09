package com.exem.util.alarm.form;

import java.util.List;

public class JandiMessage {

    private String body;
    private String connectColor;
    private ConnectInfo connectInfo;

    public JandiMessage() {
    }

    public JandiMessage(String body, String connectColor, ConnectInfo connectInfo) {
        this.body = body;
        this.connectColor = connectColor;
        this.connectInfo = connectInfo;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getConnectColor() {
        return connectColor;
    }

    public void setConnectColor(String connectColor) {
        this.connectColor = connectColor;
    }

    public ConnectInfo getConnectInfo() {
        return connectInfo;
    }

    public void setConnectInfo(ConnectInfo connectInfo) {
        this.connectInfo = connectInfo;
    }
}
