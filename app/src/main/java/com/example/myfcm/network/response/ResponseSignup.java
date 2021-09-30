package com.example.myfcm.network.response;

import com.example.myfcm.model.Account;
import com.google.gson.annotations.SerializedName;

public class ResponseSignup {
    @SerializedName("account")
    private Account account;

    @SerializedName("apitoken")
    private String apitoken;

    @SerializedName("fcmtoken")
    private String fcmtoken;

    public Account getAccount() {
        if (account == null) {
            account = new Account();
        }
        return account;
    }

    public String getApitoken() {
        if (apitoken != null) {
            return apitoken;
        }
        return "";
    }

    public String getFcmtoken() {
        if (fcmtoken != null) {
            return fcmtoken;
        }
        return "";
    }

    public void setFcmtoken(String fcmtoken) {
        this.fcmtoken = fcmtoken;
    }
}
