package com.example.android.apexware;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)

public class testForComments {
    @Test
    public void setPostIdIsCorrect(){
        Comment c1=new Comment( 9);
      int i=  c1.getPostId();
      assertEquals ("post id is wrong",9,i,0.0);
    }

    @Test
    public  void setCommentIdIsCorrect(){
        Comment c1=new Comment( 9);
        c1.setId(11);
        int i=  c1.getId();
        assertEquals ("post id is wrong",11,i,0.0);
    }
    @Test
    public  void setCommentOwnerIsCorrect(){
        Comment c1=new Comment( 9);
        c1.setCommentOwner("omar");
        String i=  c1.getCommentOwner();
        assertEquals("not correct",i,"omar");
    }

    @Test
    public  void setCommentcontentIsCorrect(){
        Comment c1=new Comment( 9);
        c1.setCommentContent("this is content");
        String i=  c1.getCommentContent();
        assertEquals("not correct",i,"this is content");
    }

    @Test
    public  void setCommentcreatedateIsCorrect(){
        Comment c1=new Comment( 9);
        c1.setCommentCreateDate(12);
        int i=  c1.getCommentCreateDate();
        assertEquals("not correct",i,12);
    }
}
