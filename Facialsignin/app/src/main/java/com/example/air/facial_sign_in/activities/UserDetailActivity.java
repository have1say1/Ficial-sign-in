package com.example.air.facial_sign_in.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.air.facial_sign_in.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserDetailActivity extends AppCompatActivity {
    Toast tst;
    private Intent intent;
    private TextView username;
    private CircleImageView userava;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        username = findViewById(R.id.user_name);
        userava = findViewById(R.id.circleImageView2);

        SharedPreferences mSharedPreferences = getSharedPreferences("LoginState", Context.MODE_PRIVATE);
        username.setText(mSharedPreferences.getString("username",null));
        final String face = mSharedPreferences.getString("userface",null);

       // userava.setImageBitmap(decodedByte);
    }
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.my_settings:
                intent=new Intent(UserDetailActivity.this,SettingActivity.class);
                System.out.println("2");
                startActivity(intent);
                break;
            case R.id.my_info:
                intent=new Intent(UserDetailActivity.this,UserInfoActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void onClickMessage(View v){
        intent = new Intent(this,MessageActivity.class);
        startActivity(intent);
    }

    public void onClickBack(View v) {
        // TODO Auto-generated method stub
        this.finish();
    }
    public void onClickBar(View v){
        // TODO Auto-generated method stub
        switch (v.getId()) {

            case R.id.sigin:
                intent = new Intent(UserDetailActivity.this, MainActivity.class);
                startActivity(intent);
                System.out.println("3");
                break;
            case R.id.message:
                intent = new Intent(UserDetailActivity.this, MessageActivity.class);
                startActivity(intent);
                System.out.println("4");
                break;
            case R.id.my:

                break;
        }

    }

}