package com.gin.cloud.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.alibaba.fastjson.JSONObject;

public class DingDingMessageUtil {
    //群设置->智能群助手->添加机器人->自定义(webhook)->名称,自定义关键词(需要和消息中含定义的"关键词")->生成地址->替换 access_token
    //https://oapi.dingtalk.com/robot/send?access_token=fabdcfa51c4dfe3e3c743b615e4e7253bb643c79cad1cf0a8ab997b064db6d34
    //public static String access_token = "fabdcfa51c4dfe3e3c743b615e4e7253bb643c79cad1cf0a8ab997b064db6d34";
    public static String access_token = "access_token";

    public static void sendTextMessage(String msg) {
        System.out.println("-------- spring cloud 服务状态改变...准备发送钉钉消息 --------");
        try {
            Message message = new Message();
            message.setMsgtype("text");
            message.setText(new MessageInfo(msg));
            URL url = new URL("https://oapi.dingtalk.com/robot/send?access_token=" + access_token);
            // 建立 http 连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "application/Json; charset=UTF-8");
            conn.connect();
            OutputStream out = conn.getOutputStream();
            String textMessage = JSONObject.toJSONString(message);
            byte[] data = textMessage.getBytes();
            out.write(data);
            out.flush();
            out.close();
            InputStream in = conn.getInputStream();
            byte[] data1 = new byte[in.available()];
            in.read(data1);
            System.out.println(new String(data1));
        } catch (Exception e) {
            System.out.println("-------- spring cloud 服务状态改变...钉钉消息发送失败 --------");
            System.out.println("error:" + e.getMessage());
        }
    }
}