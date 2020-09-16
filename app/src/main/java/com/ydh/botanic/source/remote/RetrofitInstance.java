package com.ydh.botanic.source.remote;

import android.util.Log;

import com.ydh.botanic.utils.singleton.SingletonAccessToken;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ydh.botanic.utils.Constants.CALL_TIMEOUT;
import static com.ydh.botanic.utils.Constants.CONNECTION_TIMEOUT;
import static com.ydh.botanic.utils.Constants.READ_TIMEOUT;
import static com.ydh.botanic.utils.Constants.WRITE_TIMEOUT;

public class RetrofitInstance {

    private static HttpLoggingInterceptor logger =
            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder client = new OkHttpClient.Builder()
            .addInterceptor(logger)
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    SingletonAccessToken accessToken = SingletonAccessToken.INSTANCE;
                    if (accessToken.getLastestAuth() != null){
                        String token = "Token " + accessToken.getLastestAuth();
                        request = request.newBuilder()
                                .addHeader("Authorization", token).build();
                        Log.i("tokenReceived","Token " + accessToken.getLastestAuth());
                    }
                    return chain.proceed(request);
                }
            })
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)// establish connection to server
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)// time between each byte read from the server
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)// time between each byte sent to server
            .callTimeout(CALL_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true);

    private static OkHttpClient.Builder clientGbif = new OkHttpClient.Builder()
            .addInterceptor(logger)
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)// establish connection to server
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)// time between each byte read from the server
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)// time between each byte sent to server
            .callTimeout(CALL_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true);

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl, Boolean self) {
            System.out.println(self);
            if (self){
                retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client.build())
                        .build();
            }
            else {
                retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(clientGbif.build())
                        .build();
            }
        return retrofit;
    }
}
