package com.example.android.apexware;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import org.junit.Test;

import static org.junit.Assert.*;
/**
 * this class tests that the dependant class calls the right interface method for the right supplier
 * without problems
 *
 * @author mostafa
 */
public class DepandantClassTest extends AppCompatActivity {
    private DepandantClass restClient_mock = new DepandantClass(new MockRestService());

    /**
     * check that the empty strings will always return false if the data suppier is the mock service
     */
    @Test
    public void login_false() {
        assertFalse(restClient_mock.login("", "",null ));
    }

    /**
     * check that a random dummy user will always return true if the data suppier is the mock service
     */
    @Test
    public void login_true() {
        assertTrue(restClient_mock.login("mostafa", "1234",null));
    }

    /**
     * check that the empty strings will always return false if the data suppier is the mock service
     */
    @Test
    public void signup_false() {
        assertFalse(restClient_mock.signup("", "", ""));
    }

    /**
     * check that a random dummy user will always return true if the data suppier is the mock service
     */
    @Test
    public void signup_true() {
        assertTrue(restClient_mock.signup("mostafa", "mostafa@gmail.com", "1234"));
    }
}