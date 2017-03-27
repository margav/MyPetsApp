package com.sdmd.mgava.mypetsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mButtonDogs;
    private Button mButtonCats;
    private Button mButtonOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonDogs = (Button) findViewById(R.id.button_for_dogs);
        mButtonCats = (Button) findViewById(R.id.button_for_cats);
        mButtonOther = (Button) findViewById(R.id.button_for_other);

        mButtonDogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BrowseActivity.class);
                intent.putExtra(BrowseActivity.EXTRA_KEY_FOR_LIST, "dogs");
                startActivity(intent);
            }
        });

        mButtonCats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BrowseActivity.class);
                intent.putExtra(BrowseActivity.EXTRA_KEY_FOR_LIST, "cats");
                startActivity(intent);

            }
        });

        mButtonOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BrowseActivity.class);
                intent.putExtra(BrowseActivity.EXTRA_KEY_FOR_LIST, "other");
                startActivity(intent);
            }
        });

    }
}
