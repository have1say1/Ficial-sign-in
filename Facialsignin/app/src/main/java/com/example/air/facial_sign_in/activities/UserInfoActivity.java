package com.example.air.facial_sign_in.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.air.facial_sign_in.R;

public class UserInfoActivity extends AppCompatActivity {

    private TextView userid;
    private TextView username;
    private TextView usersex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);
        userid = findViewById(R.id.user_setting_id);
        username = findViewById(R.id.user_setting_name);
        usersex = findViewById(R.id.user_setting_sex);

        SharedPreferences mSharedPreferences = getSharedPreferences("LoginState", Context.MODE_PRIVATE);
        userid.setText(mSharedPreferences.getString("userid",null));
        username.setText(mSharedPreferences.getString("username",null));
        Log.d("ddd",mSharedPreferences.getInt("usersex",0) + "");
        Log.d("ddd",mSharedPreferences.getInt("usersex",0) + "");
        usersex.setText( (mSharedPreferences.getInt("usersex",0) == 1)?"男":"女");
    }


    public void onClickBack(View v) {
        // TODO Auto-generated method stub
        this.finish();
    }
}
