package com.example.myfcm.network.resultInterface;

import com.example.myfcm.network.response.ResponseMessage;

public interface FcmTokenListener {
    void success(ResponseMessage response);

    void fail(String message);
}
