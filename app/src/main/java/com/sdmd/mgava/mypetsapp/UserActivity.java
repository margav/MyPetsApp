package com.sdmd.mgava.mypetsapp;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class UserActivity extends AppCompatActivity {


    public static final String EXTRA_NAME_OF_USER = "firstname";
    public static final String EXTRA_LASTNAME_OF_USER = "lastname";
    public static final String EXTRA_USERNAME = "username";
    public static final String EXTRA_PASSWORD = "password";
    public static final String CREATE_USER = "create user";
    public static final String GET_USER = "get user";
    public static final String CREATE_USER_RESULT = "create user result";
    public static final String GET_USER_RESULT = " get user result";
    public static final String EXTRA_USER_RESULT = "user result";
    public static final String EXTRA_USER_RESULT_ERROR = "No Results.";
    public static final String EXTRA_MESSAGE_FROM_SERVER = "message";
    public static final String EXTRA_KEY_FOR_USERNAME = "username";
    private static final String GET_USER_URL = "http://hodor.ait.gr:8080/myPets/api/user/";
    private static final String CREATE_USER_URL = "http://hodor.ait.gr:8080/myPets/api/user/";
    public static final String EXTRA_TEST_USERNAME = "extra.test.username";
    public static final String EXTRA_TEST_PASSWORD = "extra.test.password";

    public UserActivity() {
        super("User Activity");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        if (CREATE_USER.equals(action)) {
            createUser(intent);
        } else if (GET_USER.equals(action)) {
            getTheUser(intent);
        } else {
            throw new UnsupportedOperationException("No action " + action);
        }
    }
    private void createUser(Intent intent) {
        try {
            String username = intent.getStringExtra(EXTRA_USERNAME);
            String password = intent.getStringExtra(EXTRA_PASSWORD);
            String nameOfUser = intent.getStringExtra(EXTRA_NAME_OF_USER);
            String lastNameOfUser = intent.getStringExtra(EXTRA_LASTNAME_OF_USER);
            String stringURL = CREATE_USER_URL;
            URL url = new URL(stringURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.addRequestProperty("Content-Type", "application/json");
            User user = new User(username, password, nameOfUser, lastNameOfUser);
            String userJson = new Json().toJson(user);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
            writer.write(userJson);
            writer.flush();
            writer.close();
            conn.getOutputStream().close();
            conn.connect();
            int response = conn.getResponseCode();
            Intent resultIntent = new Intent(CREATE_USER_RESULT);
            if (response >= 200 && response < 300) {
                resultIntent.putExtra(RegisterActivity.KEY_FOR_STATUS, true);
                resultIntent.putExtra(EXTRA_MESSAGE_FROM_SERVER, response);
                resultIntent.putExtra(EXTRA_KEY_FOR_USERNAME, username);
            } else {
                resultIntent.putExtra(RegisterActivity.KEY_FOR_STATUS, false);
                resultIntent.putExtra(EXTRA_MESSAGE_FROM_SERVER, response);
            }
            LocalBroadcastManager.getInstance(this).sendBroadcast(resultIntent);
        } catch (Exception e) {
            Intent resultIntent = new Intent(CREATE_USER_RESULT);
            resultIntent.putExtra(RegisterActivity.KEY_FOR_STATUS, false);
            LocalBroadcastManager.getInstance(this).sendBroadcast(resultIntent);
        }
    }

    private void getTheUser(Intent intent) {
        InputStream is = null;
        try {
            String username = intent.getStringExtra(EXTRA_TEST_USERNAME);
            String password = intent.getStringExtra(EXTRA_TEST_PASSWORD);
            String stringURL = GET_USER_URL + username + "/" + password;

            URL url = new URL(stringURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();
            is = conn.getInputStream();
            String user = convertStreamToString(is);
            User registeredUser = new Json().fromJson(user, User.class);
            if (username.equals(registeredUser.getUserName()) && password.equals(registeredUser.getPassword()) && (response >= 200 && response < 300)) {
                Intent resultIntent = new Intent(GET_USER_RESULT);
                resultIntent.putExtra(RegisterActivity.KEY_FOR_STATUS, true);
                resultIntent.putExtra(EXTRA_MESSAGE_FROM_SERVER, response);
                resultIntent.putExtra(EXTRA_KEY_FOR_USERNAME, registeredUser.getUserName());
                LocalBroadcastManager.getInstance(this).sendBroadcast(resultIntent);
            } else {
                Intent resultIntent = new Intent(GET_USER_RESULT);
                resultIntent.putExtra(RegisterActivity.KEY_FOR_STATUS, false);
                resultIntent.putExtra(EXTRA_MESSAGE_FROM_SERVER, response);
                LocalBroadcastManager.getInstance(this).sendBroadcast(resultIntent);
            }
        } catch (Exception e) {
            Intent resultIntent = new Intent(GET_USER_RESULT);
            resultIntent.putExtra(EXTRA_USER_RESULT, EXTRA_USER_RESULT_ERROR);
            LocalBroadcastManager.getInstance(this).sendBroadcast(resultIntent);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private String convertStreamToString(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = is.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        return baos.toString();
    }
}



