package com.quickwebapp.framework.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.quickwebapp.framework.core.entity.MapEntity;
import com.quickwebapp.framework.core.exception.BusinessException;
import com.quickwebapp.framework.core.mapper.BaseMapper;

public abstract class BaseService<P, E> {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected abstract BaseMapper<P, E> getMapper();

    public E selectEntity(P primaryKey) {
        return getMapper().selectEntity(primaryKey);
    }

    public List<E> selectEntityListPage(MapEntity mapEntity) {
        return getMapper().selectEntityListPage(mapEntity);
    }

    public List<MapEntity> selectMapEntityListPage(MapEntity mapEntity) {
        return getMapper().selectMapEntityListPage(mapEntity);
    }

    public void insertEntity(E entity) {
        getMapper().insertEntity(entity);
    }

    public void insertEntities(List<E> entityList) {
        getMapper().insertEntities(entityList);
    }

    public void updateEntity(E entity) {
        getMapper().updateEntity(entity);
    }

    public void deleteEntity(P primaryKey) {
        if (getMapper().isCanDeleteEntity(primaryKey) > 0) {
            throw new BusinessException("存在关联数据，不能删除！");
        }

        getMapper().deleteEntity(primaryKey);
    }

    public void deleteEntities(MapEntity mapEntity) {
        getMapper().deleteEntities(mapEntity);
    }
}
