package com.sdmd.mgava.mypetsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.sdmd.mgava.mypetsapp.model.OwnerInfo;
import com.sdmd.mgava.mypetsapp.model.PetInfo;
import com.sdmd.mgava.mypetsapp.model.VetInfo;
import com.sdmd.mgava.mypetsapp.repository.PetInfoRepository;


public class PetInfoForm extends Base {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petinfo_form);

        Intent intent = getIntent();
        PetInfo pet = intent.getParcelableExtra("petInfo");
        if (pet != null) {
            fillForm(pet);
        }
        petInfoRepository = new PetInfoRepository(this);
        setListeners();
    }

    /**
     * Setup the various activity listeners
     */
    private void setListeners() {
        final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String name = ((EditText) findViewById(R.id.petNameText)).getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(PetInfoForm.this, "PetInfoActivity should have a name!", Toast.LENGTH_LONG).show();
                    return;
                }

                PetInfo pet = new PetInfo();
                pet.setName(name);

                try {
                    String date = ((EditText) findViewById(R.id.date)).getText().toString();
                    pet.setDateOfBirth(format.parse(date.isEmpty() ? "01/01/1970" : date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                pet.setGender(((EditText) findViewById(R.id.genderText)).getText().toString());
                pet.setBreed(((EditText) findViewById(R.id.breedText)).getText().toString());
                pet.setColor(((EditText) findViewById(R.id.colorText)).getText().toString());
                pet.setDistinguishingMarks(((EditText) findViewById(R.id.MarksText)).getText().toString());
                pet.setChipID(((EditText) findViewById(R.id.chipIdText)).getText().toString());
                String species = ((EditText) findViewById(R.id.speciesText)).getText().toString();
                pet.setSpecies(species.isEmpty() ? "other" : species);
                pet.setComments(((EditText) findViewById(R.id.commentsText)).getText().toString());

                OwnerInfo ownerInfo = new OwnerInfo();
                ownerInfo.setFirstName(((EditText) findViewById(R.id.ownerNameText)).getText().toString());
                ownerInfo.setLastName(((EditText) findViewById(R.id.ownerLastName)).getText().toString());
                ownerInfo.setAddress(((EditText) findViewById(R.id.ownerAddressText)).getText().toString());
                ownerInfo.setPhoneNumber(((EditText) findViewById(R.id.ownerPhoneText)).getText().toString());

                VetInfo vetInfo = new VetInfo();
                vetInfo.setFirstName(((EditText) findViewById(R.id.vetNameText)).getText().toString());
                vetInfo.setLastName(((EditText) findViewById(R.id.vetLastName)).getText().toString());
                vetInfo.setAddress(((EditText) findViewById(R.id.vetAddressText)).getText().toString());
                vetInfo.setPhoneNumber(((EditText) findViewById(R.id.vetPhoneText)).getText().toString());

                pet.setOwnerInfo(ownerInfo);
                pet.setVetInfo(vetInfo);

                switch (pet.getSpecies()) {
                    case "dog":
                        pet.setImageUri(R.drawable.dog1);
                        break;
                    case "cat":
                        pet.setImageUri(R.drawable.cat);
                        break;
                    case "other":
                        pet.setImageUri(R.drawable.lizard);
                        break;
                    default:
                        break;
                }

                petInfoRepository.insertPet(pet);

                Intent intent = new Intent(PetInfoForm.this, PetInfo.class);
                intent.putExtra("petInfo", pet);
                startActivity(intent);
            }
        });

    }

    private void fillForm(PetInfo pet) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        ((EditText) findViewById(R.id.petNameText)).setText(pet.getName());
        ((EditText) findViewById(R.id.date)).setText(format.format(pet.getDateOfBirth()));
        ((EditText) findViewById(R.id.genderText)).setText(pet.getGender());
        ((EditText) findViewById(R.id.breedText)).setText(pet.getBreed());
        ((EditText) findViewById(R.id.colorText)).setText(pet.getColor());
        ((EditText) findViewById(R.id.MarksText)).setText(pet.getDistinguishingMarks());
        ((EditText) findViewById(R.id.chipIdText)).setText(pet.getChipID());
        ((EditText) findViewById(R.id.speciesText)).setText(pet.getSpecies());
        ((EditText) findViewById(R.id.commentsText)).setText(pet.getComments());

        ((EditText) findViewById(R.id.ownerNameText)).setText(pet.getOwnerInfo().getFirstName());
        ((EditText) findViewById(R.id.ownerLastName)).setText(pet.getOwnerInfo().getLastName());
        ((EditText) findViewById(R.id.ownerAddressText)).setText(pet.getOwnerInfo().getAddress());
        ((EditText) findViewById(R.id.ownerPhoneText)).setText(pet.getOwnerInfo().getPhoneNumber());

        ((EditText) findViewById(R.id.vetNameText)).setText(pet.getVetInfo().getFirstName());
        ((EditText) findViewById(R.id.vetLastName)).setText(pet.getVetInfo().getLastName());
        ((EditText) findViewById(R.id.vetAddressText)).setText(pet.getVetInfo().getAddress());
        ((EditText) findViewById(R.id.vetPhoneText)).setText(pet.getVetInfo().getPhoneNumber());
    }
}
