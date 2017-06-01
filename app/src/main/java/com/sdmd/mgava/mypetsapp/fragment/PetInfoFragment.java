package com.sdmd.mgava.mypetsapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.text.ParseException;

import com.sdmd.mgava.mypetsapp.R;
import com.sdmd.mgava.mypetsapp.repository.PetInfoRepository;
import com.sdmd.mgava.mypetsapp.service.PetInfoService;

public class PetInfoFragment extends Fragment {


    PetInfoRepository petInfoRepository;
    PetInfoService petInfoService;

    public interface OnFragmentInteractionListener {
        void onSpeciesSelected(String species);
    }

    public static PetInfoFragment newInstance() {
        return new PetInfoFragment();
    }

    private OnFragmentInteractionListener mListener;

    public PetInfoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_species_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListeners();

        petInfoService = new PetInfoService();
        petInfoRepository = new PetInfoRepository(getActivity());
        petInfoRepository.initDb(petInfoService.getPets(""));
        try {
            petInfoRepository.getPets(null, null);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setListeners() {

        final Button dogsButton = (Button) getActivity().findViewById(R.id.dogsButton);
        dogsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mListener.onSpeciesSelected("dog");
            }
        });

        final Button catsButton = (Button) getActivity().findViewById(R.id.catsButton);
        catsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mListener.onSpeciesSelected("cat");
            }
        });

        final Button otherButton = (Button) getActivity().findViewById(R.id.otherButton);
        otherButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mListener.onSpeciesSelected("other");
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()+ " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
