package com.example.android.apexware;

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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.lang.StrictMath.abs;
/** This class handle see other users profile */
public class ExploreUserProfile extends AppCompatActivity {
  private TabLayout tabLayout;
  private AppBarLayout appBarLayout;
  private ViewPager viewPager;
  private CollapsingToolbarLayout collapsingToolbarLayout;
  private LinearLayout linearLayout;
  public static ArrayList<Post> postArrayList = new ArrayList<>();
  ArrayList<Comment> commentArrayList = new ArrayList<>();
  String desiredUser;

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_explore_user_profile);
    Window window = this.getWindow();
    // clear FLAG_TRANSLUCENT_STATUS flag:
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    // finally change the color
    window.setStatusBarColor(ContextCompat.getColor(this, R.color.myblue));

    Intent i = getIntent();
    desiredUser = i.getStringExtra("username");
    desiredUser = desiredUser.substring(10, desiredUser.indexOf('.'));
    tabLayout = (TabLayout) findViewById(R.id.explore_profile_tab_layout);
    appBarLayout = (AppBarLayout) findViewById(R.id.explore_profile_appbar_layout);
    viewPager = (ViewPager) findViewById(R.id.explore_profile_viewpager);
    collapsingToolbarLayout =
        (CollapsingToolbarLayout) findViewById(R.id.explore_collapsingToolbarLayout);
    linearLayout = (LinearLayout) findViewById(R.id.explore_profile_info);

    appBarLayout.addOnOffsetChangedListener(
        new AppBarLayout.OnOffsetChangedListener() {
          boolean isShow = true;
          int scrollRange = -1;

          @Override
          public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            if (scrollRange == -1) {
              scrollRange = appBarLayout.getTotalScrollRange();
              linearLayout.setVisibility(View.VISIBLE);
            }
            if (scrollRange + verticalOffset == 0) {
              collapsingToolbarLayout.setTitle("Title");
              linearLayout.setVisibility(View.INVISIBLE);
              isShow = true;
            } else if (isShow) {
              collapsingToolbarLayout.setTitle(
                  " "); // careFull there should be a space between double quote otherwise it won`t
                        // work
              isShow = false;
              linearLayout.setVisibility(View.VISIBLE);
            }
          }
        });
    final TextView displayName = (TextView) findViewById(R.id.explore_profile_user_name);
    final TextView username = (TextView) findViewById(R.id.explore_profile_tag_name);
    final TextView karmaCounterAndFollowers =
        (TextView) findViewById(R.id.explore_profile_creation_info);
    final ImageView profilepicture = (ImageView) findViewById(R.id.explore_profilepicture);
    if (Routes.active_mock) {
      displayName.setText("Mazen");
      username.setText("u/mazen");
      karmaCounterAndFollowers.setText("1 Karma .1m . 0 followers");
      Picasso.get().load(R.drawable.profilepic).resize(50, 50).into(profilepicture);
      ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

      viewPagerAdapter.addFragment(new PostFragment(), "Post");
      viewPagerAdapter.addFragment(new CommentFragment(), "Comment");
      viewPagerAdapter.addFragment(new AboutFragment(), "About");
      viewPager.setAdapter(viewPagerAdapter);
      tabLayout.setupWithViewPager(viewPager);
    } else {
      getResponse(
          Request.Method.POST,
          Routes.userData,
          null,
          new VolleyCallback() {
            @Override
            public void onSuccessResponse(String response) {
              try {
                // converting response to json object
                JSONObject obj = new JSONObject(response);
                JSONObject userInfo = obj.getJSONObject("userData");

                displayName.setText(userInfo.getString("username"));
                username.setText(userInfo.getString("username"));

                String karma_followers = userInfo.getString("karma");
                karmaCounterAndFollowers.setText(karma_followers);

                Picasso.get()
                    .load("http://35.232.3.8" + userInfo.getString("avatar"))
                    .resize(50, 50)
                    .into(profilepicture);
                JSONArray post = obj.getJSONArray("posts");
                for (int i = 0; i < post.length(); i++) {
                  JSONObject current = post.getJSONObject(i);
                  Post temp = new Post();
                  temp.setPostId(current.getString("id"));
                  temp.setPostOwner(current.getString("post_writer_username"));
                  temp.setPostTitle(current.getString("title"));
                  temp.setApexcomName("apex_com_name");
                  temp.setVotesCount(current.getInt("votes"));
                  temp.setApexcomLogo("https://i.imgur.com/S7USWRb.jpg");
                  Date currentTime = Calendar.getInstance().getTime();
                  String currendate = "20" + Integer.toString(currentTime.getYear() - 100);
                  if (currentTime.getMonth() > 9) {
                    currendate += "-" + Integer.toString(currentTime.getMonth());
                  } else {
                    currendate += "-" + "0" + Integer.toString(currentTime.getMonth() + 1);
                  }
                  currendate += "-" + Integer.toString(currentTime.getDate()) + " ";
                  currendate += Integer.toString(currentTime.getHours());
                  currendate += ":" + Integer.toString(currentTime.getMinutes());
                  currendate += ":" + Integer.toString(currentTime.getSeconds());
                  String createdDate = current.getString("created_at");
                  // Custom date format
                  SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

                  // Calculate difference between current and created time
                  Date d1 = null;
                  Date d2 = null;
                  try {
                    d2 = format.parse(currendate);
                    d1 = format.parse(createdDate);
                  } catch (ParseException e) {
                    e.printStackTrace();
                  }
                  // Get msec from each, and subtract.
                  long diffTime = abs(d2.getTime() - d1.getTime());
                  long diffSeconds = diffTime / 1000 % 60;
                  long diffMinutes = diffTime / (60 * 1000);
                  long diffHours = diffTime / (60 * 60 * 1000);
                  long diffDays = d2.getDate() - d1.getDate();
                  long diffWeeks = diffDays / 7;
                  if (diffMinutes <= 59) {
                    temp.setPostCreateDate(Long.toString(diffMinutes) + " min ago");
                  } else if (diffHours < 23) {
                    temp.setPostCreateDate(Long.toString(diffHours) + " hr ago");
                  } else if (diffDays < 7) {
                    temp.setPostCreateDate(Long.toString(diffDays) + " days ago");
                  } else {
                    temp.setPostCreateDate(Long.toString(diffWeeks) + " weeks ago");
                  }

                  String type = current.getString("content");
                  String type1 = "http://35.232.3.8" + current.getString("img");
                  String type2 = current.getString("videolink");
                  if (type != "null") { // text post
                    temp.setTextPostcontent(type);
                    temp.setPostType(0);
                  } else if (type1 != "null") { // image post
                    temp.setImageURL(type1);
                    temp.setPostType(1);
                  } else if (type2 != "null") {
                    temp.setVideoURL(type2);
                    temp.setPostType(2);
                  }
                  postArrayList.add(temp);
                }
                // send data to the post fragment
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("post", postArrayList);
                // set Fragmentclass Arguments
                PostFragment fragobj = new PostFragment();
                fragobj.setArguments(bundle);

                ViewPagerAdapter viewPagerAdapter =
                    new ViewPagerAdapter(getSupportFragmentManager());
                viewPagerAdapter.addFragment(new PostFragment(), "Post");
                viewPagerAdapter.addFragment(new CommentFragment(), "Comment");
                viewPagerAdapter.addFragment(new AboutFragment(), "About");
                viewPager.setAdapter(viewPagerAdapter);
                tabLayout.setupWithViewPager(viewPager);
              } catch (JSONException e) {
                e.printStackTrace();
              }
            }
          },
          desiredUser);
    }
  }
  /**
   * This function handle response on request see other user profile
   *
   * @param url :requested server route
   * @param method : request method
   */
  public void getResponse(
      int method,
      String url,
      JSONObject jsonValue,
      final VolleyCallback callback,
      final String name) {
    final User user = SharedPrefmanager.getInstance(this).getUser();
    final String token = user.getToken();
    StringRequest stringRequest =
        new StringRequest(
            Request.Method.POST,
            url,
            new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                int x = 0;
                callback.onSuccessResponse(response);
              }
            },
            new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                  if (error.getClass().equals(TimeoutError.class)) {
                    errorMessage = "Request timeout";
                  } else if (error.getClass().equals(NoConnectionError.class)) {
                    errorMessage = "Failed to connect server";
                  }
                } else {
                  String result = new String(networkResponse.data);
                  try {
                    JSONObject response = new JSONObject(result);
                    String status = response.getString("status");
                    String message = response.getString("message");

                    Log.e("Error Status", status);
                    Log.e("Error Message", message);

                    if (networkResponse.statusCode == 404) {
                      errorMessage = "Resource not found";
                    } else if (networkResponse.statusCode == 401) {
                      errorMessage = message + " Please login again";
                    } else if (networkResponse.statusCode == 400) {
                      errorMessage = message + " Check your inputs";
                    } else if (networkResponse.statusCode == 500) {
                      errorMessage = message + " Something is getting wrong";
                    }
                  } catch (JSONException e) {
                    e.printStackTrace();
                  }
                }
                Log.i("Error", errorMessage);
                error.printStackTrace();
              }
            }) {
          @Override
          protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<>();
            params.put("username", name);
            params.put("token", token);
            return params;
          }
        };
    VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
  }
}
