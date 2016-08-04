package com.quickwebapp.usm.sys.mapper;

import java.util.List;

import com.quickwebapp.framework.core.entity.MapEntity;
import com.quickwebapp.framework.core.mapper.BaseMapper;

public interface UserRoleMapper extends BaseMapper<Integer, MapEntity> {
    /**
     * 查询用户（操作员）可赋&已赋的角色列表
     * 
     * @param params
     * @return
     */
    public List<MapEntity> selectRoleListPage(MapEntity params);
}
