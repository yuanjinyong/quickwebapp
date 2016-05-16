/**
 * 
 */
package com.quickwebapp.weixin.http.client;

import java.io.IOException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.support.json.JSONParser;
import com.alibaba.druid.support.json.JSONUtils;
import com.quickwebapp.framework.core.exception.BusinessException;
import com.quickwebapp.framework.core.utils.JsonUtil;
import com.quickwebapp.framework.core.utils.MapUtil;
import com.quickwebapp.weixin.autoconfigure.WeixinProperties;

/**
 * 调用微信http接口
 * 
 * @author 袁进勇
 *
 */
public class WeixinClient {
    private static final Logger LOG = LoggerFactory.getLogger(WeixinClient.class);
    private static final String ERROR_CODE = "errcode";
    private static final String ERROR_MSG = "errmsg";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String EXPIRES_IN = "expires_in";
    private static WeixinProperties properties = null;
    private static String accessToken = null;

    public Map<String, Object> get(String uri, Map<String, Object> params) {
        String url = buildUrl(uri, params);
        LOG.info("Http get url is: {}", url);

        String responseContent = executeRequest(new HttpGet(url));
        LOG.info("Http get response content is: {}", responseContent);

        JSONParser jsonparer = new JSONParser(responseContent);// 响应内容解析为JSON对象
        @SuppressWarnings("unchecked")
        Map<String, Object> result = (Map<String, Object>) jsonparer.parse();
        Long errcode = MapUtil.getLong(result, ERROR_CODE, 0L);
        if (errcode != 0) {
            throw new BusinessException(errcode, MapUtil.getString(result, ERROR_MSG));
        }

        return result;
    }

    public Map<String, Object> post(String uri, Object data) {
        String url = buildUrl(uri, null);
        LOG.info("Http post url is: {}", url);
        String json = JsonUtil.toJson(data);
        LOG.info("Http post data is: {}", json);

        String responseContent = executeRequest(buildJsonHttpPost(url, json));
        LOG.info("Http post response content is: {}", responseContent);

        @SuppressWarnings("unchecked")
        Map<String, Object> result = (Map<String, Object>) JSONUtils.parse(responseContent); // 响应内容解析为JSON对象
        Long errcode = MapUtil.getLong(result, ERROR_CODE, 0L);
        if (errcode != 0) {
            throw new BusinessException(errcode, MapUtil.getString(result, ERROR_MSG));
        }

        return result;
    }

    private String buildUrl(String url, Map<String, Object> params) {
        StringBuffer urlWithAccessToken = new StringBuffer(url);
        urlWithAccessToken.append(urlWithAccessToken.lastIndexOf("?") < 0 ? '?' : '&');
        urlWithAccessToken.append(ACCESS_TOKEN).append('=').append(getAccessToken());
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                urlWithAccessToken.append('&').append(entry.getKey()).append('=').append(entry.getValue());
            }
        }

        return urlWithAccessToken.toString();
    }

    private HttpPost buildJsonHttpPost(String url, String json) {
        StringEntity se = new StringEntity(json, "UTF-8");
        se.setContentType("text/json");
        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(se);

        return httpPost;
    }

    private String executeRequest(final HttpRequestBase request) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpResponse response = httpClient.execute(request);
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (IOException e) {
            throw new BusinessException("调用URL地址[" + request.toString() + "]失败：", e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                LOG.error("Close http client failed: ", e);
            }
        }
    }

    public String getAccessToken() {
        if (accessToken == null) {
            refreshToken();
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
    private void refreshToken() {
        Integer expiresTime = 30; // 如果网络调用失败，30秒后重试。
        try {
            String responseContent = executeRequest(new HttpGet(getProperties().getAccessTokenUrl()));
            LOG.info("Http response content is: {}", responseContent);

            @SuppressWarnings("unchecked")
            Map<String, Object> result = (Map<String, Object>) new JSONParser(responseContent).parse();
            Long errcode = MapUtil.getLong(result, ERROR_CODE, 0L);
            if (errcode != 0) {
                throw new BusinessException(errcode, MapUtil.getString(result, ERROR_MSG));
            }
            expiresTime = MapUtil.getInteger(result, EXPIRES_IN) - 200;
            accessToken = MapUtil.getString(result, ACCESS_TOKEN);
        } catch (Exception e) {
            LOG.error("获取Access Token失败，" + expiresTime + "秒后重试：", e);
            accessToken = null;
        }

        // 定时调用刷新access_token的过期时间
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                refreshToken();
            }
        }, expiresTime * 1000);
    }

    public static WeixinProperties getProperties() {
        return properties;
    }

    public static void setProperties(WeixinProperties properties) {
        WeixinClient.properties = properties;
    }
}
