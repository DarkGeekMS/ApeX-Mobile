package com.example.android.apexware;
/**
 * the interface for every function used by the service (mock up or backend) and they override these
 * methods in their classes
 *
 * @author mostafa
 */
public interface DataSupplier {

    /**
     * @param username
     * @param password
     * @return : return true if login successful and user exists
     */
    boolean login_interface(String username, String password);
    /**
     * send string of all details to create a new user
     *
     * @param username
     * @param email
     * @param password
     * @return : return true if it is succesful
     */
    boolean signup_interface(String username, String email, String password);
}