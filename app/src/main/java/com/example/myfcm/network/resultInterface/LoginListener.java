package com.example.myfcm.network.resultInterface;

import com.example.myfcm.network.Response;
import com.example.myfcm.network.response.ResponseLogin;

public interface LoginListener {
    void success(Response<ResponseLogin> responseLogin);

    void fail(String message);
}
