package com.example.air.facial_sign_in.model;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.LitePalSupport;

public class UserInfo extends LitePalSupport {

    private int errorcode;

    private DataBean data;

    private String msg;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{

        private String phonenumber;
        private String username;
        private int sex;
        private String organization;
        private String face;


        public String getPhoneNumber() {
            return phonenumber;
        }

        public void setPhoneNumber(String phonenumber) {
            this.phonenumber = phonenumber;
        }

        public String getUserName() {
            return username;
        }

        public void setUserName(String username) {
            this.username = username;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getOrganization() {
            return organization;
        }

        public void setOrganization(String organization) {
            this.organization = organization;
        }
        public String getPhoto() {
            return face;
        }

        public void setPhoto(String face) {
            this.face = face;
        }
    }



}
