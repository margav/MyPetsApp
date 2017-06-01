package com.sdmd.mgava.mypetsapp.model;

import android.os.Parcel;
import android.os.Parcelable;


public class OwnerInfo extends PersonInfo {

    public OwnerInfo() {
    }

    public OwnerInfo(String firstName, String lastName, String address, String phoneNumber) {
        super(firstName, lastName, address, phoneNumber);
    }

    @Override
    public String getType() {
        return "owner";
    }

    protected OwnerInfo(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        address = in.readString();
        phoneNumber = in.readString();
    }

    public static final Parcelable.Creator<PersonInfo> CREATOR = new Parcelable.Creator<PersonInfo>() {
        @Override
        public OwnerInfo createFromParcel(Parcel in) {
            return new OwnerInfo(in);
        }

        @Override
        public OwnerInfo[] newArray(int size) {
            return new OwnerInfo[size];
        }
    };
}
