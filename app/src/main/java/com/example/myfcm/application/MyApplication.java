package com.example.myfcm.application;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import com.example.myfcm.util.SoundPlayerUtil;

public class MyApplication extends Application {
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
}
