package com.example.android.apexware;

/**
 * this class is to save the data of comments and replys of the post
 * @author Omar
 */
public class Comment {

  /** data members */
  private int Id;

  private int postId;
  private String commentOwner = null;
  private String commentContent;
  private int commentCreateDate = 0;

  private int votesCount;
  /** default constructor */
  public Comment(int postId) {
    this.postId = postId;
  }

  /** get functions */
  /** this method is to get comment id for Id */
  public int getId() {
    return Id;
  }

  /** this method is to get post id for postId */
  public int getPostId() {
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
  public int getCommentCreateDate() {
    return commentCreateDate;
  }
  /** set functions */
  /**
   * this method is to set comment id
   *
   * @param id
   */
  public void setId(int id) {
    Id = id;
  }

  /**
   * this method is to set post id
   *
   * @param postId
   */
  public void setPostId(int postId) {
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
  public void setCommentCreateDate(int commentCreateDate) {
    this.commentCreateDate = commentCreateDate;
  }

  public void setVotesCount(int votesCount) {
    this.votesCount = votesCount;
  }
}
