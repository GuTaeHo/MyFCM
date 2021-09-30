package com.example.myfcm.network.resultInterface;

import com.example.myfcm.network.response.ResponseSignup;

public interface SignupListener {
    void success(ResponseSignup responseSignup);
    void fail(String message);
}
