package com.example.air.facial_sign_in.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.air.facial_sign_in.R;

public class MainActivity extends AppCompatActivity {
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyApplication application = (MyApplication)this.getApplicationContext();
        if(!application.getState()){
            intent=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.activity_main);
        }

    public void onClickBar(View v){
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.sigin:
                intent = new Intent(MainActivity.this, MeetingActivity.class);
                startActivity(intent);
                System.out.println("3");
                break;
            case R.id.my:
                intent = new Intent(MainActivity.this, UserDetailActivity.class);
                startActivity(intent);
                System.out.println("1");
                break;
            case R.id.message:
                intent = new Intent(MainActivity.this, MessageActivity.class);
                startActivity(intent);
                System.out.println("4");
                break;

        }

    }

    private class TextViewListener implements View.OnClickListener {
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {

//                case R.id.tv2:
//                    intent = new Intent(MainActivity.this, GroupInfoActivity.class);
//                    startActivity(intent);
//                    System.out.println("2");
//                    break;
                case R.id.sigin:

                    System.out.println("3");
                    break;
                case R.id.message:
                    intent = new Intent(MainActivity.this, MessageActivity.class);
                    startActivity(intent);
                    System.out.println("4");
                    break;
                case R.id.my:
                    intent = new Intent(MainActivity.this, UserDetailActivity.class);
                    startActivity(intent);
                    System.out.println("1");
                    break;

            }
        }
    }


}

