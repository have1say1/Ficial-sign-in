package com.example.air.facial_sign_in.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.air.facial_sign_in.R;
import com.example.air.facial_sign_in.model.Meeting;
import com.example.air.facial_sign_in.model.UserInfo;
import com.example.air.facial_sign_in.util.HttpUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.Chart;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.model.Column;

import java.util.*;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MyStatisticsActivity extends Activity {
    Intent intent;
    private static final String TAG ="MyStatisticsActivity" ;
    private String url ="http://123.56.96.92:3000/api/v1/check/statistic";

    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH)+1;
    int day = calendar.get(Calendar.DAY_OF_MONTH);

    private LineChartView lineChart;

    private ColumnChartView mColumnChartView;

    private String userId;
    private String meetingId = "126133";

    //柱状图
    /*========== 数据相关 ==========*/
    private ColumnChartData mColumnChartData;               //柱状图数据
    public final static String[] xValues = new String[]{"正常", "迟到", "早退", "缺席", "请假"};
    public int[] yValues = new int[]{1, 0, 0, 0, 0};

    //折线图
    String[] date = {"01-07","08-14","15-21","22-28","29-31"};//X轴的标注
    String[] date2 = {"01-07","08-14","15-21","22-28","29"};//X轴的标注
    public int[] score= {0,1,0,0,0};//图表的数据
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_attendance);
        //从服务器申请数据
        mystatitics();

        //折线图
        getAxisXLables();//获取x轴的标注
//        getAxisPoints();//获取坐标点
        dateshow();
        initLineChart();//初始化

        //柱状图
        initView();
    }

    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
//            case R.id.group_statistics:
//                intent = new Intent(MyStatisticsActivity.this,GroupStatisticsActivity.class);
//                startActivity(intent);
//                break;
        }
    }

    public void onClickBack(View v) {
        // TODO Auto-generated method stub
        this.finish();
    }
///////////////////////////////////折线图////////////////////////////////////
    /**
     * 初始化LineChart的一些设置
     */
    private void initLineChart(){
        lineChart = (LineChartView)findViewById(R.id.my_chart1);
        lineChart.setOnValueTouchListener(new ValueTouchListener0());
        Line line = new Line(mPointValues).setColor(Color.parseColor("#FFCD41"));  //折线的颜色
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.SQUARE）
        line.setCubic(false);//曲线是否平滑
//	        line.setStrokeWidth(3);//线条的粗细，默认是3
        line.setFilled(false);//是否填充曲线的面积
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
//		    line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用直线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(true);  //X轴下面坐标轴字体是斜的显示还是直的，true是斜的显示
//	        axisX.setTextColor(Color.WHITE);  //设置字体颜色
        axisX.setTextColor(Color.parseColor("#D6D6D9"));//灰色
//	        axisX.setName("未来几天的天气");  //表格名称
        axisX.setTextColor(Color.GRAY);// 设置X轴文字颜色
        axisX.setTextSize(14);// 设置X轴文字大小
        axisX.setMaxLabelChars(7); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisValues.length
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
//	        data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(true); //x 轴分割线


        Axis axisY = new Axis();  //Y轴
        axisY.setName("");//y轴标注
        axisY.setTextColor(Color.GRAY);// 设置Y轴文字颜色
        axisY.setTextSize(14);//设置字体大小
        data.setAxisYLeft(axisY);  //Y轴设置在左边
        //data.setAxisYRight(axisY);  //y轴设置在右边
        //设置行为属性，支持缩放、滑动以及平移
        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);  //缩放类型，水平
        lineChart.setMaxZoom((float) 3);//缩放比例
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);
        /**注：下面的7，10只是代表一个数字去类比而已
         * 见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
         * 下面几句可以设置X轴数据的显示个数（x轴0-7个数据），当数据点个数小于（29）的时候，缩小到极致hellochart默认的是所有显示。当数据点个数大于（29）的时候，
         * 若不设置axisX.setMaxLabelChars(int count)这句话,则会自动适配X轴所能显示的尽量合适的数据个数。
         * 若设置axisX.setMaxLabelChars(int count)这句话,
         * 33个数据点测试，若 axisX.setMaxLabelChars(10);里面的10大于v.right= 7; 里面的7，则
         刚开始X轴显示7条数据，然后缩放的时候X轴的个数会保证大于7小于10
         若小于v.right= 7;中的7,反正我感觉是这两句都好像失效了的样子 - -!
         * 并且Y轴是根据数据的大小自动设置Y轴上限
         * 若这儿不设置 v.right= 7; 这句话，则图表刚开始就会尽可能的显示所有数据，交互性太差
         */
        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = 0;
        v.right= 7;
        v.top = 6;
        lineChart.setCurrentViewport(v);


    }

    /**
     * 日期的显示
     */
    private void dateshow() {
        TextView person_month = (TextView) findViewById(R.id.person_month1);
        person_month.setText(month + "月数据");
        TextView oneweek = (TextView) findViewById(R.id.oneweek1);
        if ((day >= 1) && (day <= 7)) {
            oneweek.setText(year + "/" + "0" + month + "/" + "01" + "-" + year + "/" + "0"+ month + "/" + "07");
        }
        if ((day >= 8) && (day <= 14)) {
            oneweek.setText(year + "/" + "0" + month + "/" + "08" + "-" + year + "/" + "0"+ month + "/" + "14");
        }
    }

    /**
     * X 轴的显示
     */
    private void getAxisXLables(){
        if(month == 1)
        {
            for (int i = 0; i < date.length; i++) {
                mAxisXValues.add(new AxisValue(i).setLabel(date[i]));}
        }
        if(month == 2)
        {
            for (int i = 0; i < date2.length; i++) {
                mAxisXValues.add(new AxisValue(i).setLabel(date2[i]));}
        }
    }
    /**
     * 图表的每个点的显示
     */
    private void getAxisPoints(){
        for (int i = 0; i < score.length; i++) {
            mPointValues.add(new PointValue(i, score[i]));
        }
    }

    private class ValueTouchListener0 implements LineChartOnValueSelectListener {

        @Override
        public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
            Toast.makeText(MyStatisticsActivity.this, date[pointIndex]+"日正常签到次数 : " +
                    (int)value.getY(),Toast.LENGTH_SHORT).show();
//            intent = new Intent(MyStatisticsActivity.this,PersonStatisticsActivity1.class);
//            startActivity(intent);
              //Toast.makeText(MyStatisticsActivity.this, "Selected: " + value, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

    }


