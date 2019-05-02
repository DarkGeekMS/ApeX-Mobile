package com.example.android.apexware;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * gather all the data needed to create the post whatever it's kind is ..
 *
 * @author mostafa
 */
public class CreatePost extends AppCompatActivity {

  public static final int REQUEST_GET_SINGLE_FILE = 1;
  private static final int TAKE_PICTURE = 2;
  private static final String TAG = "create post";
  public static int stPosition = -1;
  Uri imageUri;
  ImageButton back_btn;
  ImageView preview;
  Button post_btn;
  Button choose_community;
  EditText main_post;
  EditText post_title;
  TextView title;
  ConstraintLayout choose_image;
  String communityID = "t5_1";

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
    post_title = (EditText) findViewById(R.id.Title_et);
    preview = (ImageView) findViewById(R.id.imagePreview);

    // get user token
    User user = SharedPrefmanager.getInstance(this).getUser();
    final String token = user.getToken();

    // check which button called the activity to determine post type
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
        main_post.setHint("your text post ..");
        title.setText(getString(R.string.text_post));
        choose_image.setVisibility(View.GONE);
        break;
    }
    CustomAdapterForCommunities adapter = new CustomAdapterForCommunities(this);
    if (stPosition != -1) {
      choose_community.setText(adapter.getName(stPosition));
      // communityID = adapter.getItem(stPosition).toString();
    }

    // when post button is pressed .. validate then send request
    post_btn.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            if (checkValidation(
                type, post_title.getText().toString(), main_post.getText().toString())) {
              takeData(
                  type,
                  post_title.getText().toString(),
                  main_post.getText().toString(),
                  communityID,
                  token);
              post_btn.setEnabled(false);//turn off button to avoid multiple requests
              // TODO: 13/04/2019  complete create post request with image and correct order
            }
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
   * validate that all needed data is inserted by the user then call the function that sends the
   * data
   *
   * @param type : post type (link,text,image)
   * @param title :post title (necessary)
   * @param mainPost : main post (link , lines of text)
   */
  public boolean checkValidation(String type, String title, String mainPost) {
    if (TextUtils.isEmpty(title)) {
      post_title.setError("Please enter title");
      post_title.requestFocus();
      return false;
    }
    if (type.equals("link")) {
      if (TextUtils.isEmpty(mainPost)) {
        main_post.setError("Please enter url");
        main_post.requestFocus();
        return false;
      }
    } else if (type.equals("text")) {
      if (TextUtils.isEmpty(mainPost)) {
        main_post.setError("Please enter text");
        main_post.requestFocus();
        return false;
      }
    }
    return true;
  }

  public void takeData(
      String type, String title, String mainPost, String communityID, String token) {
    getResponse(
        Request.Method.POST,
        Routes.submit_post,
        null,
        new VolleyCallback() {
          @Override
          public void onSuccessResponse(String response) {
            try {
              // converting response to json object
              JSONObject obj = new JSONObject(response);

              // if no error in response
              if (response != null) {
                Toast.makeText(getApplicationContext(), "Post Successful", Toast.LENGTH_SHORT)
                    .show();

                finish();
                startActivity(new Intent(getApplicationContext(), HomePage.class));
              } else {
                Toast.makeText(
                        getApplicationContext(), "Unsuccessful onsuccess ", Toast.LENGTH_SHORT)
                    .show();
              }

            } catch (JSONException e) {
              e.printStackTrace();
            }
          }
        },
        type,
        title,
        mainPost,
        communityID,
        token);
  }

  public void getResponse(
      int method,
      String url,
      JSONObject jsonValue,
      final VolleyCallback callback,
      final String type,
      final String title,
      final String mainPost,
      final String communityID,
      final String token) {
    StringRequest stringRequest =
        new StringRequest(
            Request.Method.POST,
            url,
            new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                callback.onSuccessResponse(response);
              }
            },
            new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ",error );
                Toast.makeText(
                        getApplicationContext(), "Unsuccessful get responce", Toast.LENGTH_SHORT)
                    .show();
                post_btn.setEnabled(true); //re enable button
              }
            }) {
          @Override
          protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<>();
            params.put("ApexCom_id", communityID);
            params.put("title", title);
            if (type.equals("text")) params.put("body", mainPost);
            else if (type.equals("link")) params.put("video_url", mainPost);
            params.put("token", token);
            return params;
          }
        };
    VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
  }

  /** return to home form and discard post */
  public void backToHome() {
    startActivity(new Intent(this, HomePage.class));
  }

  /** open communities of this user to choose where to post */
  public void userComunities(View view) {
    startActivity(new Intent(this, UserCommunities.class));
  }

  /** open galley to get image and add it to the post */
  public void OpenGallery(View view) {
    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
    intent.addCategory(Intent.CATEGORY_OPENABLE);
    intent.setType("image/*");
    startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GET_SINGLE_FILE);
  }

  /**
   * get data returned from intent and preview it on the image view from camera or gallery
   *
   * @param requestCode request defined previously to ensure consistency
   * @param resultCode defined in activity.java and used as a check
   * @param data : intent opening the gallery and returning with image
   */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == Activity.RESULT_OK) {
      switch (requestCode) {
        case REQUEST_GET_SINGLE_FILE:
          {
            try {
              Uri selectedImageUri = data.getData();
              // Get the path from the Uri
              final String path = getPathFromURI(selectedImageUri);
              if (path != null) {
                File f = new File(path);
                selectedImageUri = Uri.fromFile(f);
              }
              // Set the image in ImageView
              preview.setVisibility(View.VISIBLE);
              preview.setImageURI(selectedImageUri);
            } catch (Exception e) {
              Log.e("FileSelectorActivity", "File select error", e);
            }
          }
        case TAKE_PICTURE:
          {
            Uri selectedImage = imageUri;
            getContentResolver().notifyChange(selectedImage, null);
            ContentResolver cr = getContentResolver();
            Bitmap bitmap;
            try {
              bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, selectedImage);

              preview.setImageBitmap(bitmap);
              Toast.makeText(this, selectedImage.toString(), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
              Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT).show();
              Log.e("Camera", e.toString());
            }
          }
      }
    }
  }

  /**
   * get file path from its uri
   *
   * @param contentUri uri of the image returned from the image picker
   * @return file path in device
   */
  public String getPathFromURI(Uri contentUri) {
    String res = null;
    String[] proj = {MediaStore.Images.Media.DATA};
    Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
    if (cursor.moveToFirst()) {
      int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
      res = cursor.getString(column_index);
    }
    cursor.close();
    return res;
  }

  /** opens camera to take a photo */
  public void openCamera(View view) {
    // COMPLETED TODO handle capturing image with camera
    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    File photo = new File(Environment.getExternalStorageDirectory(), "Pic.jpg");
    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
    imageUri = Uri.fromFile(photo);
    startActivityForResult(intent, TAKE_PICTURE);
  }
}
