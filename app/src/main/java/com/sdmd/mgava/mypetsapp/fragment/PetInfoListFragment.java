package com.sdmd.mgava.mypetsapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import java.text.ParseException;
import java.util.List;
import com.sdmd.mgava.mypetsapp.Adapter;
import com.sdmd.mgava.mypetsapp.R;
import com.sdmd.mgava.mypetsapp.model.PetInfo;
import com.sdmd.mgava.mypetsapp.repository.PetInfoRepository;

import static com.sdmd.mgava.mypetsapp.R.id.list_view;


public class PetInfoListFragment extends Fragment {

    private ListView listView;
    private Adapter adapter;
    private List<PetInfo> petInfos;
    private PetInfoRepository petInfoRepository;

    public interface OnFragmentInteractionListener {
        void onPetSelected(PetInfo petInfo);
    }

    public static PetInfoListFragment newInstance() {
        return new PetInfoListFragment();
    }

    private OnFragmentInteractionListener mListener;

    public PetInfoListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_petinfo_list, container, false);
        petInfoRepository = new PetInfoRepository(getActivity());
        Intent intent = getActivity().getIntent();
        String species = intent != null ? intent.getStringExtra("species") : null;
        try {
            petInfos = petInfoRepository.getPets(species);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        adapter = new Adapter(getActivity(), petInfos);
        this.listView=(ListView)view.findViewById(R.id.list_view);
        this.listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListeners();

        if (petInfos.isEmpty())
            showEmpty();
        else {
            ((TextView) getActivity().findViewById(R.id.speciesText)).setText("");
            showListView();
        }
    }


    private void showListView() {
        this.listView.setAdapter(adapter);
        this.listView.setVisibility(View.VISIBLE);
        this.listView.setDivider(null);
    }

    /**
     * Setup the various activity listeners
     */
    private void setListeners() {
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PetInfo petInfo = (PetInfo) parent.getAdapter().getItem(position);
                mListener.onPetSelected(petInfo);
            }
        });
    }


    private void showEmpty() {
        (getActivity().findViewById(list_view)).setVisibility(View.GONE);
        (getActivity().findViewById(R.id.speciesText)).setVisibility(View.GONE);
        (getActivity().findViewById(R.id.emptyText)).setVisibility(View.VISIBLE);
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
