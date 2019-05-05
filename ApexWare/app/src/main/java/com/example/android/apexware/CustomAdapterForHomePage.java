package com.example.android.apexware;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
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
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;

public class CustomAdapterForHomePage extends ArrayAdapter {
    List<Post> hiddenPotsList = new ArrayList<>();
    int mSelected = -1;
   final User user = SharedPrefmanager.getInstance(this.getContext()).getUser();
    final String token=user.getToken();
    String userProfileToExplore;
    String userIDBlocked;

    @Override
    public void remove( Object object) {
        super.remove(object);
    }

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
            PopupMenu popup = new PopupMenu(CustomAdapterForHomePage.this.getContext(), button);
            User user =
                SharedPrefmanager.getInstance(CustomAdapterForHomePage.this.getContext()).getUser();
            final String token = user.getToken();
            if (user.getUsername().equals(currentPost.getPostOwner())) {
              popup.getMenuInflater().inflate(R.menu.mypostoptions, popup.getMenu());
              popup.setOnMenuItemClickListener(
                  new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                      if (item.getItemId() == R.id.editmypost) {
                        if (currentPost.getPostType() != 1
                            || currentPost.getPostType() != 2) // no edit to photos or videos
                        {
                          Intent intent =
                              new Intent(
                                  CustomAdapterForHomePage.this.getContext(), EditPost.class);
                          Gson gson = new Gson();
                          String postAsString = gson.toJson(currentPost);
                          intent.putExtra(
                              "postToEdit", postAsString); // sending the post to next activity
                          context.startActivityForResult(intent, 10);
                        }
                      }
                      if (item.getItemId() == R.id.deletemypost) {
                        deletePost(
                            currentPost.getPostId(),
                            Request.Method.DELETE,
                            null,
                            new VolleyCallback() {
                              @Override
                              public void onSuccessResponse(String result) {
                                try {
                                  JSONObject response = new JSONObject(result);
                                  value = response.getString("deleted");
                                  if (value .equals("true") ) {
                                    Toast.makeText(
                                            CustomAdapterForHomePage.this.getContext(),
                                            "Post is deleted",
                                            Toast.LENGTH_SHORT)
                                        .show();
                                    context.startActivity(
                                        new Intent(
                                            CustomAdapterForHomePage.this.getContext(),
                                            HomePage.class));
                                  } else
                                    Toast.makeText(
                                            CustomAdapterForHomePage.this.getContext(),
                                            "error,not deletd",
                                            Toast.LENGTH_SHORT)
                                        .show();

                                } catch (JSONException e) {
                                  e.printStackTrace();
                                }
                              }
                            },
                            currentPost.getPostId());
                      }
                      return true;
                    }
                  });
              popup.show(); // showing popup menu
            } else {
              popup.getMenuInflater().inflate(R.menu.option_menu, popup.getMenu());
              popup.setOnMenuItemClickListener(
                  new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                      if (item.getItemId() == R.id.savepost) {
                        savePost(
                            currentPost.getPostId(),
                            Request.Method.GET,
                            null,
                            new VolleyCallback() {
                              @Override
                              public void onSuccessResponse(String result) {
                                try {
                                  JSONObject response = new JSONObject(result);
                                  value = response.getString("value");
                                  if (value .equals("the post is saved successfully"))
                                    Toast.makeText(
                                            CustomAdapterForHomePage.this.getContext(),
                                            "Post is saved",
                                            Toast.LENGTH_SHORT)
                                        .show();
                                  else
                                    Toast.makeText(
                                            CustomAdapterForHomePage.this.getContext(),
                                            "error,not saved",
                                            Toast.LENGTH_SHORT)
                                        .show();
                                } catch (JSONException e) {
                                  e.printStackTrace();
                                }
                              }
                            });
                      }
                      // we can use item name to make intent for the new responces
                      if (item.getItemId() == R.id.hidepost) {
                        hidePost(
                            currentPost.getPostId(),
                            Request.Method.GET,
                            null,
                            new VolleyCallback() {
                              @Override
                              public void onSuccessResponse(String result) {
                                try {
                                  JSONObject response = new JSONObject(result);
                                  value = response.getString("hide");
                                  if (value .equals("true") ) {
                                    Intent hidePost = new Intent();
                                    hidePost.putExtra("postpos", currentPost.getPostId());
                                    context.setResult(RESULT_OK, hidePost);
                                    potsList.remove(currentPost);
                                    notifyDataSetChanged();
                                  } else
                                    Toast.makeText(
                                            CustomAdapterForHomePage.this.getContext(),
                                            "Error,post isn`t hidden",
                                            Toast.LENGTH_SHORT)
                                        .show();

                                } catch (JSONException e) {
                                  e.printStackTrace();
                                }
                              }
                            });
                      }
                      if (item.getItemId() == R.id.reportpost) {
                        final String[] reason =
                            new String[] {
                              "It's spam or abuse",
                              "It breaks the rules",
                              "It's threatening self-harm or suicide"
                            };
                        final ArrayList selectedItems =
                            new ArrayList(); // Where we track the selected items
                        AlertDialog.Builder builder =
                            new AlertDialog.Builder(CustomAdapterForHomePage.this.getContext());
                        builder.setTitle("report");
                        builder.setMultiChoiceItems(
                            reason,
                            null,
                            new DialogInterface.OnMultiChoiceClickListener() {
                              @Override
                              public void onClick(
                                  DialogInterface dialog, int which, boolean isChecked) {
                                if (isChecked) {
                                  if ((mSelected != -1) && (mSelected != which)) {
                                    final int oldVal = mSelected;
                                    final AlertDialog alert = (AlertDialog) dialog;
                                    final ListView list = alert.getListView();
                                    list.setItemChecked(oldVal, false);
                                  }
                                  // If the user checked the item, add it to the selected items
                                  mSelected = which;
                                  selectedItems.add(mSelected);
                                } else if (selectedItems.contains(which)) {
                                  // Else, if the item is already in the array, remove it
                                  selectedItems.remove(Integer.valueOf(which));
                                }
                              }
                            });
                        builder
                            .setPositiveButton(
                                "send",
                                new DialogInterface.OnClickListener() {
                                  @Override
                                  public void onClick(DialogInterface dialog, int id) {
                                    reportPost(
                                        currentPost.getPostId(),
                                        Request.Method.GET,
                                        null,
                                        new VolleyCallback() {
                                          @Override
                                          public void onSuccessResponse(String result) {
                                            try {
                                              JSONObject response = new JSONObject(result);
                                              value = response.getString("reported");
                                              if (value.equals("true"))
                                                Toast.makeText(
                                                        CustomAdapterForHomePage.this.getContext(),
                                                        "Post Is Reported",
                                                        Toast.LENGTH_SHORT)
                                                    .show();
                                              else
                                                Toast.makeText(
                                                        CustomAdapterForHomePage.this.getContext(),
                                                        "error,not reported",
                                                        Toast.LENGTH_SHORT)
                                                    .show();
                                            } catch (JSONException e) {
                                              e.printStackTrace();
                                            }
                                          }
                                        },
                                        reason[mSelected]);
                                    // User clicked send, we should send the selectedItems results
                                    // to the server

                                  }
                                })
                            .setNegativeButton(
                                "cancel",
                                new DialogInterface.OnClickListener() {
                                  @Override
                                  public void onClick(DialogInterface dialog, int id) {
                                    // report canceled

                                    Toast.makeText(
                                            CustomAdapterForHomePage.this.getContext(),
                                            "report is canceled",
                                            Toast.LENGTH_SHORT)
                                        .show();
                                  }
                                });

                        builder.show();
                      }
                      return true;
                    }
                  });
              popup.show();
            } // showing popup menu
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
    postOwnerAndCreatedTime.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            userProfileToExplore="Posted by " + currentPost.getPostOwner() + "." + currentPost.getPostCreateDate();
            Point p=new Point(0,50);
            // Open popup window
            if (p != null) showPopup((Activity) getContext(), p);
        }
    });

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
    counter.setText(String.valueOf(currentPost.getVotesCount()) );
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
                              currentPost.setVotesCount(newvotes);
                              if(!currentPost.isDownvoted()&&!currentPost.isUpvoted())//not voted before
                              { up.setTextColor(Color.BLUE);
                              currentPost.setUpvoted(true);
                              notifyDataSetChanged();}
                              else {
                                if(currentPost.isDownvoted()){
                                //was downvoted and upvote clicked
                                 down.setTextColor(Color.GRAY);
                                  currentPost.setDownvoted(false);
                                up.setTextColor(Color.BLUE);
                                currentPost.setUpvoted(true);
                                notifyDataSetChanged();}
                              else //was upvoted & upvote clicked
                              {
                                up.setTextColor(Color.GRAY);
                                currentPost.setUpvoted(false);
                                currentPost.setDownvoted(false);
                                notifyDataSetChanged();
                              }}
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
                                      currentPost.setVotesCount(newvotes);
                                      if(!currentPost.isDownvoted()&&!currentPost.isUpvoted())//not voted before
                                      { down.setTextColor(Color.RED);
                                          currentPost.setDownvoted(true);
                                      notifyDataSetChanged();}
                                      else {
                                          if(currentPost.isUpvoted()){
                                              //was up and down clicked
                                               up.setTextColor(Color.GRAY);
                                                  currentPost.setUpvoted(false);
                                              down.setTextColor(Color.RED);
                                              currentPost.setDownvoted(true);
                                          notifyDataSetChanged();}
                                          else //was down & down clicked
                                          {
                                              down.setTextColor(Color.GRAY);
                                              currentPost.setUpvoted(false);
                                              currentPost.setDownvoted(false);
                                              notifyDataSetChanged();
                                          }}
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
        Picasso.get().load(tempPost.getImageURL()).resize(500, 282).into(uploadedImage);
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
    StringRequest stringRequest = new StringRequest(Request.Method.POST, Routes.vote,
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
                                    context,
                                    "something went wrong.. try again",
                                    Toast.LENGTH_SHORT)
                                    .show();
                          }
                        } catch (JSONException e) {
                          e.printStackTrace();
                        }
                      }
                    }, new Response.ErrorListener() {@Override
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
    StringRequest stringRequest = new StringRequest(Request.Method.POST,Routes.hidePost, new Response.Listener<String>() {
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
    public void reportPost(String postID, int method, JSONObject jsonValue, final VolleyCallback callback, final String reason){
        final String postId=postID;
        User user = SharedPrefmanager.getInstance(context).getUser();
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
                        params.put("content",reason);
                        return params;
                    }
                };
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }
    public void deletePost(String postID, int method, JSONObject jsonValue, final VolleyCallback callback,final String postname){
        User user = SharedPrefmanager.getInstance(context).getUser();
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
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }
    // Get the x and y position after the button is draw on screen
    // (It's important to note that we can't get the position in the onCreate(),
    // because at that stage most probably the view isn't drawn yet, so it will return (0, 0))

    // The method that displays the popup.
    private void showPopup(final Activity context, Point p) {
        Display display = context.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int popupWidth = size.x;
        double y = 0.6 * size.y;
        int popupHeight = (int) y;
        // Inflate the popup_layout.xml
        RelativeLayout viewGroup = (RelativeLayout) context.findViewById(R.id.select_option_menu);
        LayoutInflater layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.view_user_profile, viewGroup);//TODO change layout

        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(popupWidth);
        popup.setHeight(popupHeight);
        popup.setFocusable(true);

        // Some offset to align the popup a bit to the right, and a bit down, relative to button's
        // position.
        int OFFSET_X = -60;
        int OFFSET_Y = 90;

        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);

        TextView viewProfileButton=(TextView) layout.findViewById(R.id.viewUserProfileData);
        viewProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,ExploreUserProfile.class);
                i.putExtra("username",userProfileToExplore);
                context.startActivity(i);
            }
        });
        TextView sendMessageToUser=(TextView) layout.findViewById(R.id.SendUserMessage);
        sendMessageToUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,WriteMessage.class);
                //i.putExtra("username",userProfileToExplore);
                context.startActivity(i);
            }
        });
        TextView blockThisUser=(TextView) layout.findViewById(R.id.blockuseroption);
        blockThisUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Come on next version",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
