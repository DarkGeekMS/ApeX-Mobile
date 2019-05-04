package com.example.android.apexware;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
//import static com.example.android.apexware.Profile.postArrayList;

public class PostFragment extends Fragment {
    View view;
    CustomAdapterForHomePage adapter;
    ListView list;
    public PostFragment() {
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.post_fragment,container,false);
        try{
            Profile profile=new Profile();
            list=view.findViewById(R.id.userPostedList);
            ArrayList<Post> postArrayList=profile.postArrayList;
            adapter = new CustomAdapterForHomePage((Activity) getContext(), postArrayList);
            list.setAdapter(adapter);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                list.setNestedScrollingEnabled(true);
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getContext(),"Fragment crashed Peacfuly",Toast.LENGTH_SHORT).show();
        }
        return view;
    }
}
