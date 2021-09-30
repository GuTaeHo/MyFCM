package com.example.myfcm.activity;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.myfcm.R;
import com.example.myfcm.application.MyApplication;
import com.example.myfcm.databinding.ActivityEtcBinding;
import com.example.myfcm.databinding.CommonToolbarBinding;
import com.example.myfcm.util.ClipboardUtil;

import java.util.concurrent.atomic.AtomicBoolean;

public class EtcActivity extends BaseActivity {
    private ActivityEtcBinding binding;
    private CommonToolbarBinding commonToolbarBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(EtcActivity.this, R.layout.activity_etc);
        commonToolbarBinding = DataBindingUtil.bind(binding.commonToolbar.toolbar);

        initLayout();
    }

    private void initLayout() {
        initCommonActionBarLayout(commonToolbarBinding, "기타", true);

        initListener();
    }

    private void initListener() {
        AtomicBoolean apiAct = new AtomicBoolean(false);
        AtomicBoolean fcmAct = new AtomicBoolean(false);

        binding.loApiToken.setOnClickListener(v -> {
            binding.tvApiToken.setText(MyApplication.getApiToken());
            apiAct.set(true);
        });
        binding.loFcmToken.setOnClickListener(v -> {
            binding.tvFcmToken.setText(MyApplication.getFcmToken());
            fcmAct.set(true);
        });

        binding.loApiToken.setOnLongClickListener(v -> {
            if (apiAct.get()) {
                ClipboardUtil.saveClipboard(EtcActivity.this, binding.tvApiToken.getText().toString());
                showToast("토큰이 클립보드에 저장되었습니다");
                return true;
            }
            return false;
        });

        binding.loFcmToken.setOnLongClickListener(v -> {
            if (fcmAct.get()) {
                ClipboardUtil.saveClipboard(EtcActivity.this, binding.tvFcmToken.getText().toString());
                showToast("토큰이 클립보드에 저장되었습니다");
                return true;
            }
            return false;
        });
    }
}