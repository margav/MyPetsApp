package com.sdmd.mgava.mypetsapp.service;

import java.util.ArrayList;
import java.util.List;

import com.sdmd.mgava.mypetsapp.model.OwnerInfo;
import com.sdmd.mgava.mypetsapp.model.PersonInfo;
import com.sdmd.mgava.mypetsapp.model.VetInfo;


public class PersonService {

    public List<PersonInfo> getOwners() {

        List<PersonInfo> owners = new ArrayList<>();

        owners.add(new OwnerInfo("Harvey", "Specter", "33 Maine Str.", "(555)-356-4780"));
        owners.add(new OwnerInfo("Mike", "Ross", "5th ave", "(577)-145-6778"));


        return owners;
    }

    public List<PersonInfo> getVets() {

        List<PersonInfo> vets = new ArrayList<>();

        vets.add(new VetInfo("John", "Appleseed", "Palo alto 4", "(555)-678-7890"));
        vets.add(new VetInfo("Josh", "Macalley", "Maine 32", "(555)-234-4563"));

        return vets;
    }
}
