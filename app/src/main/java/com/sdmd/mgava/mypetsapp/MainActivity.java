package com.sdmd.mgava.mypetsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    private EditText etEmail;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get Reference to variables
        etEmail = (EditText) findViewById(R.id.email);
        etPassword = (EditText) findViewById(R.id.password);

    }

    // Triggers when LOGIN Button clicked
    public void checkLogin(View arg0) {

        // Get text from email and passord field
        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();

        // Initialize  AsyncLogin() class with email and password
        new AsyncLogin().execute(email,password);

    }

    private class AsyncLogin extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }
        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL("http://localhost/test/login.inc.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("username", params[0])
                        .appendQueryParameter("password", params[1]);
                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return(result.toString());

                }else{

                    return("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();

            if(result.equalsIgnoreCase("true"))
            {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */

                Intent intent = new Intent(MainActivity.this,SuccessActivity.class);
                startActivity(intent);
                MainActivity.this.finish();

            }else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                Toast.makeText(MainActivity.this, "Invalid email or password", Toast.LENGTH_LONG).Show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(MainActivity.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).Show();

            }
        }

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
