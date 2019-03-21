package com.example.android.apexware;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
//import android.widget.Toolbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import android.app.SearchManager;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {

    public Toolbar toolbar;
    public TabLayout tabLayout;
    public ViewPager viewPager;
    private DrawerLayout drawerLayout;
    ListView list;
    CustomAdapterForHomePage adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //default bar is no action bar but this is custom for bar for every layout
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        BottomNavigationViewEx bnve =  findViewById(R.id.bnve);


        //create list view object to handele given array adapter
        list = (ListView) findViewById(R.id.postslist);
        //create array adapter list for the view
        ArrayList<Post> postArrayList = new ArrayList();
        Post testpost = new Post();
        testpost.postType = 0;
        testpost.postOwner = "Mazen";
        testpost.postCreateDate = 19;
        testpost.ApexcomLogo = "https://i.imgur.com/S7USWRb.jpg";
        testpost.apexcomName = "AndroidTeam";
        testpost.postTitle = "Test this pot";
        testpost.textPostTitle="Hello its plaesure to meet you here please fell as hoemand leave wuefhiwoeufhwieufhweiufhief  fhewiuf eiufh ief ufhieuhf iuehf uihefiu h feufh iuehf  fiue  eiufhei h efiueh iuh feiufh eiuhf uehf iuhiufheiufheiufh  ehfiuefheiufhiuhefiufehiue  efihoeoIUFEHWEIFUHIF IUHIU HIU";
        // uo&down vote and comment ";
        //testpost.ImageURL = "https://i.imgur.com/S7USWRb.jpg";
        // testpost.videoURL="https://www.youtube.com/watch?v=mWRsgZuwf_8&list=RDL3wKzyIN1yk&index=23";
        postArrayList.add(testpost);
        Post testpost1=new Post();
        testpost1.postType = 1;
        testpost1.postOwner = "Mazen";
        testpost1.postCreateDate = 19;
        testpost1.ApexcomLogo = "https://i.imgur.com/S7USWRb.jpg";
        testpost1.apexcomName = "AndroidTeam";
        testpost1.postTitle = "Test this pot";
        testpost1.ImageURL = "https://i.imgur.com/S7USWRb.jpg";
        postArrayList.add(testpost1);
        Post testpost2=new Post();
        testpost2.postType = 2;
        testpost2.postOwner = "Mazen";
        testpost2.postCreateDate = 19;
        testpost2.ApexcomLogo = "https://i.imgur.com/S7USWRb.jpg";
        testpost2.apexcomName = "AndroidTeam";
        testpost2.postTitle = "Test this pot";
        testpost2.ImageURL = "https://i.imgur.com/S7USWRb.jpg";
        testpost2.videoURL =testpost2.getVideoId("https://www.youtube.com/watch?v=mWRsgZuwf_8");
        postArrayList.add(testpost2);
        postArrayList.add(testpost);
        postArrayList.add(testpost1);
        postArrayList.add(testpost2);
        postArrayList.add(testpost);
        // to do creat instances of posts
        adapter = new CustomAdapterForHomePage(this, postArrayList);
        list.setAdapter(adapter);


        //add image icon to open drawer from it
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_audiotrack_black_24dp);
        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        if(menuItem.getItemId()==R.id.history)
                            Toast.makeText( getApplicationContext(),"History has been choosed",Toast.LENGTH_LONG).show();
                        if(menuItem.getItemId()==R.id.save)
                            Toast.makeText( getApplicationContext(),"Save has been choosed",Toast.LENGTH_LONG).show();
                        if(menuItem.getItemId()==R.id.myProfile)
                            Toast.makeText( getApplicationContext(),"Myprofile has been choosed",Toast.LENGTH_LONG).show();
                        if(menuItem.getItemId()==R.id.setting)
                            Toast.makeText( getApplicationContext(),"Setting has been choosed",Toast.LENGTH_LONG).show();
                        // close drawer when item is tapped
                        drawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            getMenuInflater().inflate(R.menu.options_menu, menu);
            MenuItem mSearch = menu.findItem(R.id.action_search);
            SearchView mSearchView = (SearchView) mSearch.getActionView();
            mSearchView.setOnSearchClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "you type" , Toast.LENGTH_LONG).show();
                }
            });
            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Toast.makeText(getApplicationContext(), "you type" + query, Toast.LENGTH_LONG).show();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    Toast.makeText(getApplicationContext(), "you type" + newText, Toast.LENGTH_LONG).show();
                    return false;
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return super.onCreateOptionsMenu(menu);

    }
}
