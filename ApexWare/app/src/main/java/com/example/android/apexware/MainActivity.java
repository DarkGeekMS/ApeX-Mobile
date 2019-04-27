package com.example.android.apexware;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Debug;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

import static com.example.android.apexware.Routes.active_mock;

/**
 * first activity in the program and contains login form which the user fill with his data to enter
 * th app
 *
 * @author mostafa
 */
public class MainActivity extends AppCompatActivity {

  Button login;
  Button signup;
  Button forgot_pass;
  ToggleButton toggle_btn;
  EditText editTextPassword;
  EditText editTextUsername;
  static boolean successFlag=false;

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Window window = this.getWindow();

    // clear FLAG_TRANSLUCENT_STATUS flag:
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

    // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

    // finally change the color
    window.setStatusBarColor(ContextCompat.getColor(this, R.color.ic_launcher_background));

    /*// if the token is saved
    if (SharedPrefmanager.getInstance(this).isLoggedIn()) {
        finish();
        startActivity(new Intent(this, HomePage.class));
        return;
    }*/

    /*
     * use either mock service or back end service
     * */
    DepandantClass restClient = null;
    if (active_mock) {
      restClient = new DepandantClass(new MockRestService());
    } else {
      restClient = new DepandantClass(new RestService());
    }

    login = (Button) findViewById(R.id.login_btn);
    signup = (Button) findViewById(R.id.signup_btn);
    forgot_pass = (Button) findViewById(R.id.forgot_pass_btn);
    toggle_btn = (ToggleButton) findViewById(R.id.toggle_pass_btn);
    editTextPassword = (EditText) findViewById(R.id.password_text_input);
    editTextUsername = (EditText) findViewById(R.id.username_text_input);

    signup.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            openActivity_sign_up();
          }
        });

    final DepandantClass finalRestClient = restClient;
    login.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            if (passwordCheck()) {
              if(
                  finalRestClient.login(
                      editTextUsername.getText().toString(),
                      editTextPassword.getText().toString(),
                      getApplicationContext()))
              {
                  Intent i=new Intent(getApplicationContext(), HomePage.class);//only used when debug
                  startActivity(i);}
            } else {
              Toast.makeText(
                      getApplicationContext(),
                      "Password or username don't meet the standers",
                      Toast.LENGTH_SHORT)
                  .show();
            }
          }
        });
  }
  /*
   * opens the activity sign up on pressing the button sign up
   */
  public void openActivity_sign_up() {
    Intent intent = new Intent(this, SignUp.class);
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
  public boolean passwordCheck()
  {
      // first getting the values
      final String username = editTextUsername.getText().toString();
      final String password = editTextPassword.getText().toString();

      // validating inputs
      if (TextUtils.isEmpty(username)) {
          editTextUsername.setError("Please enter your username");
          editTextUsername.requestFocus();
          return false;
      }
      if (TextUtils.isEmpty(password)) {
          editTextPassword.setError("Please enter your password");
          editTextPassword.requestFocus();
          return false;
      }
      return true;
  }

    public void forgot_user_name(View view) {
      //COMPLETED TODO IMPLEMENT
        Intent intent = new Intent(MainActivity.this, ForgotPass.class);
        intent.putExtra("type", "user");
        startActivity(intent);
    }

    public void forgot_password(View view) {
        //COMPLETED TODO IMPLEMENT
        Intent intent = new Intent(MainActivity.this, ForgotPass.class);
        intent.putExtra("type", "pass");
        startActivity(intent);
    }
}
