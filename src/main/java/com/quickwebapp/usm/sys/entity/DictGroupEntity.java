package com.quickwebapp.usm.sys.entity;

import java.util.List;

import com.quickwebapp.framework.core.entity.BaseEntity;

public class DictGroupEntity extends BaseEntity<Integer> {
    private String f_code;// 字典组编码
    private String f_remark;// 字典组描述
    private String f_type;// 字典组类型，单表，通用
    private String f_sql;// 查询字典项的SQL语句，单表时有效
    private List<DictItemEntity> itemList; // 角色所赋的菜单（权限）列表

    public String getF_code() {
        return f_code;
    }

    public void setF_code(String f_code) {
        this.f_code = f_code;
    }

    public String getF_remark() {
        return f_remark;
    }

    public void setF_remark(String f_remark) {
        this.f_remark = f_remark;
    }

    public String getF_type() {
        return f_type;
    }

    public void setF_type(String f_type) {
        this.f_type = f_type;
    }

    public String getF_sql() {
        return f_sql;
    }

    public void setF_sql(String f_sql) {
        this.f_sql = f_sql;
    }

    public List<DictItemEntity> getItemList() {
        return itemList;
    }

    public void setItemList(List<DictItemEntity> itemList) {
        this.itemList = itemList;
    }
}
