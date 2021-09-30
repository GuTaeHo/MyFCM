package com.example.myfcm.network.request;

import com.google.gson.annotations.SerializedName;

public class RequestLogin {
    @SerializedName("id")
    private String id;

    @SerializedName("password")
    private String password;

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
}
