package com.quickwebapp.core.web.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.quickwebapp.core.entity.BaseEntity;
import com.quickwebapp.core.entity.MapEntity;
import com.quickwebapp.core.service.BaseService;
import com.quickwebapp.core.utils.HelpUtil;

public abstract class BaseController<P, E extends BaseEntity<P>> extends TopController {
    // protected static final String OP_SELECT = "select"; // 查询
    // protected static final String OP_INSERT = "insert"; // 新增
    // protected static final String OP_UPDATE = "update"; // 修改
    // protected static final String OP_DELETE = "delete"; // 删除

    protected abstract BaseService<P, E> getService();

    public ResponseEntity<MapEntity> page(MapEntity mapEntity) {
        if (mapEntity.getPageSize() == null) {
            mapEntity.setPageSizeWithDefault();
        }

        List<E> currentPageData = getService().selectEntityListPage(mapEntity);
        return new ResponseEntity<MapEntity>(buildPageEntity(mapEntity, currentPageData), HttpStatus.OK);
    }

    private MapEntity buildPageEntity(MapEntity mapEntity, List<E> currentPageData) {
        MapEntity pageEntity = new MapEntity();
        pageEntity.setPageSize(mapEntity.getPageSize());
        pageEntity.setCurrentPage(mapEntity.getCurrentPage() != null ? mapEntity.getCurrentPage()
                : (HelpUtil.isEmptyCollection(currentPageData) ? 0 : 1));
        pageEntity.setCurrentPageData(currentPageData);
        pageEntity.setTotalCount(mapEntity.getTotalCount());
        return pageEntity;
    }

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
}
