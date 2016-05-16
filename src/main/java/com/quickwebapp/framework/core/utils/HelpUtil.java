package com.quickwebapp.framework.core.utils;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.util.StringUtils;

public class HelpUtil extends StringUtils {
    public static boolean isEmptyString(String string) {
        return string == null || string.trim().length() == 0;
    }

    public static boolean isEmptyCollection(List<?> list) {
        return list == null || list.size() == 0;
    }

    /**
     * 获取应用服务器当前时间
     * 
     * @return
     */
    public static Timestamp getNowTime() {
        return new Timestamp(System.currentTimeMillis());
    }
}
