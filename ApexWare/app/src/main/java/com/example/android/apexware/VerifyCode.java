package com.example.android.apexware;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import java.util.Map;

import static android.support.constraint.Constraints.TAG;
import static com.example.android.apexware.ForgotPass.magic_code;
import static com.example.android.apexware.Routes.active_mock;

/** activity where user enters the verification code he recieved to log in */
public class VerifyCode extends AppCompatActivity {

  String type;
  DepandantClass restClient = null;
  String email;
  String userName;
  EditText code_et;
  Button check_btn;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_verify_code);
    // Get the transferred data from source activity.
    Intent intent = getIntent();
    type = intent.getStringExtra("type");
    email = intent.getStringExtra("email");
    code_et = (EditText) findViewById(R.id.ver_code);
    check_btn = (Button) findViewById(R.id.check_code);
    /*
     * use either mock service or back end service
     * */
    if (active_mock) {
      restClient = new DepandantClass(new MockRestService());
    } else {
      restClient = new DepandantClass(new RestService());
    }
  }

  /** on button verify pressed .. check code*/
  public void confirm_code(View view) {
    if (Verified()) {
      check_btn.setEnabled(false); // disable button to avoid multiple requests
      String code = code_et.getText().toString().trim();
      if (active_mock) {
        confirmCode_Mock(email, code);
      } else {
        userName = confirmCode_Rest(email, code);
      }
      // userName = restClient.confirmCode(email, code,this);
      // todo move to new pass if password then to login carrying data to login
    } else {
      Toast.makeText(this, "code invalid", Toast.LENGTH_SHORT).show();
    }
  }

  /**
   * check that code sent by automatic mail is the entered by the user
   *
   * @param email user email
   * @param code code received by user email
   */
  private void confirmCode_Mock(String email, String code) {
    if (code.equals(magic_code)) {
      Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show();
      startActivity(new Intent(this, HomePage.class));
    }
    else
    {
      Toast.makeText(this, "Wrong code", Toast.LENGTH_SHORT).show();
      check_btn.setEnabled(true); // enable button for retry

    }
  }

  /**
   * verify if code is written with the right format
   *
   * @return true if valid
   */
  private boolean Verified() {
    final String code = code_et.getText().toString().trim();

    if (TextUtils.isEmpty(code)) {
      code_et.setError("Please enter code");
      code_et.requestFocus();
      return false;
    }
    if (code.length() < 8) // code length
    {
      code_et.setError("code too short");
      code_et.requestFocus();
      return false;
    }
    return true;
  }

  /**
   * check code written is the same sent by the server
   * @param email user email
   * @param code code received by user email
   * @return username
   */
  public String confirmCode_Rest(String email, String code) {
    getResponse4(
        Request.Method.POST,
        Routes.checkCode,
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
                // todo get user name from response
              } else {
                Log.d(TAG, "onFailResponse: ");
                check_btn.setEnabled(true); // enable button for retry
              }

            } catch (JSONException e) {
              e.printStackTrace();
            }
          }
        },
        email,
        code);
    return "debug"; // for debug todo
  }

  public void getResponse4(
      int method,
      String url,
      JSONObject jsonValue,
      final VolleyCallback callback,
      final String email,
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
                Toast.makeText(getApplicationContext(), "ServerError", Toast.LENGTH_SHORT).show();
                error.getMessage();
                check_btn.setEnabled(true); // enable button for retry
              }
            }) {
          @Override
          protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<>();
            params.put("email", email);
            params.put("code", code);
            return params;
          }
        };
    VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
  }
}
