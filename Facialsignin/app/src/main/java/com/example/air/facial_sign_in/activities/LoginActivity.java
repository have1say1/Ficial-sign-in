package com.example.air.facial_sign_in.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.air.facial_sign_in.R;
import com.example.air.facial_sign_in.util.HttpUtils;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    Intent intent;
    private static final String TAG ="LoginActivity" ;
    //手机号
    private EditText mEtPhonum;
    //密码
    private EditText mEtPwd;
    //登录按键
    private Button mBtnLogin;

    private TextView mTvResult;
    private String url ="http://123.56.96.92:3000/api/v1/user/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login2);
        initView();
    }


    /**
     * 初始化组件
     */
    private void initView() {
        mEtPhonum = (EditText) findViewById(R.id.phone_number);
        mEtPwd = (EditText) findViewById(R.id.password);
        mBtnLogin = (Button) findViewById(R.id.login_bt);
        mTvResult = (TextView) findViewById(R.id.login_tv_result);
    }

    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.login_bt:
                login();
//                intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
//                MyApplication application = (MyApplication) this.getApplicationContext();
//                application.setState(true);
                break;
            case R.id.forget_bt:
                intent = new Intent(LoginActivity.this, ForgetActivity.class);
                startActivity(intent);
                break;
            case R.id.regist_bt:
                intent = new Intent(LoginActivity.this, Regist1Activity.class);
                startActivity(intent);
                break;

        }
    }


    private void login() {
        final String phone_number = mEtPhonum.getText().toString().trim();
        final String password = mEtPwd.getText().toString().trim();

        if(TextUtils.isEmpty(phone_number) || TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this, "用户名或者密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        new Thread(){
            @Override
            public void run() {
                HttpUtils httpUtils = new HttpUtils();
                //转换为JSON
                String user = httpUtils.bowlingJson(phone_number, password);
                //String user ="{\"phonenumber\":" + "\"" + phonenumber + "\"" + "," + "\"password\":" + "\"" + password + "\"" + "}";
                Log.d(TAG, "user:" + user);

                try {
                    final String result = httpUtils.login(url, user);
                    Log.d(TAG, "返回结果:" + result);
                    //更新UI,在UI线程中
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(result != null){
                                mTvResult.setText("登录成功");
                                intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
//                                MyApplication application = (MyApplication) this.getApplicationContext();
//                                application.setState(true);
                            }else{
                                mTvResult.setText("登录失败");
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();

    }
}