package com.sdmd.mgava.mypetsapp;

import android.content.Intent;
import android.os.Bundle;

import com.sdmd.mgava.mypetsapp.fragment.PetInfoFragment;

public class MainActivity extends Base implements PetInfoFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loggedIn = isLoggedIn();

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.sp_fragment, PetInfoFragment.newInstance(), "speciesList")
                    .commit();
        }
    }


    @Override
    public void onSpeciesSelected(String species) {
        Intent intent = new Intent(this, BrowseActivity.class);
        intent.putExtra("species", species);
        startActivity(intent);
    }

}
