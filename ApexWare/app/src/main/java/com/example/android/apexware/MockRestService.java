package com.example.android.apexware;

/**
 * this class is a simulation to the back end service that sends dummy objects and responses to the
 * activities to debug without getting affected with the bacck end and connection proplems
 *
 * @author mostafa
 */
public class MockRestService implements DataSupplier {

  /**
   * this function only checks if rhe sent parameters are empty strings or no and whatever their
   * values are it returns true
   *
   * @param username : the user name field in the form
   * @param password : the password field in the form
   * @return : true if the parameters are not empty strings
   */
  @Override
  public boolean login_interface(String username, String password) {
    if (username.equals("") && password.equals("")) return false;
    return true;
  }

  /**
   * this function only checks if rhe sent parameters are empty strings or no and whatever their
   * values are it returns true
   *
   * @param username : the user name field in the form
   * @param email : the user email field in the form
   * @param password : the password field in the form
   * @return true if the parameters are not empty strings
   */
  @Override
  public boolean signup_interface(String username, String email, String password) {
    if (username.equals("") || password.equals("") || email.equals(" ")) return false;
    return true;
  }
}
