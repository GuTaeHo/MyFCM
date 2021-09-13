package com.example.myfcm.network.request;

import com.google.gson.annotations.SerializedName;

public class RequestLogin {
    @SerializedName("id")
    private String id;

    @SerializedName("password")
    private String password;

    @SerializedName("fcmtoken")
    private String fcmtoken;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFcmtoken() {
        return fcmtoken;
    }

    public void setFcmtoken(String fcmtoken) {
        this.fcmtoken = fcmtoken;
    }
}
