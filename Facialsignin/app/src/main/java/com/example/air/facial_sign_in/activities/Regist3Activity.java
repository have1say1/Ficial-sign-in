package com.example.air.facial_sign_in.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.air.facial_sign_in.R;

public class Regist3Activity extends AppCompatActivity {
    Intent intent;
    private String phone_number;
    //用户名
    private EditText mEtUName;
    //公司/组织
    private EditText mEtOrganization;
    //性别
    private RadioGroup rdSex;
    private int sex =0;
    private static final String TAG ="Regist3Activity" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist3);
        initView();
        rdSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.regist_male: //选中的是男
                        sex =1;
                        break;
                        case R.id.regist_female: //选中的是女
                            sex =2;
                            break;
            }
        }
    });

        Bundle extras = getIntent().getExtras();
        phone_number = extras.getString("phone_number");
    }

    private void initView() {
        mEtUName = (EditText) findViewById(R.id.regist_user_name);
        mEtOrganization = (EditText) findViewById(R.id.regist_organization);
        rdSex = (RadioGroup) findViewById(R.id.rg_group);
    }


    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.next:
                //Log.d(TAG, "phone_number:" + phone_number);
                intent = new Intent(Regist3Activity.this, Regist4Activity.class);
                String username = mEtUName.getText().toString().trim();
                String organization = mEtOrganization.getText().toString().trim();
                intent.putExtra("username",username);
                intent.putExtra("organization",organization);
                intent.putExtra("sex",sex);
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
