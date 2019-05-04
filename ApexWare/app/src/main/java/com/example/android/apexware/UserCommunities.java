package com.example.android.apexware;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.android.apexware.Routes.active_mock;

/**
 * list of communities to post to including your subscribed apexcoms or all apexcoms to serch into
 *
 * @author mostafa
 */
public class UserCommunities extends AppCompatActivity {

  // public static final CustomAdapterForCommunities adapter;
  ListView listView;
  ArrayList<CommunityInfo> commlist = new ArrayList<>();
  CommAdapter adapter;

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

    User user = SharedPrefmanager.getInstance(getApplicationContext()).getUser();
    final String token = user.getToken();
    listView = (ListView) findViewById(R.id.listcommunitiesid);

    if (active_mock) {
      CommunityInfo comm1 =
          new CommunityInfo(
              "apex 1",
              "apex 1",
              200,
              "https://i.imgur.com/6z13lku.jpg",
              "https://i.imgur.com/Z6s6Who.jpg",
              "co1");
      CommunityInfo comm2 =
          new CommunityInfo(
              "apex 2",
              "apex 2",
              5000,
              "https://i.imgur.com/6z13lku.jpg",
              "https://i.imgur.com/Z6s6Who.jpg",
              "co2");
      CommunityInfo comm3 =
          new CommunityInfo(
              "apex 3",
              "apex 3",
              6000,
              "https://i.imgur.com/6z13lku.jpg",
              "https://i.imgur.com/Z6s6Who.jpg",
              "co3");
      commlist.add(comm1);
      commlist.add(comm2);
      commlist.add(comm3);
      adapter = new CommAdapter(this, commlist);
      listView.setAdapter(adapter);
    } else {
      getResponse(
          Request.Method.POST,
          Routes.getApexcom,
          null,
          new VolleyCallback() {
            @Override
            public void onSuccessResponse(String response) {
              try {
                // converting response to json object
                JSONObject obj = new JSONObject(response);
                JSONArray jsonArray = obj.getJSONArray("apexComs");
                // if no error in response
                if (response != null) {
                  for (int i = 0; i < jsonArray.length(); i++) {
                    CommunityInfo tenp = new CommunityInfo();
                    JSONObject current = jsonArray.getJSONObject(i);
                    tenp.setBackground("https://imgur.com/gallery/cniisLx");
                    tenp.setCommunityLogo("https://imgur.com/gallery/cniisLx");
                    tenp.setCommunityName(current.getString("name"));
                    tenp.setComID(current.getString("id"));
                    commlist.add(tenp);
                  }
                  Toast.makeText(getApplicationContext(), "All apexcom get", Toast.LENGTH_SHORT)
                      .show();
                  adapter = new CommAdapter((Activity) getApplicationContext(), commlist);
                  listView.setAdapter(adapter);
                } else {
                  Toast.makeText(
                          getApplicationContext(), "Unsuccessful operation", Toast.LENGTH_SHORT)
                      .show();
                }

              } catch (JSONException e) {
                e.printStackTrace();
              }
            }
          },
          token);
    }
    listView.setOnItemClickListener(
        new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Object current = parent.getItemAtPosition(position);
            CommunityInfo c1 = (CommunityInfo) current;
            CreatePost.communityID = c1.getComID();
            CreatePost.communityName = c1.getCommunityName();
            startActivity(new Intent(getApplicationContext(),CreatePost.class));
          }
        });
  }

  /** return to post and discard choosing the community */
  public void returnToPost(View view) {
    finish();
  }

  public void getResponse(
      int method,
      String url,
      JSONObject jsonValue,
      final VolleyCallback callback,
      final String token) {
    StringRequest stringRequest =
        new StringRequest(
            Request.Method.POST,
            url,
            new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                callback.onSuccessResponse(response);
              }
            },
            new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                int x = 0;
                Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
                error.getMessage();
              }
            }) {
          @Override
          protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<>();
            params.put("token", token);
            return params;
          }
        };
    VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
  }
}
