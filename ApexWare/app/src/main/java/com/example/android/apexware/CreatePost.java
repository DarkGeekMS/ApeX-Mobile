package com.example.android.apexware;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
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
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.example.android.apexware.Routes.active_mock;

/**
 * gather all the data needed to create the post whatever it's kind is ..
 *
 * @author mostafa
 */
public class CreatePost extends AppCompatActivity {

  public static final int REQUEST_GET_SINGLE_FILE = 1;
  private static final int TAKE_PICTURE = 2;
  public static final int REQUEST_GET_GALLERY_PERMIT = 200;
  public static final int REQUEST_GET_CAMERA_PERMIT = 201;
  public static final int REQUEST_GET_STORAGE_PERMIT = 202;
  boolean gallery_approved = false;
  boolean camera_approved = false;
  boolean storage_approved = false;
  private static final String TAG = "create post";
  public static String communityName = "choose a community from here";
  public static String communityID = "t5_1";
  ImageButton back_btn;
  ImageButton gallery_btn;
  ImageButton camera_btn;
  ImageView preview;
  Button post_btn;
  Button choose_community;
  EditText main_post;
  EditText post_title;
  TextView title;
  ConstraintLayout choose_image;
  String communityID = "t5_2";
  private String galleyFilePath = "";
  private String cameraFilePath = "";

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
    gallery_btn = (ImageButton) findViewById(R.id.imageButton3);
    camera_btn = (ImageButton) findViewById(R.id.imageButton2);
    main_post = (EditText) findViewById(R.id.post_et);
    choose_image = (ConstraintLayout) findViewById(R.id.choose_img);
    title = (TextView) findViewById(R.id.title_TV);
    post_btn = (Button) findViewById(R.id.post_btn);
    choose_community = (Button) findViewById(R.id.choose_community);
    post_title = (EditText) findViewById(R.id.Title_et);
    preview = (ImageView) findViewById(R.id.imagePreview);

    choose_community.setText(communityName);

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

