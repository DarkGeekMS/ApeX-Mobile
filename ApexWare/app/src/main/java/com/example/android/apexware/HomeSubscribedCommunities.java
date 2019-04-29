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

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class HomeSubscribedCommunities extends Fragment {
    ListView list;
    CustomAdapterForHomePage adapter;
    List<Post> hiddenPotsList = new ArrayList<>();
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
          // create list view object to handele given array adapter
          list = (ListView)view.findViewById(R.id.ListOFPosts);
        // create array adapter list for the view
        Post testpost = new Post();
        testpost.setPostId("1");
        testpost.setPostType(0);
        testpost.setPostOwner("Mazen");
        testpost.setPostCreateDate(19);
        testpost.setApexcomLogo("https://i.imgur.com/S7USWRb.jpg");
        testpost.setApexcomName("AndroidTeam");
        testpost.setPostTitle("Test this post");
        testpost.setTextPostcontent(
            "Hello its plaesure to meet you here please fell as hoemand leave wuefhiwoeufhwieufhweiufhief  fhewiuf eiufh ief ufhieuhf iuehf uihefiu h feufh iuehf  fiue  eiufhei h efiueh iuh feiufh eiuhf uehf iuhiufheiufheiufh  ehfiuefheiufhiuhefiufehiue  efihoeoIUFEHWEIFUHIF IUHIU HIU");
        postArrayList.add(testpost);
        Post testpost1 = new Post();
        testpost1.setPostType(1);
        testpost1.setPostOwner("Mazen");
        testpost1.setPostCreateDate(19);
        testpost1.setApexcomLogo("https://i.imgur.com/7cWUnve.jpg");
        testpost1.setApexcomName("AndroidTeam");
        testpost1.setPostTitle("Test this post");
        testpost1.setImageURL("https://i.imgur.com/7cWUnve.jpg");
        postArrayList.add(testpost1);
        Post testpost2 = new Post();
        testpost2.setPostType(2);
        testpost2.setPostOwner("Mazen");
        testpost2.setPostCreateDate(19);
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
        // to do create instances of posts

        adapter = new CustomAdapterForHomePage((Activity) getContext(), postArrayList);
        list.setAdapter(adapter);
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
                startActivity(intent);
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

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("id");
                hiddenPotsList.add(postArrayList.get(Integer.parseInt(result)));
                postArrayList.remove(Integer.parseInt(result));
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult
*/
}
