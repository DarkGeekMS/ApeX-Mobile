package com.example.android.apexware;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;

public class NewPass extends AppCompatActivity {
  String username;
  String code;
  EditText new_pass;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_new_pass);

    new_pass = (EditText) findViewById(R.id.new_pass);
    Intent i = getIntent();
    username = i.getStringExtra("username");
    code = i.getStringExtra("code");
  }

  public void save(View view) {
    String password = new_pass.getText().toString().trim();
    if (verified()) {
      getResponse5(
          Request.Method.PATCH,
          Routes.changePassword,
          null,
          new VolleyCallback() {
            @Override
            public void onSuccessResponse(String response) {
              try {
                // converting response to json object
                JSONObject obj = new JSONObject(response);

                // if no error in response
                if (response != null) {
                  Log.d(TAG, "onSuccessResponse: a");
                  Toast.makeText(getApplicationContext(),"your new password is saved",Toast.LENGTH_SHORT).show();
                  startActivity(new Intent (getApplicationContext(),MainActivity.class));
                } else {
                  Log.d(TAG, "onFailResponse: ");
                }

              } catch (JSONException e) {
                e.printStackTrace();
              }
            }
          },
          password,
          username,
          code);
    }
  }

  private boolean verified() {
    final String password = new_pass.getText().toString().trim();
    if (TextUtils.isEmpty(password)) {
      new_pass.setError("Enter a valid password");
      new_pass.requestFocus();
      return false;
    }
    return true;
  }

  /** go to login page after updating his password */
  public void to_login(View view) {
    startActivity(new Intent(this, MainActivity.class));
  }

  public void getResponse5(
      int method,
      String url,
      JSONObject jsonValue,
      final VolleyCallback callback,
      final String password,
      final String username,
      final String code) {
    // if everything is fine
    StringRequest stringRequest =
        new StringRequest(
            Request.Method.POST,
            url,
            new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                // if no error in response
                if (response != null) {
                  callback.onSuccessResponse(response);
                }
              }
            },
            new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error .. try again", Toast.LENGTH_SHORT)
                    .show();
                error.getMessage();
              }
            }) {
          @Override
          protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<>();
            params.put("withcode", "true");
            params.put("password", password);
            params.put("username", username);
            params.put("key", code);
            return params;
          }
        };
    VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
  }
}
