package com.example.myfcm.application;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import com.example.myfcm.util.SoundPlayerUtil;

public class MyApplication extends Application {
    // 사용자 정보 저장
    public static String apiToken;
    public static String fcmToken;
    public static String id;
    public static String nickName;
    public static String registration;

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        initSound();
    }

    public void initSound() {
        // initialize sound player
        SoundPlayerUtil.initSounds(this);
    }

    public static void setApiToken(String token) {
        MyApplication.apiToken = token;
    }

    public static String getApiToken() {
        if (apiToken != null) {
            return apiToken;
        }
        return "";
    }

    public static String getFcmToken() {
        if (fcmToken != null) {
            return fcmToken;
        }
        return "";
    }

    public static void setFcmToken(String fcmToken) {
        MyApplication.fcmToken = fcmToken;
    }
}
