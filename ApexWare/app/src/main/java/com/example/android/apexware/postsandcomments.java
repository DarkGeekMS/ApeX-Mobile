package com.example.android.apexware;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.GONE;
/**
 * this class is to handle the data of post and it`s comments
 * @author Omar
 */
public class postsandcomments extends AppCompatActivity {
   String value;
  CustomAdapterForComments adapter; // adapter for the data in the list
  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    /** set the view */
    setContentView(R.layout.activity_postsndcomments);
    Window window = this.getWindow();
    // clear FLAG_TRANSLUCENT_STATUS flag:
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    // finally change the color
    window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorGray));
    ExpandableListView commentsList =  findViewById(R.id.listofcomments);
    final ArrayList<Comment> commentArrayList = new ArrayList();
    ArrayList<Comment> repliesArrayList = new ArrayList();
    HashMap<Comment, List<Comment>> listHashMap = new HashMap<>();
    /** * fake data */
    /** extracting info was sent with the intent to the post */
    Gson gson = new Gson();
    String postAsString = getIntent().getStringExtra("postToDisplay");
    final Post post1 = gson.fromJson(postAsString, Post.class);
    /** some comments */
    Comment comment1 = new Comment("1");
    comment1.setId("1");
    comment1.setCommentCreateDate(125);
    comment1.setCommentOwner("Omar229");
    comment1.setCommentContent(
            "lknlscnsln jjcnsljncl slxiklm pscmscm skcskc scjosijcoskn ss;l,pokpsm79 s98cu9sjcosjnci cih9sjoksc sisisisisis ");
    commentArrayList.add(comment1);
    Comment comment2 = new Comment("1");
    comment2.setId("2");
    comment2.setCommentCreateDate(2132);
    comment2.setCommentOwner("mostafa");
    comment2.setCommentContent(
            "lknlscnsln jjcnsljncl slxiklm pscmscm skcskc scjosijcoskn ss;l,pokpsm79 s98cu9sjcosjnci cih9sjoksc sisisisisis ");
    commentArrayList.add(comment2);
    Comment rply1 = new Comment("1");
    /** some replys */
    rply1.setId("3");
    rply1.setCommentCreateDate(12665);
    rply1.setCommentOwner("bla7");
    rply1.setCommentContent(
            "lknlscnsln jjcnsljncl slxiklm pscmscm skcskc scjosijcoskn ss;l,pokpsm79 s98cu9sjcosjnci cih9sjoksc sisisisisis ");
    repliesArrayList.add(rply1);
    Comment rply2 = new Comment("1");
    rply2.setId("4");
    rply2.setCommentCreateDate(12665);
    rply2.setCommentOwner("bla bla bllllla");
    rply2.setCommentContent(
            "lknlscnsln jjcnsljncl slxiklm pscmscm skcskc scjosijcoskn ss;l,pokpsm79 s98cu9sjcosjnci cih9sjoksc sisisisisis ");
    repliesArrayList.add(rply2);
    listHashMap.put(commentArrayList.get(0), repliesArrayList);
    listHashMap.put(commentArrayList.get(1), repliesArrayList);
    /**
     * here we send the comments and their replys to the adapter which assigns the to the proper
     * view
     */
    adapter = new CustomAdapterForComments(this, repliesArrayList, listHashMap, commentArrayList);
    commentsList.setAdapter(adapter);
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
                popup.getMenuInflater().inflate(R.menu.option_menu, popup.getMenu());
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
                                            value=response.toString();
                                            Toast.makeText(getApplicationContext(),value,Toast.LENGTH_SHORT).show();
                                          } catch (JSONException e) {
                                            e.printStackTrace();
                                          }
                                        }
                                      });}
                            // we can use item name to make intent for the new responces
                            if(item.getItemId()==R.id.hidepost){
                                //test,request was working
                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("id",post1.getPostId());
                                setResult(Activity.RESULT_OK,returnIntent);
                                finish();
                                //end test
                             /* hidePost(post1.getPostId(),Request.Method.GET, null,
                                      new  VolleyCallback(){
                                        @Override
                                        public void onSuccessResponse(String result) {
                                          try {
                                            JSONObject response = new JSONObject(result);
                                            value=response.toString();
                                            Toast.makeText(getApplicationContext(),"post is hidden",Toast.LENGTH_SHORT).show();
                                            // creating a new user object
                                            User user = new User(response.getString("token"));
                                            // storing the user in shared preferences
                                            SharedPrefmanager.getInstance(getApplicationContext()).userLogin(user);
                                              Intent intent = new Intent();
                                              intent.putExtra("editTextValue", post1.getPostId());
                                              setResult(Integer.parseInt(post1.getPostId()), intent);
                                              finish();
                                          } catch (JSONException e) {
                                            e.printStackTrace();
                                          }
                                        }
                                      });*/
                            }
                              if(item.getItemId()==R.id.reportpost){
                                  final ArrayList selectedItems = new ArrayList();  // Where we track the selected items
                                  AlertDialog.Builder builder = new AlertDialog.Builder(postsandcomments.this);
                                  builder.setTitle("report");
                                  builder.setMultiChoiceItems(R.array.report_reason, null, new DialogInterface.OnMultiChoiceClickListener() {
                                      @Override
                                      public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                          if (isChecked) {
                                              // If the user checked the item, add it to the selected items
                                              selectedItems.add(which);
                                          } else if (selectedItems.contains(which)) {
                                              // Else, if the item is already in the array, remove it
                                              selectedItems.remove(Integer.valueOf(which));
                                          }
                                      }
                                  });
                                  builder.setPositiveButton("send", new DialogInterface.OnClickListener() {
                                      @Override
                                      public void onClick(DialogInterface dialog, int id) {
                                          // User clicked send, we should send the selectedItems results to the server
                                          Toast.makeText(postsandcomments.this,"post is reported",Toast.LENGTH_SHORT).show();

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
                popup.show(); // showing popup menu
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
    TextView textPost = (TextView) findViewById(R.id.TextPostBody);
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
        Intent intent = new Intent(postsandcomments.this ,addCommentActivity.class);
        Gson gson = new Gson();
        String ID = gson.toJson(post1.getPostId());
        intent.putExtra("postID", ID); // sending the post to next activity
        startActivity(intent);
    }
});


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
    String url = "http://35.232.3.8/api/vote";
    StringRequest stringRequest =
            new StringRequest(
                    Request.Method.POST,
                    url,
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
    String url = "http://35.232.3.8/api/vote";
    StringRequest stringRequest =
            new StringRequest(
                    Request.Method.POST,
                    url,
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
    String url = "http://35.232.3.8/api/save";
    StringRequest stringRequest =
            new StringRequest(
                    Request.Method.POST,
                    url,
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
    String url = "http://35.232.3.8/api/Hide";
    StringRequest stringRequest =
            new StringRequest(
                    Request.Method.POST,
                    url,
                    new Response.Listener<String>() {
                      @Override
                      public void onResponse(String response) {
                        try {
                          // converting response to json object
                          JSONObject obj = new JSONObject(response);
                          // if no error in response
                          if (response != null) {
                              callback.onSuccessResponse(response);
                              Toast.makeText(getApplicationContext(), "Post is hidden", Toast.LENGTH_SHORT).show();
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
    }}



