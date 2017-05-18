package com.sdmd.mgava.mypetsapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    protected EditText username;
    private EditText password;
    protected String enteredUsername;
    private final String serverUrl = "hodor.ait.gr:8080/myPets/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText)findViewById(R.id.username_field);
        password = (EditText)findViewById(R.id.password_field);
        Button loginButton = (Button)findViewById(R.id.login);
        Button registerButton = (Button)findViewById(R.id.register_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enteredUsername = username.getText().toString();
                String enteredPassword = password.getText().toString();

                if(enteredUsername.equals("") || enteredPassword.equals("")){
                    Toast.makeText(MainActivity.this, "Username or password must be filled", Toast.LENGTH_LONG).show();
                    return;
                }
                if(enteredUsername.length() <= 1 || enteredPassword.length() <= 1){
                    Toast.makeText(MainActivity.this, "Username or password length must be greater than one", Toast.LENGTH_LONG).show();
                    return;
                }
                // request authentication with remote server4
                AsyncDataClass asyncRequestObject = new AsyncDataClass();
                asyncRequestObject.execute(serverUrl, enteredUsername, enteredPassword);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private class AsyncDataClass extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
            HttpConnectionParams.setSoTimeout(httpParameters, 5000);

            HttpClient httpClient = new DefaultHttpClient(httpParameters);
            HttpPost httpPost = new HttpPost(params[0]);

            String jsonResult = "";
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("username", params[1]));
                nameValuePairs.add(new BasicNameValuePair("password", params[2]));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpClient.execute(httpPost);
                jsonResult = inputStreamToString(response.getEntity().getContent()).toString();

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonResult;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println("Resulted Value: " + result);
            if(result.equals("") || result == null){
                Toast.makeText(MainActivity.this, "Server connection failed", Toast.LENGTH_LONG).show();
                return;
            }
            int jsonResult = returnParsedJsonObject(result);
            if(jsonResult == 0){
                Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                return;
            }
            if(jsonResult == 1){
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.putExtra("USERNAME", enteredUsername);
                intent.putExtra("MESSAGE", "You have been successfully login");
                startActivity(intent);
            }
        }
        private StringBuilder inputStreamToString(InputStream is) {
            String rLine;
            StringBuilder answer = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            try {
                while ((rLine = br.readLine()) != null) {
                    answer.append(rLine);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return answer;
        }
    }
    private int returnParsedJsonObject(String result){

        JSONObject resultObject;
        int returnedResult = 0;
        try {
            resultObject = new JSONObject(result);
            returnedResult = resultObject.getInt("success");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnedResult;
    }
}


//    private GridView gridView;
//    private ListView listView;
//
//    private DBSchemaHelper helper;
//    private SQLiteDatabase database;
//
//    private List<String> petCategories = PetInfoFactory.getPetCategories();
//    private List<PetInfo> listOfPetInfos = PetInfoFactory.getListOfPetInfos();
//
//    private BaseAdapter adapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        helper = new DBSchemaHelper(this);
//
//        listView = (ListView) findViewById(R.id.list_view);
//        gridView = (GridView) findViewById(R.id.grid_view);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(MainActivity.this, com.sdmd.mgava.mypetsapp.ListActivity.class);
//                String category = (String) adapter.getItem(position);
//
//                for (PetInfo p: listOfPetInfos) {
//                    if (p.getAnimals().equals(category)) { insertPet(p); }
//                }
//
//                startActivity(intent);
//            }
//        });
//
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(MainActivity.this, com.sdmd.mgava.mypetsapp.ListActivity.class);
//                String category = (String) adapter.getItem(position);
//
//                for (PetInfo p: listOfPetInfos) {
//                    if (p.getAnimals().equals(category)) { insertPet(p); }
//                }
//                startActivity(intent);
//            }
//        });
//
//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, petCategories);
//        showListView();
//    }
//
//    //code for adding to database
//    private ContentValues getContentValues(PetInfo p) {
//        ContentValues values = new ContentValues();
//
//        values.put(DBSchema.PetInfoTable.NAME, p.getPetName());
//        values.put(DBSchema.PetInfoTable.DATE_OF_BIRTH, p.getDateOfBirth());
//        values.put(DBSchema.PetInfoTable.GENDER, p.getSex());
//        values.put(DBSchema.PetInfoTable.BREED, p.getBreed());
//        values.put(DBSchema.PetInfoTable.COLOUR, p.getColour());
//        values.put(DBSchema.PetInfoTable.DISTINGUISHING_MARKS, p.getDistinguishingMarks());
//        values.put(DBSchema.PetInfoTable.CHIP_ID, p.getChipId());
//        values.put(DBSchema.PetInfoTable.OWNER_NAME, p.getOwnerName());
//        values.put(DBSchema.PetInfoTable.OWNER_ADDRESS, p.getOwnerAddress());
//        values.put(DBSchema.PetInfoTable.OWNER_PHONE, p.getOwnerPhone());
//        values.put(DBSchema.PetInfoTable.VET_NAME, p.getVetName());
//        values.put(DBSchema.PetInfoTable.VET_ADDRESS, p.getVetAddress());
//        values.put(DBSchema.PetInfoTable.VET_PHONE, p.getVetPhone());
//        values.put(DBSchema.PetInfoTable.COMMENTS, p.getComments());
//        values.put(DBSchema.PetInfoTable.IMAGE_URI, p.getImageUri());
//
//        return values;
//    }
//
//    private void insertPet(PetInfo p) {
//        database = helper.getWritableDatabase();
//        ContentValues values = getContentValues(p);
//        database.insert(DBSchema.PetInfoTable.TABLE_NAME, null, values);
//    }
//
//    //menu staff
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.menu_btn_list_view:
//                showListView();
//                return true;
//            case R.id.menu_btn_grid_view:
//                showGridView();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    private void showListView() {
//        gridView.setAdapter(null);
//        gridView.setVisibility(View.GONE);
//
//        listView.setAdapter(adapter);
//        listView.setVisibility(View.VISIBLE);
//    }
//
//    private void showGridView() {
//        listView.setAdapter(null);
//        listView.setVisibility(View.GONE);
//
//        gridView.setAdapter(adapter);
//        gridView.setVisibility(View.VISIBLE);
//    }
//
//    //onResume
//    @Override
//    protected void onResume() {
//        super.onResume();
//        database = helper.getWritableDatabase();
//        database.execSQL(DBSchema.SQL_DELETE_PETS);
//        database.execSQL(DBSchema.SQL_CREATE_PETS);
//    }
//
//}
