package com.example.myfcm.network.request;

import com.example.myfcm.model.Data;
import com.example.myfcm.model.Notification;

public class RequestMessage {
    private String to;
    private String priority;
    private Data data;
    private Notification notification;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}
