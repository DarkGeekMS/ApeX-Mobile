package com.example.android.apexware;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.lang.StrictMath.abs;

public class PostsFragment extends Fragment {
    View view;
    ArrayList<Post> postArrayList = new ArrayList();
    CustomAdapterForHomePage adapter;
    ListView posts;
String commid;
    public PostsFragment() {
    }
    @SuppressLint("ValidFragment")
    public PostsFragment(String id) {
        commid=id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.postsfragmentforcommunity,container,false);
      //  ArrayList<Post> postArrayList=new ArrayList<Post>();
        final ListView posts=view.findViewById(R.id.PostsFragment);

       if(Routes.active_mock) {  Post p1=new Post();
        Post p2=new Post();
        Post p3=new Post();
        Post p4=new Post();
        Post p5=new Post();
        p1.setPostId("t3_1");
        p1.setPostType(0);
        p1.setPostOwner("omar");
        p1.setPostCreateDate("19");
        p1.setApexcomLogo("https://i.imgur.com/S7USWRb.jpg");
        p1.setApexcomName("AndroidTeam");
        p1.setPostTitle("Test this post");
        p1.setTextPostcontent(
                "Hello its plaesure to meet you here please fell as hoemand leave wuefhiwoeufhwieufhweiufhief  fhewiuf eiufh ief ufhieuhf iuehf uihefiu h feufh iuehf  fiue  eiufhei h efiueh iuh feiufh eiuhf uehf iuhiufheiufheiufh  ehfiuefheiufhiuhefiufehiue  efihoeoIUFEHWEIFUHIF IUHIU HIU");

        p2.setPostId("t3_1");
        p2.setPostType(1);
        p2.setPostOwner("omar");
        p2.setPostCreateDate("19");
        p2.setApexcomLogo("https://i.imgur.com/S7USWRb.jpg");
        p2.setApexcomName("AndroidTeam");
        p2.setPostTitle("Test this post");
        p2.setImageURL("https://i.imgur.com/sgVvE4n.png");

        p3.setPostId("t3_1");
        p3.setPostType(3);
        p3.setPostOwner("omar");
        p3.setPostCreateDate("19");
        p3.setApexcomLogo("https://i.imgur.com/S7USWRb.jpg");
        p3.setApexcomName("AndroidTeam");
        p3.setPostTitle("Test this post");
        p3.setVideoURL("https://www.youtube.com/watch?v=C6hq5cziIHc");

        p4.setPostId("t3_1");
        p4.setPostType(2);
        p4.setPostOwner("omar");
        p4.setPostCreateDate("19");
        p4.setApexcomLogo("https://i.imgur.com/S7USWRb.jpg");
        p4.setApexcomName("AndroidTeam");
        p4.setPostTitle("Test this post");
        p4.setImageURL("https://i.imgur.com/CchNQmg.jpg");

        p5.setPostId("t3_1");
        p5.setPostType(2);
        p5.setPostOwner("omar");
        p5.setPostCreateDate("19");
        p5.setApexcomLogo("https://i.imgur.com/S7USWRb.jpg");
        p5.setApexcomName("AndroidTeam");
        p5.setPostTitle("Test this post");
        p5.setImageURL("https://i.imgur.com/7JeZUcC.jpg");
        postArrayList.add(p1);
        postArrayList.add(p2);
        postArrayList.add(p3);
        postArrayList.add(p4);
        postArrayList.add(p5);
            CustomAdapterForHomePage adapter=new CustomAdapterForHomePage(this.getActivity(),postArrayList);
            posts.setAdapter(adapter);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                posts.setNestedScrollingEnabled(true);}
        }else{
           {
               getposts(Request.Method.GET,
                       Routes.sortPosts,
                       null,
                       new VolleyCallback(){
                           @Override
                           public void onSuccessResponse(String response) {
                               try {
                                   // converting response to json object
                                   JSONObject obj = new JSONObject(response);
                                   JSONArray jsonArray=obj.getJSONArray("posts");
                                   // if no error in response
                                   if (response != null) {
                                       for(int i=0;i<jsonArray.length();i++){
                                           JSONObject current=jsonArray.getJSONObject(i);
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
                                           String type1="http://35.232.3.8"+current.getString("img");
                                           String type2=current.getString("videolink");
                                           if(type!="null"){//text post
                                               temp.setTextPostcontent(type);
                                               temp.setPostType(0);
                                           }
                                           else if(type1!="null"){//image post
                                               temp.setImageURL(type1);
                                               temp.setPostType(1);
                                           }
                                           else if(type2!="null"){
                                               temp.setVideoURL(type2);
                                               temp.setPostType(2);
                                           }
                                           postArrayList.add(temp);
                                       }
                                       adapter = new CustomAdapterForHomePage((Activity) getContext(), postArrayList);
                                       posts.setAdapter(adapter);
                                       Toast.makeText(getContext(),"Home done retrived",Toast.LENGTH_SHORT).show();
                                   } else {
                                       int x=0;
                                       Toast.makeText(PostsFragment.this.getContext(), "Unsuccessful operation", Toast.LENGTH_SHORT)
                                               .show();
                                   }

                               } catch (JSONException e) {
                                   e.printStackTrace();
                               }
                           }
                       },commid);
           }


        /*CustomAdapterForHomePage adapter=new CustomAdapterForHomePage(this.getActivity(),postArrayList);
        ListView posts=view.findViewById(R.id.PostsFragment);
        posts.setAdapter(adapter);*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            posts.setNestedScrollingEnabled(true);
       }
        posts.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent( PostsFragment.this.getContext(),postsandcomments.class);
                        Object current = parent.getItemAtPosition(position);
                        Post p1 = (Post) current;
                        Gson gson = new Gson();
                        String postAsString = gson.toJson(p1);
                        intent.putExtra("postToDisplay", postAsString); // sending the post to next activity
                        intent.putExtra("itemPos",position);
                        startActivityForResult(intent,10);
                    }
                });
        return view;
    }


    public void getposts(
            int method,
            String url,
            JSONObject jsonValue,
            final VolleyCallback callback, final String commID) {
        User user = SharedPrefmanager.getInstance(PostsFragment.this.getActivity()).getUser();
        final String token=user.getToken();
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
                                int x=0;
                                Toast.makeText(
                                        PostsFragment.this.getActivity(), "Server Error", Toast.LENGTH_SHORT)
                                        .show();
                                error.getMessage();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("apexComID",commID);
                        return params;
                    }
                };
        VolleySingleton.getInstance(PostsFragment.this.getActivity()).addToRequestQueue(stringRequest);
    }
}
