package com.example.android.apexware;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
public class RestService  implements DataSupplier {
   public boolean flag=false;
    /**
   * @param username : the user name field in the form
   * @param password : the password field in the form
   * @return true if login successful
   */
  @Override
  public boolean login_interface(final String username, final String password,final Context context) {
      String url = "http://35.232.3.8//api/sign_in?";
      try{
          getResponse(Request.Method.GET, url, null,
                  new  VolleyCallback(){
                      @Override
                      public void onSuccessResponse(String result) {
                          try {
                              JSONObject response = new JSONObject(result);
                              String token=response.getString("token");
                              if(token!=null)
                              {
                                  // creating a new user object
                                  User user = new User(response.getString("token"));
                                  // storing the user in shared preferences
                                  SharedPrefmanager.getInstance(context).userLogin(user);
                                  Toast.makeText(context,token,Toast.LENGTH_SHORT).show();
                                  Intent i=new Intent(context, HomePage.class);
                                  context.startActivity(i);
                                  flag=true;
                              }
                          } catch (JSONException e) {
                              e.printStackTrace();
                          }
                      }
                  },username,password,context);

      }
      catch (Exception e)
      {
          e.printStackTrace();
      }
      return flag;
  }
  @Override
  public boolean signup_interface(String username, String email, String password) {
    return true; // for debug
  }

  /**
   * check all fields are filled with data , connect to server , fill jason and send the request to
   * the server with all data needed
   *
   * @author mazen
   */
  private void userLogin() {

  }


  public void getResponse(int method, String url, JSONObject jsonValue, final VolleyCallback callback, final String username, final String password,final Context context) {
    // if everything is fine
    StringRequest stringRequest =
            new StringRequest(
                    Request.Method.POST,
                    url,
                    new Response.Listener<String>() {
                      @Override
                      public void onResponse(String response) {
                        // if no error in response
                        if (response != null) {
                          callback.onSuccessResponse(response);
                        }
                      }
                    },
                    new Response.ErrorListener() {
                      @Override
                      public void onErrorResponse(VolleyError error) {
                        Toast.makeText(
                                context,
                                "ServerError",
                                Toast.LENGTH_SHORT)
                                .show();
                        error.getMessage();
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
    VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
  }

    @Override
    public boolean createPost_interface(String type, String title, String mainPost, int communityID) {
        return false;
    }
}
