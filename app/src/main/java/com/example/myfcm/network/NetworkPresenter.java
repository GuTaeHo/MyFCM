package com.example.myfcm.network;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myfcm.network.request.RequestLogin;
import com.example.myfcm.network.request.RequestSignup;
import com.example.myfcm.network.request.RequestUpdateToken;
import com.example.myfcm.network.response.ResponseLogin;
import com.example.myfcm.network.response.ResponsePeople;
import com.example.myfcm.network.response.ResponseSignup;
import com.example.myfcm.network.response.ResponseUpdateToken;
import com.example.myfcm.network.resultInterface.FcmTokenListener;
import com.example.myfcm.network.request.RequestMessage;
import com.example.myfcm.network.response.ResponseMessage;
import com.example.myfcm.network.resultInterface.LoginListener;
import com.example.myfcm.network.resultInterface.MemberListListener;
import com.example.myfcm.network.resultInterface.SignupListener;
import com.example.myfcm.network.resultInterface.UpdateTokenListener;
import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

// 통신 성공 & 실패의 리스너를 정의한 클래스
public class NetworkPresenter implements NetworkPresenterInterface {

    @Override
    public void fcmToken(RequestMessage message, FcmTokenListener listener) {
        RetrofitFcmClient
                .getInstance()
                .getInterface()
                .sendMessage(message)
                .enqueue(new Callback<ResponseMessage>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseMessage> call, @NonNull retrofit2.Response<ResponseMessage> response) {
                        try {
                            if (response.body() != null && response.isSuccessful()) {
                                // 통신 성공 시 http 바디 반환
                                listener.success(response.body());
                            } else {
                                listener.fail(response.errorBody().string());
                            }
                        } catch (Exception e) {
                            listener.fail("Exception " + e.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseMessage> call, Throwable t) {
                        listener.fail(t.toString());
                    }
                });
    }

    @Override
    public void login(RequestLogin requestLogin, LoginListener listener) {
        RetrofitClient
                .getInstance()
                .getInterface()
                .login(requestLogin)
                .enqueue(new Callback<Response<ResponseLogin>>() {
                    @Override
                    public void onResponse(@NonNull Call<Response<ResponseLogin>> call, retrofit2.Response<Response<ResponseLogin>> response) {
                        try {
                            if (response.body() != null && response.isSuccessful()) {
                                // 통신 성공 시 http 바디 반환
                                listener.success(response.body());
                            } else if (!response.isSuccessful() && response.errorBody() != null){
                                listener.fail(getError(response.errorBody()).getMessage());
                            }
                        } catch (Exception e) {
                            listener.fail("Exception " + e.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Response<ResponseLogin>> call, Throwable t) {
                        listener.fail(t.toString());
                    }
                });
    }

    @Override
    public void signup(RequestSignup requestSignup, SignupListener listener) {
        RetrofitClient
                .getInstance()
                .getInterface()
                .signup(requestSignup)
                .enqueue(new Callback<Response<ResponseSignup>>() {
                    @Override
                    public void onResponse(Call<Response<ResponseSignup>> call, retrofit2.Response<Response<ResponseSignup>> response) {
                        try {
                            if (response.body() != null && response.isSuccessful()) {
                                // 통신 성공 시 http 바디 반환
                                listener.success(response.body().getResultData());
                            } else if (!response.isSuccessful() && response.errorBody() != null){
                                listener.fail(getError(response.errorBody()).getMessage());
                            }
                        } catch (Exception e) {
                            listener.fail("Exception " + e.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Response<ResponseSignup>> call, Throwable t) {
                        listener.fail(t.toString());
                    }
                });
    }

    @Override
    public void memberList(String header, MemberListListener listener) {
        RetrofitClient
                .getInstance()
                .getInterface()
                .memberList(header)
                .enqueue(new Callback<Response<ResponsePeople>>() {
                    @Override
                    public void onResponse(Call<Response<ResponsePeople>> call, retrofit2.Response<Response<ResponsePeople>> response) {
                        try {
                            if (response.body() != null && response.isSuccessful()) {
                                // 통신 성공 시 유저들의 정보가 담긴 리스트 반환
                                listener.success(response.body().getResultData().getPeople());
                            } else if (!response.isSuccessful() && response.errorBody() != null){
                                listener.fail(getError(response.errorBody()).getMessage());
                            }
                        } catch (Exception e) {
                            listener.fail("Exception " + e.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Response<ResponsePeople>> call, Throwable t) {
                        listener.fail(t.toString());
                    }
                });
    }

    @Override
    public void updateToken(String header, RequestUpdateToken fcmToken, UpdateTokenListener listener) {
        RetrofitClient
                .getInstance()
                .getInterface()
                .updateToken(header, fcmToken)
                .enqueue(new Callback<Response<ResponseUpdateToken>>() {
                    @Override
                    public void onResponse(Call<Response<ResponseUpdateToken>> call, retrofit2.Response<Response<ResponseUpdateToken>> response) {
                        try {
                            if (response.body() != null && response.isSuccessful()) {
                                listener.success(response.body().getResultData().getFcmToken());
                            } else if (!response.isSuccessful() && response.errorBody() != null){
                                listener.fail(getError(response.errorBody()).getMessage());
                            }
                        } catch (Exception e) {
                            listener.fail("Exception " + e.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Response<ResponseUpdateToken>> call, Throwable t) {
                        listener.fail(t.toString());
                    }
                });
    }

    private Response<?> getError(ResponseBody errorBody) {
        Gson gson = new Gson();
        return gson.fromJson(errorBody.charStream(), Response.class);
    }
}
