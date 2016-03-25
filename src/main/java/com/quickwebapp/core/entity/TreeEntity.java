/**
 * 
 */
package com.quickwebapp.core.entity;

import java.util.List;

/**
 * @author JohnYuan
 *
 */
public abstract class TreeEntity<P, E> extends BaseEntity<P> {
    private P f_parent_id; // 父级ID
    private List<E> children; // 子列表

    public P getF_parent_id() {
        return f_parent_id;
    }

    public void setF_parent_id(P f_parent_id) {
        this.f_parent_id = f_parent_id;
    }

    public List<E> getChildren() {
        return children;
    }

    public void setChildren(List<E> children) {
        this.children = children;
    }
}
