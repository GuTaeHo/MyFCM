package com.example.myfcm.network.response;

import com.google.gson.annotations.SerializedName;

public class ResponseMessage {
    @SerializedName("success")
    private String success;

    @SerializedName("failure")
    private String failure;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getFailure() {
        return failure;
    }

    public void setFailure(String failure) {
        this.failure = failure;
    }
}
