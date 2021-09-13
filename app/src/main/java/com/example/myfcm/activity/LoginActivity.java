package com.example.myfcm.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.myfcm.R;
import com.example.myfcm.databinding.ActivityLoginBinding;
import com.example.myfcm.databinding.CommonToolbarBinding;
import com.example.myfcm.dialog.NoticeDialog;
import com.example.myfcm.network.Response;
import com.example.myfcm.network.request.RequestLogin;
import com.example.myfcm.network.response.ResponseLogin;
import com.example.myfcm.network.resultInterface.LoginListener;
import com.example.myfcm.util.ActivityUtil;

public class LoginActivity extends BaseActivity {
    private ActivityLoginBinding binding;
    private CommonToolbarBinding commonToolbarBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        commonToolbarBinding = DataBindingUtil.bind(binding.toolbar.toolbar);

        initLayout();
    }

    private void initLayout() {
        initCommonActionBarLayout(commonToolbarBinding, "로그인", false);

        initListener();
    }

    private void initListener() {
        binding.containerLogin.setOnClickListener(v -> {
            hideKeyboard();
        });
        binding.tvLogin.setOnClickListener(v -> {

            String id = binding.etId.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();
            
            if ("".equals(id)) {
                showToast("아이디를 입력해주세요");
                return;
            }
            
            if ("".equals(password)) {
                showToast("비밀번호를 입력해주세요");
                return;
            }

            RequestLogin requestLogin = new RequestLogin();
            requestLogin.setId(id);
            requestLogin.setPassword(password);
            requestLogin.setFcmtoken(preferencesManager.getFcmId());

            showProgressDialog("로그인 중...");

            networkPresenter.login(requestLogin, new LoginListener() {
                @Override
                public void success(Response<ResponseLogin> responseLogin) {
                    dismissProgressDialog();
                    showToast("로그인 성공");
                    ActivityUtil.startSingleActivity(LoginActivity.this, MainActivity.class);
                }

                @Override
                public void fail(String message) {
                    dismissProgressDialog();
                    new NoticeDialog(LoginActivity.this)
                            .setMsg("LoginError\n" + message).show();
                }
            });
        });
    }
}