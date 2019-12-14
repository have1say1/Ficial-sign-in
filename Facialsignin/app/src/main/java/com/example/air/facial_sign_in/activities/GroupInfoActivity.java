package com.example.air.facial_sign_in.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.air.facial_sign_in.R;

public class GroupInfoActivity extends AppCompatActivity {
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_info);

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
