package com.sdmd.mgava.mypetsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class BrowseActivity extends AppCompatActivity {

    private ImageView mImageView;
    private TextView mPetNameView;
    private TextView mPetAnimalView;
    private TextView mPetBreedView;
    private TextView mPetSexView;
    private TextView mPetColourView;
    private TextView mPetDateOfBirthView;
    private TextView mPetOwnerView;

    private int currentIndex = 0;
    private static final String KEY_INDEX = "theCurrentIndex";
    static final String EXTRA_KEY_FOR_LIST = "keyForList";

    private List<PetInfo> dogs = PetInfoFactory.getListOfDogs();
    private List<PetInfo> cats = PetInfoFactory.getListOfCats();
    private List<PetInfo> other = PetInfoFactory.getListOfOther();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        mImageView = (ImageView) findViewById(R.id.image_view);
        mPetNameView = (TextView) findViewById(R.id.pet_name_view);
        mPetAnimalView = (TextView) findViewById(R.id.pet_animal_view);
        mPetBreedView = (TextView) findViewById(R.id.pet_breed_view);
        mPetSexView = (TextView) findViewById(R.id.pet_sex_view);
        mPetColourView = (TextView) findViewById(R.id.pet_colour_view);
        mPetDateOfBirthView = (TextView) findViewById(R.id.pet_date_of_birth_view);
        mPetOwnerView = (TextView) findViewById(R.id.pet_name_owner_view);

        if (getIntent().getStringExtra(EXTRA_KEY_FOR_LIST).equals("dogs")) {
        displayViewsDogs();

        ImageButton mArrowLeft = (ImageButton) findViewById(R.id.arrow_left);
        mArrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = currentIndex - 1;
                if (currentIndex < 0) { currentIndex = dogs.size() - 1;}
                displayViewsDogs();

            }
        });

        ImageButton mArrowRight =  (ImageButton) findViewById(R.id.arrow_right);
        mArrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex + 1) % dogs.size();
                displayViewsDogs();
            }
        });
        }

        if (getIntent().getStringExtra(EXTRA_KEY_FOR_LIST).equals("cats")) {
            displayViewsCats();

            ImageButton mArrowLeft = (ImageButton) findViewById(R.id.arrow_left);
            mArrowLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentIndex = currentIndex - 1;
                    if (currentIndex < 0) { currentIndex = cats.size() - 1;}
                    displayViewsCats();

                }
            });

            ImageButton mArrowRight =  (ImageButton) findViewById(R.id.arrow_right);
            mArrowRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentIndex = (currentIndex + 1) % cats.size();
                    displayViewsCats();
                }
            });
        }

        if (getIntent().getStringExtra(EXTRA_KEY_FOR_LIST).equals("other")) {
            displayViewsOther();

            ImageButton mArrowLeft = (ImageButton) findViewById(R.id.arrow_left);
            mArrowLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentIndex = currentIndex - 1;
                    if (currentIndex < 0) { currentIndex = other.size() - 1;}
                    displayViewsOther();

                }
            });

            ImageButton mArrowRight =  (ImageButton) findViewById(R.id.arrow_right);
            mArrowRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentIndex = (currentIndex + 1) % other.size();
                    displayViewsOther();
                }
            });
        }

    }

    private void displayViewsDogs() {

        mImageView.setImageResource(dogs.get(currentIndex).getImageId());

        mPetNameView.setText(dogs.get(currentIndex).getPetNameId());
        mPetAnimalView.setText(dogs.get(currentIndex).getPetAnimalId());
        mPetBreedView.setText(dogs.get(currentIndex).getPetBreedId());
        mPetSexView.setText(dogs.get(currentIndex).getPetSexId());
        mPetColourView.setText(dogs.get(currentIndex).getPetColourId());
        mPetDateOfBirthView.setText(dogs.get(currentIndex).getPetDateOfBirthId());
        mPetOwnerView.setText(dogs.get(currentIndex).getPetOwnerId());
    }

    private void displayViewsCats() {

        mImageView.setImageResource(cats.get(currentIndex).getImageId());

        mPetNameView.setText(cats.get(currentIndex).getPetNameId());
        mPetAnimalView.setText(cats.get(currentIndex).getPetAnimalId());
        mPetBreedView.setText(cats.get(currentIndex).getPetBreedId());
        mPetSexView.setText(cats.get(currentIndex).getPetSexId());
        mPetColourView.setText(cats.get(currentIndex).getPetColourId());
        mPetDateOfBirthView.setText(cats.get(currentIndex).getPetDateOfBirthId());
        mPetOwnerView.setText(cats.get(currentIndex).getPetOwnerId());
    }

    private void displayViewsOther() {

        mImageView.setImageResource(other.get(currentIndex).getImageId());

        mPetNameView.setText(other.get(currentIndex).getPetNameId());
        mPetAnimalView.setText(other.get(currentIndex).getPetAnimalId());
        mPetBreedView.setText(other.get(currentIndex).getPetBreedId());
        mPetSexView.setText(other.get(currentIndex).getPetSexId());
        mPetColourView.setText(other.get(currentIndex).getPetColourId());
        mPetDateOfBirthView.setText(other.get(currentIndex).getPetDateOfBirthId());
        mPetOwnerView.setText(other.get(currentIndex).getPetOwnerId());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INDEX, currentIndex);
    }
}
