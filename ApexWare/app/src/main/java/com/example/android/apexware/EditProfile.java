package com.example.android.apexware;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static com.example.android.apexware.CreatePost.REQUEST_GET_GALLERY_PERMIT;
import static com.example.android.apexware.CreatePost.REQUEST_GET_SINGLE_FILE;
import static java.lang.StrictMath.abs;

public class EditProfile extends AppCompatActivity {
    boolean gallery_approved = false;
     EditText userName;
     EditText fullName;
     EditText emailName;
    ImageButton gallery_btn;
    ImageView preview;
    String fullname;
    MenuItem item;
    private String galleyFilePath = "";
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Window window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.myblue));
         gallery_btn=(ImageButton) findViewById(R.id.editProfilePicture);
         preview=(ImageView)findViewById(R.id.newProfilePicturePreview) ;
        item=findViewById(R.id.saveChanges);
        if(item!=null){
            item.setEnabled(false);
        }
        Toolbar toolbar =findViewById(R.id.edittoolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Replay to message");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        ActionBar actionbar =EditProfile.this.getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.close);

         userName=(EditText)findViewById(R.id.username_change);
         fullName=(EditText)findViewById(R.id.fullname_change);
         emailName=(EditText)findViewById(R.id.email_change);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getResponse(
                Request.Method.POST,
                Routes.me,
                null,
                new VolleyCallback() {
                    @Override
                    public void onSuccessResponse(String response) {
                        try {
                            // converting response to json object
                            JSONObject obj = new JSONObject(response);
                            JSONObject userInfo = obj.getJSONObject("user");
                            userName.setText(userInfo.getString("username"));
                            fullName.setText(userInfo.getString("fullname"));
                            emailName.setText(userInfo.getString("email"));
                            if(item!=null){
                                item.setEnabled(true);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.save_option, menu);
        return true;
    }
    public void SaveChanges(MenuItem item){
        getuploadResponce(
                Request.Method.POST,
                Routes.updatePrefrence,
                null,
                new VolleyCallback() {
                    @Override
                    public void onSuccessResponse(String response) {
                        try {
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                galleyFilePath);

    }
    public void getResponse(
            int method,
            String url,
            JSONObject jsonValue,
            final VolleyCallback callback) {
        final User user = SharedPrefmanager.getInstance(this).getUser();
        final String token=user.getToken();
        StringRequest stringRequest =
                new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                int x=0;
                                callback.onSuccessResponse(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
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
                        }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("token",token);
                        return params;
                    }
                };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void getuploadResponce(
            int method,
            String url,
            JSONObject jsonValue,
            final VolleyCallback callback,
            final String imagePath) {
        final User user = SharedPrefmanager.getInstance(this).getUser();
        final String token=user.getToken();
        VolleyMultipartRequest multipartRequest =
                new VolleyMultipartRequest(
                        Request.Method.POST,
                        Routes.updatePrefrence,
                        new Response.Listener<NetworkResponse>() {
                            @Override
                            public void onResponse(NetworkResponse response) {
                                callback.onSuccessResponse(response.toString());
                                Toast.makeText(
                                        getApplicationContext(), "Update changes successfully", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                int x = 0;
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
                                            errorMessage = message + " Please login again";
                                        } else if (networkResponse.statusCode == 400) {
                                            errorMessage = message + " Check your inputs";
                                        } else if (networkResponse.statusCode == 500) {
                                            errorMessage = message + " Something is getting wrong";
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
                        params.put("username",userName.getText().toString());
                        params.put("fullname", fullName.getText().toString());
                        params.put("email",emailName.getText().toString());
                        params.put("notification","1");
                        params.put("token", token);
                        return params;
                    }
                    @Override
                    protected Map<String, DataPart> getByteData() {
                        Map<String, DataPart> params = new HashMap<>();
                        // file name could found file base or direct access from real path
                        // for now just get bitmap data from ImageView
                        params.put(
                                "avatar",
                                new DataPart(
                                        imagePath,
                                        AppHelper.getFileDataFromDrawable(getBaseContext(), preview.getDrawable()),
                                        "image/jpeg"));
                        return params;
                    }
                };
        VolleySingletonForImage.getInstance(getBaseContext()).addToRequestQueue(multipartRequest);
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
                    grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED;}
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
                        Picasso.get().load(selectedImageUri).into(preview);
                    } catch (Exception e) {
                        Log.e("FileSelectorActivity", "File select error", e);
                    }
                    break;

            }
        }
    }
}


