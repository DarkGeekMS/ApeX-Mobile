package com.example.android.apexware;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static com.example.android.apexware.Routes.active_mock;

/** activity where user enters the verification code he recieved to log in */
public class VerifyCode extends AppCompatActivity {

  public static String mock_code = "empty";
  String type;
  DepandantClass restClient = null;
  String email;
  String userName;
  EditText code_et;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_verify_code);
    // Get the transferred data from source activity.
    Intent intent = getIntent();
    type = intent.getStringExtra("type");
    email = intent.getStringExtra("email");
    code_et = (EditText) findViewById(R.id.ver_code);

    /*
     * use either mock service or back end service
     * */
    if (active_mock) {
      restClient = new DepandantClass(new MockRestService());
    } else {
      restClient = new DepandantClass(new RestService());
    }
    // TODO
  }

  public void confirm_code(View view) {
    String code = code_et.getText().toString().trim();
    userName = restClient.confirmCode(email, code,this);
    //todo move to new pass if password then to login carrying data to login
  }
}
