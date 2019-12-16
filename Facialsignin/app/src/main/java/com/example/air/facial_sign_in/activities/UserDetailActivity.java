package com.example.air.facial_sign_in.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.air.facial_sign_in.R;


public class UserDetailActivity extends AppCompatActivity {
    Toast tst;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
    }
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.qrcode_share:
                intent=new Intent(UserDetailActivity.this,ShareActivity.class);
                System.out.println("1");
                startActivity(intent);
                break;
            case R.id.my_settings:
                intent=new Intent(UserDetailActivity.this,UserInfoActivity.class);
                System.out.println("2");
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