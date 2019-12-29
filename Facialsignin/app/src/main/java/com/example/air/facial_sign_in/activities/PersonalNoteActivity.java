package com.example.air.facial_sign_in.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.air.facial_sign_in.R;

public class PersonalNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_note);
        Toast ts = Toast.makeText(getBaseContext(),"识别成功",Toast.LENGTH_LONG);
        ts.show();
    }
}
