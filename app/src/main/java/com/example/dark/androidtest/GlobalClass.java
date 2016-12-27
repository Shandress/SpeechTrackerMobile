package com.example.dark.androidtest;

import android.app.Application;

import com.example.dark.androidtest.DataModel.User;

/**
 * Created by Dark on 12/21/2016.
 */

public class GlobalClass extends Application {
    public User currentUser;

    public GlobalClass() {
        currentUser = new User();
    }

}
