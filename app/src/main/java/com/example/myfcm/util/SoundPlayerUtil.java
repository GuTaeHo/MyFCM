package com.example.myfcm.util;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

import com.example.myfcm.R;

import java.util.HashMap;

public class SoundPlayerUtil {
    public static final int DING_DONG = R.raw.snd_dingdong;
    public static final int SUCCESS = R.raw.snd_success;
    // 소리 재생 X
    public static final int MUTE = 0;

    private static SoundPool soundPool;
    private static HashMap<Integer, Integer> soundPoolMap;

    // sound media initialize
    public static void initSounds(Context context) {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();

        // 맵에 음원파일 등록
        soundPoolMap = new HashMap<>(2);
        soundPoolMap.put(DING_DONG, soundPool.load(context, DING_DONG, 1));
        soundPoolMap.put(SUCCESS, soundPool.load(context, SUCCESS, 2));
    }

    // play music source
    public static void play(int select){
        if (soundPoolMap.containsKey(select)) {
            soundPool.play(soundPoolMap.get(select), 1, 1, 1, 0, 1f);
        }
    }
}
