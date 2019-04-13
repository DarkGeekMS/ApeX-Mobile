package com.example.android.apexware;

import android.content.Context;

/**
 * this class is a base class that 2 kinds of services inherit it it contain methods that only call
 * the interface methods using the inherited supplier
 *
 * @author mostafa
 */
public class DepandantClass {

  private final DataSupplier mSupplier;
  /**
   * constructor of the class with one of its inherited class objects
   *
   * @param dataSupplier : the type of supplier that will use the virtual functions in this class
   */
  public DepandantClass(DataSupplier dataSupplier) {
    mSupplier = dataSupplier;
  }

  /** @return : return true if login successful and user exists */
  boolean login(String username, String password,final Context context) {
    return mSupplier.login_interface(username, password,context);
  }
  /**
   * send string of all details to create a new user
   *
   * @return : return true if it is successful
   */
  boolean signup(String username, String email, String password) {
    return mSupplier.signup_interface(username, email, password);
  }
  // msupplier can be used at this point to call any function there in a generic use

}
