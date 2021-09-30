package com.example.myfcm.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class People implements Parcelable {

    @SerializedName("thumbnail")
    private String thumbnail;

    @SerializedName("nickname")
    private String nickname;

    @SerializedName("fcmtoken")
    private String fcmtoken;

    public People() {
        // don't write anything on this constructor
    }

    public People(Parcel in) {
        thumbnail = in.readString();
        nickname = in.readString();
        fcmtoken = in.readString();
    }

    public static final Creator<People> CREATOR = new Creator<People>() {
        @Override
        public People createFromParcel(Parcel in) {
            return new People(in);
        }

        @Override
        public People[] newArray(int size) {
            return new People[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(thumbnail);
        dest.writeString(nickname);
        dest.writeString(fcmtoken);
    }

    public String getThumbNail() {
        return thumbnail;
    }

    public void setThumbNail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getNickName() {
        return nickname;
    }

    public void setNickName(String nickname) {
        this.nickname = nickname;
    }

    public String getFcmtoken() {
        return fcmtoken;
    }

    public void setFcmtoken(String fcmtoken) {
        this.fcmtoken = fcmtoken;
    }
}
