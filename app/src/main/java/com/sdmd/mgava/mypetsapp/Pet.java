package com.sdmd.mgava.mypetsapp;

class Pet {

    private String PetName;
    private String dateOfBirth;
    private String sex;
    private String breed;
    private String colour;
    private String distinguishingMarks;
    private String ownerName;
    private String ownerAddress;
    private String ownerPhone;
    private String VetName;
    private String VetAddress;
    private String VetPhone;
    private String comments;
    private int chipId;
    private int imageUri;

    private String animals;

    public String getPetName() {
        return PetName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getSex() {
        return sex;
    }

    public String getBreed() {
        return breed;
    }

    public String getColour() {
        return colour;
    }

    public String getDistinguishingMarks() {
        return distinguishingMarks;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public String getVetName() {
        return VetName;
    }

    public String getVetAddress() {
        return VetAddress;
    }

    public String getVetPhone() {
        return VetPhone;
    }

    public String getComments() {
        return comments;
    }

    public int getChipId() {
        return chipId;
    }

    public int getImageUri() {
        return imageUri;
    }

    public String getAnimals() {
        return animals;
    }

    Pet (String PetName, String dateOfBirth, String sex, String breed, String colour, String distinguishingMarks, int chipId, String ownerName, String ownerAddress, String ownerPhone, String VetName,
         String VetAddress, String VetPhone, String comments, int imageUri, String animals)
     {
         this.PetName = PetName;
         this.dateOfBirth = dateOfBirth;
         this.sex = sex;
         this.breed = breed;
         this.colour = colour;
         this.distinguishingMarks = distinguishingMarks;
         this.ownerName = ownerName;
         this.ownerAddress =ownerAddress;
         this.ownerPhone = ownerPhone;
         this.VetName = VetName;
         this.VetAddress = VetAddress;
         this.VetPhone = VetPhone;
         this.comments = comments;
         this.chipId = chipId;
         this.imageUri = imageUri;
         this.animals = animals;
     }



 }
