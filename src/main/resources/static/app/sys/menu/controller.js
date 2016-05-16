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
                                                cellTemplate : '<div class="ui-grid-cell-contents" style="padding-left:{{row.entity.$$treeLevel*14}}px"><span class="glyphicon glyphicon-{{row.entity.f_icon || (row.entity.f_type < 2 ? \'menu-hamburger\' : \'file\')}}"><span>{{grid.getCellValue(row, col)}}</div>',
                                                pinnedLeft : true,// 靠左固定列
                                                width : 300
                                            }), $qw.buildColumnFn({
                                        field : 'f_order',
                                        displayName : '排序',
                                        width : 55
                                    }), $qw.buildLinkColumnFn({
                                        field : 'f_id',
                                        displayName : '菜单编码',
                                        cellClass : 'text-left',
                                        pinnedLeft : true,// 靠左固定列
                                        width : 200
                                    }), $qw.buildColumnFn({
                                        field : 'f_menu_remark',
                                        displayName : '菜单描述',
                                        cellClass : 'text-left',
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

        // 打开时首次加载表格数据
        $scope.menuGridOptions.loadFn();
    };

    MenuController.$inject = [ '$scope', 'MenuService' ];
    angular.module("app.controllers").controller("MenuController", MenuController);

    // *****************************************************************************************************************
    // MenuFormController begin
    var MenuFormController = function($scope, menuService) {
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
    };

    MenuFormController.$inject = [ '$scope', 'MenuService' ];
    angular.module("app.controllers").controller("MenuFormController", MenuFormController);
    // MenuFormController end
    // *****************************************************************************************************************
}(angular));