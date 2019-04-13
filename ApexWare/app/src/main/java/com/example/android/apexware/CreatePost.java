package com.example.android.apexware;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * gather all the data needed to create the post whatever it's kind is ..
 *
 * @author mostafa
 */
public class CreatePost extends AppCompatActivity {

  ImageButton back_btn;
  Button post_btn;
  Button choose_community;
  EditText main_post;
  TextView title;
  ConstraintLayout choose_image;
  int communityID = 0;

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_post);
    Window window = this.getWindow();

    // clear FLAG_TRANSLUCENT_STATUS flag:
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

    // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

    // finally change the color
    window.setStatusBarColor(ContextCompat.getColor(this, R.color.myblue));

    // Get the transferred data from source activity.
    Intent intent = getIntent();
    final String type = intent.getStringExtra("type");

    back_btn = (ImageButton) findViewById(R.id.BackButton);
    main_post = (EditText) findViewById(R.id.post_et);
    choose_image = (ConstraintLayout) findViewById(R.id.choose_img);
    title = (TextView) findViewById(R.id.title_TV);
    post_btn = (Button) findViewById(R.id.post_btn);
    choose_community = (Button) findViewById(R.id.choose_community);

    /*
    check which button called the activity to determine post type
     */
    switch (type) {
      case "link":
        main_post.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
        main_post.setHint("http://");
        choose_image.setVisibility(View.GONE);
        title.setText(getString(R.string.link_post));
        break;
      case "image":
        main_post.setVisibility(View.GONE);
        title.setText(getString(R.string.image_post));
        break;
      default: // type is text or string is empty
        main_post.setInputType(InputType.TYPE_CLASS_TEXT);
        main_post.setHint("your text post (optional) ");
        title.setText(getString(R.string.text_post));
        choose_image.setVisibility(View.GONE);
        break;
    }

    post_btn.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            sendPostDetails(
                type, title.getText().toString(), main_post.getText().toString(), communityID);
          }
        });

    back_btn.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            backToHome();
          }
        });
  }

  /**
   * send post details to the server to add it to a certain community
   *
   * @param type : post type (link,text,image)
   * @param title :post title (necessary)
   * @param mainPost : main post (link , lines of text , image uri)
   * @param communityID : id of apexcom chosen to post to
   */
  private void sendPostDetails(String type, String title, String mainPost, int communityID) {
    // do nothing
  }

  /** return to home form and discard post */
  public void backToHome() {
    Intent intent = new Intent(this, HomePage.class);
    startActivity(intent);
  }

  /** open communities of this user to choose where to post */
  public void userComunities(View view) {
    Intent intent = new Intent(this, UserCommunities.class);
    startActivity(intent);
  }
}
