package com.example.air.facial_sign_in.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.air.facial_sign_in.R;


public class Regist1Activity extends AppCompatActivity {
    Intent intent;
    //手机号
    private EditText mEtPhonum;

    private static final String TAG ="Regist1Activity" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist1);
        initView();
    }

    private void initView() {
        mEtPhonum = (EditText) findViewById(R.id.regist_phone_number);
    }

    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.next:
                intent = new Intent(Regist1Activity.this, Regist3Activity.class);
                String phone_number = mEtPhonum.getText().toString().trim();
                intent.putExtra("phone_number",phone_number);
                startActivity(intent);
                break;

        }
    }
    public void onClickBack(View v) {
        // TODO Auto-generated method stub
        this.finish();
    }
}

