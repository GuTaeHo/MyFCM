package com.example.myfcm.firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myfcm.activity.MainActivity;
import com.example.myfcm.application.MyPreferencesManager;
import com.example.myfcm.model.Item;
import com.example.myfcm.util.SoundPlayerUtil;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static final String TAG = "FMS";
    public static final String FROM = "from";
    public static final String TIME = "time";
    public static final String SENDER = "sender";
    public static final String CONTENTS = "contents";
    public static final String ITEM = "item";

    private NotificationManager manager;
    private Notification.Builder builder;

    private MyPreferencesManager preferencesManager;

    private static final String CHANNEL_ID = "channel_id";
    private static final String CHANNEL_NAME = "일반 알림";

    public MyFirebaseMessagingService() {

    }

    @Override
    // 새로운 토큰을 확인했을 때 호출 (파라미터로 전달되는 token 은 이 앱의 등록 id를 의미)
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d(TAG, "onNewToken() 호출됨 : " + token);
        preferencesManager = MyPreferencesManager.getInstance(this);
        // 쉐어드 프리퍼런스에 토큰값 저장
        preferencesManager.setFcmId(token);
    }

    @Override
    // 새로운 메시지를 받으면 호출 (파라미터로 전달되는 remoteMessage 에 서버에서 보낸 메시지가 담겨있음)
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // getFrom() 메서드를 통해 발신자 코드를 확인할 수 있음
        // String from = remoteMessage.getFrom();
        Map<String, String> data = remoteMessage.getData();
        String from = data.get(SENDER);
        String contents = data.get(CONTENTS);
        String time = data.get(TIME);
        
        // 노티 출력
        // showNotification(contents);

        Item item = new Item();
        // 메시지 발신자
        item.setFrom(from);
        // 메시지 시간 (수신 받은 형태 그대로)
        item.setTime(time);
        // 메시지 내용
        item.setContents(contents);
        
        // 푸시 메시지를 받았을 때 내용을 액티비티로 보내는 메서드
        sendToActivity(getApplicationContext(), item);

        // Log.d(TAG, "title : " + remoteMessage.getNotification().getTitle() + ", body : " + remoteMessage.getNotification().getBody());
        // Log.d(TAG, "from : " + from + ", contents : " + contents);
    }

    private void sendToActivity(Context context, Item item) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(ITEM, item);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_SINGLE_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TOP);

        context.startActivity(intent);
    }

    public void showNotification(String message) {
        // 시스템에서 노티피케이션 서비스 획득
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // 오레오 이상 수행
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // channel_id에 해당하는 채널이 없다면 수행
            if (manager.getNotificationChannel(CHANNEL_ID) == null) {
                // 채널을 생성할 때 필요한 인자 (채널 아이디, 채널 이름, 중요도)
                manager.createNotificationChannel(new NotificationChannel(
                        CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
                ));
            }
            // 설정된 이름으로 채널 생성
            builder = new Notification.Builder(MyFirebaseMessagingService.this, CHANNEL_ID);
        } else {
            builder = new Notification.Builder(this);
        }

        Intent intent = new Intent(this, MainActivity.class);
        // getActivity() : 액티비티 실행
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 101, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        /**
         * PendingIntent 의 FLAG에 대한 설정은
         * https://lesslate.github.io/android/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%ED%8E%9C%EB%94%A9-%EC%9D%B8%ED%85%90%ED%8A%B8(Pending-Intent)/
         * 참조
         */

        builder.setContentTitle("알림");
        builder.setContentText(message);
        builder.setSmallIcon(android.R.drawable.ic_menu_view);
        // 알림 클릭 시 삭제
        builder.setAutoCancel(true);
        // 빌더에 펜딩인텐트 적용
        builder.setContentIntent(pendingIntent);
        // 노티 생성
        Notification notification = builder.build();

        // 노티 출력
        manager.notify(2, notification);
    }
}