package com.example.air.facial_sign_in.model;

public class Meeting {
    private int errorcode;
    private String msg;
    private String mcover;
    private String mname;
    private String mid;
    private String createTime;
    private String locationDiscribe;
    private double locationX;
    private double locationY;
    private String checkrule;
    private int startTime;
    private int endTime;
    private String ownnerId;
    private int sum;

    public int getErrorCode() {
        return errorcode;
    }
    public void setErrorCode(int errorcode) {
        this.errorcode = errorcode;
    }

    public String getMSG() {
        return msg;
    }
    public void setMSG(String msg) {
        this.msg = msg;
    }

    public Meeting(String mname,int sum){
        this.mname = mname;
        this.sum = sum;
    }
    public Meeting(){}

    public String getMcover() {
        return mcover;
    }

    public void setMcover(String mcover) {
        this.mcover = mcover;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getLocationDiscribe() {
        return locationDiscribe;
    }

    public void setLocationDiscribe(String locationDiscribe) {
        this.locationDiscribe = locationDiscribe;
    }

    public double getLocationX() {
        return locationX;
    }

    public void setLocationX(double locationX) {
        this.locationX = locationX;
    }

    public double getLocationY() {
        return locationY;
    }

    public void setLocationY(double locationY) {
        this.locationY = locationY;
    }

    public String getCheckrule() {
        return checkrule;
    }

    public void setCheckrule(String checkrule) {
        this.checkrule = checkrule;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public String getOwnnerId() {
        return ownnerId;
    }

    public void setOwnnerId(String ownnerId) {
        this.ownnerId = ownnerId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public Meeting(String mcover, String mname, String mid, String createTime, String locationDiscribe, double locationX, double locationY, String checkrule, int startTime, int endTime, String ownnerId, int sum) {
        this.mcover = mcover;
        this.mname = mname;
        this.mid = mid;
        this.createTime = createTime;
        this.locationDiscribe = locationDiscribe;
        this.locationX = locationX;
        this.locationY = locationY;
        this.checkrule = checkrule;
        this.startTime = startTime;
        this.endTime = endTime;
        this.ownnerId = ownnerId;
        this.sum = sum;
    }
}
