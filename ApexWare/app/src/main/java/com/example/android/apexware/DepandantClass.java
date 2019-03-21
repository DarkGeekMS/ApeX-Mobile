package com.example.android.apexware;

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
  /**
   * @param username
   * @param password
   * @return : return true if login successful and user exists
   */
  boolean login(String username, String password) {
    return mSupplier.login_interface(username, password);
  }

  /**
   * send string of all details to create a new user
   *
   * @param username
   * @param email
   * @param password
   * @return : return true if it is succesful
   */
  boolean signup(String username, String email, String password) {
    return mSupplier.signup_interface(username, email, password);
  }
}
