package com.example.android.apexware;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * this class tests the implementation of the interface functions on the mock up side
 *
 * @author mostafa
 */
public class MockRestServiceTest {
  private MockRestService restClient = new MockRestService();
  /**
   * check that the empty strings will always return false if the data suppier is the mock service
   */
  @Test
  public void login_interface_false() {
    assertFalse(restClient.login_interface("", ""));
  }
  /**
   * check that a random dummy user will always return true if the data suppier is the mock service
   */
  @Test
  public void login_interface_true() {
    assertTrue(restClient.login_interface("mostafa", "1234"));
  }
  /**
   * check that the empty strings will always return false if the data suppier is the mock service
   */
  @Test
  public void signup_interface_false() {
    assertFalse(restClient.signup_interface("", "", ""));
  }
  /**
   * check that a random dummy user will always return true if the data suppier is the mock service
   */
  @Test
  public void signup_interface_true() {
    assertTrue(restClient.signup_interface("mostafa", "mostafa@gmail.com", "1234"));
  }
}
