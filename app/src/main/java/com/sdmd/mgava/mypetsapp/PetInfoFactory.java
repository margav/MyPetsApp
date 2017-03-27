package com.sdmd.mgava.mypetsapp;

import java.util.ArrayList;
import java.util.List;

class PetInfoFactory {

    private static List<PetInfo> listOfDogs = new ArrayList<>() ;
    private static List<PetInfo> listOfCats = new ArrayList<>() ;
    private static List<PetInfo> listOfOther = new ArrayList<>() ;

    private PetInfoFactory() {

    }

    private static void addDog (int PetName,int PetAnimal, int PetBreed, int PetSex, int PetColour, int PetDateOfBirth, int PetOwner, int image) {
        PetInfo petInfo = new PetInfo(PetName,PetAnimal, PetBreed, PetSex,PetColour, PetDateOfBirth, PetOwner, image);
        listOfDogs.add(petInfo);
    }

    private static void addCat(int PetName,int PetAnimal, int PetBreed, int PetSex, int PetColour, int PetDateOfBirth, int PetOwner, int image) {
        PetInfo petInfo = new PetInfo(PetName,PetAnimal, PetBreed, PetSex,PetColour, PetDateOfBirth, PetOwner, image);
        listOfCats.add(petInfo);
    }

    private static void addOther (int PetName,int PetAnimal, int PetBreed, int PetSex, int PetColour, int PetDateOfBirth, int PetOwner, int image) {
        PetInfo petInfo = new PetInfo(PetName,PetAnimal, PetBreed, PetSex,PetColour, PetDateOfBirth, PetOwner, image);
        listOfOther.add(petInfo);
    }

    private static void initializeDogs () {
        addDog(R.string.jack, R.string.dog,R.string.sheepferd ,R.string.female,R.string.black, R.string.date_jack,R.string.owner1, R.drawable.dog1);
        addDog(R.string.mailo, R.string.dog,R.string.corgie ,R.string.male,R.string.brown, R.string.date_mailo,R.string.owner2, R.drawable.dog2);
    }

    private static void initializeCats () {
        addCat(R.string.bizou,  R.string.cat, R.string.siamese ,R.string.male,R.string.grey, R.string.date_bizou,R.string.owner3, R.drawable.cat);
    }

    private static void initializeOther () {
        addOther(R.string.jill, R.string.lizard,R.string.iguana ,R.string.female,R.string.green, R.string.date_jill,R.string.owner4, R.drawable.lizard);
    }

    static List<PetInfo> getListOfDogs() {
        initializeDogs();
        return listOfDogs;
    }

    static List<PetInfo> getListOfCats() {
        initializeCats();
        return listOfCats;
    }

    static List<PetInfo> getListOfOther() {
        initializeOther();
        return listOfOther;
    }

}

