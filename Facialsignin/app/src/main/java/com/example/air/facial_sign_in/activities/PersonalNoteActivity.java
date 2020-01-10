package com.example.air.facial_sign_in.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.air.facial_sign_in.R;

public class PersonalNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_note);

    }
    public void onClickBack(View v) {
        // TODO Auto-generated method stub
        Intent intent = new Intent(PersonalNoteActivity.this,MeetingActivity.class);
        startActivity(intent);
    }
}
