package com.sdmd.mgava.mypetsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.sdmd.mgava.mypetsapp.repository.PetInfoRepository;

import com.sdmd.mgava.mypetsapp.model.PetInfo;



public class Base extends AppCompatActivity {

    protected boolean loggedIn;
    protected PetInfo pet;
    protected String species;
    protected PetInfoRepository petInfoRepository;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
                sharedPreferences.edit().remove("username").apply();
                return true;
            case R.id.addPet:
                Intent intent1 = new Intent(this, PetInfoForm.class);
                startActivity(intent1);
                return true;
            case R.id.edtPet:
                Intent intent2 = new Intent(this, PetInfoForm.class);
                intent2.putExtra("petInfo", this.getPet());
                startActivity(intent2);
                return true;
            case R.id.deletePet:
                petInfoRepository.deletePet(this.getPet().getName());
                Toast.makeText(this, "PetInfoActivity deleted!", Toast.LENGTH_LONG).show();
                Intent intent3 = new Intent(this, BrowseActivity.class);
                intent3.putExtra("species", this.getSpecies());
                startActivity(intent3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        if (pet == null) {
            menu.findItem(R.id.edtPet).setVisible(false);
            menu.findItem(R.id.deletePet).setVisible(false);
        }
        if (loggedIn) {
            menu.findItem(R.id.login).setVisible(false);
        } else {
            menu.findItem(R.id.logout).setVisible(false);
        }
        return true;
    }

    protected boolean isLoggedIn() {
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        return username == null ? false : true;
    }

    public PetInfo getPet() {
        return pet;
    }

    public void setPet(PetInfo pet) {
        this.pet = pet;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }
}
