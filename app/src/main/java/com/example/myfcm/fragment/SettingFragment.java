package com.example.myfcm.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myfcm.R;
import com.example.myfcm.activity.AccountActivity;
import com.example.myfcm.activity.EtcActivity;
import com.example.myfcm.databinding.FragmentSettingBinding;
import com.example.myfcm.util.ActivityUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {
    private FragmentSettingBinding binding;

    public SettingFragment() {
        // Required empty public constructor
    }

    public static SettingFragment newInstance(String param1, String param2) {
        return new SettingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false);
        initListener();

        return binding.getRoot();
    }

    public void initListener() {
        binding.loAccount.setOnClickListener(v -> {
            ActivityUtil.startSingleActivity(getActivity(), AccountActivity.class);
        });
        binding.loVersion.setOnClickListener(v -> {

        });
        binding.loEtc.setOnClickListener(v -> {
            ActivityUtil.startSingleActivity(getActivity(), EtcActivity.class);
        });
    };
}