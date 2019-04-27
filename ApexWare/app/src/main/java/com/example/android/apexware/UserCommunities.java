package com.example.android.apexware;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

/**
 * list of communities to post to including your subscribed apexcoms or all apexcoms to serch into
 *
 * @author mostafa
 */
public class UserCommunities extends AppCompatActivity {

 // public static final CustomAdapterForCommunities adapter;
  ListView listView;
  String[] nameArray;
  String[] idArray;
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

    /*
      getUserCommunities( nameArray, imageLinks, idArray);
      TODO get all communities from database and add them to list
      */

      CustomAdapterForCommunities adapter = new CustomAdapterForCommunities(this, nameArray, imageLinks, idArray);
    listView = (ListView) findViewById(R.id.listcommunitiesid);
    listView.setAdapter(adapter);
  }

  /** return to post and discard choosing the community */
  public void returnToPost(View view) {
    Intent intent = new Intent(this, CreatePost.class);
    startActivity(intent);
  }

}
