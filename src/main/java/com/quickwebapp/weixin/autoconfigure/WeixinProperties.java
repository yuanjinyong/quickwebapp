/**
 * 
 */
package com.quickwebapp.weixin.autoconfigure;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 微信相关的配置
 * 
 * @author 袁进勇
 *
 */
@ConfigurationProperties(prefix = WeixinProperties.PREFIX)
public class WeixinProperties {
    public static final String PREFIX = "weixin";
    public static final String MENU_SERVICE = "meunService";

    /**
     * 提供给微信公众号签名校验用的Token
     */
    private String token;

    /**
     * 同步自定义菜单到微信服务器的处理类，必须实现MeunService接口。
     */
    private String meunService;

    /**
     * 微信服务器的URL地址
     */
    private String url;

    /**
     * 获取Access Token的URL地址
     */
    private String accessTokenUrl;

    /**
     * 获取用户信息的URL地址
     */
    private String userInfoUrl;

    /**
     * 更新自定义菜单的URL地址
     */
    private String createMenuUrl;

    /**
     * 主动推送消息的URL地址
     */
    private String sendMessageUrl;

    @PostConstruct
    public void init() {
        if (token == null) {
            token = "quickwebapp";
        }
        if (meunService == null) {
            meunService = "meunService";
        }
        if (url == null) {
            url = "https://api.weixin.qq.com/cgi-bin";
        }
        if (accessTokenUrl == null) {
            accessTokenUrl = url
                    + "/token?grant_type=client_credential&appid=wx9504135e7dbe2999&secret=9229e5d3954ba97b63b367b45943a5bb";
        }
        if (userInfoUrl == null) {
            userInfoUrl = url + "/user/info";
        }
        if (createMenuUrl == null) {
            createMenuUrl = url + "/menu/create";
        }
        if (sendMessageUrl == null) {
            sendMessageUrl = url + "/message/custom/send";
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMeunService() {
        return meunService;
    }

    public void setMeunService(String meunService) {
        this.meunService = meunService;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAccessTokenUrl() {
        return accessTokenUrl;
    }

    public void setAccessTokenUrl(String accessTokenUrl) {
        this.accessTokenUrl = accessTokenUrl;
    }

    public String getUserInfoUrl() {
        return userInfoUrl;
    }

    public void setUserInfoUrl(String userInfoUrl) {
        this.userInfoUrl = userInfoUrl;
    }

    public String getCreateMenuUrl() {
        return createMenuUrl;
    }

    public void setCreateMenuUrl(String createMenuUrl) {
        this.createMenuUrl = createMenuUrl;
    }

    public String getSendMessageUrl() {
        return sendMessageUrl;
    }

    public void setSendMessageUrl(String sendMessageUrl) {
        this.sendMessageUrl = sendMessageUrl;
    }
}
