package com.example.air.facial_sign_in.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.air.facial_sign_in.R;
import com.zhy.android.percent.support.PercentLinearLayout;

public class PersonalNoteActivity extends AppCompatActivity {
    private TextView username;
    private TextView num;
    private PercentLinearLayout a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_note);
        SharedPreferences prefs = getSharedPreferences("LoginState", Context.MODE_PRIVATE);
        String meetingid = prefs.getString("meetingid",null);
        int flag = prefs.getInt(meetingid,0);
        username = findViewById(R.id.username);
        num = findViewById(R.id.qdnum);
        a = findViewById(R.id.kejian);
        if(flag == 1){
           num.setText("1");
           SharedPreferences mSharedPreferences = getSharedPreferences("LoginState", Context.MODE_PRIVATE);
           username.setText(mSharedPreferences.getString("username",null));
           a.setVisibility(View.VISIBLE);
        }
    }
    public void onClickBack(View v) {
        // TODO Auto-generated method stub
        Intent intent = new Intent(PersonalNoteActivity.this,MeetingActivity.class);
        startActivity(intent);
    }
}
