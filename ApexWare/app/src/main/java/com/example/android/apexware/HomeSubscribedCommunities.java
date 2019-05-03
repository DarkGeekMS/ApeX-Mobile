package com.example.android.apexware;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
import java.util.List;
import java.util.Map;

import static java.lang.StrictMath.abs;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class HomeSubscribedCommunities extends Fragment {
    ListView list;
    CustomAdapterForHomePage adapter;
    ArrayList<Post> postArrayList = new ArrayList();


    public HomeSubscribedCommunities() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final AppCompatActivity activity=(AppCompatActivity)getActivity();
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home_subscriebed_communities, container, false);
        User user = SharedPrefmanager.getInstance(getContext()).getUser();
        final String token=user.getToken();
          // create list view object to handele given array adapter
          list = (ListView)view.findViewById(R.id.ListOFPosts);
          if(Routes.active_mock){
              // create array adapter list for the view
              Post testpost = new Post();
              testpost.setPostId("1");
              testpost.setPostType(0);
              testpost.setPostOwner("Mazen");
              testpost.setPostCreateDate("19");
              testpost.setApexcomLogo("https://i.imgur.com/S7USWRb.jpg");
              testpost.setApexcomName("AndroidTeam");
              testpost.setPostTitle("Test this post");
              testpost.setTextPostcontent(
                      "Hello its plaesure to meet you here please fell as hoemand leave wuefhiwoeufhwieufhweiufhief  fhewiuf eiufh ief ufhieuhf iuehf uihefiu h feufh iuehf  fiue  eiufhei h efiueh iuh feiufh eiuhf uehf iuhiufheiufheiufh  ehfiuefheiufhiuhefiufehiue  efihoeoIUFEHWEIFUHIF IUHIU HIU");
              postArrayList.add(testpost);
              Post testpost1 = new Post();
              testpost1.setPostType(1);
              testpost1.setPostOwner("Mazen");
              testpost1.setPostCreateDate("19");
              testpost1.setApexcomLogo("https://i.imgur.com/7cWUnve.jpg");
              testpost1.setApexcomName("AndroidTeam");
              testpost1.setPostTitle("Test this post");
              testpost1.setImageURL("https://i.imgur.com/7cWUnve.jpg");
              postArrayList.add(testpost1);
              Post testpost2 = new Post();
              testpost2.setPostType(2);
              testpost2.setPostOwner("Mazen");
              testpost2.setPostCreateDate("19");
              testpost2.setApexcomLogo("https://i.imgur.com/S7USWRb.jpg");
              testpost2.setApexcomName("AndroidTeam");
              testpost2.setPostTitle("Test this post");
              testpost2.setImageURL("https://i.imgur.com/7cWUnve.jpg");
              testpost2.setVideoURL("https://www.youtube.com/watch?v=C6hq5cziIHc");
              postArrayList.add(testpost2);
              postArrayList.add(testpost);
              postArrayList.add(testpost1);
              postArrayList.add(testpost2);
              postArrayList.add(testpost);
              adapter = new CustomAdapterForHomePage((Activity) getContext(), postArrayList);
              list.setAdapter(adapter);

          }else {
              getResponse(Request.Method.POST,
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
                                          postArrayList.add(temp);
                                      }
                                      adapter = new CustomAdapterForHomePage((Activity) getContext(), postArrayList);
                                      list.setAdapter(adapter);
                                      Toast.makeText(getContext(),"Home done retrived",Toast.LENGTH_SHORT).show();
                                  } else {
                                      int x=0;
                                      Toast.makeText(
                                              activity.getApplicationContext(), "Unsuccessful operation", Toast.LENGTH_SHORT)
                                              .show();
                                  }

                              } catch (JSONException e) {
                                  e.printStackTrace();
                              }
                          }
                      },token);

          }
        list.setOnItemClickListener(
            new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent( activity,postsandcomments.class);
                Object current = parent.getItemAtPosition(position);
                Post p1 = (Post) current;
                Gson gson = new Gson();
                String postAsString = gson.toJson(p1);
                intent.putExtra("postToDisplay", postAsString); // sending the post to next activity
                  intent.putExtra("itemPos",position);
                startActivityForResult(intent,10);
              }
            });
        list.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                loadNextDataFromApi(page);
                // or loadNextDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });
            return view;
    }
    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void loadNextDataFromApi(int offset) {
        Toast.makeText(getContext(),"No more Posts",Toast.LENGTH_SHORT).show();
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyDataSetChanged()`
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if(requestCode==10&&resultCode==RESULT_OK)
        {
            String id=data.getStringExtra("postpos");
            if(id!="300000")
            {
                for(int i = 0 ; i < postArrayList.size() ; i++){
                    if(id.equalsIgnoreCase(postArrayList.get(i).getPostId())){
                        postArrayList.remove(i);
                    }
                }
                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(),"post is hidden",Toast.LENGTH_SHORT).show();
            }
            else  Toast.makeText(getContext(),"what tf",Toast.LENGTH_SHORT).show();

        }
    }//onActivityResult

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
                                int x=0;
                                Toast.makeText(
                                        getActivity().getApplicationContext(), "Server Error", Toast.LENGTH_SHORT)
                                        .show();
                                error.getMessage();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("token", token);
                        params.put("subscribedApexCom","true");
                        return params;
                    }
                };
        VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
