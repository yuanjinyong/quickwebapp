package com.quickwebapp.framework.core.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.quickwebapp.framework.core.entity.BaseEntity;
import com.quickwebapp.framework.core.entity.MapEntity;
import com.quickwebapp.framework.core.exception.BusinessException;
import com.quickwebapp.framework.core.service.BaseService;
import com.quickwebapp.framework.core.utils.HelpUtil;
import com.quickwebapp.usm.sys.security.OpenIdAuthenticationToken;

public abstract class BaseController<P, E extends BaseEntity<P>> extends TopController {
    private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);

    protected abstract BaseService<P, E> getService();

    protected String getF_openid(HttpServletRequest request) {
        OpenIdAuthenticationToken token = (OpenIdAuthenticationToken) request.getUserPrincipal();
        return token.getOpenid();
    }

    public ResponseEntity<List<E>> list(MapEntity params) {
        return new ResponseEntity<List<E>>(getService().selectEntityListPage(params), HttpStatus.OK);
    }

    public ResponseEntity<MapEntity> page(MapEntity params) {
        params.setCurrentPageData(getService().selectEntityListPage(params));
        return new ResponseEntity<MapEntity>(params, HttpStatus.OK);
    }

    public ResponseEntity<MapEntity> create(E entity, UriComponentsBuilder ucBuilder) {
        getService().insertEntity(entity);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/{id}").buildAndExpand(entity.getF_id()).toUri());
        return new ResponseEntity<MapEntity>(success(entity), headers, HttpStatus.CREATED);
    }

    public ResponseEntity<MapEntity> get(P primaryKey) {
        return new ResponseEntity<MapEntity>(success(getService().selectEntity(primaryKey)), HttpStatus.OK);
    }

    public ResponseEntity<MapEntity> update(P primaryKey, E entity) {
        getService().updateEntity(entity);
        return new ResponseEntity<MapEntity>(success(entity), HttpStatus.OK);
    }

    public ResponseEntity<MapEntity> delete(P primaryKey) {
        getService().deleteEntity(primaryKey);

        // 这里我们把执行结果通过state和msg字段返回给客户端，所以返回的状态码从204改为200。
        // 返回码具体意义请参考HTTP协议：https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html
        // return new ResponseEntity<MapEntity>(success(), HttpStatus.NO_CONTENT);
        return new ResponseEntity<MapEntity>(success(), HttpStatus.OK);
    }

    public ResponseEntity<MapEntity> deleteBatch(MapEntity params) {
        getService().deleteEntities(params);

        // 这里我们把执行结果通过state和msg字段返回给客户端，所以返回的状态码从204改为200。
        // 返回码具体意义请参考HTTP协议：https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html
        // return new ResponseEntity<MapEntity>(success(), HttpStatus.NO_CONTENT);
        return new ResponseEntity<MapEntity>(success(), HttpStatus.OK);
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
            errorMsg = e.getMessage();
            if (HelpUtil.isEmptyString(errorMsg)) {
                errorMsg = e.toString();
            }
        }
        LOG.error(e.getMessage(), e);

        return failed(errorMsg);
    }
}
