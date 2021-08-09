package com.exem.util.alarm.enums;

public enum WebhookUrl {

    JANDI_URL("https://wh.jandi.com/connect-api/webhook/11671944/224f89d84e5bbb8046867c463e217291");

    private String url;

    WebhookUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
