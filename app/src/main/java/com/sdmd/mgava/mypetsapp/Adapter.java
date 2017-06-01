package com.sdmd.mgava.mypetsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.sdmd.mgava.mypetsapp.model.PetInfo;


public class Adapter extends BaseAdapter {

    private Context context;
    private List<PetInfo> petInfos;
    private int layout;

    public Adapter(Context context, List<PetInfo> petInfos) {
        this.context = context;
        this.petInfos = petInfos;
        this.layout = R.layout.petinfo_list;
    }

    @Override
    public int getCount() {
        return petInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return petInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(this.context);
            convertView = inflater.inflate(layout, parent, false);
        }

        PetInfo pet = (PetInfo) getItem(position);

        TextView nameText = (TextView) convertView.findViewById(R.id.petName);
        TextView breedText = (TextView) convertView.findViewById(R.id.petBreed);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.petImage);

        nameText.setText(pet.getName());
        breedText.setText(pet.getBreed());
        imageView.setImageResource(pet.getImageUri());

        return convertView;
    }

    public int getLayout() {
        return layout;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }
}
