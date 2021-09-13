package com.example.myfcm.network;

import com.example.myfcm.network.request.RequestLogin;
import com.example.myfcm.network.resultInterface.FcmTokenListener;
import com.example.myfcm.network.request.RequestMessage;
import com.example.myfcm.network.resultInterface.LoginListener;

// 액티비티에서 사용하는 통신 기능을 반드시 구현하도록 하는 인터페이스 선언
public interface NetworkPresenterInterface {
    public void fcmToken(RequestMessage message, FcmTokenListener listener);
    public void login(RequestLogin requestLogin, LoginListener listener);
}
