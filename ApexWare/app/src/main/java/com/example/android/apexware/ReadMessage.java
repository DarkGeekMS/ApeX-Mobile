package com.example.android.apexware;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonArray;
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
import java.util.Map;

import javax.security.auth.Subject;

import static java.lang.StrictMath.abs;

public class ReadMessage extends AppCompatActivity {
    String messageSubject;
    String messageId;
    ArrayList<Replays> replaysArrayList;
    CustomAdapterForReplayMessages adapterForReplays;
    ListView listView;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_message);

        Window window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.myblue));

        Toolbar toolbar =findViewById(R.id.ReadMessagesToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setTitle("Read message");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        replaysArrayList=new ArrayList();
        listView=findViewById(R.id.replayMessagesList);
        Intent i=getIntent();
        Messages selectedMessage=i.getParcelableExtra("selectedMessage");
        getResponseONMessageClick(selectedMessage.getId(),
                Routes.readMessage,
                null,
                new VolleyCallback(){
                    @Override
                    public void onSuccessResponse(String response) {
                        try {
                            // converting response to json object
                            JSONObject obj = new JSONObject(response);
                            // if no error in response
                            if (response != null) {
                                JSONObject jsonMessageSubject=obj.getJSONObject("message");
                                messageSubject=jsonMessageSubject.getString("subject");
                                messageId=jsonMessageSubject.getString("id");
                                View headerView = getLayoutInflater().inflate(R.layout.replay_message_header, null);
                                TextView subject=headerView.findViewById(R.id.hirarcheyMessagesubject);
                                subject.setText(messageSubject);
                                listView.addHeaderView(headerView);

                                Replays originalmessage=new Replays();
                                originalmessage.setConten(jsonMessageSubject.getString("content"));
                                originalmessage.setSender(jsonMessageSubject.getString("sender"));
                                Date currenttime = Calendar.getInstance().getTime();
                                String currenDate="20"+Integer.toString(currenttime.getYear()-100);
                                if(currenttime.getMonth()>9){
                                    currenDate+="-"+Integer.toString(currenttime.getMonth());
                                }
                                else{
                                    currenDate+="-"+"0"+Integer.toString(currenttime.getMonth()+1);
                                }
                                currenDate+="-"+Integer.toString(currenttime.getDate())+" ";
                                currenDate+=Integer.toString(currenttime.getHours());
                                currenDate+=":"+Integer.toString(currenttime.getMinutes());
                                currenDate+=":"+Integer.toString(currenttime.getSeconds());
                                String createdDateo=jsonMessageSubject.getString("created_at");
                                if(createdDateo!="null"){
                                    // Custom date format
                                    SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

                                    //Calculate difference between current and created time
                                    Date d1 = null;
                                    Date d2 = null;
                                    try {
                                        d2 = format.parse(currenDate);
                                        d1 = format.parse(createdDateo);
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
                                        originalmessage.setAgoTime(Long.toString(diffMinutes)+" min ago");;
                                    }
                                    else if(diffHours<23){
                                        originalmessage.setAgoTime(Long.toString(diffHours)+" hr ago");
                                    }
                                    else if(diffDays<7){
                                        originalmessage.setAgoTime(Long.toString(diffDays)+" days ago");
                                    }
                                    else{
                                        originalmessage.setAgoTime(Long.toString(diffWeeks)+" weeks ago");
                                    }
                                }else{
                                    originalmessage.setAgoTime("Not defined");
                                }
                                replaysArrayList.add(originalmessage);
                                JSONArray getreplays=obj.getJSONArray("replies");
                                for(int i=0;i<getreplays.length();i++){
                                    JSONObject current=getreplays.getJSONObject(i);
                                    Replays temp=new Replays();
                                    temp.setSender(current.getString("sender_name"));
                                    temp.setConten(current.getString("content"));
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
                                            temp.setAgoTime(Long.toString(diffMinutes)+" min ago");;
                                        }
                                        else if(diffHours<23){
                                            temp.setAgoTime(Long.toString(diffHours)+" hr ago");
                                        }
                                        else if(diffDays<7){
                                            temp.setAgoTime(Long.toString(diffDays)+" days ago");
                                        }
                                        else{
                                            temp.setAgoTime(Long.toString(diffWeeks)+" weeks ago");
                                        }
                                    }else{
                                        temp.setAgoTime("Not defined");
                                    }
                                    replaysArrayList.add(temp);
                                }
                                adapterForReplays=new CustomAdapterForReplayMessages(ReadMessage.this,replaysArrayList);
                                listView.setAdapter(adapterForReplays);
                            } else {
                                Toast.makeText(
                                        ReadMessage.this, "Can't parsing the jason file", Toast.LENGTH_SHORT)
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    public void getResponseONMessageClick(
            final String messagId,
            String url,
            JSONObject jsonValue,
            final VolleyCallback callback) {
        User user = SharedPrefmanager.getInstance(ReadMessage.this).getUser();
        final String token=user.getToken();
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
                                int x=0;
                                Toast.makeText(
                                        ReadMessage.this, "Server Error", Toast.LENGTH_SHORT)
                                        .show();
                                error.getMessage();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("ID",messagId);
                        params.put("token", token);
                        return params;
                    }
                };
        VolleySingleton.getInstance(ReadMessage.this).addToRequestQueue(stringRequest);
    }

    public void addRepaly(View view) {
        Intent i=new Intent(ReadMessage.this,WriteReplay.class);
        i.putExtra("messageID",messageId);
        //TODO what to send message or previous replay id
        startActivity(i);

    }
}
