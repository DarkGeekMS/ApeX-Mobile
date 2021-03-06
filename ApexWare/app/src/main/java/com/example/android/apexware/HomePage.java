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
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import com.onesignal.OneSignal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.android.apexware.MainActivity.login_guest;
import static java.lang.StrictMath.abs;

/**
 * main page of the whole application
 *
 * @author mazen
 */
public class HomePage extends AppCompatActivity {
  private DrawerLayout drawerLayout;
  ListView list;
  // The "x" and "y" position of the "Show Button" on screen.
  Point p;
  CustomAdapterForHomePage adapter;
  MainActivity loginAsGuestobj = new MainActivity();

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home_page);
    Intent i = getIntent();
    String username = i.getStringExtra("username");
    if (login_guest) {
      // OneSignal Initialization
      OneSignal.startInit(this)
          .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
          .unsubscribeWhenNotificationsAreDisabled(true)
          .init();
      // OneSignal.setSubscription(allow_all); //turn notifications on and off
    }
    Window window = this.getWindow();
    // clear FLAG_TRANSLUCENT_STATUS flag:
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    // finally change the color
    window.setStatusBarColor(ContextCompat.getColor(this, R.color.myblue));

    FloatingActionButton btn_show = (FloatingActionButton) findViewById(R.id.fab);
    btn_show.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View arg0) {
            if (login_guest) {
              Toast.makeText(
                      HomePage.this, "Signin/login to enable this feature", Toast.LENGTH_SHORT)
                  .show();
            } else {
              // Open popup window
              if (p != null) showPopup(HomePage.this, p);
            }
          }
        });
    BottomNavigationViewEx bnve = findViewById(R.id.bnve);
    bnve.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    drawerLayout = findViewById(R.id.drawer_layout);
    // Enable Navigation bar
    NavigationView navigationView = findViewById(R.id.nav_view);
    View headView = navigationView.getHeaderView(0);
    TextView userNameView = headView.findViewById(R.id.username_text_input);
    userNameView.setText(username);

    navigationView.setNavigationItemSelectedListener(
        new NavigationView.OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(MenuItem menuItem) {
            // set item as selected to persist highlight
            if (login_guest) menuItem.setChecked(false);
            else menuItem.setChecked(false);
            if (menuItem.getItemId() == R.id.history) {
              if (login_guest) {
                menuItem.setVisible(false);
              } else {
                Toast.makeText(
                        getApplicationContext(), "History has been choosed", Toast.LENGTH_LONG)
                    .show();
              }
            }
            if (menuItem.getItemId() == R.id.save) {
              if (login_guest) {
                menuItem.setVisible(false);
              } else {
                Toast.makeText(getApplicationContext(), "Save has been choosed", Toast.LENGTH_LONG)
                    .show();
              }
            }
            if (menuItem.getItemId() == R.id.myProfile) {
              if (login_guest) {
                menuItem.setVisible(false);
              } else {
                startActivity(new Intent(getApplicationContext(), Profile.class));
              }
            }
            if (menuItem.getItemId() == R.id.setting) {
              if (login_guest) {
                menuItem.setVisible(false);
              } else {

                startActivity(new Intent(getApplicationContext(), Settings.class));
              }
            }
            if (menuItem.getItemId() == R.id.logout) {
              if (login_guest) {
                menuItem.setVisible(false);
                return true;
              } else {
                getResponse(
                    Request.Method.POST,
                    Routes.information,
                    null,
                    new VolleyCallback() {
                      @Override
                      public void onSuccessResponse(String response) {
                        try {
                          // converting response to json object
                          JSONObject obj = new JSONObject(response);
                          SharedPrefmanager.getInstance(HomePage.this).logout();
                          Toast.makeText(HomePage.this, "Logout Successful", Toast.LENGTH_SHORT)
                              .show();
                          Intent i = new Intent(HomePage.this, MainActivity.class);
                          startActivity(i);
                        } catch (JSONException e) {
                          e.printStackTrace();
                        }
                      }
                    });
              }
            }
            // close drawer when item is tapped
            drawerLayout.closeDrawers();

            // Add code here to update the UI based on the item selected
            // For example, swap UI fragments here
            return true;
          }
        });

    Fragment fragment = new HomeFragment();
    loadFragment(fragment);
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

  private BottomNavigationViewEx.OnNavigationItemSelectedListener
      mOnNavigationItemSelectedListener =
          new BottomNavigationViewEx.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
              Fragment fragment;

              switch (item.getItemId()) {
                case R.id.i_home:
                  fragment = new HomeFragment();
                  loadFragment(fragment);
                  return true;
                case R.id.i_apexLists:
                  fragment = new ListOfCommunityFragment();
                  loadFragment(fragment);
                  return true;
                case R.id.i_notifications:
                  if (login_guest) {
                    Toast.makeText(
                            HomePage.this,
                            "Signin/login to enable this feature",
                            Toast.LENGTH_SHORT)
                        .show();
                    return true;
                  }
                  fragment = new NotificationFragment();
                  loadFragment(fragment);
                  return true;
                case R.id.i_inbox:
                  if (login_guest) {
                    Toast.makeText(
                            HomePage.this,
                            "Signin/login to enable this feature",
                            Toast.LENGTH_SHORT)
                        .show();
                    return true;
                  }
                  fragment = new MessageFragment();
                  loadFragment(fragment);
                  return true;
                case R.id.i_empty:
                  Toast.makeText(getApplicationContext(), "empty is selected", Toast.LENGTH_SHORT)
                      .show();
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

    // Initialize the Point with x, and y positions
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
    double y = 0.28 * size.y;
    int popupHeight = (int) y;

    // Inflate the popup_layout.xml
    RelativeLayout viewGroup = (RelativeLayout) context.findViewById(R.id.popup);
    LayoutInflater layoutInflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View layout = layoutInflater.inflate(R.layout.popup_layout, viewGroup);

    // Creating the PopupWindow
    final PopupWindow popup = new PopupWindow(context);
    popup.setContentView(layout);
    popup.setWidth(popupWidth);
    popup.setHeight(popupHeight);
    popup.setFocusable(true);

    // Some offset to align the popup a bit to the right, and a bit down, relative to button's
    // position.
    int OFFSET_X = 30;
    int OFFSET_Y = 30;

    // Clear the default translucent background
    popup.setBackgroundDrawable(new BitmapDrawable());

    // Displaying the popup at the specified location, + offsets.
    popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);

    // Getting a reference to Close button, and close the popup when clicked.
    Button close = (Button) layout.findViewById(R.id.cancel_button);
    close.setOnClickListener(
        new View.OnClickListener() {

          @Override
          public void onClick(View v) {
            popup.dismiss();
          }
        });
  }

  public void textClick(View view) {
    Intent intent = new Intent(HomePage.this, CreatePost.class);
    intent.putExtra("type", "text");
    startActivity(intent);
  }

  public void imgClick(View view) {
    Intent intent = new Intent(HomePage.this, CreatePost.class);
    intent.putExtra("type", "image");
    startActivity(intent);
  }

  public void linkClick(View view) {
    Intent intent = new Intent(HomePage.this, CreatePost.class);
    intent.putExtra("type", "link");
    startActivity(intent);
  }

  private void loadFragment(Fragment fragment) {
    // load fragment
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.content_frame, fragment);
    transaction.addToBackStack(null);
    transaction.commit();
  }

  /** overrride the back button to do nothing instead of returning to login activity */
  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
      Log.d("CDA", "onKeyDown Called");
      onBackPressed();
      return true;
    }
    return super.onKeyDown(keyCode, event);
  }

  @Override
  public void onBackPressed() {
    Log.d("CDA", "onBackPressed Called");
    // do nothing
  }

  public void getResponse(
      int method, String url, JSONObject jsonValue, final VolleyCallback callback) {
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
            params.put("token", token);
            return params;
          }
        };
    VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
  }
}
