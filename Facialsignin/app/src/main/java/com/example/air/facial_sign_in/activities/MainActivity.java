package com.example.air.facial_sign_in.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

import com.example.air.facial_sign_in.R;
import com.example.air.facial_sign_in.model.Meeting;
import com.example.air.facial_sign_in.util.meeting.MeetingListService;
import com.example.air.facial_sign_in.widget.MeetingAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Intent intent;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private MeetingAdapter mRecycleViewAdapter;

    private List<Meeting> meetings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //判断登录状态
        SharedPreferences prefs = getSharedPreferences("LoginState", Context.MODE_PRIVATE);
        Boolean islogin = prefs.getBoolean("IsLogin",false);

        if(!islogin){
            intent=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_main);
//初始化线性布局管理器
        mLinearLayoutManager = new LinearLayoutManager(this);

        bindViews();
        try {
            sendRequestWithHttpURLConnection();
        }catch (Exception e){
            System.out.println(e);
        }


    }

    private void sendRequestWithHttpURLConnection() {
        //开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                MeetingListService s;
                Bundle bundle = getIntent().getExtras();
                String uid;
                int type;
                uid = "12345678_1";
                type = 0;
                MeetingListService r = new MeetingListService(uid,type);
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
                mRecycleViewAdapter = new MeetingAdapter(meetings);
                //设置适配器
                mRecyclerView.setAdapter(mRecycleViewAdapter);
            }
        });
    }

    private void bindViews() {
        mRecyclerView = findViewById(R.id.meeting_containner);
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

