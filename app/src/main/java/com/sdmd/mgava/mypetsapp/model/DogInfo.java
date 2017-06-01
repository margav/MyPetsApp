package com.sdmd.mgava.mypetsapp.model;

import java.util.Date;



public class DogInfo extends PetInfo {

    public DogInfo(String name, Date dateOfBirth, String sex, String breed, String color, String distinguishingMarks, String chipId, int image, OwnerInfo ownerInfo, VetInfo vetInfo) {
        super(name, dateOfBirth, sex, breed, color, distinguishingMarks, chipId, image, ownerInfo, vetInfo);
    }


    @Override
    public String getSpecies() {
        return "dog";
    }
}
