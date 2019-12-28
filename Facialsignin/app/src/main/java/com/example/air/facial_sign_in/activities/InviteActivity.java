package com.example.air.facial_sign_in.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.air.facial_sign_in.R;
import com.example.air.facial_sign_in.database.MeetingInfo;
import com.example.air.facial_sign_in.model.Meeting;
import com.example.air.facial_sign_in.model.UserInfo;
import com.example.air.facial_sign_in.util.HttpUtils;
import com.google.gson.Gson;
import com.jyn.vcview.VerificationCodeView;

import java.io.IOException;


public class InviteActivity extends AppCompatActivity implements VerificationCodeView.OnCodeFinishListener {
    Intent intent;
    private TextView textView;
    private VerificationCodeView verificationcodeview;
    private  String userId;
    private  String meetingId;
    private String url ="http://123.56.96.92:3000/api/v1/meeting/join";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        verificationcodeview = findViewById(R.id.verificationcodeview);

        textView = findViewById(R.id.text);
        verificationcodeview.setOnCodeFinishListener(this);
    }

    @Override
    public void onComplete(View view, String content) {
        if (view == verificationcodeview) {
            meetingId = content;
            invite();
//            textView.setText("输入结束：" + content);
        }
    }


    private void invite() {

        new Thread(){
            @Override
            public void run() {
                //获取userid数据
                SharedPreferences prefs = getSharedPreferences("Userdata", Context.MODE_PRIVATE);
                String alluserdata = prefs.getString("all_userdata",null);
                Gson gson = new Gson();
                UserInfo userinfo = gson.fromJson(alluserdata, UserInfo.class);
                userId = userinfo.getData().getPhoneNumber();
                Log.d("InviteActivity", " myuserId:" +  userId);

                HttpUtils httpUtils = new HttpUtils();
                //转换为JSON
                String meeting = httpUtils.inviteJson(userId,meetingId);
                Log.d("InviteActivity", "meeting:" + meeting);

                try {
                    final String result = httpUtils.login(url, meeting);
                    Log.d("InviteActivity", "InviteActivity返回结果:" + result);
                    //Gson gson = new Gson();
                    final Meeting meetinginfo = gson.fromJson(result, Meeting.class);
//                    final MeetingInfo meetinginfo = gson.fromJson(result, MeetingInfo.class);//result就是服务器返回的Json字符串
                    //更新UI,在UI线程中
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(meetinginfo.getErrorCode() == 0){
                                intent = new Intent(InviteActivity.this, MainActivity.class);
                                startActivity(intent);
                            }else{
                                if(meetinginfo.getMSG().equals("user already in this meeting !") )
                                {
                                    textView.setText("您已经加入此会议，请勿重复加入");
                                }
                                if(meetinginfo.getMSG().equals("Meeting Not Found"))
                                {
                                    textView.setText("该邀请码不存在或者已经失效");
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


    public void clear(View view) {
        verificationcodeview.setEmpty();
    }

    @Override
    public void onTextChange(View view, String content) {
        if (view == verificationcodeview) {
//            textView.setText("输入中：" + content);
        }
    }



    public void onClickBack(View v) {
        // TODO Auto-generated method stub
        this.finish();
    }

}

