package com.sdmd.mgava.mypetsapp.service;

import com.sdmd.mgava.mypetsapp.model.UserInfo;
import com.sdmd.mgava.mypetsapp.rest.UserInterface;
import retrofit2.Call;


public class UserService {

    public void getUser(String username, String password) {
        UserInterface userInterface =
                ApiClient.getClient().create(UserInterface.class);

        Call<UserInfo> call = userInterface.getUser(username, password);
    }
}
