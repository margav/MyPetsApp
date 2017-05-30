package com.sdmd.mgava.mypetsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class PetInfoAdapter extends ArrayAdapter {

    static class DataHandler{

        ImageView petImage;
        TextView petName;
        TextView petBreed;
    }

    List list=new ArrayList();

    public PetInfoAdapter(Context context, int resource) {

        super(context, resource);
    }

    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return this.list.size();
    }


    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        View row;
        row = convertView;
        DataHandler handler;
        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           row= inflater.inflate(R.layout.activity_petinfo_row,parent,false);
                 handler= new DataHandler();
                 handler.petImage=(ImageView) row.findViewById(R.id.pet_pic_resource);
                 handler.petName=(TextView) row.findViewById(R.id.pet_name_resource);
                 handler.petBreed=(TextView) row.findViewById(R.id.pet_breed_resource);
        }else{
            handler=(DataHandler) row.getTag();
        }

        DataProvider dataProvider;
        dataProvider=(DataProvider)this.getItem(position);
        handler.petImage.setImageResource(dataProvider.getPet_pic());
        handler.petName.setText(dataProvider.getPet_name());
        handler.petBreed.setText(dataProvider.getPet_breed());

        return row;
    }
}
