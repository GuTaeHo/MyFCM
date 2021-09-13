package com.example.myfcm.network;

import androidx.annotation.NonNull;

import com.example.myfcm.network.request.RequestLogin;
import com.example.myfcm.network.response.ResponseLogin;
import com.example.myfcm.network.resultInterface.FcmTokenListener;
import com.example.myfcm.network.request.RequestMessage;
import com.example.myfcm.network.response.ResponseMessage;
import com.example.myfcm.network.resultInterface.LoginListener;
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

    private Response<?> getError(ResponseBody errorBody) {
        Gson gson = new Gson();
        return gson.fromJson(errorBody.charStream(), Response.class);
    }
}
