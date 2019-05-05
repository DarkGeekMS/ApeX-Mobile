package com.example.android.apexware;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
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
import com.squareup.picasso.Picasso;

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

import static android.view.View.GONE;
import static java.lang.StrictMath.abs;

/**
 * this class is to handle the data of post and it`s comments
 * @author Omar
 */
public class postsandcomments extends AppCompatActivity {
   String value;
   int position;
   int mSelected = -1;
    Point p;
     Post post1=new Post();
    TextView textPost;
    final ArrayList<Comment> commentArrayList = new ArrayList();
    final ArrayList<Comment> repliesArrayList = new ArrayList();
    final HashMap<Comment, List<Comment>> listHashMap = new HashMap<>();
    User user = SharedPrefmanager.getInstance(postsandcomments.this).getUser();
    final String token=user.getToken();
    CustomAdapterForComments adapter; // adapter for the data in the list
    List<ArrayList<Comment>> allreplies;
    ArrayList<Comment> repliesOFOneComment=new ArrayList();
  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    /* set the view */
    setContentView(R.layout.activity_postsndcomments);
    Window window = this.getWindow();
    // clear FLAG_TRANSLUCENT_STATUS flag:
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    // finally change the color
    window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorGray));
    final ExpandableListView commentsList =  findViewById(R.id.listofcomments);

    /** * fake data */
    /** extracting info was sent with the intent to the post */
    Gson gson = new Gson();
    String postAsString = getIntent().getStringExtra("postToDisplay");
    position=getIntent().getIntExtra("itemPos",0);
     post1 = gson.fromJson(postAsString, Post.class);
    /** some comments */
    /*Comment comment1 = new Comment(post1.getPostId());
    comment1.setId("1");
    comment1.setCommentCreateDate( "1265");
    comment1.setCommentOwner("Omar229");
    comment1.setCommentContent(
            "lknlscnsln jjcnsljncl slxiklm pscmscm skcskc scjosijcoskn ss;l,pokpsm79 s98cu9sjcosjnci cih9sjoksc sisisisisis ");
    commentArrayList.add(comment1);
    final Comment comment2 = new Comment("1");
    comment2.setId("2");
    comment2.setCommentCreateDate("1265");
    comment2.setCommentOwner("mostafa");
    comment2.setCommentContent(
            "lknlscnsln jjcnsljncl slxiklm pscmscm skcskc scjosijcoskn ss;l,pokpsm79 s98cu9sjcosjnci cih9sjoksc sisisisisis ");
    commentArrayList.add(comment2);
    Comment rply1 = new Comment("1");
    *//** some replys *//*
    rply1.setId("3");
    rply1.setCommentCreateDate("1265");
    rply1.setCommentOwner("bla7");
    rply1.setCommentContent(
            "lknlscnsln jjcnsljncl slxiklm pscmscm skcskc scjosijcoskn ss;l,pokpsm79 s98cu9sjcosjnci cih9sjoksc sisisisisis ");
    repliesArrayList.add(rply1);
    Comment rply2 = new Comment("1");
    rply2.setId("4");
    rply2.setCommentCreateDate("1265");
    rply2.setCommentOwner("bla bla bllllla");
    rply2.setCommentContent(
            "lknlscnsln jjcnsljncl slxiklm pscmscm skcskc scjosijcoskn ss;l,pokpsm79 s98cu9sjcosjnci cih9sjoksc sisisisisis ");
    repliesArrayList.add(rply2);
    listHashMap.put(commentArrayList.get(0), repliesArrayList);
    listHashMap.put(commentArrayList.get(1), repliesArrayList);
*/      getResponse(Request.Method.POST,
              Routes.moreComments,
              null,
              new VolleyCallback(){
                  @Override
                  public void onSuccessResponse(String response) {
                      try {
                          // converting response to json object
                          JSONObject obj = new JSONObject(response);
                          JSONArray jsonArray=obj.getJSONArray("comments");
                          // if no error in response
                          if (response != null) {
                              for(int i=0;i<jsonArray.length();i++){
                                  JSONObject current=jsonArray.getJSONObject(i);
                                  if(current.getString("parent")=="null")
                                  { Comment temp=new Comment(post1.getPostId());
                                  temp.setId(current.getString("id"));
                                  temp.setPostId(current.getString("root"));
                                  temp.setCommentOwner(current.getString("writerUsername"));
                                  temp.setCommentContent(current.getString("content"));
                                  temp.setVotesCount(current.getInt("votes"));
                                  int vot=current.getInt("userVote");

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
                                      temp.setCommentCreateDate(Long.toString(diffMinutes)+" min ago");
                                  }
                                  else if(diffHours<23){
                                      temp.setCommentCreateDate(Long.toString(diffHours)+" hr ago");
                                  }
                                  else if(diffDays<7){
                                      temp.setCommentCreateDate(Long.toString(diffDays)+" days ago");
                                  }
                                  else{
                                      temp.setCommentCreateDate(Long.toString(diffWeeks)+" weeks ago");
                                  }


                                      commentArrayList.add(temp);}
                                else{
                                      Comment reply=new Comment(post1.getPostId());
                                      reply.setId(current.getString("id"));
                                      reply.setPostId(current.getString("root"));
                                      reply.setCommentOwner(current.getString("writerUsername"));
                                      reply.setCommentContent(current.getString("content"));
                                      reply.setVotesCount(current.getInt("votes"));
                                      reply.setParentID(current.getString("parent"));
                                      int vot=current.getInt("userVote");

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
                                          reply.setCommentCreateDate((diffMinutes)+" min ago");
                                      }
                                      else if(diffHours<23){
                                          reply.setCommentCreateDate((diffHours)+" hr ago");
                                      }
                                      else if(diffDays<7){
                                          reply.setCommentCreateDate((diffDays)+" days ago");
                                      }
                                      else{
                                          reply.setCommentCreateDate((diffWeeks)+" weeks ago");
                                      }

                                      repliesArrayList.add(reply);
                                  }
                              }
                              for(int k=0;k<commentArrayList.size();k++)
                              {
                                  for(int l=0;l<repliesArrayList.size();l++)
                                  {
                                      if(commentArrayList.get(k).getId().equals(repliesArrayList.get(l).getParentID()))
                                      repliesOFOneComment.add(repliesArrayList.get(l));
                                  }
                                  listHashMap.put(commentArrayList.get(k), repliesOFOneComment);

                              }

                              adapter = new CustomAdapterForComments(postsandcomments.this, repliesArrayList, listHashMap, commentArrayList);
                              commentsList.setAdapter(adapter);
                              Toast.makeText(postsandcomments.this,"Home done retrived",Toast.LENGTH_SHORT).show();
                          } else {
                              int x=0;
                              Toast.makeText(
                                    postsandcomments.this, "Unsuccessful operation", Toast.LENGTH_SHORT)
                                      .show();
                          }

                      } catch (JSONException e) {
                          e.printStackTrace();
                      }
                  }
              },token,post1.getPostId());


      /**
       * here we send the comments and their replys to the adapter which assigns the to the proper
       * view
       */

    /** here we add the post to the top of comments */
    View po = getLayoutInflater().inflate(R.layout.homepgaelistview, null);
    commentsList.addHeaderView(po);
    /** popup menu action */
    final Button button = findViewById(R.id.popupmeu);
    button.setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                PopupMenu popup = new PopupMenu(postsandcomments.this, button);
                  User user = SharedPrefmanager.getInstance(postsandcomments.this).getUser();
                  final String token = user.getToken();
                  if(user.getUsername().equals(post1.getPostOwner())){
                      popup.getMenuInflater().inflate(R.menu.mypostoptions, popup.getMenu());
                      popup.setOnMenuItemClickListener(
                              new PopupMenu.OnMenuItemClickListener() {
                                  @Override
                                  public boolean onMenuItemClick(MenuItem item) {
                                      if(item.getItemId()==R.id.editmypost){
                                          if(post1.getPostType()!=1||post1.getPostType()!=2)//no edit to photos or videos
                                          { Intent intent = new Intent( postsandcomments.this,EditPost.class);
                                          Gson gson = new Gson();
                                          String postAsString = gson.toJson(post1);
                                          intent.putExtra("postToEdit", postAsString); // sending the post to next activity
                                          startActivityForResult(intent,10);}
                                      }
                                      if(item.getItemId()==R.id.deletemypost){
                                          deletePost(post1.getPostId(),Request.Method.DELETE, null,
                                                  new  VolleyCallback(){
                                                      @Override
                                                      public void onSuccessResponse(String result) {
                                                          try {
                                                              JSONObject response = new JSONObject(result);
                                                              value=response.getString("deleted");
                                                              if(value=="true"){
                                                                  Toast.makeText(postsandcomments.this,"Post is deleted",Toast.LENGTH_SHORT).show();
                                                                  startActivity(new Intent(postsandcomments.this,HomePage.class));
                                                              }
                                                              else  Toast.makeText(postsandcomments.this,"error,not deletd",Toast.LENGTH_SHORT).show();

                                                          } catch (JSONException e) {
                                                              e.printStackTrace();
                                                          }
                                                      }
                                                  },post1.getPostId());
                                      }
                                      // we can use item name to make intent for the new responces
                                      return true;
                                  }
                              });
                      popup.show(); // showing popup menu
                  }
                  else {  popup.getMenuInflater().inflate(R.menu.option_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(
                        new PopupMenu.OnMenuItemClickListener() {
                          @Override
                          public boolean onMenuItemClick(MenuItem item) {
                            if(item.getItemId()==R.id.savepost){
                              savePost(post1.getPostId(),Request.Method.GET, null,
                                      new  VolleyCallback(){
                                        @Override
                                        public void onSuccessResponse(String result) {
                                          try {
                                            JSONObject response = new JSONObject(result);
                                              value=response.getString("value");
                                              if(value=="true")
                                                  Toast.makeText(postsandcomments.this,"Post is saved",Toast.LENGTH_SHORT).show();
                                              else  Toast.makeText(postsandcomments.this,"error,not saved",Toast.LENGTH_SHORT).show();
                                          } catch (JSONException e) {
                                            e.printStackTrace();
                                          }
                                        }
                                      });}
                            // we can use item name to make intent for the new responces
                            if(item.getItemId()==R.id.hidepost){
                              hidePost(post1.getPostId(),Request.Method.GET, null,
                                      new  VolleyCallback(){
                                        @Override
                                        public void onSuccessResponse(String result) {
                                          try {
                                            JSONObject response = new JSONObject(result);
                                            value=response.getString("hide");
                                            if(value.equals("true"))
                                            {
                                                Intent hidePost=new Intent();
                                                hidePost.putExtra("postpos",post1.getPostId());
                                                setResult(20,hidePost);
                                                finish();
                                            }
                                            else  Toast.makeText(getApplicationContext(),"Error,post isn`t hidden",Toast.LENGTH_SHORT).show();

                                          } catch (JSONException e) {
                                            e.printStackTrace();
                                          }
                                        }
                                      });
                            }
                              if(item.getItemId()==R.id.reportpost){
                                  final String[] reason = new String[]{"It's spam or abuse", "It breaks the rules", "It's threatening self-harm or suicide"};
                                  final ArrayList selectedItems = new ArrayList();  // Where we track the selected items
                                  AlertDialog.Builder builder = new AlertDialog.Builder(postsandcomments.this);
                                  builder.setTitle("report");
                                  builder.setMultiChoiceItems(reason, null, new DialogInterface.OnMultiChoiceClickListener() {
                                      @Override
                                      public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                          if (isChecked) {
                                              if ((mSelected != -1) && (mSelected != which)) {
                                                  final int oldVal = mSelected;
                                                  final AlertDialog alert = (AlertDialog)dialog;
                                                  final ListView list = alert.getListView();
                                                  list.setItemChecked(oldVal, false);}
                                              // If the user checked the item, add it to the selected items
                                              mSelected = which;
                                              selectedItems.add(mSelected);
                                          } else if (selectedItems.contains(which)) {
                                              // Else, if the item is already in the array, remove it
                                              selectedItems.remove(Integer.valueOf(which));
                                          }
                                      }
                                  });
                                  builder.setPositiveButton("send", new DialogInterface.OnClickListener() {
                                      @Override
                                      public void onClick(DialogInterface dialog, int id) {
                                          reportPost(post1.getPostId(),Request.Method.GET, null,
                                                  new  VolleyCallback(){
                                                      @Override
                                                      public void onSuccessResponse(String result) {
                                                          try {
                                                              JSONObject response = new JSONObject(result);
                                                              value=response.getString("reported");
                                                              if(value.equals("true"))
                                                                  Toast.makeText(postsandcomments.this,"Post Is Reported",Toast.LENGTH_SHORT).show();
                                                              else  Toast.makeText(postsandcomments.this,"error,not reported",Toast.LENGTH_SHORT).show();
                                                          } catch (JSONException e) {
                                                              e.printStackTrace();
                                                          }
                                                      }
                                                  },reason[mSelected]);
                                          // User clicked send, we should send the selectedItems results to the server

                                      }
                                  })
                                          .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                              @Override
                                              public void onClick(DialogInterface dialog, int id) {
                                                  //report canceled

                                                  Toast.makeText(postsandcomments.this,"report is canceled",Toast.LENGTH_SHORT).show();

                                              }
                                          });

                                  builder.show();

                              }
                            return true;
                          }
                        });
                popup.show(); }// showing popup menu
              }
            });
    /*put the data on the views*/
    /*logo*/
    ImageView logo = findViewById(R.id.apexcomlogo);
    Picasso.get().load(post1.getApexcomLogo()).resize(50, 50).into(logo);
    // *apex community name*//*
    TextView comName = findViewById(R.id.apexcomName);
    comName.setText(post1.getApexcomName());
    // *creator name and date created*//*
    TextView postOwnerAndCreatedTime = (TextView) findViewById(R.id.apexcomOwnerNameAndTimeCreated);
    postOwnerAndCreatedTime.setText(
            "posted by " + post1.getPostOwner() + "." + post1.getPostCreateDate());
    TextView Title = findViewById(R.id.PostTitle);
    Title.setText(post1.getPostTitle());
     textPost = (TextView) findViewById(R.id.TextPostBody);
    WebView videoLinkView = (WebView) findViewById(R.id.videoWebView);
    ImageView uploadedImageView = (ImageView) findViewById(R.id.imageUploadedView);
    textPost.setText(post1.getTextPostcontent());
    /** handling which view will be shown */
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
      setuoVideoSetting(videoLinkView, post1.getVideoURL());
    }

    /** setting 1 view to be shown */
    if (post1.getPostType() == 0) {
      // set body of the post
      TextView postBody = findViewById(R.id.TextPostBody);
      postBody.setText(post1.getTextPostcontent());
      postBody.getText();
    } else if (post1.getPostType() == 1) {
      // set image of the post
      ImageView uploadedImage = (ImageView) findViewById(R.id.imageUploadedView);
      Picasso.get().load(post1.getImageURL()).into(uploadedImage);
    } else if (post1.getPostType() == 2) {
      // set VideoLinks
      WebView viewVideoLinks = (WebView) findViewById(R.id.videoWebView);
      setuoVideoSetting(viewVideoLinks, post1.getVideoURL());
    }
    final Button up = findViewById(R.id.upvote);
    final Button down = findViewById(R.id.downvote);
    final TextView counter = findViewById(R.id.votecounter);
    /** up vote */
    up.setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  final int currentvotes = Integer.parseInt(counter.getText().toString());
                    upvote(post1.getPostId(),Request.Method.POST, null,
                            new  VolleyCallback(){
                              @Override
                              public void onSuccessResponse(String result) {
                                try {
                                  JSONObject response = new JSONObject(result);
                                  value=response.getString("votes");
                                  counter.setText(value);
                                  int newvotes=Integer.parseInt(value);
                                  if(newvotes>currentvotes){
                                      if(newvotes==currentvotes+2)//was downvoted and upvote clicked
                                      { down.setTextColor(Color.GRAY);
                                      post1.setDownvoted(false);}
                                      up.setTextColor(Color.BLUE);
                                      post1.setUpvoted(true);}
                                      else if (newvotes<currentvotes)//was upvoted & upvote clicked
                                      {
                                          up.setTextColor(Color.GRAY);
                                          post1.setUpvoted(false);
                                      }
                                } catch (JSONException e) {
                                  e.printStackTrace();
                                }
                              }
                            });
              }
            });

    /** downVote */
    down.setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  final int currentvotes = Integer.parseInt(counter.getText().toString());
                    downvote(post1.getPostId(),Request.Method.GET, null,
                            new  VolleyCallback(){
                              @Override
                              public void onSuccessResponse(String result) {
                                try {
                                  JSONObject response = new JSONObject(result);
                                  value=response.getString("votes");
                                    counter.setText(value);
                                    int newvotes=Integer.parseInt(value);
                                    if(newvotes<currentvotes){
                                        if(newvotes==currentvotes-2)//was upvoted and downvote clicked
                                        { up.setTextColor(Color.GRAY);
                                        post1.setUpvoted(false);}
                                        down.setTextColor(Color.RED);
                                        post1.setDownvoted(true);}
                                    else if (newvotes>currentvotes)//was downvoted & downvote clicked(cancel downvote)
                                    {
                                        down.setTextColor(Color.GRAY);
                                        post1.setDownvoted(false);
                                    }
                                } catch (JSONException e) {
                                  e.printStackTrace();
                                }
                              }
                            });

              }
            });
