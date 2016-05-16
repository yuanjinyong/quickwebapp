/**
 * 
 */
package com.quickwebapp.weixin.entity;

import com.quickwebapp.framework.core.entity.MapEntity;

/**
 * 微信用户信息
 * 
 * @author 袁进勇
 *
 */
public class UserInfo extends MapEntity {
    private static final long serialVersionUID = -4768988755464811455L;

    private static final String FIELD_SUBSCRIBE = "subscribe"; // 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
    private static final String FIELD_OPEN_ID = "openid"; // 用户的标识，对当前公众号唯一， "o6_bmjrPTlm6_2sgVt7hMZOPfL2M"
    private static final String FIELD_NICK_NAME = "nickname"; // 用户的昵称，"Band"
    private static final String FIELD_SEX = "sex"; // 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
    private static final String FIELD_CITY = "city"; // 用户所在城市，"广州"
    private static final String FIELD_COUNTRY = "country"; // 用户所在国家，"中国"
    private static final String FIELD_PROVINCE = "province"; // 用户所在省份，"广东"
    private static final String FIELD_LANGUAGE = "language"; // 用户的语言， "zh_CN"，简体中文为zh_CN
    private static final String FIELD_HEAD_IMG_URL = "headimgurl"; // 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。"http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0"
    private static final String FIELD_SUBSCRIBE_TIME = "subscribe_time"; // 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间，1382694957
    private static final String FIELD_UNION_ID = "unionid"; // 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。"o6_bmasdasdsad6_2sgVt7hMZOPfL"
    private static final String FIELD_REMARK = "remark"; // 公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注，"备注"
    private static final String FIELD_GROUP_ID = "groupid"; // 用户所在的分组ID（兼容旧的用户分组接口），0
    private static final String FIELD_TAG_ID_LIST = "tagid_list"; // 用户被打上的标签ID列表，[128,2]

    public Integer getSubscribe() {
        return getInteger(FIELD_SUBSCRIBE, 0);
    }

    public void setSubscribe(Integer subscribe) {
        put(FIELD_SUBSCRIBE, subscribe);
    }

    public String getOpenId() {
        return getString(FIELD_OPEN_ID);
    }

    public void setOpenId(String openId) {
        put(FIELD_OPEN_ID, openId);
    }

    public String getNickName() {
        return getString(FIELD_NICK_NAME);
    }

    public void setNickName(String nickName) {
        put(FIELD_NICK_NAME, nickName);
    }

    public Integer getSex() {
        return getInteger(FIELD_SEX, 0);
    }

    public void setSex(Integer sex) {
        put(FIELD_SEX, sex);
    }

    public String getCountry() {
        return getString(FIELD_COUNTRY);
    }

    public void setCountry(String country) {
        put(FIELD_COUNTRY, country);
    }

    public String getProvince() {
        return getString(FIELD_PROVINCE);
    }

    public void setProvince(String province) {
        put(FIELD_PROVINCE, province);
    }

    public String getCity() {
        return getString(FIELD_CITY);
    }

    public void setCity(String city) {
        put(FIELD_CITY, city);
    }

    public String getLanguage() {
        return getString(FIELD_LANGUAGE);
    }

    public void setLanguage(String language) {
        put(FIELD_LANGUAGE, language);
    }

    public String getHeadImgUrl() {
        return getString(FIELD_HEAD_IMG_URL);
    }

    public void setHeadImgUrl(String headImgUrl) {
        put(FIELD_HEAD_IMG_URL, headImgUrl);
    }

    public Long getSubscribeTime() {
        return getLong(FIELD_SUBSCRIBE_TIME, 0L);
    }

    public void setSubscribeTime(Long subscribeTime) {
        put(FIELD_SUBSCRIBE_TIME, subscribeTime);
    }

    public String getUnionId() {
        return getString(FIELD_UNION_ID);
    }

    public void setUnionId(String unionId) {
        put(FIELD_UNION_ID, unionId);
    }

    public String getRemark() {
        return getString(FIELD_REMARK);
    }

    public void setRemark(String remark) {
        put(FIELD_REMARK, remark);
    }

    public Integer getGroupId() {
        return getInteger(FIELD_GROUP_ID, 0);
    }

    public void setGroupId(Integer groupId) {
        put(FIELD_GROUP_ID, groupId);
    }

    public Integer[] getTagIdList() {
        return (Integer[]) get(FIELD_TAG_ID_LIST);
    }

    public void setTagIdList(Integer[] tagIdList) {
        put(FIELD_TAG_ID_LIST, tagIdList);
    }
}
