/**
 * 
 */
package com.quickwebapp.usm.sys.entity;

import com.quickwebapp.framework.core.entity.BaseEntity;

/**
 * @author Administrator
 *
 */
public class TenantBusinessEntity extends BaseEntity<Integer> {
    private Integer f_tenant_cid; // 所属租户客户ID
    private String f_tenant_cname; // 所属租户客户名称
    private String f_name; // 租户运营实体名称
    private String f_remark; // 租户运营实体描述

    public Integer getF_tenant_cid() {
        return f_tenant_cid;
    }

    public void setF_tenant_cid(Integer f_tenant_cid) {
        this.f_tenant_cid = f_tenant_cid;
    }

    public String getF_tenant_cname() {
        return f_tenant_cname;
    }

    public void setF_tenant_cname(String f_tenant_cname) {
        this.f_tenant_cname = f_tenant_cname;
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
}
