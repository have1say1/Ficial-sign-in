package com.example.air.facial_sign_in.activities;

import android.app.Application;

/**
 * Created by jialichun on 2019/12/17.
 */

public class MyApplication extends Application {
    public  boolean state;
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean s) {
        state = s;
    }


}