/**
 * 
 */
package com.quickwebapp.weixin.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.quickwebapp.framework.core.utils.BeanUtil;
import com.quickwebapp.weixin.entity.Message;
import com.quickwebapp.weixin.exception.ServiceNotFoundException;
import com.quickwebapp.weixin.exception.UnknownMsgTypeException;
import com.quickwebapp.weixin.service.EventMessageService;
import com.quickwebapp.weixin.service.ImageMessageService;
import com.quickwebapp.weixin.service.LinkMessageService;
import com.quickwebapp.weixin.service.LocationMessageService;
import com.quickwebapp.weixin.service.MessageService;
import com.quickwebapp.weixin.service.ShortVideoMessageService;
import com.quickwebapp.weixin.service.TextMessageService;
import com.quickwebapp.weixin.service.VideoMessageService;
import com.quickwebapp.weixin.service.VoiceMessageService;

/**
 * @author 袁进勇
 *
 */
public class DefaultMessageServiceImpl implements MessageService {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultMessageServiceImpl.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.jianyanxinxi.weixin.service.MessageService#onReceivedMessage(com.jianyanxinxi.weixin.entity.Message)
     */
    @Override
    public Message oMessage(Message reqMsg) {
        String msgType = reqMsg.getMsgType();
        if (Message.MSG_TYPE_EVENT.equals(msgType)) {
            return getService(EventMessageService.class, "事件推送消息").onEvent(reqMsg);
        } else if (Message.MSG_TYPE_TEXT.equals(msgType)) {
            return getService(TextMessageService.class, "文本消息").onTextMessage(reqMsg);
        } else if (Message.MSG_TYPE_IMAGE.equals(msgType)) {
            return getService(ImageMessageService.class, "图片消息").onImageMessage(reqMsg);
        } else if (Message.MSG_TYPE_VOICE.equals(msgType)) {
            return getService(VoiceMessageService.class, "语音消息").onVoiceMessage(reqMsg);
        } else if (Message.MSG_TYPE_VIDEO.equals(msgType)) {
            return getService(VideoMessageService.class, "视频消息").onVideoMessage(reqMsg);
        } else if (Message.MSG_TYPE_SHORTVIDEO.equals(msgType)) {
            return getService(ShortVideoMessageService.class, "小视频消息").onShortVideoMessage(reqMsg);
        } else if (Message.MSG_TYPE_LOCATION.equals(msgType)) {
            return getService(LocationMessageService.class, "地理位置消息").onLocationMessage(reqMsg);
        } else if (Message.MSG_TYPE_LINK.equals(msgType)) {
            return getService(LinkMessageService.class, "链接消息").onLinkMessage(reqMsg);
        }

        throw new UnknownMsgTypeException(msgType);
    }

    private <T> T getService(Class<T> clz, String desc) {
        T service = BeanUtil.getBean(clz, false);
        if (service != null) {
            return service;
        }

        LOG.warn("未配置{}的Service处理类，忽略收到的消息。如需处理，请添加一个实现了{}接口的Java Bean，并注册到Spring Bean容器中。", desc, clz.getName());
        throw new ServiceNotFoundException("未配置%s的Service处理类，忽略收到的事件。如需处理，请添加一个实现了%s接口的Java Bean，并注册到Spring Bean容器中。",
                desc, clz.getName());
    }
}
