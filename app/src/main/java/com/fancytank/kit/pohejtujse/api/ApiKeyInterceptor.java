package com.fancytank.kit.pohejtujse.api;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * from https://futurestud.io/tutorials/retrofit-2-how-to-add-query-parameters-to-every-request
 */

public class ApiKeyInterceptor implements Interceptor {
    private final static String API_KEY = "a";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build();

        // Request customization: add request headers
        Request.Builder requestBuilder = original.newBuilder()
                .url(url);

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
