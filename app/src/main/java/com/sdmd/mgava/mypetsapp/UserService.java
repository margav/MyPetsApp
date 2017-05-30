package com.sdmd.mgava.mypetsapp;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class UserService extends IntentService {

    private static final String LOG_TAG = "UserService";

    public static final String ACTION_CREATE_USER = "CREATE_USER";
    public static final String ACTION_GET_USERS = "GET_USERS";

    public static final String ACTION_CREATE_USER_RESULT = "CREATE_USER_RESULT";
    public static final String ACTION_GET_USERS_RESULT = "GET_USERS_RESULT";

    public static final String EXTRA_FIRST_NAME = "first.name";
    public static final String EXTRA_LAST_NAME = "last.name";
    public static final String EXTRA_USERNAME = "username";
    public static final String EXTRA_PASSWORD = "password";

    public static final String EXTRA_CREATE_USER_RESULT = "create.user.result";
    public static final String EXTRA_USERS_RESULT = "users.result";

    private static final String GET_USERS_URL = "http://hodor.ait.gr:8080/myPets/api/user/";
    private static final String CREATE_USERS_URL = "http://hodor.ait.gr:8080/myPets/api/user/";

    private static final String URL_USERNAME = "username";
    private static final String URL_PASSWORD = "password";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * //@param name Used to name the worker thread, important only for debugging.
     */
    public UserService() {
        super("User Service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        if (ACTION_CREATE_USER.equals(action)) {
            createUser(intent);
        } else if (ACTION_GET_USERS.equals(action)) {
            getUsers(intent);
        } else {
            throw new UnsupportedOperationException("No implementation for action " + action);
        }
    }

    private void createUser(Intent intent) {

        try {
            URL url = new URL(CREATE_USERS_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.addRequestProperty("Content-Type", "application/json");

            String firstName = intent.getStringExtra(EXTRA_FIRST_NAME);
            String lastName = intent.getStringExtra(EXTRA_LAST_NAME);
            String username = intent.getStringExtra(EXTRA_USERNAME);
            String password = intent.getStringExtra(EXTRA_PASSWORD);

            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setUserName(username);
            user.setPassword(password);

            String userJson = new Gson().toJson(user);

            Log.d(LOG_TAG , userJson);

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
            writer.write(userJson);
            writer.flush();
            writer.close();

            conn.getOutputStream().close();

            // Starts the post
            conn.connect();

            int response = conn.getResponseCode();

            Log.d(LOG_TAG, "The response is: " + response);

            Intent resultIntent = new Intent(ACTION_CREATE_USER_RESULT);
            resultIntent.putExtra(EXTRA_CREATE_USER_RESULT, "Created user. Server responded with status " + response);

            LocalBroadcastManager.getInstance(this).sendBroadcast(resultIntent);

        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception creating users", e);
        }
    }

    private void getUsers(Intent intent) {
        InputStream is = null;
         final String EXTRA_TEST_USERNAME = "extra.test.username";
         final String EXTRA_TEST_PASSWORD = "extra.test.password";

        String firstName = intent.getStringExtra(EXTRA_FIRST_NAME);
        String lastName = intent.getStringExtra(EXTRA_LAST_NAME);
        String username = intent.getStringExtra(EXTRA_TEST_USERNAME);
        String password = intent.getStringExtra(EXTRA_TEST_PASSWORD);
        String newURL = GET_USERS_URL + username + "/" + password ;
        try {
            URL url = new URL(newURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            // Starts the query
            conn.connect();

            int response = conn.getResponseCode();
            Log.d(LOG_TAG, "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a bitmap
            String result = convertStreamToString(is);

            Intent resultIntent = new Intent(ACTION_GET_USERS_RESULT);
            resultIntent.putExtra(EXTRA_USERS_RESULT, result);



            LocalBroadcastManager.getInstance(this).sendBroadcast(resultIntent);

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception fetching students", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Exception closing stream", e);
                }
            }
        }
    }

    private String convertStreamToString(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = is.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }

        return baos.toString();
    }

}
