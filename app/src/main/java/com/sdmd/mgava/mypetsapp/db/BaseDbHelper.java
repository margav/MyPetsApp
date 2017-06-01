package com.sdmd.mgava.mypetsapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.sdmd.mgava.mypetsapp.db.PetInfoDbHelper.DATABASE_NAME;
import static com.sdmd.mgava.mypetsapp.db.PetInfoDbHelper.SQL_CREATE;
import static com.sdmd.mgava.mypetsapp.db.PetInfoDbHelper.SQL_DELETE;



public class BaseDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    protected static final int DATABASE_VERSION = 1;

    protected static final String TEXT_TYPE = " TEXT";
    protected static final String INT_TYPE = " INTEGER";
    protected static final String COMMA_SEP = ",";


    public BaseDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade
        // policy is to simply discard the data and start over
        db.execSQL(SQL_DELETE);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
