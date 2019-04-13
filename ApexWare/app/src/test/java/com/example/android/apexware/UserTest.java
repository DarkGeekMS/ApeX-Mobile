package com.example.android.apexware;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
  User user=new User( "23jknk1","mazen","mawdlkw@hotmail.com", "dwdjwefnewfnoewifneowfineifn");

  @Test
  public void getId() {
      Assert.assertEquals(user.getId(),"23jknk1");
  }

  @Test
  public void getUsername() {
      Assert.assertEquals(user.getUsername(),"mazen");
  }

  @Test
  public void getEmail() {
      Assert.assertEquals(user.getEmail(),"mawdlkw@hotmail.com");
  }

  @Test
  public void getToken() {
      Assert.assertEquals(user.getToken(),"dwdjwefnewfnoewifneowfineifn");
  }
}