/**
 * 
 */
package com.quickwebapp.framework.core.entity;

import java.util.ArrayList;
import java.util.List;

import com.quickwebapp.framework.core.utils.HelpUtil;

/**
 * @author JohnYuan
 *
 */
public abstract class TreeNodeEntity<P, E extends TreeNodeEntity<P, ?>> extends BaseEntity<P> {
    private P f_parent_id; // 父级ID
    private Integer $$treeLevel; // 所处层数
    private List<E> children; // 子列表

    @SuppressWarnings("unchecked")
    public List<E> convertToList(Integer treeLevel) {
        List<E> list = new ArrayList<E>();
        convertToList((E) this, list, treeLevel);
        return list;
    }

    private void convertToList(E treeNode, List<E> list, Integer treeLevel) {
        list.add(treeNode);
        treeNode.set$$treeLevel(treeLevel);

        @SuppressWarnings("unchecked")
        List<E> children = (List<E>) treeNode.getChildren();
        if (!HelpUtil.isEmptyCollection(children)) {
            for (E child : children) {
                convertToList(child, list, treeLevel + 1);
            }
            children.clear();
        }
    }

    public P getF_parent_id() {
        return f_parent_id;
    }

    public void setF_parent_id(P f_parent_id) {
        this.f_parent_id = f_parent_id;
    }

    public Integer get$$treeLevel() {
        return $$treeLevel;
    }

    public void set$$treeLevel(Integer $$treeLevel) {
        this.$$treeLevel = $$treeLevel;
    }

    public List<E> getChildren() {
        return children;
    }

    public void setChildren(List<E> children) {
        this.children = children;
    }
}
