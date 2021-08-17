package com.example.myfcm.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    private String from;
    private String time;
    private String contents;

    public Item() {
        // don't write anything on this constructor
    }

    public Item(Parcel in) {
        from = in.readString();
        time = in.readString();
        contents = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(from);
        dest.writeString(time);
        dest.writeString(contents);
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
