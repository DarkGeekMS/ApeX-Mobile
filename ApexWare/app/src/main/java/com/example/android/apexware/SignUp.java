package com.example.android.apexware;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * where user can create a new account if he can't login or using the app for first time
 *
 * @author mostafa
 */
public class SignUp extends AppCompatActivity {
  Button login;
  Button create_acc;
  ToggleButton toggle_btn;
  EditText editTextEmail;
  EditText editTextUsername;
  EditText editTextPassword;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_up);

    login = (Button) findViewById(R.id.login_instead_btn);
    create_acc = (Button) findViewById(R.id.create_acc_btn);
    toggle_btn = (ToggleButton) findViewById(R.id.toggle_pass_btn);
    editTextEmail = (EditText) findViewById(R.id.email_text_input);
    editTextUsername = (EditText) findViewById(R.id.username_text_input);
    editTextPassword = (EditText) findViewById(R.id.password_text_input);

    try {

      create_acc.setOnClickListener(
          new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              registerUser();
            }
          });
    } catch (Exception e) {
      e.printStackTrace();
    }
    login.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            openActivity_login();
          }
        });
  }

  /** open login form again */
  public void openActivity_login() {
    Intent intent = new Intent(this, MainActivity.class);
    startActivity(intent);
  }

  /** toggle button affect viewing password as text or as dots */
  public void onToggleClick(View v) {
    if (toggle_btn.isChecked()) {
      editTextPassword.setInputType(
          InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
      Drawable img = null;
      if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
        img = getDrawable(R.drawable.toggle_on);
      }
      toggle_btn.setBackground(img);
    } else {
      editTextPassword.setInputType(
          InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
      Drawable img = null;
      if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
        img = getDrawable(R.drawable.toggle_off);
      }
      toggle_btn.setBackground(img);
    }
  }

  /**
   * check all fields are filled with data , connect to server , fill jason and send the request to
   * the server with all data needed
   *
   * @author mazen
   */
  private void registerUser() {
    final String username = editTextUsername.getText().toString().trim();
    final String email = editTextEmail.getText().toString().trim();
    final String password = editTextPassword.getText().toString().trim();
    // first we will do the validations

    if (TextUtils.isEmpty(username)) {
      editTextUsername.setError("Please enter username");
      editTextUsername.requestFocus();
      return;
    }

    if (TextUtils.isEmpty(email)) {
      editTextEmail.setError("Please enter your email");
      editTextEmail.requestFocus();
      return;
    }

    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
      editTextEmail.setError("Enter a valid email");
      editTextEmail.requestFocus();
      return;
    }
    if (TextUtils.isEmpty(password)) {
      editTextPassword.setError("Enter a password");
      editTextPassword.requestFocus();
      return;
    }
    String url = "http://35.232.3.8/api/sign_up";
    getResponse(Request.Method.POST, Routes.signUp,null, new VolleyCallback(){
            @Override
            public void onSuccessResponse(String response) {
                try {
                  // converting response to json object
                  JSONObject obj = new JSONObject(response);

              // if no error in response
              if (response != null) {
                Toast.makeText(getApplicationContext(), "SignUp Successfully", Toast.LENGTH_SHORT)
                    .show();

                // getting the user from the response
                JSONObject userJson = obj.getJSONObject("user");
                // JSONObject userJson1 = obj.getJSONObject("token");
                // creating a new user object
                User user =
                    new User(
                        userJson.getString("email"),
                        userJson.getString("username"),
                        userJson.getString("id"),
                        obj.getString("token"));
                // storing the user in shared preferences
                SharedPrefmanager.getInstance(getApplicationContext()).userLogin(user);
                // starting the profile activity
                finish();
                startActivity(new Intent(getApplicationContext(), HomePage.class));
              } else {
                Toast.makeText(
                        getApplicationContext(), "Unsuccessful operation", Toast.LENGTH_SHORT)
                    .show();
              }

            } catch (JSONException e) {
              e.printStackTrace();
            }
          }
        },
        username,
        password,
        email);
  }

  public void getResponse(
      int method,
      String url,
      JSONObject jsonValue,
      final VolleyCallback callback,
      final String username,
      final String password,
      final String email) {
    StringRequest stringRequest =
        new StringRequest(
            Request.Method.POST,
            url,
            new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                callback.onSuccessResponse(response);
              }
            },
            new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                Toast.makeText(
                        getApplicationContext(), "Server Error", Toast.LENGTH_SHORT)
                    .show();
                error.getMessage();
              }
            }) {
          @Override
          protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<>();
            params.put("username", username);
            params.put("email", email);
            params.put("password", password);
            return params;
          }
        };
    VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
  }
}
