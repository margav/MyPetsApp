package com.sdmd.mgava.mypetsapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import com.sdmd.mgava.mypetsapp.repository.PetInfoRepository;

/**
 * Show one petInfo
 */
public class PetInfoActivity extends Base {

    com.sdmd.mgava.mypetsapp.model.PetInfo petInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petinfo);
        petInfoRepository = new PetInfoRepository(this);

        Intent intent = getIntent();

        if (intent == null)
            petInfo = savedInstanceState != null ? (com.sdmd.mgava.mypetsapp.model.PetInfo) savedInstanceState.getParcelable("petInfo") : null;
        else
            petInfo = intent.getParcelableExtra("petInfo");

        if (petInfo != null) {
            showPet(petInfo);
            this.setPet(pet);
            this.setSpecies(petInfo.getSpecies());
        } else
            showEmpty();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("petInfo", petInfo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        if (loggedIn) {
            menu.findItem(R.id.login).setVisible(false);
        } else {
            menu.findItem(R.id.logout).setVisible(false);
        }
        return true;
    }

    /**
     * Show the petInfo information
     */
    private void showPet(com.sdmd.mgava.mypetsapp.model.PetInfo petInfo) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        //set the image
        ((ImageView) findViewById(R.id.petImage)).setImageResource(petInfo.getImageUri());

        //set name
        ((TextView) findViewById(R.id.petNameText)).setText(petInfo.getName());

        //set petInfo info
        ((TextView) findViewById(R.id.petInfoText)).setText(petInfo.getBreed() + ", " + petInfo.getGender() + ", " + format.format(petInfo.getDateOfBirth()));

        //set color
        ((TextView) findViewById(R.id.petColorText)).setText(petInfo.getColor());

        //set chipId
        ((TextView) findViewById(R.id.petChipIDText)).setText(petInfo.getChipID());

        //set marks
        ((TextView) findViewById(R.id.petMarksText)).setText(petInfo.getDistinguishingMarks());

        //set owner
        ((TextView) findViewById(R.id.ownerName)).setText(petInfo.getOwnerInfo().getFirstName() + " " + petInfo.getOwnerInfo().getLastName());
        ((TextView) findViewById(R.id.ownerNumber)).setText(petInfo.getOwnerInfo().getPhoneNumber());
        ((TextView) findViewById(R.id.ownerAddress)).setText(petInfo.getOwnerInfo().getAddress());

        //set vet
        ((TextView) findViewById(R.id.vetName)).setText(petInfo.getVetInfo().getFirstName() + " " + petInfo.getOwnerInfo().getLastName());
        ((TextView) findViewById(R.id.vetNumber)).setText(petInfo.getVetInfo().getPhoneNumber());
        ((TextView) findViewById(R.id.vetAddress)).setText(petInfo.getVetInfo().getAddress());

    }

    private void showEmpty() {
        if (Configuration.ORIENTATION_PORTRAIT == getResources().getConfiguration().orientation) {
            (this.findViewById(R.id.imageWrapper)).setVisibility(RelativeLayout.INVISIBLE);
            (this.findViewById(R.id.infoWrapper)).setVisibility(RelativeLayout.INVISIBLE);
            (this.findViewById(R.id.emptyWrapper)).setVisibility(RelativeLayout.VISIBLE);
        } else {
            (this.findViewById(R.id.imageWrapperLand)).setVisibility(LinearLayout.INVISIBLE);
            (this.findViewById(R.id.infoWrapperLand)).setVisibility(LinearLayout.INVISIBLE);
            (this.findViewById(R.id.infoWrapper2Land)).setVisibility(LinearLayout.INVISIBLE);
        }
    }

}
