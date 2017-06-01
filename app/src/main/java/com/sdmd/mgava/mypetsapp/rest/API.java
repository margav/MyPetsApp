package com.sdmd.mgava.mypetsapp.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class API {


    public static final String BASE_URL = "http://hodor.ait.gr:8080/myPets/api";
    private static Retrofit retrofit = null;


    public static Retrofit getUser() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit createUser() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
