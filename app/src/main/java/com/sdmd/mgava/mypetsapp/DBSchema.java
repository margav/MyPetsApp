package com.sdmd.mgava.mypetsapp;

import android.provider.BaseColumns;

public class DbSchema {

    public DbSchema() {
    }

    public static abstract class PetInfoTable implements BaseColumns {

        public static final String TABLE_NAME = "petInfo";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DATE_OF_BIRTH = "date_of_birth";
        public static final String COLUMN_NAME_GENDER = "gender";
        public static final String COLUMN_NAME_BREED = "breed";
        public static final String COLUMN_NAME_COLOUR = "colour";
        public static final String COLUMN_NAME_DISTINGUISHING_MARKS = "distinguishing_marks";
        public static final String COLUMN_NAME_CHIP_ID = "chip_id";
        public static final String COLUMN_NAME_OWNER_NAME = "owner_name";
        public static final String COLUMN_NAME_OWNER_ADDRESS = "owner_address";
        public static final String COLUMN_NAME_OWNER_PHONE = "owner_phone";
        public static final String COLUMN_NAME_VET_NAME = "vet_name";
        public static final String COLUMN_NAME_VET_ADDRESS = "vet_address";
        public static final String COLUMN_NAME_VET_PHONE = "vet_phone";
        public static final String COLUMN_NAME_COMMENTS = "comments";
        public static final String COLUMN_NAME_SPECIES = "species";
        public static final String COLUMN_NAME_IMAGE_URI = "image_uri";
        public static final String TEXT_TYPE = " TEXT";
        public static final String INT_TYPE = " INTEGER";
        public static final String COMMA_SEP = ",";


    }

}

