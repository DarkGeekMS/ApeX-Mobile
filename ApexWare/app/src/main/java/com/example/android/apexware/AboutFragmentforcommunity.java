package com.example.android.apexware;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/** This class to handle all information about community */
public class AboutFragmentforcommunity extends Fragment {
  View view;
  String commid;
  ListView moderats;
  ExpandableListView Rules;
  /** Default constructor */
  public AboutFragmentforcommunity() {}

  @SuppressLint("ValidFragment")
  public AboutFragmentforcommunity(String id) {
    commid = id;
  }
  /**
   * Built in function to handle call of fragment
   *
   * @param container
   * @param inflater
   * @param savedInstanceState
   */
  @SuppressLint("ClickableViewAccessibility")
  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.aboutfragmentforcommunity, container, false);
    moderats = view.findViewById(R.id.moderators);
    Rules = view.findViewById(R.id.Ruleslist);
    View rulestextview = getLayoutInflater().inflate(R.layout.rulestextview, null);
    Rules.addHeaderView(rulestextview);
    View moderatorstextview = getLayoutInflater().inflate(R.layout.modstextview, null);
    ArrayList<String> RulesHeader = new ArrayList<String>();
    HashMap<String, List<String>> RuleDetails = new HashMap<String, List<String>>();
    String[] modearators = {
      "ali", "yasser", "yomna", "shaddad", "pewds", "ali", "yasser", "yomna", "shaddad", "pewds",
      "ali", "yasser", "yomna", "shaddad", "pewds", "ali", "yasser", "yomna", "shaddad", "pewds"
    };
    ArrayAdapter<String> mods =
        new ArrayAdapter<String>(
            this.getActivity(), android.R.layout.simple_list_item_1, modearators);
    moderats.setAdapter(mods);
    RulesHeader.add("rule 1");
    RulesHeader.add("rule 2");
    RulesHeader.add("rule 3");
    RulesHeader.add("rule 4");
    RulesHeader.add("rule 5");
    RulesHeader.add("rule 6");
    RulesHeader.add("rule 7");
    List<String> rule1details = new ArrayList<String>();
    rule1details.add("bllllllllllllllllllllllla");
    RuleDetails.put(RulesHeader.get(0), rule1details);
    RuleDetails.put(RulesHeader.get(1), rule1details);
    RuleDetails.put(RulesHeader.get(2), rule1details);
    RuleDetails.put(RulesHeader.get(3), rule1details);
    RuleDetails.put(RulesHeader.get(4), rule1details);
    RuleDetails.put(RulesHeader.get(5), rule1details);
    AboutExListAdapter rulesAdapter =
        new AboutExListAdapter(this.getActivity(), RulesHeader, RuleDetails);
    Rules.setAdapter(rulesAdapter);
    moderats.addHeaderView(moderatorstextview);
    // this ontouch listener makes the listview scrollable but   NOT THE WHOLE VIEW
    moderats.setOnTouchListener(
        new ListView.OnTouchListener() {
          @Override
          public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            switch (action) {
              case MotionEvent.ACTION_DOWN:
                v.getParent().requestDisallowInterceptTouchEvent(true);
                break;
              case MotionEvent.ACTION_UP:
                v.getParent().requestDisallowInterceptTouchEvent(false);
                break;
            }
            v.onTouchEvent(event);
            return true;
          }
        });
    return view;
  }
  /**
   * This function hold all server request data using volley android library
   *
   * @param commID :send Community ID
   * @param url server url route which we talk to
   * @param method post type
   * @param callback inter face of class so that we dont have to wait for response
   */
  public void getabout(
      int method,
      String url,
      JSONObject jsonValue,
      final VolleyCallback callback,
      final String commID) {
    User user =
        SharedPrefmanager.getInstance(AboutFragmentforcommunity.this.getActivity()).getUser();
    final String token = user.getToken();
    StringRequest stringRequest =
        new StringRequest(
            method,
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
                Toast.makeText(
                        AboutFragmentforcommunity.this.getActivity(),
                        "Server Error",
                        Toast.LENGTH_SHORT)
                    .show();
                error.getMessage();
              }
            }) {
          @Override
          protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<>();
            params.put("apexComID", commID);
            params.put("token", token);
            return params;
          }
        };
    VolleySingleton.getInstance(AboutFragmentforcommunity.this.getActivity())
        .addToRequestQueue(stringRequest);
  }
}
