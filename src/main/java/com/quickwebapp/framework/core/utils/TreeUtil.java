/**
 * 
 */
package com.quickwebapp.framework.core.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.quickwebapp.framework.core.entity.TreeNodeEntity;

/**
 * @author 袁进勇
 *
 */
public final class TreeUtil {
    /**
     * 把列表形式的转换为树形的结构
     * 注意：列表形式的必须是已经排好序的，父节点必须排在子节点的前面
     * 
     * @param nodeList
     * @return
     */
    public static <T extends TreeNodeEntity<?, T>> List<T> listToTree(List<T> nodeList) {
        // 找出顶层的节点返回
        List<T> nodeTree = new ArrayList<T>();

        // 组织好父子关系
        Map<Object, T> nodeMap = new HashMap<Object, T>();
        for (T node : nodeList) {
            nodeMap.put(node.getF_id(), node);

            T parentNode = nodeMap.get(node.getF_parent_id());
            if (parentNode == null) {
                nodeTree.add(node);
            } else {
                parentNode.getChildren().add(node);
            }

            node.setChildren(new ArrayList<T>());
        }

        return nodeTree;
    }
}
