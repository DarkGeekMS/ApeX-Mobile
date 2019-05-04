package com.example.android.apexware;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    public NotificationFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_notification, container, false);
        AppCompatActivity activity=(AppCompatActivity)getActivity();
        Toolbar toolbar = view.findViewById(R.id.NotificationToolbar);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("Notification");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        ActionBar actionbar =activity.getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.list_white);//------> make it change with profile picture
        return view;
    }

}