////////////////////////////////////折线图////////////////////////////////////////
////////////////////////////////////柱状图////////////////////////////////////////
    private void initView() {
        mColumnChartView = (ColumnChartView) findViewById(R.id.my_chart2);
        mColumnChartView.setOnValueTouchListener(new ValueTouchListener());

        /*========== 柱状图数据填充 ==========*/
        List<Column> columnList = new ArrayList<>(); //柱子列表
        List<SubcolumnValue> subcolumnValueList;     //子柱列表（即一个柱子，因为一个柱子可分为多个子柱）
        List<AxisValue> axisValues = new ArrayList<>();
        for (int i = 0; i < xValues.length; ++i) {
            subcolumnValueList = new ArrayList<>();
            subcolumnValueList.add(new SubcolumnValue(yValues[i], ChartUtils.pickColor()));

            Column column = new Column(subcolumnValueList);
            column.setHasLabels(true);                    //设置列标签
            //            column.setHasLabelsOnlyForSelected(true);       //只有当点击时才显示列标签

            columnList.add(column);

            //设置坐标值
            axisValues.add(new AxisValue(i).setLabel(xValues[i]));
        }

        mColumnChartData = new ColumnChartData(columnList);               //设置数据


        /*===== 坐标轴相关设置 =====*/
        Axis axisX = new Axis(axisValues); //将自定义x轴显示值传入构造函数
        Axis axisY = new Axis().setHasLines(true); //setHasLines是设置线条
        axisX.setName("");    //设置横轴名称
        axisY.setName("");    //设置竖轴名称
        axisX.setTextColor(Color.GRAY);// 设置X轴文字颜色
        axisY.setTextColor(Color.GRAY);// 设置Y轴文字颜色
        axisX.setTextSize(14);// 设置X轴文字大小

        mColumnChartData.setAxisXBottom(axisX); //设置横轴
        mColumnChartData.setAxisYLeft(axisY);   //设置竖轴

        //以上所有设置的数据、坐标配置都已存放到mColumnChartData中，接下来给mColumnChartView设置这些配置
        mColumnChartView.setColumnChartData(mColumnChartData);


        /*===== 设置竖轴最大值 =====*/
        //法一：
        Viewport v = mColumnChartView.getMaximumViewport();
        v.top = 6;
        mColumnChartView.setCurrentViewport(v);
                /*法二：
                Viewport v = mColumnChartView.getCurrentViewport();
                v.top = 100;
                mColumnChartView.setMaximumViewport(v);
                mColumnChartView.setCurrentViewport(v);*/
    }

    private class ValueTouchListener implements ColumnChartOnValueSelectListener {

        @Override
        public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
            Toast.makeText(MyStatisticsActivity.this, xValues[columnIndex]+"次数 : " +
                    (int)value.getValue(),Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub
        }
    }
