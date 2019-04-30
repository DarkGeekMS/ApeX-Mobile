package com.example.android.apexware;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment {
    ArrayList<Messages> messagesArrayList;
    CustomAdapterForMessages adapterForMessages;
    ListView listView;
    public MessageFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final AppCompatActivity activity=(AppCompatActivity)getActivity();
        View view=inflater.inflate(R.layout.fragment_message, container, false);
        Toolbar toolbar = view.findViewById(R.id.MessagesToolbar);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("Messages");

        ActionBar actionbar =activity.getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.list_white);//------> make it change with profile picture
        setHasOptionsMenu(true);
        messagesArrayList=new ArrayList();
        if(!Routes.active_mock||true){
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yy-mm-dd hh:mm:ss", Locale.GERMANY);
            Messages messages=new Messages();
            messages.setContent("Ramadan Karim ,we kol sana we anto tayebin");
            messages.setId("t4_1");
            messages.setRead(false);
            messages.setSender("Mazen");
            messages.setFormat(simpleDateFormat);
            messages.setSubject("test message");
            messagesArrayList.add(messages);
            SimpleDateFormat simpleDateFormat2=new SimpleDateFormat("yy-mm-dd hh:mm:ss", Locale.UK);
            Messages messages2=new Messages();
            messages2.setContent("No cure for fools");
            messages2.setId("t4_2");
            messages2.setRead(true);
            messages2.setSender("omar");
            messages2.setFormat(simpleDateFormat2);
            messages2.setSubject("League of legends");
            messagesArrayList.add(messages2);
            SimpleDateFormat simpleDateFormat3=new SimpleDateFormat("yy-mm-dd hh:mm:ss", Locale.US);
            Messages messages3=new Messages();
            messages3.setContent("Legends never die");
            messages3.setId("t4_3");
            messages3.setRead(false);
            messages3.setSender("Mostafa");
            messages3.setFormat(simpleDateFormat);
            messages3.setSubject("Apex");
            messagesArrayList.add(messages3);
            adapterForMessages=new CustomAdapterForMessages((Activity) getContext(),messagesArrayList);
            listView=view.findViewById(R.id.InboxList);
            listView.setAdapter(adapterForMessages);
    } else {
      getResponse(
          Request.Method.POST,
          Routes.InboxMessages,
          null,
          new VolleyCallback() {
            @Override
            public void onSuccessResponse(String response) {
              try {
                // converting response to json object
                JSONObject obj = new JSONObject(response);

                // if no error in response
                if (response != null) {
                  // getting the result from the response
                  JSONArray jsonArray = obj.getJSONArray("all");
                    Toast.makeText(
                            activity.getApplicationContext(),
                            "All message received",
                            Toast.LENGTH_SHORT)
                        .show();
                    for(int i=0;i<jsonArray.length();i++){
                        Messages temp=new Messages();//----------------->check for message state
                    }
                } else {
                  Toast.makeText(
                          activity.getApplicationContext(),
                          "Failed to parsing jason Array",
                          Toast.LENGTH_SHORT)
                      .show();
                }

              } catch (JSONException e) {
                e.printStackTrace();
              }
            }
          });
        }
        // TO DO ADD CLICK LISTNER
        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.mesasges_option_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.WriteMessage:
                try{
                startActivity(new Intent(getActivity(),WriteMessage.class));
                return true;
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            case R.id.MarkAllAsRead:

                Toast.makeText(getContext(),"All message are checked",Toast.LENGTH_SHORT).show();
                return false;
            default:
                break;
        }

        return false;
    }
    public void getResponse(
            int method,
            String url,
            JSONObject jsonValue,
            final VolleyCallback callback) {
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
                                Toast.makeText(
                                        getActivity().getApplicationContext(), "Server Error", Toast.LENGTH_SHORT)
                                        .show();
                                error.getMessage();
                            }
                        });
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }
}
