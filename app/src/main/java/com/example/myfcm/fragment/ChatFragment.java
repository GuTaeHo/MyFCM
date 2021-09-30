package com.example.myfcm.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myfcm.R;
import com.example.myfcm.activity.ChatActivity;
import com.example.myfcm.adapter.ChatListAdapter;
import com.example.myfcm.adapter.PeopleListAdapter;
import com.example.myfcm.databinding.FragmentChatBinding;
import com.example.myfcm.model.People;
import com.example.myfcm.util.ActivityUtil;

import java.util.ArrayList;

public class ChatFragment extends Fragment {
    private ArrayList<People> list;
    private FragmentChatBinding binding;

    private static final String ARG_PARAM1 = "param1";
    public static final String FCM_TOKEN = "fcm_token";


    public ChatFragment() {
        // Required empty public constructor
    }

    public static ChatFragment newInstance(ArrayList<People> list) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            list = getArguments().getParcelableArrayList(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false);
        binding.recyclerview.setVerticalScrollBarEnabled(false);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 인텐트로 전달받은 아이템 리스트에 저장

        // 리사이클러뷰에 리스트 전달
        ChatListAdapter adapter = new ChatListAdapter(list);
        adapter.setOnItemClickListener((v, pos)-> {
            People people = new People();
            people.setFcmtoken(list.get(pos).getFcmtoken());
            people.setNickName(list.get(pos).getNickName());
            // 채팅 시작, 상대방의 FCM 토큰 채팅 액티비티로 전달
            ActivityUtil.startSingleActivityExtras(getActivity(), ChatActivity.class, FCM_TOKEN, people);
        });
        binding.recyclerview.setAdapter(adapter);

        return binding.getRoot();
    }
}