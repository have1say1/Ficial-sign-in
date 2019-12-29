package com.example.air.facial_sign_in.util.meeting;

import com.example.air.facial_sign_in.model.Meeting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

public class AddMeetingService {
    public String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
    public static final String DEF_CHATSET = "UTF-8";
    private String urlStr;
    private String param;
    private Meeting meeting;
    public static int requestTimes = 0;

    public AddMeetingService(){
        this.urlStr = "http://123.56.96.92:3000/api/v1/meeting/add";
    }
    public AddMeetingService(Meeting meeting){
        this.meeting = meeting;
        this.urlStr = "http://123.56.96.92:3000/api/v1/meeting/add";
        this.param = "mname="+meeting.getMname()+"&mcover="+meeting.getMcover()+"&check_rule="+meeting.getCheckrule()+"&check_time_start="+meeting.getStartTime()+"&check_time_end="+meeting.getEndTime()+"&longitude="+meeting.getLocationX()+"&latitude="+meeting.getLocationY()+"&describe="+meeting.getLocationDiscribe()+"&uid="+meeting.getOwnnerId();
    }

    public String sendPost() {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(urlStr);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("User-agent", userAgent);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //1.获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            //2.中文有乱码的需要将PrintWriter改为如下
            //out=new OutputStreamWriter(conn.getOutputStream(),"UTF-8")
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),DEF_CHATSET));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        System.out.println("post推送结果："+result);
        return result;
    }

    public static void main(String[] args) {
        Meeting m = new Meeting("cover","haha","","12:39","大兴区xxx",23,69,"1_2",100,800,"12345678_1",9);
        AddMeetingService r = new AddMeetingService(m);
        //发送 POST 请求
        String sr=r.sendPost();
        System.out.println(sr);
    }


}
