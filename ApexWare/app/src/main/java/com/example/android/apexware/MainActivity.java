package com.example.android.apexware;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
/**
 * wkeldkwdoiejdpweijdowiedjoiewdoiewdoi
 * @author  shaer
 */

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }
    /**
     * The HelloWorld program implements an application that
     * simply displays "Hello World!" to the standard output.
     *
     * @author  Zara Ali
     * @version 1.0
     * @since   2014-03-31
     */
    public void goHome(View view) {
      Intent dummyLogin = new Intent(this,listOfPostsClass.class);
      startActivity(dummyLogin);
    }

}
