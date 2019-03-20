package com.example.android.apexware;

public class RestService implements DataSupplier {
    @Override
    public boolean login_interface(String username, String password) {
        return false;
    }

    @Override
    public boolean signup_interface(String username, String email, String password) {
        return false;
    }
}
