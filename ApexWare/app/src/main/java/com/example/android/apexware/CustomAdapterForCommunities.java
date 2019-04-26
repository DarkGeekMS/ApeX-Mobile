package com.example.android.apexware;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapterForCommunities extends ArrayAdapter {
    // to reference the Activity
    private final Activity context;

    //to store the list of communities names
    private final String[] nameArray;

    //to store the community images
    private final String[] imageLinkArray;

    public CustomAdapterForCommunities(Activity context, String[] nameArrayParam,String[] imageLinkArrayParam) {
        super(context,R.layout.community_sample , nameArrayParam);
        this.context=context;
        this.nameArray = nameArrayParam;
        this.imageLinkArray = imageLinkArrayParam;
    }
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.community_sample, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTextField = (TextView) rowView.findViewById(R.id.community_name_TV);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.globeIMG);

        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(nameArray[position]);
        Picasso.get().load(imageLinkArray[position]).into(imageView);

        return rowView;

    }

}
