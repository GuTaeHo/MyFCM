package com.example.myfcm.network;

import com.example.myfcm.network.request.RequestLogin;
import com.example.myfcm.network.request.RequestMessage;
import com.example.myfcm.network.response.ResponseLogin;
import com.example.myfcm.network.response.ResponseMessage;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class RetrofitFcmClient {
    // google FCM 도메인
    public static final String URL_FCM = "https://fcm.googleapis.com/";

    // 인증키
    private static final String HEADER = "key=AAAAC4WNdgo:APA91bHaz4TcyJ2FFCfj30ST55a65duccOaonAunpivvszKxGXtkvpW1HIymalykxturMFXNgdf9-XMtpT3rOSedwZGTEijmoArACbZEUIUpQdeg_puOs2xsajB1LPgPQFAERc8dmXVr";

    private final RetrofitInterface retrofitInterface;
    public static RetrofitFcmClient retrofitFCMClient = new RetrofitFcmClient();

    // 싱글톤으로 객체 생성
    private RetrofitFcmClient() {

        // HTTP 로깅 객체 생성
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // 헤더에 값 적재
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request newRequest  = chain.request().newBuilder()
                                .addHeader("Authorization", HEADER)
                                .build();
                        return chain.proceed(newRequest);
                    }
                }).addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(URL_FCM)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);
    }

    // API 통신 객체 반환
    public static RetrofitFcmClient getInstance() {
        return retrofitFCMClient;
    }

    public RetrofitInterface getInterface() {
        return retrofitInterface;
    }

    public interface RetrofitInterface {
        @POST("fcm/send")
        Call<ResponseMessage> sendMessage(@Body RequestMessage jsonObject);
    }
}