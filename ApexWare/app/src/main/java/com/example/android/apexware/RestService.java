package com.example.android.apexware;

/**
 * this class is the actual class that deals with the api side and overrides the interface methods
 * to send correct requests to the back end server
 *
 * @author mostafa
 */
public class RestService implements DataSupplier {

  /**
   * @param username : the user name field in the form
   * @param password : the password field in the form
   * @return false always uptil now
   */
  @Override
  public boolean login_interface(String username, String password) {
    return false;
  }

  /**
   * @param username : the user name field in the form
   * @param email : the user email field in the form
   * @param password : the password field in the form
   * @return false always uptil now
   */
  @Override
  public boolean signup_interface(String username, String email, String password) {
    return false;
  }
}
