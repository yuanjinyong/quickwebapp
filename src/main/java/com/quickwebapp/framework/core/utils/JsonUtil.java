/**
 * 
 */
package com.quickwebapp.framework.core.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quickwebapp.framework.core.exception.BusinessException;

/**
 * @author Administrator
 *
 */
public final class JsonUtil {
    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * 如果JSON字符串为Null或"null"字符串,返回Null. 如果JSON字符串为"[]",返回空集合.
     * 
     * 如需读取集合如List/Map,且不是List<String>这种简单类型时使用如下语句: List<MyBean> beanList =
     * binder.getMapper().readValue(listString, new TypeReference<List<MyBean>>()
     * {});
     */
    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        if (HelpUtil.isEmptyString(jsonString)) {
            return null;
        }

        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            logger.warn("parse json string error:" + jsonString, e);
            return null;
        }
    }

    /**
     * 如果对象为Null,返回"null". 如果集合为空集合,返回"[]".
     */
    public static String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            logger.warn("write to json string error:" + object, e);
            return null;
        }
    }

    /**
     * 设置转换日期类型的format pattern,如果不设置默认打印Timestamp毫秒数.
     */
    public void setDateFormat(String pattern) {
        if (!HelpUtil.isEmptyString(pattern)) {
            DateFormat df = new SimpleDateFormat(pattern);
            mapper.getSerializationConfig().with(df);
            mapper.getDeserializationConfig().with(df);
        }
    }

    /**
     * 取出Mapper做进一步的设置或使用其他序列化API.
     */
    public ObjectMapper getMapper() {
        return mapper;
    }

    public static Map<String, Object> jsonToMap(String jsonStr) {
        Map<String, Object> map;
        try {
            map = mapper.readValue(jsonStr, new TypeReference<HashMap<String, String>>() {
            });
        } catch (Exception e) {
            logger.error("json 格式转换错误" + jsonStr);
            throw new BusinessException("json 格式转换错误！");
        }

        return map;
    }

    public static String objectToJackson(Object obj) throws Exception {
        return mapper.writeValueAsString(obj);
    }

    public static List<?> jacksonToCollection(String jsonStr) {
        List<?> result = null;
        try {
            result = mapper.readValue(jsonStr, new TypeReference<List<Map<String, Object>>>() {
            });
        } catch (Exception e) {
            logger.error("json 格式转换错误" + jsonStr);
            e.printStackTrace();
            throw new BusinessException("json 格式转换错误！");
        }

        return result;
    }

    public static <T> List<T> fromJson(String jsonStr, Class<?> collectionClass, Class<?>... elementClasses) {
        List<T> result = null;
        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            result = mapper.readValue(jsonStr, getCollectionType(collectionClass, elementClasses));
        } catch (Exception e) {
            logger.error("json 格式转换错误" + jsonStr);
            e.printStackTrace();
            throw new BusinessException("json 格式转换错误！");
        }

        return result;
    }

    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametrizedType(collectionClass, List.class, elementClasses);
    }
}
