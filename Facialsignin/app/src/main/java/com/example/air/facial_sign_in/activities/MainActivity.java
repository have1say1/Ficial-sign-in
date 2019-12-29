package com.example.air.facial_sign_in.activities;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.air.facial_sign_in.R;
import com.example.air.facial_sign_in.model.Meeting;
import com.example.air.facial_sign_in.model.UserInfo;
import com.example.air.facial_sign_in.util.meeting.MeetingListService;
import com.example.air.facial_sign_in.widget.MeetingAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Intent intent;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private MeetingAdapter mRecycleViewAdapter;
    private MyApplication application;
    private List<Meeting> meetings;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (MyApplication)this.getApplicationContext();
        //判断登录状态
        SharedPreferences prefs = getSharedPreferences("LoginState", Context.MODE_PRIVATE);
        Boolean islogin = prefs.getBoolean("IsLogin",false);

        if(!islogin){
            intent=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
        }
        else{
            SharedPreferences prefs2 = getSharedPreferences("Userdata", MainActivity.this.MODE_PRIVATE);
            String alluserdata = prefs2.getString("all_userdata",null);
            Gson gson = new Gson();
            UserInfo userinfo = gson.fromJson(alluserdata, UserInfo.class);
            userId = userinfo.getData().getPhoneNumber();
        }
        setContentView(R.layout.activity_main);
//初始化线性布局管理器
        mLinearLayoutManager = new LinearLayoutManager(this);

        bindViews();
        try {
            getJoinMeetingListRequest();
        }catch (Exception e){
            System.out.println(e);
        }


    }

    private void getJoinMeetingListRequest() {
        //开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                MeetingListService s;
                Bundle bundle = getIntent().getExtras();
                String uid;
                int type;
                type = 0;
                MeetingListService r = new MeetingListService(userId,type);
                try {
                    meetings = r.getModel();
                    showResponse();

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }
    private void getManageMeetingListRequest() {
        //开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                MeetingListService s;
                Bundle bundle = getIntent().getExtras();
                String uid;
                int type;
                type = 1;
                MeetingListService r = new MeetingListService(userId,type);
                try {
                    meetings = r.getModel();
                    showResponse();

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }
    private void showResponse() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //设置布局管理器
                mRecyclerView.setLayoutManager(mLinearLayoutManager);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                //初始化适配器
                mRecycleViewAdapter = new MeetingAdapter(meetings, new MeetingAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(int pos) {
                        intent = new Intent(MainActivity.this,MeetingActivity.class);
                        intent.putExtra("mid", meetings.get(pos).getMid());
                        intent.putExtra("mname", meetings.get(pos).getMname());
                        //Toast ts = Toast.makeText(getBaseContext(),meetings.get(pos).getMid(),Toast.LENGTH_LONG);
                        //ts.show();
                        startActivity(intent);
                    }
                });
                //设置适配器
                mRecyclerView.setAdapter(mRecycleViewAdapter);

            }
        });
    }

    private void bindViews() {
        mRecyclerView = findViewById(R.id.meeting_containner);
    }

    public void onClickList(View v){
        CardView v1 = findViewById(R.id.meeting_joinList);
        CardView v2 = findViewById(R.id.meeting_manageList);
        switch (v.getId()){
            case R.id.meeting_joinList:
                v1.setCardBackgroundColor(getResources().getColor(R.color.yellow,getTheme()));
                v2.setCardBackgroundColor(getResources().getColor(R.color.white,getTheme()));
                getJoinMeetingListRequest();
                break;
            case R.id.meeting_manageList:
                v2.setCardBackgroundColor(getResources().getColor(R.color.yellow,getTheme()));
                v1.setCardBackgroundColor(getResources().getColor(R.color.white,getTheme()));
                getManageMeetingListRequest();
                break;
        }

    }

    private void initData() throws Exception {
        MeetingListService s = new MeetingListService();
        String str = s.DataUtl();
        meetings = s.meetingsDAO(str);

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
            case R.id.join_organ:
                intent = new Intent(MainActivity.this, InviteActivity.class);
                startActivity(intent);
                break;


        }

    }
    public void onClickAdd(View v){
        intent = new Intent(MainActivity.this,AddMeeting.class);
        startActivity(intent);
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

