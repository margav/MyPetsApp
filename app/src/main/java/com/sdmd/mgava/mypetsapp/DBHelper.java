package com.sdmd.mgava.mypetsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper{

    String DB_PATH;
    private final static String DB_NAME = "db_petInfo";
    public final static int DB_VERSION = 1;
    public static SQLiteDatabase db;

    private final Context context;

    private final String TABLE_NAME = "tbl_petInfo";
    private final String ID = "id";
    private final String PET_NAME = "Pet_name";



    public DBHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;

        DB_PATH = Utils.DBPath;
    }

    public void createDataBase() throws IOException{

        boolean dbExist = checkDataBase();
        SQLiteDatabase db_Read = null;


        if(dbExist){
            //do nothing - database already exist

        }else{
            db_Read = this.getReadableDatabase();
            db_Read.close();

            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }

    }



    private boolean checkDataBase(){

        File dbFile = new File(DB_PATH + DB_NAME);

        return dbFile.exists();

    }


    private void copyDataBase() throws IOException{

        InputStream myInput = context.getAssets().open(DB_NAME);

        String outFileName = DB_PATH + DB_NAME;

        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException{
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public void close() {
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /** this code is used to get all data from database */
    public ArrayList<ArrayList<Object>> getAllData(){
        ArrayList<ArrayList<Object>> dataArrays = new ArrayList<ArrayList<Object>>();

        Cursor cursor = null;

        try{
            cursor = db.query(
                    TABLE_NAME,
                    new String[]{ID, PET_NAME},
                    null,null, null, null, null);
            cursor.moveToFirst();

            if (!cursor.isAfterLast()){
                do{
                    ArrayList<Object> dataList = new ArrayList<Object>();

                    dataList.add(cursor.getLong(0));
                    dataList.add(cursor.getString(1));
                    dataList.add(cursor.getString(2));
                    dataList.add(cursor.getString(3));

                    dataArrays.add(dataList);
                }

                while (cursor.moveToNext());
            }
            cursor.close();
        }catch (SQLException e){
            Log.e("DB Error", e.toString());
            e.printStackTrace();
        }

        return dataArrays;
    }

    /** this code is used to get all data from database */
    public boolean isDataExist(long id){
        boolean exist = false;

        Cursor cursor = null;

        try{
            cursor = db.query(
                    TABLE_NAME,
                    new String[]{ID},
                    ID +"="+id,
                    null, null, null, null);
            if(cursor.getCount() > 0){
                exist = true;
            }

            cursor.close();
        }catch (SQLException e){
            Log.e("DB Error", e.toString());
            e.printStackTrace();
        }

        return exist;
    }

    /** this code is used to get all data from database */
    public boolean isPreviousDataExist(){
        boolean exist = false;

        Cursor cursor = null;

        try{
            cursor = db.query(
                    TABLE_NAME,
                    new String[]{ID},
                    null,null, null, null, null);
            if(cursor.getCount() > 0){
                exist = true;
            }

            cursor.close();
        }catch (SQLException e){
            Log.e("DB Error", e.toString());
            e.printStackTrace();
        }

        return exist;
    }

    public void addData(long id, String pet_name){
        // this is a key value pair holder used by android's SQLite functions
        ContentValues values = new ContentValues();
        values.put(ID, id);
        values.put(PET_NAME, pet_name);


        // ask the database object to insert the new data
        try{db.insert(TABLE_NAME, null, values);}
        catch(Exception e)
        {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public void deleteData(long id){
        // ask the database manager to delete the row of given id
        try {db.delete(TABLE_NAME, ID + "=" + id, null);}
        catch (Exception e)
        {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public void deleteAllData(){
        // ask the database manager to delete the row of given id
        try {db.delete(TABLE_NAME, null, null);}
        catch (Exception e)
        {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public void updateData(long id){
        // this is a key value pair holder used by android's SQLite functions
        ContentValues values = new ContentValues();


        // ask the database object to update the database row of given rowID
        try {db.update(TABLE_NAME, values, ID + "=" + id, null);}
        catch (Exception e)
        {
            Log.e("DB Error", e.toString());
            e.printStackTrace();
        }
    }


}