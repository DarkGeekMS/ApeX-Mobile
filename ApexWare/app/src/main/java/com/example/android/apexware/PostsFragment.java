package com.example.android.apexware;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class PostsFragment extends Fragment {
    View view;
    ArrayList<Post> postArrayList = new ArrayList();

    public PostsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.postsfragmentforcommunity,container,false);
      //  ArrayList<Post> postArrayList=new ArrayList<Post>();
        Post p1=new Post();
        Post p2=new Post();
        Post p3=new Post();
        Post p4=new Post();
        Post p5=new Post();

        p1.setPostId("t3_1");
        p1.setPostType(0);
        p1.setPostOwner("omar");
        p1.setPostCreateDate(19);
        p1.setApexcomLogo("https://i.imgur.com/S7USWRb.jpg");
        p1.setApexcomName("AndroidTeam");
        p1.setPostTitle("Test this post");
        p1.setTextPostcontent(
                "Hello its plaesure to meet you here please fell as hoemand leave wuefhiwoeufhwieufhweiufhief  fhewiuf eiufh ief ufhieuhf iuehf uihefiu h feufh iuehf  fiue  eiufhei h efiueh iuh feiufh eiuhf uehf iuhiufheiufheiufh  ehfiuefheiufhiuhefiufehiue  efihoeoIUFEHWEIFUHIF IUHIU HIU");

        p2.setPostId("t3_1");
        p2.setPostType(1);
        p2.setPostOwner("omar");
        p2.setPostCreateDate(19);
        p2.setApexcomLogo("https://i.imgur.com/S7USWRb.jpg");
        p2.setApexcomName("AndroidTeam");
        p2.setPostTitle("Test this post");
        p2.setImageURL("https://i.imgur.com/sgVvE4n.png");

        p3.setPostId("t3_1");
        p3.setPostType(3);
        p3.setPostOwner("omar");
        p3.setPostCreateDate(19);
        p3.setApexcomLogo("https://i.imgur.com/S7USWRb.jpg");
        p3.setApexcomName("AndroidTeam");
        p3.setPostTitle("Test this post");
        p3.setVideoURL("https://www.youtube.com/watch?v=C6hq5cziIHc");

        p4.setPostId("t3_1");
        p4.setPostType(2);
        p4.setPostOwner("omar");
        p4.setPostCreateDate(19);
        p4.setApexcomLogo("https://i.imgur.com/S7USWRb.jpg");
        p4.setApexcomName("AndroidTeam");
        p4.setPostTitle("Test this post");
        p4.setImageURL("https://i.imgur.com/CchNQmg.jpg");

        p5.setPostId("t3_1");
        p5.setPostType(2);
        p5.setPostOwner("omar");
        p5.setPostCreateDate(19);
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
        ListView posts=view.findViewById(R.id.PostsFragment);
        posts.setAdapter(adapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            posts.setNestedScrollingEnabled(true);}
        return view;
    }
}
