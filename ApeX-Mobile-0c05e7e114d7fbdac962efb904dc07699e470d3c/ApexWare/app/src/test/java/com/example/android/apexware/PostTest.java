package com.example.android.apexware;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class PostTest {

    @Test
    public void getVideoId() {
        String url="https://www.youtube.com/watch?v=slDvh3NcpNI";
        String id="slDvh3NcpNI";
        Post post=new Post();
        Assert.assertEquals(post.getVideoId(url),id);
    }


}