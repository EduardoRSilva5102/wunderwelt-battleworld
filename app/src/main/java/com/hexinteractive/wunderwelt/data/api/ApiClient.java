package com.hexinteractive.wunderwelt.data.api;

import com.hexinteractive.wunderwelt.BuildConfig;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ApiClient {
    public static final String COMIC_VINE_BASE_URL = "https://comicvine.gamespot.com/api/";
    private ApiClient() { }
    public static ComicVineApi create() {
        if (BuildConfig.COMIC_VINE_API.trim().isEmpty()) return null;
        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(12, TimeUnit.SECONDS).connectTimeout(5, TimeUnit.SECONDS)
                .followRedirects(false).retryOnConnectionFailure(false)
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    return chain.proceed(request.newBuilder()
                            .url(request.url().newBuilder().addQueryParameter("api_key", BuildConfig.COMIC_VINE_API)
                                    .addQueryParameter("format", "json").build())
                            .header("User-Agent", "Wunderwelt-Android-MVP/1.0").build());
                }).build();
        // No logging interceptor: URLs contain the developer's API key.
        return new Retrofit.Builder().baseUrl(COMIC_VINE_BASE_URL).client(client)
                .addConverterFactory(GsonConverterFactory.create()).build().create(ComicVineApi.class);
    }
}
