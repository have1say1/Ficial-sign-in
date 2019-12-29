package com.example.air.facial_sign_in.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.air.facial_sign_in.R;

import lecho.lib.hellocharts.view.LineChartView;

public class GroupStatisticsActivity extends AppCompatActivity {
    Intent intent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_attendance_statistics);
    }

    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.person_statistics:
                intent = new Intent(GroupStatisticsActivity.this,PersonStatisticsActivity.class);
                startActivity(intent);
                break;

        }
    }


}
