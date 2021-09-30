package com.example.myfcm.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myfcm.R;
import com.example.myfcm.activity.ChatActivity;
import com.example.myfcm.adapter.PeopleListAdapter;
import com.example.myfcm.databinding.FragmentPeopleBinding;
import com.example.myfcm.model.People;
import com.example.myfcm.util.ActivityUtil;

import java.util.ArrayList;

public class PeopleFragment extends Fragment {
    private ArrayList<People> list;
    private FragmentPeopleBinding binding;

    private static final String ARG_PARAM1 = "param1";
    public static final String FCM_TOKEN = "fcm_token";

    public PeopleFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static PeopleFragment newInstance(ArrayList<People> list) {
        PeopleFragment fragment = new PeopleFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_people, container, false);

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 인텐트로 전달받은 아이템 리스트에 저장

        // 리사이클러뷰에 리스트 전달
        PeopleListAdapter adapter = new PeopleListAdapter(list);
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