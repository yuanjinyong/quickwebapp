(function(angular) {
    var MenuController = function($scope, menuService) {
        $qw.dev && console.info('MenuController');

        $scope.feature = {
            id : 'menu',
            name : '菜单',
            path : 'app/sys/menu',
            service : menuService
        }

        $scope.menuGridOptions = $qw.grid
                .buildGridOptionsFn(
                        $scope,
                        {
                            treeIndent : 14,
                            showTreeExpandNoChildren : false,
                            onLoadedSuccess : function(result) {
                                var gridOptions = this;
                                $qw.timeout(function() {
                                    // gridOptions.gridApi.treeBase.expandAllRows();
                                    var rows = gridOptions.gridApi.grid.rows;
                                    angular.forEach(rows, function(row, index, rows) {
                                        if (row.entity.f_type < 2) {
                                            gridOptions.gridApi.treeBase.toggleRowTreeState(row);
                                        }
                                    });
                                }, 50);
                                gridOptions.toolbar.enableItems([ 'add', 'refresh' ]);
                            },
                            onRowSelectionChanged : function(row) {
                                if (!row.isSelected) {
                                    this.toolbar.enableItems([ 'add', 'refresh' ]);
                                } else {
                                    this.toolbar.enableAllItems();
                                }
                            },
                            columnDefs : [
                                    $qw
                                            .buildColumnFn({
                                                field : 'f_menu_name',
                                                displayName : '菜单名称',
                                                cellClass : 'text-left',
                                                cellTemplate : '<div class="ui-grid-cell-contents" style="padding-left:{{row.entity.$$treeLevel*14+5}}px"><span class="glyphicon glyphicon-{{row.entity.f_icon || (row.entity.f_type < 2 ? \'menu-hamburger\' : \'file\')}}"><span>{{grid.getCellValue(row, col)}}</div>',
                                                pinnedLeft : true,// 靠左固定列
                                                width : 300
                                            }), $qw.buildColumnFn({
                                        field : 'f_order',
                                        displayName : '排序',
                                        enableFiltering : false,
                                        width : 55
                                    }), $qw.buildLinkColumnFn({
                                        field : 'f_id',
                                        displayName : '菜单编码',
                                        cellClass : 'text-left',
                                        width : 200
                                    }), $qw.buildColumnFn({
                                        field : 'f_menu_remark',
                                        displayName : '菜单描述',
                                        cellClass : 'text-left',
                                        enableFiltering : false,
                                        width : 300
                                    }), $qw.buildDictColumnFn({
                                        field : 'f_type',
                                        displayName : '类型',
                                        dictCode : 'MenuType',
                                        enableColumnResizing : false,
                                        enableSorting : false,
                                        width : 85
                                    }), $qw.buildDictColumnFn({
                                        field : 'f_is_show',
                                        displayName : '启用',
                                        dictCode : 'TrueFalse',
                                        enableColumnResizing : false,
                                        enableSorting : false,
                                        width : 70
                                    }), $qw.buildColumnFn({
                                        field : 'f_url_id',
                                        displayName : '页面路径',
                                        cellClass : 'text-left',
                                        width : 200
                                    }) ]
                        });

        var addGroupItems = [];
        if ($qw.hasAuthority('XTGL-CDGL-ZJ')) {
            addGroupItems.push($qw.buildAddItemFn({
                click : function(item, gridOptions) {
                    gridOptions.toFormFn(item, gridOptions.getSelectedRowFn());
                }
            }));
        }
        if ($qw.hasAuthority('XTGL-CDGL-XG')) {
            addGroupItems.push($qw.buildEditItemFn({
                click : function(item, gridOptions) {
                    gridOptions.toFormFn(item, gridOptions.getSelectedRowFn());
                }
            }));
        }
        if ($qw.hasAuthority('XTGL-CDGL-SC')) {
            addGroupItems.push($qw.buildRemoveItemFn({
                click : function(item, gridOptions) {
                    gridOptions.toRemoveFn(item, gridOptions);
                }
            }));
        }
        $scope.menuGridOptions.toolbar = $qw.buildButtonBarFn('gridToolbar', [
                $qw.buildGroupFn('addGroup', addGroupItems),
                $qw.buildGroupFn('refreshGroup', [ $qw.buildRefreshItemFn({
                    click : function(item, gridOptions) {
                        gridOptions.loadFn();
                    }
                }) ]) ]);

        // 打开时首次加载表格数据
        $scope.menuGridOptions.loadFn();
    };

    MenuController.$inject = [ '$scope', 'MenuService' ];
    angular.module("app.controllers").controller("MenuController", MenuController);

    // *****************************************************************************************************************
    // MenuFormController begin
    var MenuFormController = function($scope, menuService, menuUrlService) {
        $qw.dev && console.info('MenuFormController');

        if ($scope.formOptions.toolbarItem.id == 'add') {
            var parentMenu = $scope.formOptions.entity;
            $scope.entity.f_parent_id = parentMenu.f_id;
            $scope.entity.f_parent_name = parentMenu.f_menu_name;
            $scope.entity.f_parent_ids = '';
            if (parentMenu.f_parent_ids) {
                $scope.entity.f_parent_ids += parentMenu.f_parent_ids + '/';
            }
            $scope.entity.f_parent_ids += parentMenu.f_id;
            $scope.entity.f_type = parentMenu.f_type + 1;
            $scope.entity.f_order = 10;
            $scope.entity.f_is_show = 1;
        }

        $scope.menuUrlGridOptions = $qw.grid.buildGridOptionsFn($scope, {
            height : 5,
            feature : {
                id : 'menuUrl',
                name : '授权URL',
                path : 'app/sys/dict',
                service : menuUrlService
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
                var gridOptions = this;
                gridOptions.formDialogOptions = $qw.dialog.buildDialogOptionsFn({ // formOptions
                    formTemplateUrl : $qw.getTemplateUrl('app/sys/url/list.html'),
                    gridOptions : gridOptions,
                    toolbarItem : {
                        id : 'search',
                        text : '选择',
                        ico : 'search'
                    },
                    entity : {},
                    selectedFn : function(footbarItem, formOptions, entity, selectedRows) {
                        angular.forEach(selectedRows, function(url) {
                            var hasUrl = false;
                            for ( var i in $scope.menuUrlGridOptions.data) {
                                if ($scope.menuUrlGridOptions.data[i].f_id == url.f_id) {
                                    hasUrl = true;
                                    break;
                                }
                            }
                            if (!hasUrl) {
                                $scope.menuUrlGridOptions.data.push(url);
                            }
                        });

                        var formResult = {
                            formOptions : formOptions,
                            item : footbarItem,
                            entity : entity,
                            result : {},
                            putResponseHeaders : {}
                        };
                        formOptions.modalInstance.close(formResult);
                    }
                }, { // dialogOptions
                    size : ''
                });

                gridOptions.formDialogOptions.openFn();
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
                field : 'f_url',
                displayName : 'URL',
                cellClass : 'text-left',
            }), $qw.buildColumnFn({
                field : 'f_methods',
                displayName : '提交方式',
                width : 90
            }), $qw.buildColumnFn({
                field : 'f_description',
                displayName : 'URL描述',
                cellClass : 'text-left',
                width : 200
            }) ]
        });
        $scope.menuUrlGridOptions.toolbar.showItems([ 'add', 'remove' ]);

        if ($scope.entity.f_id) {
            menuService.get({
                id : $scope.entity.f_id
            }, function(result, putResponseHeaders) {
                $scope.menuUrlGridOptions.data = result.data.entity.menuUrlList;
            });
        }

        $scope.formOptions.footbar.getGroup('submitGroup').getItem('submit').click = function(footbarItem, formOptions,
                entity) {
            entity.menuUrlList = [];
            angular.forEach($scope.menuUrlGridOptions.data, function(url, index, data) {
                entity.menuUrlList.push({
                    f_menu_id : $scope.entity.f_id,
                    f_url_id : url.f_id
                });
            });

            formOptions.submitFn(footbarItem, formOptions, entity);
        };
    };

    MenuFormController.$inject = [ '$scope', 'MenuService', 'MenuUrlService' ];
    angular.module("app.controllers").controller("MenuFormController", MenuFormController);
    // MenuFormController end
    // *****************************************************************************************************************
}(angular));