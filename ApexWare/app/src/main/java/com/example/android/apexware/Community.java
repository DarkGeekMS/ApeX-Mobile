package com.example.android.apexware;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Community extends AppCompatActivity {
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private LinearLayout linearLayout;
    private LinearLayout btns;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.communityview);
        Window window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.myblue));
        /**
         * get comm info
         */
        Gson gson = new Gson();
        String commAsString = getIntent().getStringExtra("Comm");
        final CommunityInfo comm1 = gson.fromJson(commAsString, CommunityInfo.class);
        tabLayout=  findViewById(R.id.comm_tab_layout);
        appBarLayout= findViewById(R.id.community_appbar_layout);
        viewPager= findViewById(R.id.comm_viewpager);
        collapsingToolbarLayout =  findViewById(R.id.comcollapsingToolbarLayout);
        linearLayout= findViewById(R.id.texts);
        btns=findViewById(R.id.btns);
        TextView comname=findViewById(R.id.comm_name);
        TextView comTagName=findViewById(R.id.comm_tag_name);
        TextView followers=findViewById(R.id.commfollowers);
        CircleImageView logo=findViewById(R.id.commLogo);
        Picasso.get().load(comm1.getCommunityLogo()).into(logo);
        ImageView background=findViewById(R.id.toolbarbackground);
        Picasso.get().load(comm1.getBackground()).into(background);
        comname.setText(comm1.getCommunityName());
        comTagName.setText(comm1.getCommunityTagName());
        followers.setText(String.valueOf(comm1.getnumOfFollowers()));
        ToggleButton sub=findViewById(R.id.subscribeBtn);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                    linearLayout.setVisibility(View.VISIBLE);
                    btns.setVisibility(View.VISIBLE);
                }
                if (scrollRange + verticalOffset == 0) {

                    linearLayout.setVisibility(View.INVISIBLE);
                    btns.setVisibility(View.INVISIBLE);
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");//careFull there should be a space between double quote otherwise it won`t work
                    isShow = false;
                    linearLayout.setVisibility(View.VISIBLE);
                    btns.setVisibility(View.VISIBLE);

                }
            }
        });
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new PostsFragment(),"posts");
        viewPagerAdapter.addFragment(new AboutFragmentforcommunity(),"About");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
       final Button options = findViewById(R.id.commOptions);
        options.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popup = new PopupMenu(Community.this, options);
                        popup.getMenuInflater().inflate(R.menu.commmenu, popup.getMenu());
                        popup.setOnMenuItemClickListener(
                                new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem item) {
                                        if(item.getItemId()==R.id.menuInfo)
                                            Toast.makeText(Community.this,"should show info",Toast.LENGTH_SHORT).show();

                                            // we can use item name to make intent for the new responces
                                        if(item.getItemId()==R.id.contactmods)
                                            Toast.makeText(Community.this,"will be asked",Toast.LENGTH_SHORT).show();
                                        return true;
                                    }
                                });
                        popup.show(); // showing popup menu
                    }
                });
      }


}
