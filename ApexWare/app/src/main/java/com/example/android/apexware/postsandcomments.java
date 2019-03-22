package com.example.android.apexware;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;

public class postsandcomments extends AppCompatActivity {
  CustomAdapterForComments adapter; // adapter for the data in the list
  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_postsndcomments);
    Window window = this.getWindow();

    // clear FLAG_TRANSLUCENT_STATUS flag:
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

    // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

    // finally change the color
    window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorGray));

    ExpandableListView commentsList = (ExpandableListView) findViewById(R.id.listofcomments);
    ArrayList<Comment> commentArrayList = new ArrayList();
    ArrayList<Comment> repliesArrayList = new ArrayList();
    HashMap<Comment, List<Comment>> listHashMap = new HashMap<>();

    /** * fake data */

    /** extracting info for the post */
    Gson gson = new Gson();
    String postAsString = getIntent().getStringExtra("postToDisplay");
    Post post1 = gson.fromJson(postAsString, Post.class);
    /*
    Post post1=new Post();
            post1.setPostId(getIntent().getIntExtra("id",0));
            post1.postType = 0;
            post1.postOwner = "Mazen";
            post1.postCreateDate = 19;
            post1.ApexcomLogo = "https://i.imgur.com/S7USWRb.jpg";
            post1.apexcomName = "AndroidTeam";
            post1.postTitle = "Test this pot";
            post1.textPostTitle="Hello its plaesure to meet you here please fell as hoemand leave wuefhiwoeufhwieufhweiufhief  fhewiuf eiufh ief ufhieuhf iuehf uihefiu h feufh iuehf  fiue  eiufhei h efiueh iuh feiufh eiuhf uehf iuhiufheiufheiufh  ehfiuefheiufhiuhefiufehiue  efihoeoIUFEHWEIFUHIF IUHIU HIU";
            post1.postId=1;

    */
    /*put the data on the views*/
    /*logo*/
   /* ImageView logo = findViewById(R.id.apexcomlogo);
    Picasso.get().load(post1.ApexcomLogo).resize(50, 50).into(logo);
    *//*apex community name*//*
    TextView comName = findViewById(R.id.apexcomName);
    comName.setText(post1.apexcomName);
    *//*creator name and date created*//*
    TextView postOwnerAndCreatedTime = (TextView) findViewById(R.id.apexcomOwnerNameAndTimeCreated);
    postOwnerAndCreatedTime.setText("posted by " + post1.postOwner + "." + post1.postCreateDate);
TextView Title=findViewById(R.id.PostTitle);
Title.setText(post1.postTitle);
    *//* handling which view will be shown *//*
    *//*setting 1 view to be shown*//*
    TextView textPost = (TextView) findViewById(R.id.TextPostBody);
    WebView videoLinkView = (WebView) findViewById(R.id.videoWebView);
    ImageView uploadedImageView = (ImageView) findViewById(R.id.imageUploadedView);
    textPost.setText(post1.getTextPostcontent());
    if (post1.getPostType() == 0) {
      textPost.setVisibility(View.VISIBLE);
      textPost.setText(post1.getTextPostcontent());
      videoLinkView.setVisibility(GONE);
      uploadedImageView.setVisibility(GONE);
    } else if (post1.getPostType() == 1) {
      textPost.setVisibility(GONE);
      videoLinkView.setVisibility(GONE);
      uploadedImageView.setVisibility(View.VISIBLE);
      Picasso.get().load(post1.getImageURL()).into(uploadedImageView);

    } else if (post1.getPostType() == 2) {
      textPost.setVisibility(GONE);
      uploadedImageView.setVisibility(GONE);
      videoLinkView.setVisibility(View.VISIBLE);
      setuoVideoSetting(videoLinkView, post1.videoURL);

    }
    *//**//*

    *//**//*

   if (post1.getPostType() == 0) {
      // set body of the post
      TextView postBody = findViewById(R.id.TextPostBody);
      postBody.setText(post1.getTextPostcontent() );
      postBody.getText();


    } else if (post1.getPostType() == 1) {
      // set image of the post
      ImageView uploadedImage = (ImageView) findViewById(R.id.imageUploadedView);
      Picasso.get().load(post1.getImageURL()).into(uploadedImage);
    } else if (post1.getPostType() == 2) {
      // set VideoLinks
      WebView viewVideoLinks = (WebView) findViewById(R.id.videoWebView);
      setuoVideoSetting(viewVideoLinks, post1.videoURL);
    }

    // set Title psot
    // TextView postTitle = (TextView) findViewById(R.id.PostTitle);
    // postTitle.setText(post1.postTitle);

    *//* *//*

   */ Comment comment1 = new Comment(1);
    comment1.Id = 1;
    comment1.commentCreateDate = 125;
    comment1.commentOwner = "Omar229";
    comment1.commentContent =
        "lknlscnsln jjcnsljncl slxiklm pscmscm skcskc scjosijcoskn ss;l,pokpsm79 s98cu9sjcosjnci cih9sjoksc sisisisisis ";
    commentArrayList.add(comment1);

    Comment comment2 = new Comment(1);
    comment2.Id = 2;
    comment2.commentCreateDate = 2132;
    comment2.commentOwner = "mostafa";
    comment2.commentContent =
        "lknlscnsln jjcnsljncl slxiklm pscmscm skcskc scjosijcoskn ss;l,pokpsm79 s98cu9sjcosjnci cih9sjoksc sisisisisis ";
    commentArrayList.add(comment2);

    Comment rply1 = new Comment(1);
    rply1.Id = 3;
    rply1.commentCreateDate = 12665;
    rply1.commentOwner = "bla bla";
    rply1.commentContent =
        "lknlscnsln jjcnsljncl slxiklm pscmscm skcskc scjosijcoskn ss;l,pokpsm79 s98cu9sjcosjnci cih9sjoksc sisisisisis ";
    repliesArrayList.add(rply1);

    Comment rply2 = new Comment(1);
    rply2.Id = 4 ;
    rply2.commentCreateDate = 12665;
    rply2.commentOwner = "bla bla bllllla";
    rply2.commentContent =
        "lknlscnsln jjcnsljncl slxiklm pscmscm skcskc scjosijcoskn ss;l,pokpsm79 s98cu9sjcosjnci cih9sjoksc sisisisisis ";
    repliesArrayList.add(rply2);
    listHashMap.put(commentArrayList.get(0), repliesArrayList);
    listHashMap.put(commentArrayList.get(1), repliesArrayList);
    adapter = new CustomAdapterForComments(this, repliesArrayList, listHashMap, commentArrayList);
    commentsList.setAdapter(adapter);
    View po=getLayoutInflater().inflate(R.layout.homepgaelistview,null);
    commentsList.addHeaderView(po);


    post1.setPostId(getIntent().getIntExtra("id",0));

    /*put the data on the views*/
    /*logo*/
   ImageView logo = findViewById(R.id.apexcomlogo);
    Picasso.get().load(post1.ApexcomLogo).resize(50, 50).into(logo);
    //*apex community name*//*
    TextView comName = findViewById(R.id.apexcomName);
    comName.setText(post1.apexcomName);
    //*creator name and date created*//*
    TextView postOwnerAndCreatedTime = (TextView) findViewById(R.id.apexcomOwnerNameAndTimeCreated);
    postOwnerAndCreatedTime.setText("posted by " + post1.postOwner + "." + post1.postCreateDate);
