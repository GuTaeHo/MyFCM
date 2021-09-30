package com.example.myfcm.activity;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.ScrollView;

import com.example.myfcm.R;
import com.example.myfcm.adapter.ChatAdapter;
import com.example.myfcm.databinding.ActivityChatBinding;
import com.example.myfcm.databinding.CommonToolbarBinding;
import com.example.myfcm.firebase.MyFirebaseMessagingService;
import com.example.myfcm.fragment.PeopleFragment;
import com.example.myfcm.model.Item;
import com.example.myfcm.model.People;
import com.example.myfcm.util.ClipboardUtil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

public class ChatActivity extends BaseActivity {
    private ActivityChatBinding binding;
    private CommonToolbarBinding commonToolbarBinding;
    private ArrayList<Item> list;
    private BroadcastReceiver broadcastReceiver;

    private static final String TAG = "tag";
    public static final String RECEIVE_ACTION = "com.example.myfcm.MESSAGE_RECEIVED";

    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        commonToolbarBinding = DataBindingUtil.bind(binding.toolbar.toolbar);

        // initReceiver();
        initLayout();
    }

    private void initLayout() {
        // 사용자 정보 객체
        People people = getIntent().getParcelableExtra(PeopleFragment.FCM_TOKEN);

        initCommonActionBarLayout(commonToolbarBinding, people.getNickName(), true);
        list = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("message");

        reference.setValue("Hello, World");
    }

    private void initListener() {
    }

    private void processIntent(Intent intent) {
        Item item = intent.getParcelableExtra(MyFirebaseMessagingService.ITEM);

        if (item.getFrom() == null) {
            Log.d(TAG, "발신자가 null 입니다...");
            return;
        }

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(ChatActivity.this));

        list.add(item);
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

    private void initReceiver() {
        if (broadcastReceiver != null) return;

        final IntentFilter filter = new IntentFilter();

        filter.addAction(RECEIVE_ACTION);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                processIntent(intent);
            }
        };
        registerReceiver(broadcastReceiver, filter);
    }

}