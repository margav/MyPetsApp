package com.sdmd.mgava.mypetsapp.db;

import android.net.Uri;
import android.provider.BaseColumns;


public class UserManagement {

    public static final String AUTHORITY = "com.sdmd.mgava.mypetsapp";

    private UserManagement() {
    }

    public static abstract class User implements BaseColumns {

        public static final String TABLE_NAME = "student";
        public static final String COLUMN_NAME_USERNAME= "username";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_FIRST_NAME = "first_name";
        public static final String COLUMN_NAME_LAST_NAME = "last_name";

        public static final Uri CONTENT_URI = Uri.parse("content://" +
                AUTHORITY + "/" + TABLE_NAME);
    }

}
