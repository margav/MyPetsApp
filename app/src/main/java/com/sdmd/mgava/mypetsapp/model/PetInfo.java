package com.sdmd.mgava.mypetsapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;


public class PetInfo implements Parcelable {

    private String name;
    private Date dateOfBirth;
    private String gender;
    private String breed;
    private String color;
    private String distinguishingMarks;
    private String chipID;
    private String species;
    private String comments;
    private int imageUri;
    private OwnerInfo ownerInfo;
    private VetInfo vetInfo;

    public PetInfo() {
    }

    public PetInfo(String name, String breed, int imageUri) {
        this.name = name;
        this.breed = breed;
        this.imageUri = imageUri;
    }

    public PetInfo(String name, Date dateOfBirth, String gender, String breed, String color, String distinguishingMarks, String chipID, int imageUri, OwnerInfo ownerInfo, VetInfo vetInfo) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.breed = breed;
        this.color = color;
        this.distinguishingMarks = distinguishingMarks;
        this.chipID = chipID;
        this.species = this.getSpecies();
        this.imageUri = imageUri;
        this.ownerInfo = ownerInfo;
        this.vetInfo = vetInfo;
    }

    public PetInfo(String name, Date dateOfBirth, String gender, String breed, String color, String distinguishingMarks, String chipID, String species, String comments, int imageUri) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.breed = breed;
        this.color = color;
        this.distinguishingMarks = distinguishingMarks;
        this.chipID = chipID;
        this.species = species;
        this.comments = comments;
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDistinguishingMarks() {
        return distinguishingMarks;
    }

    public void setDistinguishingMarks(String distinguishingMarks) {
        this.distinguishingMarks = distinguishingMarks;
    }

    public String getChipID() {
        return chipID;
    }

    public void setChipID(String chipID) {
        this.chipID = chipID;
    }

    public int getImageUri() {
        return imageUri;
    }

    public void setImageUri(int imageUri) {
        this.imageUri = imageUri;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public OwnerInfo getOwnerInfo() {
        return ownerInfo;
    }

    public void setOwnerInfo(OwnerInfo ownerInfo) {
        this.ownerInfo = ownerInfo;
    }

    public VetInfo getVetInfo() {
        return vetInfo;
    }

    public void setVetInfo(VetInfo vetInfo) {
        this.vetInfo = vetInfo;
    }

    protected PetInfo(Parcel in) {
        name = in.readString();
        long tmpDateOfBirth = in.readLong();
        dateOfBirth = tmpDateOfBirth != -1 ? new Date(tmpDateOfBirth) : null;
        gender = in.readString();
        breed = in.readString();
        color = in.readString();
        distinguishingMarks = in.readString();
        chipID = in.readString();
        imageUri = in.readInt();
        ownerInfo = (OwnerInfo) in.readValue(OwnerInfo.class.getClassLoader());
        vetInfo = (VetInfo) in.readValue(VetInfo.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeLong(dateOfBirth != null ? dateOfBirth.getTime() : -1L);
        dest.writeString(gender);
        dest.writeString(breed);
        dest.writeString(color);
        dest.writeString(distinguishingMarks);
        dest.writeString(chipID);
        dest.writeInt(imageUri);
        dest.writeValue(ownerInfo);
        dest.writeValue(vetInfo);
    }

    public static final Creator<PetInfo> CREATOR = new Creator<PetInfo>() {
        @Override
        public PetInfo createFromParcel(Parcel in) {
            return new PetInfo(in);
        }

        @Override
        public PetInfo[] newArray(int size) {
            return new PetInfo[size];
        }
    };
}
