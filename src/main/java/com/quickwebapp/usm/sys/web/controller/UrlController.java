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
import com.quickwebapp.usm.sys.entity.UrlEntity;
import com.quickwebapp.usm.sys.service.UrlService;

@RestController
@RequestMapping(value = "/api/sys/urls")
public class UrlController extends BaseController<String, UrlEntity> {
    @Resource
    private UrlService urlService;

    @Override
    protected BaseService<String, UrlEntity> getService() {
        return urlService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<MapEntity> list(HttpServletRequest request) {
        return super.page($params(request));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<MapEntity> create(@RequestBody UrlEntity entity, UriComponentsBuilder ucBuilder) {
        return super.create(entity, ucBuilder);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<MapEntity> get(@PathVariable("id") String primaryKey) {
        return super.get(primaryKey);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<MapEntity> update(@PathVariable("id") String primaryKey, @RequestBody UrlEntity entity) {
        return super.update(primaryKey, entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<MapEntity> delete(@PathVariable("id") String primaryKey) {
        return super.delete(primaryKey);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<MapEntity> delete(HttpServletRequest request) {
        return super.deleteBatch($params(request));
    }
}
