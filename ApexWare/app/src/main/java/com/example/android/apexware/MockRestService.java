package com.example.android.apexware;

// simulation to the back end service that sends dummy objects to debug
public class MockRestService implements DataSupplier {

  @Override
  public boolean login_interface(String username, String password) {
    if (username.equals("") || password.equals("")) return false;
    return true;
  }

  @Override
  public boolean signup_interface(String username, String email, String password) {
    if (username.equals("") || password.equals("") || email.equals(" ")) return false;
    return true;
  }
}
