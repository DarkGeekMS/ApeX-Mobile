package com.example.android.apexware;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * this class is to save the data of comments and replys of the post
 * @author Omar
 */
public class Comment implements Parcelable {
  /** data members */
  private String Id;
  private String postId;
  private String commentOwner = null;
  private String commentContent;
  private String  commentCreateDate ;
  private boolean Upvoted=false;
  private boolean Downvoted=false;
  private String parentID=null;

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    protected Comment(Parcel in) {
        Id = in.readString();
        postId = in.readString();
        commentOwner = in.readString();
        commentContent = in.readString();
        commentCreateDate = in.readString();
        Upvoted = in.readByte() != 0;
        Downvoted = in.readByte() != 0;
        votesCount = in.readInt();
        parentID=in.readString();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    public boolean isUpvoted() {
    return Upvoted;
  }

  public void setUpvoted(boolean upvoted) {
    Upvoted = upvoted;
  }

  public boolean isDownvoted() {
    return Downvoted;
  }

  public void setDownvoted(boolean downvoted) {
    Downvoted = downvoted;
  }

  private int votesCount;
  /** default constructor */
  public Comment(String postId) {
    this.postId = postId;
  }

  /** get functions */
  /** this method is to get comment id for Id */
  public String getId() {
    return Id;
  }

  /** this method is to get post id for postId */
  public String getPostId() {
    return postId;
  }

  /** this method is to get comment owner name for commentowner */
  public String getCommentOwner() {
    return commentOwner;
  }

  /** this method is to get comment content/text name for commentContent */
  public String getCommentContent() {
    return commentContent;
  }

  public int getVotesCount() {
    return votesCount;
  }

  /** this method is to get comment create date name for commentCreateDate */
  public String getCommentCreateDate() {
    return commentCreateDate;
  }
  /** set functions */
  /**
   * this method is to set comment id
   *
   * @param id
   */
  public void setId(String id) {
    Id = id;
  }

  /**
   * this method is to set post id
   *
   * @param postId
   */
  public void setPostId(String postId) {
    this.postId = postId;
  }

  /**
   * this method is to set commentOwner
   *
   * @param commentOwner
   */
  public void setCommentOwner(String commentOwner) {
    this.commentOwner = commentOwner;
  }

  /**
   * this method is to set comment content
   *
   * @param commentContent
   */
  public void setCommentContent(String commentContent) {
    this.commentContent = commentContent;
  }

  /**
   * this method is to set comment date
   *
   * @param commentCreateDate
   */
  public void setCommentCreateDate(String commentCreateDate) {
    this.commentCreateDate = commentCreateDate;
  }

  public void setVotesCount(int votesCount) {
    this.votesCount = votesCount;
  }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Id);
        dest.writeString(postId);
        dest.writeString(commentOwner);
        dest.writeString(commentContent);
        dest.writeString(commentCreateDate);
        dest.writeByte((byte) (Upvoted ? 1 : 0));
        dest.writeByte((byte) (Downvoted ? 1 : 0));
        dest.writeInt(votesCount);
        dest.writeString(parentID);
    }
}
