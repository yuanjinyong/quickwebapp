package com.quickwebapp.usm.sys.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
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
import com.quickwebapp.usm.sys.entity.DictGroupEntity;
import com.quickwebapp.usm.sys.entity.DictItemEntity;
import com.quickwebapp.usm.sys.entity.UserEntity;
import com.quickwebapp.usm.sys.security.SecurityUtil;
import com.quickwebapp.usm.sys.service.DictGroupService;

@RestController
@RequestMapping(value = "/api/sys/dict/groups")
public class DictGroupController extends BaseController<Integer, DictGroupEntity> {
    @Resource
    private DictGroupService dictGroupService;

    @Override
    protected BaseService<Integer, DictGroupEntity> getService() {
        return dictGroupService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<MapEntity> list(HttpServletRequest request) {
        return super.page($params(request));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<MapEntity> create(@RequestBody DictGroupEntity entity, UriComponentsBuilder ucBuilder) {
        ResponseEntity<MapEntity> result = super.create(entity, ucBuilder);
        // securityCacheManager.loadSecurityUserCache();
        return result;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<MapEntity> get(@PathVariable("id") Integer primaryKey) {
        return super.get(primaryKey);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<MapEntity> update(@PathVariable("id") Integer primaryKey,
            @RequestBody DictGroupEntity entity) {
        ResponseEntity<MapEntity> result = super.update(primaryKey, entity);
        // securityCacheManager.loadSecurityUserCache();
        return result;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<MapEntity> delete(@PathVariable("id") Integer primaryKey) {
        return super.delete(primaryKey);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public ResponseEntity<MapEntity> listDict(HttpServletRequest request) {
        MapEntity params = $params(request);
        List<DictItemEntity> itemList = dictGroupService.selectDictItemEntityListPage(params);

        MapEntity dictData = new MapEntity();
        for (DictItemEntity item : itemList) {
            @SuppressWarnings("unchecked")
            List<MapEntity> dictGroup = (List<MapEntity>) dictData.get(item.getF_code());
            if (dictGroup == null) {
                dictGroup = new ArrayList<MapEntity>();
                dictData.put(item.getF_code(), dictGroup);
            }

            MapEntity dictItem = new MapEntity();
            dictGroup.add(dictItem);
            dictItem.put("value", item.getF_item_code());
            dictItem.put("label", item.getF_item_text());
        }

        return new ResponseEntity<MapEntity>(dictData, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/items", method = RequestMethod.GET)
    public ResponseEntity<MapEntity> listItems(HttpServletRequest request) {
        MapEntity params = $params(request);
        params.setCurrentPageData(dictGroupService.selectDictItemEntityListPage(params));

        return new ResponseEntity<MapEntity>(params, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/items", method = RequestMethod.POST)
    public ResponseEntity<MapEntity> createItem(@RequestBody DictItemEntity entity, UriComponentsBuilder ucBuilder) {
        UserEntity user = SecurityUtil.getCurrentUser();
        entity.setF_tenant_cid(user.getF_tenant_cid());
        entity.setF_tenant_bid(user.getF_tenant_bid());
        dictGroupService.insertDictItemEntity(entity);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/{id}").buildAndExpand(entity.getF_id()).toUri());
        return new ResponseEntity<MapEntity>(success(entity), headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}/items/{itemId}", method = RequestMethod.GET)
    public ResponseEntity<MapEntity> getItem(@PathVariable("itemId") Integer primaryKey) {
        return new ResponseEntity<MapEntity>(success(dictGroupService.selectDictItemEntity(primaryKey)), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/items/{itemId}", method = RequestMethod.PUT)
    public ResponseEntity<MapEntity> updateItem(@PathVariable("itemId") Integer primaryKey,
            @RequestBody DictItemEntity entity) {
        dictGroupService.updateDictItemEntity(entity);

        return new ResponseEntity<MapEntity>(success(entity), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/items/{itemId}", method = RequestMethod.DELETE)
    public ResponseEntity<MapEntity> deleteItem(@PathVariable("itemId") Integer primaryKey) {
        return super.delete(primaryKey);
    }
}
