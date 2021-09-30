package com.example.myfcm.network.response;

import com.example.myfcm.model.People;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponsePeople {
    @SerializedName("account")
    private ArrayList<People> account;

    public ArrayList<People> getPeople() {
        if (account == null) {
            account = new ArrayList<People>();
        }
        return account;
    }
}
