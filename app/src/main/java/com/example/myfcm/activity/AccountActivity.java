package com.example.myfcm.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.myfcm.R;
import com.example.myfcm.application.MyApplication;
import com.example.myfcm.databinding.ActivityAccountBinding;
import com.example.myfcm.databinding.CommonToolbarBinding;
import com.example.myfcm.dialog.NoticeDialog;
import com.example.myfcm.util.ActivityUtil;

public class AccountActivity extends BaseActivity {
    private ActivityAccountBinding binding;
    private CommonToolbarBinding commonToolbarBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(AccountActivity.this, R.layout.activity_account);
        commonToolbarBinding = DataBindingUtil.bind(binding.toolbar.toolbar);

        initLayout();
    }

    private void initLayout() {
        initCommonActionBarLayout(commonToolbarBinding, "계정", true);

        initListener();
    }

    private void initListener() {
        binding.tvLogout.setOnClickListener(v -> {
            new NoticeDialog(AccountActivity.this)
                    .setMsg("로그아웃 하시겠습니까?")
                    .setPositiveMsg("네")
                    .setNegativeMsg("아니요")
                    .setNoticeDialogCallbackListener(new NoticeDialog.NoticeDialogCallbackListener() {
                        @Override
                        public void positive() {
                            MyApplication.setApiToken("");
                            preferencesManager.setId("");
                            preferencesManager.setPw("");
                            preferencesManager.setNickName("");
                            preferencesManager.setApiToken("");
                            ActivityUtil.startNewActivity(AccountActivity.this, LoginActivity.class);
                        }

                        @Override
                        public void negative() {

                        }
                    }).show();

        });
    }
}