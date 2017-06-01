package com.sdmd.mgava.mypetsapp;

import android.content.Intent;
import android.os.Bundle;

import com.sdmd.mgava.mypetsapp.fragment.PetInfoListFragment;
import com.sdmd.mgava.mypetsapp.model.PetInfo;

public class BrowseActivity extends Base implements PetInfoListFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.petinfolist_fragment, PetInfoListFragment.newInstance(), "petInfoList")
                    .commit();
        }
    }


    @Override
    public void onPetSelected(PetInfo petInfo) {
        Intent intent = new Intent(this, PetInfoActivity.class);
        intent.putExtra("petInfo", petInfo);
        startActivity(intent);
    }

}
