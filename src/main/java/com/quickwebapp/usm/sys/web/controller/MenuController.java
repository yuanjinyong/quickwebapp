package com.quickwebapp.usm.sys.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.quickwebapp.framework.core.entity.MapEntity;
import com.quickwebapp.framework.core.service.BaseService;
import com.quickwebapp.framework.core.web.controller.BaseController;
import com.quickwebapp.usm.sys.entity.MenuEntity;
import com.quickwebapp.usm.sys.security.SecurityCacheManager;
import com.quickwebapp.usm.sys.service.MenuService;

@RestController
@RequestMapping(value = "/api/sys/menus")
public class MenuController extends BaseController<String, MenuEntity> {
    // private static final String VIEW_PATH = "sys/menu";
    // private static final String TITLE = "菜单管理";
    // private static final String LIST = "菜单列表";
    // private static final String DETAIL = "菜单";

    @Resource
    private MenuService menuService;
    @Resource
    private SecurityCacheManager securityCacheManager;

    @Override
    protected BaseService<String, MenuEntity> getService() {
        return menuService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<MapEntity> list(HttpServletRequest request) {
        MapEntity params = $params(request);
        params.setPageSizeWithMax().setOrderBy("f_parent_ids, f_order");
        List<MenuEntity> menuList = getService().selectEntityListPage(params);
        params.setCurrentPageData(menuList);
        params.setTotalCount(menuList.size());
        return new ResponseEntity<MapEntity>(params, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<MapEntity> create(@RequestBody MenuEntity entity, UriComponentsBuilder ucBuilder) {
        ResponseEntity<MapEntity> result = super.create(entity, ucBuilder);
        // 刷新权限控制的缓存
        securityCacheManager.loadUrlAuthoritiesCache();
        return result;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<MapEntity> get(@PathVariable("id") String primaryKey) {
        return super.get(primaryKey);
        // return new ResponseEntity<MapEntity>(success(menuService.getMenuTree(primaryKey, true)), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<MapEntity> update(@PathVariable("id") String primaryKey, @RequestBody MenuEntity entity) {
        ResponseEntity<MapEntity> result = super.update(primaryKey, entity);
        // 刷新权限控制的缓存
        securityCacheManager.loadUrlAuthoritiesCache();
        return result;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<MapEntity> delete(@PathVariable("id") String primaryKey) {
        // 刷新权限控制的缓存
        securityCacheManager.loadUrlAuthoritiesCache();
        return super.delete(primaryKey);
    }
}
