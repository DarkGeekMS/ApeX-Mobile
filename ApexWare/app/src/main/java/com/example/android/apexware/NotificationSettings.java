package com.example.android.apexware;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ToggleButton;

import com.onesignal.OneSignal;

public class NotificationSettings extends AppCompatActivity {
  ToggleButton disable_all;
  CheckBox[] boxes = new CheckBox[4];
  boolean allow_all = true;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_notification_settings);
    boxes[0] = (CheckBox) findViewById(R.id.private_msg_checkBox);
    boxes[1] = (CheckBox) findViewById(R.id.comments_checkBox);
    boxes[2] = (CheckBox) findViewById(R.id.replies_checkBox);
    boxes[3] = (CheckBox) findViewById(R.id.mentions_checkBox);
    disable_all = (ToggleButton) findViewById(R.id.disable_all);
  }

  /** on back button pressed .. activity is ended and user return to settings */
  public void returnToSettings(View view) {
    finish();
  }

  /**
   * the allow and disable button changes text and color and check boxes state and turn off one
   * signal subscribtion
   */
  public void toggleAllow(View view) {
    if (disable_all.isChecked()) {
      for (int i = 0; i < 4; i++) boxes[i].setChecked(false);
      // turn off notifications
      allow_all = false;
      Drawable blue_color = getApplicationContext().getResources().getDrawable(R.color.myblue);
      disable_all.setBackground(blue_color);
      OneSignal.setSubscription(allow_all); // turn notifications off
    } else {
      for (int i = 0; i < 4; i++) boxes[i].setChecked(true);
      allow_all = true;
      Drawable grey_color = getApplicationContext().getResources().getDrawable(R.color.light_grey);
      disable_all.setBackground(grey_color);
      OneSignal.setSubscription(allow_all); // turn notifications on
    }
  }
}
