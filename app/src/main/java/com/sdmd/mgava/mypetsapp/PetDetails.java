package com.sdmd.mgava.mypetsapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.text.DecimalFormat;

public class PetDetails extends Activity {

    // declare view objects
    ImageButton imgNavBack;
    ImageView imgPreview;
    TextView txtText, txtSubText, txtDescription;
    Button btnAdd;
    ScrollView sclDetail;
    ProgressBar prgLoading;
    TextView txtAlert;

    // declare dbhelper object
    static DBHelper dbhelper;

    // declare ImageLoader object
    ImageLoader imageLoader;

    // declare variables to store menu data
    String Pet_image, Pet_name, Menu_serve, Pet_description;
    double Menu_price;
    long Pet_ID;
    String PetDetailAPI;
    int IOConnect = 0;

    // create price format
    DecimalFormat formatData = new DecimalFormat("#.##");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_detail);

        // connect view objects with xml id
        imgNavBack = (ImageButton) findViewById(R.id.imgNavBack);
        imgPreview = (ImageView) findViewById(R.id.imgPreview);
        txtText = (TextView) findViewById(R.id.txtText);
        txtSubText = (TextView) findViewById(R.id.txtSubText);
        txtDescription = (TextView) findViewById(R.id.txtDescription);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        sclDetail = (ScrollView) findViewById(R.id.sclDetail);
        prgLoading = (ProgressBar) findViewById(R.id.prgLoading);
        txtAlert = (TextView) findViewById(R.id.txtAlert);

        // get screen device width and height
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int wPix = dm.widthPixels;
        int hPix = wPix / 2 + 50;

        // change menu image width and height
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(wPix, hPix);
        imgPreview.setLayoutParams(lp);

        imageLoader = new ImageLoader(PetDetails.this);
        dbhelper = new DBHelper(this);

        // get menu id that sent from previous page
        Intent iGet = getIntent();
        Pet_ID = iGet.getLongExtra("pet_id", 0);

        // Menu detail API url
        PetDetailAPI = Utils.serverUrl+"&pet_id="+ Pet_ID;

        // call asynctask class to request data from server
        new getDataTask().execute();

        // event listener to handle back button when clicked
        imgNavBack.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                // close database and back to previous page
                dbhelper.close();
                finish();
            }
        });

        // event listener to handle add button when clicked
        btnAdd.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                // show input dialog
                inputDialog();
            }
        });

    }

    // method to show number of order form
    void inputDialog(){

        // open database first
        try{
            dbhelper.openDataBase();
        }catch(SQLException sqle){
            throw sqle;
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle(R.string.app_name);
        alert.setCancelable(false);
        final EditText edtdescription = new EditText(this);
        edtdescription.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(edtdescription);

        alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String temp = edtdescription.getText().toString();
                int description = 0;

                // when add button clicked add menu to order table in database
                if(!temp.equalsIgnoreCase("")){
                    description = Integer.parseInt(temp);
                    if(dbhelper.isDataExist(Pet_ID)){
                        dbhelper.updateData(Pet_ID);
                    }else{
                        dbhelper.addData(Pet_ID, Pet_name);
                    }
                }else{
                    dialog.cancel();
                }


            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                // when cancel button clicked close dialog
                dialog.cancel();
            }
        });

        alert.show();
    }

    // asynctask class to handle parsing json in background
    public class getDataTask extends AsyncTask<Void, Void, Void>{

        // show progressbar first
        getDataTask(){
            if(!prgLoading.isShown()){
                prgLoading.setVisibility(0);
                txtAlert.setVisibility(8);
            }
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            // parse json data from server in background
            parseJSONData();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            // when finish parsing, hide progressbar
            prgLoading.setVisibility(8);
            // if internet connection and data available show data
            // otherwise, show alert text
            if((Pet_name != null) && IOConnect == 0){
                sclDetail.setVisibility(0);

                imageLoader.DisplayImage(Utils.serverUrl+ Pet_image,
                        PetDetails.this, imgPreview);

                txtText.setText(Pet_name);
                txtDescription.setText(Pet_description);
            }else{
                txtAlert.setVisibility(0);
            }
        }
    }

    // method to parse json data from server
    public void parseJSONData(){

        try {
            // request data from menu detail API
            HttpClient client = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(client.getParams(), 15000);
            HttpConnectionParams.setSoTimeout(client.getParams(), 15000);
            HttpUriRequest request = new HttpGet(PetDetailAPI);
            HttpResponse response = client.execute(request);
            InputStream atomInputStream = response.getEntity().getContent();


            BufferedReader in = new BufferedReader(new InputStreamReader(atomInputStream));

            String line;
            String str = "";
            while ((line = in.readLine()) != null){
                str += line;
            }

            // parse json data and store into tax and currency variables
            JSONObject json = new JSONObject(str);
            JSONArray data = json.getJSONArray("data"); // this is the "items: [ ] part

            for (int i = 0; i < data.length(); i++) {
                JSONObject object = data.getJSONObject(i);

                JSONObject petInfo = object.getJSONObject("Pet_detail");

                Pet_image = petInfo.getString("Pet_image");
                Pet_name = petInfo.getString("Pet_name");
                Pet_description = petInfo.getString("Description");

            }


        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            IOConnect = 1;
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    // close database before back to previous page
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        dbhelper.close();
        finish();
    }


    @Override
    public void onConfigurationChanged(final Configuration newConfig)
    {
        // Ignore orientation change to keep activity from restarting
        super.onConfigurationChanged(newConfig);
    }
}
