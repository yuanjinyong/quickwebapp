package com.quickwebapp.usm.sys.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quickwebapp.framework.core.entity.MapEntity;
import com.quickwebapp.framework.core.exception.BusinessException;
import com.quickwebapp.framework.core.mapper.BaseMapper;
import com.quickwebapp.framework.core.service.BaseService;
import com.quickwebapp.usm.sys.entity.DictGroupEntity;
import com.quickwebapp.usm.sys.entity.DictItemEntity;
import com.quickwebapp.usm.sys.entity.UserEntity;
import com.quickwebapp.usm.sys.mapper.DictGroupMapper;
import com.quickwebapp.usm.sys.mapper.DictItemMapper;
import com.quickwebapp.usm.sys.security.SecurityUtil;

@Service
@Transactional
public class DictGroupService extends BaseService<Integer, DictGroupEntity> {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DictGroupMapper dictGroupMapper;
    @Autowired
    private DictItemMapper dictItemMapper;

    @Override
    protected BaseMapper<Integer, DictGroupEntity> getMapper() {
        return dictGroupMapper;
    }

    @Override
    public DictGroupEntity selectEntity(Integer primaryKey) {
        DictGroupEntity entity = super.selectEntity(primaryKey);
        entity.setItemList(selectDictItemList(entity.getF_code()));
        return entity;
    }

    private List<DictItemEntity> selectDictItemList(String f_code) {
        MapEntity mapEntity = new MapEntity();
        mapEntity.put("f_code", f_code);
        mapEntity.setOrderBy("f_item_order");
        return dictItemMapper.selectEntityListPage(mapEntity);
    }

    @Override
    public void deleteEntity(Integer primaryKey) {
        DictGroupEntity entity = super.selectEntity(primaryKey);

        deleteDictItemList(entity.getF_code());

        // 最后在删除自己
        super.deleteEntity(primaryKey);
    }

    private void deleteDictItemList(String f_code) {
        MapEntity mapEntity = new MapEntity();
        mapEntity.put("f_code", f_code);

        UserEntity user = SecurityUtil.getCurrentUser();
        mapEntity.put("f_tenant_cid", user.getF_tenant_cid());
        mapEntity.put("f_tenant_bid", user.getF_tenant_bid());

        dictItemMapper.deleteEntities(mapEntity);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public DictItemEntity selectDictItemEntity(Integer primaryKey) {
        return dictItemMapper.selectEntity(primaryKey);
    }

    public List<DictItemEntity> selectDictItemEntityListPage(MapEntity params) {
        return dictItemMapper.selectEntityListPage(params);
    }

    public void insertDictItemEntity(DictItemEntity entity) {
        dictItemMapper.insertEntity(entity);
    }

    public void updateDictItemEntity(DictItemEntity entity) {
        dictItemMapper.updateEntity(entity);
    }

    public void deleteDictItemEntity(Integer primaryKey) {
        if (dictItemMapper.isCanDeleteEntity(primaryKey) > 0) {
            throw new BusinessException("存在关联数据，不能删除！");
        }

        dictItemMapper.deleteEntity(primaryKey);
    }
}
