package com.example.android.apexware;

import android.content.Context;

/**
 * this class is a base class that 2 kinds of services inherit it it contain methods that only call
 * the interface methods using the inherited supplier
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
  boolean login(String username, String password, final Context context) {
    return mSupplier.login_interface(username, password, context);
  }
  /**
   * sends string of all details to create a new user
   *
   * @return : return true if it is successful
   */
  boolean signup(String username, String email, String password) {
    return mSupplier.signup_interface(username, email, password);
  }

  /** @return : return true if post was published seccessfully */
  boolean createPost(String type, String title, String mainPost, int communityID) {
    return mSupplier.createPost_interface(type, title, mainPost, communityID);
  }

  void verify_forget_pass(String email, String username,final Context context) {
    mSupplier.verify_forget_pass_interface(email, username,context);
  }

  void verify_forget_user(String email, String password,final Context context) {
    mSupplier.verify_forget_user_interface(email, password,context);
  }
}
