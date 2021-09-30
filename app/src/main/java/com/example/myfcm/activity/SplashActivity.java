package com.example.myfcm.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.myfcm.R;
import com.example.myfcm.application.MyApplication;
import com.example.myfcm.dialog.NoticeDialog;
import com.example.myfcm.network.Response;
import com.example.myfcm.network.request.RequestLogin;
import com.example.myfcm.network.request.RequestUpdateToken;
import com.example.myfcm.network.response.ResponseLogin;
import com.example.myfcm.network.resultInterface.LoginListener;
import com.example.myfcm.network.resultInterface.UpdateTokenListener;
import com.example.myfcm.util.ActivityUtil;
import com.google.firebase.messaging.FirebaseMessaging;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

public class SplashActivity extends BaseActivity {
    public static final String TAG = "tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // permissionCheck();
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable()  {
            public void run() {
                getFcmToken();
            }
        }, 1000);
    }

    private void permissionCheck() {
        String[] permission = new String[]{};

        TedPermission.with(this)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    // 모든 권한이 허용된 경우 호출
                    public void onPermissionGranted() {
                        getFcmToken();
                    }

                    @Override
                    // 일부 또는 모든 권한이 허용되지 않은 경우 호출
                    // 인자인 deniedPermissions로 거부 된 권한 목록을 전달 받음
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        showToast(getString(R.string.permission_not_agree));
                        finish();
                    }
                })
                // 사용자가 권한을 거부했을때 출력되는 메시지 설정
                .setDeniedMessage(getString(R.string.ted_permission_denied_message))
                // 설정 화면으로 가는 버튼의 텍스트 설정
                .setGotoSettingButtonText(getString(R.string.ted_permission_go_to_setting_button_text))
                .setPermissions(permission)
                // 권한 체크 시작
                .check();
    }

    // FCM 토큰 확인
    private void getFcmToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.d(TAG,"Fetching FCM registration token failed " + task.getException());
                        return;
                    }
                    MyApplication.setFcmToken(task.getResult());
                    // 로그인 처리
                    processLogin(task.getResult());
                });
    }

    private void processLogin(String fcmToken) {
        if ("".equals(preferencesManager.getId())) {
            ActivityUtil.startNewActivity(this, LoginActivity.class);
        } else {
            RequestLogin request = new RequestLogin();
            request.setId(preferencesManager.getId());
            request.setPassword(preferencesManager.getPw());

            showProgressDialog("로그인 중...");
            networkPresenter.login(request, new LoginListener() {
                @Override
                public void success(Response<ResponseLogin> responseLogin) {
                    dismissProgressDialog();

                    // 전역 변수에 토큰 값 저장
                    MyApplication.setApiToken(responseLogin.getResultData().getApitoken());

                    // 앱 고유의 FCM 토큰과 서버의 FCM 토큰이 일치하지 않으면 토큰 갱신
                    if (!fcmToken.equals(responseLogin.getResultData().getFcmtoken())) {
                        updateFcmToken(responseLogin.getResultData().getApitoken(), fcmToken);
                    }
                    ActivityUtil.startNewActivity(SplashActivity.this, MainActivity.class);
                }

                @Override
                public void fail(String message) {
                    new NoticeDialog(SplashActivity.this)
                            .setBackPressButton(false)
                            .setMsg(message)
                            .onBackPressed();
                }
            });
        }
    }

    private void updateFcmToken(String apiToken, String fcmToken) {
        RequestUpdateToken request = new RequestUpdateToken();
        request.setFcmtoken(fcmToken);
        networkPresenter
                .updateToken(apiToken, request, new UpdateTokenListener() {
                    @Override
                    // FCM Token 이 서버에 업데이트 되면 호출
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