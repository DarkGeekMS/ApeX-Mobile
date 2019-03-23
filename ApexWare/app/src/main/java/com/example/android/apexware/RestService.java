package com.example.android.apexware;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * this class is the actual class that deals with the api side and overrides the interface methods
 * to send correct requests to the back end server
 *
 * @author mostafa, mazen
 */
public class RestService extends AppCompatActivity implements DataSupplier {
    /**
     * @param username : the user name field in the form
     * @param password : the password field in the form
     * @return true if login successful
     */
    @Override
    public boolean login_interface(final String username, final String password) {
       /*
        String url = "http://localhost:8000/api/Sign_in?";
        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            //if no error in response
                            if (response != null) {
                                Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                //getting the user from the response
                                //-----------JSONObject userJson = obj.getJSONObject("token");
                                //creating a new user object
                                User user = new User(
                                        obj.getString("token")
                                );
                                //storing the user in shared preferences
                                SharedPrefmanager.getInstance(getApplicationContext()).userLogin(user);
                                //starting the profile activity
                                finish();
                                startActivity(new Intent(getApplicationContext(), HomePage.class));
                                //  } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Username or Password maybe incorrect", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        */
        return false ; // for debug
    }

    @Override
    public boolean signup_interface(String username, String email, String password) {
        return true; // for debug
    }
}
