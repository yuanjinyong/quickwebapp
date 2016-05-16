/**
 * 
 */
package com.quickwebapp.weixin.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.quickwebapp.weixin.autoconfigure.WeixinProperties;
import com.quickwebapp.weixin.entity.MenuTree;
import com.quickwebapp.weixin.http.client.WeixinClient;
import com.quickwebapp.weixin.service.MeunService;

/**
 * @author 袁进勇
 *
 */
public class DefaultMeunServiceImpl implements MeunService {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultMeunServiceImpl.class);
    @Autowired
    private WeixinClient weixinClient;

    @Override
    public void createMenu() {
        LOG.info("========更新微信端菜单开始========");

        MenuTree menuTree = createMenuTree();
        if (menuTree == null) {
            LOG.warn("未创建自定义菜单，请在application.properties中配置{}.{}参数来指定创建自定义菜单的Java Bean ID。", WeixinProperties.PREFIX,
                    WeixinProperties.MENU_SERVICE);
        } else {
            weixinClient.post(WeixinClient.getProperties().getCreateMenuUrl(), menuTree);
        }

        LOG.info("========更新微信端菜单完成========");
    }

    protected MenuTree createMenuTree() {
        return null;
    }

    public WeixinClient getWeixinClient() {
        return weixinClient;
    }

    public void setWeixinClient(WeixinClient weixinClient) {
        this.weixinClient = weixinClient;
    }
}
