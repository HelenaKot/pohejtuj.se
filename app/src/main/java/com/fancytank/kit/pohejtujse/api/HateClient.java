package com.fancytank.kit.pohejtujse.api;

import com.fancytank.kit.pohejtujse.api.dto.Hate;
import com.fancytank.kit.pohejtujse.api.event.HttpExceptionEvent;
import com.fancytank.kit.pohejtujse.api.event.HttpOkEvent;

import org.greenrobot.eventbus.EventBus;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HateClient {
    static final String API_URL = "https://d5b914a9.ngrok.io";
    static final String POST_ENDPOINT = "/hate";

    HateService myService;

    public HateClient() {
        Retrofit retrofit = initRetrofit(API_URL);
        myService = retrofit.create(HateService.class);
    }

    private Retrofit initRetrofit(String baseUri) {
        return new Retrofit.Builder()
                .baseUrl(baseUri)
                .client(initHttpLoginClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private OkHttpClient initHttpLoginClient() {
        //Logging
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        return httpClient.build();
    }

    public void postHate(Hate hate) {
        myService.postHate(hate).enqueue(
                new Callback<Void>() {
                    @Override
                    public void onResponse(retrofit2.Call<Void> call, Response<Void> response) {
                        EventBus.getDefault().post(new HttpOkEvent());
                        System.out.println("ok");
                    }

                    @Override
                    public void onFailure(retrofit2.Call<Void> call, Throwable t) {
                        EventBus.getDefault().post(new HttpExceptionEvent(t));
                        System.out.println("shame");
                    }
                }
        );
    }
}
