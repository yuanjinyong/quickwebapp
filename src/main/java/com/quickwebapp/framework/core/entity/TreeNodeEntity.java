/**
 * 
 */
package com.quickwebapp.framework.core.entity;

import java.util.List;

/**
 * @author JohnYuan
 *
 */
public abstract class TreeNodeEntity<P, E extends TreeNodeEntity<P, E>> extends BaseEntity<P> {
    private P f_parent_id; // 父级ID
    private Boolean checked; // 是否选中
    private Boolean expanded; // 是否展开
    private List<E> children; // 子列表

    public P getF_parent_id() {
        return f_parent_id;
    }

    public void setF_parent_id(P f_parent_id) {
        this.f_parent_id = f_parent_id;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }

    public List<E> getChildren() {
        return children;
    }

    public void setChildren(List<E> children) {
        this.children = children;
    }
}