TextView addcomment=findViewById(R.id.addcomment);
addcomment.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(postsandcomments.this , AddCommentActivity.class);
        Gson gson = new Gson();
        String ID = gson.toJson(post1.getPostId());
        intent.putExtra("postID", ID); // sending the post to next activity
        startActivityForResult(intent,9);
    }
});

    /*  commentsList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
          @Override
          public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
              Log.d("onGroupClick:", "worked");
              parent.expandGroup(groupPosition);
              return false;
          }
      });*/

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
    /**
     * upvote post request
     * @param postID
     * @param method
     * @param jsonValue
     * @param callback
     */
  public void upvote(String postID,int method, JSONObject jsonValue, final VolleyCallback callback){
    final String postId=postID;
    User user = SharedPrefmanager.getInstance(getApplicationContext()).getUser();
    final String token=user.getToken();
    StringRequest stringRequest =
            new StringRequest(
                    Request.Method.POST,
                    Routes.vote,
                    new Response.Listener<String>() {
                      @Override
                      public void onResponse(String response) {
                        try {
                          // converting response to json object
                          JSONObject obj = new JSONObject(response);
                          // if no error in response
                          if (response != null) {
                              callback.onSuccessResponse(response);
                              Toast.makeText(getApplicationContext(), "you upvoted the post", Toast.LENGTH_SHORT).show();
                          } else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "something went wrong.. try again",
                                    Toast.LENGTH_SHORT)
                                    .show();
                          }
                        } catch (JSONException e) {
                          e.printStackTrace();
                        }
                      }
                    },
                    new Response.ErrorListener() {
                      @Override
                      public void onErrorResponse(VolleyError error) {
                        Toast.makeText(
                                getApplicationContext(),
                                "something went wrong with the connection",
                                Toast.LENGTH_SHORT)
                                .show();
                      }
                    }) {
              @Override
              protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", postId);
                params.put("dir", "1");
                params.put("token", token);
                return params;
              }
            };
    VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
  }

    /**
     * downvote post request
     * @param postID
     * @param method
     * @param jsonValue
     * @param callback
     */
  public void downvote(String postID,int method, JSONObject jsonValue, final VolleyCallback callback){
    final String postId=postID;
    User user = SharedPrefmanager.getInstance(getApplicationContext()).getUser();
    final String token=user.getToken();
    StringRequest stringRequest =
            new StringRequest(
                    Request.Method.POST,
                    Routes.vote,
                    new Response.Listener<String>() {
                      @Override
                      public void onResponse(String response) {
                        try {
                          // converting response to json object
                          JSONObject obj = new JSONObject(response);
                          // if no error in response
                          if (response != null) {
                              callback.onSuccessResponse(response);
                              Toast.makeText(getApplicationContext(), "you downvoted the post", Toast.LENGTH_SHORT).show();
                            }
                           else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "something went wrong.. try again",
                                    Toast.LENGTH_SHORT)
                                    .show();
                          }
                        } catch (JSONException e) {
                          e.printStackTrace();
                        }
                      }
                    },
                    new Response.ErrorListener() {
                      @Override
                      public void onErrorResponse(VolleyError error) {
                        Toast.makeText(
                                getApplicationContext(),
                                "something went wrong with the connection",
                                Toast.LENGTH_SHORT)
                                .show();
                      }
                    }) {
              @Override
              protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", postId);
                params.put("dir", "-1");
                params.put("token", token);
                return params;
              }
            };
    VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
  }

    /**
     * save post request
     * @param postID
     * @param method
     * @param jsonValue
     * @param callback
     */
  public void savePost(String postID,int method, JSONObject jsonValue, final VolleyCallback callback){
    final String postId=postID;
    User user = SharedPrefmanager.getInstance(getApplicationContext()).getUser();
    final String token=user.getToken();
    StringRequest stringRequest =
            new StringRequest(
                    Request.Method.POST,
                    Routes.savePost,
                    new Response.Listener<String>() {
                      @Override
                      public void onResponse(String response) {
                        try {
                          // converting response to json object
                          JSONObject obj = new JSONObject(response);
                          // if no error in response
                          if (response != null) {
                              callback.onSuccessResponse(response);
                              Toast.makeText(getApplicationContext(), "Post is saved ", Toast.LENGTH_SHORT).show();
                          } else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "something went wrong.. try again",
                                    Toast.LENGTH_SHORT)
                                    .show();
                          }
                        } catch (JSONException e) {
                          e.printStackTrace();
                        }
                      }
                    },
                    new Response.ErrorListener() {
                      @Override
                      public void onErrorResponse(VolleyError error) {
                          String er=error.getMessage();
                        Toast.makeText(
                                getApplicationContext(),
                                "something went wrong with the connection",
                                Toast.LENGTH_SHORT)
                                .show();
                      }
                    }) {
              @Override
              protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID", postId);
                params.put("token", token);
                return params;
              }
            };
    VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
  }

    /**
     * hide post request
     * @param postID
     * @param method
     * @param jsonValue
     * @param callback
     */
  public void hidePost(String postID,int method, JSONObject jsonValue, final VolleyCallback callback){
    final String postId=postID;
    User user = SharedPrefmanager.getInstance(getApplicationContext()).getUser();
    final String token=user.getToken();
    StringRequest stringRequest =
            new StringRequest(
                    Request.Method.POST,
                    Routes.hidePost,
                    new Response.Listener<String>() {
                      @Override
                      public void onResponse(String response) {
                        try {
                          // converting response to json object
                          JSONObject obj = new JSONObject(response);
                          // if no error in response
                          if (response != null) {
                              callback.onSuccessResponse(response);
                          } else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "something went wrong.. try again",
                                    Toast.LENGTH_SHORT)
                                    .show();
                          }
                        } catch (JSONException e) {
                          e.printStackTrace();
                        }
                      }
                    },
                    new Response.ErrorListener() {
                      @Override
                      public void onErrorResponse(VolleyError error) {
                        Toast.makeText(
                                getApplicationContext(),
                                "something went wrong with the connection",
                                Toast.LENGTH_SHORT)
                                .show();
                      }
                    }) {
              @Override
              protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", postId);
                params.put("token", token);
                return params;
              }
            };
    VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
  }

    public void reportPost(String postID, int method, JSONObject jsonValue, final VolleyCallback callback, final String reason){
        final String postId=postID;
        User user = SharedPrefmanager.getInstance(getApplicationContext()).getUser();
        final String token=user.getToken();
        StringRequest stringRequest =
                new StringRequest(
                        Request.Method.POST,
                        Routes.report,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    // converting response to json object
                                    JSONObject obj = new JSONObject(response);
                                    // if no error in response
                                    if (response != null) {
                                        callback.onSuccessResponse(response);
                                    } else {
                                        Toast.makeText(
                                                getApplicationContext(),
                                                "something went wrong.. try again",
                                                Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(
                                        getApplicationContext(),
                                        "something went wrong with the connection",
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("name", postId);
                        params.put("token", token);
                        params.put("content",reason);
                        return params;
                    }
                };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    /**
     * upvote comment of a post
     * @param v
     */
    public void upvotecomment(View v) {
        TextView counter = findViewById(R.id.votecounterforcomment);
        int i = Integer.parseInt(counter.getText().toString());
        Button up = findViewById(R.id.upvotecomment);
        Button down = findViewById(R.id.downvotecomment);
        if (down.getCurrentTextColor() == Color.RED) {
            down.setTextColor(Color.GRAY);
        }
        up.setTextColor(Color.BLUE);
        i++;
        counter.setText(Integer.toString(i));
    }
    /**
     * downvote comment of a post
     * @param v
     */
    public void downvotecomment(View v) {
        TextView counter = findViewById(R.id.votecounterforcomment);
        int i = Integer.parseInt(counter.getText().toString());
        Button down = findViewById(R.id.downvotecomment);
        Button up = findViewById(R.id.upvotecomment);
        if (up.getCurrentTextColor() == Color.BLUE) {
            up.setTextColor(Color.GRAY);
        }
        down.setTextColor(Color.RED);
        i--;
        counter.setText(Integer.toString(i));
    }
    /**
     * upvote reply of a comment
     * @param v
     */
    public void upvotereply(View v) {
        TextView counter = findViewById(R.id.votecounterforreply);
        int i = Integer.parseInt(counter.getText().toString());
        Button up = findViewById(R.id.upvotereply);
        Button down = findViewById(R.id.downvotereply);
        if (down.getCurrentTextColor() == Color.RED) {
            down.setTextColor(Color.GRAY);
        }
        up.setTextColor(Color.BLUE);
        i++;
        counter.setText(Integer.toString(i));
    }
    /**
     * downvote reply of a comment
     * @param v
     */
    public void downvotereply(View v) {
        TextView counter = findViewById(R.id.votecounterforreply);
        int i = Integer.parseInt(counter.getText().toString());
        Button down = findViewById(R.id.downvotereply);
        Button up = findViewById(R.id.upvotereply);
        if (up.getCurrentTextColor() == Color.BLUE) {
            up.setTextColor(Color.GRAY);
        }
        down.setTextColor(Color.RED);
        i--;
        counter.setText(Integer.toString(i));
    }
    public void deletePost(String postID, int method, JSONObject jsonValue, final VolleyCallback callback,final String postname){
        User user = SharedPrefmanager.getInstance(postsandcomments.this).getUser();
        final String token=user.getToken();
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, Routes.delete+"token="+token+"&name="+postname,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccessResponse(response);
                    }
                },
                new Response.ErrorListener()
                {
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
                });
        VolleySingleton.getInstance(postsandcomments.this).addToRequestQueue(stringRequest);
    }
    public void getResponse(int method, String url, JSONObject jsonValue, final VolleyCallback callback,final  String token,final String id) {
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
                                        postsandcomments.this.getApplicationContext(), "Server Error", Toast.LENGTH_SHORT)
                                        .show();
                                error.getMessage();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("token", token);
                        params.put("parent",id);
                        return params;
                    }
                };
        VolleySingleton.getInstance(postsandcomments.this.getApplicationContext()).addToRequestQueue(stringRequest);
    }
