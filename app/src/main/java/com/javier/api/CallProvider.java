package com.javier.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CallProvider {
    public static ProductService call(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.walmart.com.mx/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(ProductService.class);
    }
}
