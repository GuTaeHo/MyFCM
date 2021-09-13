package com.example.myfcm.activity;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ScrollView;

import com.example.myfcm.adapter.ChatAdapter;
import com.example.myfcm.firebase.MyFirebaseMessagingService;
import com.example.myfcm.R;
import com.example.myfcm.databinding.ActivityMainBinding;
import com.example.myfcm.model.Item;
import com.example.myfcm.util.ClipboardUtil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private static final String TAG = "tag";
    public ActivityMainBinding binding;
    private ArrayList<Item> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list = new ArrayList<>();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.btCheck.setOnClickListener(V -> {
            FirebaseMessaging.getInstance().getToken().addOnSuccessListener(
                    MainActivity.this, new OnSuccessListener<String>() {
                        @Override
                        public void onSuccess(String token) {
                            Log.d(TAG,"등록 Token : " + token);
                            // 클립보드에 토큰 값 저장
                            ClipboardUtil.saveClipboard(MainActivity.this, token);
                            showToast("ID 복사 완료!");
                        }
                    });

            // Firebase 인스턴스 확인
            Task<String> instanceID = FirebaseInstallations.getInstance().getId();
            Log.d(TAG,"확인된 인스턴스 ID : " + instanceID);
            // Toast.makeText(MainActivity.this, "" + instanceID, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    // FCM 서비스로 부터 인텐트를 받았을 때(클라우드 메시지 서버로 부터 메시지를 받았을 때) 처리
    protected void onNewIntent(Intent intent) {
        Log.d(TAG,"onNewIntent() 호출됨.");

        if (intent != null) {
            processIntent(intent);
        }

        super.onNewIntent(intent);
    }

    // 수신된 메시지 가공하여 출력
    private void processIntent(Intent intent) {
        Item item = intent.getParcelableExtra(MyFirebaseMessagingService.ITEM);

        if (item.getFrom() == null) {
            Log.d(TAG, "발신자가 null 입니다...");
            return;
        }

        // 보낸 시간
        // String sendTime = intent.getStringExtra(MyFirebaseMessagingService.TIME);
        // 메시지 내용
        // String contents = intent.getStringExtra(MyFirebaseMessagingService.CONTENTS);
        Log.d(TAG, "보낸이 : " + item.getFrom() + ", 시간 : " + item.getTime() + ", 내용 : " + item.getContents());

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        // 인텐트로 전달받은 아이템 리스트에 저장
        list.add(item);
        // 리사이클러뷰에 리스트 전달
        ChatAdapter adapter = new ChatAdapter(list);
        binding.recyclerview.setAdapter(adapter);

        binding.svLog.post(new Runnable() {
            @Override
            public void run() {
                // 스크롤 최하단으로 이동
                binding.svLog.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }
}