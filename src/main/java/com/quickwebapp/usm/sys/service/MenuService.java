package com.quickwebapp.usm.sys.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quickwebapp.framework.core.entity.MapEntity;
import com.quickwebapp.framework.core.exception.BusinessException;
import com.quickwebapp.framework.core.mapper.BaseMapper;
import com.quickwebapp.framework.core.service.BaseService;
import com.quickwebapp.framework.core.utils.HelpUtil;
import com.quickwebapp.usm.sys.entity.MenuEntity;
import com.quickwebapp.usm.sys.entity.UrlEntity;
import com.quickwebapp.usm.sys.mapper.MenuMapper;
import com.quickwebapp.usm.sys.mapper.MenuUrlMapper;
import com.quickwebapp.usm.sys.mapper.UrlMapper;

@Service
@Transactional
public class MenuService extends BaseService<String, MenuEntity> {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private MenuUrlMapper menuUrlMapper;
    @Autowired
    private UrlMapper urlMapper;

    @Override
    protected BaseMapper<String, MenuEntity> getMapper() {
        return menuMapper;
    }

    /**
     * 获取指定菜单节点的菜单树
     * 
     * @param menuId
     *            根节点的菜单ID
     * @param includeAllChildren
     *            是否递归包含所有下级子菜单，false则只返回一级子菜单
     * @return
     */
    public MenuEntity getMenuTree(String menuId, boolean includeAllChildren) {
        MenuEntity rootMenu = getMapper().selectEntity(menuId);

        MapEntity mapEntity = new MapEntity();
        if (includeAllChildren) {
            mapEntity.put("f_parent_ids",
                    (HelpUtil.isEmptyString(rootMenu.getF_parent_ids()) ? "" : rootMenu.getF_parent_ids() + "/")
                            + rootMenu.getF_id());
        } else {
            mapEntity.put("f_parent_id", rootMenu.getF_id());
        }

        rootMenu.setChildren(getMenuList(mapEntity));

        return rootMenu;
    }

    public List<MenuEntity> getMenuList(MapEntity mapEntity) {
        mapEntity.setPageSizeWithMax().setOrderBy("f_parent_ids, f_order");
        List<MenuEntity> menuList = getMapper().selectEntityListPage(mapEntity);
        Map<String, MenuEntity> menuMap = new HashMap<String, MenuEntity>();
        for (MenuEntity menu : menuList) {
            menu.setChildren(new ArrayList<MenuEntity>());
            menuMap.put(menu.getF_id(), menu);

            MenuEntity parentMenu = menuMap.get(menu.getF_parent_id());
            if (parentMenu != null) {
                parentMenu.getChildren().add(menu);
            }
        }

        List<MenuEntity> childrenList = new ArrayList<MenuEntity>();
        for (MenuEntity menu : menuList) {
            if (!menuMap.containsKey(menu.getF_parent_id())) {
                childrenList.add(menu);
            }
        }

        return childrenList;
    }

    @Override
    public MenuEntity selectEntity(String primaryKey) {
        MenuEntity menu = super.selectEntity(primaryKey);
        menu.setMenuUrlList(selectMenuUrl(menu.getF_id()));
        return menu;
    }

    @Override
    public void insertEntity(MenuEntity entity) {
        insertMenuUrl(entity);

        super.insertEntity(entity);
    }

    public void updateEntity(MenuEntity entity) {
        deleteMenuUrl(entity);
        insertMenuUrl(entity);

        super.updateEntity(entity);
    }

    @Override
    public void deleteEntity(String primaryKey) {
        MenuEntity menu = super.selectEntity(primaryKey);
        if (menu.getF_parent_id() == null) {
            throw new BusinessException("顶级根菜单不能删除！");
        }

        // 查出所有的子菜单，先删除子菜单
        MapEntity mapEntity = new MapEntity();
        mapEntity.put("f_parent_ids", menu.getF_parent_ids() + "/" + menu.getF_id());
        List<MenuEntity> subMenuList = super.selectEntityListPage(mapEntity);
        for (MenuEntity subMenu : subMenuList) {
            deleteMenuUrl(subMenu);
            super.deleteEntity(subMenu.getF_id());
        }

        // 最后在删除自己
        deleteMenuUrl(menu);
        super.deleteEntity(primaryKey);
    }

    public List<MapEntity> selectMenuUrl(String f_menu_id) {
        MapEntity mapEntity = new MapEntity();
        mapEntity.put("f_menu_id", f_menu_id);
        return menuUrlMapper.selectEntityListPage(mapEntity);
    }

    private void insertMenuUrl(MenuEntity menu) {
        List<MapEntity> urlList = menu.getMenuUrlList();
        if (!HelpUtil.isEmptyCollection(urlList)) {
            menuUrlMapper.insertEntities(urlList);
        }
    }

    private void deleteMenuUrl(MenuEntity menu) {
        MapEntity mapEntity = new MapEntity();
        mapEntity.put("f_menu_id", menu.getF_id());
        menuUrlMapper.deleteEntities(mapEntity);
    }

