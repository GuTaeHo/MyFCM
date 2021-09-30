package com.example.myfcm.network.response;

import com.google.gson.annotations.SerializedName;

public class ResponseUpdateToken {
    @SerializedName("fcmtoken")
    private String fcmtoken;

    public String getFcmToken() {
        return fcmtoken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmtoken = fcmToken;
    }
}
