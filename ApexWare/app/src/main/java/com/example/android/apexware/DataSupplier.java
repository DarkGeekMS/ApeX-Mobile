package com.example.android.apexware;

public interface DataSupplier {

  // return true if login successful and false if failed
  boolean login_interface(String username, String password);

  // send string of all details to create a new user
  boolean signup_interface(String username, String email, String password);
}