    public void insertMenuPage(MenuEntity pageMenu, String[] hasOperations) {
        List<MenuEntity> menuList = new ArrayList<MenuEntity>();
        menuList.add(pageMenu);

        String rootUrl = pageMenu.getF_url().substring(0, pageMenu.getF_url().lastIndexOf("/"));
        List<UrlEntity> urlEntityList = getUrlEntityList(rootUrl);
        UrlEntity listUrlEntity = getUrlEntity(urlEntityList, rootUrl + "/list");
        UrlEntity toDetailUrlEntity = getUrlEntity(urlEntityList, rootUrl + "/toDetail");
        UrlEntity saveUrlEntity = getUrlEntity(urlEntityList, rootUrl + "/save");
        UrlEntity deleteUrlEntity = getUrlEntity(urlEntityList, rootUrl + "/delete");
        UrlEntity auditUrlEntity = getUrlEntity(urlEntityList, rootUrl + "/audit");
        UrlEntity backUrlEntity = getUrlEntity(urlEntityList, rootUrl + "/back");

        List<MapEntity> menUrlList = new ArrayList<MapEntity>();
        if (listUrlEntity != null) {
            menUrlList.add(buildMenUrl(pageMenu, listUrlEntity));
        }

        String parenMenuName = pageMenu.getF_menu_name();
        String suffix = parenMenuName.endsWith("管理")
                ? parenMenuName.substring(0, parenMenuName.length() - "管理".length()) : parenMenuName;
        List<String> operationList = Arrays.asList(hasOperations);
        if (operationList.contains("ZJ") && toDetailUrlEntity != null && saveUrlEntity != null) {
            MenuEntity operationMenu = buildOperationMenuEntity(pageMenu, 10, "ZJ", "增加", "增加" + suffix);
            menuList.add(operationMenu);
            menUrlList.add(buildMenUrl(operationMenu, toDetailUrlEntity));
            menUrlList.add(buildMenUrl(operationMenu, saveUrlEntity));
        }
        if (operationList.contains("XG") && toDetailUrlEntity != null && saveUrlEntity != null) {
            MenuEntity operationMenu = buildOperationMenuEntity(pageMenu, 20, "XG", "修改", "修改" + suffix);
            menuList.add(operationMenu);
            menUrlList.add(buildMenUrl(operationMenu, toDetailUrlEntity));
            menUrlList.add(buildMenUrl(operationMenu, saveUrlEntity));
        }
        if (operationList.contains("SC") && deleteUrlEntity != null) {
            MenuEntity operationMenu = buildOperationMenuEntity(pageMenu, 30, "SC", "删除", "删除" + suffix);
            menuList.add(operationMenu);
            menUrlList.add(buildMenUrl(operationMenu, deleteUrlEntity));
        }
        if (operationList.contains("SHTG") && auditUrlEntity != null) {
            MenuEntity operationMenu = buildOperationMenuEntity(pageMenu, 40, "SHTG", "审核通过", "审核" + suffix + "通过");
            menuList.add(operationMenu);
            menUrlList.add(buildMenUrl(operationMenu, auditUrlEntity));
        }
        if (operationList.contains("SHBTG") && auditUrlEntity != null) {
            MenuEntity operationMenu = buildOperationMenuEntity(pageMenu, 50, "SHBTG", "审核不通过", "审核" + suffix + "不通过");
            menuList.add(operationMenu);
            menUrlList.add(buildMenUrl(operationMenu, auditUrlEntity));
        }
        if (operationList.contains("THZXJ") && backUrlEntity != null) {
            MenuEntity operationMenu = buildOperationMenuEntity(pageMenu, 60, "SHBTG", "退回至新建",
                    "退回" + suffix + "至新建状态");
            menuList.add(operationMenu);
            menUrlList.add(buildMenUrl(operationMenu, backUrlEntity));
        }

        menuUrlMapper.insertEntities(menUrlList);
        super.insertEntities(menuList);
    }

    private MenuEntity buildOperationMenuEntity(MenuEntity parentMenu, Integer order, String operation,
            String operationName, String operationDesc) {
        MenuEntity menu = new MenuEntity();
        menu.setF_id(parentMenu.getF_id() + "-" + operation);
        menu.setF_parent_id(parentMenu.getF_id());
        menu.setF_parent_ids(parentMenu.getF_parent_ids() + "/" + menu.getF_parent_id());
        menu.setF_menu_name(operationName);
        menu.setF_menu_remark(operationDesc);
        menu.setF_type(3); // 按钮
        menu.setF_order(order);
        menu.setF_is_show(1);
        return menu;
    }

    private List<UrlEntity> getUrlEntityList(String f_url) {
        MapEntity params = new MapEntity();
        params.put("f_url", f_url);
        params.put("orderBy", "f_url");
        return urlMapper.selectEntityListPage(params);
    }

    private UrlEntity getUrlEntity(List<UrlEntity> urlEntityList, String f_url) {
        for (UrlEntity urlEntity : urlEntityList) {
            if (f_url.equals(urlEntity.getF_url())) {
                return urlEntity;
            }
        }

        return null;
    }

    private MapEntity buildMenUrl(MenuEntity menu, UrlEntity url) {
        MapEntity menUrl = new MapEntity();
        menUrl.put("f_menu_id", menu.getF_id());
        menUrl.put("f_url_id", url.getF_id());
        menUrl.put("f_url", url.getF_url());
        menUrl.put("f_description", url.getF_description());
        menUrl.put("f_methods", url.getF_methods());
        return menUrl;
    }
}
