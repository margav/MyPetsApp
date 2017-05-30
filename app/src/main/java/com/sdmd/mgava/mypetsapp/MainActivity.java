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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import static com.sdmd.mgava.mypetsapp.R.id.Species;


public class MainActivity extends AppCompatActivity {
    DbHelper mDbHelper;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDbHelper = new DbHelper(this);
        mDbHelper.initDb();
        String[] species = {"Dogs", "Cats", "Other"};

        ArrayAdapter<String> adapter = new
                ArrayAdapter<String>(this, R.layout.species, species);

        ListView list=(ListView) findViewById(Species);
        list.setAdapter(adapter);

        ListView listView = (ListView) findViewById(Species);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = null;
                switch(position) {
                    case 0:
                        intent = new Intent(MainActivity.this, BrowseActivity.class);
                        intent.putExtra("Species", "Dog");
                        break;
                    case 1:
                        intent = new Intent(MainActivity.this, BrowseActivity.class);
                        intent.putExtra("Species", "Cat");
                        break;
                    case 2:
                        intent = new Intent(MainActivity.this, BrowseActivity.class);
                        intent.putExtra("Species", "Other");
                        break;
                }
                startActivity(intent);
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
        System.out.println("THIS IS:"+ userDetailsName);
        if(userDetailsName.isEmpty()) {
            login.setVisible(true);
            logout.setVisible(false);

        }else{
            login.setVisible(true);
            logout.setVisible(true);

        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.idLogin:
                Intent LoginScreen = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(LoginScreen);


                return true;

            case R.id.idLogout:
                SharedPreferences preferences = getSharedPreferences("MY PREFERENCES", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                LoginScreen = new Intent(MainActivity.this, MainActivity.class);
                startActivity(LoginScreen);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }



}















