package com.example.android.apexware;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

public class Settings extends AppCompatActivity {
  Point p;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);
  }

  /** finish editing and return back to home */
  public void returnToHome(View view) {
    startActivity(new Intent(this, HomePage.class));
  }

  public void open_notifications(View view) {
    // todo open notifications activity
  }

    /**open accounts pop up when accounts button is chosen in settings*/
  public void open_accounts(View view) {
    if (p != null) showPopup(Settings.this, p);
  }

  @Override
  public void onWindowFocusChanged(boolean hasFocus) {

    int[] location = new int[2];
    Button button = (Button) findViewById(R.id.acc_btn);

    // Get the x, y location and store it in the location[] array
    // location[0] = x, location[1] = y.
    button.getLocationOnScreen(location);

    // Initialize the Point with x, and y positions
    p = new Point();
    p.x = location[0];
    p.y = location[1];
  }

  // Get the x and y position after the button is draw on screen
  // (It's important to note that we can't get the position in the onCreate(),
  // because at that stage most probably the view isn't drawn yet, so it will return (0, 0))

  // The method that displays the popup.
  private void showPopup(final Activity context, Point p) {
    Display display = getWindowManager().getDefaultDisplay();
    Point size = new Point();
    display.getSize(size);
    int popupWidth = size.x;
    double y = 0.28 * size.y;
    int popupHeight = (int) y;

    // Inflate the popup_layout.xml
    ConstraintLayout viewGroup = (ConstraintLayout) context.findViewById(R.id.acc_popup);
    LayoutInflater layoutInflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View layout = layoutInflater.inflate(R.layout.accounts_popup, viewGroup);

    // Creating the PopupWindow
    final PopupWindow popup = new PopupWindow(context);
    popup.setContentView(layout);
    popup.setWidth(popupWidth);
    popup.setHeight(popupHeight);
    popup.setFocusable(true);

    // Some offset to align the popup a bit to the right, and a bit down, relative to button's
    // position.
    int OFFSET_X = 30;
    int OFFSET_Y = 30;

    // Clear the default translucent background
    popup.setBackgroundDrawable(new BitmapDrawable());

    // Displaying the popup at the specified location, + offsets.
    popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);

    // Getting a reference to Close button, and close the popup when clicked.
    /*Button close = (Button) layout.findViewById(R.id.cancel_button);
    close.setOnClickListener(
        new View.OnClickListener() {

          @Override
          public void onClick(View v) {
            popup.dismiss();
          }
        });
        */
  }
}