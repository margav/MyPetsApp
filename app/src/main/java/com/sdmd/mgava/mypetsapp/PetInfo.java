package com.sdmd.mgava.mypetsapp;


 class PetInfo {

     private int PetNameId;
     private int PetAnimalId;
     private int PetBreedId;
     private int PetSexId;
     private int PetColourId;
     private int PetDateOfBirthId;
     private int PetOwnerId;
     private int imageId;

     PetInfo (int PetName, int PetAnimal, int PetBreed, int PetSex, int PetColour, int PetDateOfBirth, int PetOwner, int theImage) {
         PetNameId = PetName;
         PetAnimalId = PetAnimal;
         PetBreedId = PetBreed;
         PetSexId = PetSex;
         PetColourId = PetColour;
         PetDateOfBirthId = PetDateOfBirth;
         PetOwnerId = PetOwner;
         imageId = theImage;
     }


     public int getPetNameId() {
         return PetNameId;
     }

     public void setPetNameId(int petNameId) {
         PetNameId = petNameId;
     }

     public int getPetBreedId() {
         return PetBreedId;
     }

     public void setPetBreedId(int petBreedId) {
         PetBreedId = petBreedId;
     }

     public int getPetSexId() {
         return PetSexId;
     }

     public void setPetSexId(int petSexId) {
         PetSexId = petSexId;
     }

     public int getPetColourId() {
         return PetColourId;
     }

     public void setPetColourId(int petColourId) {
         PetColourId = petColourId;
     }

     public int getPetDateOfBirthId() {
         return PetDateOfBirthId;
     }

     public void setPetDateOfBirthId(int petDateOfBirthId) {
         PetDateOfBirthId = petDateOfBirthId;
     }

     public int getPetOwnerId() {
         return PetOwnerId;
     }

     public void setPetOwnerId(int petOwnerId) {
         PetOwnerId = petOwnerId;
     }

     public int getImageId() {
         return imageId;
     }

     public void setImageId(int imageId) {
         this.imageId = imageId;
     }

     public int getPetAnimalId() {
         return PetAnimalId;
     }

     public void setPetAnimalId(int petAnimalId) {
         PetAnimalId = petAnimalId;
     }

 }