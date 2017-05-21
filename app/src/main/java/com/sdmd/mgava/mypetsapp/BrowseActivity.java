package com.sdmd.mgava.mypetsapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class BrowseActivity extends AppCompatActivity {

    public static final String EXTRA_PET_CATEGORY = "petInfo.category";
    public static final String EXTRA_FOR_STATUS = "status";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        int category = getIntent().getIntExtra(EXTRA_PET_CATEGORY, -1);
        boolean status = getIntent().getBooleanExtra(EXTRA_FOR_STATUS, false);
        FragChoice fragment = new FragChoice();
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        args.putInt(FragChoice.EXTRA_PET_CATEGORY2, category);
        args.putBoolean(FragChoice.EXTRA_FOR_STATUS2, status);
        fragment.setArguments(args);
        trans.replace(R.id.fragment_container, fragment);
        trans.commit();
    }
}
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.widget.TextView;
//
//public class BrowseActivity extends AppCompatActivity {
//
//    private DBSchemaHelper helper;
//
//    private static final String[] PROJECTIONS = {DBSchema.PetInfoTable.NAME, DBSchema.PetInfoTable.DATE_OF_BIRTH,    DBSchema.PetInfoTable.GENDER,
//            DBSchema.PetInfoTable.BREED,
//            DBSchema.PetInfoTable.COLOUR,
//            DBSchema.PetInfoTable.DISTINGUISHING_MARKS,
//            DBSchema.PetInfoTable.CHIP_ID,
//            DBSchema.PetInfoTable.OWNER_NAME,
//            DBSchema.PetInfoTable.OWNER_ADDRESS,
//            DBSchema.PetInfoTable.OWNER_PHONE,
//            DBSchema.PetInfoTable.VET_NAME,
//            DBSchema.PetInfoTable.VET_ADDRESS,
//            DBSchema.PetInfoTable.VET_PHONE,
//            DBSchema.PetInfoTable.COMMENTS ,
//            DBSchema.PetInfoTable.IMAGE_URI};
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_browse);
//
//        helper = new DBSchemaHelper(this);
//
//        getDesiredPets();
//    }
//
//    private void getDesiredPets() {
//        SQLiteDatabase database = helper.getReadableDatabase();
//
//        Cursor cursor = database.query(DBSchema.PetInfoTable.TABLE_NAME, PROJECTIONS, null, null, null, null, null);
//        String results = "";
//
//        while (cursor.moveToNext()) {
//
//            String NAME = cursor.getString(cursor.getColumnIndex(DBSchema.PetInfoTable.NAME));
//            String DATE_OF_BIRTH = cursor.getString(cursor.getColumnIndex(DBSchema.PetInfoTable.DATE_OF_BIRTH));
//            String GENDER = cursor.getString(cursor.getColumnIndex(DBSchema.PetInfoTable.GENDER));
//            String BREED = cursor.getString(cursor.getColumnIndex(DBSchema.PetInfoTable.BREED));
//            String COLOUR = cursor.getString(cursor.getColumnIndex(DBSchema.PetInfoTable.COLOUR));
//            String DISTINGUISHING_MARKS = cursor.getString(cursor.getColumnIndex(DBSchema.PetInfoTable.DISTINGUISHING_MARKS));
//            int CHIP_ID = cursor.getInt(cursor.getColumnIndex(DBSchema.PetInfoTable.CHIP_ID));
//            String OWNER_NAME = cursor.getString(cursor.getColumnIndex(DBSchema.PetInfoTable.OWNER_NAME));
//            String OWNER_ADDRESS = cursor.getString(cursor.getColumnIndex(DBSchema.PetInfoTable.OWNER_ADDRESS));
//            String OWNER_PHONE = cursor.getString(cursor.getColumnIndex(DBSchema.PetInfoTable.OWNER_PHONE));
//            String VET_NAME = cursor.getString(cursor.getColumnIndex(DBSchema.PetInfoTable.VET_NAME));
//            String VET_ADDRESS = cursor.getString(cursor.getColumnIndex(DBSchema.PetInfoTable.VET_ADDRESS));
//            String VET_PHONE = cursor.getString(cursor.getColumnIndex(DBSchema.PetInfoTable.VET_PHONE));
//            String COMMENTS = cursor.getString(cursor.getColumnIndex(DBSchema.PetInfoTable.COMMENTS));
//            int IMAGE_URI = cursor.getInt(cursor.getColumnIndex(DBSchema.PetInfoTable.IMAGE_URI));
//
//
//            results += IMAGE_URI + "\t" + NAME + "\t" + BREED + "\t" + DATE_OF_BIRTH + "\t" + GENDER + "\t" + COLOUR + "\t" + DISTINGUISHING_MARKS + "\t" + CHIP_ID + "\t" + OWNER_NAME + "\t" + OWNER_ADDRESS + "\t"
//                    + OWNER_PHONE + "\t" + VET_NAME + "\t" + VET_ADDRESS + "\t" + VET_PHONE + "\t" + COMMENTS + "\n\n";
//        }
//
//        TextView resultsTextView = (TextView) findViewById(R.id.view_for_database_results2);
//        resultsTextView.setText(results);
//
//        cursor.close();
//
//    }
//
//    //onDestroy
//    @Override
//    protected void onDestroy() {
//        helper.close();
//        super.onDestroy();
//    }
//}
