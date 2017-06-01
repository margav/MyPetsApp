package com.sdmd.mgava.mypetsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import static com.sdmd.mgava.mypetsapp.R.id.PetDetailsId;

public class BrowseActivity extends AppCompatActivity {

    DbHelper myDb;

    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petinfo_details);

        myDb = new DbHelper(this);


        final String petIntent = getIntent().getExtras().getString("Species");

        final List<PetInfo> petInfo = myDb.getPets();


        ListView listview = (ListView) findViewById(PetDetailsId);
        PetInfoAdapter petInfoAdapter = new PetInfoAdapter(getApplicationContext(), R.layout.activity_petinfo_details);
        listview.setAdapter(petInfoAdapter);

        for (i = 0; i < petInfo.size(); i++)

        {

            System.out.println(petInfo.get(i).getSpecies()+"   "+petIntent);
            if (petInfo.get(i).getSpecies().equals(petIntent)) {

                DataProvider dataProvider = new DataProvider(
                        petInfo.get(i).getImageUri(), petInfo.get(i).getName(), petInfo.get(i).getBreed());
                petInfoAdapter.add(dataProvider);

            }
        }


        final ListView listView = (ListView) findViewById(PetDetailsId);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                SharedPreferences preferences = getSharedPreferences("MY PREFERENCES", MODE_PRIVATE);

                String userDetailsName = preferences.getString("newUsername", "");
                System.out.println("auto einai2"+" "+ userDetailsName);
                if(!userDetailsName.isEmpty()) {


                         Intent intent = new Intent(BrowseActivity.this, PetInfoDetails.class);


                         String result = (String) listView.getItemAtPosition(position).toString();
                         intent.putExtra("Id", result);

                         startActivity(intent);
                  }else{

                Toast toast = Toast.makeText(BrowseActivity.this, "please register first", Toast.LENGTH_SHORT);
                toast.show();

            }

                     }


        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem logout = menu.findItem(R.id.idLogout);
        MenuItem login = menu.findItem(R.id.idLogin);
        SharedPreferences preferences = getSharedPreferences("MY PREFERENCES", MODE_PRIVATE);
        String userDetailsName = preferences.getString("newUsername", "");
        System.out.println(userDetailsName);
        if(userDetailsName.isEmpty()) {
            login.setVisible(true);
            logout.setVisible(false);

        }else{
            login.setVisible(false);
            logout.setVisible(true);

        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.idLogin:
                Intent LoginScreen = new Intent(BrowseActivity.this,LoginActivity.class);
                startActivity(LoginScreen);


                return true;

            case R.id.idLogout:
                SharedPreferences preferences = getSharedPreferences("MY PREFERENCES", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                Intent LognScreen = new Intent(BrowseActivity.this,MainActivity.class);
                startActivity(LognScreen);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
