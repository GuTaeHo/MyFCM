package com.example.myfcm.model;

import com.google.gson.annotations.SerializedName;

public class Account {
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
