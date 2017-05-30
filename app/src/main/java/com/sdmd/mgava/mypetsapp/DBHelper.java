package com.sdmd.mgava.mypetsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import static com.sdmd.mgava.mypetsapp.DbSchema.PetInfoTable.TABLE_NAME;



public class DbHelper extends SQLiteOpenHelper {

    private static final String[] PROJECTION = {
            DbSchema.PetInfoTable._ID,
            DbSchema.PetInfoTable.COLUMN_NAME_NAME,
            DbSchema.PetInfoTable.COLUMN_NAME_DATE_OF_BIRTH,
            DbSchema.PetInfoTable.COLUMN_NAME_GENDER,
            DbSchema.PetInfoTable.COLUMN_NAME_BREED,
            DbSchema.PetInfoTable.COLUMN_NAME_COLOUR,
            DbSchema.PetInfoTable.COLUMN_NAME_DISTINGUISHING_MARKS,
            DbSchema.PetInfoTable.COLUMN_NAME_CHIP_ID,
            DbSchema.PetInfoTable.COLUMN_NAME_SPECIES,
            DbSchema.PetInfoTable.COLUMN_NAME_COMMENTS,
            DbSchema.PetInfoTable.COLUMN_NAME_IMAGE_URI,

            DbSchema.PetInfoTable.COLUMN_NAME_OWNER_NAME,
            DbSchema.PetInfoTable.COLUMN_NAME_OWNER_ADDRESS,
            DbSchema.PetInfoTable.COLUMN_NAME_OWNER_PHONE,

            DbSchema.PetInfoTable.COLUMN_NAME_VET_NAME,
            DbSchema.PetInfoTable.COLUMN_NAME_VET_ADDRESS,
            DbSchema.PetInfoTable.COLUMN_NAME_VET_PHONE,
    };

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PetInfo.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    private static final String SORT_ORDER = DbSchema.PetInfoTable.COLUMN_NAME_NAME + " ASC";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_ID " + INT_TYPE + " PRIMARY KEY AUTOINCREMENT," +
                DbSchema.PetInfoTable.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                DbSchema.PetInfoTable.COLUMN_NAME_DATE_OF_BIRTH + TEXT_TYPE + COMMA_SEP +
                DbSchema.PetInfoTable.COLUMN_NAME_GENDER + TEXT_TYPE + COMMA_SEP +
                DbSchema.PetInfoTable.COLUMN_NAME_BREED + TEXT_TYPE + COMMA_SEP +
                DbSchema.PetInfoTable.COLUMN_NAME_COLOUR + TEXT_TYPE + COMMA_SEP +
                DbSchema.PetInfoTable.COLUMN_NAME_DISTINGUISHING_MARKS + TEXT_TYPE + COMMA_SEP +
                DbSchema.PetInfoTable.COLUMN_NAME_CHIP_ID + INT_TYPE + COMMA_SEP +
                DbSchema.PetInfoTable.COLUMN_NAME_OWNER_NAME + TEXT_TYPE + COMMA_SEP +
                DbSchema.PetInfoTable.COLUMN_NAME_OWNER_ADDRESS + TEXT_TYPE + COMMA_SEP +
                DbSchema.PetInfoTable.COLUMN_NAME_OWNER_PHONE + INT_TYPE + COMMA_SEP +
                DbSchema.PetInfoTable.COLUMN_NAME_VET_NAME + TEXT_TYPE + COMMA_SEP +
                DbSchema.PetInfoTable.COLUMN_NAME_VET_ADDRESS + TEXT_TYPE + COMMA_SEP +
                DbSchema.PetInfoTable.COLUMN_NAME_VET_PHONE + INT_TYPE + COMMA_SEP +
                DbSchema.PetInfoTable.COLUMN_NAME_COMMENTS + TEXT_TYPE + COMMA_SEP +
                DbSchema.PetInfoTable.COLUMN_NAME_SPECIES + TEXT_TYPE + COMMA_SEP +
                DbSchema.PetInfoTable.COLUMN_NAME_IMAGE_URI + INT_TYPE +")");

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade
        // policy is to simply discard the data and start over
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        onCreate(db);

    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private ContentValues getContentValues(PetInfo p1) {
        ContentValues values = new ContentValues();
        values.put(DbSchema.PetInfoTable.COLUMN_NAME_NAME, p1.getName());
        values.put(DbSchema.PetInfoTable.COLUMN_NAME_DATE_OF_BIRTH, p1.getDateOfBirth());
        values.put(DbSchema.PetInfoTable.COLUMN_NAME_GENDER, p1.getGender());
        values.put(DbSchema.PetInfoTable.COLUMN_NAME_BREED, p1.getBreed());
        values.put(DbSchema.PetInfoTable.COLUMN_NAME_COLOUR, p1.getColour());
        values.put(DbSchema.PetInfoTable.COLUMN_NAME_DISTINGUISHING_MARKS, p1.getDistinguishingMarks());
        values.put(DbSchema.PetInfoTable.COLUMN_NAME_CHIP_ID, p1.getChipID());
        values.put(DbSchema.PetInfoTable.COLUMN_NAME_OWNER_NAME, p1.getOwnerName());
        values.put(DbSchema.PetInfoTable.COLUMN_NAME_OWNER_ADDRESS, p1.getOwnerAddress());
        values.put(DbSchema.PetInfoTable.COLUMN_NAME_OWNER_PHONE, p1.getOwnerPhone());
        values.put(DbSchema.PetInfoTable.COLUMN_NAME_VET_NAME, p1.getVetName());
        values.put(DbSchema.PetInfoTable.COLUMN_NAME_VET_ADDRESS, p1.getVetAddress());
        values.put(DbSchema.PetInfoTable.COLUMN_NAME_VET_PHONE, p1.getVetPhone());
        values.put(DbSchema.PetInfoTable.COLUMN_NAME_COMMENTS, p1.getComments());
        values.put(DbSchema.PetInfoTable.COLUMN_NAME_SPECIES, p1.getSpecies());
        values.put(DbSchema.PetInfoTable.COLUMN_NAME_IMAGE_URI, p1.getImageUri());
        return values;
    }

    private void insertPet(PetInfo p) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = getContentValues(p);
        db.insert(TABLE_NAME, null, values);
    }

     public List<PetInfo> getPets() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_NAME,           // The table to query
                PROJECTION,                                     // The columns to return
                null,                                    // The columns for the WHERE clause
                null,                                      // The values for the WHERE clause
                null,                                           // don't group the rows
                null,                                           // don't filter by row groups
                SORT_ORDER                                      // The sort order
        );

        List<PetInfo> petInfos = new ArrayList<>();

        int nameColumn = cursor.getColumnIndexOrThrow(DbSchema.PetInfoTable.COLUMN_NAME_NAME);
        int dateOfBirthColumn = cursor.getColumnIndexOrThrow(DbSchema.PetInfoTable.COLUMN_NAME_DATE_OF_BIRTH);
        int genderColumn = cursor.getColumnIndexOrThrow(DbSchema.PetInfoTable.COLUMN_NAME_GENDER);
        int breedColumn = cursor.getColumnIndexOrThrow(DbSchema.PetInfoTable.COLUMN_NAME_BREED);
        int colorColumn = cursor.getColumnIndexOrThrow(DbSchema.PetInfoTable.COLUMN_NAME_COLOUR);
        int distinguishingMarksColumn = cursor.getColumnIndexOrThrow(DbSchema.PetInfoTable.COLUMN_NAME_DISTINGUISHING_MARKS);
        int chipIDColumn = cursor.getColumnIndexOrThrow(DbSchema.PetInfoTable.COLUMN_NAME_CHIP_ID);
        int speciesColumn = cursor.getColumnIndexOrThrow(DbSchema.PetInfoTable.COLUMN_NAME_SPECIES);
        int commentsColumn = cursor.getColumnIndexOrThrow(DbSchema.PetInfoTable.COLUMN_NAME_COMMENTS);
        int imageUriColumn = cursor.getColumnIndexOrThrow(DbSchema.PetInfoTable.COLUMN_NAME_IMAGE_URI);
        int ownerNameColumn = cursor.getColumnIndexOrThrow(DbSchema.PetInfoTable.COLUMN_NAME_OWNER_NAME);
        int ownerAddressColumn = cursor.getColumnIndexOrThrow(DbSchema.PetInfoTable.COLUMN_NAME_OWNER_ADDRESS);
        int ownerPhoneNumberColumn = cursor.getColumnIndexOrThrow(DbSchema.PetInfoTable.COLUMN_NAME_OWNER_PHONE);
        int vetNameColumn = cursor.getColumnIndexOrThrow(DbSchema.PetInfoTable.COLUMN_NAME_VET_NAME);
        int vetAddressColumn = cursor.getColumnIndexOrThrow(DbSchema.PetInfoTable.COLUMN_NAME_VET_ADDRESS);
        int vetPhoneNumberColumn = cursor.getColumnIndexOrThrow(DbSchema.PetInfoTable.COLUMN_NAME_VET_PHONE);

        while (cursor.moveToNext()) {
            PetInfo petInfo = new PetInfo(cursor.getString(nameColumn),
                    cursor.getString(dateOfBirthColumn),
                    cursor.getString(genderColumn),
                    cursor.getString(breedColumn),
                    cursor.getString(colorColumn),
                    cursor.getString(distinguishingMarksColumn),
                    cursor.getString(chipIDColumn),
                    cursor.getString(ownerNameColumn),
                    cursor.getString(ownerAddressColumn),
                    cursor.getString(ownerPhoneNumberColumn),
                    cursor.getString(vetNameColumn),
                    cursor.getString(vetAddressColumn),
                    cursor.getString(vetPhoneNumberColumn),
                    cursor.getString(commentsColumn),
                    cursor.getString(speciesColumn),
                    cursor.getInt(imageUriColumn));

            petInfos.add(petInfo);
        }

        cursor.close();

        return petInfos;
    }


    public int countPets() {
        SQLiteDatabase db = this.getWritableDatabase();
        String countQuery = "SELECT count(*) FROM " + DbSchema.PetInfoTable.TABLE_NAME;
        Cursor mcursor = db.rawQuery(countQuery, null);
        mcursor.moveToFirst();
        return mcursor.getInt(0);
    }

    public void initDb() {
        if (countPets() == 0) {
            for (PetInfo petInfo : getPetList()) {
                insertPet(petInfo);
            }
        }
    }

    private List<PetInfo> getPetList() {
        List<PetInfo> petInfos = new ArrayList<>();
        petInfos.add(new PetInfo("Jack", "2015", "female", "Shepferd", "1553", "Black", "none", "Harvey Specter", "33 Maine", "100876554", "Rita Rose", "Park ave 42", "190999878","good","Dog", R.drawable.dog1));
        petInfos.add(new PetInfo("Bizou", "2014", "male", "Siam", "1245", "Brown", "none", "Mike Ross", "5 ave", "123456778", "Josh Macalley", "3 ave ", "1898799897", "good", "Cat", R.drawable.cat));
        petInfos.add(new PetInfo("Saimon", "2014", "male", "Inguana", "Green", "1445", "none", "John Appleseed", "Palo alto 4", "16566768", "Meghan Ross", "Maine 32", "189980090", "well", "Other", R.drawable.lizard));
        return petInfos;
    }
}



