package com.example.air.facial_sign_in.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.air.facial_sign_in.R;

public class MeetingActivity extends AppCompatActivity {
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);
    }
    public void onClickDaka(View v){
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.daka_note:
                intent = new Intent(MeetingActivity.this, PersonalNoteActivity.class);
                startActivity(intent);
                System.out.println("1");
                break;

            case R.id.daka_static:
                intent = new Intent(MeetingActivity.this, StaticActivity.class);
                startActivity(intent);
                System.out.println("3");
                break;
            case R.id.daka_share:
                intent = new Intent(MeetingActivity.this, ShareActivity.class);
                startActivity(intent);
                System.out.println("4");
                break;
            case R.id.daka_info:
                intent = new Intent(MeetingActivity.this, GroupInfoActivity.class);
                startActivity(intent);
                System.out.println("4");
                break;
            case R.id.cam:
                intent = new Intent(MeetingActivity.this, ChooseFunctionActivity.class);
                startActivity(intent);
                System.out.println("4");
                break;

        }

    }
}
