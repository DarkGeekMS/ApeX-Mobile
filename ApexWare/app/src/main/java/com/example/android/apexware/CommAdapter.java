package com.example.android.apexware;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class CommAdapter extends ArrayAdapter {
    private final Activity context;
    //to store the list of communities names
   ArrayList<CommunityInfo> list1;
     CommAdapter(Activity context,ArrayList<CommunityInfo>l1)
    {
        super(context,R.layout.community_sample,l1 );
        this.context=context;
        this.list1=l1;
    }
    public View getView(int position, View view, ViewGroup parent) {
        CommunityInfo comm=list1.get(position);
        if(view==null)
            view = LayoutInflater.from(context).inflate(R.layout.community_sample, parent, false);
        TextView nameTextField =  view.findViewById(R.id.community_name_TV);
        ImageView imageView =  view.findViewById(R.id.globeIMG);
        nameTextField.setText(comm.getCommunityName());
        Picasso.get().load(comm.getCommunityLogo()).into(imageView);
        return view;
}

}
