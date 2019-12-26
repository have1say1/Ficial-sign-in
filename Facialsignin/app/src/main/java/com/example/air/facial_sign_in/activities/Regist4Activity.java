package com.example.air.facial_sign_in.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.air.facial_sign_in.R;


public class Regist4Activity extends AppCompatActivity {
    Intent intent;
    private String phone_number;
    private String username;
    private String organization;
    private int sex;
    private String face = "myface";
    private static final String TAG ="Regist4Activity" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist4);
        //initView();
        Bundle extras = getIntent().getExtras();
        phone_number = extras.getString("phone_number");
        username = extras.getString("username");
        organization = extras.getString("organization");
        sex = extras.getInt("sex");
    }

    private void initView() {

    }

    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.next:
//                Log.d(TAG, "phone_number:" + phone_number);
//                Log.d(TAG, "username:" + username);
//                Log.d(TAG, "organization:" + organization);
//                Log.d(TAG, "sex:" + sex);
                intent = new Intent(Regist4Activity.this, Regist5Activity.class);
                intent.putExtra("username",username);
                intent.putExtra("organization",organization);
                intent.putExtra("sex",sex);
                intent.putExtra("phone_number",phone_number);
                intent.putExtra("face",face);
                startActivity(intent);
                break;

        }
    }
    public void onClickBack(View v) {
        // TODO Auto-generated method stub
        this.finish();
    }
}
