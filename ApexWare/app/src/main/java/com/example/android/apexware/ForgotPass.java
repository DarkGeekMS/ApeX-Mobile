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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import static com.example.android.apexware.Routes.active_mock;

public class ForgotPass extends AppCompatActivity {
  String type;
  Button back_to_login;
  Button verify;
  ToggleButton toggle_btn;
  EditText editTextEmail;
  EditText editTextUsername;
  EditText editTextPassword;
  TextView title;
  DepandantClass restClient = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_forgot_pass);

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
          title.setText(getString(R.string.reset_pass_form));
        }
      case "user":
        {
          editTextUsername.setVisibility(View.GONE);
          title.setText(getString(R.string.reset_user_form));
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

  /**send request to get the verify code sent*/
  public void verify(View view) {
    if (verified()) {
      // COMPLETED todo go to next activity and send the request of mail verify
      final String username = editTextUsername.getText().toString().trim();
      final String email = editTextEmail.getText().toString().trim();
      final String password = editTextPassword.getText().toString().trim();
      switch (type) {
        case "pass":
          {
            restClient.verify_forget_pass(email, username,this);
          }
        case "user":
          {
            restClient.verify_forget_user(email, password,this);
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
}
