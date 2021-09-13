package com.example.myfcm.network.response;

import com.example.myfcm.model.Account;
import com.google.gson.annotations.SerializedName;

public class ResponseLogin {
    @SerializedName("account")
    private Account account;

    @SerializedName("apitoken")
    private String apitoken;

    public Account getAccount() {
        if (account == null) {
            account = new Account();
        }
        return account;
    }

    public String getApitoken() {
        return apitoken;
    }
}
