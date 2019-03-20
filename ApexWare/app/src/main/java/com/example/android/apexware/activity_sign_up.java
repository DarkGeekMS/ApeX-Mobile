package com.example.android.apexware;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class activity_sign_up extends AppCompatActivity {
  Button login;
  Button create_acc;
  ToggleButton toggle_btn;
  EditText pass_et;
  EditText email_et;
  EditText username_et;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_up);

    /*
     * use either mock service or back end service
     * */
    boolean debug = true;
    DepandantClass restClient = null;
    if (debug) {
      restClient = new DepandantClass(new MockRestService());
    } else {
      restClient = new DepandantClass(new RestService());
    }

    login = (Button) findViewById(R.id.login_instead_btn);
    create_acc = (Button) findViewById(R.id.create_acc_btn);
    toggle_btn = (ToggleButton) findViewById(R.id.toggle_pass_btn);
    pass_et = (EditText) findViewById(R.id.password_text_input);
    username_et = (EditText) findViewById(R.id.username_text_input);
    email_et = (EditText) findViewById(R.id.e_mail_text_input);

    login.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            openActivity_login();
          }
        });

    final DepandantClass finalRestClient = restClient;
    create_acc.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            if (finalRestClient.signup(
                username_et.getText().toString(),
                email_et.getText().toString(),
                pass_et.getText().toString())) {
              Toast.makeText(getApplicationContext(), "Sign up successful", Toast.LENGTH_SHORT)
                  .show();
              open_home_activity();
            } else {
              Toast.makeText(
                      getApplicationContext(), "sign up failed .. try again", Toast.LENGTH_SHORT)
                  .show();
            }
          }
        });
  }
  /*
   * opens the activity login on pressing the button log in instead
   */
  public void openActivity_login() {
    Intent intent = new Intent(this, MainActivity.class);
    startActivity(intent);
  }
  /*
   * toggle button affect viewing password as text or as dots
   * */
  public void onToggleClick(View v) {
    if (toggle_btn.isChecked()) {
      pass_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
      Drawable img = null;
      if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
        img = getDrawable(R.drawable.toggle_on);
      }
      toggle_btn.setBackground(img);
    } else {
      pass_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
      Drawable img = null;
      if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
        img = getDrawable(R.drawable.toggle_off);
      }
      toggle_btn.setBackground(img);
    }
  }
  /*
   * opens the activity home on valid sign up
   */
  public void open_home_activity() {
    Intent intent = new Intent(this, listOfPostsClass.class);
    startActivity(intent);
  }
}
