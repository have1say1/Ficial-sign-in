package com.example.air.facial_sign_in.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.air.facial_sign_in.R;


public class CheckInModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rule_of_calendar);
    }


    public void onClickBack(View v) {
        // TODO Auto-generated method stub
        this.finish();
    }
}
