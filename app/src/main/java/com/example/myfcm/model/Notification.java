package com.example.myfcm.model;

public class Notification {
    public static final String DEFAULT_TITLE = "알림";
    // private static final String DEFAULT_SOUND = "";

    private String title;
    private String body;
    private String icon;
    private String sound;
    private String image;

    public Notification() {
        this.title = DEFAULT_TITLE;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
