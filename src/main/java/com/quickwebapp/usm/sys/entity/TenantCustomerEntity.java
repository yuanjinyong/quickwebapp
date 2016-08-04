/**
 * 
 */
package com.quickwebapp.usm.sys.entity;

import java.util.List;

import com.quickwebapp.framework.core.entity.BaseEntity;

/**
 * @author Administrator
 *
 */
public class TenantCustomerEntity extends BaseEntity<Integer> {
    private String f_name; // 租户客户名称
    private String f_remark; // 租户客户描述
    private List<TenantBusinessEntity> tenantBusinessList; // 客户名下的运营实体列表

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

    public List<TenantBusinessEntity> getTenantBusinessList() {
        return tenantBusinessList;
    }

    public void setTenantBusinessList(List<TenantBusinessEntity> tenantBusinessList) {
        this.tenantBusinessList = tenantBusinessList;
    }
}
