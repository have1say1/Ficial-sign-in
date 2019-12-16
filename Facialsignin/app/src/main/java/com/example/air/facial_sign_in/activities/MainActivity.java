package com.example.air.facial_sign_in.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.air.facial_sign_in.R;

public class MainActivity extends AppCompatActivity {
    private TextView tev1;
    private TextView tev2;
    private TextView tev3;
    private TextView tev4;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tev1 = (TextView) findViewById(R.id.my);
        tev1.setOnClickListener(new TextViewListener());
        TextView tev2 = (TextView) findViewById(R.id.tv2);
        tev2.setOnClickListener(new TextViewListener());
        TextView tev3 = (TextView) findViewById(R.id.tv3);
        tev3.setOnClickListener(new TextViewListener());
        TextView tev4 = (TextView) findViewById(R.id.message);
        tev4.setOnClickListener(new TextViewListener());
        }

    private class TextViewListener implements View.OnClickListener {
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.my:
                    intent = new Intent(MainActivity.this, UserDetailActivity.class);
                    startActivity(intent);
                    System.out.println("1");
                    break;
                case R.id.tv2:
                    intent = new Intent(MainActivity.this, GroupInfoActivity.class);
                    startActivity(intent);
                    System.out.println("2");
                    break;
                case R.id.tv3:
                    intent = new Intent(MainActivity.this, CheckInModeActivity.class);
                    startActivity(intent);
                    System.out.println("3");
                    break;
                case R.id.message:
                    intent = new Intent(MainActivity.this, MessageActivity.class);
                    startActivity(intent);
                    System.out.println("4");
                    break;

            }
        }
    }


}

