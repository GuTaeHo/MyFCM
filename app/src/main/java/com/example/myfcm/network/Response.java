package com.example.myfcm.network;

import com.google.gson.annotations.SerializedName;

public class Response<T> {
    public static final int SUCCESS = 0;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("result_data")
    private T result_data;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getResultData() {
        return result_data;
    }
}
