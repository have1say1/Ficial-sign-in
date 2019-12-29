package com.example.air.facial_sign_in.activities;

import android.app.Application;


public class MyApplication extends Application {
    public  boolean state;
    private String uid;
    private String mid;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }
}