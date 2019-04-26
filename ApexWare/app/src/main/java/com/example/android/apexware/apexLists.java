package com.example.android.apexware;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class apexLists extends AppCompatActivity {
    ArrayList<CommunityInfo> commlist=new ArrayList<>() ;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_communities);
        Window window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.myblue));
        CommunityInfo comm1=new CommunityInfo("any name bla bla bla","JDOCNOJWNC",1234859,"https://i.imgur.com/6z13lku.jpg","https://i.imgur.com/Z6s6Who.jpg");
        CommunityInfo comm2=new CommunityInfo("any name bs mo5tlf","JDOCNOJWNC",69841651,"https://i.imgur.com/6z13lku.jpg","https://i.imgur.com/Z6s6Who.jpg");
        CommunityInfo comm3=new CommunityInfo("any name bs very different","JDOCNOJWNC",65541651,"https://i.imgur.com/6z13lku.jpg","https://i.imgur.com/Z6s6Who.jpg");
        commlist.add(comm1);
        commlist.add(comm2);
        commlist.add(comm3);
        ListView listt=findViewById(R.id.listcommunitiesid);
        CommAdapter adapter=new CommAdapter(this,commlist);
        listt.setAdapter(adapter);
    listt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent i=new Intent(apexLists.this,Community.class);
            Object current = parent.getItemAtPosition(position);
            CommunityInfo c1=(CommunityInfo) current;
            Gson gson = new Gson();
            String commAsString = gson.toJson(c1);
            i.putExtra("Comm", commAsString); // sending the community to next activity
            startActivity(i);
        }
    });
    }
}
