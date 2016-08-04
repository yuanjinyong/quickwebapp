package com.quickwebapp.usm.sys.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.quickwebapp.framework.core.utils.TreeUtil;
import com.quickwebapp.framework.core.web.controller.BaseController;
import com.quickwebapp.usm.sys.entity.RoleEntity;
import com.quickwebapp.usm.sys.entity.UserEntity;
import com.quickwebapp.usm.sys.security.OpenIdAuthenticationProvider;
import com.quickwebapp.usm.sys.security.SecurityCacheManager;
import com.quickwebapp.usm.sys.security.SecurityUtil;
import com.quickwebapp.usm.sys.service.RoleService;
import com.quickwebapp.usm.sys.service.UserService;

@RestController
@RequestMapping(value = "/api/sys/roles")
public class RoleController extends BaseController<Integer, RoleEntity> {
    @Resource
    private RoleService roleService;
    @Resource
    private UserService userService;
    @Autowired
    private OpenIdAuthenticationProvider openIdAuthenticationProvider;
    @Resource
    private SecurityCacheManager securityCacheManager;

    @Override
    protected BaseService<Integer, RoleEntity> getService() {
        return roleService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<MapEntity> list(HttpServletRequest request) {
        return super.page($params(request));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<MapEntity> create(@RequestBody RoleEntity entity, UriComponentsBuilder ucBuilder) {
        UserEntity user = SecurityUtil.getCurrentUser();
        entity.setF_tenant_cid(user.getF_tenant_cid());
        entity.setF_tenant_bid(user.getF_tenant_bid());

        ResponseEntity<MapEntity> result = super.create(entity, ucBuilder);
        securityCacheManager.loadSecurityUserCache();
        return result;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<MapEntity> get(@PathVariable("id") Integer primaryKey) {
        return super.get(primaryKey);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<MapEntity> update(@PathVariable("id") Integer primaryKey, @RequestBody RoleEntity entity) {
        ResponseEntity<MapEntity> result = super.update(primaryKey, entity);

        if (RoleEntity.ROLE_WEIXIN.equals(entity.getF_role_code())) {
            openIdAuthenticationProvider.loadWeinxinAuthorityCache();
        }

        securityCacheManager.loadSecurityUserCache();
        return result;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<MapEntity> delete(@PathVariable("id") Integer primaryKey) {
        return super.delete(primaryKey);
    }

    // @RequestMapping(method = RequestMethod.DELETE)
    // public ResponseEntity<MapEntity> delete(HttpServletRequest request) {
    // return super.deleteBatch($params(request));
    // }

    @RequestMapping(value = "/{id}/menus", method = RequestMethod.GET)
    public ResponseEntity<MapEntity> listRoleMenu(@PathVariable("id") Integer primaryKey) {
        MapEntity params = new MapEntity();
        UserEntity user = SecurityUtil.getCurrentUser();
        if (!user.isSuperAdmin()) {
            UserEntity u = userService.selectEntity(user.getF_id());
            params.put("cur_user_id", u.getF_id());
            StringBuffer sb = new StringBuffer();
            for (MapEntity roleMap : u.getRoleList()) {
                sb.append(',').append(roleMap.getInteger("f_role_id", -1));
            }
            if (sb.length() > 0) {
                params.put("cur_role_ids", sb.substring(1));
            }
        }
        params.put("f_role_id", primaryKey);
        params.put("f_is_show", 1);
        params.setPageSizeWithMax().setOrderBy("f_parent_ids, f_order");
        params.setCurrentPageData(TreeUtil.listToTree(roleService.listRoleMenu(params)));
        return new ResponseEntity<MapEntity>(params, HttpStatus.OK);
    }
}
