package com.example.android.apexware;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import static android.support.constraint.Constraints.TAG;
import static com.example.android.apexware.Routes.active_mock;

/** enter data to confirm user and send him a verification code */
public class ForgotPass extends AppCompatActivity {
  String type;
  Button back_to_login;
  Button verify;
  ToggleButton toggle_btn;
  EditText editTextEmail;
  EditText editTextUsername;
  EditText editTextPassword;
  TextView title;
  static String magic_code = "123456789"; // can be any value larger than 8 characters
  DepandantClass restClient = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_forgot_pass);


      StrictMode.ThreadPolicy policy =
              new StrictMode.ThreadPolicy.Builder().permitAll().build();
      StrictMode.setThreadPolicy(policy);

    // Get the transferred data from source activity.
    Intent intent = getIntent();
    type = intent.getStringExtra("type");

    // use either mock service or back end service
    if (active_mock) {
      restClient = new DepandantClass(new MockRestService());
    } else {
      restClient = new DepandantClass(new RestService());
    }

    back_to_login = (Button) findViewById(R.id.back_toLogin_btn);
    verify = (Button) findViewById(R.id.verify_btn);
    toggle_btn = (ToggleButton) findViewById(R.id.toggle_pass_btn_forgot);
    title = (TextView) findViewById(R.id.title_forgot);
    editTextEmail = (EditText) findViewById(R.id.email_text_input_forgot);
    editTextUsername = (EditText) findViewById(R.id.username_text_input_forgot);
    editTextPassword = (EditText) findViewById(R.id.password_text_input_forgot);

    switch (type) {
      case "pass":
        {
          toggle_btn.setVisibility(View.GONE);
          editTextPassword.setVisibility(View.GONE);
          // editTextUsername.setVisibility(View.VISIBLE);
          title.setText(getString(R.string.reset_pass_form));
          break;
        }
      case "user":
        {
          editTextUsername.setVisibility(View.GONE);
          title.setText(getString(R.string.reset_user_form));
          break;
        }
    }
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
  /** open login form again */
  public void openActivity_login(View view) {
    startActivity(new Intent(this, MainActivity.class));
  }

  /** send request to get the verify code sent */
  public void verify(View view) {
    if (verified()) {
      verify.setEnabled(false); // disable button to avoid multiple requests
      // COMPLETED todo go to next activity and send the request of mail verify
      final String username = editTextUsername.getText().toString().trim();
      final String email = editTextEmail.getText().toString().trim();
      final String password = editTextPassword.getText().toString().trim();
      switch (type) {
        case "pass":
          {
            // restClient.verify_forget_pass(email, username,this);
            // use either mock service or back end service
            if (active_mock) {
              verify_forget_pass_Mock(email, username);

            } else {
              verify_forget_pass_Rest(email, username);
            }
            break;
          }
        case "user":
          {
            // restClient.verify_forget_user(email, password, this);
            // use either mock service or back end service
            if (active_mock) {
              verify_forget_pass_Mock(email, username); // same method as password

            } else {
              verify_forget_user_Rest(email, username);
            }
          }
      }
      Intent intent = new Intent(ForgotPass.this, VerifyCode.class);
      intent.putExtra("type", type);
      intent.putExtra("email", email);
      startActivity(intent);

    } else {
      Toast.makeText(getApplicationContext(), "verification unsuccessful", Toast.LENGTH_SHORT)
          .show();
    }
  }

  /**
   * SEND email from the application to the user
   *
   * @param email user email
   * @param username user name
   */
  public void verify_forget_pass_Mock(String email, String username) {
    // COMPLETED TODO send automatic email with unified code
    try {
      String senderEmail = "apex.sw19";
      GMailSender sender = new GMailSender(senderEmail, "apex1234");
      sender.sendMail("verification code", "your code is" + magic_code, senderEmail,email);
    } catch (Exception e) {
      Log.e("SendMail", e.getMessage(), e);
    }
  }

  /**
   * verify needed data is inserted by the user
   *
   * @return true if all data is correct
   */
  public boolean verified() {
    final String username = editTextUsername.getText().toString().trim();
    final String email = editTextEmail.getText().toString().trim();
    final String password = editTextPassword.getText().toString().trim();
    // first we will do the validations

    if (TextUtils.isEmpty(email)) {
      editTextEmail.setError("Please enter your email");
      editTextEmail.requestFocus();
      return false;
    }
    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
      editTextEmail.setError("Enter a valid email");
      editTextEmail.requestFocus();
      return false;
    }

    switch (type) {
      case "pass":
        {
          if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Please enter username");
            editTextUsername.requestFocus();
            return false;
          }
          break;
        }
      case "user":
        {
          if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Enter a password");
            editTextPassword.requestFocus();
            return false;
          }
        }
    }
    return true;
  }

  /**
   * used in cases of forgetting password to send verify mail to the user
   *
   * @param email user email
   * @param username user name
   */
  public void verify_forget_pass_Rest(String email, String username) {
    getResponse2(
        Request.Method.POST,
        Routes.mailVerification,
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
              } else {
                Log.d(TAG, "onFailResponse: ");
              }

            } catch (JSONException e) {
              e.printStackTrace();
            }
          }
        },
        email,
        username);
  }

  /**
   * used in cases of forgetting username to send verify mail to the user
   *
   * @param email user email
   * @param password the password user knows
   */
  public void verify_forget_user_Rest(String email, String password) {
    getResponse3(
        Request.Method.POST,
        Routes.mailVerification,
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
              } else {
                Log.d(TAG, "onFailResponse: ");
              }

            } catch (JSONException e) {
              e.printStackTrace();
            }
          }
        },
        email,
        password);
  }

  public void getResponse2(
      int method,
      String url,
      JSONObject jsonValue,
      final VolleyCallback callback,
      final String email,
      final String username) {
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
                Toast.makeText(getApplicationContext(), "ServerError", Toast.LENGTH_SHORT).show();
                error.getMessage();
                verify.setEnabled(true); // get button back to verify again
              }
            }) {
          @Override
          protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<>();
            params.put("email", email);
            params.put("username", username);
            return params;
          }
        };
    VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
  }

  public void getResponse3(
      int method,
      String url,
      JSONObject jsonValue,
      final VolleyCallback callback,
      final String email,
      final String password) {
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
                Toast.makeText(getApplicationContext(), "ServerError", Toast.LENGTH_SHORT).show();
                error.getMessage();
                verify.setEnabled(true); // get button back to verify again
              }
            }) {
          @Override
          protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<>();
            params.put("email", email);
            params.put("password", password);
            return params;
          }
        };
    VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
  }
}
