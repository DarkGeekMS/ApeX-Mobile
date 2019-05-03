package com.example.android.apexware;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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

import static java.lang.StrictMath.abs;

public class SerachList extends AppCompatActivity {
    User user = SharedPrefmanager.getInstance(this).getUser();
    final String token=user.getToken();
    private ArrayAdapter adapter;
    private ArrayList<CommunityInfo> communityInfoArrayList=new ArrayList<>();
    private ArrayList<User> userArrayList=new ArrayList<>();
    private ArrayList<Post> postArrayList=new ArrayList<>();
    ListView list;
    ArrayList<String> names;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serach_list);
        Window window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.myblue));

        Toolbar toolbar =findViewById(R.id.searchToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        list = (ListView) findViewById(R.id.theList);
        EditText theFilter = (EditText) findViewById(R.id.searchFilter);
        names = new ArrayList<>();
        theFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String resultToSearch= ""+ charSequence.toString();

                if(charSequence.length()>2){
                    getResponse(Request.Method.POST,
                            Routes.searchForApexcom,
                            null,
                            new VolleyCallback(){
                                @Override
                                public void onSuccessResponse(String response) {
                                    try {

                                        // converting response to json object
                                        JSONObject obj = new JSONObject(response);
                                        JSONArray apexComsArray=obj.getJSONArray("apexComs");
                                        JSONArray usersArray=obj.getJSONArray("users");
                                        JSONArray posts=obj.getJSONArray("posts");
                                        for(int i=0;i<apexComsArray.length();i++){
                                            JSONObject current=apexComsArray.getJSONObject(i);
                                            CommunityInfo temp=new CommunityInfo();
                                            temp.setComID(current.getString("id"));
                                            temp.setnumOfFollowers(current.getInt("subscribers_count"));
                                            temp.setCommunityName(current.getString("name"));
                                            temp.setCommunityLogo("avatar");
                                            temp.setBackground("https://imgur.com/gallery/N8QHZI6");

                                            names.add(temp.getCommunityName());
                                            communityInfoArrayList.add(temp);
                                        }
                                        for(int i=0;i<usersArray.length();i++){
                                            JSONObject current=usersArray.getJSONObject(i);
                                            User temp=new User();
                                            temp.setId(current.getString("id"));
                                            temp.setUsername("username");
                                            //TODO add profile picture

                                            names.add(temp.getUsername());
                                            userArrayList.add(temp);
                                        }
                                        for(int i=0;i<posts.length();i++){
                                            JSONObject current=posts.getJSONObject(i);
                                            Post temp=new Post();
                                            temp.setPostId(current.getString("id"));
                                            temp.setPostOwner(current.getString("post_writer_username"));
                                            temp.setPostTitle(current.getString("title"));
                                            temp.setApexcomName("apex_com_name");
                                            temp.setVotesCount(current.getInt("votes"));
                                            temp.setApexcomLogo("https://i.imgur.com/S7USWRb.jpg");
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
                                                temp.setPostCreateDate(Long.toString(diffMinutes)+" min ago");
                                            }
                                            else if(diffHours<23){
                                                temp.setPostCreateDate(Long.toString(diffHours)+" hr ago");
                                            }
                                            else if(diffDays<7){
                                                temp.setPostCreateDate(Long.toString(diffDays)+" days ago");
                                            }
                                            else{
                                                temp.setPostCreateDate(Long.toString(diffWeeks)+" weeks ago");
                                            }

                                            String type=current.getString("content");
                                            String type1=current.getString("img");
                                            String type2=current.getString("videolink");
                                            if(type!=null){//text post
                                                temp.setTextPostcontent(current.getString("type"));
                                                temp.setPostType(0);
                                            }
                                            else if(type1!=null){//image post
                                                temp.setTextPostcontent(current.getString("type1"));
                                                temp.setPostType(1);
                                            }
                                            else if(type2!=null){
                                                temp.setTextPostcontent(current.getString("type2"));
                                                temp.setPostType(2);
                                            }

                                            names.add(temp.getPostTitle());
                                            postArrayList.add(temp);
                                        }
                                        // if no error in response
                                        if (response != null) {

                                            adapter = new ArrayAdapter(SerachList.this, R.layout.search_list_item, names);
                                            list.setAdapter(adapter);
                                            (SerachList.this).adapter.getFilter().filter(resultToSearch);
                                        } else {
                                            int x=0;
                                            Toast.makeText(
                                                    getApplicationContext(), "Unsuccessful operation", Toast.LENGTH_SHORT)
                                                    .show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },""+charSequence.toString(),
                            token);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    public void getResponse(
            int method,
            String url,
            JSONObject jsonValue,
            final VolleyCallback callback,
            final String searchResult,
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
                                int x=0;
                                Toast.makeText(
                                        getApplicationContext(), "Server Error", Toast.LENGTH_SHORT)
                                        .show();
                                error.getMessage();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("query", searchResult);
                        params.put("token",token);
                        return params;
                    }
                };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
