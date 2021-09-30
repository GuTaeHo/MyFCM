package com.example.myfcm.network;

import com.example.myfcm.network.request.RequestLogin;
import com.example.myfcm.network.request.RequestSignup;
import com.example.myfcm.network.request.RequestUpdateToken;
import com.example.myfcm.network.resultInterface.FcmTokenListener;
import com.example.myfcm.network.request.RequestMessage;
import com.example.myfcm.network.resultInterface.LoginListener;
import com.example.myfcm.network.resultInterface.MemberListListener;
import com.example.myfcm.network.resultInterface.SignupListener;
import com.example.myfcm.network.resultInterface.UpdateTokenListener;

// 액티비티에서 사용하는 통신 기능을 반드시 구현하도록 하는 인터페이스 선언
public interface NetworkPresenterInterface {
    void fcmToken(RequestMessage message, FcmTokenListener listener);
    void login(RequestLogin requestLogin, LoginListener listener);
    void signup(RequestSignup requestSignup, SignupListener listener);
    void memberList(String header, MemberListListener listener);
    void updateToken(String header, RequestUpdateToken fcmToken, UpdateTokenListener listener);
}
