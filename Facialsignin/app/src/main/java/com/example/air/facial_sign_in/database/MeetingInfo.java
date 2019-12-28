package com.example.air.facial_sign_in.database;

import org.litepal.crud.LitePalSupport;

public class MeetingInfo extends LitePalSupport {
    private int errorcode;
    private DataBean data;
    private String msg;

    public int getErrorCode() {
        return errorcode;
    }
    public void setErrorCode(int errorcode) {
        this.errorcode = errorcode;
    }

    public DataBean getData() {
        return data;
    }
    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMSG() {
        return msg;
    }
    public void setMSG(String msg) {
        this.msg = msg;
    }

    public static class DataBean{

        private String mcover;
        private String mname;
        private String mid;
        private LocationBean location;



        public String getmcover() {
            return mcover;
        }
        public void setmcover(String mcover) {
            this.mcover = mcover;
        }

        public String getmname() {
            return mname;
        }
        public void setmname(String mname) {
            this.mname = mname;
        }

        public String getmid() {
            return mid;
        }
        public void setmid(String mid) {
            this.mid = mid;
        }

        public LocationBean getLocation() {
            return location;
        }
        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public static class LocationBean {
            private String longitude;
            private String latitude;
            private String describe;

            public String getlongitude() {
                return longitude;
            }
            public void setlongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getlatitude() {
                return latitude;
            }
            public void setlatitude(String latitude) {
                this.latitude =latitude;
            }

            public String getdescribe() {
                return describe;
            }
            public void setdescribe(String describe) {
                this.describe = describe;
            }
        }
        }
    }


