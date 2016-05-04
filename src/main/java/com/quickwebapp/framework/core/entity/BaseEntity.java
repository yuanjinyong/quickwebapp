/**
 * 
 */
package com.quickwebapp.framework.core.entity;

/**
 * @author JohnYuan
 *
 */
public abstract class BaseEntity<P> {
    private P f_id; // 主键

    public P getF_id() {
        return f_id;
    }

    public void setF_id(P f_id) {
        this.f_id = f_id;
    }
}