    camera_btn.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            if ((check_camera_permission() || camera_approved)
                && (check_storage_permission() || storage_approved)) {
              captureFromCamera();
            } else {
              Toast.makeText(
                      getApplicationContext(),
                      "permission galley not available please try again",
                      Toast.LENGTH_SHORT)
                  .show();
            }
          }
        });

    gallery_btn.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            if (check_gallery_permission() || gallery_approved) {
              Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
              intent.addCategory(Intent.CATEGORY_OPENABLE);
              intent.setType("image/*");
              startActivityForResult(
                  Intent.createChooser(intent, "Select Picture"), REQUEST_GET_SINGLE_FILE);
            } else {
              Toast.makeText(
                      getApplicationContext(),
                      "permission galley not available please try again",
                      Toast.LENGTH_SHORT)
                  .show();
            }
          }
        });
    // when post button is pressed .. validate then send request
    post_btn.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if (!active_mock) {
                  // get user token
                  User user = SharedPrefmanager.getInstance(getApplicationContext()).getUser();
                  final String token = user.getToken();
            if (checkValidation(
                type,
                post_title.getText().toString(),
                main_post.getText().toString(),
                galleyFilePath,
                cameraFilePath)) {
              switch (type) {
                case "image":
                  String currentPath=null;
                  if(galleyFilePath.isEmpty()){
                    currentPath=galleyFilePath;
                  }
                  else{
                    currentPath=cameraFilePath;
                  }
                  getuploadResponce(Request.Method.POST,
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
                          },token,
                          post_title.getText().toString(),
                          currentPath
                          );
                  break;
                default:
                  takeData(
                      type,
                      post_title.getText().toString(),
                      main_post.getText().toString(),
                      communityID,
                      token);
              }
              post_btn.setEnabled(false); // turn off button to avoid multiple requests
            }} else {
                Toast.makeText(
                        getApplicationContext(), "post published succesfully", Toast.LENGTH_SHORT)
                        .show();
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
  public boolean checkValidation(
      String type, String title, String mainPost, String galleyFilePath, String cameraFilePath) {
    if (TextUtils.isEmpty(title)) {
      post_title.setError("Please enter title");
      post_title.requestFocus();
      return false;
    } // for all cases

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
    } else if (type.equals("image")) {
      if (cameraFilePath.isEmpty() && galleyFilePath.isEmpty()) {
        Toast.makeText(this, "image invalid .. retry ", Toast.LENGTH_SHORT).show();
        return false;
      }
    }
    return true;
  }

  /**
   * send post request
   *
   * @param type of post (text, image)
   * @param title of post
   * @param mainPost link url or post content
   * @param communityID id of apex com that posted into
   * @param token of user
   */
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
                Log.e(TAG, "onErrorResponse: ", error);
                Toast.makeText(
                        getApplicationContext(), "Unsuccessful get responce", Toast.LENGTH_SHORT)
                    .show();
                post_btn.setEnabled(true); // re enable button
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
    finish();
  }

  /** open communities of this user to choose where to post */
  public void userCommunities(View view) {
    startActivity(new Intent(this, UserCommunities.class));
  }

  /**
   * check if galley access permission is already granted .. if not it asks for it and return false
   *
   * @return true if access granted , false other wise
   */
  public boolean check_gallery_permission() {
    if (ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED) {
      // Permission is not granted
      String[] permissions = {READ_EXTERNAL_STORAGE};
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        requestPermissions(permissions, REQUEST_GET_GALLERY_PERMIT);
        return false;
      } else {
        Toast.makeText(
                this, "feature is not present for your current android version", Toast.LENGTH_SHORT)
            .show();
        return false;
      }
    }
    return true;
  }

  /**
   * check if camera access permission is already granted .. if not it asks for it and return false
   *
   * @return true if access granted , false other wise
   */
  public boolean check_camera_permission() {
    if (ContextCompat.checkSelfPermission(this, CAMERA) != PackageManager.PERMISSION_GRANTED) {
      // Permission is not granted
      String[] permissions = {CAMERA};
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        requestPermissions(permissions, REQUEST_GET_CAMERA_PERMIT);
        return false;
      } else {
        Toast.makeText(
                this, "feature is not present for your current android version", Toast.LENGTH_SHORT)
            .show();
        return false;
      }
    }
    return true;
  }

  /**
   * check if camera access permission is already granted .. if not it asks for it and return false
   *
   * @return true if access granted , false other wise
   */
  public boolean check_storage_permission() {
    if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED) {
      // Permission is not granted
      String[] permissions = {WRITE_EXTERNAL_STORAGE};
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        requestPermissions(permissions, REQUEST_GET_STORAGE_PERMIT);
        return false;
      } else {
        Toast.makeText(
                this, "feature is not present for your current android version", Toast.LENGTH_SHORT)
            .show();
        return false;
      }
    }
    return true;
  }

  /**
   * get user response on asking for permissions sent
   *
   * @param requestCode request defined previously to ensure consistency
   * @param permissions permissions that where asked to be given
   * @param grantResults granted or denied
   */
  @Override
  public void onRequestPermissionsResult(
      int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    if (requestCode == REQUEST_GET_GALLERY_PERMIT) {
      gallery_approved =
          grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
    } else if (requestCode == REQUEST_GET_CAMERA_PERMIT) {
      camera_approved =
          grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
    } else if (requestCode == REQUEST_GET_STORAGE_PERMIT) {
      storage_approved =
          grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
    }
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
          try {
            Uri selectedImageUri = data.getData();
            // Get the path from the Uri
            String filePath = PathUtil.getPath(this, selectedImageUri);
            galleyFilePath = filePath;
            Toast.makeText(this, galleyFilePath, Toast.LENGTH_LONG).show(); // get file path
            // Set the image in ImageView
            preview.setVisibility(View.VISIBLE);
            choose_image.setVisibility(View.INVISIBLE);
            preview.setImageURI(selectedImageUri);
            choose_image.setVisibility(View.INVISIBLE);
          } catch (Exception e) {
            Log.e("FileSelectorActivity", "File select error", e);
          }
          break;

        case TAKE_PICTURE:
          cameraFilePath = cameraFilePath.substring(cameraFilePath.indexOf(':') + 3); // cut url
          preview.setImageURI(Uri.parse(cameraFilePath));
          Toast.makeText(this, cameraFilePath, Toast.LENGTH_SHORT).show();
          break;
      }
    }
  }

  /**
   * create file in memory to save image captured into
   *
   * @return this file
   */
  public File createImageFile() throws IOException {
    // Create an image file name
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    String imageFileName = "JPEG_" + timeStamp + "_";
    // This is the directory in which the file will be created. This is the default location of
    // Camera photos
    File storageDir =
        new File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera");
    File image =
        File.createTempFile(
            imageFileName, /* prefix */ ".jpg", /* suffix */ storageDir /* directory */);
    // Save a file: path for using again
    cameraFilePath = "file://" + image.getAbsolutePath();
    return image;
  }

  /** use camera to capture photo */
  private void captureFromCamera() {
    try {
      Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
      intent.putExtra(
          MediaStore.EXTRA_OUTPUT,
          FileProvider.getUriForFile(
              this, BuildConfig.APPLICATION_ID + ".provider", createImageFile()));
      startActivityForResult(intent, TAKE_PICTURE);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
  public void getuploadResponce(int method,
                                String url,
                                JSONObject jsonValue,
                                final VolleyCallback callback,final String token,final String title,final String imagePath){
    VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, Routes.submit_post, new Response.Listener<NetworkResponse>() {
      @Override
      public void onResponse(NetworkResponse response) {
          int x=0;
        callback.onSuccessResponse(response.toString());
      }
    }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
          int x=0;
        NetworkResponse networkResponse = error.networkResponse;
        String errorMessage = "Unknown error";
        if (networkResponse == null) {
          if (error.getClass().equals(TimeoutError.class)) {
            errorMessage = "Request timeout";
          } else if (error.getClass().equals(NoConnectionError.class)) {
            errorMessage = "Failed to connect server";
          }
        } else {
          String result = new String(networkResponse.data);
          try {
            JSONObject response = new JSONObject(result);
            String status = response.getString("status");
            String message = response.getString("message");

            Log.e("Error Status", status);
            Log.e("Error Message", message);

            if (networkResponse.statusCode == 404) {
              errorMessage = "Resource not found";
            } else if (networkResponse.statusCode == 401) {
              errorMessage = message+" Please login again";
            } else if (networkResponse.statusCode == 400) {
              errorMessage = message+ " Check your inputs";
            } else if (networkResponse.statusCode == 500) {
              errorMessage = message+" Something is getting wrong";
            }
          } catch (JSONException e) {
            e.printStackTrace();
          }
        }
        Log.i("Error", errorMessage);
        error.printStackTrace();
      }
    }) {
      @Override
      protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put("ApexCom_id",communityID);
        params.put("title",title);
        params.put("token",token);
        return params;
      }
      @Override
      protected Map<String, DataPart> getByteData() {
        Map<String, DataPart> params = new HashMap<>();
        // file name could found file base or direct access from real path
        // for now just get bitmap data from ImageView
        params.put("img_name", new DataPart(imagePath, AppHelper.getFileDataFromDrawable(getBaseContext(), preview.getDrawable()), "image/jpeg"));
        return params;
      }
    };
    VolleySingletonForImage.getInstance(getBaseContext()).addToRequestQueue(multipartRequest);

  }
}
