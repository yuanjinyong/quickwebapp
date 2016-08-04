package com.quickwebapp.usm.sys.mapper;

import java.util.List;

import com.quickwebapp.framework.core.entity.MapEntity;
import com.quickwebapp.framework.core.mapper.BaseMapper;
import com.quickwebapp.usm.sys.entity.MenuEntity;

public interface RoleMenuMapper extends BaseMapper<Integer, MapEntity> {
    /**
     * 查询角色可赋&已赋的菜单（权限）列表
     * 
     * @param params
     * @return
     */
    public List<MenuEntity> selectMenuListPage(MapEntity params);

    /**
     * 查询角色已赋的菜单（权限）列表
     * 
     * @param params
     * @return
     */
    public List<MenuEntity> selectRoleMenuListPage(MapEntity params);
}
