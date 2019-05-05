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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static java.lang.StrictMath.abs;


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
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        ActionBar actionbar =activity.getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.list_white);//------> make it change with profile picture
        setHasOptionsMenu(true);
        User user = SharedPrefmanager.getInstance(getContext()).getUser();
        final String token=user.getToken();
        messagesArrayList=new ArrayList();
        listView=view.findViewById(R.id.InboxList);
        if(Routes.active_mock){
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yy-mm-dd hh:mm:ss", Locale.GERMANY);
            Messages messages=new Messages();
            messages.setContent("Ramadan Karim ,we kol sana we anto tayebin");
            messages.setId("t4_1");
            messages.setRead(0);
            messages.setSender("Mazen");
            messages.setFormat("2019-12-26");
            messages.setSubject("Test message");
            messagesArrayList.add(messages);
            SimpleDateFormat simpleDateFormat2=new SimpleDateFormat("yy-mm-dd hh:mm:ss", Locale.UK);
            Messages messages2=new Messages();
            messages2.setContent("No cure for fools");
            messages2.setId("t4_2");
            messages2.setRead(1);
            messages2.setSender("omar");
            messages2.setFormat("2017-18-26");
            messages2.setSubject("League of legends");
            messagesArrayList.add(messages2);
            SimpleDateFormat simpleDateFormat3=new SimpleDateFormat("yy-mm-dd hh:mm:ss", Locale.US);
            Messages messages3=new Messages();
            messages3.setContent("Legends never die");
            messages3.setId("t4_3");
            messages3.setRead(2);
            messages3.setSender("Mostafa");
            messages3.setFormat("2019-1-2");
            messages3.setSubject("Apex");
            messagesArrayList.add(messages3);
            adapterForMessages=new CustomAdapterForMessages((Activity) getContext(),messagesArrayList);
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
                    JSONArray sentMessages = obj.getJSONArray("sent");
                    JSONObject recived=obj.optJSONObject("received");
                    JSONArray recivedMessage = recived.getJSONArray("all");
                    Toast.makeText(
                            activity.getApplicationContext(),
                            "All message received",
                            Toast.LENGTH_SHORT)
                        .show();
                    for(int i=0;i<sentMessages.length();i++){
                        JSONObject current=sentMessages.getJSONObject(i);
                        Messages temp=new Messages();
                        temp.setSubject(current.getString("subject"));
                        temp.setRead(2);
                        temp.setContent(current.getString("content"));
                        temp.setId(current.getString("id"));
                        Date currentTime = Calendar.getInstance().getTime();
                        String currendate="20"+Integer.toString(currentTime.getYear()-100);
                        if(currentTime.getMonth()>9){
                            currendate+="-"+Integer.toString(currentTime.getMonth());
                        }
                        else{
                            currendate+="-"+"0"+Integer.toString(currentTime.getMonth()+1);
                        }
                        currendate+="-"+Integer.toString(currentTime.getDate())+" ";
                        currendate+=Integer.toString(currentTime.getHours());
                        currendate+=":"+Integer.toString(currentTime.getMinutes());
                        currendate+=":"+Integer.toString(currentTime.getSeconds());
                        String createdDate=current.getString("created_at");
                        if(createdDate!="null"){
                            // Custom date format
                            SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

                            //Calculate difference between current and created time
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
                            long diffDays=d2.getDate()-d1.getDate();
                            long diffWeeks=diffDays/7;
                            if(diffMinutes<=59){
                                temp.setFormat(Long.toString(diffMinutes)+" min ago");;
                            }
                            else if(diffHours<23){
                                temp.setFormat(Long.toString(diffHours)+" hr ago");
                            }
                            else if(diffDays<7){
                                temp.setFormat(Long.toString(diffDays)+" days ago");
                            }
                            else{
                                temp.setFormat(Long.toString(diffWeeks)+" weeks ago");
                            }
                        }else{
                            temp.setFormat("Not defined");
                        }
                        JSONObject sender=current.getJSONObject("sender");
                        temp.setSender(sender.getString("username"));
                        messagesArrayList.add(temp);
                    }
                    for(int i=0;i<recivedMessage.length();i++){
                        JSONObject current=recivedMessage.getJSONObject(i);
                        Messages temp=new Messages();//---------------------->check this
                        temp.setSubject(current.getString("subject"));
                        temp.setRead(current.getInt("read"));
                        temp.setContent(current.getString("content"));
                        temp.setId(current.getString("id"));
                        Date currentTime = Calendar.getInstance().getTime();
                        String currendate="20"+Integer.toString(currentTime.getYear()-100);
                        if(currentTime.getMonth()>9){
                            currendate+="-"+Integer.toString(currentTime.getMonth());
                        }
                        else{
                            currendate+="-"+"0"+Integer.toString(currentTime.getMonth()+1);
                        }
                        currendate+="-"+Integer.toString(currentTime.getDate())+" ";
                        currendate+=Integer.toString(currentTime.getHours());
                        currendate+=":"+Integer.toString(currentTime.getMinutes());
                        currendate+=":"+Integer.toString(currentTime.getSeconds());
                        String createdDate=current.getString("created_at");
                        if(createdDate!="null"){
                            // Custom date format
                            SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

                            //Calculate difference between current and created time
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
                            long diffDays=d2.getDate()-d1.getDate();
                            long diffWeeks=diffDays/7;
                            if(diffMinutes<=59){
                                temp.setFormat(Long.toString(diffMinutes)+" min ago");;
                            }
                            else if(diffHours<23){
                                temp.setFormat(Long.toString(diffHours)+" hr ago");
                            }
                            else if(diffDays<7){
                                temp.setFormat(Long.toString(diffDays)+" days ago");
                            }
                            else{
                                temp.setFormat(Long.toString(diffWeeks)+" weeks ago");
                            }
                        }else{
                            temp.setFormat("Not defined");
                        }
                        JSONObject sender=current.getJSONObject("sender");
                        temp.setSender(sender.getString("username"));
                        messagesArrayList.add(temp);
                    }
                    adapterForMessages=new CustomAdapterForMessages((Activity) getContext(),messagesArrayList);
                    listView.setAdapter(adapterForMessages);
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
          },token);
        }
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i=new Intent(activity.getApplicationContext(),ReadMessage.class);
                        i.putExtra("selectedMessage",messagesArrayList.get(position));
                        startActivity(i);
                    }
                });
        // COMPLETED : TODO ADD CLICK LISTNER
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
            final VolleyCallback callback,final  String token) {
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
                                            errorMessage = message+" Please login again";
                                        } else if (networkResponse.statusCode == 400) {
                                            errorMessage = message+ " Check your inputs";
                                        } else if (networkResponse.statusCode == 500) {
                                            errorMessage = message+" Something is getting wrong";
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
        VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
