package com.sdmd.mgava.mypetsapp;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends Activity {

    // declare view objects
    ImageButton imgSetting;
    ListView listMainMenu;
    ImageView imgCover;

    // declare dbhelper and adapter object
    static DBHelper dbHelper;
    MainMenuAdapter mma;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // connect view objects and xml id
        imgSetting = (ImageButton) findViewById(R.id.imgSetting);
        listMainMenu = (ListView) findViewById(R.id.listMainMenu);
        imgCover = (ImageView) findViewById(R.id.imgCover);

        // get screen device width and height
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int wPix = dm.widthPixels;
        int hPix = wPix / 2 + 50;

        // change cover image width and height
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(wPix, hPix);
        imgCover.setLayoutParams(lp);

        // checking internet connection
        if (!Utils.isNetworkAvailable(MainActivity.this)) {
            Toast.makeText(MainActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }

        mma = new MainMenuAdapter(this);
        dbHelper = new DBHelper(this);
        listMainMenu.setAdapter(mma);

        // create database
        try {
            dbHelper.createDataBase();
        }catch(IOException ioe){
            throw new Error("Unable to create database");
        }

        // then, the database will be open to use
        try{
            dbHelper.openDataBase();
        }catch(SQLException sqle){
            throw sqle;
        }

        // if user has already ordered food previously then show confirm dialog
        if(dbHelper.isPreviousDataExist()){
            showAlertDialog();
        }


        // event listener to handle list when clicked
        listMainMenu.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                // TODO Auto-generated method stub
                switch(position){
                    case 0:
                        // go to category page
                        Intent iMenuList = new Intent(MainActivity.this, CategoryList.class);
                        startActivity(iMenuList);
                        break;
                    case 1:
                        // go to my order page
                        Intent iMyOrder = new Intent(MainActivity.this, PetInfo.class);
                        startActivity(iMyOrder);
                        break;

                }
            }

        });


    }

    // show confirm dialog to ask user to delete previous order or not
    void showAlertDialog(){
        AlertDialog.Builder builder = 	new AlertDialog.Builder(this);
        builder.setTitle(R.string.confirm);
        builder.setMessage(getString(R.string.db_exist_alert));
        builder.setCancelable(false);
        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                // delete order data when yes button clicked
                dbHelper.deleteAllData();
                dbHelper.close();

            }
        });

        builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                // close dialog when no button clicked
                dbHelper.close();
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        dbHelper.close();
        finish();
    }

}
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.v7.app.ActionBarActivity;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.params.BasicHttpParams;
//import org.apache.http.params.HttpConnectionParams;
//import org.apache.http.params.HttpParams;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class MainActivity extends ActionBarActivity {
//
//    protected EditText username;
//    private EditText password;
//    protected String enteredUsername;
//    private final String serverUrl = "hodor.ait.gr:8080/myPets/api/";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        username = (EditText)findViewById(R.id.username_field);
//        password = (EditText)findViewById(R.id.password_field);
//        Button loginButton = (Button)findViewById(R.id.login);
//        Button registerButton = (Button)findViewById(R.id.register_button);
//
//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                enteredUsername = username.getText().toString();
//                String enteredPassword = password.getText().toString();
//
//                if(enteredUsername.equals("") || enteredPassword.equals("")){
//                    Toast.makeText(MainActivity.this, "Username or password must be filled", Toast.LENGTH_LONG).show();
//                    return;
//                }
//                if(enteredUsername.length() <= 1 || enteredPassword.length() <= 1){
//                    Toast.makeText(MainActivity.this, "Username or password length must be greater than one", Toast.LENGTH_LONG).show();
//                    return;
//                }
//                // request authentication with remote server4
//                AsyncDataClass asyncRequestObject = new AsyncDataClass();
//                asyncRequestObject.execute(serverUrl, enteredUsername, enteredPassword);
//            }
//        });
//
//        registerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//    private class AsyncDataClass extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... params) {
//
//            HttpParams httpParameters = new BasicHttpParams();
//            HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
//            HttpConnectionParams.setSoTimeout(httpParameters, 5000);
//
//            HttpClient httpClient = new DefaultHttpClient(httpParameters);
//            HttpPost httpPost = new HttpPost(params[0]);
//
//            String jsonResult = "";
//            try {
//                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
//                nameValuePairs.add(new BasicNameValuePair("username", params[1]));
//                nameValuePairs.add(new BasicNameValuePair("password", params[2]));
//                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//
//                HttpResponse response = httpClient.execute(httpPost);
//                jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
//
//            } catch (ClientProtocolException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return jsonResult;
//        }
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            System.out.println("Resulted Value: " + result);
//            if(result.equals("") || result == null){
//                Toast.makeText(MainActivity.this, "Server connection failed", Toast.LENGTH_LONG).show();
//                return;
//            }
//            int jsonResult = returnParsedJsonObject(result);
//            if(jsonResult == 0){
//                Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_LONG).show();
//                return;
//            }
//            if(jsonResult == 1){
//                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                intent.putExtra("USERNAME", enteredUsername);
//                intent.putExtra("MESSAGE", "You have been successfully login");
//                startActivity(intent);
//            }
//        }
//        private StringBuilder inputStreamToString(InputStream is) {
//            String rLine;
//            StringBuilder answer = new StringBuilder();
//            BufferedReader br = new BufferedReader(new InputStreamReader(is));
//            try {
//                while ((rLine = br.readLine()) != null) {
//                    answer.append(rLine);
//                }
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            return answer;
//        }
//    }
//    private int returnParsedJsonObject(String result){
//
//        JSONObject resultObject;
//        int returnedResult = 0;
//        try {
//            resultObject = new JSONObject(result);
//            returnedResult = resultObject.getInt("success");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return returnedResult;
//    }
//}


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
