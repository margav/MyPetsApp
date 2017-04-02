package com.sdmd.mgava.mypetsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends BaseAdapter {

    private Context context;
    private List<PetInfo> petInfos;

    public ItemAdapter(Context context, List<PetInfo> petInfos) {
        this.context = context;
        this.petInfos = petInfos;
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
            convertView = inflater.inflate(R.layout.layout_item, parent, false);
        }

        PetInfo petInfo = (PetInfo) getItem(position);

        TextView nameTextView = (TextView) convertView.findViewById(R.id.pet_name_view_layout_item);
        TextView breedTextView = (TextView) convertView.findViewById(R.id.pet_breed_view_layout_item);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.image_view_layout_item);

        imageView.setImageResource(petInfo.getImageId());
        nameTextView.setText(petInfo.getPetNameId());
        breedTextView.setText(petInfo.getPetBreedId());

        return convertView;
    }
}
