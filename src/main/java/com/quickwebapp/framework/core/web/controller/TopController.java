package com.quickwebapp.framework.core.web.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.quickwebapp.framework.core.entity.BaseEntity;
import com.quickwebapp.framework.core.entity.MapEntity;
import com.quickwebapp.framework.core.exception.BusinessException;
import com.quickwebapp.framework.core.utils.HelpUtil;
import com.quickwebapp.framework.core.utils.Pagination;

public abstract class TopController {
    private Logger logger = LoggerFactory.getLogger(TopController.class);

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

    protected MapEntity success() {
        return success(new MapEntity());
    }

    protected MapEntity success(MapEntity data) {
        return success(data, null);
    }

    protected MapEntity success(BaseEntity<?> entity) {
        return success(new MapEntity(), entity);
    }

    protected MapEntity success(MapEntity data, Object entity) {
        data.put("operation", $("operation", "list"));
        data.safePut("identity", $("identity"));
        data.safePut("entity", entity);
        return result(true, "系统处理成功！", data);
    }

    protected MapEntity failed(String errorMsg) {
        return result(false, errorMsg, null);
    }

    private MapEntity result(boolean state, String message, MapEntity data) {
        MapEntity resultMap = new MapEntity();
        resultMap.put("state", state);
        resultMap.put("msg", message);
        resultMap.safePut("data", data);
        return resultMap;
    }

    /** 基于@ExceptionHandler异常处理 */
    @ExceptionHandler
    @ResponseBody
    public MapEntity exceptionHandler(HttpServletRequest request, Exception e) {
        String errorMsg = "";
        if (e instanceof DataAccessException) {
            Throwable root = ((DataAccessException) e).getRootCause();
            errorMsg = root != null ? root.getMessage() : ((DataAccessException) e).getMessage();
        } else if (e instanceof BusinessException) {
            errorMsg = ((BusinessException) e).getFormattedMessage();
        } else if (e instanceof NoSuchMethodException) {
            errorMsg = "请求的方法不存在!";
        } else if (e instanceof BindException) {
            errorMsg = "类型转换错误!";
        } else {
            errorMsg = e.toString();
        }
        logger.error(e.getMessage(), e);

        return failed(errorMsg);
    }

    protected static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