TextView Title=findViewById(R.id.PostTitle);
Title.setText(post1.postTitle);
    //* handling which view will be shown *//*
     //*setting 1 view to be shown*//*
    TextView textPost = (TextView) findViewById(R.id.TextPostBody);
    WebView videoLinkView = (WebView) findViewById(R.id.videoWebView);
    ImageView uploadedImageView = (ImageView) findViewById(R.id.imageUploadedView);
    textPost.setText(post1.getTextPostcontent());
    if (post1.getPostType() == 0) {
      textPost.setVisibility(View.VISIBLE);
      textPost.setText(post1.getTextPostcontent());
      videoLinkView.setVisibility(GONE);
      uploadedImageView.setVisibility(GONE);
    } else if (post1.getPostType() == 1) {
      textPost.setVisibility(GONE);
      videoLinkView.setVisibility(GONE);
      uploadedImageView.setVisibility(View.VISIBLE);
      Picasso.get().load(post1.getImageURL()).into(uploadedImageView);

    } else if (post1.getPostType() == 2) {
      textPost.setVisibility(GONE);
      uploadedImageView.setVisibility(GONE);
      videoLinkView.setVisibility(View.VISIBLE);
      setuoVideoSetting(videoLinkView, post1.videoURL);

    }




   if (post1.getPostType() == 0) {
      // set body of the post
      TextView postBody = findViewById(R.id.TextPostBody);
      postBody.setText(post1.getTextPostcontent() );
      postBody.getText();


    } else if (post1.getPostType() == 1) {
      // set image of the post
      ImageView uploadedImage = (ImageView) findViewById(R.id.imageUploadedView);
      Picasso.get().load(post1.getImageURL()).into(uploadedImage);
    } else if (post1.getPostType() == 2) {
      // set VideoLinks
      WebView viewVideoLinks = (WebView) findViewById(R.id.videoWebView);
      setuoVideoSetting(viewVideoLinks, post1.videoURL);
    }

    // set Title psot
    // TextView postTitle = (TextView) findViewById(R.id.PostTitle);
    // postTitle.setText(post1.postTitle);



  }

  public void upvote(View v) {
    TextView counter =findViewById(R.id.votecounter);
    int i=Integer.parseInt(counter.getText().toString()) ;
    Button up = findViewById(R.id.upvote);
    Button down =findViewById(R.id.downvote);
    if(down.getCurrentTextColor()==Color.RED)
    {
      down.setTextColor(Color.GRAY);
    }
    up.setTextColor(Color.BLUE);
    i++;
    counter.setText(Integer.toString(i));
  }

  public void downvote(View v) {
    TextView counter =findViewById(R.id.votecounter);
    int i=Integer.parseInt(counter.getText().toString()) ;
    Button down = findViewById(R.id.downvote);
    Button up =findViewById(R.id.upvote);
    if(up.getCurrentTextColor()==Color.BLUE)
    {
      up.setTextColor(Color.GRAY);
    }
    down.setTextColor(Color.RED);
    i--;
    counter.setText(Integer.toString(i));

  }

  public void upvotecomment(View v) {

    TextView counter =findViewById(R.id.votecounterforcomment);
    int i=Integer.parseInt(counter.getText().toString()) ;
    Button up = findViewById(R.id.upvotecomment);
    Button down =findViewById(R.id.downvotecomment);
    if(down.getCurrentTextColor()==Color.RED)
    {
      down.setTextColor(Color.GRAY);
    }
    up.setTextColor(Color.BLUE);
    i++;
    counter.setText(Integer.toString(i));
  }

  public void downvotecomment(View v) {
    TextView counter =findViewById(R.id.votecounterforcomment);
    int i=Integer.parseInt(counter.getText().toString()) ;
    Button down = findViewById(R.id.downvotecomment);
    Button up =findViewById(R.id.upvotecomment);
    if(up.getCurrentTextColor()==Color.BLUE)
    {
      up.setTextColor(Color.GRAY);
    }
    down.setTextColor(Color.RED);
    i--;
    counter.setText(Integer.toString(i));

  }


  public void upvotereply(View v) {
    TextView counter =findViewById(R.id.votecounterforreply);
    int i=Integer.parseInt(counter.getText().toString()) ;
    Button up = findViewById(R.id.upvotereply);
    Button down =findViewById(R.id.downvotereply);
    if(down.getCurrentTextColor()==Color.RED)
    {
      down.setTextColor(Color.GRAY);
    }
    up.setTextColor(Color.BLUE);
    i++;
    counter.setText(Integer.toString(i));
  }

  public void downvotereply(View v) {
    TextView counter =findViewById(R.id.votecounterforreply);
    int i=Integer.parseInt(counter.getText().toString()) ;
    Button down = findViewById(R.id.downvotereply);
    Button up =findViewById(R.id.upvotereply);
    if(up.getCurrentTextColor()==Color.BLUE)
    {
      up.setTextColor(Color.GRAY);
    }
    down.setTextColor(Color.RED);
    i--;
    counter.setText(Integer.toString(i));

  }


  private void setuoVideoSetting(WebView temp, String url) {
    String frameVideo =
        "<html><body><br><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/"
            + url
            + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

    WebView displayYoutubeVideo = (WebView) temp.findViewById(R.id.videoWebView);
    displayYoutubeVideo.setWebViewClient(
        new WebViewClient() {
          @Override
          public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
          }
        });
    WebSettings webSettings = displayYoutubeVideo.getSettings();
    webSettings.setJavaScriptEnabled(true);
    displayYoutubeVideo.loadData(frameVideo, "text/html", "utf-8");
    displayYoutubeVideo.getSettings().setLoadWithOverviewMode(true);
    displayYoutubeVideo.getSettings().setUseWideViewPort(true);
  }
}
