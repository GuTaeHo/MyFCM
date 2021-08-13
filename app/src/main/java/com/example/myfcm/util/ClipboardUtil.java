package com.example.myfcm.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

public class ClipboardUtil {
    private static final String CLIP_BOARD_ID = "clip_board_id";

    public static void saveClipboard(Context context, String token) {
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = ClipData.newPlainText(CLIP_BOARD_ID, token);
        manager.setPrimaryClip(data);
    }
}
