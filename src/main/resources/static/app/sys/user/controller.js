(function(angular) {
    var UserController = function($scope, userService) {
        $qw.dev && console.info('UserController');

        $scope.feature = {
            id : 'role',
            name : '用户',
            path : 'app/sys/user',
            service : userService
        }

        $scope.userGridOptions = $qw.grid.buildGridOptionsFn($scope, {
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
                field : 'f_account',
                displayName : '账号',
                cellClass : 'text-left',
                pinnedLeft : true, // 靠左固定列
                width : 100
            }), $qw.buildColumnFn({
                field : 'f_name',
                displayName : '姓名',
                cellClass : 'text-left',
                width : 100
            }), $qw.buildDictColumnFn({
                field : 'f_status',
                displayName : '状态',
                dictCode : 'UserStatus',
                width : 80
            }), $qw.buildDatetimeColumnFn({
                field : 'f_create_time',
                displayName : '创建时间',
                enableFiltering : false,
            }), $qw.buildDatetimeColumnFn({
                field : 'f_last_login_time',
                displayName : '最后登录时间',
                visible : false,
            }), $qw.buildDatetimeColumnFn({
                field : 'f_locked_time',
                displayName : '锁定时间',
                visible : false,
            }), $qw.buildDatetimeColumnFn({
                field : 'f_unregister_time',
                displayName : '注销时间',
                visible : false,
            }), $qw.buildColumnFn({
                field : 'f_remark',
                displayName : '备注',
                cellClass : 'text-left',
                enableFiltering : false,
            }) ]
        });
        if ($qw.currentUser.user.superAdmin) {
            $scope.userGridOptions.columnDefs.push($qw.buildColumnFn({
                field : 'f_tenant_cid',
                displayName : 'CID',
                width : 60
            }));
            $scope.userGridOptions.columnDefs.push($qw.buildColumnFn({
                field : 'f_tenant_bid',
                displayName : 'BID',
                width : 60
            }));
        }

        var addGroupItems = [];
        if ($qw.hasAuthority('XTGL-YHGL-ZJ')) {
            addGroupItems.push($qw.buildAddItemFn({
                click : function(item, gridOptions) {
                    gridOptions.toFormFn(item, gridOptions.getSelectedRowFn());
                }
            }));
        }
        if ($qw.hasAuthority('XTGL-YHGL-XG')) {
            addGroupItems.push($qw.buildEditItemFn({
                click : function(item, gridOptions) {
                    gridOptions.toFormFn(item, gridOptions.getSelectedRowFn());
                }
            }));
        }
        if ($qw.hasAuthority('XTGL-YHGL-SC')) {
            addGroupItems.push($qw.buildRemoveItemFn({
                click : function(item, gridOptions) {
                    gridOptions.toRemoveFn(item, gridOptions);
                }
            }));
        }
        $scope.userGridOptions.toolbar = $qw.buildButtonBarFn('gridToolbar', [
                $qw.buildGroupFn('addGroup', addGroupItems),
                $qw.buildGroupFn('refreshGroup', [ $qw.buildRefreshItemFn({
                    click : function(item, gridOptions) {
                        gridOptions.loadFn();
                    }
                }) ]) ]);

        // 在指定组前插入一组新的按钮
        $scope.userGridOptions.toolbar.addGroup($qw.buildGroupFn('resetPasswordGroup', [ $qw.buildGroupItemFn({
            id : "resetPassword",
            ico : "wrench",
            css : 'warning',
            text : "重置密码",
            click : function(item, gridOptions) {
                alert(item.text);
            }
        }), $qw.buildGroupItemFn({
            id : "unlock",
            ico : "briefcase",
            //css : 'success',
            text : "解锁",
            click : function(item, gridOptions) {
                alert(item.text);
            }
        }), $qw.buildGroupItemFn({
            id : "unregister",
            ico : "trash",
            css : 'danger',
            text : "注销",
            click : function(item, gridOptions) {
                alert(item.text);
            }
        }) ]), 0);

        // 打开时首次加载表格数据
        $scope.userGridOptions.loadFn();
    };

    UserController.$inject = [ '$scope', 'UserService' ];
    angular.module("app.controllers").controller("UserController", UserController);

    var UserFormController = function($scope, userService, userRoleService, userMenuService) {
        $qw.dev && console.log('UserFormController');

        $scope.menuTreeOptions = {
            height : '400px',
            textField : 'f_menu_name',
            valueField : 'f_id',
            render : function($treeOptions, $node) {

            },
            nodeCheckedChanged : function($treeOptions, $node) {
                $qw.dev
                        && console.log('node checked', $treeOptions, $node, $treeOptions.getCheckedNodes(),
                                $treeOptions.getCheckedNodeValues());
            },
            nodeClicked : function($treeOptions, $node) {
                $scope.selectedNode = $node;
                $qw.dev && console.log('node clicked', $treeOptions, $node);
            },
            nodeExpandedChanged : function($treeOptions, $node) {
                $qw.dev && console.log('node expanded', $treeOptions, $node);
            },
        };

        if ($scope.entity.f_id) {
            userService.get({
                id : $scope.entity.f_id
            }, function(result, putResponseHeaders) {
                $scope.entity.userExtEntity = result.data.entity.userExtEntity;
            });
        }

        userRoleService.query({
            userId : $scope.entity.f_id || 0
        }, function(result, putResponseHeaders) {
            angular.forEach(result.currentPageData, function(row, index, data) {
                row.checked = row.checked === 1;
            });
            $scope.roleGridOptions = {
                data : result.currentPageData
            };
        });

        userMenuService.query({
            userId : $scope.entity.f_id || 0
        }, function(result, putResponseHeaders) {
            $scope.menuTreeOptions.data = result.currentPageData;
        });

        $scope.formOptions.footbar.getGroup('submitGroup').getItem('submit').click = function(footbarItem, formOptions,
                entity) {
            entity.roleList = [];
            angular.forEach($scope.roleGridOptions.data, function(role, index, data) {
                if (role.checked) {
                    entity.roleList.push({
                        f_user_id : entity.f_id,
                        f_role_id : role.f_id
                    });
                }
            });

            entity.menuList = [];
            var checkedNodeValues = $scope.menuTreeOptions.getCheckedNodeValues();
            angular.forEach(checkedNodeValues, function(nodeValue, index, nodeValues) {
                entity.menuList.push({
                    f_user_id : entity.f_id,
                    f_menu_id : nodeValue
                });
            });

            formOptions.submitFn(footbarItem, formOptions, entity);
        };
    };

    UserFormController.$inject = [ '$scope', 'UserService', 'UserRoleService', 'UserMenuService' ];
    angular.module("app.controllers").controller("UserFormController", UserFormController);
}(angular));