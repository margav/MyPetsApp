package com.sdmd.mgava.mypetsapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class BrowseActivity extends AppCompatActivity {

    private DBSchemaHelper helper;

    private static final String[] PROJECTIONS = {DBSchema.PetTable.NAME, DBSchema.PetTable.DATE_OF_BIRTH,    DBSchema.PetTable.GENDER,
            DBSchema.PetTable.BREED,
            DBSchema.PetTable.COLOUR,
            DBSchema.PetTable.DISTINGUISHING_MARKS,
            DBSchema.PetTable.CHIP_ID,
            DBSchema.PetTable.OWNER_NAME,
            DBSchema.PetTable.OWNER_ADDRESS,
            DBSchema.PetTable.OWNER_PHONE,
            DBSchema.PetTable.VET_NAME,
            DBSchema.PetTable.VET_ADDRESS,
            DBSchema.PetTable.VET_PHONE,
            DBSchema.PetTable.COMMENTS ,
            DBSchema.PetTable.IMAGE_URI};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_2);

        helper = new DBSchemaHelper(this);

        getDesiredPets();
    }

    private void getDesiredPets() {
        SQLiteDatabase database = helper.getReadableDatabase();

        Cursor cursor = database.query(DBSchema.PetTable.TABLE_NAME, PROJECTIONS, null, null, null, null, null);
        String results = "";

        while (cursor.moveToNext()) {

            String NAME = cursor.getString(cursor.getColumnIndex(DBSchema.PetTable.NAME));
            String DATE_OF_BIRTH = cursor.getString(cursor.getColumnIndex(DBSchema.PetTable.DATE_OF_BIRTH));
            String GENDER = cursor.getString(cursor.getColumnIndex(DBSchema.PetTable.GENDER));
            String BREED = cursor.getString(cursor.getColumnIndex(DBSchema.PetTable.BREED));
            String COLOUR = cursor.getString(cursor.getColumnIndex(DBSchema.PetTable.COLOUR));
            String DISTINGUISHING_MARKS = cursor.getString(cursor.getColumnIndex(DBSchema.PetTable.DISTINGUISHING_MARKS));
            int CHIP_ID = cursor.getInt(cursor.getColumnIndex(DBSchema.PetTable.CHIP_ID));
            String OWNER_NAME = cursor.getString(cursor.getColumnIndex(DBSchema.PetTable.OWNER_NAME));
            String OWNER_ADDRESS = cursor.getString(cursor.getColumnIndex(DBSchema.PetTable.OWNER_ADDRESS));
            String OWNER_PHONE = cursor.getString(cursor.getColumnIndex(DBSchema.PetTable.OWNER_PHONE));
            String VET_NAME = cursor.getString(cursor.getColumnIndex(DBSchema.PetTable.VET_NAME));
            String VET_ADDRESS = cursor.getString(cursor.getColumnIndex(DBSchema.PetTable.VET_ADDRESS));
            String VET_PHONE = cursor.getString(cursor.getColumnIndex(DBSchema.PetTable.VET_PHONE));
            String COMMENTS = cursor.getString(cursor.getColumnIndex(DBSchema.PetTable.COMMENTS));
            int IMAGE_URI = cursor.getInt(cursor.getColumnIndex(DBSchema.PetTable.IMAGE_URI));


            results += IMAGE_URI + "\t" + NAME + "\t" + BREED + "\t" + DATE_OF_BIRTH + "\t" + GENDER + "\t" + COLOUR + "\t" + DISTINGUISHING_MARKS + "\t" + CHIP_ID + "\t" + OWNER_NAME + "\t" + OWNER_ADDRESS + "\t"
                    + OWNER_PHONE + "\t" + VET_NAME + "\t" + VET_ADDRESS + "\t" + VET_PHONE + "\t" + COMMENTS + "\n\n";
        }

        TextView resultsTextView = (TextView) findViewById(R.id.view_for_database_results2);
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
