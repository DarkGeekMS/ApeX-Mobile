package com.example.android.apexware;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * this class is to handle the data of the comments to show it in the list
 * @author Omar
 */
public class CustomAdapterForComments extends BaseExpandableListAdapter {
  private final Activity context;
  private List<Comment> replies;
  private HashMap<Comment, List<Comment>> listHashMap;
  private List<Comment> commentList;

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
    if (convertView == null)
      convertView = LayoutInflater.from(context).inflate(R.layout.commentview, parent, false);
    Comment currentComment = commentList.get(groupPosition);
    TextView commentOwnerandDte =
        (TextView) convertView.findViewById(R.id.commentOwnerNameAndTimeCreated);
    commentOwnerandDte.setText(
        currentComment.getCommentOwner() + "/" + currentComment.getCommentCreateDate());
    TextView commentContent = convertView.findViewById(R.id.commentContents);
    commentContent.setText(currentComment.getCommentContent());
    View tempView = convertView;

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
    Comment currentComment = replies.get(childPosition);
    TextView commentOwnerandDte =
        (TextView) convertView.findViewById(R.id.replyOwnerNameAndTimeCreated);
    commentOwnerandDte.setText(
        currentComment.getCommentOwner() + "/" + currentComment.getCommentCreateDate());
    TextView commentContent = convertView.findViewById(R.id.replyContents);
    commentContent.setText(currentComment.getCommentContent());
    View tempView = convertView;
    return convertView;
  }

  @Override
  public boolean isChildSelectable(int groupPosition, int childPosition) {
    return true;
  }
}
