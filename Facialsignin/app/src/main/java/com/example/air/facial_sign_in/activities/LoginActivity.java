package com.example.air.facial_sign_in.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.air.facial_sign_in.R;
import com.example.air.facial_sign_in.model.UserInfo;
import com.example.air.facial_sign_in.util.HttpUtils;
import com.google.gson.Gson;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    Intent intent;
    private static final String TAG ="LoginActivity" ;
    //手机号
    private EditText mEtPhonum;
    //密码
    private EditText mEtPwd;
    //登录按键

    private String url ="http://123.56.96.92:3000/api/v1/user/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }


    /**
     * 初始化组件
     */
    private void initView() {
        mEtPhonum = (EditText) findViewById(R.id.phone_number);
        mEtPwd = (EditText) findViewById(R.id.password);
    }

    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.login_bt:
                login();
                break;
            case R.id.forget_bt:
                intent = new Intent(LoginActivity.this, ForgetActivity.class);
                startActivity(intent);
                break;
            case R.id.regist_bt:
                intent = new Intent(LoginActivity.this, Regist1Activity.class);
                startActivity(intent);
                break;
            case R.id.testbutton1:
                intent = new Intent(LoginActivity.this, PersonStatisticsActivity.class);
                startActivity(intent);
                break;

        }
    }


    private void login() {
        final String phone_number = mEtPhonum.getText().toString().trim();
        final String password = mEtPwd.getText().toString().trim();

        if(TextUtils.isEmpty(phone_number) || TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this, "用户名或者密码不能为空", Toast.LENGTH_SHORT).show();
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
                    String result = httpUtils.login(url, user);
                    Log.d(TAG, "LoginActivity返回结果:" + result);

                    //保存用户数据到SharedPreferences
                    //getSharedPreferences第一个参数是文件名称，第二个参数是操作模式
                    SharedPreferences mSharedPreferences = getSharedPreferences("Userdata", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putString("all_userdata",result);
                    editor.apply();


                    //解析数据到类UserInfo
                    Gson gson = new Gson();
                    final UserInfo userinfo = gson.fromJson(result, UserInfo.class);//result就是服务器返回的Json字符串
                    //更新UI,在UI线程中
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(userinfo.getErrorCode() == 0){
                                //保存登陆状态数据到SharedPreferences
                                SharedPreferences mSharedPreferences = getSharedPreferences("LoginState", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = mSharedPreferences.edit();
                                editor.putBoolean("IsLogin",true);
                                editor.apply();

                                intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            }else{
                                if(!(TextUtils.isEmpty(phone_number) || TextUtils.isEmpty(password))){
                                    Toast.makeText(LoginActivity.this, "用户名或者密码错误", Toast.LENGTH_SHORT).show();
                                }
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