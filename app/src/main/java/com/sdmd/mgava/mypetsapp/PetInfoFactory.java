package com.sdmd.mgava.mypetsapp;

import java.util.ArrayList;
import java.util.List;

class PetInfoFactory {

    public static List<PetInfo> listOfPetInfos = new ArrayList<>();


    private PetInfoFactory() { }

    private static void addPet (String PetName, String dateOfBirth, String sex, String breed, String colour, String distinguishingMarks, int chipId, String ownerName, String ownerAddress,
                                String ownerPhone, String VetName, String VetAddress, String VetPhone, String comments, int imageUri, String animals) {
        PetInfo petInfo = new PetInfo( PetName,  dateOfBirth,  sex,  breed,  colour,  distinguishingMarks,  chipId,  ownerName,  ownerAddress,
                 ownerPhone,  VetName,  VetAddress,  VetPhone,  comments,  imageUri , animals);
        listOfPetInfos.add(petInfo);    }


    private static void initializePets () {
        addPet("Jack", "12/4/2015", "F", "Shepferd", "Black", "Broken leg", 1, "Harvey Specter", "33 Maine", "100876554", "Rita Rose", "Park ave 42", "190999878", "", R.drawable.dog1, "dogs");

        addPet("Bizou", "15/3/2014", "M", "Siam", "Brown", "Missing tooth", 11, "Mike Ross", "5 ave", "123456778", "Josh Macalley", "3 ave ", "1898799897", "", R.drawable.cat, "cats");

        addPet("Saimon", "15/3/2014", "M", "Inguana", "Green", "", 21, "John Appleseed", "Palo alto 4", "16566768", "Meghan Ross", "Maine 32", "189980090", "", R.drawable.lizard, "lizards");

 }

    static List<PetInfo> getListOfPetInfos() {
        initializePets();
        return listOfPetInfos;
    }

    static List<String> getPetCategories() {
        ArrayList<String> pets = new ArrayList<>();

        pets.add("dogs");
        pets.add("cats");
        pets.add("lizards");

        return pets;
    }

}

