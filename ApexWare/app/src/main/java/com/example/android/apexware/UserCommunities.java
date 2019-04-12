package com.example.android.apexware;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class UserCommunities extends AppCompatActivity {

    ListView listView;
    String[] nameArray;
    String[] imageLinks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_communities);
        //nameArray = getUserCommunities(nameArray);
        CustomAdapterForCommunities adapter = new CustomAdapterForCommunities(this, nameArray, imageLinks);
        listView = (ListView) findViewById(R.id.listcommunitiesid);
        listView.setAdapter(adapter);

    }
/*
    private String[] getUserCommunities(String[] nameArray) {

    }
    */
}
