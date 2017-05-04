package com.sdmd.mgava.mypetsapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private ListView listView;

    private PetDBSchemaHelper helper;
    private SQLiteDatabase database;

    private List<String> petCategories = PetFactory.getPetCategories();
    private List<Pet> listOfPets = PetFactory.getListOfPets();

    private BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new PetDBSchemaHelper(this);

        listView = (ListView) findViewById(R.id.list_view);
        gridView = (GridView) findViewById(R.id.grid_view);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, com.sdmd.mgava.mypetsapp.ListActivity.class);
                String category = (String) adapter.getItem(position);

                for (Pet p: listOfPets) {
                    if (p.getAnimals().equals(category)) { insertPet(p); }
                }

                startActivity(intent);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, com.sdmd.mgava.mypetsapp.ListActivity.class);
                String category = (String) adapter.getItem(position);

                for (Pet p: listOfPets) {
                    if (p.getAnimals().equals(category)) { insertPet(p); }
                }
                startActivity(intent);
            }
        });

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, petCategories);
        showListView();
    }

    //code for adding to database
    private ContentValues getContentValues(Pet p) {
        ContentValues values = new ContentValues();

        values.put(PetDBSchema.PetTable.NAME, p.getPetName());
        values.put(PetDBSchema.PetTable.DATE_OF_BIRTH, p.getDateOfBirth());
        values.put(PetDBSchema.PetTable.GENDER, p.getSex());
        values.put(PetDBSchema.PetTable.BREED, p.getBreed());
        values.put(PetDBSchema.PetTable.COLOUR, p.getColour());
        values.put(PetDBSchema.PetTable.DISTINGUISHING_MARKS, p.getDistinguishingMarks());
        values.put(PetDBSchema.PetTable.CHIP_ID, p.getChipId());
        values.put(PetDBSchema.PetTable.OWNER_NAME, p.getOwnerName());
        values.put(PetDBSchema.PetTable.OWNER_ADDRESS, p.getOwnerAddress());
        values.put(PetDBSchema.PetTable.OWNER_PHONE, p.getOwnerPhone());
        values.put(PetDBSchema.PetTable.VET_NAME, p.getVetName());
        values.put(PetDBSchema.PetTable.VET_ADDRESS, p.getVetAddress());
        values.put(PetDBSchema.PetTable.VET_PHONE, p.getVetPhone());
        values.put(PetDBSchema.PetTable.COMMENTS, p.getComments());
        values.put(PetDBSchema.PetTable.IMAGE_URI, p.getImageUri());

        return values;
    }

    private void insertPet(Pet p) {
        database = helper.getWritableDatabase();
        ContentValues values = getContentValues(p);
        database.insert(PetDBSchema.PetTable.TABLE_NAME, null, values);
    }

    //menu staff
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_btn_list_view:
                showListView();
                return true;
            case R.id.menu_btn_grid_view:
                showGridView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showListView() {
        gridView.setAdapter(null);
        gridView.setVisibility(View.GONE);

        listView.setAdapter(adapter);
        listView.setVisibility(View.VISIBLE);
    }

    private void showGridView() {
        listView.setAdapter(null);
        listView.setVisibility(View.GONE);

        gridView.setAdapter(adapter);
        gridView.setVisibility(View.VISIBLE);
    }

    //onResume
    @Override
    protected void onResume() {
        super.onResume();
        database = helper.getWritableDatabase();
        database.execSQL(PetDBSchema.SQL_DELETE_PETS);
        database.execSQL(PetDBSchema.SQL_CREATE_PETS);
    }

}
