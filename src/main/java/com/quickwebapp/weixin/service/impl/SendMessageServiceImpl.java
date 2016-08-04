package com.quickwebapp.weixin.service.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.quickwebapp.framework.core.utils.JsonUtil;
import com.quickwebapp.weixin.http.client.AccessToken;
import com.quickwebapp.weixin.service.SendMessageService;

public class SendMessageServiceImpl implements SendMessageService {
    private static String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx9504135e7dbe2999&secret=9229e5d3954ba97b63b367b45943a5bb";
    /**
     * 主动推送模板信息接口
     */
    private static String sendTemplateUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";

    @Override
    public void sendMessage(Map<String, Object> params) {
        sendTextMessageToUser(params);
    }

    public void sendTextMessageToUser(Map<String, Object> params) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("touser", (String) params.get("f_openid"));
        map.put("template_id", "1jOwcJcPq0dIo0BC15JmUTzf_q6fZPSoQyXb0-Mwqlo");
        map.put("url", "");

        Map<String, Object> context = new HashMap<String, Object>();
        context.put("value", "您好，你的生产计划已审核完毕");
        context.put("color", "#173177");

        Map<String, Object> keyword1 = new HashMap<String, Object>();
        keyword1.put("value", (String) params.get("content"));
        keyword1.put("color", "#173177");

        Map<String, Object> keyword2 = new HashMap<String, Object>();
        keyword2.put("value", (String) params.get("result"));
        keyword2.put("color", "#173177");

        Map<String, Object> remark = new HashMap<String, Object>();
        remark.put("value", new SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(new Date()));
        remark.put("color", "#173177");

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("first", context);
        data.put("keyword1", keyword1);
        data.put("keyword2", keyword2);
        data.put("remark", remark);

        map.put("data", data);

        // 获取access_token
        String accessToken = AccessToken.getValue(accessTokenUrl);
        String action = sendTemplateUrl + accessToken;
        try {
            connectWeiXinInterface(action, JsonUtil.toJson(map));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void connectWeiXinInterface(String action, String json) {
        URL url;
        try {
            url = new URL(action);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            OutputStream os = http.getOutputStream();
            os.write(json.getBytes("UTF-8"));// 传入参数
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String result = new String(jsonBytes, "UTF-8");
            System.out.println("请求返回结果:" + result);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
