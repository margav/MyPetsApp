package com.sdmd.mgava.mypetsapp.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.sdmd.mgava.mypetsapp.R;
import com.sdmd.mgava.mypetsapp.model.CatInfo;
import com.sdmd.mgava.mypetsapp.model.DogInfo;
import com.sdmd.mgava.mypetsapp.model.OwnerInfo;
import com.sdmd.mgava.mypetsapp.model.PetInfo;
import com.sdmd.mgava.mypetsapp.model.VetInfo;


public class PetInfoService {

    public List<PetInfo> getPets(String species) {

        List<PetInfo> petInfos = new ArrayList<>();

        switch (species) {
            case "dog":
                getDogs(petInfos);
                break;
            case "cat":
                getCats(petInfos);
                break;
            case "other":
                getOther(petInfos);
                break;
            default:
                getDogs(petInfos);
                getCats(petInfos);
                getOther(petInfos);
                break;
        }

        return petInfos;
    }

    private void getDogs(List<PetInfo> petInfos) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        try {
            petInfos.add(new DogInfo("Jack", format.parse("03/12/2012"),
                    "Male", "Sheppferd", "Black",
                    "Nice", "-",
                    R.drawable.dog1,
                    new OwnerInfo("Harvey", "Specter", "33 Maine Str.", "(555)-356-4780"),
                    new VetInfo("John", "Appleseed", "Palo alto 4", "(555)-678-7890")));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void getCats(List<PetInfo> petInfos) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        try {
            petInfos.add(new CatInfo("Bizou", format.parse("14/08/2014"),
                    "Female", "Siamese", "Brown",
                    "Good", "-",
                    R.drawable.cat,
                    new OwnerInfo("Mike", "Ross", "5th ave", "(577)-145-6778"),
                    new VetInfo("Josh", "Macalley", "Maine 32", "(555)-234-4563")));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void getOther(List<PetInfo> petInfos) {
    }
}