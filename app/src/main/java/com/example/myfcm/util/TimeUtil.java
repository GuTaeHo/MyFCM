package com.example.myfcm.util;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    public static final String TAG = "exception";
    public static final int DAYTIME = 3;
    public static final int HOUR = 4;
    public static final int MINUTE = 5;
    public static final int SECOND = 6;

    public static String getCurrentTime() {
        long now = System.currentTimeMillis();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm");

        return dateFormat.format(date);
    }

    public static String getConvertTime(String time) {
        // 시간 형식
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
        Date date = new Date();

        try {
            // string to date
            date = dateFormat.parse(time);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

        // 설정한 형식으로 string 형 날짜 값 반환
        return dateFormat.format(date);
    }

    public static String getCustomTime(String time) {
        String[] timeSplit = time.split("/");

        return timeSplit[DAYTIME] + " " + timeSplit[HOUR] + ":" + timeSplit[MINUTE];
    }
}
