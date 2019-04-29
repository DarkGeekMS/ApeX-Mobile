package com.example.android.apexware;

import android.content.Context;
import static com.example.android.apexware.VerifyCode.mock_code;

/**
 * this class is a simulation to the back end service that sends dummy objects and responses to the
 * activities to debug without getting affected with the bacck end and connection proplems
 *
 * @author mostafa
 */
public class MockRestService implements DataSupplier {

  /**
   * this function only checks if rhe sent parameters are empty strings or no and whatever their
   * values are it returns true
   *
   * @param username : the user name field in the form
   * @param password : the password field in the form
   * @return : true if the parameters are not empty strings
   */
  @Override
  public boolean login_interface(String username, String password, final Context context) {
    if (username.equals("") && password.equals("")) return false;
    return true;
  }

  /**
   * this function only checks if rhe sent parameters are empty strings or no and whatever their
   * values are it returns true
   *
   * @param username : the user name field in the form
   * @param email : the user email field in the form
   * @param password : the password field in the form
   * @return true if the parameters are not empty strings
   */
  @Override
  public boolean signup_interface(String username, String email, String password) {
    if (username.equals("") || password.equals("") || email.equals(" ")) return false;
    return true;
  }

    /**
     * @param type        : post type (link,text,image)
     * @param title       :post title (necessary)
     * @param mainPost    : main post (link , lines of text , image uri)
     * @param communityID : id of apexcom chosen to post to
     * @return true always to let the program continue in case of debugging with no server
     */
    @Override
    public boolean createPost_interface(String type, String title, String mainPost, int communityID) {
        return true;
    }

  /**
   * used in cases of forgetting password to send verify mail to the user
   *
   * @param email    user email
   * @param username user name
   */
  @Override
  public void verify_forget_pass_interface(String email, String username,final Context context) {
    mock_code = "0123";
  }

    /**
     * used in cases of forgetting username to send verify mail to the user
     *
     * @param email    user email
     * @param password the password user knows
     */
    @Override
    public void verify_forget_user_interface(String email, String password,final Context context) {
        mock_code = "0123";
        // todo send automatic mail with code let it be 0123
    }

  /**
   * @param email email of user
   * @param code  code he entered and sent to be checked
   * @return user name of the user if correct
   */
  @Override
  public String confirmCode_interface(String email, String code,final Context context) {
    if (code.equals("0123")) return "mock user";
     return "wrong code";
  }
}
