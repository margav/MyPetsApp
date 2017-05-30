package com.sdmd.mgava.mypetsapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PetInfoDetails extends AppCompatActivity {

    int count;
    Button button;
    Button button2;

    TextView t1;
    TextView t2;
    TextView t3;
    TextView t4;
    TextView t5;
    TextView t6;
    TextView t7;
    TextView t8;
    TextView t9;
    TextView t10;
    TextView t11;
    TextView t12;
    TextView t13;
    TextView t14;
    TextView t15;
    ImageView i1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        if (savedInstanceState != null) {
            count = savedInstanceState.getInt("bla", count);
        }

        DbHelper myDb = new DbHelper(this);

        final List<PetInfo> petInfo = myDb.getPets();


        final String petIntent1 = getIntent().getExtras().getString("Id");

        for (int i = 0; i < petInfo.size(); i++) {

            if (petInfo.get(i).getName().equals(petIntent1)) {

                final TextView t1 = (TextView) findViewById(R.id.IdName);
                t1.setText( " name :" + petInfo.get(i).getName());
                final TextView t2 = (TextView) findViewById(R.id.IdDateOfBirth);
                t2.setText(" Date Of Birth :" + petInfo.get(i).getDateOfBirth());
                final TextView t3 = (TextView) findViewById(R.id.idGender);
                t3.setText(" Gender :" + petInfo.get(i).getGender());
                final TextView t4 = (TextView) findViewById(R.id.IdBreed);
                t4.setText(" Breed :" + petInfo.get(i).getBreed());
                final TextView t5 = (TextView) findViewById(R.id.idColour);
                t5.setText( " Colour :" + petInfo.get(i).getColour());
                final TextView t6 = (TextView) findViewById(R.id.idDistinguishingMarks);
                t6.setText(" Distinguishing Marks :" + petInfo.get(i).getDistinguishingMarks());
                final TextView t7 = (TextView) findViewById(R.id.idChipId);
                t7.setText(" ChipID :" + petInfo.get(i).getChipID());
                final TextView t8 = (TextView) findViewById(R.id.idOwnerName);
                t8.setText(" Owner's Name :" + petInfo.get(i).getOwnerName());
                final TextView t9 = (TextView) findViewById(R.id.idOwnerAddress);
                t9.setText(" Owner's Address :" + petInfo.get(i).getOwnerAddress());
                final TextView t10 = (TextView) findViewById(R.id.idOwnerPhone);
                t10.setText( " Owner's Phone :" + petInfo.get(i).getOwnerPhone());
                final TextView t11 = (TextView) findViewById(R.id.idVetName);
                t11.setText(" Vet's Name :"  + petInfo.get(i).getVetName());
                final TextView t12 = (TextView) findViewById(R.id.idVetAddress);
                t12.setText(" Vet's Address :" + petInfo.get(i).getVetAddress());
                final TextView t13 = (TextView) findViewById(R.id.idVetPhone);
                t13.setText(" Vet's Phone :" + petInfo.get(i).getVetPhone());
                final TextView t14 = (TextView) findViewById(R.id.idComments);
                t14.setText(" Comments :" + petInfo.get(i).getComments());
                final TextView t15 = (TextView) findViewById(R.id.idspecies);
                t15.setText(" Species :" + petInfo.get(i).getSpecies());
                final ImageView i1 = (ImageView) findViewById(R.id.Pic);
                i1.setImageResource(petInfo.get(i).getImageUri());
            }

        }


    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putInt("bla", count);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem logout = menu.findItem(R.id.idLogout);
        MenuItem login = menu.findItem(R.id.idLogin);
        SharedPreferences preferences = getSharedPreferences("MYPREFS", MODE_PRIVATE);
        String userDetailsName = preferences.getString("newUsername", "");
        System.out.println(userDetailsName);

        login.setVisible(false);

        logout.setVisible(true);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.idLogout:
                SharedPreferences preferences = getSharedPreferences("MY PREFERENSES", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                Intent LognScreen = new Intent(PetInfoDetails.this,MainActivity.class);
                startActivity(LognScreen);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

