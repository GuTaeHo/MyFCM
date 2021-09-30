package com.example.myfcm.activity;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.myfcm.R;
import com.example.myfcm.application.MyApplication;
import com.example.myfcm.databinding.ActivityLoginBinding;
import com.example.myfcm.databinding.CommonToolbarBinding;
import com.example.myfcm.dialog.NoticeDialog;
import com.example.myfcm.network.Response;
import com.example.myfcm.network.request.RequestLogin;
import com.example.myfcm.network.request.RequestUpdateToken;
import com.example.myfcm.network.response.ResponseLogin;
import com.example.myfcm.network.resultInterface.LoginListener;
import com.example.myfcm.network.resultInterface.UpdateTokenListener;
import com.example.myfcm.util.ActivityUtil;
import com.example.myfcm.util.KeyboardUtil;

public class LoginActivity extends BaseActivity {
    private ActivityLoginBinding binding;
    private CommonToolbarBinding commonToolbarBinding;
    private Animation shake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        commonToolbarBinding = DataBindingUtil.bind(binding.toolbar.toolbar);

        initLayout();
    }

    private void initLayout() {
        initCommonActionBarLayout(commonToolbarBinding, "로그인", false);
        shake = AnimationUtils.loadAnimation(this, R.anim.anim_shake);
        initListener();
    }

    private void initListener() {
        binding.containerLogin.setOnClickListener(v -> {
            hideKeyboard();
        });
        binding.tvEnterSignup.setOnClickListener(v -> {
            ActivityUtil.startSingleActivity(LoginActivity.this, SignupActivity.class);
        });
        binding.tvLogin.setOnClickListener(v -> {
            String id = binding.etId.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();
            
            if ("".equals(id)) {
                binding.etId.startAnimation(shake);
                binding.etId.requestFocus();
                showToast("아이디를 입력해주세요");
                KeyboardUtil.showKeyBoard(LoginActivity.this);
                return;
            }
            
            if ("".equals(password)) {
                binding.etPassword.startAnimation(shake);
                binding.etPassword.requestFocus();
                showToast("비밀번호를 입력해주세요");
                KeyboardUtil.showKeyBoard(LoginActivity.this);
                return;
            }

            RequestLogin requestLogin = new RequestLogin();
            requestLogin.setId(id);
            requestLogin.setPassword(password);

            // 로그인
            processLogin(requestLogin);
        });
    }

    private void processLogin(RequestLogin requestLogin) {
        showProgressDialog("로그인 중...");

        networkPresenter.login(requestLogin, new LoginListener() {
            @Override
            public void success(Response<ResponseLogin> responseLogin) {
                dismissProgressDialog();
                showToast("로그인 성공");

                // api 토큰 메모리에 저장
                MyApplication.setApiToken(responseLogin.getResultData().getApitoken());
                // 저장된 토큰과 서버 FCM 토큰 비교 다르면 호출
                if (!MyApplication.getFcmToken().equals(responseLogin.getResultData().getFcmtoken())) {
                    updateFcmToken(responseLogin.getResultData().getApitoken(), MyApplication.getFcmToken());
                }

                if (binding.cbInfo.isChecked()) {
                    // 자동 로그인 활성화
                    preferencesManager.setAutoLogin(true);
                    preferencesManager.setId(requestLogin.getId());
                    preferencesManager.setPw(requestLogin.getPassword());
                }

                ActivityUtil.startNewActivity(LoginActivity.this, MainActivity.class);
            }

            @Override
            public void fail(String message) {
                dismissProgressDialog();
                new NoticeDialog(LoginActivity.this)
                        .setMsg("LoginError\n" + message)
                        .setShowNegativeButton(false)
                        .show();
            }
        });
    }

    private void updateFcmToken(String apiToken, String fcmToken) {
        RequestUpdateToken request = new RequestUpdateToken();
        request.setFcmtoken(fcmToken);
        networkPresenter
                .updateToken(apiToken, request, new UpdateTokenListener() {
                    @Override
                    // FCM Token 이 업데이트 되면 호출
                    public void success(String token) {
                        preferencesManager.setFcmToken(token);
                    }

                    @Override
                    // FCM Token 이 동일하거나 API Token 이 다르면 호출
                    public void fail(String message) {
                        Log.d("updateFcmTokenFail", message);
                    }
                });
    }
}