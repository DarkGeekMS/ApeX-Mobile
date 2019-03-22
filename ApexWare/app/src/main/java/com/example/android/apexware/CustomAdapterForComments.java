package com.example.android.apexware;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class CustomAdapterForComments extends BaseExpandableListAdapter {
    private final Activity context;
    private List<Comment> replies;
    private HashMap<Comment,List<Comment>> listHashMap;
    private  List<Comment>commentList;
    public CustomAdapterForComments(Activity context, List<Comment> replies, HashMap<Comment, List<Comment>> listHashMap, List<Comment>commentList) {
        this.context = context;
        this.replies = replies;
        this.listHashMap = listHashMap;
        this.commentList=commentList;
    }

    @Override
    public int getGroupCount() {
        return commentList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listHashMap.get(commentList.get(groupPosition)).size() ;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return replies.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listHashMap.get(replies.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.commentview, parent, false);
        Comment currentComment = commentList.get(groupPosition);
        TextView commentOwnerandDte= (TextView)convertView.findViewById(R.id.commentOwnerNameAndTimeCreated);
        commentOwnerandDte.setText(currentComment.getCommentOwner()+"/"+currentComment.getCommentCreateDate());
        TextView commentContent= convertView.findViewById(R.id.commentContents);
        commentContent.setText(currentComment.getCommentContent());
        View tempView = convertView;

        return  convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.replyview, parent, false);
        Comment currentComment = replies.get(childPosition);
        TextView commentOwnerandDte= (TextView)convertView.findViewById(R.id.replyOwnerNameAndTimeCreated);
        commentOwnerandDte.setText(currentComment.getCommentOwner()+"/"+currentComment.getCommentCreateDate());
        TextView commentContent= convertView.findViewById(R.id.replyContents);
        commentContent.setText(currentComment.getCommentContent());
        View tempView = convertView;
        return  convertView;    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    }
