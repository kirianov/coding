package com.kirianov.coding.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {

    private static final int TIMEOUT_SECONDS = 30;
    public static final String BASE_URL = "https://data.cityofnewyork.us/";

    private static NetworkClient instance;
    private final NetworkServices networkServices;

    public NetworkClient() {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(clientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        networkServices = retrofit.create(NetworkServices.class);
    }

    public static NetworkClient getInstance() {
        if (instance == null) {
            instance = new NetworkClient();
        }
        return instance;
    }

    public NetworkServices getNetworkServices() {
        return networkServices;
    }
}
