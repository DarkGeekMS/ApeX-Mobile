package com.example.android.apexware;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * this class is to handle the data of the comments to show it in the list
 * @author Omar
 */
public class CustomAdapterForComments extends BaseExpandableListAdapter {
  private final Activity context;
  private List<Comment> replies;
  private HashMap<Comment, List<Comment>> listHashMap;
  private List<Comment> commentList;
   String value;
  public CustomAdapterForComments(
      Activity context,
      List<Comment> replies,
      HashMap<Comment, List<Comment>> listHashMap,
      List<Comment> commentList) {
    this.context = context;
    this.replies = replies;
    this.listHashMap = listHashMap;
    this.commentList = commentList;
  }

  /** this method gets the size of items on the list,which is comments number */
  @Override
  public int getGroupCount() {
    return commentList.size();
  }

  /**
   * this method gets number of childes,which is replies numbers
   *
   * @param groupPosition
   * @return
   */
  @Override
  public int getChildrenCount(int groupPosition) {
    return listHashMap.get(commentList.get(groupPosition)).size();
  }

  /**
   * this method gets the group/comment by position
   *
   * @param groupPosition
   * @return
   */
  @Override
  public Object getGroup(int groupPosition) {
    return replies.get(groupPosition);
  }

  /**
   * this method gets the child/reply by position of it and the parent
   *
   * @param groupPosition
   * @param childPosition
   * @return
   */
  @Override
  public Object getChild(int groupPosition, int childPosition) {
    return listHashMap.get(replies.get(groupPosition)).get(childPosition);
  }

  /**
   * this method gets the ID of the group/comment
   *
   * @param groupPosition
   * @return
   */
  @Override
  public long getGroupId(int groupPosition) {
    return groupPosition;
  }

  /**
   * this method gets child/reply ID
   *
   * @param groupPosition
   * @param childPosition
   * @return
   */
  @Override
  public long getChildId(int groupPosition, int childPosition) {
    return childPosition;
  }

  @Override
  public boolean hasStableIds() {
    return false;
  }

