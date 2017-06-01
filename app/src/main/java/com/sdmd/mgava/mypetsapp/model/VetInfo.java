package com.sdmd.mgava.mypetsapp.model;

import android.os.Parcel;
import android.os.Parcelable;


public class VetInfo extends PersonInfo {

    public VetInfo() {
    }

    public VetInfo(String firstName, String lastName, String address, String phoneNumber) {
        super(firstName, lastName, address, phoneNumber);
    }

    @Override
    public String getType() {
        return "vet";
    }

    protected VetInfo(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        address = in.readString();
        phoneNumber = in.readString();
    }

    public static final Parcelable.Creator<PersonInfo> CREATOR = new Parcelable.Creator<PersonInfo>() {
        @Override
        public VetInfo createFromParcel(Parcel in) {
            return new VetInfo(in);
        }

        @Override
        public VetInfo[] newArray(int size) {
            return new VetInfo[size];
        }
    };
}
