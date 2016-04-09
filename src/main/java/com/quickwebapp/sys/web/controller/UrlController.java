package com.quickwebapp.sys.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.quickwebapp.core.service.BaseService;
import com.quickwebapp.core.web.controller.BaseController;
import com.quickwebapp.sys.entity.UrlEntity;
import com.quickwebapp.sys.service.UrlService;

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
    public ResponseEntity<List<UrlEntity>> list(HttpServletRequest request) {
        return super.list($params(request));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody UrlEntity entity, UriComponentsBuilder ucBuilder) {
        return super.create(entity, ucBuilder);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<UrlEntity> get(@PathVariable("id") String primaryKey) {
        return super.get(primaryKey);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<UrlEntity> update(@PathVariable("id") String primaryKey, @RequestBody UrlEntity entity) {
        return super.update(primaryKey, entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<UrlEntity> delete(@PathVariable("id") String primaryKey) {
        return super.delete(primaryKey);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<UrlEntity> delete(HttpServletRequest request) {
        return super.deleteBatch($params(request));
    }
}
