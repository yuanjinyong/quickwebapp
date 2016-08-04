package com.quickwebapp.usm.sys.entity;

import java.util.List;

import com.quickwebapp.framework.core.entity.MapEntity;
import com.quickwebapp.framework.core.entity.TenantEntity;

public class RoleEntity extends TenantEntity<Integer> {
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_WEIXIN = "WEIXIN";
    private String f_role_code;// 角色编码
    private String f_role_name;// 角色名称
    private String f_remark;// 角色描述
    private List<MapEntity> menuList; // 角色所赋的菜单（权限）列表

    public String getF_role_code() {
        return f_role_code;
    }

    public void setF_role_code(String f_role_code) {
        this.f_role_code = f_role_code;
    }

    public String getF_role_name() {
        return f_role_name;
    }

    public void setF_role_name(String f_role_name) {
        this.f_role_name = f_role_name;
    }

    public String getF_remark() {
        return f_remark;
    }

    public void setF_remark(String f_remark) {
        this.f_remark = f_remark;
    }

    public List<MapEntity> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MapEntity> menuList) {
        this.menuList = menuList;
    }
}
