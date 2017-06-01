package com.sdmd.mgava.mypetsapp.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sdmd.mgava.mypetsapp.model.PetInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sdmd.mgava.mypetsapp.db.PetInfoManagement;
import com.sdmd.mgava.mypetsapp.db.PetInfoDbHelper;
import com.sdmd.mgava.mypetsapp.model.OwnerInfo;
import com.sdmd.mgava.mypetsapp.model.VetInfo;


public class PetInfoRepository {

    private static final String[] PROJECTION = {
            PetInfoManagement.Pet._ID,
            PetInfoManagement.Pet.COLUMN_NAME_NAME,
            PetInfoManagement.Pet.COLUMN_NAME_DATE_OF_BIRTH,
            PetInfoManagement.Pet.COLUMN_NAME_GENDER,
            PetInfoManagement.Pet.COLUMN_NAME_BREED,
            PetInfoManagement.Pet.COLUMN_NAME_COLOR,
            PetInfoManagement.Pet.COLUMN_NAME_MARKS,
            PetInfoManagement.Pet.COLUMN_NAME_CHIP_ID,
            PetInfoManagement.Pet.COLUMN_NAME_SPECIES,
            PetInfoManagement.Pet.COLUMN_NAME_COMMENTS,
            PetInfoManagement.Pet.COLUMN_NAME_IMAGE_URI,
            PetInfoManagement.Pet.COLUMN_NAME_OWNER_FIRST_NAME,
            PetInfoManagement.Pet.COLUMN_NAME_OWNER_LAST_NAME,
            PetInfoManagement.Pet.COLUMN_NAME_OWNER_ADDRESS,
            PetInfoManagement.Pet.COLUMN_NAME_OWNER_PHONE_NUMBER,
            PetInfoManagement.Pet.COLUMN_NAME_VET_FIRST_NAME,
            PetInfoManagement.Pet.COLUMN_NAME_VET_LAST_NAME,
            PetInfoManagement.Pet.COLUMN_NAME_VET_ADDRESS,
            PetInfoManagement.Pet.COLUMN_NAME_VET_PHONE_NUMBER,
    };

    // How you want the results sorted in the resulting Cursor
    private static final String SORT_ORDER = PetInfoManagement.Pet.COLUMN_NAME_NAME + " ASC";

    private PetInfoDbHelper dbHelper;

    public PetInfoRepository(Context context) {
        dbHelper = new PetInfoDbHelper(context);
    }

    public Long insertPet(PetInfo pet) {
        ContentValues values = new ContentValues();
        values.put(PetInfoManagement.Pet.COLUMN_NAME_NAME, pet.getName());
        values.put(PetInfoManagement.Pet.COLUMN_NAME_DATE_OF_BIRTH, pet.getDateOfBirth().getTime());
        values.put(PetInfoManagement.Pet.COLUMN_NAME_GENDER, pet.getGender());
        values.put(PetInfoManagement.Pet.COLUMN_NAME_BREED, pet.getBreed());
        values.put(PetInfoManagement.Pet.COLUMN_NAME_COLOR, pet.getColor());
        values.put(PetInfoManagement.Pet.COLUMN_NAME_MARKS, pet.getDistinguishingMarks());
        values.put(PetInfoManagement.Pet.COLUMN_NAME_CHIP_ID, pet.getChipID());
        values.put(PetInfoManagement.Pet.COLUMN_NAME_SPECIES, pet.getSpecies());
        values.put(PetInfoManagement.Pet.COLUMN_NAME_COMMENTS, pet.getComments());
        values.put(PetInfoManagement.Pet.COLUMN_NAME_IMAGE_URI, pet.getImageUri());
        values.put(PetInfoManagement.Pet.COLUMN_NAME_OWNER_FIRST_NAME, pet.getOwnerInfo().getFirstName());
        values.put(PetInfoManagement.Pet.COLUMN_NAME_OWNER_LAST_NAME, pet.getOwnerInfo().getLastName());
        values.put(PetInfoManagement.Pet.COLUMN_NAME_OWNER_ADDRESS, pet.getOwnerInfo().getAddress());
        values.put(PetInfoManagement.Pet.COLUMN_NAME_OWNER_PHONE_NUMBER, pet.getOwnerInfo().getPhoneNumber());
        values.put(PetInfoManagement.Pet.COLUMN_NAME_VET_FIRST_NAME, pet.getVetInfo().getFirstName());
        values.put(PetInfoManagement.Pet.COLUMN_NAME_VET_LAST_NAME, pet.getVetInfo().getLastName());
        values.put(PetInfoManagement.Pet.COLUMN_NAME_VET_ADDRESS, pet.getVetInfo().getAddress());
        values.put(PetInfoManagement.Pet.COLUMN_NAME_VET_PHONE_NUMBER, pet.getVetInfo().getPhoneNumber());

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long newRowId;
        newRowId = db.insert(
                PetInfoManagement.Pet.TABLE_NAME,
                null,
                values);

        return newRowId;
    }

    public List<PetInfo> getPets(String whereClause, String[] whereArgs) throws ParseException {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                PetInfoManagement.Pet.TABLE_NAME,           // The table to query
                PROJECTION,                                     // The columns to return
                whereClause,                                    // The columns for the WHERE clause
                whereArgs,                                      // The values for the WHERE clause
                null,                                           // don't group the rows
                null,                                           // don't filter by row groups
                SORT_ORDER                                      // The sort order
        );

