package com.example.android.apexware;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment {

    public MessageFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        AppCompatActivity activity=(AppCompatActivity)getActivity();
        View view=inflater.inflate(R.layout.fragment_message, container, false);
        Toolbar toolbar = view.findViewById(R.id.MessagesToolbar);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("Messages");

        ActionBar actionbar =activity.getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.list_white);//------> make it change with profile picture
        setHasOptionsMenu(true);
        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.mesasges_option_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.WriteMessage:
                try{
                startActivity(new Intent(getActivity(),WriteMessage.class));
                return true;
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            case R.id.MarkAllAsRead:

                Toast.makeText(getContext(),"All message are checked",Toast.LENGTH_SHORT).show();
                return false;
            default:
                break;
        }

        return false;
    }
}
