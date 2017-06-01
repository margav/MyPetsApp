package com.sdmd.mgava.mypetsapp.rest;

import com.sdmd.mgava.mypetsapp.model.UserInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserInterface {

    @GET("/user/{username}/{password}")
    Call<UserInfo> getUser(@Path("username") String username, @Query("password") String password);
}
