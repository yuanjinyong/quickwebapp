package com.quickwebapp.framework.core.mapper;

import java.util.List;

import com.quickwebapp.framework.core.entity.MapEntity;

public interface BaseMapper<P, E> {
    /**
     * 根据主键查询单个实体对象
     * 
     * @param primaryKey
     *            主键
     * @return
     *         实体对象
     */
    public E selectEntity(P primaryKey);

    /**
     * 根据组合条件查询多个实体对象
     * 
     * @param mapEntity
     *            组合条件
     * @return
     *         实体对象列表
     */
    public List<E> selectEntityListPage(MapEntity mapEntity);

    /**
     * 根据组合条件查询多个数据记录，没行数据以Map形式返回
     * 
     * @param mapEntity
     * @return
     *         数据记录列表
     */
    public List<MapEntity> selectMapEntityListPage(MapEntity mapEntity);

    /**
     * 插入单个实体对象
     * 
     * @param mapEntity
     *            实体对象
     */
    public void insertEntity(E mapEntity);

    /**
     * 批量插入多个实体对象
     * 
     * @param entityList
     *            实体对象列表
     */
    public void insertEntities(List<E> entityList);

    /**
     * 根据主键更新单个实体对象
     * 
     * @param entity
     *            实体对象
     */
    public void updateEntity(E entity);

    /**
     * 暂未实现
     * 
     * @param entityList
     */
    public void updateEntities(List<E> entityList);

    /**
     * 根据主键删除单个实体对象
     * 
     * @param primaryKey
     *            主键
     */
    public void deleteEntity(P primaryKey);

    /**
     * 根据组合条件删除多个实体对象
     * 
     * @param mapEntity
     *            组合条件
     */
    public void deleteEntities(MapEntity mapEntity);

    /**
     * 根据主键判断实体对象是否可以删除，返回0表示可以删除
     * 
     * @param primaryKey
     *            主键
     * @return
     *         0表示可以删除，大于0表示有数据关联不能删除
     */
    public Integer isCanDeleteEntity(P primaryKey);
}
