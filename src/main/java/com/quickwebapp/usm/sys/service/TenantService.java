package com.quickwebapp.usm.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quickwebapp.framework.core.entity.MapEntity;
import com.quickwebapp.framework.core.exception.BusinessException;
import com.quickwebapp.framework.core.mapper.BaseMapper;
import com.quickwebapp.framework.core.service.BaseService;
import com.quickwebapp.framework.core.utils.HelpUtil;
import com.quickwebapp.usm.sys.entity.TenantBusinessEntity;
import com.quickwebapp.usm.sys.entity.TenantCustomerEntity;
import com.quickwebapp.usm.sys.mapper.TenantBusinessMapper;
import com.quickwebapp.usm.sys.mapper.TenantCustomerMapper;

@Service
@Transactional
public class TenantService extends BaseService<Integer, TenantCustomerEntity> {
    @Autowired
    private TenantCustomerMapper tenantCustomerMapper;
    @Autowired
    private TenantBusinessMapper tenantBusinessMapper;

    @Override
    protected BaseMapper<Integer, TenantCustomerEntity> getMapper() {
        return tenantCustomerMapper;
    }

    @Override
    public TenantCustomerEntity selectEntity(Integer primaryKey) {
        TenantCustomerEntity entity = super.selectEntity(primaryKey);
        entity.setTenantBusinessList(selectTenantBusinessList(entity.getF_id()));
        return entity;
    }

    private List<TenantBusinessEntity> selectTenantBusinessList(Integer f_tenant_cid) {
        MapEntity mapEntity = new MapEntity();
        mapEntity.put("f_tenant_cid", f_tenant_cid);
        mapEntity.setOrderBy("f_id");
        return tenantBusinessMapper.selectEntityListPage(mapEntity);
    }

    @Override
    public void insertEntity(TenantCustomerEntity entity) {
        super.insertEntity(entity);

        insertTenantBusinessList(entity);
    }

    private void insertTenantBusinessList(TenantCustomerEntity entity) {
        List<TenantBusinessEntity> tenantBusinessList = entity.getTenantBusinessList();
        if (!HelpUtil.isEmptyCollection(tenantBusinessList)) {
            for (TenantBusinessEntity tenantBusiness : tenantBusinessList) {
                tenantBusiness.setF_tenant_cid(entity.getF_id());
            }
            tenantBusinessMapper.insertEntities(tenantBusinessList);
        }
    }

    public void updateEntity(TenantCustomerEntity entity) {
        deleteTenantBusinessList(entity.getF_id());
        insertTenantBusinessList(entity);

        super.updateEntity(entity);
    }

    @Override
    public void deleteEntity(Integer primaryKey) {
        TenantCustomerEntity entity = super.selectEntity(primaryKey);
        if (entity.getF_id() == 0) {
            throw new BusinessException("默认的租户不能删除！");
        }

        deleteTenantBusinessList(primaryKey);

        // 最后在删除自己
        super.deleteEntity(primaryKey);
    }

    private void deleteTenantBusinessList(Integer f_tenant_cid) {
        MapEntity mapEntity = new MapEntity();
        mapEntity.put("f_tenant_cid", f_tenant_cid);
        tenantBusinessMapper.deleteEntities(mapEntity);
    }
}
