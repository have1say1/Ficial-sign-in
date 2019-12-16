package com.example.air.facial_sign_in.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.air.facial_sign_in.R;

public class MessageActivity extends AppCompatActivity {
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
    }
    public void onClickBack(View v) {
        // TODO Auto-generated method stub
        this.finish();
    }
    public void onClickBar(View v){
        // TODO Auto-generated method stub
        switch (v.getId()) {
//                case R.id.tv2:
//                    intent = new Intent(MainActivity.this, GroupInfoActivity.class);
//                    startActivity(intent);
//                    System.out.println("2");
//                    break;
            case R.id.sigin:
                intent = new Intent(MessageActivity.this, MainActivity.class);
                startActivity(intent);
                System.out.println("3");
                break;
            case R.id.message:

                break;
            case R.id.my:
                intent = new Intent(MessageActivity.this, UserDetailActivity.class);
                startActivity(intent);
                System.out.println("1");
                break;

        }

    }

}


