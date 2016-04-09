package com.quickwebapp.core.web.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.quickwebapp.core.entity.BaseEntity;
import com.quickwebapp.core.entity.MapEntity;
import com.quickwebapp.core.service.BaseService;

public abstract class BaseController<P, E extends BaseEntity<P>> extends TopController {
    // protected static final String OP_SELECT = "select"; // 查询
    // protected static final String OP_INSERT = "insert"; // 新增
    // protected static final String OP_UPDATE = "update"; // 修改
    // protected static final String OP_DELETE = "delete"; // 删除

    protected abstract BaseService<P, E> getService();

    public ResponseEntity<List<E>> list(MapEntity mapEntity) {
        return new ResponseEntity<List<E>>(getService().selectEntityListPage(mapEntity), HttpStatus.OK);
    }

    public ResponseEntity<Void> create(E entity, UriComponentsBuilder ucBuilder) {
        getService().insertEntity(entity);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/{id}").buildAndExpand(entity.getF_id()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    public ResponseEntity<E> get(P primaryKey) {
        return new ResponseEntity<E>(getService().selectEntity(primaryKey), HttpStatus.OK);
    }

    public ResponseEntity<E> update(P primaryKey, E entity) {
        getService().updateEntity(entity);
        return new ResponseEntity<E>(entity, HttpStatus.OK);
    }

    public ResponseEntity<E> delete(P primaryKey) {
        getService().deleteEntity(primaryKey);
        return new ResponseEntity<E>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<E> deleteBatch(MapEntity mapEntity) {
        getService().deleteEntities(mapEntity);
        return new ResponseEntity<E>(HttpStatus.NO_CONTENT);
    }

    // protected String toList(String viewPath) {
    // $attr("operation", $("operation"));
    // $attr("identity", $("identity"));
    // return viewPath + "/list";
    // }
    //
    // protected String toDetail(String viewPath, P primaryKey, Class<E> clz) throws InstantiationException,
    // IllegalAccessException, ClassNotFoundException, UnSupportedOperationException {
    // $attr("entity", getDetail(primaryKey, clz));
    // $attr("operation", $("operation"));
    // $attr("identity", $("identity"));
    // return viewPath + "/detail";
    // }
    //
    // protected String toDetail(String viewPath, E entity) {
    // $attr("entity", entity);
    // $attr("operation", $("operation"));
    // $attr("identity", $("identity"));
    // return viewPath + "/detail";
    // }
    //
    // @SuppressWarnings("unchecked")
    // protected E getDetail(P primaryKey, Class<E> clz) throws InstantiationException, IllegalAccessException,
    // ClassNotFoundException, UnSupportedOperationException {
    // String operation = $("operation");
    // if (OP_INSERT.equals(operation)) {
    // return (E) Class.forName(clz.getName()).newInstance();
    // } else if (OP_UPDATE.equals(operation) || OP_SELECT.equals(operation)) {
    // return getService().selectEntity(primaryKey);
    // } else {
    // throw new UnSupportedOperationException("不支持操作类型：" + operation + "！");
    // }
    // }
    //
    // protected MapEntity list(HttpServletRequest request) throws Exception {
    // MapEntity mapEntity = $params(request);
    // List<E> entityList = getService().selectEntityListPage(mapEntity);
    // MapEntity resultMap = new MapEntity();
    // resultMap.put("Rows", entityList);
    // resultMap.put("Total", mapEntity.getInteger("total", 0));
    // return resultMap;
    // }
    //
    // protected MapEntity save(E entity) throws UnSupportedOperationException {
    // return save(entity);
    // }
    //
    // protected MapEntity save(E entity, MapEntity data) throws UnSupportedOperationException {
    // String operation = $("operation");
    // if (OP_INSERT.equals(operation)) {
    // getService().insertEntity(entity);
    // } else if (OP_UPDATE.equals(operation)) {
    // getService().updateEntity(entity);
    // } else {
    // throw new UnSupportedOperationException("不支持操作类型[" + operation + "]！");
    // }
    //
    // return success(data, entity);
    // }
    //
    // protected MapEntity delete(P primaryKey) {
    // getService().deleteEntity(primaryKey);
    // return success();
    // }
}
