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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v7.widget.SearchView;

import com.android.volley.Request;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.android.apexware.Routes.active_mock;


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        /*
        * test request to get apex communities*/
        /*end test*/
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_list_of_community, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbarForCommunity);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("");
        ActionBar actionbar =activity.getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.list_white);
        setHasOptionsMenu(true);
        //we should receive the list here
        ArrayList<CommunityInfo> commlist=new ArrayList<>() ;
        CommunityInfo comm1=new CommunityInfo("any name bla bla bla","JDOCNOJWNC",1234859,"https://i.imgur.com/6z13lku.jpg","https://i.imgur.com/Z6s6Who.jpg","co1");
        CommunityInfo comm2=new CommunityInfo("any name bs mo5tlf","JDOCNOJWNC",69841651,"https://i.imgur.com/6z13lku.jpg","https://i.imgur.com/Z6s6Who.jpg","co1");
        CommunityInfo comm3=new CommunityInfo("any name bs very different","JDOCNOJWNC",65541651,"https://i.imgur.com/6z13lku.jpg","https://i.imgur.com/Z6s6Who.jpg","co3");
        commlist.add(comm1);
        commlist.add(comm2);
        commlist.add(comm3);
        ListView listt=view.findViewById(R.id.listOFCommunity);
        CommAdapter adapter=new CommAdapter(this.getActivity(),commlist);
        listt.setAdapter(adapter);
        listt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(activity,Community.class);
                Object current = parent.getItemAtPosition(position);
                CommunityInfo c1=(CommunityInfo) current;
                Gson gson = new Gson();
                String commAsString = gson.toJson(c1);
                i.putExtra("Comm", commAsString); // sending the community to next activity
                startActivity(i);
            }
        });

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
