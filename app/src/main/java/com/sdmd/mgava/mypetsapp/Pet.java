package com.sdmd.mgava.mypetsapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public class Pet {

    private static Pet sPet;
    private List<PetInfo> mPetInfos;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private Pet(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new DbHelper(mContext)
                .getWritableDatabase();
        mPetInfos = new ArrayList<>();
        PetInfo p1 =new PetInfo("Jack", "2015", "female", "Shepferd", "1553", "Black", "none", "Harvey Specter", "33 Maine", "100876554", "Rita Rose", "Park ave 42", "190999878","good","Dog", R.drawable.dog1);
        PetInfo p2 =new PetInfo("Bizou", "2014", "male", "Siam", "1245", "Brown", "none", "Mike Ross", "5 ave", "123456778", "Josh Macalley", "3 ave ", "1898799897", "good", "Cat", R.drawable.cat);
        PetInfo p3 =new PetInfo("Saimon", "2014", "male", "Inguana", "Green", "1445", "none", "John Appleseed", "Palo alto 4", "16566768", "Meghan Ross", "Maine 32", "189980090", "well", "Other", R.drawable.lizard);

        mPetInfos.add(p1);
        mPetInfos.add(p2);
        mPetInfos.add(p3);

    }

}
