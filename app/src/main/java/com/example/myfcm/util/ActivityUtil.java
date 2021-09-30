package com.example.myfcm.util;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.example.myfcm.model.Account;
import com.example.myfcm.model.People;

/**
 * 플래그 정리
 * - FLAG_ACTIVITY_NEW_TASK : 새로운 태스크를 생성하여 내부에 액티비티 추가
 * - FLAG_ACTIVITY_CLEAR_TOP : 태스크에 호출하려는 액티비티가 있다면 기존의 액티비티를 호출 [호출된 액티비티 상위의 액티비티는 모두 사라짐]
 * - FLAG_ACTIVITY_SINGLE_TOP : 호출하려는 액티비티가 태스크에 있다면(연속해서 쌓인다면), 새로 인스턴스를 만들지 않고 호출
 */

public class ActivityUtil {
    // 단일 액티비티 실행 (여러번 터치시 하나만 실행)
    public static void startSingleActivity(Context context, Class<?> c) {
        Intent intent = new Intent(context, c);
        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    // 단일 액티비티 실행, 부가데이터 사용
    public static void startSingleActivityExtras(Context context, Class<?> c, String key, People value) {
        Intent intent = new Intent(context, c);
        intent.putExtra(key, value);
        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    // 단일 액티비티 실행, String 부가데이터 사용
    public static void startSingleActivityExtrasString(Context context, Class<?> c, String key, String value) {
        Intent intent = new Intent(context, c);
        intent.putExtra(key, value);
        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    // 액티비티 스택을 모두 지우고 단일 엑티비티 실행
    public static void startNewActivity(Context context, Class<?> c) {
        Intent intent = new Intent(context, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    // Url 액티비티 실행 (웹브라우저 호출)
    public static void startUrlActivity(Context context, String url) {
        // url을 파싱해 적절한 액티비티 호출
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(intent);
    }
}
