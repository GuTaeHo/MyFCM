package com.example.myfcm.network.resultInterface;

public interface UpdateTokenListener {
    void success(String token);
    void fail(String message);
}
