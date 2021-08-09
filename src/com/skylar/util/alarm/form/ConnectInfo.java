package com.exem.util.alarm.form;

public class ConnectInfo {

    private String title;
    private String description;
    private String imageUrl;

    public ConnectInfo() {
    }

    public ConnectInfo(String title, String description) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public ConnectInfo(String title, String description, String imageUrl) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "ConnectInfo{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
