package com.example.myfcm.network.request;

import com.google.gson.annotations.SerializedName;

public class RequestUpdateToken {
    @SerializedName("fcmtoken")
    private String fcmtoken;

    public String getFcmtoken() {
        return fcmtoken;
    }

    public void setFcmtoken(String fcmtoken) {
        this.fcmtoken = fcmtoken;
    }
}
