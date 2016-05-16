/**
 * 
 */
package com.quickwebapp.weixin.entity;

import com.quickwebapp.framework.core.entity.MapEntity;

/**
 * 与微信服务器交互报文的消息对象实体类
 * 
 * @author 袁进勇
 *
 */
public class Message extends MapEntity {
    private static final long serialVersionUID = -3921389356727405578L;

    // 消息类型
    public static final String MSG_TYPE_TEXT = "text"; // 文本消息
    public static final String MSG_TYPE_IMAGE = "image"; // 图片消息
    public static final String MSG_TYPE_VOICE = "voice"; // 语音消息
    public static final String MSG_TYPE_VIDEO = "video"; // 视频消息
    public static final String MSG_TYPE_SHORTVIDEO = "shortvideo"; // 小视频消息
    public static final String MSG_TYPE_LOCATION = "location"; // 地理位置消息
    public static final String MSG_TYPE_LINK = "link"; // 链接消息
    public static final String MSG_TYPE_EVENT = "event"; // 事件推送

    // 事件类型
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe"; // 订阅、关注事件
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe"; // 取消订阅、取消关注事件
    public static final String EVENT_TYPE_SCAN = "SCAN"; // 扫描带参数二维码事件
    public static final String EVENT_TYPE_LOCATION = "LOCATION"; // 上报地理位置事件
    public static final String EVENT_TYPE_CLICK = "CLICK"; // 自定义菜单事件
    public static final String EVENT_TYPE_VIEW = "VIEW"; // 点击菜单跳转链接时的事件

    // 以下为消息公共字段
    private static final String FIELD_TO_USER_NAME = "ToUserName"; // 对于接收到的消息，为开发者微信号（公众号）；对于回复类的消息，为接收方帐号（一个OpenID）
    private static final String FIELD_FROM_USER_NAME = "FromUserName"; // 对于接收到的消息，为发送方帐号（一个OpenID）；对于回复类的消息，为开发者微信号（公众号）
    private static final String FIELD_CREATE_TIME = "CreateTime"; // 消息创建时间 （整型），微信端为int
    private static final String FIELD_MSG_TYPE = "MsgType"; // 消息类型
    // 以下为普通消息的字段
    private static final String FIELD_MSG_ID = "MsgId"; // 消息id，64位整型。（请求消息有该字段）
    private static final String FIELD_CONTENT = "Content"; // 文本消息内容。（文本消息有该字段）
    private static final String FIELD_MEDIA_ID = "MediaId"; // 媒体id，可以调用多媒体文件下载接口拉取数据。（多媒体消息有该字段）
    private static final String FIELD_PIC_URL = "PicUrl"; // 图片链接（由系统生成）。（图片消息有该字段）
    private static final String FIELD_FORMAT = "Format"; // 语音格式，如amr，speex等。（语音消息有该字段）
    private static final String FIELD_RECOGNITION = "Recognition"; // 语音识别结果，UTF8编码。（语音消息有该字段，可选）
    private static final String FIELD_THUMBMEDIA_ID = "ThumbMediaId"; // 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。（视频消息、小视频消息有该字段）
    private static final String FIELD_LOCATION_X = "Location_X"; // 地理位置维度。（地理位置消息有该字段）
    private static final String FIELD_LOCATION_Y = "Location_Y"; // 地理位置经度。（地理位置消息有该字段）
    private static final String FIELD_SCALE = "Scale"; // 地图缩放大小。（地理位置消息有该字段）
    private static final String FIELD_LABEL = "Label"; // 地理位置信息。（地理位置消息有该字段）
    private static final String FIELD_TITLE = "Title"; // 消息标题。（链接消息有该字段）
    private static final String FIELD_DESCRIPTION = "Description"; // 消息描述。（链接消息有该字段）
    private static final String FIELD_URL = "Url"; // 消息链接。（链接消息有该字段）
    // 以下为事件推送的字段
    private static final String FIELD_EVENT = "Event"; // 事件类型。（事件推送消息有该字段）
    private static final String FIELD_EVENT_KEY = "EventKey"; // 事件KEY值。 （扫描带参数二维码事件、订阅、关注事件、自定义菜单事件、点击菜单跳转链接时的事件有该字段）
    private static final String FIELD_TICKET = "Ticket"; // 二维码的ticket，可用来换取二维码图片。（扫描带参数二维码事件、订阅、关注事件有该字段）
    private static final String FIELD_LATITUDE = "Latitude"; // 地理位置纬度。（上报地理位置事件有该字段）
    private static final String FIELD_LONGITUDE = "Longitude"; // 地理位置经度。（上报地理位置事件有该字段）
    private static final String FIELD_PRECISION = "Precision"; // 地理位置精度。（上报地理位置事件有该字段）

    public String getToUserName() {
        return getString(FIELD_TO_USER_NAME);
    }

    public void setToUserName(String toUserName) {
        put(FIELD_TO_USER_NAME, toUserName);
    }

