package com.example.air.facial_sign_in.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.air.facial_sign_in.R;

public class MeetingActivity extends AppCompatActivity {
    private Intent intent;
    private TextView title;
    private MyApplication application;
    private String mname;
    private String mid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);
        title = (TextView) findViewById(R.id.meeting_title);
        intent = getIntent();
        title.setText(intent.getStringExtra("mname"));
        mname = intent.getStringExtra("mname");
        mid = intent.getStringExtra("mid");

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
                intent = new Intent(MeetingActivity.this, MyStatisticsActivity.class);
                startActivity(intent);
                System.out.println("3");
                break;
            case R.id.daka_share:
                intent = new Intent(MeetingActivity.this, ShareActivity.class);
                intent.putExtra("mname",mname);
                intent.putExtra("mid",mid);
                startActivity(intent);
                System.out.println("4");
                break;
            case R.id.daka_info:
                intent = new Intent(MeetingActivity.this, GroupInfoActivity.class);
                startActivity(intent);
                System.out.println("4");
                break;
            case R.id.cam:
                intent = new Intent(MeetingActivity.this, RegisterAndRecognizeActivity.class);
                startActivity(intent);
                System.out.println("4");
                break;

        }

    }
}
