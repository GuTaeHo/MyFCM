package com.example.myfcm.activity;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.myfcm.R;
import com.example.myfcm.application.MyApplication;
import com.example.myfcm.databinding.ActivitySignupBinding;
import com.example.myfcm.databinding.CommonToolbarBinding;
import com.example.myfcm.dialog.NoticeDialog;
import com.example.myfcm.network.request.RequestSignup;
import com.example.myfcm.network.response.ResponseSignup;
import com.example.myfcm.network.resultInterface.SignupListener;
import com.example.myfcm.util.ActivityUtil;
import com.example.myfcm.util.KeyboardUtil;
import com.example.myfcm.util.PatternUtil;

public class SignupActivity extends BaseActivity {
    private ActivitySignupBinding binding;
    private CommonToolbarBinding commonToolbarBinding;
    private Animation shake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(SignupActivity.this, R.layout.activity_signup);
        commonToolbarBinding = DataBindingUtil.bind(binding.toolbar.toolbar);

        initLayout();
    }

    private void initLayout() {
        initCommonActionBarLayout(commonToolbarBinding, "회원가입", true);

        initListener();
    }

    private void initListener() {
        binding.containerSignup.setOnClickListener(v -> {
            hideKeyboard();
        });
        binding.tvReturn.setOnClickListener(v -> {
            finish();
        });
        shake = AnimationUtils.loadAnimation(this, R.anim.anim_shake);
        binding.tvSignup.setOnClickListener(v -> {
            processSignup();
        });
    }

    private void processSignup() {
        String id = binding.etId.getText().toString().trim();
        String nickName = binding.etNickName.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();
        String passwordCheck = binding.etPasswordCheck.getText().toString().trim();

        if ("".equals(id)) {
            binding.etId.startAnimation(shake);
            binding.etId.requestFocus();
            showToast("아이디를 입력해주세요");
            KeyboardUtil.showKeyBoard(SignupActivity.this);
            return;
        }

        if ("".equals(nickName)) {
            binding.etNickName.startAnimation(shake);
            binding.etNickName.requestFocus();
            showToast("닉네임을 입력해주세요");
            KeyboardUtil.showKeyBoard(SignupActivity.this);
            return;
        }

        if ("".equals(password)) {
            binding.etPassword.startAnimation(shake);
            binding.etPassword.requestFocus();
            showToast("비밀번호를 입력해주세요");
            KeyboardUtil.showKeyBoard(SignupActivity.this);
            return;
        }

        if ("".equals(passwordCheck)) {
            binding.etPasswordCheck.startAnimation(shake);
            binding.etPasswordCheck.requestFocus();
            showToast("비밀번호 확인란을 입력해주세요");
            KeyboardUtil.showKeyBoard(SignupActivity.this);
            return;
        }
        
        if (!password.equals(passwordCheck)) {
            binding.etPassword.startAnimation(shake);
            binding.etPasswordCheck.startAnimation(shake);
            binding.etPassword.requestFocus();
            showToast("비밀번호가 서로 일치하지 않습니다");
            KeyboardUtil.showKeyBoard(SignupActivity.this);
            return;
        }

        if (!PatternUtil.isEmailPattern(id)) {
            binding.etId.startAnimation(shake);
            binding.etId.requestFocus();
            showToast("이메일 형식에 맞지 않습니다");
            KeyboardUtil.showKeyBoard(SignupActivity.this);
            return;
        }

        if (!PatternUtil.isNickNamePattern(nickName)) {
            binding.etNickName.startAnimation(shake);
            binding.etNickName.requestFocus();
            showToast("닉네임은 5자 이상 20자 이하 입니다");
            KeyboardUtil.showKeyBoard(SignupActivity.this);
            return;
        }
        
        if (!PatternUtil.isPassWordPattern(password)) {
            binding.etPassword.startAnimation(shake);
            binding.etPassword.requestFocus();
            showToast("비밀번호 형식에 맞지 않습니다");
            KeyboardUtil.showKeyBoard(SignupActivity.this);
            return;
        }

        RequestSignup requestSignup = new RequestSignup();
        requestSignup.setId(id);
        requestSignup.setNickname(nickName);
        requestSignup.setPassword(password);
        requestSignup.setFcmtoken(MyApplication.getFcmToken());

        showProgressDialog("회원가입 중...");

        networkPresenter
                .signup(requestSignup, new SignupListener() {
                    @Override
                    public void success(ResponseSignup responseSignup) {
                        dismissProgressDialog();

                        // 회원 가입 성공 시 내부 저장소에 FCM 토큰 저장, API 토큰 저장
                        preferencesManager.setFcmToken(MyApplication.getFcmToken());
                        preferencesManager.setApiToken(responseSignup.getApitoken());
                        // 메모리(임시 저장소)에 API 토큰 저장
                        MyApplication.setApiToken(responseSignup.getApitoken());
                        
                        if (binding.cbAutoLogin.isChecked()) {
                            preferencesManager.setId(id);
                            preferencesManager.setPw(password);
                            preferencesManager.setNickName(nickName);
                        }
                        ActivityUtil.startNewActivity(SignupActivity.this, MainActivity.class);
                    }

                    @Override
                    public void fail(String message) {
                        dismissProgressDialog();
                        new NoticeDialog(SignupActivity.this)
                                .setMsg(message)
                                .setShowNegativeButton(false)
                                .show();
                    }
                });
    }
}