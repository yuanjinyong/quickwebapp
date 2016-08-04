package com.quickwebapp.framework.core.entity;

public class TenantEntity<P> extends BaseEntity<P> {
    private Integer f_tenant_cid; // 租户客户ID
    private Integer f_tenant_bid; // 租户运营实体ID

    public Integer getF_tenant_cid() {
        return f_tenant_cid;
    }

    public void setF_tenant_cid(Integer f_tenant_cid) {
        this.f_tenant_cid = f_tenant_cid;
    }

    public Integer getF_tenant_bid() {
        return f_tenant_bid;
    }

    public void setF_tenant_bid(Integer f_tenant_bid) {
        this.f_tenant_bid = f_tenant_bid;
    }
}
