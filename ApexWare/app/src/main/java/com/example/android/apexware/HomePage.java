package com.example.android.apexware;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import java.util.ArrayList;
public class HomePage extends AppCompatActivity {
  private DrawerLayout drawerLayout;
  ListView list;
  int first_row, last_row, total_row = 20;
  //The "x" and "y" position of the "Show Button" on screen.
  Point p;
  CustomAdapterForHomePage adapter;
  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home_page);


    Window window = this.getWindow();
    // clear FLAG_TRANSLUCENT_STATUS flag:
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    // finally change the color
    window.setStatusBarColor(ContextCompat.getColor(this, R.color.myblue));


      FloatingActionButton btn_show = (FloatingActionButton) findViewById(R.id.fab);
      btn_show.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View arg0) {

              //Open popup window
              if (p != null)
                  showPopup(HomePage.this, p);
          }
      });
    // default bar is no action bar but this is custom for bar for every layout
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setTitle("");



    BottomNavigationViewEx bnve = findViewById(R.id.bnve);
    bnve.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



    // create list view object to handele given array adapter
    list = (ListView) findViewById(R.id.postslist);
    // create array adapter list for the view
    ArrayList<Post> postArrayList = new ArrayList();
    Post testpost = new Post();
    testpost.setPostId("t3_1");
    testpost.setPostType(0);
    testpost.setPostOwner("Mazen");
    testpost.setPostCreateDate(19);
    testpost.setApexcomLogo("https://i.imgur.com/S7USWRb.jpg");
    testpost.setApexcomName("AndroidTeam");
    testpost.setPostTitle("Test this post");
    testpost.setTextPostcontent(
            "Hello its plaesure to meet you here please fell as hoemand leave wuefhiwoeufhwieufhweiufhief  fhewiuf eiufh ief ufhieuhf iuehf uihefiu h feufh iuehf  fiue  eiufhei h efiueh iuh feiufh eiuhf uehf iuhiufheiufheiufh  ehfiuefheiufhiuhefiufehiue  efihoeoIUFEHWEIFUHIF IUHIU HIU");
    postArrayList.add(testpost);
    Post testpost1 = new Post();
    testpost1.setPostType(1);
    testpost1.setPostOwner("Mazen");
    testpost1.setPostCreateDate(19);
    testpost1.setApexcomLogo("https://i.imgur.com/7cWUnve.jpg");
    testpost1.setApexcomName("AndroidTeam");
    testpost1.setPostTitle("Test this post");
    testpost1.setImageURL("https://i.imgur.com/7cWUnve.jpg");
    postArrayList.add(testpost1);
    Post testpost2 = new Post();
    testpost2.setPostType(2);
    testpost2.setPostOwner("Mazen");
    testpost2.setPostCreateDate(19);
    testpost2.setApexcomLogo("https://i.imgur.com/S7USWRb.jpg");
    testpost2.setApexcomName("AndroidTeam");
    testpost2.setPostTitle("Test this post");
    testpost2.setImageURL("https://i.imgur.com/7cWUnve.jpg");
    testpost2.setVideoURL("https://www.youtube.com/watch?v=C6hq5cziIHc");
    postArrayList.add(testpost2);
    postArrayList.add(testpost);
    postArrayList.add(testpost1);
    postArrayList.add(testpost2);
    postArrayList.add(testpost);
    // to do create instances of posts

    adapter = new CustomAdapterForHomePage(this, postArrayList);
    list.setAdapter(adapter);
    list.setOnItemClickListener(
            new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(HomePage.this, postsandcomments.class);
                Object current = parent.getItemAtPosition(position);
                Post p1 = (Post) current;
                Gson gson = new Gson();
                String postAsString = gson.toJson(p1);
                intent.putExtra("postToDisplay", postAsString); // sending the post to next activity
                startActivity(intent);
              }
            });

    // add image icon to open drawer from it
    ActionBar actionbar = getSupportActionBar();
    actionbar.setDisplayHomeAsUpEnabled(true);
    actionbar.setHomeAsUpIndicator(R.drawable.ic_audiotrack_black_24dp);
    drawerLayout = findViewById(R.id.drawer_layout);



    // Enable Navigation bar
    NavigationView navigationView = findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
              @Override
              public boolean onNavigationItemSelected(MenuItem menuItem) {
                // set item as selected to persist highlight
                menuItem.setChecked(true);
                if (menuItem.getItemId() == R.id.history)
                  Toast.makeText(getApplicationContext(), "History has been choosed", Toast.LENGTH_LONG)
                          .show();
                if (menuItem.getItemId() == R.id.save)
                  Toast.makeText(getApplicationContext(), "Save has been choosed", Toast.LENGTH_LONG)
                          .show();
                if (menuItem.getItemId() == R.id.myProfile)
                  startActivity(new Intent(getApplicationContext(), Profile.class));
                if (menuItem.getItemId() == R.id.setting)
                  Toast.makeText(getApplicationContext(), "Setting has been choosed", Toast.LENGTH_LONG)
                          .show();
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
      mSearchView.setOnSearchClickListener(
              new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  Toast.makeText(getApplicationContext(), "you type", Toast.LENGTH_LONG).show();
                }
              });
      mSearchView.setOnQueryTextListener(
              new OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                  Toast.makeText(getApplicationContext(), "you type" + query, Toast.LENGTH_LONG).show();
                  return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                  Toast.makeText(getApplicationContext(), "you type" + newText, Toast.LENGTH_LONG)
                          .show();
                  return false;
                }
              });
    } catch (Exception e) {
      e.printStackTrace();
    }
    return super.onCreateOptionsMenu(menu);
  }
    private BottomNavigationViewEx.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationViewEx.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.i_music:
                    Toast.makeText(getApplicationContext(),"music is selected",Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.i_backup:
                    Toast.makeText(getApplicationContext(),"backup is selected",Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.i_favor:
                    Toast.makeText(getApplicationContext(),"favour is selected",Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.i_visibility:
                    Toast.makeText(getApplicationContext(),"visibility is selected",Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.i_empty:
                    Toast.makeText(getApplicationContext(),"empty is selected",Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }
    };
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        int[] location = new int[2];
        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.fab);

        // Get the x, y location and store it in the location[] array
        // location[0] = x, location[1] = y.
        button.getLocationOnScreen(location);

        //Initialize the Point with x, and y positions
        p = new Point();
        p.x = location[0];
        p.y = location[1];
    }

    // Get the x and y position after the button is draw on screen
// (It's important to note that we can't get the position in the onCreate(),
// because at that stage most probably the view isn't drawn yet, so it will return (0, 0))

    // The method that displays the popup.
    private void showPopup(final Activity context, Point p) {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int popupWidth = size.x;
        double y=0.28*size.y;
        int popupHeight = (int)y;

        // Inflate the popup_layout.xml
        RelativeLayout viewGroup = (RelativeLayout) context.findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_layout, viewGroup);

        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(popupWidth);
        popup.setHeight(popupHeight);
        popup.setFocusable(true);

        // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
        int OFFSET_X = 30;
        int OFFSET_Y = 30;

        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);

        // Getting a reference to Close button, and close the popup when clicked.
        Button close = (Button) layout.findViewById(R.id.cancel_button);
        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });
    }
}

