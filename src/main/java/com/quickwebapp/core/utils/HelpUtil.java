package com.quickwebapp.core.utils;

import java.util.List;

import org.springframework.util.StringUtils;

public class HelpUtil extends StringUtils {
    public static boolean isEmptyString(String string) {
        return string == null || string.trim().length() == 0;
    }

    public static boolean isEmptyCollection(List<?> list) {
        return list == null || list.size() == 0;
    }
}