//related 2 add comment
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==9&&resultCode==RESULT_OK)
        {
            Gson gson = new Gson();
            String newcom = data.getStringExtra("newcomment");
            final Comment Comment1 = gson.fromJson(newcom, Comment.class);
            if(Comment1!= null)
            {commentArrayList.add(Comment1);
                adapter.notifyDataSetChanged();}
           /* if(id!="300000")
            {
                for(int i = 0 ; i < postArrayList.size() ; i++){
                    if(id.equalsIgnoreCase(postArrayList.get(i).getPostId())){
                        postArrayList.remove(i);
                    }
                }
                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(),"post is hidden",Toast.LENGTH_SHORT).show();
            }
            else  Toast.makeText(getContext(),"what tf",Toast.LENGTH_SHORT).show();*/

        }
        if(requestCode==10&&resultCode==RESULT_OK){
            String edited = data.getStringExtra("edited");
            post1.setTextPostcontent(edited);
            textPost.setText(post1.getTextPostcontent());

        }
        if(requestCode==11&&resultCode==RESULT_OK)
        {
            Gson gson = new Gson();
            String commentAsString = getIntent().getStringExtra("commentEdited");
            final Comment comment = gson.fromJson(commentAsString, Comment.class);
            String id=comment.getId();
            for(int i=0;i<commentArrayList.size();i++)
            {
                if(commentArrayList.get(i).getId().equals(id))
                {commentArrayList.get(i).setCommentContent(comment.getCommentContent());}
            }
        }
    }
    //onActivityResult



}
