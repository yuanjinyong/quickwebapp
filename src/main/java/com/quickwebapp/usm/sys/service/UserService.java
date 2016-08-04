package com.quickwebapp.usm.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quickwebapp.framework.core.entity.MapEntity;
import com.quickwebapp.framework.core.exception.BusinessException;
import com.quickwebapp.framework.core.mapper.BaseMapper;
import com.quickwebapp.framework.core.service.BaseService;
import com.quickwebapp.framework.core.utils.HelpUtil;
import com.quickwebapp.framework.core.utils.TreeUtil;
import com.quickwebapp.usm.sys.entity.MenuEntity;
import com.quickwebapp.usm.sys.entity.UserEntity;
import com.quickwebapp.usm.sys.mapper.MenuMapper;
import com.quickwebapp.usm.sys.mapper.RoleMenuMapper;
import com.quickwebapp.usm.sys.mapper.UserExtMapper;
import com.quickwebapp.usm.sys.mapper.UserMapper;
import com.quickwebapp.usm.sys.mapper.UserMenuMapper;
import com.quickwebapp.usm.sys.mapper.UserRoleMapper;

@Service
@Transactional
public class UserService extends BaseService<Integer, UserEntity> {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserExtMapper userExtMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserMenuMapper userMenuMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected BaseMapper<Integer, UserEntity> getMapper() {
        return userMapper;
    }

    @Override
    public UserEntity selectEntity(Integer primaryKey) {
        UserEntity entity = super.selectEntity(primaryKey);
        entity.setUserExtEntity(userExtMapper.selectEntity(primaryKey));
        entity.setRoleList(selectRoleList(entity.getF_id()));
        entity.setMenuList(selectMenuList(entity.getF_id()));
        return entity;
    }

    private List<MapEntity> selectRoleList(Integer f_user_id) {
        MapEntity mapEntity = new MapEntity();
        mapEntity.put("f_user_id", f_user_id);
        return userRoleMapper.selectEntityListPage(mapEntity);
    }

    private List<MapEntity> selectMenuList(Integer f_user_id) {
        MapEntity mapEntity = new MapEntity();
        mapEntity.put("f_user_id", f_user_id);
        return userMenuMapper.selectEntityListPage(mapEntity);
    }

    @Override
    public void insertEntity(UserEntity entity) {
        entity.setF_create_time(HelpUtil.getNowTime());
        entity.setF_password(passwordEncoder.encode("pkpm")); // TODO
        entity.setF_status(1);

        super.insertEntity(entity);

        if (null != entity.getUserExtEntity()) {
            entity.getUserExtEntity().setF_id(entity.getF_id());
            userExtMapper.insertEntity(entity.getUserExtEntity());
        }

        insertRoleList(entity);
        insertMenuList(entity);
    }

    private void insertRoleList(UserEntity user) {
        List<MapEntity> roleList = user.getRoleList();
        if (!HelpUtil.isEmptyCollection(roleList)) {
            for (MapEntity map : roleList) {
                map.put("f_user_id", user.getF_id());
            }

            userRoleMapper.insertEntities(roleList);
        }
    }

    private void insertMenuList(UserEntity user) {
        List<MapEntity> menuList = user.getMenuList();
        if (!HelpUtil.isEmptyCollection(menuList)) {
            for (MapEntity map : menuList) {
                map.put("f_user_id", user.getF_id());
            }

            userMenuMapper.insertEntities(menuList);
        }
    }

    public void updateEntity(UserEntity entity) {
        if (null != entity.getUserExtEntity()) {
            entity.getUserExtEntity().setF_id(entity.getF_id());

            if (userExtMapper.updateEntity(entity.getUserExtEntity()) == 0) {
                userExtMapper.insertEntity(entity.getUserExtEntity());
            }
        }

        deleteRoleList(entity.getF_id());
        deleteMenuList(entity.getF_id());

        insertRoleList(entity);
        insertMenuList(entity);

        super.updateEntity(entity);
    }

    @Override
    public void deleteEntity(Integer primaryKey) {
        UserEntity entity = super.selectEntity(primaryKey);
        if (entity.isSuperAdmin() || entity.isAdmin()) {
            throw new BusinessException("系统管理员用户不能删除！");
        }

        userExtMapper.deleteEntity(primaryKey);

        deleteRoleList(primaryKey);
        deleteMenuList(primaryKey);

        // 最后在删除自己
        super.deleteEntity(primaryKey);
    }

    private void deleteRoleList(Integer f_user_id) {
        MapEntity mapEntity = new MapEntity();
        mapEntity.put("f_user_id", f_user_id);
        userRoleMapper.deleteEntities(mapEntity);
    }

    private void deleteMenuList(Integer f_user_id) {
        MapEntity mapEntity = new MapEntity();
        mapEntity.put("f_user_id", f_user_id);
        userMenuMapper.deleteEntities(mapEntity);
    }

    /**
     * 查询用户（操作员）可赋&已赋的角色列表
     * 
     * @param params
     * @return
     */
    public List<MapEntity> listUserRole(MapEntity params) {
        return userRoleMapper.selectRoleListPage(params);
    }

    /**
     * 查询用户（操作员）可赋&已赋的菜单（权限）列表
     * 
     * @param params
     * @return
     */
    public List<MenuEntity> listUserMenu(MapEntity params) {
        return userMenuMapper.selectMenuListPage(params);
    }

    /**
     * 获取所有的可用菜单
     * 
     * @return
     */
    public MenuEntity getMenuTree() {
        MenuEntity rootMenu = menuMapper.selectEntity(MenuEntity.MENU_ROOT);

        MapEntity params = new MapEntity();
        params.put("f_parent_ids",
                (HelpUtil.isEmptyString(rootMenu.getF_parent_ids()) ? "" : rootMenu.getF_parent_ids() + "/")
                        + rootMenu.getF_id());
        params.put("f_is_show", 1);
        params.setPageSizeWithMax().setOrderBy("f_parent_ids, f_order");

        rootMenu.setChildren(TreeUtil.listToTree(menuMapper.selectEntityListPage(params)));

        return rootMenu;
    }

    /**
     * 获取指定角色的可用菜单
     * 
     * @param f_role_code
     * @return
     */
    public MenuEntity getMenuTree(String f_role_code) {
        MapEntity params = new MapEntity();
        params.put("f_role_code", f_role_code);
        params.put("f_is_show", 1);
        params.setPageSizeWithMax().setOrderBy("f_parent_ids, f_order");

        List<MenuEntity> nodeTree = TreeUtil.listToTree(roleMenuMapper.selectRoleMenuListPage(params));

        return HelpUtil.isEmptyCollection(nodeTree) ? null : nodeTree.get(0);
    }

    /**
     * 获取指定用户的可用菜单
     * 
     * @param f_user_id
     * @return
     */
    public MenuEntity getMenuTree(Integer f_user_id) {
        MapEntity params = new MapEntity();
        params.put("f_user_id", f_user_id);
        params.put("f_is_show", 1);
        params.setPageSizeWithMax().setOrderBy("f_parent_ids, f_order");

        List<MenuEntity> nodeTree = TreeUtil.listToTree(userMenuMapper.selectUserAndRoleMenuListPage(params));

        return HelpUtil.isEmptyCollection(nodeTree) ? null : nodeTree.get(0);
    }
}
