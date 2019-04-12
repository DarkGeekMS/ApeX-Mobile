package com.example.android.apexware;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * this class is the actual class that deals with the api side and overrides the interface methods
 * to send correct requests to the back end server
 *
 * @author mostafa, mazen
 */
public class RestService extends AppCompatActivity implements DataSupplier {
  /**
   * @param username : the user name field in the form
   * @param password : the password field in the form
   * @return true if login successful
   */
  @Override
  public boolean login_interface(final String username, final String password) {
    return true; // for debug

  }

  @Override
  public boolean signup_interface(String username, String email, String password) {
    return true; // for debug
  }
}
