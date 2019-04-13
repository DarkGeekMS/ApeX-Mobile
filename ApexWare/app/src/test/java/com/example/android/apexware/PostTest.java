package com.example.android.apexware;

import org.checkerframework.checker.units.qual.A;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class PostTest {
    Post post=new Post();
    /**
     * this method tests the set and get functions of the voting state(upvoting)
     */

  @Test
  public void isUpvoted() {
      post.setUpvoted(true);
      Assert.assertEquals(post.isUpvoted(),true);
  }
    /**
     * this method tests the set and get functions of the voting state(downvoting)
     */
  @Test
  public void isDownvoted() {
      post.setDownvoted(true);
      Assert.assertEquals(post.isDownvoted(),true);
  }
    /**
     * this method tests the set and get functions of ApexcomName
     */
  @Test
  public void setApexcomName() {
      post.setApexcomName("asdjkiqwdjnijwd");
      Assert.assertEquals(post.getApexcomName(),"asdjkiqwdjnijwd");
  }
    /**
     * this method tests the set and get functions of image URL
     */
  @Test
  public void setImageURL() {
      post.setImageURL("http://www,cmp.com/wjeiwdu");
      Assert.assertEquals(post.getImageURL(),"http://www,cmp.com/wjeiwdu");
  }

  @Test
  public void setPostCreateDate() {
      post.setPostCreateDate(22);
      Assert.assertEquals(post.getPostCreateDate(),22);
  }
    /**
     * this method tests the set and get functions of post owner
     */
  @Test
  public void setPostOwner() {
      post.setPostOwner("John Arthur");
      Assert.assertEquals(post.getPostOwner(),"John Arthur");
  }
    /**
     * this method tests the set and get functions of post Title
     */
  @Test
  public void setPostTitle() {
      post.setPostTitle("Title is good");
      Assert.assertEquals(post.getPostTitle(),"Title is good");
  }
    /**
     * this method tests the set and get functions of postType
     */
  @Test
  public void setPostType() {
      post.setPostType(2);
      Assert.assertEquals(post.getPostType(),2);
  }
    /**
     * this method tests the set and get functions of video URL
     */
  @Test
  public void setVideoURL() {
      post.setVideoURL("http://2328038.232j3bhi789qe7f98");
      Assert.assertEquals(post.getVideoURL(),"http://2328038.232j3bhi789qe7f98");
  }
    /**
     * this method tests the set and get functions of ApexComLogo
     */
  @Test
  public void setApexcomLogo() {
      post.setApexcomLogo("hehwefuweyhfoefouew");
      Assert.assertEquals(post.getApexcomLogo(),"hehwefuweyhfoefouew");
  }

  @Test
  public void setTextPostcontent() {
      post.setTextPostcontent("djqewbfueiwbfueiwgfyewgfuewgfuewgfiuwgefoigewfueiwgfueiwgf37t3gfo");
      Assert.assertEquals(post.getTextPostcontent(),"djqewbfueiwbfueiwgfyewgfuewgfuewgfiuwgefoigewfueiwgfueiwgf37t3gfo");
  }

  @Test
  public void setPostId() {
      post.setPostId("546");
      Assert.assertEquals(post.getPostId(),546);
  }

    /**
     * this method tests the set and get functions of videoID
     */
  @Test
  public void getVideoId() {
    String url="https://www.youtube.com/watch?v=slDvh3NcpNI";
    String id="slDvh3NcpNI";
    Post post=new Post();
    Assert.assertEquals(post.getVideoId(url),id);
  }
}