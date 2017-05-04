package com.sdmd.mgava.mypetsapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PetDBSchemaHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Pet.db";

    public PetDBSchemaHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(com.sdmd.mgava.mypetsapp.PetDBSchema.SQL_CREATE_PETS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(com.sdmd.mgava.mypetsapp.PetDBSchema.SQL_DELETE_PETS);
        onCreate(db);
    }

}