        List<PetInfo> pets = new ArrayList<>();

        int nameColumn = cursor.getColumnIndexOrThrow(PetInfoManagement.Pet.COLUMN_NAME_NAME);
        int dateOfBirthColumn = cursor.getColumnIndexOrThrow(PetInfoManagement.Pet.COLUMN_NAME_DATE_OF_BIRTH);
        int genderColumn = cursor.getColumnIndexOrThrow(PetInfoManagement.Pet.COLUMN_NAME_GENDER);
        int breedColumn = cursor.getColumnIndexOrThrow(PetInfoManagement.Pet.COLUMN_NAME_BREED);
        int colorColumn = cursor.getColumnIndexOrThrow(PetInfoManagement.Pet.COLUMN_NAME_COLOR);
        int distinguishingMarksColumn = cursor.getColumnIndexOrThrow(PetInfoManagement.Pet.COLUMN_NAME_MARKS);
        int chipIDColumn = cursor.getColumnIndexOrThrow(PetInfoManagement.Pet.COLUMN_NAME_CHIP_ID);
        int speciesColumn = cursor.getColumnIndexOrThrow(PetInfoManagement.Pet.COLUMN_NAME_SPECIES);
        int commentsColumn = cursor.getColumnIndexOrThrow(PetInfoManagement.Pet.COLUMN_NAME_COMMENTS);
        int imageUriColumn = cursor.getColumnIndexOrThrow(PetInfoManagement.Pet.COLUMN_NAME_IMAGE_URI);
        int ownerFirstNameColumn = cursor.getColumnIndexOrThrow(PetInfoManagement.Pet.COLUMN_NAME_OWNER_FIRST_NAME);
        int ownerLastNameColumn = cursor.getColumnIndexOrThrow(PetInfoManagement.Pet.COLUMN_NAME_OWNER_LAST_NAME);
        int ownerAddressColumn = cursor.getColumnIndexOrThrow(PetInfoManagement.Pet.COLUMN_NAME_OWNER_ADDRESS);
        int ownerPhoneNumberColumn = cursor.getColumnIndexOrThrow(PetInfoManagement.Pet.COLUMN_NAME_OWNER_PHONE_NUMBER);
        int vetFirstNameColumn = cursor.getColumnIndexOrThrow(PetInfoManagement.Pet.COLUMN_NAME_VET_FIRST_NAME);
        int vetLastNameColumn = cursor.getColumnIndexOrThrow(PetInfoManagement.Pet.COLUMN_NAME_VET_LAST_NAME);
        int vetAddressColumn = cursor.getColumnIndexOrThrow(PetInfoManagement.Pet.COLUMN_NAME_VET_ADDRESS);
        int vetPhoneNumberColumn = cursor.getColumnIndexOrThrow(PetInfoManagement.Pet.COLUMN_NAME_VET_PHONE_NUMBER);
        while (cursor.moveToNext()) {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            String dob = format.format(new Date(cursor.getLong(dateOfBirthColumn)));
            Date dateOfBirth = format.parse(dob);

            PetInfo pet = new PetInfo(cursor.getString(nameColumn),
                    dateOfBirth,
                    cursor.getString(genderColumn),
                    cursor.getString(breedColumn),
                    cursor.getString(colorColumn),
                    cursor.getString(distinguishingMarksColumn),
                    cursor.getString(chipIDColumn),
                    cursor.getString(speciesColumn),
                    cursor.getString(commentsColumn),
                    cursor.getInt(imageUriColumn));

            OwnerInfo owner = new OwnerInfo(cursor.getString(ownerFirstNameColumn), cursor.getString(ownerLastNameColumn), cursor.getString(ownerAddressColumn), cursor.getString(ownerPhoneNumberColumn));
            VetInfo vet = new VetInfo(cursor.getString(vetFirstNameColumn), cursor.getString(vetLastNameColumn), cursor.getString(vetAddressColumn), cursor.getString(vetPhoneNumberColumn));

            pet.setOwnerInfo(owner);
            pet.setVetInfo(vet);

            pets.add(pet);
        }

        cursor.close();

        return pets;
    }

    public List<PetInfo> getPets(String species) throws ParseException {
        String whereClause = PetInfoManagement.Pet.COLUMN_NAME_SPECIES + "=?";
        String[] whereArgs = {species.toLowerCase()};

        return getPets(whereClause, whereArgs);
    }

    public void deletePet(String name) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.delete(PetInfoManagement.Pet.TABLE_NAME, PetInfoManagement.Pet.COLUMN_NAME_NAME + "='" + name+"'", null);
    }

    public int countPets() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String countQuery = "SELECT count(*) FROM " + PetInfoManagement.Pet.TABLE_NAME;
        Cursor mcursor = db.rawQuery(countQuery, null);
        mcursor.moveToFirst();
        return mcursor.getInt(0);
    }

    public void initDb(List<PetInfo> petInfos) {
        if (countPets() == 0) {
            for (PetInfo petInfo : petInfos) {
                insertPet(petInfo);
            }
        }
    }
}
