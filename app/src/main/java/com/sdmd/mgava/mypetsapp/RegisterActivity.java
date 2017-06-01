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

import com.google.gson.Gson;

public class RegisterActivity extends AppCompatActivity {


    private BroadcastReceiver getAllStudentsResultBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String studentResults = intent.getStringExtra(UserService.EXTRA_USERS_RESULT);

            User[] users = new Gson().fromJson(studentResults, User[].class);

            String result = "";

            for (int i = 0; i < users.length; i++) {
                User user = users[i];
                result += user.getFirstName() + "\t" + user.getLastName() + "\t"  + "\n";
            }
        }
    };

    private BroadcastReceiver createStudentResultBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String resultMsg = intent.getStringExtra(UserService.EXTRA_CREATE_USER_RESULT);

            Toast.makeText(RegisterActivity.this, resultMsg, Toast.LENGTH_SHORT).show();
        }
    };





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        final EditText registerUsername = (EditText) findViewById(R.id.idRegUsername);
        final EditText registerPassword = (EditText) findViewById(R.id.idRegPassword);
        final EditText confirmPassWord = (EditText) findViewById(R.id.idRegConfirmPassword);
        final EditText Name = (EditText) findViewById(R.id.idName);
        final EditText surname = (EditText) findViewById(R.id.idSurname);



        Button btnRegister = (Button)findViewById(R.id.idRegButton);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username= registerUsername.getText().toString();
                final String password = registerPassword.getText().toString();
                final String confirmPassword= confirmPassWord.getText().toString();
                final String firstName = Name.getText().toString();
                final String lastName= surname.getText().toString();

                if(firstName.isEmpty() && password.isEmpty() && lastName.isEmpty() && username.isEmpty()){
                    Toast toast = Toast.makeText(RegisterActivity.this, "fill all fields", Toast.LENGTH_SHORT);
                    toast.show();
                }else if( !password.equals(confirmPassword)){
                    Toast toast = Toast.makeText(RegisterActivity.this, "password does not match confirmation password", Toast.LENGTH_SHORT);
                    toast.show();
                          }else if(password.length()<6){
                    Toast toast = Toast.makeText(RegisterActivity.this, "password must be at least 6 characters", Toast.LENGTH_SHORT);
                    toast.show();
                          } else {
                              insertStudent(firstName, lastName, username, password);
                              SharedPreferences preferences = getSharedPreferences("MY PREFERENCES", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("newUsername", username);
                            editor.apply();


                            Intent loginScreen = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(loginScreen);
                        }


            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);

        IntentFilter getStudentsResultIntentFilter = new IntentFilter(UserService.ACTION_GET_USERS_RESULT);
        broadcastManager.registerReceiver(getAllStudentsResultBroadcastReceiver, getStudentsResultIntentFilter);

        IntentFilter createStudentResultIntentFilter = new IntentFilter(UserService.ACTION_CREATE_USER_RESULT);
        broadcastManager.registerReceiver(createStudentResultBroadcastReceiver, createStudentResultIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);

        broadcastManager.unregisterReceiver(getAllStudentsResultBroadcastReceiver);
        broadcastManager.unregisterReceiver(createStudentResultBroadcastReceiver);
    }
    private void insertStudent(String firstName, String lastName, String username, String password) {
        Intent intent = new Intent(this, UserService.class);
        intent.setAction(UserService.ACTION_CREATE_USER);

        intent.putExtra(UserService.EXTRA_FIRST_NAME, firstName);
        intent.putExtra(UserService.EXTRA_LAST_NAME, lastName);
        intent.putExtra(UserService.EXTRA_USERNAME, username);
        intent.putExtra(UserService.EXTRA_PASSWORD, password);

        startService(intent);
    }

    private void getAllStudents() {
        Intent intent = new Intent(this, UserService.class);
        intent.setAction(UserService.ACTION_GET_USERS);

        startService(intent);
    }



}
