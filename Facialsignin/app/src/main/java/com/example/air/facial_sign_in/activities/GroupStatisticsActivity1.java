package com.example.air.facial_sign_in.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.air.facial_sign_in.R;

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

public class GroupStatisticsActivity1 extends Activity {
    Intent intent;

    private LineChartView lineChart;

    private ColumnChartView mColumnChartView;

    //柱状图
    /*========== 数据相关 ==========*/
    private ColumnChartData mColumnChartData;               //柱状图数据
    public final static String[] xValues = new String[]{"正常", "迟到", "早退", "缺席", "请假"};
    public final static int[] yValues = new int[]{7, 0, 0, 3, 0};

    //折线图
    String[] date = {"周一","周二","周三","周四","周五","周六","周日"};//X轴的标注
    public final static int[] score= {7,8,8,7,9,6,7};//图表的数据
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_attendance_statistics1);
        //折线图
        getAxisXLables();//获取x轴的标注
        getAxisPoints();//获取坐标点
        initLineChart();//初始化

        //柱状图
        initView();
    }

    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.person_statistics:
                intent = new Intent(GroupStatisticsActivity1.this,PersonStatisticsActivity.class);
                startActivity(intent);
                break;
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
        lineChart = (LineChartView)findViewById(R.id.group_chart3);
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
        lineChart.setCurrentViewport(v);
    }

    /**
     * X 轴的显示
     */
    private void getAxisXLables(){
        for (int i = 0; i < date.length; i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(date[i]));
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
//            intent = new Intent(GroupStatisticsActivity1.this,GroupStatisticsActivity1.class);
//            startActivity(intent);
            //Toast.makeText(PersonStatisticsActivity.this, "Selected: " + value, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

    }


    ////////////////////////////////////折线图////////////////////////////////////////
////////////////////////////////////柱状图////////////////////////////////////////
    private void initView() {
        mColumnChartView = (ColumnChartView) findViewById(R.id.group_chart4);
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
        v.top = 10;
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
            Toast.makeText(GroupStatisticsActivity1.this, xValues[columnIndex]+"人数 : " +
                    (int)value.getValue(),Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub
        }
    }
////////////////////////////////////柱状图////////////////////////////////////////


}