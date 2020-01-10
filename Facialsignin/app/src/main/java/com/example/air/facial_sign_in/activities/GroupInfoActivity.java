package com.example.air.facial_sign_in.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.air.facial_sign_in.R;

public class GroupInfoActivity extends AppCompatActivity {
    private Intent intent;
    private TextView group_name;
    private TextView group_name2;
    private TextView group_location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);

        group_name = findViewById(R.id.group_name);
        group_name2 = findViewById(R.id.group_name2);
        group_location = findViewById(R.id.group_location);

        SharedPreferences mSharedPreferences = getSharedPreferences("LoginState", Context.MODE_PRIVATE);
        group_name.setText(mSharedPreferences.getString("meetingname",null));
        group_name2.setText(mSharedPreferences.getString("meetingname",null));
        group_location.setText(mSharedPreferences.getString("meetinglocation",null));
    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.entermemberlist:
                intent=new Intent(GroupInfoActivity.this,MemberListActivity.class);
                startActivity(intent);
                System.out.println("1");
                break;
            case R.id.circleImageView5:
                intent=new Intent(GroupInfoActivity.this,ShareActivity.class);
                startActivity(intent);
                System.out.println("2");
                break;
            default:
                break;
        }
    }
    public void onClickBack(View v) {
        // TODO Auto-generated method stub
        this.finish();
    }
}
