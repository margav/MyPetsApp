package com.sdmd.mgava.mypetsapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ListActivity extends AppCompatActivity {

    private DBSchemaHelper helper;
    private SQLiteDatabase database;

    private static final String[] PROJECTIONS = {DBSchema.PetInfoTable.NAME, DBSchema.PetInfoTable.BREED, DBSchema.PetInfoTable.IMAGE_URI};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        helper = new DBSchemaHelper(this);

        getDesiredPets();

        Button btnDetails = (Button) findViewById(R.id.btn_for_details);
        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, com.sdmd.mgava.mypetsapp.BrowseActivity.class);
                startActivity(intent);
            }
        });
    }

    //code for accessing database
    private void getDesiredPets() {
        database = helper.getReadableDatabase();

        Cursor cursor = database.query(DBSchema.PetInfoTable.TABLE_NAME, PROJECTIONS, null, null, null, null, null);
        String results = "";

        while (cursor.moveToNext()) {

            String NAME = cursor.getString(cursor.getColumnIndex(DBSchema.PetInfoTable.NAME));
            String BREED = cursor.getString(cursor.getColumnIndex(DBSchema.PetInfoTable.BREED));
            int IMAGE_URI = cursor.getInt(cursor.getColumnIndex(DBSchema.PetInfoTable.IMAGE_URI));

            results += IMAGE_URI + "\t" + NAME + "\t" + BREED + "\n";
        }

        TextView resultsTextView = (TextView) findViewById(R.id.view_for_database_results);
        resultsTextView.setText(results);

        cursor.close();
    }

    //onDestroy
    @Override
    protected void onDestroy() {
        helper.close();
        super.onDestroy();
    }

}