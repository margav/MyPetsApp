package com.sdmd.mgava.mypetsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import com.sdmd.mgava.mypetsapp.model.UserInfo;
import com.sdmd.mgava.mypetsapp.service.UserService;



public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setListeners();
    }

    /**
     * Setup the various activity listeners
     */
    private void setListeners() {
        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                login();
            }
        });

        TextView registerTextView = (TextView) findViewById(R.id.registerTextView);
        registerTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login() {
        Intent intent = new Intent(this, UserService.class);
       // intent.setAction(UserService.ACTION_GET_USERS);
        intent.putExtra("username", ((EditText) findViewById(R.id.usernameText)).getText().toString());
        intent.putExtra("password", ((EditText) findViewById(R.id.passwordText)).getText().toString());
        startService(intent);
    }

    private void afterLogin(String userResult) {
        if (!userResult.isEmpty()) {
            UserInfo user = new Gson().fromJson(userResult, UserInfo.class);
            SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", user.getUsername());
            editor.apply();
        } else {
            Toast.makeText(this, "Username and password are not correct!", Toast.LENGTH_LONG).show();
        }

    }
}
