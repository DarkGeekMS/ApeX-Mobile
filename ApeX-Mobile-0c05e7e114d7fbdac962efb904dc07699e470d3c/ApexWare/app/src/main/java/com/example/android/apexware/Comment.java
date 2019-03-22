package com.example.android.apexware;

public class Comment {



    public int Id;
    public int postId;
    public String commentOwner=null;
    public String commentContent;
    public int commentCreateDate = 0;

    /**
     * default constructor*/
    public Comment(int postId){}

    /**get functions*/
    /**
     *this method is to get comment id for Id
     *
     * */
    public int getId() {
        return Id;
    }

    /**
     *this method is to get post id for postId
     *
     * */
    public int getPostId() {
        return postId;
    }
    /**
     *this method is to get comment owner name for commentowner
     *
     * */
    public String getCommentOwner() {
        return commentOwner;
    }

    /**
     *this method is to get comment content/text name for commentContent
     *
     * */
    public String getCommentContent() {
        return commentContent;
    }
    /**
     *this method is to get comment create date name for commentCreateDate
     *
     * */
    public int getCommentCreateDate() {
        return commentCreateDate;
    }
    /**
     * set functions
     * */
/**
 * this method is to set comment id
 * @param id
 * */


    public void setId(int id) {
        Id = id;
    }
    /**
     * this method is to set post id
     * @param postId
     * */
    public void setPostId(int postId) {
        this.postId = postId;
    }
    /**
     * this method is to set commentOwner
     * @param commentOwner
     * */
    public void setCommentOwner(String commentOwner) {
        this.commentOwner = commentOwner;
    }
    /**
     * this method is to set comment content
     * @param commentContent
     * */
    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
    /**
     * this method is to set comment date
     * @param commentCreateDate
     * */
    public void setCommentCreateDate(int commentCreateDate) {
        this.commentCreateDate = commentCreateDate;
    }

}
