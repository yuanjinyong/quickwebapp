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
import com.quickwebapp.framework.core.utils.HelpUtil;
import com.quickwebapp.usm.sys.entity.MenuEntity;
import com.quickwebapp.usm.sys.entity.RoleEntity;
import com.quickwebapp.usm.sys.mapper.RoleMapper;
import com.quickwebapp.usm.sys.mapper.RoleMenuMapper;

@Service
@Transactional
public class RoleService extends BaseService<Integer, RoleEntity> {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    protected BaseMapper<Integer, RoleEntity> getMapper() {
        return roleMapper;
    }

    @Override
    public RoleEntity selectEntity(Integer primaryKey) {
        RoleEntity entity = super.selectEntity(primaryKey);
        entity.setMenuList(selectMenuList(entity.getF_id()));
        return entity;
    }

    @Override
    public void insertEntity(RoleEntity entity) {
        super.insertEntity(entity);

        insertMenuList(entity);
    }

    public void updateEntity(RoleEntity entity) {
        deleteMenuList(entity.getF_id());
        insertMenuList(entity);

        super.updateEntity(entity);
    }

    @Override
    public void deleteEntity(Integer primaryKey) {
        RoleEntity entity = super.selectEntity(primaryKey);
        if (RoleEntity.ROLE_ADMIN.equals(entity.getF_role_code())
                || RoleEntity.ROLE_WEIXIN.equals(entity.getF_role_code())) {
            throw new BusinessException("系统管理员角色不能删除！");
        }

        deleteMenuList(entity.getF_id());

        // 最后在删除自己
        super.deleteEntity(primaryKey);
    }

    private List<MapEntity> selectMenuList(Integer f_role_id) {
        MapEntity mapEntity = new MapEntity();
        mapEntity.put("f_role_id", f_role_id);
        return roleMenuMapper.selectEntityListPage(mapEntity);
    }

    private void insertMenuList(RoleEntity role) {
        List<MapEntity> menuList = role.getMenuList();
        if (!HelpUtil.isEmptyCollection(menuList)) {
            for (MapEntity map : menuList) {
                map.put("f_role_id", role.getF_id());
            }

            roleMenuMapper.insertEntities(menuList);
        }
    }

    private void deleteMenuList(Integer f_role_id) {
        MapEntity mapEntity = new MapEntity();
        mapEntity.put("f_role_id", f_role_id);
        roleMenuMapper.deleteEntities(mapEntity);
    }

    /**
     * 查询角色可赋&已赋的菜单（权限）列表
     * 
     * @param params
     * @return
     */
    public List<MenuEntity> listRoleMenu(MapEntity params) {
        return roleMenuMapper.selectMenuListPage(params);
    }
}
