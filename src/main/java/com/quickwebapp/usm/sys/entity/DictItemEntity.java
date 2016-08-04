package com.quickwebapp.usm.sys.entity;

import com.quickwebapp.framework.core.entity.TenantEntity;

public class DictItemEntity extends TenantEntity<Integer> {
    private String f_code;// 字典组编码
    private String f_item_code;// 字典项编码
    private String f_item_text;// 字典项描述
    private String f_item_order;// 排序

    public String getF_code() {
        return f_code;
    }

    public void setF_code(String f_code) {
        this.f_code = f_code;
    }

    public String getF_item_code() {
        return f_item_code;
    }

    public void setF_item_code(String f_item_code) {
        this.f_item_code = f_item_code;
    }

    public String getF_item_text() {
        return f_item_text;
    }

    public void setF_item_text(String f_item_text) {
        this.f_item_text = f_item_text;
    }

    public String getF_item_order() {
        return f_item_order;
    }

    public void setF_item_order(String f_item_order) {
        this.f_item_order = f_item_order;
    }
}
