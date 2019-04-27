package com.example.android.apexware;

import android.content.Context;
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
import android.support.v7.widget.SearchView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class ListOfCommunityFragment extends Fragment {
    public ListOfCommunityFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_list_of_community, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbarForCommunity);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("");

        ActionBar actionbar =activity.getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.list_white);
        setHasOptionsMenu(true);

        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        try {
            final AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.getMenuInflater().inflate(R.menu.options_menu, menu);
            MenuItem mSearch = menu.findItem(R.id.action_search);
            SearchView mSearchView = (SearchView) mSearch.getActionView();
            mSearchView.setOnSearchClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(activity.getApplicationContext(), "you type", Toast.LENGTH_SHORT).show();
                        }
                    });
            mSearchView.setOnQueryTextListener(
                    new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            Toast.makeText(activity.getApplicationContext(), "you type" + query, Toast.LENGTH_SHORT).show();
                            return false;
                        }
                        @Override
                        public boolean onQueryTextChange(String newText) {
                            Toast.makeText(activity.getApplicationContext(), "you type" + newText, Toast.LENGTH_LONG)
                                    .show();AppCompatActivity activity = (AppCompatActivity) getActivity();
                            return false;
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onCreateOptionsMenu(menu,inflater);
    }

}
