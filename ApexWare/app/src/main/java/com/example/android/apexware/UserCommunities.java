package com.example.android.apexware;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

public class UserCommunities extends AppCompatActivity {

    ListView listView;
    String[] nameArray;
    String[] imageLinks;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_communities);

        Window window = this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.myblue));

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
