package com.example.air.facial_sign_in.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.BottomSheetDialog;
import android.widget.EditText;

import com.example.air.facial_sign_in.R;
import com.example.air.facial_sign_in.model.Meeting;
import com.example.air.facial_sign_in.model.UserInfo;
import com.example.air.facial_sign_in.util.meeting.AddMeetingService;
import com.google.gson.Gson;

public class AddMeeting extends AppCompatActivity {
    MyApplication application;
    View dialogView;
    BottomSheetDialog dialog;
    String mname;
    int startTime;
    int endTime ;
    String location ;
    String userId;
    String checkRule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        application = (MyApplication)this.getApplicationContext();
    }
    public void showDialog(View view){
        dialogView= LayoutInflater.from(AddMeeting.this)
                .inflate(R.layout.meeting_days_choose,null);
        dialog=new BottomSheetDialog(AddMeeting.this);//实例化必须在这里进行
        dialog.setContentView(dialogView);
        dialog.show();

    }
    public void submitAddMeeting(View v){
        mname = ((EditText)findViewById(R.id.add_meeting_name)).getText().toString();
        //startTime = Integer.parseInt(((EditText)findViewById(R.id.add_meeting_st)).getText().toString());
        //endTime = Integer.parseInt(((EditText)findViewById(R.id.add_meeting_ed)).getText().toString());
        location = ((EditText)findViewById(R.id.add_meeting_place)).getHint().toString();
        //userId = application.getUid();
        //获取userid数据
        SharedPreferences prefs = getSharedPreferences("Userdata", AddMeeting.this.MODE_PRIVATE);
        String alluserdata = prefs.getString("all_userdata",null);
        Gson gson = new Gson();
        UserInfo userinfo = gson.fromJson(alluserdata, UserInfo.class);
        userId = userinfo.getData().getPhoneNumber();
        //TODO get check_rule
         checkRule = "1_2_3";
        new Thread(new Runnable() {
            @Override
            public void run() {
                AddMeetingService s = new AddMeetingService(new Meeting("fengmian",mname,"","","字节跳动总部",34,56,checkRule,100,566,userId,1));
                try {
                    String rs = s.sendPost();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(AddMeeting.this,MainActivity.class);
                startActivity(intent);

            }
        }).start();
    }
}
