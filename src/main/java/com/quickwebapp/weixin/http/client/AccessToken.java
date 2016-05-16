package com.quickwebapp.weixin.http.client;

import java.io.IOException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.support.json.JSONParser;

public final class AccessToken {
    public static final String ACCESS_TOKEN = "access_token";
    public static final String EXPIRES_IN = "expires_in";
    private static final Logger LOG = LoggerFactory.getLogger(AccessToken.class);
    private static String accessToken = null;

    public static String getValue(String accessTokenUrl) {
        if (accessToken == null) {
            refreshToken(accessTokenUrl);
        }

        return accessToken;
    }

    /**
     * 1、为了保密appsecrect，第三方需要一个access_token获取和刷新的中控服务器。而其他业务逻辑服务器所使用的access_token均来自于该中控服务器，不应该各自去刷新，
     * 否则会造成access_token覆盖而影响业务；
     * 2、目前access_token的有效期通过返回的expire_in来传达，目前是7200秒之内的值。中控服务器需要根据这个有效时间提前去刷新新access_token。在刷新过程中，
     * 中控服务器对外输出的依然是老access_token，此时公众平台后台会保证在刷新短时间内，新老access_token都可用，这保证了第三方业务的平滑过渡；
     * 3、access_token的有效时间可能会在未来有调整，所以中控服务器不仅需要内部定时主动刷新，还需要提供被动刷新access_token的接口，这样便于业务服务器在API调用获知access_token已超时的情况下，
     * 可以触发access_token的刷新流程。
     */

    private static void refreshToken(final String accessTokenUrl) {
        Integer expiresTime = 30; // 如果网络调用失败，30秒后重试。
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpResponse response = httpClient.execute(new HttpGet(accessTokenUrl));
            String responseContent = EntityUtils.toString(response.getEntity(), "UTF-8");
            LOG.info("Http response content is: {}", responseContent);

            JSONParser jsonparer = new JSONParser(responseContent);// 响应内容解析为JSON对象
            httpClient.close();

            @SuppressWarnings("unchecked")
            Map<String, Object> mapEntity = (Map<String, Object>) jsonparer.parse();
            expiresTime = (Integer) mapEntity.get(EXPIRES_IN);
            accessToken = (String) mapEntity.get(ACCESS_TOKEN);
        } catch (IOException e) {
            accessToken = null;
            LOG.error(e.getMessage(), e);
        }

        // 定时调用刷新access_token的过期时间
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                refreshToken(accessTokenUrl);
            }
        }, (expiresTime - 200) * 1000);
    }
}
