package com.example.android.apexware;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/** here for this class we are using a singleton pattern */
public class SharedPrefmanager {

  // the constants
  private static final String SHARED_PREF_NAME = "simplifiedcodingsharedpref";
  private static final String KEY_USERNAME = "keyusername";
  private static final String KEY_EMAIL = "keyemail";
  private static final String KEY_Token = "keygender";
  private static final String KEY_ID = "keyid";

  private static SharedPrefmanager mInstance;
  private static Context mCtx;

  /** This is private constructor to make a new object */
  private SharedPrefmanager(Context context) {
    mCtx = context;
  }

  /**
   * This is public constructor that check if an object is created and return the same on if it is
   * created and return new if now one is created
   */
  public static synchronized SharedPrefmanager getInstance(Context context) {
    if (mInstance == null) {
      mInstance = new SharedPrefmanager(context);
    }
    return mInstance;
  }
  /** method to let the user login this method will store the user data in shared preferences */
  public void userLogin(User user) {
    SharedPreferences sharedPreferences =
        mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(KEY_ID, user.getId());
    editor.putString(KEY_USERNAME, user.getUsername());
    editor.putString(KEY_EMAIL, user.getEmail());
    editor.putString(KEY_Token, user.getToken());
    editor.apply();
  }
  /** this method will checker whether user is already logged in or not */
  public boolean isLoggedIn() {
    SharedPreferences sharedPreferences =
        mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    return sharedPreferences.getString(KEY_USERNAME, null) != null;
  }

  /** this method will give the logged in user */
  public User getUser() {
    SharedPreferences sharedPreferences =
        mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    return new User(sharedPreferences.getString(KEY_Token, null),sharedPreferences.getString(KEY_USERNAME,null));
  }
  public String getToken()
  {
    return KEY_Token;
  }

  //this method will logout the user
  public void logout() {
      SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
      SharedPreferences.Editor editor = sharedPreferences.edit();
      editor.clear();
      editor.apply();
      mCtx.startActivity(new Intent(mCtx, MainActivity.class));
  }
}
