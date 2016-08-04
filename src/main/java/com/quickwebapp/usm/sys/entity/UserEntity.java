package com.quickwebapp.usm.sys.entity;

import java.sql.Timestamp;
import java.util.List;

import com.quickwebapp.framework.core.entity.MapEntity;
import com.quickwebapp.framework.core.entity.TenantEntity;

public class UserEntity extends TenantEntity<Integer> {
    public static final String SUPERADMIN = "SuperAdmin";
    public static final String ADMIN = "admin";
    private String f_account;// 账号
    private String f_password;// 密码
    private String f_name;// 姓名
    private String f_remark;// 备注说明
    private Integer f_status;// 状态，1(正常)、2（锁定）、3（注销）
    private Timestamp f_create_time; // 创建时间
    private Timestamp f_last_login_time; // 最后登录时间
    private Timestamp f_locked_time; // 锁定时间
    private Timestamp f_unregister_time; // 注销时间
    private Integer f_is_can_login; // 是否允许登录，1(是)、2(否)
    private UserExtEntity userExtEntity; // 用户（操作员）扩展信息
    private List<MapEntity> roleList; // 用户（操作员）角色列表
    private List<MapEntity> menuList; // 用户（操作员）菜单（权限）列表

    public String getF_account() {
        return f_account;
    }

    public void setF_account(String f_account) {
        this.f_account = f_account;
    }

    public String getF_password() {
        return f_password;
    }

    public void setF_password(String f_password) {
        this.f_password = f_password;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getF_remark() {
        return f_remark;
    }

    public void setF_remark(String f_remark) {
        this.f_remark = f_remark;
    }

    public Integer getF_status() {
        return f_status;
    }

    public void setF_status(Integer f_status) {
        this.f_status = f_status;
    }

    public Timestamp getF_create_time() {
        return f_create_time;
    }

    public void setF_create_time(Timestamp f_create_time) {
        this.f_create_time = f_create_time;
    }

    public Timestamp getF_last_login_time() {
        return f_last_login_time;
    }

    public void setF_last_login_time(Timestamp f_last_login_time) {
        this.f_last_login_time = f_last_login_time;
    }

    public Timestamp getF_locked_time() {
        return f_locked_time;
    }

    public void setF_locked_time(Timestamp f_locked_time) {
        this.f_locked_time = f_locked_time;
    }

    public Timestamp getF_unregister_time() {
        return f_unregister_time;
    }

    public void setF_unregister_time(Timestamp f_unregister_time) {
        this.f_unregister_time = f_unregister_time;
    }

    public Integer getF_is_can_login() {
        return f_is_can_login;
    }

    public void setF_is_can_login(Integer f_is_can_login) {
        this.f_is_can_login = f_is_can_login;
    }

    public UserExtEntity getUserExtEntity() {
        return userExtEntity;
    }

    public void setUserExtEntity(UserExtEntity userExtEntity) {
        this.userExtEntity = userExtEntity;
    }

    public List<MapEntity> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<MapEntity> roleList) {
        this.roleList = roleList;
    }

    public List<MapEntity> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MapEntity> menuList) {
        this.menuList = menuList;
    }

    public boolean isSuperAdmin() {
        return SUPERADMIN.equals(f_account);
    }

    public boolean isAdmin() {
        return ADMIN.equals(f_account);
    }
}
