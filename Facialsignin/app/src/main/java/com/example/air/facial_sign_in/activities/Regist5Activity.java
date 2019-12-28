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

public class Regist5Activity extends AppCompatActivity {
    Intent intent;
    private String phone_number;
    private String username;
    private String organization;
    private int sex;
    private String face;

    private EditText mEtUPassword;
    private EditText mEtUPwdConfirm;

    private static final String TAG ="regist5Activity" ;


    private String url ="http://123.56.96.92:3000/api/v1/user/reg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist5);
        initView();
        Bundle extras = getIntent().getExtras();
        phone_number = extras.getString("phone_number");
        username = extras.getString("username");
        organization = extras.getString("organization");
        sex = extras.getInt("sex");
        face = extras.getString("face");
    }

    /**
     * 初始化组件
     */
    private void initView() {
        mEtUPassword = (EditText) findViewById(R.id.regist_password);
        mEtUPwdConfirm = (EditText) findViewById(R.id.regist_confirm_password);
    }


    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.next:
                register();
                break;
        }
    }
    public void onClickBack(View v) {
        // TODO Auto-generated method stub
        this.finish();
    }

    /**
     *  POST方式Register
     */
    private void register() {
        //获得对象的字段的值，然后转成string类型，并且去掉前后空白
        //ToString()是转化为字符串的方法 Trim()是去两边空格的方法
        final String password = mEtUPassword.getText().toString().trim();
        final String password_confirm =  mEtUPwdConfirm.getText().toString().trim();
        int res =checkDataValid(password,password_confirm);
        if(res == 1){
            new Thread(){
                @Override
                public void run() {
                    HttpUtils httpUtils = new HttpUtils();
                    //转换为JSON
                    String user_regist = httpUtils.registJson(phone_number, password, username, face, organization, sex);
                    Log.d(TAG, "user_regist:" + user_regist);

                    try {
                        String result = httpUtils.login(url, user_regist);
                        Log.d(TAG, "regist5Activity返回结果:" + result);

                        //保存数据到SharedPreferences
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
                                    Toast.makeText(Regist5Activity.this, "注册成功", Toast.LENGTH_SHORT).show();

                                    //保存登陆状态数据到SharedPreferences，注册成功即登录true
                                    SharedPreferences mSharedPreferences = getSharedPreferences("LoginState", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                                    editor.putBoolean("IsLogin",true);
                                    editor.apply();

                                    intent = new Intent(Regist5Activity.this, MainActivity.class);
                                    startActivity(intent);
                                }else{
                                        Toast.makeText(Regist5Activity.this, userinfo.getMSG(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }.start();
        }
        else {
            mEtUPassword.setText("");
            mEtUPwdConfirm.setText("");
        }
    }

    private int checkDataValid(String pwd,String pwd_confirm){
        if( TextUtils.isEmpty(pwd) | TextUtils.isEmpty(pwd_confirm)) {
            Toast.makeText(Regist5Activity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return 0;
        }
        if(!pwd.equals(pwd_confirm)){
            Toast.makeText(Regist5Activity.this, "两次输入的密码需保持一致", Toast.LENGTH_SHORT).show();
            return 0;
        }
        if((pwd.equals(pwd_confirm))&&(pwd.length()<8)){
            Toast.makeText(Regist5Activity.this, "密码长度需大于八位", Toast.LENGTH_SHORT).show();
            return 0;
        }
        return 1;
    }
}
