package com.example.android.apexware;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.GONE;


public class CustomAdapterForHomePage extends ArrayAdapter {
  /**
   * this class to make custom list to be for the post in form of card view layout it extend from
   * array adapter base class it only list of post in it
   */
  // to reference the Activity
  private final Activity context;

  // list array of post objects
  List<Post> potsList = new ArrayList<>();

  /**
   * this is constructor for the this class it the setup each item with predefined xml layout
   *
   * @param context Activity context
   * @param list Array list posts that we want to represent it in a list
   */
  public CustomAdapterForHomePage(Activity context, ArrayList<Post> list) {
    super(context, R.layout.homepgaelistview, list);
    this.context = context;
    potsList = list;
  }

  /**
   * this is predefined method to get position of each item in list in service and assign some
   * attributes and events to it
   */
  public View getView(int position, View view, ViewGroup parent) {
    View listItem = view;
    if (listItem == null)
      listItem = LayoutInflater.from(context).inflate(R.layout.homepgaelistview, parent, false);
    final Post currentPost = potsList.get(position);

    // button event for this button
    final Button button = (Button) listItem.findViewById(R.id.popupmeu);
    button.setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, button);
                popup.getMenuInflater().inflate(R.menu.option_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(
                        new PopupMenu.OnMenuItemClickListener() {
                          @Override
                          public boolean onMenuItemClick(MenuItem item) {

                            if(item.getTitle()=="Save"){savePost(currentPost.getPostId());}
                    /*Toast.makeText(
                            postsandcomments.this,
                            "You Clicked : " + item.getTitle(),
                            Toast.LENGTH_SHORT)
                        .show();*/
                            // we can use item name to make intent for the new responces
                            if(item.getTitle()=="Hide"){
                              hidePost(currentPost.getPostId());

                            }
                   /* Toast.makeText(context, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT)
                        .show();*/
                            // we can use item name to make intent for the new responces
                            return true;
                          }
                        });
                popup.show(); // showing popup menu
              }
            });

    // set apexcom logo
    ImageView apexcomLogo = (ImageView) listItem.findViewById(R.id.apexcomlogo);
    Picasso.get().load(currentPost.getApexcomLogo()).resize(50, 50).into(apexcomLogo);

    // set apexcom name
    TextView apexcomName = (TextView) listItem.findViewById(R.id.apexcomName);
    apexcomName.setText(currentPost.getApexcomName());

    // set post owner and time of created post
    TextView postOwnerAndCreatedTime =
            (TextView) listItem.findViewById(R.id.apexcomOwnerNameAndTimeCreated);
    postOwnerAndCreatedTime.setText(
            "Posted by " + currentPost.getPostOwner() + "." + currentPost.getPostCreateDate());

    // set Title psot
    TextView postTitle = (TextView) listItem.findViewById(R.id.PostTitle);
    postTitle.setText(currentPost.getPostTitle());

    // check the type of the post and disable remaining post types
    View tempView = listItem;
    setViewGone(currentPost.getPostType(), tempView);
    // only activate the proper view
    assignRightType(listItem, currentPost);
    /*up and down vote*/
    final Button up = listItem.findViewById(R.id.upvote);
    final Button down = listItem.findViewById(R.id.downvote);
    final TextView counter = listItem.findViewById(R.id.votecounter);
    // upvote
    up.setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View v) {int i = Integer.parseInt(counter.getText().toString());
                if (currentPost.isUpvoted() == false) {
                  if (currentPost.isDownvoted() == false) // was not up or down (default case)
                  {
                    upvotePost(currentPost.getPostId(),i);
                    up.setTextColor(Color.BLUE);

                    i++;
                    counter.setText(Integer.toString(i));
                    currentPost.setUpvoted(true);
                  } else if (currentPost.isDownvoted()
                          == true) // was down voted and up vote was clicked
                  {upvotePost(currentPost.getPostId(),i);
                    down.setTextColor(Color.GRAY);
                    up.setTextColor(Color.BLUE);
                    i += 2;
                    counter.setText(Integer.toString(i));
                    currentPost.setUpvoted(true);
                    currentPost.setDownvoted(false);
                  }
                } else if (currentPost.isUpvoted() == true) // was upvoted and upvote was clicked
                {
                  up.setTextColor(Color.GRAY);
                  i--;
                  counter.setText(Integer.toString(i));
                  currentPost.setUpvoted(false);
                }
              }
            });
    // downVote
    down.setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View v) {int i = Integer.parseInt(counter.getText().toString());
                if (currentPost.isDownvoted() == false) {
                  if (currentPost.isUpvoted() == false) // was not up or down (default case)
                  {
                    downvotePost(currentPost.getPostId(),i);
                    down.setTextColor(Color.RED);
                    i--;
                    counter.setText(Integer.toString(i));
                    currentPost.setDownvoted(true);
                  } else if (currentPost.isUpvoted() == true) // was up voted and down vote was clicked
                  {
                    downvotePost(currentPost.getPostId(),i);
                    down.setTextColor(Color.RED);
                    up.setTextColor(Color.GRAY);
                    i -= 2;
                    counter.setText(Integer.toString(i));
                    currentPost.setUpvoted(false);
                    currentPost.setDownvoted(true);
                  }
                } else if (currentPost.isDownvoted()
                        == true) // was down voted and down vote was clicked
                {
                  down.setTextColor(Color.GRAY);
                  i++;
                  counter.setText(Integer.toString(i));
                  currentPost.setDownvoted(false);
                }
              }
            });

    return listItem;
  };

  /**
   *
   *
   * <h1>this method activate only wanted view</h1>
   *
   * <p>this method set views which are not in use as gone im xml layout
   *
   * @param type int to define type post to set wanted view and reset unwanted view
   * @param view view that take control of xml file
   */
  private void setViewGone(int type, View view) {
    TextView textPost = (TextView) view.findViewById(R.id.TextPostBody);
    WebView videoLinkView = (WebView) view.findViewById(R.id.videoWebView);
    ImageView uploadedImageView = (ImageView) view.findViewById(R.id.imageUploadedView);
    if (type == 0) {
      textPost.setVisibility(View.VISIBLE);
      videoLinkView.setVisibility(GONE);
      uploadedImageView.setVisibility(GONE);
    } else if (type == 1) {
      textPost.setVisibility(GONE);
      videoLinkView.setVisibility(GONE);
      uploadedImageView.setVisibility(View.VISIBLE);
    } else if (type == 2) {
      textPost.setVisibility(GONE);
      uploadedImageView.setVisibility(GONE);
      videoLinkView.setVisibility(View.VISIBLE);
    }
  }

  /**
   *
   *
   * <h1>this method assign only activated view to value it should take</h1>
   *
   * <p>this method assign only activated view to avoid redundancy and optimize performance
   *
   * @param listView it take the view in charge of control
   * @param tempPost it take post to activtae the current post correctly in the item list
   */
  private void assignRightType(View listView, Post tempPost) {
    switch (tempPost.getPostType()) {
      case 0:
        // set body of the post
        TextView postBody = listView.findViewById(R.id.TextPostBody);
        postBody.setText(tempPost.getTextPostcontent());
        // break;
      case 1:
        // set image of the post
        ImageView uploadedImage = (ImageView) listView.findViewById(R.id.imageUploadedView);
        Picasso.get().load(tempPost.getImageURL()).into(uploadedImage);
        // break;
      case 2:
        // set VideoLinks
        WebView viewVideoLinks = (WebView) listView.findViewById(R.id.videoWebView);
        setuoVideoSetting(viewVideoLinks, tempPost.getVideoURL());
        // break;
    }
  }

  /**
   *
   *
   * <h1>this method setup for video preview</h1>
   *
   * <p>this method setup setting to represent video from given url
   *
   * @param url string contain url of the video
   * @param temp temproray view to to be resbonsible for setting up web view to view video inside it
   */
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
    // displayYoutubeVideo.getSettings().setUseWideViewPort(true);
  }



  //upvote request 'send 1'
  public void upvotePost(String postID,int old){
    final String postId=postID;
    final String token= SharedPrefmanager.getInstance(context.getApplicationContext()).getToken();
    final int oldvotes=old;
    String url = "http://localhost/api/vote";
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
                            final String receivedResponse = obj.getString("votes");
                            int newvotes = Integer.parseInt(receivedResponse);
                            if (newvotes > oldvotes) {
                              Toast.makeText(context, "you upvoted the post", Toast.LENGTH_SHORT).show();
                            }
                          } else {
                            Toast.makeText(
                                    context,
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
                                context,
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
    VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
  }
  //same as upvote but we send -1 instead of 1
  public void downvotePost(String postID,int old){
    final String postId=postID;
    final String token= SharedPrefmanager.getInstance(context.getApplicationContext()).getToken();
    final int oldvotes=old;
    String url = "http://localhost/api/vote";
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
                            final String receivedResponse = obj.getString("votes");
                            int newvotes = Integer.parseInt(receivedResponse);
                            if (newvotes < oldvotes) {
                              Toast.makeText(context, "you downvoted the post", Toast.LENGTH_SHORT).show();
                            }
                          } else {
                            Toast.makeText(
                                    context,
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
                                context,
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
    VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
  }
  public void savePost(String postID){
    final String postId=postID;
    final String token= SharedPrefmanager.getInstance(context.getApplicationContext()).getToken();
    String url = "http://localhost/api/save";
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
                            Toast.makeText(context, "Post is saved ", Toast.LENGTH_SHORT).show();
                          } else {
                            Toast.makeText(
                                    context,
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
                                context,
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
    VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
  }
  public void hidePost(String postID){
    final String postId=postID;
    final String token= SharedPrefmanager.getInstance(context.getApplicationContext()).getToken();
    String url = "http://localhost/api/Hide";
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
                            Toast.makeText(context, "Post is hidden", Toast.LENGTH_SHORT).show();
                          } else {
                            Toast.makeText(
                                    context,
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
                                context,
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
    VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
  }





}
