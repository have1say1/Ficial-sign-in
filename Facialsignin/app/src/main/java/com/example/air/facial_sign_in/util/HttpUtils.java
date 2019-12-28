package com.example.air.facial_sign_in.util;

import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3. Request;

public class HttpUtils {
    private static final String TAG = "HttpUtils";
    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public String login(String url, String json) throws IOException {
        Log.d(TAG, "json: "+json);
        //把请求的内容字符串转换为json
        RequestBody body = RequestBody.create(json,JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();
        return result;
    }

    public String bowlingJson(String phonenumber, String password) {
        return "{\"phonenumber\":" + "\"" + phonenumber + "\"" + "," + "\"password\":" + "\"" + password + "\"" + "}";
    }

    public String registJson(String phonenumber, String password, String username, String face, String organization, int sex) {
        return "{\"phonenumber\":" + "\"" + phonenumber + "\"" + "," + "\"password\":" + "\"" + password + "\"" +
                "," + "\"username\":" + "\"" + username + "\"" + "," + "\"face\":" + "\"" + face + "\"" +
                "," + "\"organization\":" + "\"" + organization + "\"" + "," + "\"sex\":" +  sex  +
                "}";
    }

    public String inviteJson(String userid, String meetingid) {
        return "{\"userid\":" + "\"" + userid + "\"" + "," + "\"meetingid\":" + "\"" + meetingid + "\"" + "}";
    }


}

