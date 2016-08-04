package com.quickwebapp.usm.sys.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.quickwebapp.usm.sys.entity.TenantCustomerEntity;
import com.quickwebapp.usm.sys.service.TenantService;

@RestController
@RequestMapping(value = "/api/sys/tenants")
public class TenantController extends BaseController<Integer, TenantCustomerEntity> {
    @Resource
    private TenantService tenantService;

    @Override
    protected BaseService<Integer, TenantCustomerEntity> getService() {
        return tenantService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<MapEntity> list(HttpServletRequest request) {
        return super.page($params(request));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<MapEntity> create(@RequestBody TenantCustomerEntity entity, UriComponentsBuilder ucBuilder) {
        ResponseEntity<MapEntity> result = super.create(entity, ucBuilder);
        return result;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<MapEntity> get(@PathVariable("id") Integer primaryKey) {
        return super.get(primaryKey);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<MapEntity> update(@PathVariable("id") Integer primaryKey,
            @RequestBody TenantCustomerEntity entity) {
        ResponseEntity<MapEntity> result = super.update(primaryKey, entity);
        return result;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<MapEntity> delete(@PathVariable("id") Integer primaryKey) {
        return super.delete(primaryKey);
    }
}
