package com.example.android.apexware;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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

import static android.app.PendingIntent.getActivity;
import static android.view.View.GONE;

public class CustomAdapterForHomePage extends ArrayAdapter {
    List<Post> hiddenPotsList = new ArrayList<>();
    /**
   * this class to make custom list to be for the post in form of card view layout it extend from
   * array adapter base class it only list of post in it
   */

  // to reference the Activity
  private final Activity context;
  String value;
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
  public View getView(final int position, View view, ViewGroup parent) {
     View listItem = view;
    if (listItem == null)
      listItem = LayoutInflater.from(context).inflate(R.layout.homepgaelistview, parent, false);
    final Post currentPost = potsList.get(position);
    final View post=listItem;
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
                            if(item.getItemId()==R.id.savepost){savePost(currentPost.getPostId(),Request.Method.GET, null,
                                    new  VolleyCallback(){
                                        @Override
                                        public void onSuccessResponse(String result) {
                                            try {
                                                JSONObject response = new JSONObject(result);
                                                value=response.toString();
                                                Toast.makeText(context,"post is saved",Toast.LENGTH_SHORT).show();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });}
                            if(item.getItemId()==R.id.hidepost){
                                hidePost(currentPost.getPostId(),Request.Method.GET, null, new  VolleyCallback(){
                                            @Override
                                            public void onSuccessResponse(String result) {
                                                try {
                                                    JSONObject response = new JSONObject(result);
                                                    value=response.toString();
                                                    hiddenPotsList.add(currentPost);
                                                    remove(currentPost);
                                                       /* post.setVisibility(View.GONE);*/
                                                    // creating a new user object
                                                    User user = new User(response.getString("token"));
                                                    // storing the user in shared preferences
                                                    SharedPrefmanager.getInstance(context).userLogin(user);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });

                            }
                              if(item.getItemId()==R.id.reportpost){
                                 final ArrayList selectedItems = new ArrayList();  // Where we track the selected items
                                  AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
                                          Toast.makeText(context,"post is reported",Toast.LENGTH_SHORT).show();

                                      }
                                  })
                                          .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                              @Override
                                              public void onClick(DialogInterface dialog, int id) {
                                                //report canceled
                                                  Toast.makeText(context,"report is canceled",Toast.LENGTH_SHORT).show();

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
              public void onClick(View v) {
                final int currentvotes = Integer.parseInt(counter.getText().toString());
                upVotePost(currentPost.getPostId(),Request.Method.POST, null,
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
                                  currentPost.setDownvoted(false);}
                                up.setTextColor(Color.BLUE);
                                currentPost.setUpvoted(true);}
                              else if (newvotes<currentvotes)//was upvoted & upvote clicked
                              {
                                up.setTextColor(Color.GRAY);
                                currentPost.setUpvoted(false);
                              }
                            } catch (JSONException e) {
                              e.printStackTrace();
                            }
                          }
                        });
              }
            });
    // downVote
    down.setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  final int currentvotes = Integer.parseInt(counter.getText().toString());
                  downVotePost(currentPost.getPostId(),Request.Method.GET, null,
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
                                              currentPost.setUpvoted(false);}
                                          down.setTextColor(Color.RED);
                                          currentPost.setDownvoted(true);}
                                      else if (newvotes>currentvotes)//was downvoted & downvote clicked(cancel downvote)
                                      {
                                          down.setTextColor(Color.GRAY);
                                          currentPost.setDownvoted(false);
                                      }
                                  } catch (JSONException e) {
                                      e.printStackTrace();
                                  }
                              }
                          });
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
    /**
     * upvote post request
     * @param postID
     * @param method
     * @param jsonValue
     * @param callback
     */
  public void upVotePost(String postID,int method, JSONObject jsonValue, final VolleyCallback callback){
    final String postId=postID;
    User user = SharedPrefmanager.getInstance(context).getUser();
    final String token=user.getToken();
    String url = "http://34.66.175.211/api/vote";
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
                              Toast.makeText(context, "you upvoted the post", Toast.LENGTH_SHORT).show();

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
  public void downVotePost(String postID,int method, JSONObject jsonValue, final VolleyCallback callback){
    final String postId=postID;
    User user = SharedPrefmanager.getInstance(context).getUser();
    final String token=user.getToken();
    String url = "http://34.66.175.211/api/vote";
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
                            Toast.makeText(context, "you downvoted the post", Toast.LENGTH_SHORT).show();
                          }
                          else {
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
    /**
     * save post request
     * @param postID
     * @param method
     * @param jsonValue
     * @param callback
     */
    public void savePost(String postID,int method, JSONObject jsonValue, final VolleyCallback callback){
    final String postId=postID;
    User user = SharedPrefmanager.getInstance(context).getUser();
    final String token=user.getToken();
    String url = "http://34.66.175.211/api/save";
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
                        String er=error.getMessage();
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
    /**
     * hide post request
     * @param postID
     * @param method
     * @param jsonValue
     * @param callback
     */
    public void hidePost(String postID,int method, JSONObject jsonValue, final VolleyCallback callback){
    final String postId=postID;
    User user = SharedPrefmanager.getInstance(context).getUser();
    final String token=user.getToken();
    String url = "http://34.66.175.211/api/Hide";
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
