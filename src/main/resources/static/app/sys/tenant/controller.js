(function(angular) {
    var TenantController = function($scope, tenantService) {
        $qw.dev && console.info('TenantController');

        $scope.feature = {
            id : 'tenant',
            name : '租户',
            path : 'app/sys/tenant',
            service : tenantService
        }

        $scope.tenantGridOptions = $qw.grid.buildGridOptionsFn($scope, {
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
                field : 'f_id',
                displayName : '租户客户ID',
                cellClass : 'text-left',
                pinnedLeft : true, // 靠左固定列
                width : 100
            }), $qw.buildColumnFn({
                field : 'f_name',
                displayName : '租户客户名称',
                cellClass : 'text-left',
                width : 150
            }), $qw.buildColumnFn({
                field : 'f_remark',
                displayName : '租户客户描述',
                cellClass : 'text-left',
            }) ]
        });

        var addGroupItems = [];
        if ($qw.hasAuthority('XTGL-ZHGL-ZJ')) {
            addGroupItems.push($qw.buildAddItemFn({
                click : function(item, gridOptions) {
                    gridOptions.toFormFn(item, gridOptions.getSelectedRowFn());
                }
            }));
        }
        if ($qw.hasAuthority('XTGL-ZHGL-XG')) {
            addGroupItems.push($qw.buildEditItemFn({
                click : function(item, gridOptions) {
                    gridOptions.toFormFn(item, gridOptions.getSelectedRowFn());
                }
            }));
        }
        if ($qw.hasAuthority('XTGL-ZHGL-SC')) {
            addGroupItems.push($qw.buildRemoveItemFn({
                click : function(item, gridOptions) {
                    gridOptions.toRemoveFn(item, gridOptions);
                }
            }));
        }
        $scope.tenantGridOptions.toolbar = $qw.buildButtonBarFn('gridToolbar', [
                $qw.buildGroupFn('addGroup', addGroupItems),
                $qw.buildGroupFn('refreshGroup', [ $qw.buildRefreshItemFn({
                    click : function(item, gridOptions) {
                        gridOptions.loadFn();
                    }
                }) ]) ]);

        // 打开时首次加载表格数据
        $scope.tenantGridOptions.loadFn();
    };

    TenantController.$inject = [ '$scope', 'TenantService' ];
    angular.module("app.controllers").controller("TenantController", TenantController);

    var TenantFormController = function($scope, tenantService) {
        $qw.dev && console.log('TenantFormController');

        $scope.tenantBusinessGridOptions = $qw.grid.buildGridOptionsFn($scope, {
            height : 5,
            feature : {
                id : 'tenantBusiness',
                name : '租户运营实体',
                path : 'app/sys/tenant',
                service : tenantService
            },
            showRowNumber : true, // 自定义属性，默认显示行号
            multiSelect : true, // 启用多选
            enableFullRowSelection : false, // 启用单击行选中功能
            enableGridMenu : false, // 启用表格右上角菜单
            enableFiltering : false, // 启用过滤器
            enableSorting : false,
            useExternalSorting : false, // 启用后台排序
            useExternalPagination : false, // 启用后台分页
            toFormFn : function(toolbarItem, entity, size) {
                $scope.tenantBusinessGridOptions.data.push({
                    f_tenant_cid : $scope.entity.f_id
                });
            },
            toRemoveFn : function(toolbarItem, gridOptions) {
                var selectedRows = gridOptions.gridApi.selection.getSelectedRows();
                if (selectedRows && selectedRows.length > 0) {
                    angular.forEach(selectedRows, function(row) {
                        for (var i = 0; i < gridOptions.data.length; i++) {
                            if (gridOptions.data[i] == row) {
                                gridOptions.data.splice(i, 1);
                            }
                        }
                    });
                }
            },
            columnDefs : [ $qw.buildColumnFn({
                field : 'f_id',
                displayName : '运营实体ID',
                cellClass : 'text-left',
                width : 100
            }), $qw.buildColumnFn({
                field : 'f_name',
                displayName : '运营实体名称',
                width : 120
            }), $qw.buildColumnFn({
                field : 'f_remark',
                displayName : '运营实体描述',
                cellClass : 'text-left'
            }) ]
        });
        $scope.tenantBusinessGridOptions.toolbar.showItems([ 'add', 'remove' ]);

        if ($scope.entity.f_id) {
            tenantService.get({
                id : $scope.entity.f_id
            }, function(result, putResponseHeaders) {
                $scope.tenantBusinessGridOptions.data = result.data.entity.tenantBusinessList;
            });
        }

        $scope.formOptions.footbar.getGroup('submitGroup').getItem('submit').click = function(footbarItem, formOptions,
                entity) {
            entity.tenantBusinessList = [];
            for (var index = 1; index < $scope.tenantBusinessGridOptions.data.length; index++) {
                var rowEntity = $scope.tenantBusinessGridOptions.data[index];
                if (!rowEntity.f_id || !rowEntity.f_name) {
                    $qw.dialog.buildErrorDialogFn({}, '第' + (index + 1) + "行的运营实体ID和运营实体名称不能为空！").openFn();
                    return;
                } else {
                    entity.tenantBusinessList.push(rowEntity);
                }
            }

            formOptions.submitFn(footbarItem, formOptions, entity);
        };
    };

    TenantFormController.$inject = [ '$scope', 'TenantService' ];
    angular.module("app.controllers").controller("TenantFormController", TenantFormController);
}(angular));