  /**
   * this method is to get the appearance of the group/comment
   *
   * @param groupPosition
   * @param isExpanded
   * @param convertView
   * @param parent
   * @return
   */
  @Override
  public View getGroupView(
      int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
      // get user token
      final User user = SharedPrefmanager.getInstance(context).getUser();
      final String token = user.getToken();
    if (convertView == null)
      convertView = LayoutInflater.from(context).inflate(R.layout.commentview, parent, false);
   final Comment currentComment = commentList.get(groupPosition);
    TextView commentOwnerandDte =
        (TextView) convertView.findViewById(R.id.commentOwnerNameAndTimeCreated);
    commentOwnerandDte.setText(
        currentComment.getCommentOwner() + "/" + currentComment.getCommentCreateDate());
    TextView commentContent = convertView.findViewById(R.id.commentContents);
    commentContent.setText(currentComment.getCommentContent());
    View tempView = convertView;

    final Button upcomment = convertView.findViewById(R.id.upvotecomment);
    final Button downcomment = convertView.findViewById(R.id.downvotecomment);
    final TextView votecounterforcomment = convertView.findViewById(R.id.votecounterforcomment);
    upcomment.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          final int currentvotes = Integer.parseInt(votecounterforcomment.getText().toString());
          upVote(currentComment.getId(),Request.Method.POST, null,
                  new  VolleyCallback(){
                      @Override
                      public void onSuccessResponse(String result) {
                          try {
                              JSONObject response = new JSONObject(result);
                              value=response.getString("votes");
                              votecounterforcomment.setText(value);
                              int newvotes=Integer.parseInt(value);
                              if(newvotes>currentvotes){
                                  if(newvotes==currentvotes+2)//was downvoted and upvote clicked
                                  { downcomment.setTextColor(Color.GRAY);
                                      currentComment.setDownvoted(false);}
                                  upcomment.setTextColor(Color.BLUE);
                                  currentComment.setUpvoted(true);}
                              else if (newvotes<currentvotes)//was upvoted & upvote clicked
                              {
                                  upcomment.setTextColor(Color.GRAY);
                                  currentComment.setUpvoted(false);
                              }
                          } catch (JSONException e) {
                              e.printStackTrace();
                          }
                      }
                  });
      }
    });

    downcomment.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final int currentvotes = Integer.parseInt(votecounterforcomment.getText().toString());
            downVote(currentComment.getId(),Request.Method.GET, null,
                    new  VolleyCallback(){
                        @Override
                        public void onSuccessResponse(String result) {
                            try {
                                JSONObject response = new JSONObject(result);
                                value=response.getString("votes");
                                votecounterforcomment.setText(value);
                                int newvotes=Integer.parseInt(value);
                                if(newvotes<currentvotes){
                                    if(newvotes==currentvotes-2)//was upvoted and downvote clicked
                                    { upcomment.setTextColor(Color.GRAY);
                                        currentComment.setUpvoted(false);}
                                    downcomment.setTextColor(Color.RED);
                                    currentComment.setDownvoted(true);}
                                else if (newvotes>currentvotes)//was downvoted & downvote clicked(cancel downvote)
                                {
                                    downcomment.setTextColor(Color.GRAY);
                                    currentComment.setDownvoted(false);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
    });
      final Button commentoptions=convertView.findViewById(R.id.commentoptions);
      commentoptions.setOnClickListener(
              new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      PopupMenu popup = new PopupMenu(context, commentoptions);
                      if(currentComment.getCommentOwner()==user.getUsername())//if it is my comment
                      {
                          popup.getMenuInflater().inflate(R.menu.mycommentoptions, popup.getMenu());
                          popup.setOnMenuItemClickListener(
                                  new PopupMenu.OnMenuItemClickListener() {
                                      @Override
                                      public boolean onMenuItemClick(MenuItem item) {
                                          if(item.getItemId()==R.id.EditMyComment){

                                          }
                                          // we can use item name to make intent for the new responces
                                          if(item.getItemId()==R.id.SaveMyComment){

                                          }
                                          if(item.getItemId()==R.id.DeleteMyComment){

                                          }
                                          return true;
                                      }
                                  });
                          popup.show(); // showing popup menu}
                      }else{//if it`s others comment
                          popup.getMenuInflater().inflate(R.menu.commentoptions, popup.getMenu());
                          popup.setOnMenuItemClickListener(
                                  new PopupMenu.OnMenuItemClickListener() {
                                      @Override
                                      public boolean onMenuItemClick(MenuItem item) {
                                          if(item.getItemId()==R.id.Savecomment){}
                                          // we can use item name to make intent for the new responces
                                          if(item.getItemId()==R.id.reportcomment){
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
                                                      Toast.makeText(context,"comment is reported",Toast.LENGTH_SHORT).show();

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
                          popup.show(); // showing popup menu}
                      }
                  }
              });
      final Button replytocomment=convertView.findViewById(R.id.commentreply);
      replytocomment.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(context,replytocomment.class);
              Gson gson = new Gson();
              String comment = gson.toJson(currentComment);
              intent.putExtra("Comment", comment); // sending the post to next activity
              context.startActivity(intent);
          }
      });

    return convertView;
  }

  /**
   * this method is to get the appearance of the child/reply
   *
   * @param groupPosition
   * @param childPosition
   * @param isLastChild
   * @param convertView
   * @param parent
   * @return
   */
  @Override
  public View getChildView(
      int groupPosition,
      int childPosition,
      boolean isLastChild,
      View convertView,
      ViewGroup parent) {
    if (convertView == null)
      convertView = LayoutInflater.from(context).inflate(R.layout.replyview, parent, false);
   final Comment currentComment = replies.get(childPosition);
    TextView commentOwnerandDte =convertView.findViewById(R.id.replyOwnerNameAndTimeCreated);
    commentOwnerandDte.setText(
        currentComment.getCommentOwner() + "/" + currentComment.getCommentCreateDate());
    TextView commentContent = convertView.findViewById(R.id.replyContents);
    commentContent.setText(currentComment.getCommentContent());
    View tempView = convertView;

      final Button upreply = convertView.findViewById(R.id.upvotereply);
      final Button downreply = convertView.findViewById(R.id.downvotereply);
      final TextView votecounterforcomment = convertView.findViewById(R.id.votecounterforreply);
      upreply.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              final int currentvotes = Integer.parseInt(votecounterforcomment.getText().toString());
              upVote(currentComment.getId(),Request.Method.POST, null,
                      new  VolleyCallback(){
                          @Override
                          public void onSuccessResponse(String result) {
                              try {
                                  JSONObject response = new JSONObject(result);
                                  value=response.getString("votes");
                                  votecounterforcomment.setText(value);
                                  int newvotes=Integer.parseInt(value);
                                  if(newvotes>currentvotes){
                                      if(newvotes==currentvotes+2)//was downvoted and upvote clicked
                                      { downreply.setTextColor(Color.GRAY);
                                          currentComment.setDownvoted(false);}
                                      upreply.setTextColor(Color.BLUE);
                                      currentComment.setUpvoted(true);}
                                  else if (newvotes<currentvotes)//was upvoted & upvote clicked
                                  {
                                      upreply.setTextColor(Color.GRAY);
                                      currentComment.setUpvoted(false);
                                  }
                              } catch (JSONException e) {
                                  e.printStackTrace();
                              }
                          }
                      });
          }
      });

      downreply.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              final int currentvotes = Integer.parseInt(votecounterforcomment.getText().toString());
              downVote(currentComment.getId(),Request.Method.GET, null,
                      new  VolleyCallback(){
                          @Override
                          public void onSuccessResponse(String result) {
                              try {
                                  JSONObject response = new JSONObject(result);
                                  value=response.getString("votes");
                                  votecounterforcomment.setText(value);
                                  int newvotes=Integer.parseInt(value);
                                  if(newvotes<currentvotes){
                                      if(newvotes==currentvotes-2)//was upvoted and downvote clicked
                                      { upreply.setTextColor(Color.GRAY);
                                          currentComment.setUpvoted(false);}
                                      downreply.setTextColor(Color.RED);
                                      currentComment.setDownvoted(true);}
                                  else if (newvotes>currentvotes)//was downvoted & downvote clicked(cancel downvote)
                                  {
                                      downreply.setTextColor(Color.GRAY);
                                      currentComment.setDownvoted(false);
                                  }
                              } catch (JSONException e) {
                                  e.printStackTrace();
                              }
                          }
                      });
          }
      });


      return convertView;
  }

  @Override
  public boolean isChildSelectable(int groupPosition, int childPosition) {
    return true;
  }

    /**
     * upvote request for comment/reply
     * @param ID
     * @param method
     * @param jsonValue
     * @param callback
     */
  public void upVote(String ID, int method, JSONObject jsonValue, final VolleyCallback callback){
        final String Id=ID;
        User user = SharedPrefmanager.getInstance(context).getUser();
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
                        params.put("name", Id);
                        params.put("dir", "1");
                        params.put("token", token);
                        return params;
                    }
                };
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    /**
     * downvote request for comment/reply
     * @param ID
     * @param method
     * @param jsonValue
     * @param callback
     */
    public void downVote(String ID,int method, JSONObject jsonValue, final VolleyCallback callback){
        final String Id=ID;
        User user = SharedPrefmanager.getInstance(context).getUser();
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
                        params.put("name", Id);
                        params.put("dir", "-1");
                        params.put("token", token);
                        return params;
                    }
                };
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }
}

