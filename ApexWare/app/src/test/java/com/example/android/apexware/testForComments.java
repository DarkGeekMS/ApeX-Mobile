package com.example.android.apexware;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;

//@RunWith(RobolectricTestRunner.class)


public class testForComments {
    /**
     * this method tests the set and get functions of postId
     */
    @Test
    public void setPostIdIsCorrect(){
        Comment c1=new Comment( "9");
      String i=  c1.getPostId();
        Assert.assertEquals(i,"9");
    }
    /**
     * this method tests the set and get functions of comment id
     */

    @Test
    public  void setCommentIdIsCorrect(){
        Comment c1=new Comment( "9");
        c1.setId("11");
        String i=  c1.getId();
        Assert.assertEquals (i,"11");
    }
    /**
     * this method tests the set and get functions of comment owner
     */

    @Test
    public  void setCommentOwnerIsCorrect(){
        Comment c1=new Comment( "9");
        c1.setCommentOwner("omar");
        String i=  c1.getCommentOwner();
        Assert.assertEquals("not correct",i,"omar");
    }
    /**
     * this method tests the set and get functions of comment content/body
     */

    @Test
    public  void setCommentcontentIsCorrect(){
        Comment c1=new Comment( "9");
        c1.setCommentContent("this is content");
        String i=  c1.getCommentContent();
        Assert.assertEquals("not correct",i,"this is content");
    }
    /**
     * this method tests the set and get functions of comment create date
     */

    @Test
    public  void setCommentcreatedateIsCorrect(){
        Comment c1=new Comment( "9");
        c1.setCommentCreateDate(12);
        int i=  c1.getCommentCreateDate();
        Assert.assertEquals("not correct",i,12);
    }
}
