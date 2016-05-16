package com.quickwebapp.framework.core.web.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.quickwebapp.framework.core.entity.MapEntity;
import com.quickwebapp.framework.core.utils.HelpUtil;
import com.quickwebapp.framework.core.utils.Pagination;

public abstract class TopController {
    /**
     * 直接获取参数
     */
    public static String $(String paramName) {
        return getRequest().getParameter(paramName);
    }

    public static String $(String paramName, String defaultValue) {
        String value = $(paramName);
        return HelpUtil.isEmptyString(value) ? defaultValue : value;
    }

    public static Boolean $bool(String paramName) {
        return $bool(paramName, null);
    }

    public static Boolean $bool(String paramName, Boolean defaultValue) {
        String value = $(paramName);
        return HelpUtil.isEmptyString(value) ? defaultValue : Boolean.parseBoolean(value);
    }

    public static Integer $int(String paramName) {
        return $int(paramName, null);
    }

    public static Integer $int(String paramName, Integer defaultValue) {
        String value = $(paramName);
        return HelpUtil.isEmptyString(value) ? defaultValue : Integer.parseInt(value);
    }

    public static Long $long(String paramName) {
        return $long(paramName, null);
    }

    public static Long $long(String paramName, Long defaultValue) {
        String value = $(paramName);
        return HelpUtil.isEmptyString(value) ? defaultValue : Long.parseLong(value);
    }

    public static Float $float(String paramName) {
        return $float(paramName, null);
    }

    public static Float $float(String paramName, Float defaultValue) {
        String value = $(paramName);
        return HelpUtil.isEmptyString(value) ? defaultValue : Float.parseFloat(value);
    }

    public static Double $double(String paramName) {
        return $double(paramName, null);
    }

    public static Double $double(String paramName, Double defaultValue) {
        String value = $(paramName);
        return HelpUtil.isEmptyString(value) ? defaultValue : Double.parseDouble(value);
    }

    public static MapEntity $params(HttpServletRequest request) {
        return $params(request, null);
    }

    public static MapEntity $params(HttpServletRequest request, String prefix) {
        MapEntity params = new MapEntity();

        int prefixLength = prefix == null ? 0 : prefix.length();
        Enumeration<?> paramNames = request.getParameterNames();
        while (paramNames != null && paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            if (prefixLength == 0 || paramName.startsWith(prefix)) {
                String[] values = request.getParameterValues(paramName);
                if (values == null || values.length == 0) {
                    // Do nothing, no values found at all.
                } else if (values.length > 1) {
                    params.put(paramName.substring(prefixLength), values);
                } else {
                    params.put(paramName.substring(prefixLength), values[0]);
                }
            }
        }

        return params;
    }

    public static Pagination $page() {
        return new Pagination($int("page", 0), $int("pageSize", 20), $("orderBy"));
    }

    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    public static Object $attr(String attrKey) {
        return getRequest().getAttribute(attrKey);
    }

    public static void $attr(String attrKey, Object attrValue) {
        getRequest().setAttribute(attrKey, attrValue);
    }

    public static void $attrs(Object... args) {
        HttpServletRequest request = getRequest();
        for (int i = 1; i < args.length; i += 2) {
            request.setAttribute(String.valueOf(args[i - 1]), args[i]);
        }
    }

    protected static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
