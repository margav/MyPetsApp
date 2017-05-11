package com.sdmd.mgava.mypetsapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

        static final String SHARED_PREFERENCES_FILE = "my.preferences.file";
        static final String SHARED_PREFERENCES_FILE_KEY_USERNAME = "username.preferences.file";
        static final String SHARED_PREFERENCES_FILE_KEY_STATUS = "status.preferences.file";
        private BroadcastReceiver getUserResultBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Boolean booleanResult = intent.getBooleanExtra(RegisterActivity.KEY_FOR_STATUS,false);
                int response = intent.getIntExtra(UserActivity.EXTRA_MESSAGE_FROM_SERVER, -1);
                String username = intent.getStringExtra(UserActivity.EXTRA_KEY_FOR_USERNAME);
                Toast.makeText(LoginActivity.this, "Response: " + response, Toast.LENGTH_LONG).show();
                if (booleanResult)            {

                    SharedPreferences preferences = getSharedPreferences(SHARED_PREFERENCES_FILE, 0);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(SHARED_PREFERENCES_FILE_KEY_USERNAME, username);
                    editor.putBoolean(SHARED_PREFERENCES_FILE_KEY_STATUS, true);
                    editor.apply();

                    Intent intent0 = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent0);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Didn't manage to log in.", Toast.LENGTH_LONG).show();
                }
            }
        };

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            Button button_register = (Button) findViewById(R.id.button_go_register);
            Button button_login = (Button) findViewById(R.id.button_log_in);

            button_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                }
            });
            button_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText ed_username = (EditText) findViewById(R.id.ed_username);
                    EditText ed_password = (EditText) findViewById(R.id.ed_password);
                    String username = ed_username.getText().toString();
                    String password = ed_password.getText().toString();
                    Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                    intent.setAction(UserActivity.GET_USER);
                    intent.putExtra(UserActivity.EXTRA_TEST_USERNAME, username);
                    intent.putExtra(UserActivity.EXTRA_TEST_PASSWORD, password);
                    startService(intent);
                }
            });
        }

        @Override
        protected void onResume() {
            super.onResume();

            LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
            IntentFilter getUserResultIntentFilter = new IntentFilter(UserActivity.GET_USER_RESULT);
            broadcastManager.registerReceiver(getUserResultBroadcastReceiver, getUserResultIntentFilter);

        }
        @Override
        protected void onPause() {
            super.onPause();
            LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
            broadcastManager.unregisterReceiver(getUserResultBroadcastReceiver);
        }

    }