    public String getFromUserName() {
        return getString(FIELD_FROM_USER_NAME);
    }

    public void setFromUserName(String fromUserName) {
        put(FIELD_FROM_USER_NAME, fromUserName);
    }

    public Long getCreateTime() {
        return getLong(FIELD_CREATE_TIME, 0L);
    }

    public void setCreateTime(Long createTime) {
        put(FIELD_CREATE_TIME, createTime);
    }

    public String getMsgType() {
        return getString(FIELD_MSG_TYPE);
    }

    public void setMsgType(String msgType) {
        put(FIELD_MSG_TYPE, msgType);
    }

    public Long getMsgId() {
        return getLong(FIELD_MSG_ID, 0L);
    }

    public void setMsgId(Long msgId) {
        put(FIELD_MSG_ID, msgId);
    }

    public String getContent() {
        return getString(FIELD_CONTENT);
    }

    public void setContent(String content) {
        put(FIELD_CONTENT, content);
    }

    public String getMediaId() {
        return getString(FIELD_MEDIA_ID);
    }

    public void setMediaId(String mediaId) {
        put(FIELD_MEDIA_ID, mediaId);
    }

    public String getPicUrl() {
        return getString(FIELD_PIC_URL);
    }

    public void setPicUrl(String picUrl) {
        put(FIELD_PIC_URL, picUrl);
    }

    public String getFormat() {
        return getString(FIELD_FORMAT);
    }

    public void setFormat(String format) {
        put(FIELD_FORMAT, format);
    }

    public String getRecognition() {
        return getString(FIELD_RECOGNITION);
    }

    public void setRecognition(String recognition) {
        put(FIELD_RECOGNITION, recognition);
    }

    public String getThumbMediaId() {
        return getString(FIELD_THUMBMEDIA_ID);
    }

    public void setThumbMediaId(String thumbMediaId) {
        put(FIELD_THUMBMEDIA_ID, thumbMediaId);
    }

    public Double getLocation_X() {
        return getDouble(FIELD_LOCATION_X, 0.00);
    }

    public void setLocation_X(Double location_X) {
        put(FIELD_LOCATION_X, location_X);
    }

    public Double getLocation_Y() {
        return getDouble(FIELD_LOCATION_Y, 0.00);
    }

    public void setLocation_Y(Double location_Y) {
        put(FIELD_LOCATION_Y, location_Y);
    }

    public Double getScale() {
        return getDouble(FIELD_SCALE, 0.00);
    }

    public void setScale(Double scale) {
        put(FIELD_SCALE, scale);
    }

    public String getLabel() {
        return getString(FIELD_LABEL);
    }

    public void setLabel(String label) {
        put(FIELD_LABEL, label);
    }

    public String getTitle() {
        return getString(FIELD_TITLE);
    }

    public void setTitle(String title) {
        put(FIELD_TITLE, title);
    }

    public String getDescription() {
        return getString(FIELD_DESCRIPTION);
    }

    public void setDescription(String description) {
        put(FIELD_DESCRIPTION, description);
    }

    public String getUrl() {
        return getString(FIELD_URL);
    }

    public void setUrl(String url) {
        put(FIELD_URL, url);
    }

    public String getEvent() {
        return getString(FIELD_EVENT);
    }

    public void setEvent(String event) {
        put(FIELD_EVENT, event);
    }

    /**
     * 扫描带参数二维码事件、订阅、关注事件、自定义菜单事件、点击菜单跳转链接时的事件有该字段：
     * 1、扫描带参数二维码事件、订阅、关注事件，如果用户还未关注公众号，则用户可以关注公众号，关注后微信会将带场景值关注事件推送给开发者。
     * 用户未关注时，进行关注后的事件推送，qrscene_为前缀，后面为二维码的参数值；
     * 用户已关注时，是一个32位无符号整数，即创建二维码时的二维码scene_id。
     * 2、用户点击自定义菜单后，微信会把点击事件推送给开发者，与自定义菜单接口中KEY值对应
     * 3、点击菜单跳转链接时的事件，设置的跳转URL
     * 
     * @return
     */
    public String getEventKey() {
        return getString(FIELD_EVENT_KEY);
    }

    public void setEventKey(String eventKey) {
        put(FIELD_EVENT_KEY, eventKey);
    }

    public String getTicket() {
        return getString(FIELD_TICKET);
    }

    public void setTicket(String ticket) {
        put(FIELD_TICKET, ticket);
    }

    public Double getLatitude() {
        return getDouble(FIELD_LATITUDE, 0.00);
    }

    public void setLatitude(Double latitude) {
        put(FIELD_LATITUDE, latitude);
    }

    public Double getLongitude() {
        return getDouble(FIELD_LONGITUDE, 0.00);
    }

    public void setLongitude(Double longitude) {
        put(FIELD_LONGITUDE, longitude);
    }

    public Double getPrecision() {
        return getDouble(FIELD_PRECISION, 0.00);
    }

    public void setPrecision(Double precision) {
        put(FIELD_PRECISION, precision);
    }
}
