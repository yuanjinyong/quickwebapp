/**
 * 
 */
package com.quickwebapp.weixin.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.quickwebapp.framework.core.utils.BeanUtil;
import com.quickwebapp.weixin.entity.Message;
import com.quickwebapp.weixin.entity.UserInfo;
import com.quickwebapp.weixin.exception.ServiceNotFoundException;
import com.quickwebapp.weixin.exception.UnknownEventException;
import com.quickwebapp.weixin.service.ClickEventService;
import com.quickwebapp.weixin.service.EventMessageService;
import com.quickwebapp.weixin.service.LocationEventService;
import com.quickwebapp.weixin.service.ScanEventService;
import com.quickwebapp.weixin.service.SubscribeEventService;
import com.quickwebapp.weixin.service.UnsubscribeEventService;
import com.quickwebapp.weixin.service.UserInfoService;
import com.quickwebapp.weixin.service.ViewEventService;

/**
 * @author 袁进勇
 *
 */
public class DefaultEventMessageServiceImpl implements EventMessageService {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultEventMessageServiceImpl.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.jianyanxinxi.weixin.service.EventService#onEvent(com.jianyanxinxi.weixin.entity.Message)
     */
    @Override
    public Message onEvent(Message reqMsg) {
        String event = reqMsg.getEvent();
        if (Message.EVENT_TYPE_SUBSCRIBE.equals(event)) {
            SubscribeEventService service = getService(SubscribeEventService.class, "订阅、关注事件");
            UserInfo userInfo = BeanUtil.getBean(UserInfoService.class).getUserInfo(reqMsg.getFromUserName(), "zh_CN");
            LOG.debug("微信用户信息：{}", userInfo);
            return service.onSubscribe(reqMsg, userInfo);
        } else if (Message.EVENT_TYPE_UNSUBSCRIBE.equals(event)) {
            return getService(UnsubscribeEventService.class, "取消关注事件").onUnsubscribe(reqMsg);
        } else if (Message.EVENT_TYPE_SCAN.equals(event)) {
            return getService(ScanEventService.class, "扫描带参数二维码事件").onScan(reqMsg);
        } else if (Message.EVENT_TYPE_LOCATION.equals(event)) {
            return getService(LocationEventService.class, "上报地理位置事件").onLocation(reqMsg);
        } else if (Message.EVENT_TYPE_CLICK.equals(event)) {
            return getService(ClickEventService.class, "自定义菜单点击事件").onClick(reqMsg);
        } else if (Message.EVENT_TYPE_VIEW.equals(event)) {
            return getService(ViewEventService.class, "点击菜单跳转链接时事件").onClick(reqMsg);
        }

        throw new UnknownEventException(event);
    }

    private <T> T getService(Class<T> clz, String desc) {
        T service = BeanUtil.getBean(clz, false);
        if (service != null) {
            return service;
        }

        LOG.warn("未配置{}的Service处理类，忽略收到的事件。如需处理，请添加一个实现了{}接口的Java Bean，并注册到Spring Bean容器中。", desc, clz.getName());
        throw new ServiceNotFoundException("未配置%s的Service处理类，忽略收到的事件。如需处理，请添加一个实现了%s接口的Java Bean，并注册到Spring Bean容器中。",
                desc, clz.getName());
    }
}
