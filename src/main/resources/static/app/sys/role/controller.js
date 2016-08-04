(function(angular) {
    var RoleController = function($scope, roleService) {
        $qw.dev && console.info('RoleController');

        $scope.feature = {
            id : 'role',
            name : '角色',
            path : 'app/sys/role',
            service : roleService
        }

        $scope.roleGridOptions = $qw.grid.buildGridOptionsFn($scope, {
            onLoadedSuccess : function(result) {
                this.toolbar.enableItems([ 'add', 'refresh' ]);
            },
            onRowSelectionChanged : function(row) {
                if (!row.isSelected) {
                    this.toolbar.enableItems([ 'add', 'refresh' ]);
                } else {
                    this.toolbar.enableAllItems();
                }
            },
            columnDefs : [ $qw.buildLinkColumnFn({
                field : 'f_role_code',
                displayName : '角色编码',
                cellClass : 'text-left',
                pinnedLeft : true, // 靠左固定列
                width : 100
            }), $qw.buildColumnFn({
                field : 'f_role_name',
                displayName : '角色名称',
                cellClass : 'text-left',
                width : 150
            }), $qw.buildColumnFn({
                field : 'f_remark',
                displayName : '角色描述',
                cellClass : 'text-left',
                enableFiltering : false,
            }) ]
        });
        if ($qw.currentUser.user.superAdmin) {
            $scope.roleGridOptions.columnDefs.push($qw.buildColumnFn({
                field : 'f_tenant_cid',
                displayName : 'CID',
                width : 60
            }));
            $scope.roleGridOptions.columnDefs.push($qw.buildColumnFn({
                field : 'f_tenant_bid',
                displayName : 'BID',
                width : 60
            }));
        }

        var addGroupItems = [];
        if ($qw.hasAuthority('XTGL-JSGL-ZJ')) {
            addGroupItems.push($qw.buildAddItemFn({
                click : function(item, gridOptions) {
                    gridOptions.toFormFn(item, gridOptions.getSelectedRowFn());
                }
            }));
        }
        if ($qw.hasAuthority('XTGL-JSGL-XG')) {
            addGroupItems.push($qw.buildEditItemFn({
                click : function(item, gridOptions) {
                    gridOptions.toFormFn(item, gridOptions.getSelectedRowFn());
                }
            }));
        }
        if ($qw.hasAuthority('XTGL-JSGL-SC')) {
            addGroupItems.push($qw.buildRemoveItemFn({
                click : function(item, gridOptions) {
                    gridOptions.toRemoveFn(item, gridOptions);
                }
            }));
        }
        $scope.roleGridOptions.toolbar = $qw.buildButtonBarFn('gridToolbar', [
                $qw.buildGroupFn('addGroup', addGroupItems),
                $qw.buildGroupFn('refreshGroup', [ $qw.buildRefreshItemFn({
                    click : function(item, gridOptions) {
                        gridOptions.loadFn();
                    }
                }) ]) ]);

        // 打开时首次加载表格数据
        $scope.roleGridOptions.loadFn();
    };

    RoleController.$inject = [ '$scope', 'RoleService' ];
    angular.module("app.controllers").controller("RoleController", RoleController);

    var RoleFormController = function($scope, roleService, roleMenuService) {
        $qw.dev && console.log('RoleFormController');

        $scope.menuTreeOptions = {
            height : '400px',
            textField : 'f_menu_name',
            valueField : 'f_id',
            //checkable : true,
            //autoCheckParent : true,
            //autoCheckChildren : true,
            //nodeTemplateUrl : 'template/tree/node.html',
            render : function($treeOptions, $node) {
                
            },
            nodeCheckedChanged : function($treeOptions, $node) {
                console.log('node checked', $treeOptions, $node, $treeOptions.getCheckedNodes(), $treeOptions
                        .getCheckedNodeValues());
            },
            nodeClicked : function($treeOptions, $node) {
                $scope.selectedNode = $node;
                console.log('node clicked', $treeOptions, $node);
            },
            nodeExpandedChanged : function($treeOptions, $node) {
                console.log('node expanded', $treeOptions, $node);
            },
        };

        roleMenuService.query({
            roleId : $scope.entity.f_id || 0
        }, function(result, putResponseHeaders) {
            $scope.menuTreeOptions.data = result.currentPageData;
        });

        $scope.formOptions.footbar.getGroup('submitGroup').getItem('submit').click = function(footbarItem, formOptions,
                entity) {
            entity.menuList = [];
            var checkedNodeValues = $scope.menuTreeOptions.getCheckedNodeValues();
            angular.forEach(checkedNodeValues, function(nodeValue, index, nodeValues) {
                entity.menuList.push({
                    f_role_id : entity.f_id,
                    f_menu_id : nodeValue
                });
            });

            formOptions.submitFn(footbarItem, formOptions, entity);
        };
    };

    RoleFormController.$inject = [ '$scope', 'RoleService', 'RoleMenuService' ];
    angular.module("app.controllers").controller("RoleFormController", RoleFormController);
}(angular));