////////////////////////////////////柱状图////////////////////////////////////////

    //Json数据解析
    private void StatisticsJson(String result){
        String monthtime = "2020-0"+ month;
        JSONObject rs = JSONObject.fromObject(result);
        if(rs.getInt("errorcode") == 0){
            JSONObject rsdata = JSONObject.fromObject(rs.get("data"));
            JSONArray rsperson = JSONArray.fromObject(rsdata.get("personal"));
            for (int i = 0 ; i < rsperson.size() ; i++){
                JSONObject rsJson = rsperson.getJSONObject(i);
                Log.d(TAG, "MyStatisticsActivity返回结果1111:" + i + rsJson);
                if(rsJson.getString("datetime").substring(0,7).equals(monthtime) )
                {
                    //折线图，只记录正常情况数据
                    if(1 <= Integer.parseInt(rsJson.getString("datetime").substring(8,10))  && Integer.parseInt(rsJson.getString("datetime").substring(8,10))<=7) {
                        if (rsJson.getString("status").equals("12")) {
                            score[0] += 1;
                        }
                    }
                    if(8 <= Integer.parseInt(rsJson.getString("datetime").substring(8,10))  && Integer.parseInt(rsJson.getString("datetime").substring(8,10)) <= 14) {
                        if (rsJson.getString("status").equals("12")) {
                            score[1] += 1;
                        }
                    }

                    //柱状图
                    if(1 <= day  && day <=7)
                    {
                        if(1 <= Integer.parseInt(rsJson.getString("datetime").substring(8,10))  && Integer.parseInt(rsJson.getString("datetime").substring(8,10))<=7) {
                            //正常
                            if (rsJson.getString("status").equals("12")) {
                                yValues[0] += 1;
                            }
                            //迟到
                            if (rsJson.getString("status").equals("22") || rsJson.getString("status").equals("21")) {
                                yValues[1] += 1;
                            }
                            //早退
                            if (rsJson.getString("status").equals("11") || rsJson.getString("status").equals("10") || rsJson.getString("status").equals("10")) {
                                yValues[2] += 1;
                            }
                            //缺席
                            if (rsJson.getString("status").equals("00")) {
                                yValues[3] += 1;
                            }
                        }

                    }

                    if(8 <= day  && day <=14)
                    {
                        if(8 <= Integer.parseInt(rsJson.getString("datetime").substring(8,10))  && Integer.parseInt(rsJson.getString("datetime").substring(8,10))<=14) {
                            //正常
                            if (rsJson.getString("status").equals("12")) {
                                yValues[0] += 1;
                            }
                            //迟到
                            if (rsJson.getString("status").equals("22") || rsJson.getString("status").equals("21")) {
                                yValues[1] += 1;
                            }
                            //早退
                            if (rsJson.getString("status").equals("11") || rsJson.getString("status").equals("10") || rsJson.getString("status").equals("10")) {
                                yValues[2] += 1;
                            }
                            //缺席
                            if (rsJson.getString("status").equals("00")) {
                               // yValues[3] += 1;
                            }
                        }

                    }


                }

            }
        }
    }




    //从服务器申请数据
    private void mystatitics() {
        //获取userid数据
        SharedPreferences prefs = getSharedPreferences("Userdata", Context.MODE_PRIVATE);
        String alluserdata = prefs.getString("all_userdata",null);
        Gson gson = new Gson();
        UserInfo userinfo = gson.fromJson(alluserdata, UserInfo.class);
        userId = userinfo.getData().getPhoneNumber();
        new Thread(){
            @Override
            public void run() {
                HttpUtils httpUtils = new HttpUtils();
                //转换为JSON
                String user = httpUtils.mystatisticsJson(userId,meetingId);

                Log.d(TAG, "user:" + user);

                try {
                    final String result = httpUtils.login(url, user);
                    Log.d(TAG, "MyStatisticsActivity返回结果:" + result);

                    //Json数据解析
                    StatisticsJson(result);
                    final JSONObject rs1 = JSONObject.fromObject(result);

                    //更新UI,在UI线程中
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(rs1.getInt("errorcode") == 0){
                                //折线图
                                getAxisPoints();//获取坐标点
                                initLineChart();//初始化
                                //柱状图
                                initView();

                            }else{


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