package com.example.air.facial_sign_in.util.meeting;

import com.example.air.facial_sign_in.model.Meeting;
import com.google.gson.JsonArray;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MeetingListService {
    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
    private String urlStr;
    private int type;
    private String uid;
    public static int requestTimes = 0;

    public MeetingListService(){
        uid = "12345678_1";
        type = 0;
        urlStr = "http://123.56.96.92:3000/api/v1/meeting/list?";
        urlStr = "http://123.56.96.92:3000/api/v1/meeting/list?uid="+ uid + "&type=" + type;
    }
    public MeetingListService(String uid , int type){
        this.uid = uid;
        this.type = type;
        urlStr = "http://123.56.96.92:3000/api/v1/meeting/list?uid="+ uid + "&type=" + type;
    }

    public String DataUtl() throws IOException {
        URL url = new URL(urlStr);
        StringBuffer sb = new StringBuffer();
        BufferedReader reader = null;
        String rs = null;
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-agent", userAgent);
        conn.setUseCaches(false);
        conn.setConnectTimeout(DEF_CONN_TIMEOUT);
        conn.setReadTimeout(DEF_READ_TIMEOUT);
        conn.setInstanceFollowRedirects(false);
        conn.connect();
        InputStream is = conn.getInputStream();
        reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
        String strRead = null;
        while ((strRead = reader.readLine()) != null) {
            sb.append(strRead);
        }
        rs = sb.toString();
        if (reader != null) {
            reader.close();
        }
        if (conn != null) {
            conn.disconnect();
        }
        return rs;
    }
    public ArrayList<Meeting> meetingsDAO(String jstr){

        JSONObject rs = JSONObject.fromObject(jstr);
        if(rs.getInt("errorcode") != 0)
            return null;
        JSONArray rsArray = JSONArray.fromObject(rs.get("data"));
        ArrayList<Meeting> meetings = new ArrayList<Meeting>();
        Meeting meeting;
        for (int i = 0 ; i < rsArray.size() ; i++){
            meeting = new Meeting();
            JSONObject rsJson = rsArray.getJSONObject(i);
            meeting.setMcover(rsJson.getString("mcover"));
            meeting.setMname(rsJson.getString("mname"));
            meeting.setMid(rsJson.getString("mid"));
            meeting.setSum(rsJson.getInt("sum"));
            meetings.add(meeting);
        }

        return meetings;
    }
    public ArrayList<Meeting> getModel() throws IOException {
        String str = DataUtl();
        return meetingsDAO(str);
    }
    public static void main(String[] args) throws IOException {
        MeetingListService s = new MeetingListService();
        String str = s.DataUtl();
        System.out.println(str);
        ArrayList<Meeting> m = s.meetingsDAO(str);
        for (int i = 0 ; i < m.size() ;i++){
            System.out.println(m.get(i).getSum());
        }

    }
}
