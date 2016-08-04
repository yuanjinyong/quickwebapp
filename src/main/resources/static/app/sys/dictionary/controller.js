(function(angular) {
    var DictionaryController = function($scope, dictGroupService, dictItemService) {
        $qw.dev && console.info('DictionaryController');

        $scope.feature = {
            id : 'dictGroup',
            name : '字典组',
            path : 'app/sys/dictionary',
            service : dictGroupService
        }

        $scope.dictGroupGridOptions = $qw.grid.buildGridOptionsFn($scope, {
            feature : {
                id : 'dictGroup',
                name : '字典组',
                path : 'app/sys/dictionary',
                service : dictGroupService
            },
            heightDiff : 30,
            onLoadedSuccess : function(result) {
                this.toolbar.enableItems([ 'add', 'refresh' ]);
                $scope.dictItemGridOptions.data = [];
                $scope.dictItemGridOptions.toolbar.enableItems([]);
            },
            onRowSelectionChanged : function(row) {
                if (!row.isSelected) {
                    this.toolbar.enableItems([ 'add', 'refresh' ]);
                } else {
                    this.toolbar.enableAllItems();
                }

                $scope.dictItemGridOptions.loadFn();
            },
            columnDefs : [ $qw.buildLinkColumnFn({
                field : 'f_code',
                name : 'f_code_like',
                displayName : '编码',
                cellClass : 'text-left',
                pinnedLeft : true, // 靠左固定列
                width : 200
            }), $qw.buildColumnFn({
                field : 'f_remark',
                displayName : '描述',
                cellClass : 'text-left',
                width : 150
            }), $qw.buildColumnFn({
                field : 'f_type',
                displayName : '类型',
                cellClass : 'text-left',
                width : 60
            }), $qw.buildColumnFn({
                field : 'f_sql',
                displayName : 'SQL语句',
                cellClass : 'text-left',
                enableFiltering : false,
            }) ]
        });

        var addGroupItems = [];
        if ($qw.hasAuthority('XTGL-ZDGL-ZJ')) {
            addGroupItems.push($qw.buildAddItemFn({
                click : function(item, gridOptions) {
                    gridOptions.toFormFn(item, gridOptions.getSelectedRowFn());
                }
            }));
        }
        if ($qw.hasAuthority('XTGL-ZDGL-XG')) {
            addGroupItems.push($qw.buildEditItemFn({
                click : function(item, gridOptions) {
                    gridOptions.toFormFn(item, gridOptions.getSelectedRowFn());
                }
            }));
        }
        if ($qw.hasAuthority('XTGL-ZDGL-SC')) {
            addGroupItems.push($qw.buildRemoveItemFn({
                click : function(item, gridOptions) {
                    gridOptions.toRemoveFn(item, gridOptions);
                }
            }));
        }
        $scope.dictGroupGridOptions.toolbar = $qw.buildButtonBarFn('gridToolbar', [
                $qw.buildGroupFn('addGroup', addGroupItems),
                $qw.buildGroupFn('refreshGroup', [ $qw.buildRefreshItemFn({
                    click : function(item, gridOptions) {
                        gridOptions.loadFn();
                    }
                }) ]) ]);

        // 打开时首次加载表格数据
        $scope.dictGroupGridOptions.loadFn();

        // ///
        $scope.dictItemGridOptions = $qw.grid.buildGridOptionsFn($scope, {
            feature : {
                id : 'dictItem',
                name : '字典项',
                // path : 'app/sys/dictionary',
                formTemplateUrl : 'app/sys/dictionary/item.html',
                service : dictItemService
            },
            heightDiff : 30,
            enableFiltering : false,
            loadFn : function(params, isExporterAllData) {
                var self = this;
                var dictGroup = $scope.dictGroupGridOptions.getSelectedRowFn();

                if (dictGroup) {
                    dictGroupService.get({
                        id : dictGroup.f_id
                    }, function(result, putResponseHeaders) {
                        dictItemService.dictGroup = result.data.entity;
                        self.toolbar.enableItems([ 'add' ]);
                        self.data = result.data.entity.itemList;
                    });
                } else {
                    dictItemService.dictGroup = null;
                    self.toolbar.enableItems([]);
                    self.data = [];
                }
            },
            onRowSelectionChanged : function(row) {
                if (!row.isSelected) {
                    this.toolbar.enableItems([ 'add' ]);
                } else {
                    this.toolbar.enableAllItems();
                }
            },
            columnDefs : [ $qw.buildLinkColumnFn({
                field : 'f_item_code',
                name : 'f_item_code',
                displayName : '编码',
                cellClass : 'text-left',
                pinnedLeft : true, // 靠左固定列
                width : 60
            }), $qw.buildColumnFn({
                field : 'f_item_text',
                displayName : '描述',
                cellClass : 'text-left',
                width : 100
            }), $qw.buildColumnFn({
                field : 'f_item_order',
                displayName : '排序',
                cellClass : 'text-left',
                width : 60
            }) ]
        });
        if ($qw.currentUser.user.superAdmin) {
            $scope.dictItemGridOptions.columnDefs.push($qw.buildColumnFn({
                field : 'f_tenant_cid',
                displayName : 'CID',
                width : 60
            }));
            $scope.dictItemGridOptions.columnDefs.push($qw.buildColumnFn({
                field : 'f_tenant_bid',
                displayName : 'BID',
                width : 60
            }));
        }
        $scope.dictItemGridOptions.toolbar = $qw.buildButtonBarFn('dictItemGridToolbar', [ $qw.buildGroupFn('addGroup',
                angular.copy(addGroupItems)) ]);
        $scope.dictItemGridOptions.toolbar.enableItems([]);
    };

    DictionaryController.$inject = [ '$scope', 'DictGroupService', 'DictItemService' ];
    angular.module("app.controllers").controller("DictionaryController", DictionaryController);

    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    var DictGroupFormController = function($scope, dictGroupService) {
        $qw.dev && console.log('DictGroupFormController');
    };

    DictGroupFormController.$inject = [ '$scope', 'DictGroupService' ];
    angular.module("app.controllers").controller("DictGroupFormController", DictGroupFormController);

    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    var DictItemFormController = function($scope, dictItemService) {
        $qw.dev && console.log('DictItemFormController');

        $scope.entity.f_code = dictItemService.dictGroup.f_code;
        $scope.entity.groupId = dictItemService.dictGroup.f_id;
    };

    DictItemFormController.$inject = [ '$scope', 'DictItemService' ];
    angular.module("app.controllers").controller("DictItemFormController", DictItemFormController);
}(angular));