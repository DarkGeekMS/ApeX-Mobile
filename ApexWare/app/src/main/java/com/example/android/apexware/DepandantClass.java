package com.example.android.apexware;

public class DepandantClass {

    private final DataSupplier mSupplier;

    public DepandantClass(DataSupplier dataSupplier) {
        mSupplier = dataSupplier;
    }

    boolean login(String username, String password) {
        return mSupplier.login_interface(username, password);

    }
    // msupplier can be used at this point to call any function there in a generic use

}
