package com.example.android.apexware;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class CreatePost extends AppCompatActivity {

  ImageButton back_btn;
  Button post_btn;
  Button choose_community;
  EditText main_post;
  TextView title;
  ConstraintLayout choose_image;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_post);

    // Get the transferred data from source activity.
    Intent intent = getIntent();
    String type = intent.getStringExtra("type");

    back_btn = (ImageButton) findViewById(R.id.BackButton);
    main_post = (EditText) findViewById(R.id.post_et);
    choose_image = (ConstraintLayout) findViewById(R.id.choose_img);
    title = (TextView) findViewById(R.id.title_TV);
    post_btn = (Button) findViewById(R.id.post_btn);
    choose_community = (Button) findViewById(R.id.choose_community);

    if (type.equals("link")) {
      main_post.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
      main_post.setHint("http://");
      choose_image.setVisibility(View.GONE);
      title.setText(getString(R.string.link_post));
    }
    else if (type.equals("image")) {
      main_post.setVisibility(View.GONE);
      title.setText(getString(R.string.image_post));
    }
    else // type is text or string is empty
    {
      main_post.setInputType(InputType.TYPE_CLASS_TEXT);
      main_post.setHint("your text post (optional) ");
      title.setText(getString(R.string.text_post));
      choose_image.setVisibility(View.GONE);
    }

    post_btn.setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                //do nothing
              }
            });

    back_btn.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            backToHome();
          }
        });
    choose_community.setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                openUserCommunities();
              }
            });

  }

  /** return to home form and discard post*/
  public void backToHome() {
    Intent intent = new Intent(this, HomePage.class);
    startActivity(intent);
  }
  /** open communities of this user to choose where to post */
  public void openUserCommunities() {
    Intent intent = new Intent(this, UserCommunities.class);
    startActivity(intent);
  }
}
