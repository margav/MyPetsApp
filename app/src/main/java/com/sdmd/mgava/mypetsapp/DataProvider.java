package com.sdmd.mgava.mypetsapp;


public class DataProvider {
    private int pet_pic;
    private String pet_name;
    private String pet_breed;

    public DataProvider(int pet_pic, String pet_name, String pet_breed) {
        this.setPet_pic(pet_pic);
        this.setPet_name(pet_name);
        this.setPet_breed(pet_breed);
    }

    public int getPet_pic() {
        return pet_pic;
    }

    public void setPet_pic(int pet_pic) {
        this.pet_pic = pet_pic;
    }

    public String getPet_name() {
        return pet_name;
    }

    public void setPet_name(String pet_name) {
        this.pet_name = pet_name;
    }

    public String getPet_breed() {
        return pet_breed;
    }

    public void setPet_breed(String pet_breed) {
        this.pet_breed = pet_breed;
    }


    public String toString() {
        return this.pet_name;
    }
}
