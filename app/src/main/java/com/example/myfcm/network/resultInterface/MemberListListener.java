package com.example.myfcm.network.resultInterface;

import com.example.myfcm.model.Account;
import com.example.myfcm.model.People;
import com.example.myfcm.network.Response;
import com.example.myfcm.network.response.ResponseLogin;
import com.example.myfcm.network.response.ResponsePeople;

import java.util.ArrayList;

public interface MemberListListener {
    void success(ArrayList<People> responseLogin);

    void fail(String message);
}
