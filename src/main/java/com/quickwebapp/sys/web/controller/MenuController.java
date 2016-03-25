package com.quickwebapp.sys.web.controller;

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

import com.quickwebapp.core.service.BaseService;
import com.quickwebapp.core.web.controller.BaseController;
import com.quickwebapp.sys.entity.MenuEntity;
import com.quickwebapp.sys.service.MenuService;

@RestController
@RequestMapping(value = "/api/sys/menus")
public class MenuController extends BaseController<String, MenuEntity> {
    // private static final String VIEW_PATH = "sys/menu";
    // private static final String TITLE = "菜单管理";
    // private static final String LIST = "菜单列表";
    // private static final String DETAIL = "菜单";

    @Resource
    private MenuService menuService;

    @Override
    protected BaseService<String, MenuEntity> getService() {
        return menuService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<MenuEntity>> list(HttpServletRequest request) {
        // return super.list(params);
        return new ResponseEntity<List<MenuEntity>>(menuService.getMenuList($params(request)), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody MenuEntity entity, UriComponentsBuilder ucBuilder) {
        return super.create(entity, ucBuilder);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<MenuEntity> get(@PathVariable("id") String primaryKey) {
        // return super.get(primaryKey);
        return new ResponseEntity<MenuEntity>(menuService.getMenuTree(primaryKey, true), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<MenuEntity> update(@PathVariable("id") String primaryKey, @RequestBody MenuEntity entity) {
        return super.update(primaryKey, entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<MenuEntity> delete(@PathVariable("id") String primaryKey) {
        return super.delete(primaryKey);
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<MenuEntity> delete(HttpServletRequest request) {
        return super.delete($params(request));
    }
}
