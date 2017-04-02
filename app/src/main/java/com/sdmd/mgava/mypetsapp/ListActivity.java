package com.sdmd.mgava.mypetsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    static final String EXTRA_KEY = "";

    static List<PetInfo> petInfos;

    private ListView listView;
    private GridView gridView;

    private BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        if (getIntent().getStringExtra(EXTRA_KEY).equalsIgnoreCase("dogs")) {
            petInfos = PetInfoFactory.getListOfDogs();
        }
        if (getIntent().getStringExtra(EXTRA_KEY).equalsIgnoreCase("cats")) {
            petInfos = PetInfoFactory.getListOfCats();
        }
        if (getIntent().getStringExtra(EXTRA_KEY).equalsIgnoreCase("hamsters")) {
            petInfos = PetInfoFactory.getListOfLizards();
        }

        listView = (ListView) findViewById(R.id.list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListActivity.this ,BrowseActivity.class);
                intent.putExtra(BrowseActivity.EXTRA_KEY_FOR_LIST, position);
                startActivity(intent);
            }
        });

        gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListActivity.this ,BrowseActivity.class);
                intent.putExtra(BrowseActivity.EXTRA_KEY_FOR_LIST, position);
                startActivity(intent);
            }
        });

        adapter = new ItemAdapter(this, petInfos);

        showListView();

    }

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




}