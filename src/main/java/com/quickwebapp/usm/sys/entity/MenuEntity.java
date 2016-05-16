package com.quickwebapp.usm.sys.entity;

import java.util.List;

import com.quickwebapp.framework.core.entity.MapEntity;
import com.quickwebapp.framework.core.entity.TreeNodeEntity;

public class MenuEntity extends TreeNodeEntity<String, MenuEntity> {
    private String f_parent_name; // 父级名称
    private String f_parent_ids; // 所有父级的ID，用“/”分隔
    private String f_menu_name; // 菜单名称
    private String f_menu_remark; // 菜单描述
    private String f_icon; // 图标
    private Integer f_type; // 类型，1目录、2页面、3按钮
    private String f_url_id; // 菜单对应的URL，目录和按钮不需要填写，只有对应页面的菜单才需要填写
    private String f_url;
    private Integer f_order; // 排序，10,、20、30
    private Integer f_is_show; // 当前站是否启用，1启用，2不启用
    private List<MapEntity> menuUrlList; // 页面或者按钮需要访问的URL地址

    public String getF_parent_name() {
        return f_parent_name;
    }

    public void setF_parent_name(String f_parent_name) {
        this.f_parent_name = f_parent_name;
    }

    public String getF_parent_ids() {
        return f_parent_ids;
    }

    public void setF_parent_ids(String f_parent_ids) {
        this.f_parent_ids = f_parent_ids;
    }

    public String getF_menu_name() {
        return f_menu_name;
    }

    public void setF_menu_name(String f_menu_name) {
        this.f_menu_name = f_menu_name;
    }

    public String getF_menu_remark() {
        return f_menu_remark;
    }

    public void setF_menu_remark(String f_menu_remark) {
        this.f_menu_remark = f_menu_remark;
    }

    public String getF_icon() {
        return f_icon;
    }

    public void setF_icon(String f_icon) {
        this.f_icon = f_icon;
    }

    public Integer getF_type() {
        return f_type;
    }

    public void setF_type(Integer f_type) {
        this.f_type = f_type;
    }

    public String getF_url_id() {
        return f_url_id;
    }

    public void setF_url_id(String f_url_id) {
        this.f_url_id = f_url_id;
    }

    public String getF_url() {
        return f_url;
    }

    public void setF_url(String f_url) {
        this.f_url = f_url;
    }

    public Integer getF_order() {
        return f_order;
    }

    public void setF_order(Integer f_order) {
        this.f_order = f_order;
    }

    public Integer getF_is_show() {
        return f_is_show;
    }

    public void setF_is_show(Integer f_is_show) {
        this.f_is_show = f_is_show;
    }

    public List<MapEntity> getMenuUrlList() {
        return menuUrlList;
    }

    public void setMenuUrlList(List<MapEntity> menuUrlList) {
        this.menuUrlList = menuUrlList;
    }

}
