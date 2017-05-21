package com.sdmd.mgava.mypetsapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class FragChoice extends Fragment {
    private ListView listView;

    private BaseAdapter adapter;
    public static List<PetInfo> petInfos;
    public static final String EXTRA_PET_CATEGORY2 = "pet.category";
    public static final String EXTRA_FOR_STATUS2 = "status2";
    private int category;
    private boolean status;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_choice_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        category = getArguments().getInt(EXTRA_PET_CATEGORY2);
        status = getArguments().getBoolean(EXTRA_FOR_STATUS2);
        if (category == 1) {
            petInfos = PetInfoFactory.listOfPetInfos;
        } else if (category == 2) {
            petInfos = PetInfoFactory.listOfPetInfos;
        } else if (category == 3) {
            petInfos = PetInfoFactory.listOfPetInfos;
        }
        adapter = new Adapter(getActivity(), petInfos);
        listView = (ListView) getActivity().findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setVisibility(View.VISIBLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!status) {
                    Toast.makeText(getActivity(), "Details are only available to registered users.", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
