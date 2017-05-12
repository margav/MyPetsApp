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

public class RegisterActivity extends AppCompatActivity {

    public static final String KEY_FOR_STATUS = "key_for_status";

    private BroadcastReceiver createUserResultBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Boolean booleanResult = intent.getBooleanExtra(RegisterActivity.KEY_FOR_STATUS, false);
            int response = intent.getIntExtra(UserActivity.EXTRA_MESSAGE_FROM_SERVER, -1);
            String username = intent.getStringExtra(UserActivity.EXTRA_KEY_FOR_USERNAME);

            Toast.makeText(RegisterActivity.this, "Status: " + String.valueOf(booleanResult), Toast.LENGTH_LONG).show();
            Toast.makeText(RegisterActivity.this, "Response: " + response, Toast.LENGTH_LONG).show();

            if (booleanResult) {

                SharedPreferences preferences = getSharedPreferences(LoginActivity.SHARED_PREFERENCES_FILE, 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(LoginActivity.SHARED_PREFERENCES_FILE_KEY_USERNAME, username);
                editor.putBoolean(LoginActivity.SHARED_PREFERENCES_FILE_KEY_STATUS, true);
                editor.apply();

                Intent intent2 = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent2);
            }
            else {
                Toast.makeText(RegisterActivity.this, "No registered user", Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button button_to_register = (Button) findViewById(R.id.button_to_register);

        button_to_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ed_register_username = (EditText) findViewById(R.id.ed_registry_username);
                EditText ed_register_password = (EditText) findViewById(R.id.ed_registry_password);
                EditText ed_register_confirm_password = (EditText) findViewById(R.id.ed_registry_confirm_password);
                EditText ed_register_users_name = (EditText) findViewById(R.id.ed_registry_users_name);
                EditText ed_register_users_surname = (EditText) findViewById(R.id.ed_registry_users_surname);

                String username = ed_register_username.getText().toString();
                String password = ed_register_password.getText().toString();
                String confirmedPassword = ed_register_confirm_password.getText().toString();
                String nameOfUser = ed_register_users_name.getText().toString().trim();
                String lastNameOfUser = ed_register_users_surname.getText().toString().trim();

                if (password.length() < 6) {
                    ed_register_password.setError("Must be at least 6 characters!");
                }
                else if (!password.equals(confirmedPassword)) {
                    ed_register_confirm_password.setError("Password and confirmed password must be match!");
                }
                else {
                    Intent intent = new Intent(RegisterActivity.this, UserActivity.class);
                    intent.setAction(UserActivity.CREATE_USER);

                    intent.putExtra(UserActivity.EXTRA_NAME_OF_USER, nameOfUser);
                    intent.putExtra(UserActivity.EXTRA_LASTNAME_OF_USER, lastNameOfUser);
                    intent.putExtra(UserActivity.EXTRA_USERNAME, username);
                    intent.putExtra(UserActivity.EXTRA_PASSWORD, password);

                    startService(intent);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);

        IntentFilter createUserResultIntentFilter = new IntentFilter(UserActivity.CREATE_USER_RESULT);
        broadcastManager.registerReceiver(createUserResultBroadcastReceiver, createUserResultIntentFilter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastManager.unregisterReceiver(createUserResultBroadcastReceiver);
    }

    private class KEY_FOR_STATUS {
    }
}